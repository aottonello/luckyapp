package com.lucky.app.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lucky.app.R
import com.lucky.app.actions.LuckyActions
import com.lucky.app.databinding.FragmentListBinding
import com.lucky.app.di.LuckyViewModelProviderFactory
import com.lucky.app.models.LuckyHomeModel
import com.lucky.app.models.LuckyItemModel
import com.lucky.app.models.LuckySectionsModel
import com.lucky.app.utils.concatString
import com.lucky.app.utils.setVerticalLayoutManager
import com.lucky.app.utils.strToLowerCase
import com.lucky.app.viewmodels.LuckyHomeViewModel
import com.lucky.app.views.items.LuckyHeaderSectionItem
import com.lucky.app.views.items.LuckyViewItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

/**
 * Fragment with:
 * 1.List with sections
 * 2.Search in AppBar, filter items and reload list
 * 3.Items with actions to navigate Detail View
 */
class LuckySectionsFragment : DaggerFragment(),
    LuckyViewItem.Interaction {

    private lateinit var viewModel: LuckyHomeViewModel

    @Inject
    lateinit var viewmodelProviderFactory: LuckyViewModelProviderFactory

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val groupAdapter by lazy { GroupAdapter<GroupieViewHolder>() }
    private lateinit var model: LuckyHomeModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        (activity as LuckyMainActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(false)
        return resolveBinding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        initRecyclerView()
        subscribeToViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        (activity as LuckyMainActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)
        (activity as LuckyMainActivity).getSupportActionBar()?.setDisplayShowTitleEnabled(true)
        super.onResume()
    }

    private fun resolveBinding(): View? {
        _binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, viewmodelProviderFactory).get(LuckyHomeViewModel::class.java)
    }

    private fun initRecyclerView() {
        binding.apply {
            recyclerView.apply {
                setVerticalLayoutManager()
                adapter = groupAdapter
            }
        }
    }

    private fun subscribeToViewModel() {
        viewModel.apply {
            showProgress.observe(viewLifecycleOwner, Observer { showLoading(it) })
            showErrorMessage.observe(viewLifecycleOwner, Observer { handleError(it) })
            uiActionModel.observe(viewLifecycleOwner, Observer { handleModelUpdate(it) })
        }
        viewModel.fetchHomeSections()
    }

    private fun handleModelUpdate(sectionsModel: LuckyActions) {
        groupAdapter.apply {
            clear()
            if (sectionsModel is LuckyActions.SectionsHome) {
                model = sectionsModel.data
                sectionsModel.data.sections?.let { updateAdapter() }
            }
        }
    }

    private fun updateAdapter() {
        groupAdapter.apply {
            clear()
            var offers = 0
            model.sections?.map {
                add(Section(LuckyHeaderSectionItem(it.title.orEmpty())))
                it.items?.map { LuckyViewItem(it, this@LuckySectionsFragment) }?.let { addAll(it) }
                offers += (it.items?.size ?: 0)
            }
            binding.txtOffers.text = model.title.concatString(offers)
        }
    }

    private fun handleError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(show: Boolean) {
        if (activity is LuckyMainActivity) {
            (activity as LuckyMainActivity).showLoader(show)
        }
    }

    override fun onItemSelected(item: LuckyItemModel) {
        val navDirection = LuckySectionsFragmentDirections.actionListFragmentToDetailFragment()
        findNavController().navigate(navDirection)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.copy_search_bar)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(txt: String?): Boolean {
                applyFilter(txt.strToLowerCase())
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun applyFilter(search: String) {
        groupAdapter.apply {
            clear()
            var offers = 0
            model.sections?.map { section ->
                var itemsModel = section.items?.filter {
                    it.brand.strToLowerCase().contains(search)
                            || it.tags.strToLowerCase().contains(search)
                            || it.title.strToLowerCase().contains(search)
                }

                itemsModel?.let { itemModel ->
                    if (itemModel.isNotEmpty()) {
                        add(Section(LuckyHeaderSectionItem(section.title.orEmpty())))
                        offers += itemModel.size
                    }
                    itemsModel?.map { LuckyViewItem(it, this@LuckySectionsFragment) }
                        ?.let { addAll(it) }

                }
            }
            binding.txtOffers.text = model.title.concatString(offers)
        }

    }
}



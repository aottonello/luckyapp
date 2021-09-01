package com.lucky.app.ui

import android.graphics.Paint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lucky.app.R
import com.lucky.app.actions.LuckyActions
import com.lucky.app.databinding.FragmentDetailBinding
import com.lucky.app.di.LuckyViewModelProviderFactory
import com.lucky.app.utils.formatFloatingPoint
import com.lucky.app.utils.loadImageFromUrl
import com.lucky.app.utils.setAnimationImage
import com.lucky.app.viewmodels.LuckyHomeViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/**
 * Fragment with:
 * 1.Detail item model
 * 2.Menu item in app Bar to activate animation
 * 3.scrollable view
 */
class LuckyDetailFragment : DaggerFragment() {

    private lateinit var viewModel: LuckyHomeViewModel

    @Inject
    lateinit var viewmodelProviderFactory: LuckyViewModelProviderFactory

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var favorite: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        (activity as LuckyMainActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        (activity as LuckyMainActivity).getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        return resolveBinding()
    }

    private fun resolveBinding(): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        subscribeToViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeToViewModel() {
        viewModel.apply {
            showProgress.observe(viewLifecycleOwner, Observer { showLoading(it) })
            showErrorMessage.observe(viewLifecycleOwner, Observer { handleError(it) })
            uiActionModel.observe(viewLifecycleOwner, Observer { handleModelUpdate(it) })
        }
        activity?.let { viewModel.fetchDetailItem(it.baseContext) }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, viewmodelProviderFactory).get(LuckyHomeViewModel::class.java)
    }

    private fun handleError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(show: Boolean) {
        if (activity is LuckyMainActivity) {
            (activity as LuckyMainActivity).showLoader(show)
        }
    }

    private fun handleModelUpdate(itemDetail: LuckyActions) {
        if (itemDetail is LuckyActions.ItemDetail) {
            binding.apply {
                itemDetail.data.apply {
                    imageUrl?.let { imgFirst.loadImageFromUrl(it, context = root.context) }
                    txtBrand.text = brand
                    txtFavoriteCount.text = formatFloatingPoint(favoriteCount)
                    txtDetail.text = title
                    txtDescription.text = description
                    txtPrice.text = labelPrice
                    txtPriceDiscount.text = priceDiscount
                    txtPriceValue.text = price
                    txtDate.text = date
                    txtRedemption.text = redemption
                    txtPriceDiscount.setPaintFlags(txtPriceDiscount.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu_like, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_like -> {
            setAnimationImage(binding.root.context, binding.imgSecond)
            if (favorite) {
                favorite = false
                item.setIcon(R.drawable.ic_like_filled)
            } else {
                favorite = true
                item.setIcon(R.drawable.ic_like_outline)
            }
            true
        }

        android.R.id.home -> {
            activity?.onBackPressed()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}


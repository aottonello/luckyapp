package com.lucky.app.views.items

import android.view.View
import com.lucky.app.R
import com.lucky.app.databinding.ViewItemBinding
import com.lucky.app.models.LuckyItemModel
import com.lucky.app.utils.executeAfterOnClickListenerWithoutRepeated
import com.lucky.app.utils.formatFloatingPoint
import com.lucky.app.utils.loadImageFromUrl
import com.xwray.groupie.viewbinding.BindableItem

/**
 * Visual Representation to LuckyItemModel
 * Has listener to interact with user events
 */
class LuckyViewItem(
    private val item: LuckyItemModel,
    private val listener: Interaction,
) : BindableItem<ViewItemBinding>() {

    private lateinit var binding: ViewItemBinding

    override fun bind(viewBinding: ViewItemBinding, position: Int) {
        binding = viewBinding
        binding.apply {
            txtBrand.text = item.brand
            txtDetail.text = item.title
            txtTags.text = item.tags
            txtFavoriteCount.text = formatFloatingPoint(item.favoriteCount)
            item.imageUrl?.let { imageViewIcon.loadImageFromUrl(it, context = root.context) }

            root.executeAfterOnClickListenerWithoutRepeated {
                listener.onItemSelected(item)
            }
        }
    }

    override fun getLayout(): Int = R.layout.view_item

    override fun initializeViewBinding(view: View): ViewItemBinding {
        return ViewItemBinding.bind(view)
    }

    interface Interaction {
        fun onItemSelected(item: LuckyItemModel)
    }
}
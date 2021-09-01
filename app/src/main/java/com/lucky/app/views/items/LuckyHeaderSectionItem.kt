package com.lucky.app.views.items

import android.view.View
import com.lucky.app.R
import com.lucky.app.databinding.HeaderSectionItemBinding
import com.xwray.groupie.viewbinding.BindableItem

/**
 * Header item for each Section in Home list
 */
class LuckyHeaderSectionItem(private val title: String) : BindableItem<HeaderSectionItemBinding>() {

    private lateinit var binding: HeaderSectionItemBinding

    override fun bind(viewBinding: HeaderSectionItemBinding, position: Int) {
        binding = viewBinding
        binding.apply {
            textViewTitle.text = title
        }
    }
    override fun getLayout(): Int = R.layout.header_section_item
    override fun initializeViewBinding(view: View): HeaderSectionItemBinding {
        return HeaderSectionItemBinding.bind(view)
    }
}
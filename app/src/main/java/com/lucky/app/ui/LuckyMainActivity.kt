package com.lucky.app.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.lucky.app.R
import com.lucky.app.databinding.ActivityMainBinding
import com.lucky.app.utils.removeFromParent
import com.lucky.app.views.LuckyLoadingView
import dagger.android.support.DaggerAppCompatActivity

/**
 * Main Activity
 * Has navController to navigate between the different fragments
 */
class LuckyMainActivity : DaggerAppCompatActivity() {

    lateinit var navController: NavController
    private val loadingView by lazy { LuckyLoadingView(this) }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resolveBinding()?.let { setContentView(it) }
        navController = findNavController(R.id.container)
    }

    private fun resolveBinding(): View? {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    fun showLoader(show: Boolean) {
        if (show) {
            if (loadingView.parent == null) {
                loadingView.setMessage(getString(R.string.copy_loading))
                addContentView(
                    loadingView,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
            }
        } else {
            loadingView.removeFromParent()
        }
    }
}
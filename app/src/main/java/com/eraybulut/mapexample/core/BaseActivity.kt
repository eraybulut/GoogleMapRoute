package com.eraybulut.mapexample.core

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.ViewBinding
import com.eraybulut.mapexample.R
import com.eraybulut.mapexample.util.extensions.collectLast

/**
 * Created by Eray BULUT on 22.12.2023
 * eraybulutlar@gmail.com
 */

abstract class BaseActivity<T : ViewBinding, out VM : BaseViewModel>(
    private val bindingFactory: (LayoutInflater) -> T,
) : AppCompatActivity() {

    protected abstract val viewModel: VM

    lateinit var binding: T

    private val loadingDialog: AlertDialog by lazy { createLoadingDialog() }

    protected open fun onCreateFinished() {}
    protected open fun initializeListeners() {}
    protected open fun observeEvents() {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory.invoke(layoutInflater)
        setContentView(binding.root)

        collectLast(viewModel.loading, ::handleLoadingStatus)
        onCreateFinished()
        initializeListeners()
        observeEvents()
    }

    private fun handleLoadingStatus(isLoading: Boolean) {
        if (isLoading) {
            loadingDialog.show()
        } else {
            loadingDialog.cancel()
            loadingDialog.dismiss()
        }
    }

    private fun createLoadingDialog(): AlertDialog {
        val loadingView = LayoutInflater.from(this).inflate(R.layout.view_loading, null)
        val imageView = loadingView.findViewById<ImageView>(R.id.ic_loading_icon)
        val rotation = AnimationUtils.loadAnimation(this, R.anim.rotate)

        val dialog = AlertDialog.Builder(this).setView(loadingView).setCancelable(false).create()
        dialog.apply {
            window?.apply {
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                )
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                )
            }

            setOnShowListener {
                imageView.startAnimation(rotation)
            }

            setOnDismissListener {
                imageView.clearAnimation()
            }
        }

        return dialog
    }

    override fun onResume() {
        super.onResume()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}

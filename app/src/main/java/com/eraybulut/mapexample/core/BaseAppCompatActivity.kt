package com.eraybulut.mapexample.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.ViewBinding

/**
 * Created by Eray BULUT on 13.08.2023
 * eraybulutlar@gmail.com
 */

abstract class BaseAppCompatActivity<T : ViewBinding>(
    private val bindingFactory: (layoutInflater: android.view.LayoutInflater) -> T
) : AppCompatActivity() {

    lateinit var binding: T

    open fun setListeners() {}

    open fun setObservers() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory.invoke(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)
        setListeners()
        setObservers()
    }
}
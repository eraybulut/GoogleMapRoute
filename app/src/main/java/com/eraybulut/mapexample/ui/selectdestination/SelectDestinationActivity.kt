package com.eraybulut.mapexample.ui.selectdestination

import android.os.Bundle
import androidx.activity.viewModels
import com.eraybulut.mapexample.core.BaseAppCompatActivity
import com.eraybulut.mapexample.databinding.ActivitySelectDestinationBinding
import com.eraybulut.mapexample.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SelectDestinationActivity : BaseAppCompatActivity<ActivitySelectDestinationBinding,HomeViewModel>(
    ActivitySelectDestinationBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
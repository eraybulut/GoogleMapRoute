package com.eraybulut.mapexample.ui.selectdestination

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.eraybulut.mapexample.core.BaseActivity
import com.eraybulut.mapexample.data.model.response.Prediction
import com.eraybulut.mapexample.databinding.ActivitySelectDestinationBinding
import com.eraybulut.mapexample.ui.placedetail.PlaceDetailActivity
import com.eraybulut.mapexample.util.extensions.collectLast
import com.eraybulut.mapexample.util.extensions.focusAndShowKeyboard
import com.eraybulut.mapexample.util.extensions.textChangesToFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SelectDestinationActivity :
    BaseActivity<ActivitySelectDestinationBinding, SelectDestinationViewModel>(
        ActivitySelectDestinationBinding::inflate
    ) {

    override val viewModel: SelectDestinationViewModel by viewModels()

    @Inject
    lateinit var addressAdapter: AddressAdapter

    override fun onCreateFinished() {
        setupAddressRecyclerView()
        listenToSearchLocationEditText()
        focusAndShowKeyboard()
    }

    override fun observeEvents() {
        with(viewModel) {
            collectLast(address, ::setAddressList)
        }
    }

    private fun focusAndShowKeyboard() {
        binding.acTextLocation.focusAndShowKeyboard()
    }

    private fun setupAddressRecyclerView() {
        with(binding) {
            addressAdapter.setOnItemClickListener { addressId ->
                val intent = Intent(this@SelectDestinationActivity, PlaceDetailActivity::class.java)
                intent.putExtra(PLACE_ID, addressId)
                startActivity(intent)
            }
            rvAddress.adapter = addressAdapter
        }
    }

    private fun setAddressList(addressList: List<Prediction>) {
        addressAdapter.setAddress(addressList)
    }

    private fun listenToSearchLocationEditText() {
        with(binding) {
            lifecycleScope.launch {
                acTextLocation.textChangesToFlow()
                    .debounce(SEARCH_DEBOUNCE_TIME)
                    .distinctUntilChanged()
                    .filter {
                        it.isNotEmpty()
                    }.collect { query ->
                        sendSearchLocationRequest(query)
                    }
            }
        }
    }

    private fun sendSearchLocationRequest(query: String) {
        viewModel.searchLocation(query)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_TIME = 300L
        const val PLACE_ID = "place_id"
    }
}

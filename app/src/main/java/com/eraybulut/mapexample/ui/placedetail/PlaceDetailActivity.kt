package com.eraybulut.mapexample.ui.placedetail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import coil.load
import com.eraybulut.mapexample.core.BaseActivity
import com.eraybulut.mapexample.data.PrefDataSource
import com.eraybulut.mapexample.data.model.response.Photo
import com.eraybulut.mapexample.data.model.response.PlaceDetailsResponse
import com.eraybulut.mapexample.data.model.response.SelectedAddressModel
import com.eraybulut.mapexample.databinding.ActivityPlaceDetailBinding
import com.eraybulut.mapexample.ui.home.HomeActivity
import com.eraybulut.mapexample.ui.selectdestination.SelectDestinationActivity
import com.eraybulut.mapexample.util.extensions.collectLast
import com.eraybulut.mapexample.util.extensions.openGoogleMaps
import com.eraybulut.mapexample.util.extensions.orUndefined
import com.eraybulut.mapexample.util.extensions.setCall
import com.eraybulut.mapexample.util.extensions.showToast
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaceDetailActivity : BaseActivity<ActivityPlaceDetailBinding, PlaceDetailViewModel>(
    ActivityPlaceDetailBinding::inflate
) {

    override val viewModel: PlaceDetailViewModel by viewModels()

    @Inject
    lateinit var photoAdapter: PhotoAdapter

    @Inject
    lateinit var prefDataSource: PrefDataSource

    private var placeLatLng: LatLng? = null
    private var placePhoneNumber: String? = null

    override fun onCreateFinished() {
        sendPlaceDetailRequest()
        setupPhotoRecyclerView()
    }

    override fun initializeListeners() {
        with(binding) {
            btnCall.setOnClickListener {
                if (placePhoneNumber.isNullOrEmpty()) {
                    showToast("Phone number is empty")
                    return@setOnClickListener
                }
                setCall(placePhoneNumber.orEmpty())
            }

            btnOpenMap.setOnClickListener {
                openGoogleMaps(
                    lat = placeLatLng?.latitude.toString(),
                    lng = placeLatLng?.longitude.toString()
                )
            }

            btnChooseDestination.setOnClickListener {
                if (placeLatLng == null) {
                    showToast("Place lat lng is empty")
                    return@setOnClickListener
                }

                val addressModel = SelectedAddressModel(
                    address = "Selected Address",
                    latLng = placeLatLng ?: LatLng(0.0, 0.0)
                )
                prefDataSource.saveData(addressModel)
                val intent = Intent(this@PlaceDetailActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }

            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun observeEvents() {
        with(viewModel) {
            collectLast(placeDetail, ::setPlaceDetailUi)
        }
    }

    private fun sendPlaceDetailRequest() {
        val placeId = intent.getStringExtra(SelectDestinationActivity.PLACE_ID)
        placeId?.let {
            viewModel.getPlaceDetail(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setPlaceDetailUi(placeDetail: PlaceDetailsResponse) {
        with(binding) {
            txtPlaceName.text = placeDetail.result?.name.orUndefined()
            txtPlaceAddress.text = placeDetail.result?.address.orUndefined()
            txtPlacePhone.text = placeDetail.result?.phoneNumber.orUndefined()
            txtPlaceIsOpenNow.text =
                "İs Open :" + when (placeDetail.result?.openingHours?.openNow) {
                    true -> "Open"
                    false -> "Closed"
                    else -> "Unknown"
                }
            txtPlaceWeekdayText.text =
                "Place Weekday Text: ${
                    placeDetail.result?.openingHours?.weekdayText?.joinToString("\n").orUndefined()
                }"
            icLocationIcon.load(placeDetail.result?.icon.orEmpty())

            setPhotoList(placeDetail.result?.photos.orEmpty())
            placePhoneNumber = placeDetail.result?.phoneNumber
            placeLatLng = LatLng(
                placeDetail.result?.geometry?.location?.lat ?: 0.0,
                placeDetail.result?.geometry?.location?.lng ?: 0.0
            )
        }
    }

    private fun setupPhotoRecyclerView() {
        with(binding) {
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(rvPlacePhotos)
            rvPlacePhotos.adapter = photoAdapter
        }
    }

    private fun setPhotoList(photos: List<Photo>) {
        photoAdapter.setPhotos(photos)
    }
}
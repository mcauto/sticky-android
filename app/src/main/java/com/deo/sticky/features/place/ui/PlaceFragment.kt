package com.deo.sticky.features.place.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.deo.sticky.R
import com.deo.sticky.databinding.FragmentCheckInPlaceBinding
import com.deo.sticky.features.checkin.ui.CheckInFragment
import com.deo.sticky.features.checkin.ui.CheckInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class PlaceFragment constructor(
    private val _checkInviewModel: CheckInViewModel
) : Fragment(R.layout.fragment_check_in_place) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentCheckInPlaceBinding.bind(view).apply {
            next.setOnClickListener {
                Timber.w("장소명: ${_checkInviewModel.placeName.get()}")
                val parent = parentFragment as CheckInFragment
                parent.next()
            }
            checkinViewModel = _checkInviewModel
        }
    }
}

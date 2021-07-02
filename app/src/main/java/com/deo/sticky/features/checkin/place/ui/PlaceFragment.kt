package com.deo.sticky.features.checkin.place.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.deo.sticky.R
import com.deo.sticky.base.BindableFragment
import com.deo.sticky.databinding.FragmentCheckInPlaceBinding
import com.deo.sticky.features.checkin.CheckInFragment
import com.deo.sticky.features.checkin.PlaceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@AndroidEntryPoint
@ExperimentalCoroutinesApi
internal class PlaceFragment constructor(
    private val _placeViewModel: PlaceViewModel
) : BindableFragment<FragmentCheckInPlaceBinding>(R.layout.fragment_check_in_place) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            placeViewModel = _placeViewModel
            // EditableInputConnection memory leaks 존재
            // android bug로 hack보다는 구글이 해결하게 두는게 나을 것 같다
            // 참고: https://github.com/fossasia/badge-magic-android/issues/316 > https://issuetracker.google.com/issues/37043700
            editText.doOnTextChanged { text, _, _, _ ->
                _placeViewModel.onPlaceName("$text")
            }
            next.setOnClickListener {
                Timber.w("장소명: ${_placeViewModel.placeName}")
                val parent = parentFragment as CheckInFragment
                editText.clearFocus()
                parent.next()
            }
        }
    }
}

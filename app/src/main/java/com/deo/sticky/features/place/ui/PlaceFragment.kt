package com.deo.sticky.features.place.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.deo.sticky.R
import com.deo.sticky.databinding.FragmentCheckInPlaceBinding
import com.deo.sticky.features.checkin.ui.CheckInFragment

class PlaceFragment : Fragment(R.layout.fragment_check_in_place) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCheckInPlaceBinding.bind(view)
        binding.next.setOnClickListener {
            val parent = parentFragment as CheckInFragment
            parent.next()
        }
    }
}

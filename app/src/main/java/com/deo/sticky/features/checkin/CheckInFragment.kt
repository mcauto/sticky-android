package com.deo.sticky.features.checkin

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.deo.sticky.R
import com.deo.sticky.base.BindableFragment
import com.deo.sticky.databinding.FragmentCheckInBinding
import com.deo.sticky.features.RequestKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@AndroidEntryPoint
@ExperimentalCoroutinesApi
internal class CheckInFragment :
    BindableFragment<FragmentCheckInBinding>(R.layout.fragment_check_in) {
    private val _viewPagerViewModel: ViewPagerViewModel by activityViewModels()
    private val _checkInViewModel: CheckInViewModel by activityViewModels()
    private val _placeViewModel: PlaceViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = FragmentViewPager(this@CheckInFragment, _viewPagerViewModel.totalPage)
        binding.apply {
            viewPagerViewModel = _viewPagerViewModel
            back.setOnClickListener {
                _viewPagerViewModel.onClickBack()
                _placeViewModel.initialize()
                Timber.d("뒤로가기")
            }
            progress.apply {
                _viewPagerViewModel.onChangePage(1)
            }
            viewPager.apply {
                isUserInputEnabled = false
                adapter = pagerAdapter
            }
        }
        _checkInViewModel.isCheckIn.observe(viewLifecycleOwner) {
            Timber.d("체크인: $it")
            if (it) {
                setFragmentResult(RequestKey.checkIn, bundleOf("checkIn" to true))
                _viewPagerViewModel.initialize()
                findNavController().popBackStack()
            }
        }
        _viewPagerViewModel.currentPage.observe(viewLifecycleOwner) {
            if (it > 0) {
                binding.viewPager.setCurrentItem(it - 1, true)
            } else {
                _viewPagerViewModel.initialize()
                findNavController().navigateUp()
            }
        }
    }
}

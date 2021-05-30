package com.deo.sticky.features.category.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.deo.sticky.R
import com.deo.sticky.databinding.FragmentCheckInCategoryBinding
import com.deo.sticky.features.category.models.Category
import com.deo.sticky.features.checkin.ui.CheckInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class CategoryFragment constructor(
    val viewModel: CheckInViewModel
) : Fragment(R.layout.fragment_check_in_category) {
    private val listener = object : CategoryAdapter.OnItemClickListener {
        override fun onItemClicked(category: Category) {
            // check 하기
            viewModel.onCategorySelected(category)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryAdapter = CategoryAdapter(listener)
        FragmentCheckInCategoryBinding.bind(view).apply {
            recycler.apply {
                adapter = categoryAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            checkinViewModel = viewModel
            start.setOnClickListener {
                it.findNavController().popBackStack()
            }
        }
        viewModel.categories.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
        }
    }
}

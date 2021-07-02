package com.deo.sticky.features.checkin.category.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.deo.sticky.R
import com.deo.sticky.base.BindableFragment
import com.deo.sticky.databinding.FragmentCheckInCategoryBinding
import com.deo.sticky.features.checkin.CategoryViewModel
import com.deo.sticky.features.checkin.category.models.Category
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
internal class CategoryFragment constructor(
    private val _categoryViewModel: CategoryViewModel
) : BindableFragment<FragmentCheckInCategoryBinding>(R.layout.fragment_check_in_category) {
    private val listener = object : CategoryAdapter.OnItemClickListener {
        override fun onItemClicked(category: Category) {
            // check 하기
            _categoryViewModel.onSelectedCategory(category)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryAdapter = CategoryAdapter(listener)
        binding.apply {
            categoryViewModel = _categoryViewModel
            recycler.apply {
                adapter = categoryAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            start.setOnClickListener {
                it.findNavController().popBackStack()
            }
        }

        _categoryViewModel.categories.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
        }
    }
}

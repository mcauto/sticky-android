package com.deo.sticky.features.category.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.deo.sticky.R
import com.deo.sticky.databinding.FragmentCheckInCategoryBinding
import com.deo.sticky.features.category.models.Category
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CategoryFragment :
    Fragment(R.layout.fragment_check_in_category),
    CategoryAdapter.OnItemClickListener {
    private val viewModel by viewModels<CategoryViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCheckInCategoryBinding.bind(view)
        val categoryAdapter = CategoryAdapter(this)
        binding.apply {
            recycler.apply {
                adapter = categoryAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel.categories.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
        }
    }

    override fun onItemClicked(category: Category) {
        // check 하기
        Timber.w(category.toString())
    }
}

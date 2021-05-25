package com.deo.sticky.features.category.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deo.sticky.R
import com.deo.sticky.databinding.ViewholderCategoryItemBinding
import com.deo.sticky.features.category.models.Category

class CategoryAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CATEGORY_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ViewholderCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class CategoryViewHolder(
        private val binding: ViewholderCategoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val category = getItem(position)
                        listener.onItemClicked(category)
                        count.isVisible = false
                        background.setBackgroundResource(R.color.accentTertiary)
                    }
                }
            }
        }

        fun bind(category: Category) {
            binding.apply {
                imageView.setImageResource(category.imageResourceId)
                imageViewBackground.setBackgroundResource(category.backgroundResourceId)
                name.text = category.name
                count.text = category.count.toString()
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(category: Category)
    }

    companion object {
        private val CATEGORY_COMPARATOR = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Category, newItem: Category) =
                oldItem == newItem
        }
    }
}

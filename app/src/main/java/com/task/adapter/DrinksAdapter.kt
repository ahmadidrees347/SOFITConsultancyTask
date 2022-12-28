package com.task.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.task.databinding.ItemDrinkBinding
import com.task.model.Drink

class DrinksAdapter(
    private val context: Context
) : RecyclerView.Adapter<DrinksAdapter.ItemViewHolder>() {

    val drinksList: MutableList<Drink> = mutableListOf()
    var favoriteListener: ((Drink) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemDrinkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAllData(list:MutableList<Drink>) {
        drinksList.clear()
        drinksList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(drinksList[position], position)
    }

    inner class ItemViewHolder(
        private val binding: ItemDrinkBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: Drink, position: Int) {
            with(binding) {
                Glide.with(context)
                    .load(item.strDrinkThumb)
                    .circleCrop()
                    .into(image)
                title.text = item.strDrink
                description.text = item.strInstructions
                favoriteIcon.isSelected = item.isFav

                if (item.strAlcoholic == "Alcoholic") {
                    checkbox.isChecked = true
                    checkbox.isEnabled = false
                } else {
                    checkbox.isChecked = false
                    checkbox.isEnabled = false
                }

                favoriteIcon.setOnClickListener {
                    val newStatus = !it.isSelected
                    drinksList[adapterPosition].isFav = newStatus
                    favoriteListener?.invoke(item)
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return drinksList.size
    }
}



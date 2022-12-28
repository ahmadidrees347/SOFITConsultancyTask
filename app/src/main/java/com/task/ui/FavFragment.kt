package com.task.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.task.adapter.DrinksAdapter
import com.task.databinding.FragmentFavBinding
import com.task.model.DrinkState

class FavFragment : BaseFragment() {

    private lateinit var binding: FragmentFavBinding
    private val drinkAdapter by lazy { DrinksAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        getAllFavDrinks()
    }

    private fun initAdapter() {
        with(binding) {
            drinkAdapter.favoriteListener = { model ->
                favDrinksViewModel.updateFavItem(model)
            }
            recyclerFavDrinks.adapter = drinkAdapter
        }
    }

    private fun getAllFavDrinks() {
        favDrinksViewModel.getAllFavDrinks()
        lifecycleScope.launchWhenResumed {
            favDrinksViewModel.favDrinksData.collect {
                when (it) {
                    is DrinkState.Loading -> {
                        Log.e("chat*", "*Response: Loading")
                    }
                    is DrinkState.SuccessDrinks -> {
                        val list = it.drinkList.toMutableList()
                        Log.e("chat*", "*Response: Success ${list.size}")
                        list.forEach { drink ->
                            drink.isFav = true
                        }
                        drinkAdapter.addAllData(list)
                    }
                    is DrinkState.Failure -> {
                        Log.e("chat*", "*Response: ${it.error}")
                    }
                    is DrinkState.Empty -> {
                        Log.e("chat*", "*Response: Empty")
                    }
                    else -> {}
                }
            }
        }

    }
}
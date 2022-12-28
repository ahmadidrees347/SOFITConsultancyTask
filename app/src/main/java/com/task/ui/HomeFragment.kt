package com.task.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.task.R
import com.task.adapter.DrinksAdapter
import com.task.databinding.FragmentHomeBinding
import com.task.model.DrinkState

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private var isNameSelected = true
    private val drinkAdapter by lazy { DrinksAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

    }

    private fun initViews() {
        val lastSearchedText = prefUtils.getString("lastSearched", "margarita")
        with(binding) {
            drinkAdapter.favoriteListener = { model ->
                favDrinksViewModel.updateFavItem(model)
            }
            recyclerDrinks.adapter = drinkAdapter
            imgSearch.setOnClickListener {
                val text = edtSearchDrink.text.toString().trim()
                if (text.isNotEmpty())
                    searchDrinkByName(text)
            }
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbByName -> {
                        prefUtils.putString("lastCategory", "by_name")
                        isNameSelected = true
                    }
                    R.id.rbByAlphabet -> {
                        prefUtils.putString("lastCategory", "by_alphabet")
                        isNameSelected = false
                    }
                }
            }
            when (prefUtils.getString("lastCategory", "by_name")) {
                "by_name" -> {
                    rbByName.isChecked = true
                }
                "by_alphabet" -> {
                    rbByAlphabet.isChecked = true
                }
            }
            edtSearchDrink.setText(lastSearchedText)
            imgSearch.performClick()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setFavDrinkStatus(index: Int) {
        if (index >= drinkAdapter.drinksList.size) {
            drinkAdapter.notifyDataSetChanged()
            return
        }
        favDrinksViewModel.isContainItem(drinkAdapter.drinksList[index]) {
            drinkAdapter.drinksList[index].isFav = it
            setFavDrinkStatus(index + 1)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchDrinkByName(word: String) {
        hideKeyboard()
        prefUtils.putString("lastSearched", word)
        if (isNameSelected)
            drinksViewModel.searchDrinkByName(word)
        else
            drinksViewModel.searchDrinkByAlphabet(word[0])
        lifecycleScope.launchWhenResumed {
            drinksViewModel.drinksData.collect {
                when (it) {
                    is DrinkState.Loading -> {
                        Log.e("chat*", "*Response: Loading")
                    }
                    is DrinkState.Success -> {
                        drinkAdapter.addAllData(it.drinkResponse.drinks.toMutableList())
                        if (drinkAdapter.itemCount > 0) {
                            setFavDrinkStatus(0)
                        }
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
package com.task.ui

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.task.utils.SharedPrefUtils
import com.task.viewmodel.DrinksViewModel
import com.task.viewmodel.FavDrinksViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

open class BaseFragment : Fragment() {
    protected val drinksViewModel by viewModel<DrinksViewModel>()
    protected val favDrinksViewModel by viewModel<FavDrinksViewModel>()
    protected val prefUtils by inject<SharedPrefUtils>()

    protected fun hideKeyboard() {
        try {
            val imm: InputMethodManager? =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
            imm?.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        } catch (ignored: java.lang.Exception) {
        }
    }
}
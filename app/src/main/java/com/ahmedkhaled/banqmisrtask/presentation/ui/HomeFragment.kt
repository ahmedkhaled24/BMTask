package com.ahmedkhaled.banqmisrtask.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahmedkhaled.banqmisrtask.R
import com.ahmedkhaled.banqmisrtask.data.model.custom.CustomCurrenciesModel
import com.ahmedkhaled.banqmisrtask.presentation.viewmodels.LatestCurrenciesViewModel
import com.ahmedkhaled.banqmisrtask.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "TAGHomeFragment"
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: LatestCurrenciesViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init viewModel to receiving the response
        viewModel.latestCurrenciesData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showToast("Loading")
                    Log.d(TAG, "onViewCreated: Loading")
                }

                is Resource.Success -> {
                    Log.d(TAG, "onViewCreated: Success")
                    showToast(it.data!!.rates[0].toString())
                }

                is Resource.Error -> {
                    showToast(it.message!!)
                }
            }
        }

    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}
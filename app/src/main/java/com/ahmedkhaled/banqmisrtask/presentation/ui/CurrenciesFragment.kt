package com.ahmedkhaled.banqmisrtask.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahmedkhaled.banqmisrtask.R
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesItems
import com.ahmedkhaled.banqmisrtask.databinding.FragmentCurrenciesBinding
import com.ahmedkhaled.banqmisrtask.presentation.adapters.SpinnerFromAdapter
import com.ahmedkhaled.banqmisrtask.presentation.adapters.SpinnerToAdapter
import com.ahmedkhaled.banqmisrtask.presentation.viewmodels.LatestCurrenciesViewModel
import com.ahmedkhaled.banqmisrtask.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

private const val TAG = "TAGCurrenciesFragment"
@AndroidEntryPoint
class CurrenciesFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentCurrenciesBinding
    private val viewModel: LatestCurrenciesViewModel by viewModels()
    private var valueEUR = 0.0
    private var valueFirstSelectedItem = 1.0
    private var valueSecondSelectedItem = 1.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCurrenciesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initResponseApi()
//        observeOnSelectedItemSpinnerFrom()
    }

    private fun observeOnSelectedItemSpinnerFrom() {
        viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
            binding.editTextFromCurrency.setText("1")
            binding.editTextToCurrency.hint = (item.rate.toString())
        }
    }

    private fun initResponseApi() {
        //init viewModel to receiving the response
        viewModel.latestCurrenciesData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}

                is Resource.Success -> {
                    Log.d(TAG, "onViewCreated: Success")
                    configSpinnerFrom(it.data!!.data)
                    configSpinnerTo(it.data.data)
                    valueEUR = it.data.data[1].rate
                }

                is Resource.Error -> {
                    showToast(it.message!!)
                }
            }
        }
    }

    private fun configSpinnerFrom(data: MutableList<CurrenciesItems>) {
        val adapter1 = SpinnerFromAdapter(requireContext(), data)
        binding.spinnerFromCurrency.adapter = adapter1
        binding.spinnerFromCurrency.onItemSelectedListener = this
    }

    private fun configSpinnerTo(data: MutableList<CurrenciesItems>) {
//        if (data.size >= 2) {
//            val temp = data[0]
//            data[0] = data[1]
//            data[1] = temp
//        }

        val adapter2 = SpinnerToAdapter(requireContext(), data)
        binding.spinnerToCurrency.adapter = adapter2
        binding.spinnerToCurrency.onItemSelectedListener = this

        binding.spinnerFromCurrency.setSelection(1)
    }


    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        when(parent?.id){
            R.id.spinnerFromCurrency -> {
                val item: CurrenciesItems = parent.selectedItem as CurrenciesItems
                valueFirstSelectedItem = item.rate
                binding.editTextFromCurrency.setText("1")
                binding.editTextToCurrency.hint = oneDigit((valueSecondSelectedItem/item.rate))


                Log.d(TAG, "onItemSelected1 valueSecondSelectedItem: $valueSecondSelectedItem")
                Log.d(TAG, "onItemSelected1 valueEUR: ${item.rate}")
                Log.d(TAG, "onItemSelected1 editTextFromCurrency: ${binding.editTextFromCurrency.text}")
                Log.d(TAG, "onItemSelected1 total: ${oneDigit((valueSecondSelectedItem/item.rate)*binding.editTextFromCurrency.text.toString().toDouble())}")

            }

            R.id.spinnerToCurrency -> {
                val item: CurrenciesItems = parent.selectedItem as CurrenciesItems
                valueSecondSelectedItem = item.rate
//                binding.editTextFromCurrency.setText("1")
                Log.d(TAG, "onItemSelected ----------------------")
                Log.d(TAG, "onItemSelected2 valueSecondSelectedItem: $valueSecondSelectedItem")
                Log.d(TAG, "onItemSelected2 valueEUR: $valueEUR")
                Log.d(TAG, "onItemSelected2 editTextFromCurrency: ${binding.editTextFromCurrency.text}")
                Log.d(TAG, "onItemSelected2 total: ${oneDigit((valueSecondSelectedItem/valueEUR)*binding.editTextFromCurrency.text.toString().toDouble())}")

                binding.editTextToCurrency.hint = oneDigit((valueSecondSelectedItem/valueFirstSelectedItem)*binding.editTextFromCurrency.text.toString().toDouble())

            }
        }

    }
    override fun onNothingSelected(p0: AdapterView<*>?) {}


    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun oneDigit(num: Double): String {
        val nf = NumberFormat.getCurrencyInstance(Locale.ENGLISH)
        val dd = nf as DecimalFormat
        dd.applyPattern("#.###")
        return dd.format(num)
    }
}
//"${String.format("%.2f", 1.0/item.RAW.USD.PRICE)} ${item.CoinInfo.FullName} equals"
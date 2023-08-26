package com.ahmedkhaled.banqmisrtask.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahmedkhaled.banqmisrtask.R
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesItems
import com.ahmedkhaled.banqmisrtask.databinding.FragmentCurrenciesBinding
import com.ahmedkhaled.banqmisrtask.presentation.adapters.SpinnerFromAdapter
import com.ahmedkhaled.banqmisrtask.presentation.adapters.SpinnerToAdapter
import com.ahmedkhaled.banqmisrtask.presentation.viewmodels.LatestCurrenciesViewModel
import com.ahmedkhaled.banqmisrtask.utils.NumberProcessing.oneDigit
import com.ahmedkhaled.banqmisrtask.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "TAGCurrenciesFragment"
@AndroidEntryPoint
class CurrenciesFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentCurrenciesBinding
    private val viewModel: LatestCurrenciesViewModel by viewModels()
    private var valueFirstSelectedItem = 1.0
    private var valueSecondSelectedItem = 1.0
    private var positionSpinnerItemOne: Int? = null
    private var positionSpinnerItemTwo: Int? = null
    private var nameFirstCurrency: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCurrenciesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initResponseApi()

        binding.replaceIconIv.setOnClickListener {
//            binding.replaceIconIv.rotation = 180f
            binding.replaceIconIv.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.rotate))

            replaceSpinners()
        }

        binding.btnDetails.setOnClickListener {
            findNavController().navigate(CurrenciesFragmentDirections.actionCurrenciesFragmentToHistoricalFragment(
                nameFirstCurrency, positionSpinnerItemOne!!, positionSpinnerItemTwo!!))
        }
    }

//    private fun observeOnSelectedItemSpinnerFrom() {
//        viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
//            binding.editTextFromCurrency.setText("1")
//            binding.editTextToCurrency.hint = (item.rate.toString())
//        }
//    }

    private fun initResponseApi() {
        viewModel.latestCurrenciesData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showProgressOrHide(true)
                }

                is Resource.Success -> {
                    showProgressOrHide(false)
                    configSpinnerFrom(it.data!!.data)
                    configSpinnerTo(it.data.data)
                    listenValues()
                }

                is Resource.Error -> {
                    Log.d(TAG, "initResponseApi: ${it.message}")
                    showProgressOrHide(false)
                    Toast.makeText(requireContext(), it.message!!, Toast.LENGTH_SHORT).show()
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
        val adapter2 = SpinnerToAdapter(requireContext(), data)
        binding.spinnerToCurrency.adapter = adapter2
        binding.spinnerToCurrency.onItemSelectedListener = this
        binding.spinnerFromCurrency.setSelection(1)
    }


    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        when(parent?.id){
            R.id.spinnerFromCurrency -> {
                positionSpinnerItemOne = position
                val item: CurrenciesItems = parent.selectedItem as CurrenciesItems
                nameFirstCurrency = item.name
                valueFirstSelectedItem = item.rate
                binding.editTextFromCurrency.setText("1")
                binding.editTextFromCurrency.hint = "1"
                binding.editTextToCurrency.setText(oneDigit((valueSecondSelectedItem/item.rate)))
                binding.editTextToCurrency.hint = oneDigit((valueSecondSelectedItem/item.rate))
            }

            R.id.spinnerToCurrency -> {
                positionSpinnerItemTwo = position
                val item: CurrenciesItems = parent.selectedItem as CurrenciesItems
                valueSecondSelectedItem = item.rate
                if (!binding.editTextFromCurrency.text.isNullOrEmpty()){
                    binding.editTextToCurrency.setText(oneDigit((valueSecondSelectedItem/valueFirstSelectedItem)*binding.editTextFromCurrency.text.toString().toDouble()))
                    binding.editTextToCurrency.hint = oneDigit((valueSecondSelectedItem/valueFirstSelectedItem)*binding.editTextFromCurrency.text.toString().toDouble())
                } else {
                    binding.editTextToCurrency.setText(oneDigit((valueSecondSelectedItem/valueFirstSelectedItem)))
                    binding.editTextToCurrency.hint = oneDigit((valueSecondSelectedItem/valueFirstSelectedItem))
                }
            }
        }

    }
    override fun onNothingSelected(p0: AdapterView<*>?) {}


    private fun listenValues() {
        val et1 = binding.editTextFromCurrency
        val et2 = binding.editTextToCurrency

        val onKeyListener = View.OnKeyListener { v, _, _ ->
            val s = (v as EditText).text.toString()
            if (v == et1) {
                if (s.isNotEmpty()) {
                    if (s[0].toString() == "0") {
                        et1.text.clear()
                        if (s[0].toString() == "." && s.length>1){
                            et2.setText(oneDigit(et1.text.toString().toDouble() * (valueSecondSelectedItem/valueFirstSelectedItem)))
                        }
                    } else {
                        et2.setText(oneDigit(et1.text.toString().toDouble() * (valueSecondSelectedItem/valueFirstSelectedItem)))
                    }
                } else {
                    et2.setText(oneDigit(valueSecondSelectedItem/valueFirstSelectedItem))
                }
            } else {
                if (s.isNotEmpty()) {
                    if (s[0].toString() == "0") {
                        et1.setText("1")
                        et2.text.clear()
                        if (s[0].toString() == "." && s.length>1){
                            et1.setText(oneDigit(et2.text.toString().toDouble()/(valueSecondSelectedItem/valueFirstSelectedItem)))
                        }
                    } else {
                        et1.setText(oneDigit(et2.text.toString().toDouble()/(valueSecondSelectedItem/valueFirstSelectedItem)))
                    }
                } else {
                    et1.setText("1")
                }
            }
            false
        }
        et1.setOnKeyListener(onKeyListener)
        et2.setOnKeyListener(onKeyListener)
    }

    private fun replaceSpinners(){
        binding.spinnerFromCurrency.setSelection(positionSpinnerItemTwo!!)
        binding.spinnerToCurrency.setSelection(positionSpinnerItemOne!!)
    }

    private fun showProgressOrHide(status: Boolean) {
        if (status)
            binding.progress.root.visibility = View.VISIBLE
        else
            binding.progress.root.visibility = View.GONE
    }

}
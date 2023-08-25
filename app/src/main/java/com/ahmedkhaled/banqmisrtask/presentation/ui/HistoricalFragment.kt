package com.ahmedkhaled.banqmisrtask.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmedkhaled.banqmisrtask.R
import com.ahmedkhaled.banqmisrtask.databinding.FragmentHistoricalBinding

class HistoricalFragment : Fragment() {

    private lateinit var binding: FragmentHistoricalBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHistoricalBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}
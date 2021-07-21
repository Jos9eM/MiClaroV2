package com.biorent.miclarov2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.biorent.miclarov2.R
import com.biorent.miclarov2.databinding.FragmentHomeBinding
import com.biorent.miclarov2.utils.data.Lines
import com.google.android.material.bottomsheet.BottomSheetBehavior

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var linesList : MutableList<Lines>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.percentage.observe(viewLifecycleOwner, {
        })

        val sheetBehavior = BottomSheetBehavior.from(binding.contentLayout);
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED;

        binding.tabs.addTab(binding.tabs.newTab().setIcon(R.drawable.icon_home))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(R.drawable.icon_monetization))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(R.drawable.icon_card))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(R.drawable.icon_help))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(R.drawable.icon_person))

        binding.arrowDown.setOnClickListener {
            if(sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED){
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
            else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }

        binding.chart.drawChart(20f)

        return binding.root
    }
}
package com.vass.coursevass.ui.home

import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vass.coursevass.R
import com.vass.coursevass.databinding.FragmentHomeBinding
import com.vass.coursevass.utils.adapter.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recycleTasklist()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun recycleTasklist(){
        homeViewModel.getTaskList()
        homeViewModel.taskRegister.observe(viewLifecycleOwner, { value ->
            Log.d("verificacion", "esto $value")
            val recyCle: RecyclerView = _binding!!.recyclehome
            val adapter = TaskAdapter(value)
            recyCle.layoutManager = LinearLayoutManager(context)
            recyCle.adapter = adapter

        })
    }
}
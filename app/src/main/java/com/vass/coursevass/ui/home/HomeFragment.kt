package com.vass.coursevass.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vass.coursevass.R
import com.vass.coursevass.databinding.FragmentHomeBinding
import com.vass.coursevass.network.service.db.TaskListDto
import com.vass.coursevass.utils.adapter.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), CellClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
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
            val adapter = TaskAdapter( requireContext(), value, this)
            recyCle.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyCle.adapter = adapter

        })
    }

    override fun onCellClickListener(data: TaskListDto) {
        val bundle = bundleOf("id" to data.id, "name" to data.name, "description" to data.description, "assignedTo" to data.assignedTo,"status" to data.status)
        findNavController().navigate(R.id.nav_detailshow, bundle)
    }
}
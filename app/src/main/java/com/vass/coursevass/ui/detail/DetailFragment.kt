package com.vass.coursevass.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vass.coursevass.databinding.DetailFragmentBinding


class DetailFragment : Fragment() {
    private lateinit var detailhowViewModel: DetailViewModel
    private var _binding: DetailFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailhowViewModel =
            ViewModelProvider(this)[DetailViewModel::class.java]

        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        info()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun info(){
        val local = arguments?.getString("name")
        val local1 = arguments?.getString("description")
        val local2 = arguments?.getString("status")
        val local3 = arguments?.getString("assignedTo")
        binding.itemTitleOk.text = local
        binding.itemDescripOk.text = local1
        binding.statuDetail.text= local2
        binding.userDetail.text = local3
    }
}
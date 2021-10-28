package com.vass.coursevass.ui.slideshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vass.coursevass.R
import com.vass.coursevass.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        val local = arguments?.getString("name")
        val local1 = arguments?.getString("description")
        val local2 = arguments?.getString("status")
        val local3 = arguments?.getString("assignedTo")
        Log.d("verificacion", "esto ${local}")
        Log.d("verificacion", "esto ${local1}")
        Log.d("verificacion", "esto ${local2}")
        Log.d("verificacion", "esto ${local3}")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
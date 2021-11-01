package com.vass.coursevass.ui.taksform

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vass.coursevass.LoginActivity
import com.vass.coursevass.databinding.FragmentTaksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaksFragment: Fragment() {

    private lateinit var taksViewModel: TaksViewModel
    private var _binding: FragmentTaksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taksViewModel =
            ViewModelProvider(this)[TaksViewModel::class.java]

        _binding = FragmentTaksBinding.inflate(inflater, container, false)
        valid()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun valid(){
        taksViewModel.auth.observe(viewLifecycleOwner,{ status ->
            when (status) {
                false -> {
                    onLogout()
                }
                true -> {
                    Log.d("verificacion", "esto")
                }
            }
        })
    }
    private fun onLogout(): Boolean {
        val intent = Intent(activity, LoginActivity::class.java).apply {
        }
        startActivity(intent)
        activity?.finish();
        return true
    }
}
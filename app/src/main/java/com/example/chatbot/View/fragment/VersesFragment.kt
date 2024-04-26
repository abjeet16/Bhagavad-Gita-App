package com.example.chatbot.View.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.NetworkManger
import com.example.shreebhagavatgita.databinding.FragmentVersesBinding

class VersesFragment : Fragment() {

    private lateinit var binding:FragmentVersesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVersesBinding.inflate(layoutInflater)
        binding.shimmerLayout.visibility = View.VISIBLE

        checkInternet()
        return binding.root
    }
    private fun checkInternet(){
        val networkManger = NetworkManger(requireContext())
        networkManger.observe(viewLifecycleOwner){
            if (it == true){
                binding.RecyclerView.visibility = View.VISIBLE
                binding.NoInternetCardView.visibility = View.GONE
            }else{
                binding.shimmerLayout.visibility = View.GONE
                binding.RecyclerView.visibility = View.GONE
                binding.NoInternetCardView.visibility = View.VISIBLE
            }
        }
    }
}
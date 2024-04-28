package com.example.Bhagavad_gita_app.View.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shreebhagavatgita.R
import com.example.shreebhagavatgita.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding:FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSettingsBinding.inflate(layoutInflater)

        binding.SavedChapters.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_savedChapterFragment)
        }
        binding.SavedVerse.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_savedVersesFragment)
        }
        return binding.root
    }

}
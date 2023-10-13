package com.mcwindy.ddrhelper.ui.about

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mcwindy.ddrhelper.R
import com.mcwindy.ddrhelper.databinding.FragmentAboutBinding
import com.mcwindy.ddrhelper.ui.clan.ClanViewModel

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageGroup.setColorFilter(0xFF000000.toInt(), PorterDuff.Mode.LIGHTEN)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.mcwindy.ddrhelper.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.mcwindy.ddrhelper.databinding.FragmentSettingBinding
import com.mcwindy.ddrhelper.store.SharedPreferencesUtils

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val settingViewModel: SettingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.nameInput.setText(SharedPreferencesUtils.playerName)
        binding.clanInput.setText(SharedPreferencesUtils.playerClan)

        val submitButton = binding.submitButton
        submitButton.setOnClickListener { changeSetting() }
        return root
    }

    private fun changeSetting() {
        val newName = binding.nameInput.text?.toString()
        if (newName in settingViewModel.blackList) return
        if (!newName.isNullOrBlank()) {
            SharedPreferencesUtils.playerName = newName
        }
        val newClan = binding.clanInput.text?.toString()
        if (!newClan.isNullOrBlank()) {
            SharedPreferencesUtils.playerClan = newClan
        }
        val snackbar = Snackbar.make(
            requireView(), "Submit succeeded.", Snackbar.LENGTH_SHORT
        ).setAction("Action", null)
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
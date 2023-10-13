package com.mcwindy.ddrhelper.ui.gores

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mcwindy.ddrhelper.databinding.FragmentGoresBinding
import com.mcwindy.ddrhelper.store.SharedPreferencesUtils

class GoresFragment : Fragment() {
    companion object {
        private const val TAG = "GoresFragment"
    }

    private var _binding: FragmentGoresBinding? = null
    private val binding get() = _binding!!

    private val goresViewModel: GoresViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoresBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ret = goresViewModel.getGoresData(SharedPreferencesUtils.playerName)
        Log.w(TAG, ret.toString())
    }
}

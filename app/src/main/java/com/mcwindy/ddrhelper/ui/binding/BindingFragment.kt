package com.mcwindy.ddrhelper.ui.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mcwindy.ddrhelper.databinding.FragmentBindingBinding

class BindingFragment : Fragment() {

    private var _binding: FragmentBindingBinding? = null
    private val binding get() = _binding!!

    private val bindingViewModel: BindingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBindingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerViewBinding
        recyclerView.adapter = ExpandableRecyclerViewAdapter(bindingViewModel.bindingData)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}

package com.mcwindy.ddrhelper.ui.server.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcwindy.ddrhelper.databinding.FilterDragSheetBinding

class MyBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FilterDragSheetBinding? = null
    private val binding get() = _binding!!

    private val filterViewModel: FilterViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FilterDragSheetBinding.inflate(inflater, container, false)
        val root = binding.root
        initView()
        return root
    }

    private fun initView() {
        // 复选
        val lvMultiple = binding.lvMultiple
        val ctvAdapter = MultipleAdapter(
            requireContext(),
            filterViewModel.filterOptions,
            filterViewModel.filterList,
            viewLifecycleOwner
        )
        lvMultiple.setAdapter(ctvAdapter)
        // 设置Item间距
        lvMultiple.setDividerHeight(0)


        // 单选
        val lvSingle1 = binding.lvSingle1
        val single1Adapter = SingleAdapter(
            requireContext(),
            filterViewModel.sortOptions,
            filterViewModel.sortedBy,
            viewLifecycleOwner
        )
        lvSingle1.setAdapter(single1Adapter)
        // 设置Item间距
        lvSingle1.setDividerHeight(0)

        // 单选
        val lvSingle2 = binding.lvSingle2
        val single2Adapter = SingleAdapter(
            requireContext(),
            filterViewModel.classifyOptions,
            filterViewModel.classifyBy,
            viewLifecycleOwner
        )
        lvSingle2.setAdapter(single2Adapter)
        // 设置Item间距
        lvSingle2.setDividerHeight(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        filterViewModel.isViewFinished.value = true
    }
}

package com.mcwindy.ddrhelper.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mcwindy.ddrhelper.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mapViewModel =
            ViewModelProvider(this)[MapViewModel::class.java]

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listView = binding.expandableListView

        val departments = listOf("資訊管理系", "財務金融系", "會計系")

        val classes = listOf(
            listOf("一年甲班", "二年甲班", "三年甲班", "四年甲班"),
            listOf("一年甲班", "二年甲班", "三年甲班", "四年甲班"),
            listOf(
                "一年甲班", "一年乙班", "二年甲班", "二年乙班",
                "三年甲班", "三年乙班", "四年甲班", "四年乙班"
            )
        )

        val adapter = ExpandableListViewAdapter(requireContext(), departments, classes)
        listView.setAdapter(adapter)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

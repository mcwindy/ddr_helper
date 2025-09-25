package com.mcwindy.ddrhelper.ui.rank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.mcwindy.ddrhelper.R
import com.mcwindy.ddrhelper.databinding.FragmentRankBinding
import com.mcwindy.ddrhelper.model.DdnetRankData
import com.mcwindy.ddrhelper.model.Resource
import com.mcwindy.ddrhelper.model.ResourceAdapter
import com.mcwindy.ddrhelper.store.FileStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class RankFragment : Fragment() {

    private var _binding: FragmentRankBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val rankViewModel: RankViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        viewPager.adapter = MyTabAdapter(
            requireActivity(), tabLayout.tabCount
        )
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        rankViewModel.getRankData(requireActivity())

        val moshi = Moshi.Builder().add(ResourceAdapter()).build()
        // val adapter = moshi.adapter(DdnetRankData::class.java)
        val type = Types.newParameterizedType(Resource::class.java, DdnetRankData::class.java)
        val adapter = moshi.adapter<Resource<DdnetRankData>>(type)
        rankViewModel.rankData = FileStore(requireContext(), adapter, "rankData")
        return root
    }

    class PointsFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_rank_points, container, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
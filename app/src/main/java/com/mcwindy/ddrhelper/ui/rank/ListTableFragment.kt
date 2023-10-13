package com.mcwindy.ddrhelper.ui.rank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mcwindy.ddrhelper.databinding.FragmentRankPointsBinding
import com.mcwindy.ddrhelper.model.DdnetRankData
import com.mcwindy.ddrhelper.model.Resource
import com.mcwindy.ddrhelper.model.TablerowRankData


class ListTableFragment(private val pageNum: Int) : Fragment() {
    private var _binding: FragmentRankPointsBinding? = null
    private val rankViewModel: RankViewModel by activityViewModels()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: ListRankAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankPointsBinding.inflate(inflater, container, false)

        val recyclerView = binding.scoreTable
        adapter = ListRankAdapter(emptyList(), requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context, LinearLayoutManager(requireContext()).orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rankViewModel.rankData.value?.let {
            if (it !is Resource.Success) return@let
            adapter.updateData(getListFromData(it.data!!))
        }
        rankViewModel.rankData.observe(viewLifecycleOwner) {
            if (it !is Resource.Success) return@observe
            adapter.updateData(getListFromData(it.data!!))
        }
    }

    private fun getListFromData(data: DdnetRankData): List<TablerowRankData> {
        return when (pageNum) {
            0 -> {
                data.point
            }

            1 -> {
                data.rank
            }

            2 -> {
                data.teamRank
            }

            else -> {
                emptyList()
            }
        }.take(250)
    }
}

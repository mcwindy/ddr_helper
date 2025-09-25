package com.mcwindy.ddrhelper.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.mcwindy.ddrhelper.MainActivity
import com.mcwindy.ddrhelper.MainActivity.Companion.hideKeyboard
import com.mcwindy.ddrhelper.R
import com.mcwindy.ddrhelper.databinding.FragmentHomeBinding
import com.mcwindy.ddrhelper.model.DdnetPlayerData
import com.mcwindy.ddrhelper.model.Resource
import com.mcwindy.ddrhelper.overview.OverviewViewModel
import com.mcwindy.ddrhelper.store.DdnetPlayerDataCacheObject
import com.mcwindy.ddrhelper.store.SharedPreferencesUtils
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment() {
    companion object {
        private const val TAG = "HomeFragment"
    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: OverviewViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var textPlayerProgresses: Array<TextView>
    private lateinit var progressBars: Array<ProgressBar>
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var linearLayout: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        textPlayerProgresses = arrayOf(
            binding.textPlayerProgress0,
            binding.textPlayerProgress1,
            binding.textPlayerProgress2,
            binding.textPlayerProgress3,
            binding.textPlayerProgress4,
            binding.textPlayerProgress5,
            binding.textPlayerProgress6,
            binding.textPlayerProgress7,
            binding.textPlayerProgress8,
            binding.textPlayerProgress9,
            binding.textPlayerProgress10,
            binding.textPlayerProgress11
        )
        progressBars = arrayOf(
            binding.progressBar0,
            binding.progressBar1,
            binding.progressBar2,
            binding.progressBar3,
            binding.progressBar4,
            binding.progressBar5,
            binding.progressBar6,
            binding.progressBar7,
            binding.progressBar8,
            binding.progressBar9,
            binding.progressBar10,
            binding.progressBar11
        )
        loadingProgressBar = binding.loadingProgressBar
        linearLayout = binding.linearLayout

        binding.textPlayerName.text = if (DdnetPlayerDataCacheObject.playerData != null) {
            DdnetPlayerDataCacheObject.playerData!!.player
        } else {
            SharedPreferencesUtils.playerName
        }
        binding.textPlayerClan.text = SharedPreferencesUtils.playerClan

        binding.switch0.setOnCheckedChangeListener { view, isChecked ->
            view.text = if (isChecked) "Points" else "Map"
            if (viewModel.playerData.value == null) return@setOnCheckedChangeListener
            if (isChecked) showProgressWithPoints(viewModel.playerData.value!!.data!!)
            else showProgressWithMaps(viewModel.playerData.value!!.data!!)
        }

        if (arguments != null) {
            val playerName = requireArguments().getString("playerName")
            viewModel.playerData.value = Resource.Loading()
            if (playerName != null) {
                showLoading()
                viewModel.getPlayerData(playerName)
            }
        }
        // viewModel.playerData.observe(viewLifecycleOwner) {
        //    Log.w(TAG, "gg!$it")
        //    if (it != null) {
        //        Log.w(
        //            TAG,
        //            "${it.points.rank.toString()}. ${it.points.points.toString()}/${it.points.total.toString()}${
        //                getString(
        //                    R.string.point
        //                )
        //            }"
        //        )
        //        binding.textPlayerName.text = it.player
        //        binding.textPlayerPoints.text =
        //            "${it.points.rank.toString()}. ${it.points.points.toString()}/${it.points.total.toString()}${
        //                getString(
        //                    R.string.point
        //                )
        //            }"
        //        binding.textPlayerRanks.text =
        //            "${it.rank.rank.toString()}. ${it.rank.points.toString()}${getString(R.string.point)}" // TODO handle null
        //    }
        //    homeViewModel._text.value = homeViewModel._text.value + "!"
        // }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.action_search, menu)
                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                setupSearchView(menu, searchView)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_search -> {
                        activity!!.onSearchRequested()
                        // clearCompletedTasks()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        initializePlayerData()
    }

    private fun setupSearchView(menu: Menu, searchView: SearchView) {
        searchView.queryHint = getString(R.string.search_player_id)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    showLoading()
                    viewModel.getPlayerData(query)
                }
                hideKeyboard(activity as MainActivity)
                return true
            }
        })


        val searchItem = menu.findItem(R.id.action_search)
        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                // 隐藏标题
                supportActionBar?.setDisplayShowTitleEnabled(false)
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                supportActionBar?.setDisplayShowTitleEnabled(true)
                return true
            }
        })
    }

    private fun initializePlayerData() {
        viewModel.playerData.observe(viewLifecycleOwner) {
            if (it !is Resource.Success) return@observe
            hideLoading()
            showHeaderData(it.data!!)
            if (binding.switch0.isChecked) showProgressWithPoints(it.data)
            else showProgressWithMaps(it.data)

            val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
            binding.textLastUpdate.text = formatter.format(it.data.updateTime)
        }
    }

    private fun showLoading() {
        loadingProgressBar.isVisible = true
        linearLayout.isVisible = false
    }

    private fun hideLoading() {
        loadingProgressBar.isVisible = false
        linearLayout.isVisible = true
    }

    private fun showHeaderData(data: DdnetPlayerData) {
        data.also {
            binding.textPlayerName.text = it.player
            binding.textPlayerPoints0.text = getString(R.string.player_points0, it.points.total)
            // "${getString(R.string.total)}${it.points.total}${getString(R.string.point)}"
            binding.textPlayerPoints1.text = getString(R.string.player_points, it.points.points)
            // "${it.points.points}${getString(R.string.point)}"
            binding.textPlayerPoints2.text = getString(R.string.player_rank, it.points.rank)
            // "${it.points.rank}${getString(R.string.rank)}"
            binding.textPlayerRankPoints1.text =
                getString(R.string.player_points, it.rank.points ?: 0)
            // "${it.rank.points.toString()}${getString(R.string.point)}"
            binding.textPlayerRankPoints2.text =
                if (it.rank.points == null) getString(R.string.no_rank) else getString(
                    R.string.player_rank, it.rank.rank
                )
            binding.textPlayerTeamRankPoints1.text =
                getString(R.string.player_points, it.teamRank.points ?: 0)
            binding.textPlayerTeamRankPoints2.text =
                if (it.teamRank.points == null) getString(R.string.no_rank) else getString(
                    R.string.player_rank, it.teamRank.rank
                )
        }
    }

    private fun showProgressWithPoints(data: DdnetPlayerData) {
        data.also {
            for (i in 0..11) {
                val mapType = viewModel.mapTypes[i]
                textPlayerProgresses[i].text = getString(
                    R.string.finish_percent,
                    it.types[mapType]!!.points.points,
                    it.types[mapType]!!.points.total,
                    100 * it.types[mapType]!!.points.points / it.types[mapType]!!.points.total,
                )
                progressBars[i].progress =
                    100 * it.types[mapType]!!.points.points / it.types[mapType]!!.points.total
            }
        }
    }

    private fun showProgressWithMaps(data: DdnetPlayerData) {
        data.also {
            for (i in 0..11) {
                var finished = 0
                val mapType = viewModel.mapTypes[i]
                for ((_, finish) in it.types[mapType]!!.maps) if (finish.rank != null) finished++
                textPlayerProgresses[i].text = getString(
                    R.string.finish_percent,
                    finished,
                    it.types[mapType]!!.maps.size,
                    100 * finished / it.types[mapType]!!.maps.size,
                )
                progressBars[i].progress = 100 * finished / it.types[mapType]!!.maps.size
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.playerData.removeObservers(viewLifecycleOwner)
        _binding = null
    }

}

package com.mcwindy.ddrhelper.ui.server

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mcwindy.ddrhelper.R
import com.mcwindy.ddrhelper.databinding.FragmentServerBinding
import com.mcwindy.ddrhelper.model.ClassifiedServerList
import com.mcwindy.ddrhelper.model.Resource
import com.mcwindy.ddrhelper.model.ServerData
import com.mcwindy.ddrhelper.ui.server.filter.ClassifyOption
import com.mcwindy.ddrhelper.ui.server.filter.FilterOption
import com.mcwindy.ddrhelper.ui.server.filter.FilterViewModel
import com.mcwindy.ddrhelper.ui.server.filter.MyBottomSheetDialogFragment

class ServerFragment : Fragment() {

    private var _binding: FragmentServerBinding? = null
    private val binding get() = _binding!!

    private val serverViewModel: ServerViewModel by viewModels()
    private val filterViewModel: FilterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serverViewModel.getServerData()
        val recyclerView = binding.recyclerViewServer
        val adapter = ExpandableRecyclerViewAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        serverViewModel.serverData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    // Update UI with data
                    val versionDataList = getServerList(it.data!!)
                    // Update the adapter's data
                    adapter.updateData(versionDataList)
                }

                is Resource.Loading -> {
                    // Show loading indicator
                }

                is Resource.Error -> {
                    // Show error message
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        filterViewModel.isViewFinished.observe(viewLifecycleOwner) { isFinished ->
            if (isFinished && serverViewModel.serverData.value is Resource.Success) {
                val versionDataList = getServerList(serverViewModel.serverData.value!!.data!!)
                adapter.updateData(versionDataList)
                filterViewModel.isViewFinished.value = false // Reset the value
            }
        }

        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.action_filter_server, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_filter_server -> {
                        val bottomSheetDialogFragment = MyBottomSheetDialogFragment()
                        bottomSheetDialogFragment.show(
                            parentFragmentManager, bottomSheetDialogFragment.tag
                        )
                        // clearCompletedTasks()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    // TODO filter classify sort
    private fun getServerList(list: List<ServerData>): List<ClassifiedServerList<ServerData>> {
        var filteredList = list
        for (filterItem in FilterOption.entries) {
            if (filterViewModel.filterList.value?.contains(filterItem) == true) {
                filteredList = filterItem.filter(filteredList)
            }
        }
        val versionDataList = filteredList.groupBy { server ->
            when (filterViewModel.classifyBy.value) {
                ClassifyOption.BY_VERSION -> {
                    server.version
                }
                ClassifyOption.BY_IP -> {
                    server.ip
                }
                else -> {
                    "All"
                }
            }
        }.map { (classifier, servers) ->
            ClassifiedServerList(classifier, servers.sortedWith { server1, server2 ->
                filterViewModel.sortedBy.value?.compare(server1, server2) ?: 0
            })
        }
        return versionDataList
    }
}

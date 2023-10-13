package com.mcwindy.ddrhelper.ui.server.clients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mcwindy.ddrhelper.databinding.FragmentClientsBinding
import com.mcwindy.ddrhelper.model.ServerData
import java.text.SimpleDateFormat
import java.util.Locale

class ClientsFragment : Fragment() {
    private var _binding: FragmentClientsBinding? = null
    private val binding get() = _binding!!

    private lateinit var data: ServerData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = ServerData.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClientsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
        // Use 'clients' to populate your views here
        binding.clientServerName.text = data.name
        binding.clientServerIp.text = "${data.ip}:${data.port}"
        binding.clientServerPlayer.text = "${data.numClients}/${data.maxClients}"
        binding.clientServerMap.text = data.map
        binding.clientServerFirstSeen.text = dateFormat.format(data.firstSeen)
        val recyclerView = binding.recyclerViewClient
        val adapter = ListClientAdapter(data.clients, requireContext()) // TODO
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}

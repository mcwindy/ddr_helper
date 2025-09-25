package com.mcwindy.ddrhelper.ui.server

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mcwindy.ddrhelper.Flags
import com.mcwindy.ddrhelper.R
import com.mcwindy.ddrhelper.model.ClassifiedServerList
import com.mcwindy.ddrhelper.model.ServerData

class ExpandableRecyclerViewAdapter(
    private var versionDataList: List<ClassifiedServerList<ServerData>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val expandedVersionIndices = mutableSetOf<Int>()

    companion object {
        private const val TYPE_VERSION = 0
        private const val TYPE_SERVER = 1
    }

    override fun getItemViewType(position: Int): Int {
        var pos = 0
        for (i in versionDataList.indices) {
            if (pos == position) return TYPE_VERSION
            if (expandedVersionIndices.contains(i)) {
                pos += versionDataList[i].value.size + 1
                if (pos > position) return TYPE_SERVER
            } else {
                pos++
            }
        }
        throw IllegalArgumentException("Invalid position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_VERSION) {
            val view = inflater.inflate(R.layout.item_version, parent, false)
            VersionViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_server, parent, false)
            ServerViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var pos = 0
        for ((index, versionData) in versionDataList.withIndex()) {
            if (pos == position && holder is VersionViewHolder) {
                holder.bind(versionData.key, versionData.isOpened)
                holder.itemView.setOnClickListener {
                    if (expandedVersionIndices.contains(index)) {
                        expandedVersionIndices.remove(index)
                    } else {
                        expandedVersionIndices.add(index)
                    }
                    versionData.isOpened = !versionData.isOpened
                    // notifyItemChanged(position)
                    notifyDataSetChanged()
                }
                return
            }
            if (expandedVersionIndices.contains(index)) {
                pos += versionData.value.size + 1
                if (pos > position && holder is ServerViewHolder) {
                    holder.bind(versionData.value[pos - position - 1])
                    return
                }
            } else {
                pos++
            }
        }
    }

    override fun getItemCount(): Int {
        var count = versionDataList.size
        for (i in versionDataList.indices) {
            if (expandedVersionIndices.contains(i)) {
                count += versionDataList[i].value.size
            }
        }
        return count
    }

    fun updateData(data: List<ClassifiedServerList<ServerData>>) {
        versionDataList = data
        notifyDataSetChanged()
    }

    inner class VersionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val versionTitle: TextView = itemView.findViewById(R.id.version_title)
        // NOTE click listener is set in onBindViewHolder, if set here, it will be overridden

        init {
            // itemView.setOnTouchListener { v, event ->
            //     if (event.action == MotionEvent.ACTION_DOWN) {
            //         // 按下时做些操作
            //         Log.d(TAG, "Pressed down")
            //     }
            //     if (event.action == MotionEvent.ACTION_UP) {
            //         // 抬起时做些操作
            //         Log.d(TAG, "Released")
            //     }
            //     false
            // }
        }

        fun bind(version: String, isOpened: Boolean) {
            versionTitle.text = version
            if (isOpened) {
                versionTitle.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.stat_1_24px, 0, 0, 0
                )
            } else {
                versionTitle.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.stat_minus_1_24px, 0, 0, 0
                )
            }
        }
    }

    inner class ServerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val serverName: TextView = itemView.findViewById(R.id.server_name)
        private val serverIp: TextView = itemView.findViewById(R.id.server_ip)
        private val serverPlayer: TextView = itemView.findViewById(R.id.server_player)
        private val serverFlag: ImageView = itemView.findViewById(R.id.server_flag)
        private lateinit var server: ServerData
        // Add more views if you have more data to show

        init {
            itemView.setOnClickListener {
                itemView.findNavController().navigate(
                    R.id.action_serverFragment_to_clientsFragment, server.toBundle()
                )
            }
            itemView.setOnLongClickListener {
                val clipboard =
                    itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Server IP", serverIp.text)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(
                    itemView.context,
                    itemView.context.getString(R.string.ip_copied),
                    Toast.LENGTH_LONG
                ).show()
                true
            }
        }

        fun bind(server: ServerData) {
            this.server = server
            serverName.text = server.name
            serverIp.text = "${server.ip}:${server.port}"
            serverPlayer.text = "${server.numPlayers}/${server.maxPlayers}"
            serverFlag.setImageResource(Flags.getFlag("chn"))
        }
    }
}
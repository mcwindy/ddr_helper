package com.mcwindy.ddrhelper.ui.rank

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
import com.mcwindy.ddrhelper.R
import com.mcwindy.ddrhelper.model.TablerowRankData

class ListRankAdapter(private var list: List<TablerowRankData>, private val context: Context) :
    RecyclerView.Adapter<ListRankAdapter.ViewHolder>() {

    fun updateData(newList: List<TablerowRankData>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v: View = layoutInflater.inflate(R.layout.tablerow_rank, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.index.text = list[position].index.toString()
        holder.id.text = list[position].id
        holder.points.text = list[position].points.toString()
        holder.flag.setImageResource(list[position].flagRes)
        // notifyDataSetChanged()
        holder.itemView.setOnClickListener {
            // Turn to home fragment of playerName
            val action =
                RankFragmentDirections.actionRankFragmentToHomeFragment(holder.id.text.toString())
            // v.findNavController().navigate(action)
            if (holder.itemView.isAttachedToWindow) {
                holder.itemView.findNavController().navigate(action)
            }
        }
        holder.itemView.setOnLongClickListener {
            val clipboard =
                holder.itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("PlayerName", holder.id.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                holder.itemView.context,
                holder.itemView.context.getString(R.string.name_copied),
                Toast.LENGTH_LONG
            ).show()
            true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var index: TextView = itemView.findViewById<View>(R.id.tablerow_index) as TextView
        var id: TextView = itemView.findViewById<View>(R.id.tablerow_id) as TextView
        var points: TextView = itemView.findViewById<View>(R.id.tablerow_points) as TextView
        var flag: ImageView = itemView.findViewById<View>(R.id.tablerow_flag) as ImageView
    }
}

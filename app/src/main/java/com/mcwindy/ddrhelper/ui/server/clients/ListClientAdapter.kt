package com.mcwindy.ddrhelper.ui.server.clients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mcwindy.ddrhelper.R
import com.mcwindy.ddrhelper.model.ClientData
import java.text.SimpleDateFormat
import java.util.Locale

class ListClientAdapter(list: List<ClientData>, context: Context) :
    RecyclerView.Adapter<ListClientAdapter.ViewHolder>() {
    private var list: List<ClientData>
    private val context: Context

    init {
        this.list = list
        this.context = context
    }

    fun updateData(newList: List<ClientData>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v: View = layoutInflater.inflate(R.layout.item_client, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
        val data = list[position]
        // holder.skin.setImageResource(data.skinRes)
        holder.name.text = data.name
        holder.clan.text = data.clan
        holder.first.text = dateFormat.format(data.firstSeen)
        holder.type.text = if (data.isBot) context.getString(R.string.bot) else if (data.isDummy) context.getString(R.string.dummy) else ""
        holder.country.text = data.country.toString()

        // notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var skin: ImageView
        var name: TextView
        var clan: TextView
        var first: TextView
        var type: TextView
        var country: TextView

        init {
            skin = itemView.findViewById<View>(R.id.image_skin) as ImageView
            name = itemView.findViewById<View>(R.id.text_name) as TextView
            clan = itemView.findViewById<View>(R.id.text_clan) as TextView
            first = itemView.findViewById<View>(R.id.text_first) as TextView
            type = itemView.findViewById<View>(R.id.text_type) as TextView
            country = itemView.findViewById<View>(R.id.text_country) as TextView
        }
    }
}

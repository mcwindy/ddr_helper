package com.mcwindy.ddrhelper.ui.binding

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mcwindy.ddrhelper.R
import com.mcwindy.ddrhelper.model.ClassifiedServerList

class ExpandableRecyclerViewAdapter<T>(private val dataList: List<ClassifiedServerList<T>>) :
    RecyclerView.Adapter<ExpandableRecyclerViewAdapter<T>.ViewHolderX>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderX {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.expandable_view_holder_main_layout, parent, false)
        return ViewHolderX(view)
    }

    override fun onBindViewHolder(holder: ViewHolderX, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolderX(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define your views here
        private val title: TextView = itemView.findViewById(R.id.title)
        private val content: RecyclerView = itemView.findViewById(R.id.content)

        fun bind(data: ClassifiedServerList<T>) {
            title.text = data.key
            val adapter = ContentAdapter(data.value)
            content.adapter = adapter
            content.layoutManager = LinearLayoutManager(itemView.context)
            content.visibility = if (data.isOpened) View.VISIBLE else View.GONE

            itemView.setOnClickListener {
                // Toggle visibility of children
                data.isOpened = !data.isOpened
                if (data.isOpened) {
                    content.visibility = View.VISIBLE
                    title.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.stat_1_24px, 0, 0, 0
                    )
                } else {
                    content.visibility = View.GONE
                    title.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.stat_minus_1_24px, 0, 0, 0
                    )
                }
                notifyDataSetChanged()
            }
        }
    }

    inner class ContentAdapter(private val dataList: List<T>) :
        RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

        inner class ContentViewHolder(itemView: View, infoText: String) :
            RecyclerView.ViewHolder(itemView) {
            private val bindingName: TextView = itemView.findViewById(R.id.binding_name)
            private val bindingCommand: TextView = itemView.findViewById(R.id.binding_command)
            private val bindingExplanation: TextView =
                itemView.findViewById(R.id.binding_explanation)

            init {
                itemView.setOnLongClickListener {
                    val clipboard =
                        itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Server IP", bindingCommand.text)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(itemView.context, infoText, Toast.LENGTH_SHORT).show()
                    true
                }
            }

            fun bind(data: T) {
                if (data !is BindingData) return
                bindingName.text = data.name
                bindingCommand.text = data.command
                if (data.explanation.isNotEmpty()) {
                    bindingExplanation.text = data.explanation
                    bindingExplanation.visibility = View.VISIBLE
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.expandable_view_holder_content_layout, parent, false)
            val infoText = parent.context.getString(R.string.command_copied)
            return ContentViewHolder(view, infoText)
        }

        override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
            holder.bind(dataList[position])
        }

        override fun getItemCount(): Int {
            return dataList.size
        }
    }
}
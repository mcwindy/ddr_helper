package com.mcwindy.ddrhelper.ui.server.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckedTextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.mcwindy.ddrhelper.R

class SingleAdapter<T : Enum<T>>(
    private val context: Context,
    private val list: List<T>,
    private val liveData: MutableLiveData<T>,
    lifecycleOwner: LifecycleOwner
) : BaseAdapter() {

    init {
        if (liveData.value == null && list.isNotEmpty()) {
            liveData.value =
                list[0] // Set the first item as default checked only if it's not already set
        }

        liveData.observe(lifecycleOwner) {
            notifyDataSetChanged() // Update the list when selectedItem changes
        }
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_single, parent, false)
            holder = ViewHolder(view.findViewById(R.id.checked_text_view))
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val item = getItem(position) as T
        if (item is SortOption) {
            holder.checkedTextView.text = context.getString(item.stringResId)
        } else if (item is ClassifyOption) {
            holder.checkedTextView.text = context.getString(item.stringResId)
        }
        holder.checkedTextView.isChecked = item == liveData.value

        holder.checkedTextView.setOnClickListener {
            liveData.value = if (holder.checkedTextView.isChecked) null else item
        }

        return view
    }

    private class ViewHolder(val checkedTextView: CheckedTextView)
}
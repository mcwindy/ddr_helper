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

class MultipleAdapter<T : Enum<T>>(
    private val context: Context,
    private val options: List<T>,
    private val selectedOptions: MutableLiveData<List<T>>,
    lifecycleOwner: LifecycleOwner
) : BaseAdapter() {

    init {
        selectedOptions.observe(lifecycleOwner) {
            notifyDataSetChanged() // Update the list when filterList changes
        }
    }

    override fun getCount(): Int {
        return options.size
    }

    override fun getItem(position: Int): Any {
        return options[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_multiple, parent, false)
            holder = ViewHolder(view.findViewById(R.id.checked_text_view))
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val item = getItem(position) as T
        if (item is FilterOption) {
            holder.checkedTextView.text = context.getString(item.stringResId)
        } else {
            throw IllegalArgumentException("item is not FilterOption")
        }
        holder.checkedTextView.isChecked = selectedOptions.value?.contains(item) == true

        holder.checkedTextView.setOnClickListener {
            if (holder.checkedTextView.isChecked) {
                selectedOptions.value = selectedOptions.value?.minus(item)
            } else {
                selectedOptions.value = selectedOptions.value?.plus(item)
            }
            holder.checkedTextView.isChecked = !holder.checkedTextView.isChecked
        }

        return view
    }

    private class ViewHolder(val checkedTextView: CheckedTextView)
}

package com.mcwindy.ddrhelper.ui.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.mcwindy.ddrhelper.R

class ExpandableListViewAdapter(
    private val context: Context,
    private val groups: List<String>,
    private val children: List<List<String>>
) : BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return groups.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return children[groupPosition].size
    }

    override fun getGroup(groupPosition: Int): Any {
        return groups[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return children[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return (groupPosition * 100 + childPosition).toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?
    ): View {
        val view =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.item_group, parent, false)
        val textView = view.findViewById<TextView>(R.id.department_name)
        textView.text = groups[groupPosition]

        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.item_child, parent, false)
        val textView = view.findViewById<TextView>(R.id.class_name)
        textView.text = children[groupPosition][childPosition]

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

}

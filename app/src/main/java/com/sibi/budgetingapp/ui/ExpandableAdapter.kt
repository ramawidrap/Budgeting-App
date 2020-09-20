package com.sibi.budgetingapp.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.sibi.budgetingapp.R
import com.sibi.budgetingapp.model.BaseModel
import com.sibi.budgetingapp.model.Income
import com.sibi.budgetingapp.source.IncomeViewModel

class ExpandableAdapter<T : BaseModel>(
    private val context: Context,
    private val incomeViewModel: IncomeViewModel,
    private val hashMap: HashMap<String, ArrayList<T>>,
    private val headerList: List<String>
) : BaseExpandableListAdapter() {
    override fun getGroup(groupPosition: Int): Any {
        return groupPosition;
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var _convertView = convertView
        if (convertView == null) {
            _convertView = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.component_list_group, parent, false)
        }
        val header = _convertView!!.findViewById<TextView>(R.id.tv_header)
        header.text = headerList[groupPosition]

        return _convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return hashMap[headerList[groupPosition]]!!.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return hashMap[headerList[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val child = getChild(groupPosition, childPosition) as BaseModel
        if (convertView == null) {
            convertView = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.component_list, parent, false)
        }

        bindToView(convertView, child);


        return convertView!!
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return hashMap.size
    }

    fun bindToView(convertView: View?, child: BaseModel) {
        val name = convertView!!.findViewById<TextView>(R.id.tv_name)
        val tanggal = convertView.findViewById<TextView>(R.id.tv_date)
        val value = convertView.findViewById<TextView>(R.id.tv_value)
        val deleteIcon = convertView.findViewById<ImageView>(R.id.icon_delete)
        val editIcon = convertView.findViewById<ImageView>(R.id.icon_edit)
        name.text = child.title
        value.text = child.amount
        tanggal.text = child.date

        deleteIcon.setOnClickListener {
            incomeViewModel.deleteData(child as Income);
        }

        editIcon.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra("income", child as Income)
            context.startActivity(intent)
        }
    }
}
package com.sibi.budgetingapp.ui.expense

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import com.sibi.budgetingapp.R
import com.sibi.budgetingapp.model.Expense
import com.sibi.budgetingapp.source.viewmodel.MainActivityViewModel

class ExpenseExpandableAdapter(
    private val context: Context,
    private val rv_expense: ExpandableListView,
    private val incomeViewModel: MainActivityViewModel,
    var hashMap: HashMap<String, ArrayList<Expense>>,
    var headerList: List<String>
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
        val child = getChild(groupPosition, childPosition) as Expense
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

    fun bindToView(convertView: View?, child: Expense) {
        val name = convertView!!.findViewById<TextView>(R.id.tv_name)
        val tanggal = convertView.findViewById<TextView>(R.id.tv_date)
        val value = convertView.findViewById<TextView>(R.id.tv_value)
        val deleteIcon = convertView.findViewById<ImageView>(R.id.icon_delete)
        val editIcon = convertView.findViewById<ImageView>(R.id.icon_edit)
        name.text = child.title
        value.text = "Rp${child.amount}"
        tanggal.text = child.date

        deleteIcon.setOnClickListener {
            incomeViewModel.deleteDataExpense(child);
            for(x in 0 until groupCount) {
                if(rv_expense.isGroupExpanded(x)) {
                    rv_expense.expandGroup(x)
                }
                else {
                    rv_expense.collapseGroup(x)
                }
            }
        }





        editIcon.setOnClickListener {
            val intent = Intent(context, ExpenseEditActivity::class.java)
            intent.putExtra("expense", child)
            context.startActivity(intent)
        }
    }
}
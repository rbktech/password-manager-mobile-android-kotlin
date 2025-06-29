package ru.rbz.codemanager.ui.mainframe

import ru.rbz.codemanager.R

import ru.rbz.codemanager.CMainData
import ru.rbz.codemanager.TItemWebSite

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.widget.ImageButton
import android.widget.BaseExpandableListAdapter

import androidx.activity.result.ActivityResultLauncher
import ru.rbz.codemanager.TPosition

class CPageListAdapter(context: Context, data: CMainData, resultLauncher: ActivityResultLauncher<Intent>) : BaseExpandableListAdapter() {

    private val mData = data
    private val mContext = context
    private var mResultLauncher = resultLauncher

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return mData.mListPage[groupPosition].listWebSize[childPosition].webSize
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return mData.mListPage[groupPosition].listWebSize.size
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return 0
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, view: View?, parent: ViewGroup?): View? {

        var convertView = view

        if (convertView == null) {
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.page_list_child, parent, false)
        }

        convertView?.let { it ->

            val txtPageListChildWebSite = it.findViewById<View>(R.id.txtPageListChildWebSite) as TextView
            val btnPageListChildDeleteSite = it.findViewById<View>(R.id.btnPageListChildDeleteSite) as ImageButton
            btnPageListChildDeleteSite.isFocusable = false
            btnPageListChildDeleteSite.setOnClickListener {
                mData.mListPage[groupPosition].listWebSize.removeAt(childPosition)
                notifyDataSetChanged()
            }

            txtPageListChildWebSite.text = mData.mListPage[groupPosition].listWebSize[childPosition].webSize
        }

        return convertView
    }

    override fun getGroup(groupPosition: Int): Any {
        return mData.mListPage[groupPosition].name
    }

    override fun getGroupCount(): Int {
        return mData.mListPage.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return 0
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, view: View?, parent: ViewGroup?): View? {

        var convertView = view
        if (convertView == null) {
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.page_list_group, parent, false)
        }

        convertView?.let {

            val txtListGroupNamePage = convertView.findViewById<TextView>(R.id.txtListGroupNamePage)
            val btnListGroupChangePage = convertView.findViewById<ImageButton>(R.id.btnListGroupChangePage)
            btnListGroupChangePage.isFocusable = false
            btnListGroupChangePage.setOnClickListener {

                val intent: Intent = Intent(mContext, CPageActivity::class.java)
                intent.putExtra(mContext.getString(R.string.intent_result), CPageListActivity.CHANGE_ITEM_PAGE)
                intent.putExtra(mContext.getString(R.string.intent_item_position), TPosition(groupPosition, -1))
                intent.putExtra(mContext.getString(R.string.intent_item_page), mData.mListPage[groupPosition])
                mResultLauncher.launch(intent)
            }

            val btnListGroupAddWebSite = convertView.findViewById<ImageButton>(R.id.btnListGroupAddWebSite)
            btnListGroupAddWebSite.isFocusable = false
            btnListGroupAddWebSite.setOnClickListener {
                val intent: Intent = Intent(mContext, CSiteActivity::class.java)
                intent.putExtra(mContext.getString(R.string.intent_result), CPageListActivity.NEW_ITEM_WEBSITE)
                intent.putExtra(mContext.getString(R.string.intent_item_position), TPosition(groupPosition, -1))
                intent.putExtra(mContext.getString(R.string.intent_item_web_site), TItemWebSite())
                mResultLauncher.launch(intent)
            }

            val btnListGroupDeletePage = convertView.findViewById<ImageButton>(R.id.btnListGroupDeletePage)
            btnListGroupDeletePage.isFocusable = false
            btnListGroupDeletePage.setOnClickListener {
                mData.mListPage.removeAt(groupPosition)
                notifyDataSetChanged()
            }

            txtListGroupNamePage.text = mData.mListPage[groupPosition].name
        }

        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}
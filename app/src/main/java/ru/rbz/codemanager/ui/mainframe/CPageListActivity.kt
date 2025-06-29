package ru.rbz.codemanager.ui.mainframe

import ru.rbz.codemanager.CMainData
import ru.rbz.codemanager.CSecure
import ru.rbz.codemanager.TItemPage
import ru.rbz.codemanager.TItemWebSite
import ru.rbz.codemanager.TPosition

import android.os.Bundle
import android.view.View
import android.content.Intent
import android.content.Context
import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ru.rbz.codemanager.R

import java.io.Serializable

class CPageListActivity : AppCompatActivity() {

    companion object {

        const val CHANGE_ITEM_PAGE: Int = 10
        const val NEW_ITEM_PAGE: Int = 11
        const val CHANGE_ITEM_WEBSITE: Int = 12
        const val NEW_ITEM_WEBSITE: Int = 13
    }

    private var mSecure: CSecure? = null
    private var mData: CMainData = CMainData()
    private lateinit var mResultLauncher: ActivityResultLauncher<Intent>

    private var mAdapter: CPageListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_list_activity)

        mSecure = CSecure(resources, mData)

        mResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            result.data?.let { intent ->

                val position: TPosition =
                    intent.getSerializableExtra(resources.getString(R.string.intent_item_position)) as TPosition

                when (result.resultCode) {

                    CHANGE_ITEM_WEBSITE -> {

                        val obj: Serializable? = intent.getSerializableExtra(resources.getString(R.string.intent_item_web_site))
                        if (obj != null)
                            mData.mListPage[position.iPage].listWebSize[position.iWevSite] = obj as TItemWebSite
                    }

                    NEW_ITEM_WEBSITE -> {

                        val obj: Serializable? = intent.getSerializableExtra(resources.getString(R.string.intent_item_web_site))
                        if (obj != null)
                            mData.mListPage[position.iPage].listWebSize.add(obj as TItemWebSite)
                    }

                    CHANGE_ITEM_PAGE -> {

                        val obj: Serializable? = intent.getSerializableExtra(resources.getString(R.string.intent_item_page))
                        if (obj != null)
                            mData.mListPage[position.iPage] = obj as TItemPage
                    }

                    NEW_ITEM_PAGE -> {

                        val obj: Serializable? = intent.getSerializableExtra(resources.getString(R.string.intent_item_page))
                        if (obj != null)
                            mData.mListPage.add(obj as TItemPage)
                    }
                }

                mAdapter?.notifyDataSetChanged()
            }
        }

        val btnLoad = findViewById<Button>(R.id.btnPageListLoad)
        val btnUpload = findViewById<Button>(R.id.btnPageListUpload)
        val btnAddPage = findViewById<Button>(R.id.btnPageListAddPage)

        mAdapter = CPageListAdapter(baseContext, mData, mResultLauncher)
        val expListView = findViewById<View>(R.id.exListView) as ExpandableListView
        expListView.setAdapter(mAdapter)

        expListView.setOnItemLongClickListener { adapterView, _, i, _ ->

            val value: String = adapterView.adapter.getItem(i) as String

            val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(resources.getText(R.string.app_name), value)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(baseContext, "Copy to buffer", Toast.LENGTH_SHORT).show()

            true
        }

        expListView.setOnChildClickListener { expandableListView, _, i, i2, _ ->

            val itemWebSite: TItemWebSite = mData.mListPage[i].listWebSize[i2]
            val intent: Intent = Intent(baseContext, CSiteActivity::class.java)

            intent.putExtra(resources.getString(R.string.intent_result), CHANGE_ITEM_WEBSITE)
            intent.putExtra(resources.getString(R.string.intent_item_position), TPosition(i, i2))
            intent.putExtra(resources.getString(R.string.intent_item_web_site), itemWebSite)

            mResultLauncher.launch(intent)

            true
        }

        btnLoad.setOnClickListener {
            CPasswordEntryDialog(this, mSecure, mAdapter)
        }

        btnUpload.setOnClickListener {
            mSecure?.encryptFileAndUpload(baseContext)
        }

        btnAddPage.setOnClickListener {

            val intent: Intent = Intent(baseContext, CPageActivity::class.java)
            intent.putExtra(resources.getString(R.string.intent_result), CPageListActivity.NEW_ITEM_PAGE)
            intent.putExtra(resources.getString(R.string.intent_item_position), TPosition(-1, -1))
            intent.putExtra(resources.getString(R.string.intent_item_page), TItemPage())
            mResultLauncher.launch(intent)

            /*val alertDialog: AlertDialog = AlertDialog.Builder(this).create()

            val txtName: EditText = EditText(this)
            txtName.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            alertDialog.setView(txtName)

            alertDialog.setMessage("Enter name page")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { dialog, _ ->

                mData.mListPage.add(TItemPage(txtName.text.toString()))
                mAdapter?.notifyDataSetChanged()
                dialog.dismiss()
            }

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { dialog, _ ->
                dialog.dismiss()
            }

            alertDialog.show()*/
        }
    }
}
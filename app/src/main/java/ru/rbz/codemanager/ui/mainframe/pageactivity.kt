package ru.rbz.codemanager.ui.mainframe

import ru.rbz.codemanager.TPosition
import ru.rbz.codemanager.TItemPage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import androidx.appcompat.app.AppCompatActivity
import ru.rbz.codemanager.R

class CPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_item_activity)

        val position: TPosition = intent.getSerializableExtra(resources.getString(R.string.intent_item_position)) as TPosition
        val itemPage: TItemPage = intent.getSerializableExtra(resources.getString(R.string.intent_item_page)) as TItemPage
        val result: Int = intent.getIntExtra(resources.getString(R.string.intent_result), -1)

        val txtPageItemName = findViewById<EditText>(R.id.txtPageItemName)
        val btnPageItemSave = findViewById<Button>(R.id.btnPageItemSave)

        txtPageItemName.setText(itemPage.name)

        btnPageItemSave.setOnClickListener {

            itemPage.name = txtPageItemName.text.toString()

            Intent(baseContext, CSiteActivity::class.java).let {

                it.putExtra(resources.getString(R.string.intent_item_page), itemPage)
                it.putExtra(resources.getString(R.string.intent_item_position), position)
                setResult(result, it)
                finish()
            }
        }
    }
}
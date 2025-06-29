package ru.rbz.codemanager.ui.mainframe

import ru.rbz.codemanager.TPosition
import ru.rbz.codemanager.TItemWebSite

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.text.method.HideReturnsTransformationMethod
import ru.rbz.codemanager.R

class CSiteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.site_item_activity)

        val position: TPosition = intent.getSerializableExtra(resources.getString(R.string.intent_item_position)) as TPosition
        val itemWebSite: TItemWebSite = intent.getSerializableExtra(resources.getString(R.string.intent_item_web_site)) as TItemWebSite
        val result: Int = intent.getIntExtra(resources.getString(R.string.intent_result), -1)

        val txtWebSite = findViewById<EditText>(R.id.txtSiteItemWeb)
        val txtSiteLogin = findViewById<EditText>(R.id.txtSiteItemLogin)
        val txtSitePassword = findViewById<EditText>(R.id.txtSiteItemPassword)
        val btnSiteSave = findViewById<Button>(R.id.btnSiteItemSave)

        txtSitePassword.transformationMethod = HideReturnsTransformationMethod()

        txtWebSite.setText(itemWebSite.webSize)
        txtSiteLogin.setText(itemWebSite.login)
        txtSitePassword.setText(itemWebSite.password)

        btnSiteSave.setOnClickListener {

            itemWebSite.webSize = txtWebSite.text.toString()
            itemWebSite.login = txtSiteLogin.text.toString()
            itemWebSite.password = txtSitePassword.text.toString()

            Intent(baseContext, CSiteActivity::class.java).let {

                it.putExtra(resources.getString(R.string.intent_item_web_site), itemWebSite)
                it.putExtra(resources.getString(R.string.intent_item_position), position)
                setResult(result, it)
                finish()
            }
        }
    }
}
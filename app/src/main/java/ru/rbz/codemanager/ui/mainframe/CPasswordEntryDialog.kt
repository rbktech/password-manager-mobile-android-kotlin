package ru.rbz.codemanager.ui.mainframe

import ru.rbz.codemanager.CSecure

import android.view.Gravity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import android.annotation.SuppressLint

import androidx.appcompat.app.AlertDialog

@SuppressLint("RestrictedApi")
class CPasswordEntryDialog(context: Context, secure: CSecure?, adapter: CPageListAdapter?) : AlertDialog.Builder(context) {

    private var mAlertDialog: AlertDialog
    private var mPassword: CharArray = CharArray(CSecure.SIZE_PASSWORD)

    init {

        val txtPassword = EditText(context)
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

        txtPassword.layoutParams = lp
        txtPassword.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
        txtPassword.gravity = Gravity.CENTER

        txtPassword.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {

                if (cs.length == 4 && arg1 == 3 || cs.length == 4 && arg1 == 4)
                    println()
            }

            override fun beforeTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {

                if (cs.length == 3 && arg1 == 3 || cs.length == 5 && arg1 == 4)
                    println()
            }

            override fun afterTextChanged(arg0: Editable) {

                if (arg0.length == CSecure.SIZE_PASSWORD) {

                    arg0.forEachIndexed { index, ch ->
                        mPassword[index] = ch
                    }

                    val result = secure?.loadFileAndDecrypt(context, mPassword)
                    if (result == true)
                        finish("Password success", "Ok")
                    else
                        finish("Password wrong", "Cancel")
                }
            }
        })

        setTitle("Enter Password")
        setMessage("")
        setCancelable(false)
        setView(txtPassword, 40, 40, 40, 0)
        setNegativeButton("Cancel") { _, _ ->

            refresh(adapter)
        }

        mAlertDialog = create()
        mAlertDialog.show()
    }

    fun finish(message: String, label: String) {

        if (mAlertDialog.isShowing) {
            mAlertDialog.setMessage(message)
            mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).text = label
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(adapter: CPageListAdapter?) {
        adapter?.notifyDataSetChanged()
    }
}
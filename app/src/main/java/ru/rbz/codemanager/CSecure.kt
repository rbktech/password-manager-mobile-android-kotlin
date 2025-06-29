package ru.rbz.codemanager

import android.content.Context
import android.content.res.Resources

// import com.yandex.disk.client.Credentials

class CSecure(resources: Resources, data: CMainData) {

    companion object {

        const val SIZE_PASSWORD: Int = 4
        // const val NAME_FILE: String = "KeyByAllTheDoor.txt"
        const val NAME_FILE: String = "testall.txt"

        private const val SIZE_IV: Int = 16
        private const val SIZE_SALT: Int = 256
    }

    private val mData = data

    private var mPassword: CharArray = CharArray(SIZE_PASSWORD)
    //private var mCredentials: Credentials = Credentials("", "<token>")

    private var mIv: ByteArray = ByteArray(SIZE_IV)
    private var mSalt: ByteArray = ByteArray(SIZE_SALT)

    init {

        mIv = resources.openRawResource(R.raw.iv).readBytes()
        mSalt = resources.openRawResource(R.raw.salt).readBytes()
    }

    fun loadFileAndDecrypt(context: Context, password: CharArray): Boolean {

        // CDataExchange.CDownloadFileRetained.loadFile(context, mCredentials, "/$NAME_FILE")

        val inText: ByteArray = CFile.readFile(context)

        val outText: ByteArray? = CCipher.decrypt(password, inText, mIv, mSalt)
        if (outText != null) {

            mPassword = password

            mData.mListPage.forEach { it ->
                it.listWebSize.clear()
            }
            mData.mListPage.clear()

            CFile.parse(outText, mData)

            return true
        }
        return false
    }

    fun encryptFileAndUpload(context: Context) {

        val inData: ByteArray? = CFile.collect(mData)
        if (inData != null) {

            val outData: ByteArray? = CCipher.encrypt(mPassword, inData, mIv, mSalt)
            if (outData != null) {

                CFile.writeFile(context, outData)

                //CDataExchange.CUploadFileRetained.uploadFile(context, mCredentials, "/", "${context.filesDir}/$NAME_FILE")
            }
        }
    }
}
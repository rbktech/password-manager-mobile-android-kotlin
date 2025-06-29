package ru.rbz.codemanager

import android.content.Context

/*import com.yandex.disk.client.Credentials
import com.yandex.disk.client.ProgressListener
import com.yandex.disk.client.TransportClient
import com.yandex.disk.client.exceptions.CancelledDownloadException
import com.yandex.disk.client.exceptions.CancelledUploadingException
import com.yandex.disk.client.exceptions.WebdavException

import java.io.File
import java.io.IOException

class CDataExchange {

    object CDownloadFileRetained : ProgressListener {

        fun loadFile(context: Context, credentials: Credentials?, fullPath: String) {

            val thr = Thread {
                var client: TransportClient? = null
                try {
                    client = TransportClient.getInstance(context, credentials)
                    client.downloadFile(fullPath, File(context.filesDir, File(fullPath).name), this)
                } catch (ex: CancelledDownloadException) {
                    // cancelled by user
                } catch (exception: IOException) {
                    exception.printStackTrace()
                } catch (exception: WebdavException) {
                    exception.printStackTrace()
                } finally {
                    TransportClient.shutdown(client)
                }
            }

            thr.start()
            thr.join()
        }

        override fun updateProgress(loaded: Long, total: Long) {
            /** "Not yet implemented" */
        }

        override fun hasCancelled(): Boolean {
            return false
        }
    }

    object CUploadFileRetained : ProgressListener {

        fun uploadFile(context: Context?, credentials: Credentials?, serverPath: String?, localFile: String?) {
            val thr = Thread {
                var client: TransportClient? = null
                try {
                    client = TransportClient.getUploadInstance(context, credentials)
                    client.uploadFile(localFile, serverPath, this)
                } catch (ex: CancelledUploadingException) {
                    // cancelled by user
                } catch (exception: IOException) {
                    exception.printStackTrace()
                } catch (exception: WebdavException) {
                    exception.printStackTrace()
                } finally {
                    TransportClient.shutdown(client)
                }
            }

            thr.start()
            thr.join()
        }

        override fun updateProgress(loaded: Long, total: Long) {
            /** "Not yet implemented" */
        }

        override fun hasCancelled(): Boolean {
            return false
        }
    }
}*/
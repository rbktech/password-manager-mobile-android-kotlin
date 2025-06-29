package ru.rbz.codemanager

import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object CCipher {

    fun decrypt(password: CharArray, inText: ByteArray?, iv: ByteArray?, salt: ByteArray?): ByteArray? {

        var outText: ByteArray? = null

        try {

            val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")

            val ivSpec = IvParameterSpec(iv)
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            outText = cipher.doFinal(inText)

        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return outText
    }

    fun encrypt(password: CharArray, inText: ByteArray?, iv: ByteArray?, salt: ByteArray?): ByteArray? {

        var outText: ByteArray? = null

        try {

            val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")

            val ivSpec = IvParameterSpec(iv)
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            outText = cipher.doFinal(inText)

        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return outText
    }
}
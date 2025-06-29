package ru.rbz.codemanager

import android.content.Context

import org.w3c.dom.Node
import org.w3c.dom.Element
import org.w3c.dom.Document

import java.io.File
import java.io.ByteArrayOutputStream

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

object CFile {

    fun readFile(context: Context): ByteArray {

        val file = File(context.filesDir, CSecure.NAME_FILE)

        return file.inputStream().readBytes()
    }

    fun writeFile(context: Context, outData: ByteArray) {

        val file = File(context.filesDir, CSecure.NAME_FILE)

        file.writeBytes(outData)
    }


    fun parse(inData: ByteArray, outData: CMainData) {

        val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
        val builder: DocumentBuilder = factory.newDocumentBuilder()
        val doc: Document = builder.parse(inData.inputStream())

        val listPageNode = doc.documentElement.childNodes
        val sizeListPageNode = listPageNode.length

        for (i in 0 until sizeListPageNode) {
            val itemPageNode = listPageNode.item(i)
            if (itemPageNode.nodeType == Node.ELEMENT_NODE) {

                val elementPageNode = itemPageNode as Element
                val itemPageData = TItemPage("name")

                val listWebSiteNode = elementPageNode.childNodes
                for (j in 0 until listWebSiteNode.length) {

                    val itemWebSiteNode = listWebSiteNode.item(j)

                    if (itemWebSiteNode.nodeType == Node.ELEMENT_NODE) {

                        val elementWebSiteNode = itemWebSiteNode as Element
                        val itemWebSiteData = TItemWebSite()

                        itemWebSiteData.webSize = elementWebSiteNode.getAttribute("WebSite")
                        itemWebSiteData.login = elementWebSiteNode.getAttribute("Login")
                        itemWebSiteData.password = elementWebSiteNode.getAttribute("Password")

                        itemPageData.listWebSize.add(itemWebSiteData)
                    }

                    itemPageData.name = elementPageNode.getAttribute("NamePage")
                }

                outData.mListPage.add(itemPageData)
            }
        }
    }

    fun collect(inData: CMainData): ByteArray? {

        val outStream = ByteArrayOutputStream()

        val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
        val builder: DocumentBuilder = factory.newDocumentBuilder()
        val doc: Document = builder.newDocument()

        val root = doc.createElement("root")
        doc.appendChild(root)

        inData.mListPage.forEachIndexed { nPage, itemPage ->

            val page: Element = doc.createElement("page$nPage")
            page.setAttribute("NamePage", itemPage.name)
            root.appendChild(page)

            itemPage.listWebSize.forEachIndexed { nWebSite, itemWebSite ->

                val webSite: Element = doc.createElement("web$nWebSite")

                webSite.setAttribute("WebSite", itemWebSite.webSize)
                webSite.setAttribute("Login", itemWebSite.login)
                webSite.setAttribute("Password", itemWebSite.password)
                page.appendChild(webSite)
            }
        }

        val transformerFactory: TransformerFactory = TransformerFactory.newInstance()
        val transformer: Transformer = transformerFactory.newTransformer()
        val source: DOMSource = DOMSource(doc)
        val result: StreamResult = StreamResult(outStream)

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result)

        return outStream.toByteArray()
    }
}
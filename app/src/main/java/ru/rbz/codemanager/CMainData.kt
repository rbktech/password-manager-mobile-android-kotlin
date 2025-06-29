package ru.rbz.codemanager

import java.io.Serializable

data class TPosition(
    val iPage: Int,
    val iWevSite: Int
) : Serializable

data class TItemWebSite(
    var webSize: String = "",
    var login: String = "",
    var password: String = "",
) : Serializable

typealias ListWebSite = MutableList<TItemWebSite>

data class TItemPage(
    var name: String = "",
    var listWebSize: ListWebSite = mutableListOf()
) : Serializable

/*typealias ListPage = MutableLiveData<MutableList<TItemPage>>

data class CMainData(
    val mListPage: ListPage = MutableLiveData<MutableList<TItemPage>>()
) {
    init {
        mListPage.value = mutableListOf()
    }
}*/

typealias ListPage = MutableList<TItemPage>

data class CMainData(
    val mListPage: ListPage = mutableListOf()
)


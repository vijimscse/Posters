package com.diagnal.movies.data

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.diagnal.movies.utils.getDataFromAsset
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ContentItemDataSource : PageKeyedDataSource<Int, ContentItem> {
    //the size of a page that we want
    companion object {
        const val PAGE_SIZE = 50
        const val PAGE1 = "CONTENTLISTINGPAGE-PAGE1.json"

        const val PAGE2 = "CONTENTLISTINGPAGE-PAGE2.json"

        const val PAGE3 = "CONTENTLISTINGPAGE-PAGE3.json"
    }

    var filterText: String = ""
    lateinit var mContext: Context

    constructor(context: Context, s: String) : super() {
        filterText = s
        mContext = context
    }

    //we will start from the first page which is 1
    private val FIRST_PAGE = 0
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ContentItem>
    ) {
        GlobalScope.launch {
            val apiResponse =
                Gson().fromJson(getDataFromAsset(mContext, PAGE1), ApiResponse::class.java)
            apiResponse.page?.contentItems?.content?.let { content ->
                callback.onResult(content.filter {
                    it.name.startsWith(filterText, true)
                }, null, FIRST_PAGE + 1)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ContentItem>) {
        val adjacentKey = if (params.key > 1) params.key - 1 else null
        GlobalScope.launch {
            val response = Gson().fromJson(
                getDataFromAsset(mContext, getPage(params.key)),
                ApiResponse::class.java
            )
            response.page?.contentItems?.content?.let { content ->
                callback.onResult(content.filter {
                    it.name.startsWith(filterText, true)
                }, adjacentKey)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ContentItem>) {
        val adjacentKey = if (params.key < 2) params.key + 1 else null
        GlobalScope.launch {
            val apiResponse =
                Gson().fromJson(
                    getDataFromAsset(mContext, getPage(params.key)), ApiResponse::class.java
                )
            apiResponse.page?.contentItems?.content?.let { content ->
                callback.onResult(content.filter {
                    it.name.startsWith(filterText, true)
                }, adjacentKey)
            }
        }
    }

    private fun getPage(page: Int) = when (page) {
        0 -> PAGE1
        1 -> PAGE2
        else -> PAGE3
    }
}
package com.diagnal.movies.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.diagnal.movies.data.ContentItem
import com.diagnal.movies.data.ContentItemDataSource


class ItemDataSourceFactory(private val context: Context, private val s: String) : DataSource.Factory<Int, ContentItem>() {
    //creating the mutable live data
    internal val itemLiveDataSource =
        MutableLiveData<PageKeyedDataSource<Int, ContentItem>>()

    override fun create(): DataSource<Int, ContentItem> {
        //getting our data source object
        val itemDataSource = ContentItemDataSource(context, s)

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource)

        //returning the datasource
        return itemDataSource
    }
}
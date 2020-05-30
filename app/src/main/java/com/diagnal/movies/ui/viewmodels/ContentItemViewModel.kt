package com.diagnal.movies.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.diagnal.movies.data.ContentItem
import com.diagnal.movies.data.ContentItemDataSource
import com.diagnal.movies.data.ItemDataSourceFactory


class ContentItemViewModel(application: Application) : AndroidViewModel(application) {
    //creating livedata for PagedList  and PagedKeyedDataSource
    var itemPagedList: LiveData<PagedList<ContentItem>>? = null
    var liveDataSource: LiveData<PageKeyedDataSource<Int, ContentItem>>? = null
    var filterTextAll = MutableLiveData<String>()

    init {

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ContentItemDataSource.PAGE_SIZE).build()
        Log.d("TAG", "Init")
        itemPagedList = Transformations.switchMap(
            filterTextAll
        ) { input: String? ->
            Log.d("TAG", "Input $input")
            if (input == null || input == "") {
                Log.d("TAG", "Input $input")
                //check if the current value is empty load all data else search
                val itemDataSourceFactory =
                    ItemDataSourceFactory(application,"")
                liveDataSource = itemDataSourceFactory.itemLiveDataSource
                 LivePagedListBuilder(
                    itemDataSourceFactory,
                    pagedListConfig
                ).build()
            } else {
                val itemDataSourceFactory =
                    ItemDataSourceFactory(application, input)
                liveDataSource = itemDataSourceFactory.itemLiveDataSource
                Log.d("TAG", "Input $input")
                LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)
                    .build()
            }
        }
    }

    fun search(s: String) {
        filterTextAll.value = s
    }
}
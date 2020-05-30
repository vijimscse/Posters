package com.diagnal.movies.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.diagnal.movies.R
import com.diagnal.movies.data.ContentItem
import com.diagnal.movies.ui.adapter.DefaultItemDecorator
import com.diagnal.movies.ui.adapter.ItemAdapter
import com.diagnal.movies.ui.viewmodels.ContentItemViewModel
import kotlinx.android.synthetic.main.movie_list_fragment.*
import java.lang.Exception


class MovieListFragment : Fragment() {
    var mPageListener: IPageListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context !is IPageListener) throw Exception("Activity must implement ${IPageListener::class.java.simpleName}")
             else mPageListener = context as IPageListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setting up recyclerview
        movierecyclerview.layoutManager =
            GridLayoutManager(activity, resources.getInteger(R.integer.grid_span_count))
        movierecyclerview.setHasFixedSize(true)

        //     activity?.actionBar?.title = "Romantic"
        //getting our ItemViewModel
        val itemViewModel: ContentItemViewModel? =
            activity?.let { ViewModelProviders.of(it).get(ContentItemViewModel::class.java) }

        //creating the Adapter
        val adapter = activity?.let {
            ItemAdapter(
                it
            )
        }

        //observing the itemPagedList from view model
        activity?.let {
            itemViewModel?.itemPagedList?.observe(it,
                Observer<PagedList<ContentItem>> { items ->
                    mPageListener?.onPageLoaded("Romantic Comedy")
                    Log.d("TAG", "Items ${items.size}")
                    adapter?.submitList(items)
                })
        }

        //setting the adapter
        movierecyclerview.adapter = adapter
        movierecyclerview.addItemDecoration(
            DefaultItemDecorator(
                resources.getDimensionPixelSize(R.dimen.grid_item_spacing),
                resources.getDimensionPixelSize(R.dimen.grid_item_spacing),
                resources.getInteger(R.integer.grid_span_count)
            )
        )
    }

    companion object {
        fun newInstance() = MovieListFragment()
    }
}

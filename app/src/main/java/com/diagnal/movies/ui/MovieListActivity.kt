package com.diagnal.movies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.diagnal.movies.R
import com.diagnal.movies.ui.viewmodels.ContentItemViewModel
import kotlinx.android.synthetic.main.movie_list_activity.*

class MovieListActivity : AppCompatActivity(),
    IPageListener {
    private lateinit var contentItemViewModel: ContentItemViewModel
    var searchMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_list_activity)
        setSupportActionBar(toolbar)

        toolbar.setTitleTextColor(ContextCompat.getColor(this,
            R.color.white
        ))
        toolbar_title.visibility = View.VISIBLE

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieListFragment.newInstance())
                .commit()
        }

        contentItemViewModel =
            ViewModelProviders.of(this).get(ContentItemViewModel::class.java)
        contentItemViewModel.filterTextAll.value = ""

        search.setOnClickListener {
            searchMode = true
            search.visibility = View.GONE
            close.visibility = View.VISIBLE
            search_input.visibility = View.VISIBLE
            back.visibility = View.GONE
            toolbar_title.visibility = View.GONE
            search_input.requestFocus()
        }

        search_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! >= 3) {
                    contentItemViewModel.search(search_input.text.toString())
                } else {
                    contentItemViewModel.search("")
                }
            }
        })

        close.setOnClickListener {
            search_input.setText("")
        }

       back.setOnClickListener {
           onBackPressed()
       }
    }

    override fun onPageLoaded(pageName: String) {
        toolbar_title.text = pageName
    }

    override fun onBackPressed() {
        if (searchMode) {
            searchMode = false
            close.visibility = View.GONE
            search.visibility = View.VISIBLE
            search_input.visibility = View.GONE
            toolbar_title.visibility = View.VISIBLE
            back.visibility = View.VISIBLE
        } else {
            super.onBackPressed()
        }
    }
}

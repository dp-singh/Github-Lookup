package com.dpsingh.githublookup.ui.custom_views

import android.content.Context
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import com.dpsingh.githublookup.R
import com.dpsingh.githublookup.extensions.customMessage
import kotlinx.android.synthetic.main.view_search.view.*

class CustomSearchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_search, this, true)
    }

    fun showSearchBox(onClick: (search: String) -> Unit) {
        et_search.isEnabled = true
        btn_search.visibility = View.VISIBLE
        progress_view.visibility = View.INVISIBLE
        tv_error.visibility = View.GONE

        btn_search.setOnClickListener {
            onClick(et_search.text.toString())
        }
        et_search.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    onClick(et_search.text.toString())
                    return@setOnEditorActionListener true
                }
                else -> return@setOnEditorActionListener false
            }
        }
    }

    fun showProgress() {
        et_search.isEnabled = false
        btn_search.visibility = View.INVISIBLE
        progress_view.visibility = View.VISIBLE
        tv_error.visibility = View.GONE
    }

    fun showError(errorString: Throwable?) {
        et_search.isEnabled = true
        btn_search.visibility = View.VISIBLE
        progress_view.visibility = View.INVISIBLE

        tv_error.text = errorString?.customMessage("User")
        tv_error.visibility = View.VISIBLE

        Handler().postDelayed({
            tv_error.visibility = View.GONE
        }, 4000)
    }

}
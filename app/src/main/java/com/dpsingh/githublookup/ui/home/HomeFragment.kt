package com.dpsingh.githublookup.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dpsingh.githublookup.R
import com.dpsingh.githublookup.data.remote.response.Response
import com.dpsingh.githublookup.domain.model.User
import com.dpsingh.githublookup.extensions.whenNotNull
import com.dpsingh.githublookup.ui.home.adapter.GithubHistoryAdapter
import com.dpsingh.githublookup.ui.home.adapter.view_holders.HistoryViewHolder
import com.dpsingh.githublookup.utils.EspressoIdlingResource
import com.dpsingh.githublookup.utils.ViewState
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : DaggerFragment(), Observer<Response<User>> {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UserViewModel
    private var listener: OnFragmentInteractionListener? = null

    override fun onChanged(response: Response<User>?) {
        response.whenNotNull {
            when (it.status) {
                ViewState.SUCCESS -> {
                    //in this case data can't be null that why ignoring the nullability check in data
                    listener?.openDetailFragment(it.data!!)
                    viewModel.response.value = null
                    searchView.showSearchBox(this@HomeFragment::searchUser)
                    EspressoIdlingResource.decrement()
                }
                ViewState.LOADING -> {
                    searchView.showProgress()
                    EspressoIdlingResource.increment()
                }
                ViewState.ERROR -> {
                    searchView.showError(it.error)
                    EspressoIdlingResource.decrement();
                }
            }
        }
    }

    private fun searchUser(githubHandle: String) {
        viewModel.loadUser(githubHandle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
    }

    private lateinit var adapter: GithubHistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rv_recent_search.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_recent_search.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        adapter = GithubHistoryAdapter(listener = this::onHistoryItemClick)
        rv_recent_search.adapter = adapter

        searchView.showSearchBox(this@HomeFragment::searchUser)

        viewModel.response.observe(this, this)
        viewModel.listUser.observe(this, Observer {
            it.whenNotNull {
                adapter.users = it.toMutableList()
            }
        })

        viewModel.loadSearchHistory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is OnFragmentInteractionListener -> listener = context
            else -> throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun onHistoryItemClick(githubHandle: User, historyViewHolder: HistoryViewHolder) {
        listener?.openDetailFragment(githubHandle, historyViewHolder)
    }

    interface OnFragmentInteractionListener {
        fun openDetailFragment(githubHandle: User)
        fun openDetailFragment(githubHandle: User, historyViewHolder: HistoryViewHolder)
    }

    companion object {
        @JvmStatic
        val TAG = this::class.java.canonicalName

        @JvmStatic
        fun newInstance() = HomeFragment()
    }

}

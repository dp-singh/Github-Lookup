package com.dpsingh.githublookup.ui.repository_listing

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
import com.dpsingh.githublookup.domain.model.User
import com.dpsingh.githublookup.extensions.whenNotNull
import com.dpsingh.githublookup.ui.repository_listing.adapter.RepositoryAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_repository_list.*
import timber.log.Timber
import javax.inject.Inject


private const val ARG_GITHUB_HANDLE = "arg_github_handle"

class RepositoryListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RepositoryListViewModel
    private var githubHandle: User? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var adapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = RepositoryAdapter { viewModel.retry() }
        arguments.whenNotNull {
            githubHandle = it.getParcelable(ARG_GITHUB_HANDLE)
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(RepositoryListViewModel::class.java)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repository_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main_toolbar.setNavigationIcon(R.drawable.ic_arrow)
        main_toolbar.setNavigationOnClickListener { listener?.onBackPress() }

        rv_repos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv_repos.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv_repos.adapter = adapter

        githubHandle.whenNotNull { user ->
            userView.bind(user)
            viewModel.setUserName(user.login)
            viewModel.response().observe(this, Observer {
                Timber.e("Log"+it.toString())
                it?.let(adapter::setPagingState)
            })
        }
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

    interface OnFragmentInteractionListener {
        fun onBackPress()
    }

    companion object {
        @JvmStatic
        val TAG = this::class.java.canonicalName

        @JvmStatic
        fun newInstance(user: User) =
                RepositoryListFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_GITHUB_HANDLE, user)
                    }
                }
    }


}

package com.dpsingh.githublookup.ui

import android.os.Bundle
import com.dpsingh.githublookup.domain.model.User
import com.dpsingh.githublookup.extensions.whenNull
import com.dpsingh.githublookup.ui.repository_listing.RepositoryListFragment
import com.dpsingh.githublookup.ui.home.HomeFragment
import dagger.android.support.DaggerAppCompatActivity


class HomeActivity : DaggerAppCompatActivity(), HomeFragment.OnFragmentInteractionListener, RepositoryListFragment.OnFragmentInteractionListener {

    override fun onBackPress() {
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState.whenNull {
            supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, HomeFragment.newInstance(), HomeFragment.TAG)
                    .commit()
        }
    }

    override fun openDetailFragment(githubHandle: User) {
        supportFragmentManager.beginTransaction()
                .add(android.R.id.content, RepositoryListFragment.newInstance(githubHandle), RepositoryListFragment.TAG)
                .addToBackStack(null)
                .commit()
    }
}





package com.dpsingh.githublookup.ui

import android.os.Build
import android.os.Bundle
import android.support.transition.TransitionInflater
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.widget.ImageView
import com.dpsingh.githublookup.R
import com.dpsingh.githublookup.domain.model.User
import com.dpsingh.githublookup.extensions.whenNotNull
import com.dpsingh.githublookup.extensions.whenNull
import com.dpsingh.githublookup.ui.home.HomeFragment
import com.dpsingh.githublookup.ui.home.adapter.view_holders.HistoryViewHolder
import com.dpsingh.githublookup.ui.repository_listing.RepositoryListFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.item_search_history.view.*


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

    override fun openDetailFragment(githubHandle: User, historyViewHolder: HistoryViewHolder) {
//
//        // Check that the device is running lollipop
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val fragmentOne: Fragment = supportFragmentManager.findFragmentByTag(HomeFragment.TAG)
//            val fragmentTwo: Fragment = RepositoryListFragment.newInstance(githubHandle)
//
//            // Inflate transitions to apply
//            val changeTransform = TransitionInflater.from(this).inflateTransition(R.transition.change_image_transform)
//
//            // Setup exit transition on first fragment
//            fragmentOne.sharedElementReturnTransition = changeTransform
//            fragmentOne.exitTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.slide_left)
//
//            // Setup enter transition on second fragment
//            fragmentTwo.sharedElementEnterTransition = changeTransform
//            fragmentTwo.enterTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.slide_right)
//
//
//            // Add second fragment by replacing first
//            val ft = supportFragmentManager.beginTransaction()
//                    .replace(android.R.id.content, fragmentTwo)
//                    .addToBackStack(null)
//                    .addSharedElement(historyViewHolder.getImage(), historyViewHolder.getImage().transitionName)
//                    .addSharedElement(historyViewHolder.getTextView(), historyViewHolder.getTextView().transitionName)
//            // Apply the transaction
//            ft.commit()
//        } else {
            supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, RepositoryListFragment.newInstance(githubHandle), RepositoryListFragment.TAG)
                    .addToBackStack(null)
                    .commit()
//        }
    }
}





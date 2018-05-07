package com.dpsingh.githublookup.ui


import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.dpsingh.githublookup.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.IdlingRegistry
import com.dpsingh.githublookup.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(HomeActivity::class.java)


    @Before
    fun init() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun test_searchIsVisibleAndEditTextIsEnables() {

        onView(withId(R.id.et_search))
                .check(matches(isDisplayed()))
                .check(matches(isFocusable()))
                .check(matches(isEnabled()))

        onView(withId(R.id.progress_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tv_error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.btn_search)).check(matches(isDisplayed()))

    }

    @Test
    fun test_githubSearchForValidUser() {

        onView(withId(R.id.et_search))
                .check(matches(isDisplayed()))
                .perform(replaceText("dp-singh"))
                .perform(closeSoftKeyboard())

        onView(withId(R.id.btn_search))
                .check(matches(isDisplayed()))
                .perform(click())

        onView(withId(R.id.tv_username)).check(matches(withText("Dharmendra Pratap Singh (dp-singh)")))

        Espresso.pressBack()

        onView(childAtPosition(withId(R.id.rv_recent_search), 1))
                .check(matches(hasDescendant(withText("DP-SINGH"))))
                .perform(click())

        onView(withId(R.id.tv_username))
                .check(matches(withText("Dharmendra Pratap Singh (dp-singh)")))
        Espresso.pressBack()
    }

    @Test
    fun test_githubSearchForInValidUser() {

        onView(withId(R.id.et_search))
                .check(matches(isDisplayed()))
                .perform(replaceText("alksjdlaksjd"))
                .perform(closeSoftKeyboard())

        onView(withId(R.id.btn_search))
                .check(matches(isDisplayed()))
                .perform(click())


        onView(withId(R.id.tv_error)).check(matches(isDisplayed())).check(matches(withText("User not found")))

    }


    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return (parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position))
            }
        }
    }

}

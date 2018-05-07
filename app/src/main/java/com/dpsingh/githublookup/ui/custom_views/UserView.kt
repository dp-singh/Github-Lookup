package com.dpsingh.githublookup.ui.custom_views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import com.dpsingh.githublookup.GlideApp
import com.dpsingh.githublookup.R
import com.dpsingh.githublookup.domain.model.User
import com.dpsingh.githublookup.extensions.px
import kotlinx.android.synthetic.main.item_user_detail.view.*


class UserView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.item_user_detail, this, true)
    }


    private fun formatData(stringData: String, stringValue: String): CharSequence {
        val span1 = SpannableString(stringData)
        span1.setSpan(AbsoluteSizeSpan(24.px), 0, stringData.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        val span2 = SpannableString(stringValue)
        span2.setSpan(AbsoluteSizeSpan(14.px), 0, stringValue.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return TextUtils.concat(span1, "\n", span2)
    }

    fun bind(user: User) {
        GlideApp.with(this)
                .load(user.avatar_url)
                .centerCrop()
                .optionalCircleCrop()
                .into(img_avatar)
        tv_username.text = String.format("%s (%s)", user.name, user.login)
        tv_description.text = user.company

        tv_gist.text = formatData(user.public_gists.toString(), "Gist")
        tv_repos.text = formatData(user.public_repos.toString(), "Repositories")
        tv_followers.text = formatData(user.followers.toString(), "Followers")
        tv_following.text = formatData(user.following.toString(), "Following")
    }
}
package com.mohaberabi.linkedinclone.onboarding.presentation.domain

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mohaberabi.onboarding.R


enum class OnBoardingPage(
    @StringRes val title: Int,
    @DrawableRes val image: Int
) {
    LandJob(
        image = R.drawable.onb_1,
        title = R.string.onboaring_one_ttl,
    ),
    Network(
        image = R.drawable.onb_2,
        title = R.string.onboaring_two_ttl,
    ),
    Career(
        image = R.drawable.ong_3,
        title = R.string.onboaring_three_ttl,
    ),
}


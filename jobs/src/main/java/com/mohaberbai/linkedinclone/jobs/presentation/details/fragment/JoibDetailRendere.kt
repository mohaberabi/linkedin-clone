package com.mohaberbai.linkedinclone.jobs.presentation.details.fragment

import android.widget.TextView
import com.mohaberabi.jobs.databinding.FragmentJobDetailBinding
import com.mohaberabi.linkedin.core.domain.model.JobDetailModel
import com.mohaberabi.presentation.ui.util.UiText
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.hide
import com.mohaberabi.presentation.ui.util.extension.hideAll
import com.mohaberabi.presentation.ui.util.extension.show
import com.mohaberbai.linkedinclone.jobs.presentation.details.viewmodel.JobDetailState
import com.mohaberbai.linkedinclone.jobs.presentation.details.viewmodel.JobDetailStatus

fun FragmentJobDetailBinding.bindWithState(
    state: JobDetailState,
) {
    when (state.state) {
        JobDetailStatus.Error -> error(state.error)
        JobDetailStatus.Populated -> populated(state.detail!!)
        else -> loading()
    }
}

private fun FragmentJobDetailBinding.loading() {
    hideAll(
        error,
        nestedScroll
    )
    loader.show()
}

private fun FragmentJobDetailBinding.error(errorText: UiText) {
    val errorVal = errorText.asString(root.context)
    loader.hide()
    error.show()
    error.setErrorTitle(errorVal)
}

private fun FragmentJobDetailBinding.populated(
    detail: JobDetailModel,
) {
    loader.hide()
    error.hide()
    nestedScroll.show()
    companyLogo.cachedImage(detail.companyLogo)
    company.text = detail.company
    jobTime.text = detail.time.name
    jobLocation.text = detail.jobPlace
    about.text = detail.about
    skillsLayout.removeAllViews()
    jobTitle.text = detail.jobTitle
    jobLocation.text = detail.jobPlace
    detail.skills.forEach { skill ->
        val skillView = TextView(root.context).apply {
            text = skill
            textSize = 14f
        }
        skillsLayout.addView(skillView)
    }
}

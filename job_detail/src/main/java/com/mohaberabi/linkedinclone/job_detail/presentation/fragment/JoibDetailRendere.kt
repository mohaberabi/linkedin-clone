package com.mohaberabi.linkedinclone.job_detail.presentation.fragment

import android.view.View
import android.widget.TextView
import com.mohaberabi.job_detail.databinding.FragmentJobDetailBinding
import com.mohaberabi.linkedin.core.domain.model.JobDetailModel
import com.mohaberabi.linkedinclone.job_detail.presentation.viewmodel.JobDetailState
import com.mohaberabi.linkedinclone.job_detail.presentation.viewmodel.JobDetailStatus
import com.mohaberabi.presentation.ui.util.UiText
import com.mohaberabi.presentation.ui.util.cached

fun FragmentJobDetailBinding.render(state: JobDetailState) {
    when (state.state) {
        JobDetailStatus.Error -> error(state.error)
        JobDetailStatus.Populated -> populated(state.detail!!)
        else -> loading()
    }
}

private fun FragmentJobDetailBinding.loading() {
    with(this) {
        error.hide()
        nestedScroll.visibility = View.GONE
        loader.show()
    }
}

private fun FragmentJobDetailBinding.error(errorText: UiText) {
    val errorVal = errorText.asString(root.context)
    with(this) {
        loader.hide()
        error.show()
        error.setErrorTitle(errorVal)
    }
}

private fun FragmentJobDetailBinding.populated(detail: JobDetailModel) {
    with(this) {
        loader.hide()
        error.hide()
        nestedScroll.visibility = View.VISIBLE
        companyLogo.cached(detail.companyLogo)
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
}

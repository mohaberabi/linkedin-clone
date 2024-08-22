package com.mohaberabi.linkedinclone.job_detail.data.source.remote.dto

import com.mohaberabi.linkedin.core.domain.model.JobTime
import com.mohaberabi.linkedin.core.domain.model.JobType


data class JobDetailDto(
    val id: String,
    val company: String,
    val companyLogo: String,
    val jobTitle: String,
    val jobPlace: String,
    val postedAtMillis: Long,
    val type: String,
    val time: String,
    val about: String,
    val skills: List<String>,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        0L,
        "",
        "",
        "",
        emptyList(),
    )
}
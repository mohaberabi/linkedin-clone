package com.mohaberabi.linkedin.core.domain.model

data class JobModel(
    val id: String,
    val company: String,
    val companyLogo: String,
    val jobTitle: String,
    val jobPlace: String,
    val postedAtMillis: Long
)
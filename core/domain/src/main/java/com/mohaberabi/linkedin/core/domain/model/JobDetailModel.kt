package com.mohaberabi.linkedin.core.domain.model


data class JobDetailModel(
    val id: String,
    val company: String,
    val companyLogo: String,
    val jobTitle: String,
    val jobPlace: String,
    val postedAtMillis: Long,
    val type: JobType,
    val time: JobTime,
    val about: String,
    val skills: List<String>,
)


enum class JobType {
    OnSite,
    Hybird,
    Remote
}

enum class JobTime {
    FullTime,
    PartTime,
    Contract
}
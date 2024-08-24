package com.mohaberabi.linkedinclone.core.data.dto


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
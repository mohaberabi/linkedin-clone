package com.mohaberabi.linkedinclone.core.data.dto


data class JobDto(
    val id: String,
    val company: String,
    val companyLogo: String,
    val jobTitle: String,
    val jobPlace: String,
    val postedAtMillis: Long
) {
    constructor() : this("", "", "", "", "", 0L)
}


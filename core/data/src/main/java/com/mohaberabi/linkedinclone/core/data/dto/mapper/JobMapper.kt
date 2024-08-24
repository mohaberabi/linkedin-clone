package com.mohaberabi.linkedinclone.core.data.dto.mapper

import com.mohaberabi.linkedin.core.domain.model.JobModel
import com.mohaberabi.linkedinclone.core.data.dto.JobDto


fun JobDto.toJobModel() =
    JobModel(
        id = id,
        company = company,
        companyLogo = companyLogo,
        jobTitle = jobTitle,
        jobPlace = jobPlace,
        postedAtMillis = postedAtMillis
    )
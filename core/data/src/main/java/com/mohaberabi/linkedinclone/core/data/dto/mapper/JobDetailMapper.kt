package com.mohaberabi.linkedinclone.core.data.dto.mapper

import com.mohaberabi.linkedin.core.domain.model.JobDetailModel
import com.mohaberabi.linkedin.core.domain.model.JobTime
import com.mohaberabi.linkedin.core.domain.model.JobType
import com.mohaberabi.linkedinclone.core.data.dto.JobDetailDto


fun JobDetailDto.toJobDetailModel(
) = JobDetailModel(
    id = id,
    postedAtMillis = postedAtMillis,
    time = JobTime.valueOf(time),
    type = JobType.valueOf(type),
    skills = skills,
    about = about,
    company = company,
    companyLogo = companyLogo,
    jobPlace = jobPlace,
    jobTitle = jobTitle,
)
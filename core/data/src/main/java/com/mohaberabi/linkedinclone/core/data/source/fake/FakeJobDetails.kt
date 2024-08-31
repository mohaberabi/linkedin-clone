package com.mohaberabi.linkedinclone.core.data.source.fake

import com.mohaberabi.linkedin.core.domain.model.JobTime
import com.mohaberabi.linkedin.core.domain.model.JobType
import com.mohaberabi.linkedinclone.core.data.dto.JobDetailDto
import com.mohaberabi.linkedinclone.core.data.dto.JobDto

object FakeJobDetails {


    private val jobDetailsData = listOf(
        Triple(
            "Software Engineer",
            listOf("Kotlin", "Java", "Android", "REST APIs"),
            listOf("Android Studio", "Git", "JIRA")
        ),
        Triple(
            "Product Manager",
            listOf("Product Management", "Leadership", "Analytics"),
            listOf("Trello", "Asana", "Google Analytics")
        ),
        Triple(
            "Data Scientist",
            listOf("Python", "R", "Machine Learning", "Data Analysis"),
            listOf("TensorFlow", "Jupyter", "Pandas")
        ),
        Triple(
            "Operations Manager",
            listOf("Operations Management", "Problem Solving", "Leadership"),
            listOf("Microsoft Office", "ERP Systems", "Lean Management")
        ),
        Triple(
            "UX Designer",
            listOf("Figma", "Sketch", "User Experience", "Prototyping"),
            listOf("Adobe XD", "InVision", "Marvel")
        ),
    )


    fun fakeJobDetails(): List<JobDetailDto> {
        val jobTypes = JobType.entries
        val jobTimes = JobTime.entries

        val fakes = mutableListOf<JobDetailDto>()
        for (i in 1..50) {
            val companyIndex = (i - 1) % FakeJobs.companyLogos.size
            val company = FakeJobs.companyLogos.keys.elementAt(companyIndex)
            val (jobTitle, primarySkills, additionalSkills) = jobDetailsData[i % jobDetailsData.size]
            val jobType = jobTypes[i % jobTypes.size]
            val jobTime = jobTimes[i % jobTimes.size]

            val about = FakeJobs.detailedJobRequirement
                .replace("[JobTitle]", jobTitle)
                .replace("[PrimarySkills]", primarySkills.joinToString(", "))
                .replace("[AdditionalSkills]", additionalSkills.joinToString(", "))
                .replace("[Tools/Technologies]", additionalSkills.joinToString(", "))

            fakes.add(
                JobDetailDto(
                    id = i.toString(),
                    company = company,
                    companyLogo = FakeJobs.companyLogos[company] ?: "",
                    jobTitle = jobTitle,
                    jobPlace = "New Cairo , Cairo , Egypt",
                    postedAtMillis = System.currentTimeMillis() - (i * 86400000L),
                    type = jobType.name,
                    time = jobTime.name,
                    about = about,
                    skills = primarySkills
                )
            )
        }
        return fakes
    }


}
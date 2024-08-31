package com.mohaberabi.linkedinclone.core.data.source.fake

import com.mohaberabi.linkedinclone.core.data.dto.JobDto


object FakeJobs {
    val jobTitles = listOf(
        "Software Engineer",
        "Product Manager",
        "Data Scientist",
        "Operations Manager",
        "UX Designer",
        "Marketing Specialist",
        "Sales Manager",
        "HR Coordinator",
        "Customer Support Representative",
        "DevOps Engineer",
        "Frontend Developer",
        "Backend Developer",
        "Full Stack Developer",
        "Android Developer",
        "iOS Developer",
        "Quality Assurance Engineer",
        "Project Manager",
        "Business Analyst",
        "Graphic Designer",
        "Content Writer",
        "SEO Specialist",
        "Database Administrator",
        "Network Engineer",
        "Security Analyst",
        "Cloud Architect",
        "Machine Learning Engineer",
        "AI Researcher",
        "Data Analyst",
        "Copywriter",
        "Technical Support Engineer",
        "System Administrator",
        "Software Architect",
        "Technical Lead",
        "Product Designer",
        "User Researcher",
        "UI/UX Designer",
        "Social Media Manager",
        "IT Manager",
        "Finance Manager",
        "Legal Advisor",
        "Chief Technology Officer (CTO)",
        "Chief Executive Officer (CEO)",
        "Chief Operating Officer (COO)",
        "Chief Marketing Officer (CMO)",
        "Chief Financial Officer (CFO)",
        "Corporate Trainer",
        "Account Manager",
        "Public Relations Specialist",
        "Procurement Specialist",
        "Recruitment Specialist"
    )

    val companyLogos = mapOf(
        "Foogle" to "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/480px-Google_%22G%22_logo.svg.png",
        "LinkedIn" to "https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/LinkedIn_icon.svg/480px-LinkedIn_icon.svg.png",
        "Facebook" to "https://freepnglogo.com/images/all_img/facebook-circle-logo-png.png",
        "Ammazon" to "https://www.hatchwise.com/wp-content/uploads/2022/08/Amazon-Logo-2000-present-1024x576.jpeg",
        "Uber" to "https://upload.wikimedia.org/wikipedia/commons/c/cc/Uber_logo_2018.png"
    )

    fun fakeJobs(): List<JobDto> {
        val fakes = mutableListOf<JobDto>()
        for (i in 1..50) {
            val companyIndex = (i - 1) % companyLogos.size
            val company = companyLogos.keys.elementAt(companyIndex)
            val jobTitle = jobTitles[i - 1 % jobTitles.size]
            fakes.add(
                JobDto(
                    id = i.toString(),
                    company = company,
                    companyLogo = companyLogos[company] ?: "",
                    jobTitle = jobTitle,
                    jobPlace = "New Cairo , Cairo , Egypt",
                    postedAtMillis = System.currentTimeMillis() - (i * 86400000L) // posted days ago
                )
            )
        }
        return fakes
    }

    val detailedJobRequirement = """
    We are seeking a highly skilled and motivated individual to join our team as a [Software Engineer]. The successful candidate will have the following responsibilities:
    
    - Design, develop, and maintain cutting-edge software solutions to meet the needs of our business.
    - Collaborate with cross-functional teams to define, design, and ship new features.
    - Write clean, maintainable code and perform peer code reviews.
    - Troubleshoot, debug, and upgrade existing software.
    - Ensure the performance, quality, and responsiveness of applications.
    - Identify and correct bottlenecks and fix bugs to improve application performance.
    - Continuously discover, evaluate, and implement new technologies to maximize development efficiency.
    
    **Required Skills:**
    - Proficient in [PrimarySkills], with a strong understanding of [AdditionalSkills].
    - Experience with [Tools/Technologies].
    - Strong problem-solving skills and the ability to work independently and as part of a team.
    - Excellent communication and interpersonal skills.
    - Experience in Agile development methodologies.
    
    **Preferred Qualifications:**
    - Bachelor’s degree in Computer Science, Engineering, or a related field.
    - Experience with cloud platforms such as AWS, GCP, or Azure.
    - Familiarity with CI/CD pipelines.
    - Previous experience in a similar role.
""".trimIndent()

}
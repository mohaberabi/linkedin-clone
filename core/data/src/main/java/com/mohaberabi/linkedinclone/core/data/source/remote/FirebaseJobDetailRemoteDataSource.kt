package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.model.JobDetailModel
import com.mohaberabi.linkedin.core.domain.model.JobTime
import com.mohaberabi.linkedin.core.domain.model.JobType
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedin.core.domain.source.remote.JobDetailRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.dto.JobDetailDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toJobDetailModel
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class FirebaseJobDetailRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dispatchers: DispatchersProvider,
) : JobDetailRemoteDataSource {


    override suspend fun getJobDetail(
        id: String,
    ): JobDetailModel {
        return withContext(dispatchers.io) {
            firestore.safeCall {
                collection(EndPoints.JOB_DETAILS).document(id).get().await()
                    .toObject(JobDetailDto::class.java)!!.toJobDetailModel()
            }
        }
    }

    private fun addDummyData() {
        val dummyJobs = generateDummyJobs(100)
        dummyJobs.forEach { job ->
            firestore.collection(EndPoints.JOB_DETAILS)
                .document(job.id)
                .set(job)
        }
    }

    private fun generateDummyJobs(count: Int): List<JobDetailDto> {
        return (1..count).map { index ->
            JobDetailDto(
                id = "job$index",
                company = "Google",
                companyLogo = "https://cdn2.hubspot.net/hubfs/53/image8-2.jpg",
                jobTitle = "Senior Android Developer",
                jobPlace = "San Francisco, CA",
                postedAtMillis = System.currentTimeMillis(),
                type = JobType.entries[Random.nextInt(JobType.entries.size)].name,
                time = JobTime.entries[Random.nextInt(JobTime.entries.size)].name,
                about = """
        We're looking for a Senior Android Developer to join our mobile team. 
        As a Senior Android Developer, you will be responsible for designing, 
        developing, and maintaining our mobile application to ensure an excellent 
        user experience. You will work closely with product managers, designers, 
        and other engineers to translate requirements into high-quality features 
        and ensure the application's performance and stability.
        
        Key Responsibilities:
        - Design and implement new features for our Android app using Kotlin and modern Android frameworks.
        - Collaborate with cross-functional teams to define, design, and ship new features.
        - Write clean, maintainable, and testable code.
        - Ensure the best possible performance, quality, and responsiveness of the application.
        - Identify and correct bottlenecks and fix bugs.
        - Continuously discover, evaluate, and implement new technologies to maximize development efficiency.
        
        Requirements:
        - 5+ years of experience in Android development.
        - Proficiency in Kotlin and a strong understanding of Android development frameworks.
        - Experience with Jetpack Compose, Coroutines, and modern architecture patterns like MVVM.
        - Familiarity with RESTful APIs and third-party libraries such as Retrofit and Dagger/Hilt.
        - Experience with Git and version control practices.
        - Excellent problem-solving skills and the ability to work in a fast-paced environment.
        - Strong communication skills and the ability to work well in a team.
        
        Preferred Qualifications:
        - Experience with mobile CI/CD pipelines.
        - Understanding of Android's accessibility standards and best practices.
        - Experience with testing frameworks like JUnit, Espresso, and Mockito.
        
        At LinkedIn, we value diversity, inclusion, and innovation. We offer competitive salaries, comprehensive benefits, 
        and a collaborative work environment where you can grow your career.
        
        If you're passionate about mobile development and looking to make an impact at a company that touches millions of users 
        daily, we'd love to hear from you.
    """.trimIndent(),
                skills = listOf(
                    "Kotlin",
                    "Jetpack Compose",
                    "Coroutines",
                    "MVVM",
                    "REST APIs",
                    "Unit Testing",
                    "CI/CD",
                    "Dagger/Hilt"
                )
            )

        }
    }
}


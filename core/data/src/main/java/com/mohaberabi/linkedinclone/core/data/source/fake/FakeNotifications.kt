package com.mohaberabi.linkedinclone.core.data.source.fake

import com.mohaberabi.linkedin.core.domain.model.InAppNotificationBehaviour
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedinclone.core.data.dto.InAppNotificationDto
import com.mohaberabi.linkedinclone.core.data.dto.InAppNotificationSenderDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toMap
import java.util.UUID
import kotlin.random.Random


object FakeNotifications {

    private fun getRandomReactionType(): ReactionType {
        val list = ReactionType.entries.filter { it != ReactionType.All }
        return list[Random.nextInt(list.size)]
    }

    private fun generateRandomComment(): String {
        val comments = listOf(
            "This is a great post!",
            "I totally agree with you!",
            "Thanks for sharing this!",
            "Interesting perspective!",
            "Well said!"
        )
        return comments[Random.nextInt(comments.size)]
    }

    private fun generateAction(): Map<String, Any> {

        val comment = InAppNotificationBehaviour.Comment(
            generateRandomComment(),
            postId = ""
        )
        val reaction = InAppNotificationBehaviour.Reaction(
            getRandomReactionType(),
            postId = "",
            postHeader = generateRandomComment()
        )

        val list = listOf(
            comment,
            InAppNotificationBehaviour.ViewProfile,
            reaction
        )
        return list.random().toMap()
    }

    private fun generateFakeNotification(): InAppNotificationDto {

        val sender = InAppNotificationSenderDto(
            name = names.random(),
            uid = UUID.randomUUID().toString(),
            img = images.random()
        )

        val model = InAppNotificationDto(
            sender = sender,
            receiverUid = UUID.randomUUID().toString(),
            behave = generateAction(),
            createdAtMillis = System.currentTimeMillis(),
            id = UUID.randomUUID().toString(),
        )
        return model
    }


    fun fakeNotification() = buildList {
        repeat(50) {
            add(generateFakeNotification())
        }
    }
}
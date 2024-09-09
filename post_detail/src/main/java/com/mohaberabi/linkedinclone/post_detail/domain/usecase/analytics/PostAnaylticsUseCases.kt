package com.mohaberabi.linkedinclone.post_detail.domain.usecase.analytics


data class PostAnaylticsUseCases(
    val postOpened: LogPostOpenedUseCase,
    val postGotComment: LogPostGotCommentUseCase,
    val postHasCommentInterest: LogPostHasCommentInterestUseCase,
    val postHasReactionsInterest: LogPostInterestInReactions
)
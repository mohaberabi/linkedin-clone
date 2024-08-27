package com.mohaberbia.linkedinclone.posts.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.usecase.ReactToPostUseCase
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberbia.linkedinclone.posts.usecase.GetPostsUseCase
import com.mohaberabi.presentation.ui.util.asUiText
import com.mohaberbia.linkedinclone.posts.usecase.ListenToPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val listenToPostsUseCase: ListenToPostsUseCase,
    private val reactToPostUseCase: ReactToPostUseCase,
) : ViewModel() {


    companion object {
        private const val LAST_POST_ID = "lastPostId"
        private const val POSTS_LIMIT_KEY = "postLimit"
        private const val DEFAULT_POSTS_LIMIT = 20

    }

    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    private val _events = Channel<PostsEvents>()
    val events = _events.receiveAsFlow()
    private var reactJob: Job? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    private var postsFlow = savedStateHandle.getStateFlow<Int?>(
        POSTS_LIMIT_KEY,
        DEFAULT_POSTS_LIMIT
    ).flatMapLatest { limit ->
        listenToPostsUseCase(limit = limit ?: DEFAULT_POSTS_LIMIT)
    }


//    @OptIn(ExperimentalCoroutinesApi::class)
//    private var postsFlow = savedStateHandle.getStateFlow<String?>(
//        LAST_POST_ID,
//        null
//    ).flatMapLatest { lastPostId ->
//        listenToPosts(lastPostId)
//    }


    init {
        postsFlow
            .onStart {
                if (_state.value.posts.isEmpty()) {
                    _state.update { it.copy(state = PostsStatus.Loading) }
                }
            }
            .catch {
                _state.update {
                    it.copy(state = PostsStatus.Error)
                }
            }
            .onEach { posts ->
                updatePosts(posts)
            }.launchIn(viewModelScope)

    }

    fun onAction(action: PostsActions) {
        when (action) {
            PostsActions.LoadMore -> loadMore()
            is PostsActions.ReactToPost -> reactToPost(
                postId = action.postId,
                reactionType = action.reactionType
            )
        }
    }


//    private fun listenToPosts(
//        lastPostId: String?,
//    ): Flow<List<PostModel>> {
//        return if (lastPostId != null) {
//            listenToPostsUseCase(
//                lastDocId = lastPostId,
//            )
//        } else {
//            listenToPostsUseCase()
//        }
//    }

    private fun updatePosts(
        posts: List<PostModel>,
    ) {
        _state.update { state ->
            state.copy(
                state = PostsStatus.Populated,
                posts = posts,
            )
        }
    }


    private fun reactToPost(
        postId: String,
        reactionType: ReactionType,
    ) {
        reactJob?.cancel()
        reactJob = viewModelScope.launch {
            reactToPostUseCase(
                postId = postId,
                reactionType = reactionType
            ).onFailure { fail ->
                _events.send(PostsEvents.Error(fail.asUiText()))
            }
        }
    }

    private fun loadMore() {
        val lastPage = savedStateHandle.get<Int?>(POSTS_LIMIT_KEY) ?: DEFAULT_POSTS_LIMIT
        savedStateHandle[POSTS_LIMIT_KEY] = lastPage + DEFAULT_POSTS_LIMIT
    }

//    private fun loadMore() {
//        val lastPostId = _state.value.posts.lastOrNull()?.id
//        savedStateHandle[LAST_POST_ID] = lastPostId
//    }


}
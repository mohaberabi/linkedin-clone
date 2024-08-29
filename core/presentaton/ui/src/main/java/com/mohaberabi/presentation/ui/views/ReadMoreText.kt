package com.mohaberabi.presentation.ui.views


import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.mohaberabi.core.presentation.ui.R
import com.mohaberabi.core.presentation.ui.databinding.ReadMoreTextBinding

class ReadMoreText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ReadMoreTextBinding =
        ReadMoreTextBinding.inflate(LayoutInflater.from(context), this, true)
    private var isExpanded = false
    private var defaultMaxLines = 3

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ReadMoreText,
            0, 0
        ).apply {
            try {
                setUpInitialTextViewAttributes()
                setUpInitialToggleTextViewAttributes()
            } finally {
                recycle()
            }
        }

        setupToggleClickListener()
        setupTextChangeListener()
    }

    private fun setUpInitialTextViewAttributes() {
        binding.readMoreTextView.apply {
            text = getStringFromAttrs(R.styleable.ReadMoreText_readMoreText) ?: ""
        }
    }

    private fun setUpInitialToggleTextViewAttributes() {
        binding.readMoreToggleTextView.apply {
            text = context.getString(R.string.read_more)
        }
    }

    private fun setupToggleClickListener() {
        binding.readMoreToggleTextView.setOnClickListener {
            toggleTextExpansion()
        }
    }

    private fun setupTextChangeListener() {
        binding.readMoreTextView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.readMoreTextView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                checkIfToggleIsNeeded()
            }
        })
    }

    private fun checkIfToggleIsNeeded() {
        val isTextOverflowing = binding.readMoreTextView.lineCount > defaultMaxLines
        binding.readMoreToggleTextView.visibility =
            if (isTextOverflowing) View.VISIBLE else View.GONE
        binding.readMoreTextView.maxLines =
            if (isTextOverflowing && !isExpanded) defaultMaxLines else Int.MAX_VALUE
    }

    private fun getStringFromAttrs(attr: Int): String? {
        return context.theme.obtainStyledAttributes(intArrayOf(attr)).getString(0)
    }

    fun setText(
        content: String,
        maxLines: Int = 3,
    ) {
        defaultMaxLines = maxLines
        binding.readMoreTextView.text = content
        checkIfToggleIsNeeded()
    }

    private fun toggleTextExpansion() {
        isExpanded = !isExpanded
        binding.readMoreTextView.maxLines = if (isExpanded) Int.MAX_VALUE else defaultMaxLines
        binding.readMoreToggleTextView.text =
            context.getString(if (isExpanded) R.string.read_less else R.string.read_more)
    }
}

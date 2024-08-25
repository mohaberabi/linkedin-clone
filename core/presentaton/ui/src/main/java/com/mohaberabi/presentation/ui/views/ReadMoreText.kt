package com.mohaberabi.presentation.ui.views


import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
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
                binding.readMoreTextView.setTextColor(
                    getColor(
                        R.styleable.ReadMoreText_readMoreTextColor,
                        binding.readMoreTextView.currentTextColor
                    )
                )

                val textSizeInSp = getDimension(
                    R.styleable.ReadMoreText_readMoreTextSize,
                    binding.readMoreTextView.textSize
                )
                binding.readMoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeInSp)

                binding.readMoreTextView.text =
                    getString(R.styleable.ReadMoreText_readMoreText) ?: ""

                binding.readMoreToggleTextView.setTextColor(
                    getColor(
                        R.styleable.ReadMoreText_toggleTextColor,
                        binding.readMoreToggleTextView.currentTextColor
                    )
                )

                val toggleTextSizeInSp = getDimension(
                    R.styleable.ReadMoreText_toggleTextSize,
                    binding.readMoreToggleTextView.textSize
                )
                binding.readMoreToggleTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    toggleTextSizeInSp
                )

                binding.readMoreToggleTextView.text =
                    getString(R.styleable.ReadMoreText_toggleTextReadMore) ?: "Read More"
            } finally {
                recycle()
            }
        }

        binding.readMoreToggleTextView.setOnClickListener {
            toggleTextExpansion()
        }
    }

    fun setText(content: String, maxLines: Int = 3) {
        with(binding) {
            readMoreTextView.text = content
            defaultMaxLines = maxLines
            readMoreTextView.maxLines = defaultMaxLines
            readMoreTextView.post {
                if (readMoreTextView.lineCount > defaultMaxLines) {
                    readMoreToggleTextView.visibility = VISIBLE
                    readMoreTextView.maxLines = defaultMaxLines
                } else {
                    readMoreToggleTextView.visibility = GONE
                }
            }
        }
    }

    private fun toggleTextExpansion() {
        with(binding) {
            if (isExpanded) {
                readMoreTextView.maxLines = defaultMaxLines
                readMoreToggleTextView.text = context.getString(R.string.read_more)
            } else {
                readMoreTextView.maxLines = Int.MAX_VALUE
                readMoreToggleTextView.text = context.getString(R.string.read_less)
            }
            isExpanded = !isExpanded
        }
    }

}
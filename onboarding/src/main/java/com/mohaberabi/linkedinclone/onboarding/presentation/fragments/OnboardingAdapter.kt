package com.mohaberabi.linkedinclone.onboarding.presentation.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohaberabi.linkedinclone.onboarding.presentation.domain.OnBoardingPage
import com.mohaberabi.onboarding.databinding.OnboardingPageBinding

class OnBoardingAdapter : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    private val pages = OnBoardingPage.entries

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnBoardingViewHolder {
        val binding = OnboardingPageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OnBoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(pages[position])
    }

    override fun getItemCount(): Int = pages.size

    class OnBoardingViewHolder(private val binding: OnboardingPageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(page: OnBoardingPage) {
            binding.onboardingImage.setImageResource(page.image)
            binding.onboardingText.setText(page.title)
        }
    }
}

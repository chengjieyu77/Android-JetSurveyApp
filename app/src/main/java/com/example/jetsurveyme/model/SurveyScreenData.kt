package com.example.jetsurveyme.model

import android.widget.Button

data class SurveyScreenData(
    val questionIndex:Int,
    val questionCount:Int,
    val shouldShowPreviousButton:Boolean,
    val shouldShowDoneButton: Boolean,
    val surveyQuestion:SurveyQuestion
)

enum class SurveyQuestion{
    FREE_TIME,
    SUPERHERO,
    LAST_TAKEAWAY,
    FELLING_ABOUT_SELFIES,
    TAKE_SELFIE
}

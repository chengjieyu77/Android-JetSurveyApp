package com.example.jetsurveyme.screen.surveycontent

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetsurveyme.data._questionLists
import com.example.jetsurveyme.model.Superhero
import com.example.jetsurveyme.model.SurveyQuestion
import com.example.jetsurveyme.model.SurveyScreenData

class SurveyContentViewModel():ViewModel(){
    private val questionOrder = listOf(
        SurveyQuestion.FREE_TIME,
        SurveyQuestion.SUPERHERO,
        SurveyQuestion.LAST_TAKEAWAY,
        SurveyQuestion.FELLING_ABOUT_SELFIES,
        SurveyQuestion.TAKE_SELFIE
    )
    private var questionIndex = 1

    private val _freeTimeResponse = mutableStateListOf<Int>()
    val freeTimeResponse:List<Int>
        get() = _freeTimeResponse

    private val _superheroResponse = mutableStateOf<Superhero?>(null)
    val superheroResponse : Superhero?
        get() = _superheroResponse.value

    private val _takeawayResponse = mutableStateOf<String?>(null)
    val takeawayResponse:String?
        get() = _takeawayResponse.value

    private val _feelingAboutSelfiesResponse = mutableStateOf<Float?>(null)
    val feelingAboutSelfiesResponse:Float?
        get() = _feelingAboutSelfiesResponse.value

    private val _takeSelfieResponse = mutableStateOf<Uri?>(null)
    val selfieUri
        get() = _takeSelfieResponse

    val _isNextEnabled = mutableStateOf(false)
    private val _surveyScreenData = mutableStateOf(createSurveyScreenData())

    val surveyScreenData : SurveyScreenData
        get() = _surveyScreenData.value

    var isNextEnabled : Boolean = false
        get() = _isNextEnabled.value

    fun onBackPressed():Boolean{
        if (questionIndex == 1){
            return false
        }
        changeQuestion(questionIndex-1)
        return true
    }
    fun onPreviousPressed(){
        changeQuestion(questionIndex-1)
    }

    fun onNextPressed(){
        changeQuestion(questionIndex+1)
    }

    private fun changeQuestion(newQuestionIndex: Int){
        questionIndex = newQuestionIndex
        _isNextEnabled.value = getIsNextEnabled()
        _surveyScreenData.value = createSurveyScreenData()
    }

    fun onDonePressed(onSurveyComplete:()->Unit){
        onSurveyComplete()
    }

    fun onFreeTimeResponse(selected:Boolean,answer:Int){
        if (selected){
            _freeTimeResponse.add(answer)
        }else{
            _freeTimeResponse.remove(answer)
        }
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onSuperheroResponse(superhero: Superhero){
        _superheroResponse.value = superhero
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onTakeawayResponse(date:String){
        _takeawayResponse.value = date
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onFeelingAboutSelfies(answer:Float){
        _feelingAboutSelfiesResponse.value = answer
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onTakingSelfiesResponse(uri:Uri){
        _takeSelfieResponse.value = uri
        _isNextEnabled.value = getIsNextEnabled()
    }

    private fun getIsNextEnabled():Boolean{
        return when(questionOrder[questionIndex-1]){
            SurveyQuestion.FREE_TIME -> _freeTimeResponse.isNotEmpty()
            SurveyQuestion.SUPERHERO -> _superheroResponse.value != null
            SurveyQuestion.LAST_TAKEAWAY -> _takeawayResponse.value != null
            SurveyQuestion.FELLING_ABOUT_SELFIES -> _feelingAboutSelfiesResponse.value != null
            SurveyQuestion.TAKE_SELFIE -> _takeSelfieResponse.value != null
        }
    }

    private fun createSurveyScreenData(): SurveyScreenData {
        return SurveyScreenData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowDoneButton = questionIndex == questionOrder.size,
            shouldShowPreviousButton = questionIndex>1,
            surveyQuestion = questionOrder[questionIndex-1],
        )
    }
}


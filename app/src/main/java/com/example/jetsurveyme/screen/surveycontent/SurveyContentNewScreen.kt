package com.example.jetsurveyme.screen.surveycontent

import android.icu.util.Calendar
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetsurveyme.R
import com.example.jetsurveyme.component.ColoredButton
import com.example.jetsurveyme.component.ColorlessButton
import com.example.jetsurveyme.model.Superhero
import com.example.jetsurveyme.navigation.JetsurveyScreens
import com.example.jetsurveyme.screen.surveycontent.question.Question4
import com.example.jetsurveyme.screen.surveycontent.question.Question5
import com.example.jetsurveyme.util.formatCurrentDate
import com.example.jetsurveyme.util.formatDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SurveyContentNewScreen(
    email:String = "",
    totalCount: Int = 5,
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    surveyContentViewModel: SurveyContentViewModel = viewModel()
){
    val screenData = surveyContentViewModel.surveyScreenData
    val possibleAnswersMultipleChoice = listOf(
        R.string.multiChoice1,
         R.string.multiChoice2,
        R.string.multiChoice3,
        R.string.multiChoice4,
        R.string.multiChoice5,
        R.string.multiChoice6,
       R.string.multiChoice7,
      R.string.multiChoice8,
        R.string.multiChoice9,
    )

    val possibleAnswersSingleChoice = listOf(
        Superhero(R.drawable.spark,R.string.comic1),
        Superhero(R.drawable.lenz,R.string.comic2),
        Superhero(R.drawable.bug_of_chaos,R.string.comic3),
        Superhero(R.drawable.frag,R.string.comic4)
    )

    val mContext = LocalContext.current
    val mYear:Int
    val mMonth:Int
    val mDay:Int
    val mCalendar = Calendar.getInstance()
    val currentDate = formatCurrentDate()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)

    val formatter = DateTimeFormatter.ofPattern("EEE, MMM d")

    val mDate = remember{ mutableStateOf(currentDate) }

    val mDatePickerDialog = android.app.DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = LocalDate.of(mYear,mMonth+1,mDayOfMonth).format(formatter)
            //"$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )


    Scaffold(
        modifier = modifier,
        topBar = { SurveyContentTopBar(currentIndex = screenData.questionIndex , totalCount = screenData.questionCount)},
        bottomBar = { SurveyContentBottomBar(
            currentIndex = screenData.questionIndex,
            totalCount = screenData.questionCount,
            isNextEnabled = surveyContentViewModel.isNextEnabled,
            onDonePressed = {surveyContentViewModel.onDonePressed { navController.navigate(JetsurveyScreens.FinishScreen.name) }},
            onNextPressed = {surveyContentViewModel.onNextPressed()},
            onPreviousPressed = { surveyContentViewModel.onPreviousPressed() },
            )}
    ) {
        Column(modifier = Modifier.padding(it)) {
            AnimatedContent(targetState = screenData.questionIndex) {targetCount->
                when(targetCount){
                    1 -> Question1New(possibleAnswers = possibleAnswersMultipleChoice,
                        selectedAnswers = surveyContentViewModel.freeTimeResponse,
                        onOptionSelected = surveyContentViewModel::onFreeTimeResponse)
                    2 -> Question2New(possibleAnswers =possibleAnswersSingleChoice ,
                        selectedHero = surveyContentViewModel.superheroResponse,
                        onHeroSelected = surveyContentViewModel::onSuperheroResponse)
                    3 -> Question3New(
                        date = mDate.value
                    ) { mDatePickerDialog.show()
                    surveyContentViewModel.onTakeawayResponse(mDate.value)
                    }

                    4 -> Question4()
                    5 -> Question5()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun showDatePicker(
    date: Long?,
    onDateSelected: (date: Long) -> Unit,
) {
    val mContext = LocalContext.current
    val mYear:Int
    val mMonth:Int
    val mDay:Int
    val mCalendar = Calendar.getInstance()
    val currentDate = formatCurrentDate()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)

    val formatter = DateTimeFormatter.ofPattern("EEE, MMM d")

    val mDate = remember{ mutableStateOf(currentDate) }

    val mDatePickerDialog = android.app.DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = LocalDate.of(mYear,mMonth+1,mDayOfMonth).format(formatter)
            //"$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )
    mDatePickerDialog.show()

}




@Composable
fun SurveyContentBottomBar(
    currentIndex: Int,
    totalCount: Int,
    isNextEnabled:Boolean,
    onNextPressed:()->Unit,
    onPreviousPressed:()->Unit,
    onDonePressed:()->Unit
   ){
    Row(modifier = Modifier
        .fillMaxWidth()
        .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom))
        .padding(horizontal = 16.dp, vertical = 20.dp)) {
        if (currentIndex == 1){
            ColoredButton(text = "Next",
                enabled = isNextEnabled){
                onNextPressed.invoke()
            }
        }else if(currentIndex == totalCount){

            ColorlessButton(text = "Previous", width = 0.5f){
               onPreviousPressed.invoke()
            }
            ColoredButton(text = "Done",
                enabled = isNextEnabled){
                onDonePressed.invoke()
            }
        }else{
            ColorlessButton(text = "Previous", width = 0.5f){
                onPreviousPressed.invoke()
            }
            ColoredButton(text = "Next",
                enabled = isNextEnabled){
                onNextPressed.invoke()
            }

        }



    }
}

@Composable
fun Question4New(){

}

@Composable
fun Question3New(
    date: String,
    modifier: Modifier = Modifier,
    onClick:  ()->Unit,
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .clickable { onClick.invoke() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Text(text = date,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = modifier.padding(start = 16.dp))
        Icon(imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "arrow down icon",
            modifier = modifier.padding(end = 16.dp))
    }
}

@Composable
fun Question2New(modifier: Modifier = Modifier,
                 possibleAnswers: List<Superhero>,
                 selectedHero:Superhero?,
                 onHeroSelected:(Superhero)->Unit
                 ){
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        QuestionHeader(title = R.string.question2,
            supportingText = "Select one")


        Column(Modifier.selectableGroup()) {
            possibleAnswers.forEach{superHero ->
                val selected = superHero == selectedHero

                Question2ItemNew(
                        text = superHero.name,
                        imageId = superHero.image,
                        selected = selected,
                        onHeroSelected = {onHeroSelected(superHero)})

                Spacer(modifier = modifier.height(16.dp))
            }

        }
    }
}

@Composable
fun Question2ItemNew(modifier: Modifier = Modifier,
                     text:Int,
                     imageId:Int,
                     selected: Boolean,
                     onHeroSelected: () -> Unit){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp, color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(8.dp)
            )
            .selectable(
                selected = selected,
                onClick = { onHeroSelected() },
                role = Role.RadioButton
            )
            .background(
                color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = modifier.padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id =imageId),
                contentDescription = "character's image",
                modifier = modifier
                    .size(55.dp)
                    .padding(start = 8.dp))
            Text(text = stringResource(id =text ),
                modifier = modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.titleMedium)
        }

        RadioButton(selected = selected,
            onClick = null,
            modifier = modifier.padding(end = 8.dp))
    }
}
@Composable
fun Question1New(modifier: Modifier = Modifier,
                 possibleAnswers:List<Int>,
                 selectedAnswers:List<Int>,
                 onOptionSelected:(selected:Boolean,answer:Int)->Unit){
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        QuestionHeader(title = R.string.question1,
            supportingText = "Select all that apply")

        LazyColumn {
            items(possibleAnswers){possibleAnswer->
                val selected = selectedAnswers.contains(possibleAnswer)
                CheckItem(selected = selected,
                    label = stringResource(id = possibleAnswer),
                    onOptionSelected = { onOptionSelected(!selected,possibleAnswer) })
                Spacer(modifier = modifier.height(16.dp))

            }
        }}}


@Composable
fun CheckItem(modifier: Modifier = Modifier,
              selected: Boolean,
              label:String,
              onOptionSelected: ()->Unit,
              ){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp, color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(8.dp)
            )
            .selectable(
                selected = selected,
                onClick = { onOptionSelected() },
                role = Role.Checkbox
            )
            .background(
                color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
            modifier = modifier.padding(start = 16.dp))

        Checkbox(checked = selected, onCheckedChange = null,
            modifier = modifier.padding(end = 8.dp))

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyContentTopBar(
    currentIndex: Int,
    totalCount:Int,
    onActionCLick:()->Unit = {}) {
    val progress = currentIndex/totalCount

    Column {
        CenterAlignedTopAppBar(
            title = { Text(text = "${currentIndex} of $totalCount") },
            actions = {
                IconButton(onClick = onActionCLick) {
                    Icon(imageVector = Icons.Default.Close,
                        contentDescription = "close icon")
                }
            }
        )
        LinearProgressIndicator(progress = currentIndex.toFloat()/5,
            modifier = Modifier.fillMaxWidth())
    }}




@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SurveyContentNewScreenPreview(){
    SurveyContentNewScreen()
}

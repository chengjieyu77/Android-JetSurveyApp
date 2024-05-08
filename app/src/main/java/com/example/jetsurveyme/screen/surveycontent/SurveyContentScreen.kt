package com.example.jetsurveyme.screen.surveycontent

import android.Manifest
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.jetsurveyme.R
import com.example.jetsurveyme.component.ColoredButton
import com.example.jetsurveyme.component.ColorlessButton
import com.example.jetsurveyme.data.DataSource
import com.example.jetsurveyme.model.ComicCharacter
import com.example.jetsurveyme.model.ListItem
import com.example.jetsurveyme.model.Question
import com.example.jetsurveyme.navigation.JetsurveyScreens
import com.example.jetsurveyme.util.createImageFile
import com.example.jetsurveyme.util.formatCurrentDate
import com.google.firebase.BuildConfig
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Objects

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurveyContentScreen(navController: NavController,
                        modifier: Modifier = Modifier,
                        ){
    //Text(text = "This is content")
    val dataSource = DataSource()
    val question1 = dataSource.getQuestion1()
    val question2 = dataSource.getQuestion2()
    val pagerState = rememberPagerState(pageCount = {
        5
    })
    val coroutineScope = rememberCoroutineScope()



    Column {

        ContentHeader(pagerState = pagerState){
            navController.navigate(JetsurveyScreens.LoginScreen.name)
        }

        HorizontalPager(state = pagerState,

            userScrollEnabled = false,
            modifier = modifier
                .fillMaxSize()
                .weight(1f),
            verticalAlignment = Alignment.Top) {page ->
            when(page){
                0 -> Question1(question1 = question1)
                1 -> Question2(question2 = question2)
                2 -> Question3()
                3 -> Question4()
                4 -> Question5()
            }


        }
        BottomButtons(pagerState = pagerState,
            modifier = modifier,
            question1 = question1,
            question2 = question2){
            navController.navigate(JetsurveyScreens.FinishScreen.name)
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomButtons(pagerState: PagerState,
                  modifier: Modifier = Modifier,
                  question1: Question<List<ListItem>>,
                  question2: Question<List<ComicCharacter>>,
                  onDone: () -> Unit = {}) {
    val coroutineScope = rememberCoroutineScope()
    val question1Valid = question1.answer.filter {
        it.checked == true
    }.isNotEmpty()
    val question2Valid = question2.answer.filter {
        it.selected == true
    }.isNotEmpty()
    when(pagerState.settledPage){
        0 ->ColoredButton(text = "Next", enabled = question1Valid){
            coroutineScope.launch {
                pagerState.animateScrollToPage(pagerState.settledPage+1)
            }
        }
        1 -> Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ColorlessButton(text = "Previous", width = 0.5f){
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.settledPage-1)
                }
            }
            ColoredButton(text = "Next", width = 1f){
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.settledPage+1)
                }
            }
        }
        2 -> Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ColorlessButton(text = "Previous", width = 0.5f){
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.settledPage-1)
                }
            }
            ColoredButton(text = "Next", width = 1f){
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.settledPage+1)
                }
            }
        }

        3->Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ColorlessButton(text = "Previous", width = 0.5f){
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.settledPage-1)
                }
            }
            ColoredButton(text = "Next", width = 1f){
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.settledPage+1)
                }
            }
        }

        4 ->Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ColorlessButton(text = "Previous", width = 0.5f){
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.settledPage-1)
                }
            }
            ColoredButton(text = "Done", width = 1f){
                onDone()
            }
        }
    }

}

@Composable
fun Question5(modifier: Modifier = Modifier){
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "com.example.jetsurveyme"+".provider",file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it){
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        }else{
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        QuestionTitle(title = R.string.question5)
        Spacer(modifier = modifier.height(25.dp))
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = Color.Black.copy(0.8f),
                    shape = RoundedCornerShape(8.dp)
                )
                .height(400.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            if (capturedImageUri.path?.isNotEmpty() == true) {

                    Image(
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .width(220.dp),
                        painter = rememberImagePainter(capturedImageUri),
                        contentDescription = null
                    )



            }else{
                Image(painter = painterResource(id = R.drawable.ic_selfie_light),
                    contentDescription = "selfie picture",
                    modifier = modifier
                        .padding(vertical = 16.dp)
                        .width(220.dp))
            }

            
            Row(
                modifier = modifier
                    .padding(vertical = 16.dp)
                    .clickable {
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(uri)
                        } else {
                            // Request a permission
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (capturedImageUri.path?.isNotEmpty() == true){
                    Icon(painter = painterResource(id = R.drawable.ic_swap_horiz) ,
                        contentDescription = "add a photo",
                        tint = MaterialTheme.colorScheme.primary)
                    Text(text = "RETAKE PHOTO",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = modifier.padding(start = 4.dp))
                }else{
                    Icon(painter = painterResource(id = R.drawable.baseline_add_a_photo_black_36),
                        contentDescription = "add a photo",
                        tint = MaterialTheme.colorScheme.primary)
                    Text(text = "ADD PHOTO",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = modifier.padding(start = 4.dp))
                }

            }
        }
    }
}

@Composable
fun Question4(modifier: Modifier = Modifier,
              onSliderFocusedChanged:(Boolean) -> Unit = {}){
    var  sliderPosition by remember { mutableFloatStateOf(25f) }

    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        QuestionTitle(title = R.string.question4)

        Slider(value = sliderPosition,
            onValueChange = {sliderPosition = it},
            valueRange = 0f..50f,
            steps = 5)

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Strongly Dislike",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f))
            Text(text = "Neutral",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f))
            Text(text = "Strongly Like",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f))
        }


    }



}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Question3(modifier: Modifier = Modifier){
    val mContext = LocalContext.current

    val mYear:Int
    val mMonth:Int
    val mDay:Int
    val mDayOfWeek:Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)
    mDayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK)
    val timeMill:Long = mCalendar.timeInMillis



    mCalendar.time = Date()

    val currentDate = formatCurrentDate()
    val formatter = DateTimeFormatter.ofPattern("EEE, MMM d")

    val mDate = remember{ mutableStateOf(currentDate) }

    val mDatePickerDialog = android.app.DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = LocalDate.of(mYear,mMonth+1,mDayOfMonth).format(formatter)
                //"$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())

    val openDialog = remember { mutableStateOf(false) }
    
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        QuestionHeader(title = R.string.question3,
            supportingText = "Select date")

        Question3Item(currentDate = mDate.value){
            mDatePickerDialog.show()
            //openDialog.value = true
        }


    }





}

@Composable
fun Question3Item(currentDate:String,
                  modifier: Modifier = Modifier,
                  onClick:  () -> Unit = {}){
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
        Text(text = currentDate,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = modifier.padding(start = 16.dp))
        Icon(imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "arrow down icon",
            modifier = modifier.padding(end = 16.dp))
    }

    
}

@Composable
fun Question2(modifier: Modifier = Modifier,
              question2:Question<List<ComicCharacter>>){

    val answerList = question2.answer
    val (selectedOption,onOptionSelected) = remember{
        mutableStateOf(ComicCharacter(R.drawable.spark,R.string.comic1))
        //mutableStateOf(ComicCharacter(-1,-1))
    }
    val selected = remember{
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()


    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        QuestionHeader(title = R.string.question2,
            supportingText = "Select one")


        Column(Modifier.selectableGroup()) {
            answerList.forEach{comicCharacter ->
                Question2Item(comicCharacter = comicCharacter,
                    selected = selectedOption == comicCharacter,
                    ){
                    onOptionSelected(comicCharacter)
                    scope.launch {
                        comicCharacter.selected = selectedOption == comicCharacter
                    }

                }
                Spacer(modifier = modifier.height(16.dp))
            }

        }
    }
}

@Composable
fun Question2Item(comicCharacter: ComicCharacter,
                  modifier : Modifier = Modifier,
                  selected:Boolean = false,
                  onClick:()->Unit = {}){
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
                onClick = onClick,
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
            Image(painter = painterResource(id = comicCharacter.image),
                contentDescription = "character's image",
                modifier = modifier
                    .size(55.dp)
                    .padding(start = 8.dp))
            Text(text = stringResource(id =comicCharacter.name ),
                modifier = modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.titleMedium)
        }

        RadioButton(selected = selected,
            onClick = null,
            modifier = modifier.padding(end = 8.dp))
    }

}



@Composable
fun Question1(modifier: Modifier = Modifier,
              question1:Question<List<ListItem>>){
    val choices = question1.answer

    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        QuestionHeader(title = R.string.question1,
            supportingText = "Select all that apply")

        LazyColumn {
            items(choices){choice->
                Question1Item(choice = choice.label,
                    checked = choice.checked){
                    choice.checked = !choice.checked
                }
                Spacer(modifier = modifier.height(16.dp))

            }
        }




    }


}

@Composable
fun Question1Item(choice:String,
                  checked:Boolean,
                  modifier: Modifier = Modifier,
                  onCheckedChange:(Boolean)->Unit ={},
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
                selected = checked,
                onClick = { onCheckedChange.invoke(checked) },
                role = Role.Checkbox
            )
            .background(
                color = if (checked) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = choice,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
            modifier = modifier.padding(start = 16.dp))

        Checkbox(checked = checked, onCheckedChange = null,
            modifier = modifier.padding(end = 8.dp))

    }
}



@Composable
fun QuestionHeader(title: Int,
                   modifier: Modifier = Modifier,
                   supportingText:String){
    QuestionTitle(title = title)

    Text(text = supportingText,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
        modifier = modifier.padding(vertical = 16.dp))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentHeader(modifier: Modifier = Modifier,
                  pagerState:PagerState,
                  onClose:()->Unit = {}){
    val currentPageNumber =pagerState.settledPage + 1
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = modifier)
            Text(text = "$currentPageNumber of 5",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f))
            Icon(imageVector = Icons.Default.Close,
                contentDescription = "close icon",
                modifier = modifier
                    .size(25.dp)
                    .clickable {
                        onClose.invoke()
                        //navController.navigate(JetsurveyScreens.LoginScreen.name)
                    },
                tint = MaterialTheme.colorScheme.onSurface.copy(0.6f))
        }
        LinearProgressIndicator(progress = (pagerState.settledPage.toFloat() + 1)/5,
            modifier = Modifier.fillMaxWidth())
    }
}


@Composable
fun QuestionTitle(modifier: Modifier = Modifier,
    title:Int){
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 1.dp,
        color = Color.LightGray.copy(0.3f)
        ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = title),
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.padding(vertical = 8.dp, horizontal = 8.dp))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun Question3ItemPreview(){
    Question3Item("Mon, May 6")
}

@Preview(showBackground = true)
@Composable
fun Question2ItemPreview(){
    Question2Item(comicCharacter = ComicCharacter(image = R.drawable.spark, name = R.string.comic1))
}

@Preview(showBackground = true)
@Composable
fun Question1ItemPreview(){
    Question1Item(choice = "Read", checked = false)
}


@Preview(showBackground = true)
@Composable
fun QuestionTitlePreview(){
    QuestionTitle(title = R.string.question1)
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun ContentHeaderPreview(){
    ContentHeader(pagerState = rememberPagerState(pageCount = {
        5
    }))

}
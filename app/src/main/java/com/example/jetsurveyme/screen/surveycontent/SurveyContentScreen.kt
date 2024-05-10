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
import com.example.jetsurveyme.screen.surveycontent.question.Question1
import com.example.jetsurveyme.screen.surveycontent.question.Question2
import com.example.jetsurveyme.screen.surveycontent.question.Question3
import com.example.jetsurveyme.screen.surveycontent.question.Question4
import com.example.jetsurveyme.screen.surveycontent.question.Question5
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
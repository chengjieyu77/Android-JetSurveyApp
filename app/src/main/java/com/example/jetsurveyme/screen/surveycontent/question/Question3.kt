package com.example.jetsurveyme.screen.surveycontent.question

import android.icu.util.Calendar
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetsurveyme.R
import com.example.jetsurveyme.screen.surveycontent.QuestionHeader
import com.example.jetsurveyme.util.formatCurrentDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

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

@Preview(showBackground = true)
@Composable
fun Question3ItemPreview(){
    Question3Item("Mon, May 6")
}
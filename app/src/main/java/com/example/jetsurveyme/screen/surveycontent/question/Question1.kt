package com.example.jetsurveyme.screen.surveycontent.question

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetsurveyme.R
import com.example.jetsurveyme.model.ListItem
import com.example.jetsurveyme.model.Question
import com.example.jetsurveyme.screen.surveycontent.QuestionHeader

@Composable
fun Question1(modifier: Modifier = Modifier,
              question1: Question<List<ListItem>>
){
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

@Preview(showBackground = true)
@Composable
fun Question1ItemPreview(){
    Question1Item(choice = "Read", checked = false)
}
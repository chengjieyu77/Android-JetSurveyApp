package com.example.jetsurveyme.screen.surveycontent.question

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetsurveyme.R
import com.example.jetsurveyme.model.ComicCharacter
import com.example.jetsurveyme.model.Question
import com.example.jetsurveyme.screen.surveycontent.QuestionHeader
import kotlinx.coroutines.launch

@Composable
fun Question2(modifier: Modifier = Modifier,
              question2: Question<List<ComicCharacter>>
){

    val answerList = question2.answer
    val (selectedOption,onOptionSelected) = remember{
        mutableStateOf(ComicCharacter(R.drawable.spark, R.string.comic1))
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

@Preview(showBackground = true)
@Composable
fun Question2ItemPreview(){
    Question2Item(comicCharacter = ComicCharacter(image = R.drawable.spark, name = R.string.comic1))
}
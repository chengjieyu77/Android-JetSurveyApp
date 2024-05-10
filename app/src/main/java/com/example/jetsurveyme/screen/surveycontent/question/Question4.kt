package com.example.jetsurveyme.screen.surveycontent.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetsurveyme.R
import com.example.jetsurveyme.screen.surveycontent.QuestionTitle


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
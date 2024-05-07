package com.example.jetsurveyme.screen.finish

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetsurveyme.component.ColorlessButton
import com.example.jetsurveyme.navigation.JetsurveyScreens

@Preview(showBackground = true)
@Composable
fun FinishScreen(navController: NavController = rememberNavController(),
                 modifier : Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = modifier.height(45.dp))
        Text(text = "Compose",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface)

        Text(text = "Congratulations, you are Compose",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = modifier.padding(vertical = 15.dp))

        Text(text = "You are a curious developer,always willing to try" +
                " something new. You want to stay up to date with the trends to Compose is your middle name.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary,
            lineHeight = 18.sp,
            modifier = modifier.weight(1f)
         )

        ColorlessButton(text = "Done"){
            navController.navigate(JetsurveyScreens.LoginScreen.name)
        }
    }
}
package com.example.jetsurveyme.screen.surveycontent.question

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import com.example.jetsurveyme.R
import com.example.jetsurveyme.screen.surveycontent.QuestionTitle
import com.example.jetsurveyme.util.createImageFile
import java.util.Objects


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
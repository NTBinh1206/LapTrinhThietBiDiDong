package com.example.navigation.screens

import androidx.compose.foundation.Image
import com.example.navigation.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ImageScreen(onBack: () -> Unit){
    val imageUrl = "https://upload.wikimedia.org/wikipedia/commons/7/70/Hinh_UTH.jpg?utm_source=vi.wikipedia.org&utm_campaign=index&utm_content=original"

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        DetailTopBar("Images", onBack)
        AsyncImage(
            model = imageUrl,
            contentDescription = "Ảnh từ url ",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(12.dp))
        )
        Text(imageUrl, fontSize = 16.sp,textDecoration = TextDecoration.Underline, modifier = Modifier.padding(16.dp))
        Spacer(Modifier.height(24.dp))
        Image(
            painter = painterResource(R.drawable.compose),
            contentDescription = "uth",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(12.dp))
        )
        Text("In app" , fontSize = 16.sp , color = Color.Gray , textAlign = TextAlign.Center)

    }
}
package com.example.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                ProfileScreen()
            }
        }
    }
}


@Composable
fun ProfileScreen(){
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF8FAFF)).statusBarsPadding().padding(16.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth() ,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            SquareIconButton(icon = Icons.AutoMirrored.Filled.ArrowBack , iconColor = Color.Black )
            SquareIconButton(icon = Icons.Filled.Edit , iconColor = Color(0xFF1B9E77))
        }
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Image(
                painter = painterResource(id = R.drawable.avt),
                contentDescription = null ,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).clip(CircleShape),
            )
            Spacer( modifier = Modifier.size(20.dp))
            Text (
                text = "Nguyễn Thế Bình ",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text (
                text = "SV0123",
                fontSize = 25.sp,
            )
        }
    }
}
@Composable
fun SquareIconButton ( icon : ImageVector , iconColor : Color ){
    OutlinedIconButton(
        onClick = {},
        modifier = Modifier.size(44.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFF000000)),
    )
    {
        Icon(
            imageVector = icon ,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = iconColor,
        )
    }
}


package com.example.navigation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DetailTopBar(title : String , onBack: () -> Unit){
     Box(
         modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
     ){
         IconButton(onClick = onBack , modifier = Modifier.align(Alignment.CenterStart)){
             Icon(
                 imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                 contentDescription = "Back" ,
                 tint = Color(0xFF2196F3)
             )
         }
         Text(
             text = title,
             modifier = Modifier.align(Alignment.Center)
         )
     }
}

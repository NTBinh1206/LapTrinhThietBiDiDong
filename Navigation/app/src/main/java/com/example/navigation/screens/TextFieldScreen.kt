package com.example.navigation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun TextFieldScreen( onBack: () -> Unit ){
    var text by remember { mutableStateOf("")}
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        DetailTopBar("TextField" , onBack)
        Spacer(Modifier.weight(1f))
        OutlinedTextField(
           value = text ,
            onValueChange ={text = it},
            placeholder = {Text("Thông tin nhập")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = text.ifEmpty{"Tự động cập nhật dữ liệu theo textfield"},
            color = Color.Red
        )
        Spacer(Modifier.weight(2f))
    }
}
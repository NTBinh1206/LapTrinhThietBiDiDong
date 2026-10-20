package com.example.navigation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListScreen( onItemClick :(String) -> Unit ){
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp) ,
    ){
        Text("UI Components List" , fontWeight = FontWeight.Bold , fontSize = 20.sp , color = Color(0xFF2196F3) , textAlign = TextAlign.Center , modifier = Modifier.fillMaxWidth())
        Text("Display" , textAlign = TextAlign.Left , color = Color.Black , modifier = Modifier.padding(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ){
            ComponentItem("Text" , "Display text" ) {onItemClick("text")}
            ComponentItem("Image" , "Display image")  {onItemClick("image")}
            Text("Input" , textAlign = TextAlign.Left , color = Color.Black , modifier = Modifier.padding(10.dp))
            ComponentItem("TextField", "Input field") {onItemClick("textfield")}
            ComponentItem("PasswordField", "Input field for passwords") {onItemClick("passwordfield")}
            Text("Layout", textAlign = TextAlign.Left , color = Color.Black , modifier = Modifier.padding(10.dp))
            ComponentItem("Column", "Arranges elements vertically ") {onItemClick("column")}
            ComponentItem("Row", "Arranges elements horizontally ") {onItemClick("row")}
            ComponentItem("Box", "Arranges elements in a box") {onItemClick("box")}
        }
    }
}
@Composable
fun ComponentItem( title : String , description : String , onClick: () -> Unit){
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFBBDEFB))
            .clickable { onClick()}
            .padding(12.dp)
    ){
        Text("$title", fontWeight = FontWeight.Bold)
        Text("$description")
    }
}
@Preview (showBackground = true )
@Composable
fun ListScreenPreview() {
    ListScreen(onItemClick ={})
}

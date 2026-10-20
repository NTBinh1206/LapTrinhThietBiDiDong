package com.example.navigation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextDetailScreen(onBack: () -> Unit){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ){
        DetailTopBar("Text Detail " , onBack)
        Spacer(Modifier.weight(1f))
        Text(
            text = buildAnnotatedString {
                append("The")
                withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)){
                    append("quick")
                }
                append(" ")
                withStyle(SpanStyle(color = Color(0xFFCC7A00), fontSize = 36.sp)){
                    append("Brown")
                }
                append(" fox ")
                withStyle(SpanStyle(letterSpacing = 6.sp)){
                    append("jump")
                }
                withStyle(SpanStyle(fontWeight = FontWeight.Bold , fontStyle = FontStyle.Italic)){
                    append("over")
                }
                withStyle(SpanStyle(textDecoration = TextDecoration.Underline)){
                    append("the")
                }
                withStyle(SpanStyle(fontFamily = FontFamily.Cursive , fontStyle = FontStyle.Italic)){
                    append("lazy")
                }
                append(" dog")
            }
        )

    }
}
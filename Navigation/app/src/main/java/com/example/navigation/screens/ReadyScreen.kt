package com.example.navigation.screens
import com.example.navigation.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ReadyScreen( onReadyClick: () -> Unit){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.compose),
            contentDescription = "Jetpack Compose logo",
            modifier = Modifier.size(250.dp)
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text("Jetpack Compose " , fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach",
            textAlign = TextAlign.Center,
            color = Color.Gray
            )
        Spacer(modifier = Modifier.weight(1f))
        Button( onClick = onReadyClick , modifier = Modifier.fillMaxWidth().padding(14.dp) ,
             colors = ButtonDefaults.buttonColors( containerColor = Color(0xFF2196F3))
            ) {
            Text("I'm ready")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ReadyScreenPreview() {
    ReadyScreen(onReadyClick = {})
}

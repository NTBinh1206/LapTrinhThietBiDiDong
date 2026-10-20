package com.example.lab3

import android.os.Bundle
import android.util.Log.d
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab3.ui.theme.Lab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab3Theme {
                BodyView()
            }
        }
    }
}

@Composable
fun BodyView (){
    val diem = 8.5
    val loai = xepLoai(diem)
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF2F3F8))
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().background(Color(0xFF2C4A7C)).statusBarsPadding().padding(16.dp),
        ){
            Text("Xếp loại học lực " , color = Color.White ,fontWeight= FontWeight.Bold )

        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp).background(Color.White , RoundedCornerShape(10.dp)).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text("Điểm trung bình")
            Text("$diem" , fontSize=48.sp ,fontWeight = FontWeight.Bold)
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp).background(Color.Green , RoundedCornerShape(16.dp )).height(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(loai, color = mauXepLoai(loai), fontSize = 24.sp, fontWeight = FontWeight.Bold ,
                    modifier = Modifier.fillMaxWidth().background(Color(0xFFE3F4E4)).padding(12.dp),
                    textAlign = TextAlign.Center,
                    )
            }
        }
        Text("KIỂM TRA THÊM " ,
            modifier = Modifier.padding(start= 16.dp , top = 8.dp),
            fontSize = 12.sp
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .padding(16.dp)
        ){
            val bangDiem = listOf(9,7,5.5,3,11)
            for (d in bangDiem ){
                val l = xepLoai(d.toDouble())
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text("Điểm $d")
                    Text(l,color = mauXepLoai(l),fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

fun xepLoai(diem: Double): String {
    if (diem < 0 || diem > 10 ){return "Không hợp lệ"}
        else if (diem >=8.0 ) {return "Giỏi"}
        else if ( diem >=6.5 ) {return "Khá"}
        else if (diem >=5.0) {return "Trung bình"}
    else {return "Yếu"}
}
fun mauXepLoai(loai: String): Color {
    if (loai == "Giỏi") {
        return Color(0xFF2E7D32)
    } else if (loai == "Khá") {
        return Color(0xFF1565C0)
    } else if (loai == "Trung bình") {
        return Color(0xFFEF6C00)
    } else if (loai == "Yếu") {
        return Color(0xFFC62828)
    } else {
        return Color.Gray
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab3Theme {
        BodyView()
    }
}
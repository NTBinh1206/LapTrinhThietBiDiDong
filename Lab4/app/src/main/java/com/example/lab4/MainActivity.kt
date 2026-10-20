package com.example.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab4.ui.theme.Lab4Theme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4Theme {
                UIListStudent()
            }
        }
    }
}
@Composable
fun UIListStudent(){
    var tong = 0.0
    var chuaCoDiem = 0
    var soGioi = 0
    var coDiem = 0
    var caoNhat = 0.0
    for (sv in danhSach) {
        val diem = sv.score
        if (diem == null )
        {
            chuaCoDiem++
        }
        else {
            tong = tong + diem
            coDiem++
            if (diem >= 8.0) {
                soGioi++
            }
            if (diem > caoNhat) {
                caoNhat = diem
            }
        }
    }
    val trungBinh = if(coDiem ==0) 0.0 else  tong / coDiem
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF2F3F8))
    ){
        Box(
            modifier = Modifier.fillMaxWidth().background(Color(0xFF2C4A7C)).statusBarsPadding().padding(16.dp)
        ){
            Text("Bảng điểm lớp" , color = Color.White , fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp).background(Color(0xFF2C4A7C) , RoundedCornerShape(16.dp)).padding(16.dp),
        ){
            Text("Điểm trung bình lớp" , color = Color.White , textAlign = TextAlign.Left)
            Text(
                String.format(Locale.US ,"%.2f" , trungBinh),
                color = Color.White,fontSize = 36.sp, fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween

            ){
                Text("Giỏi: \n $soGioi", color = Color.White)
                Text("Chưa có điểm: \n $chuaCoDiem", color = Color.White)
                Text("Cao nhất: \n $caoNhat", color = Color.White)
            }
        }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text ("DANH SÁCH · ${danhSach.size} SINH VIEN "
                , modifier = Modifier.padding(start = 16.dp , top = 16.dp , bottom = 8.dp)
            )
            for (sv in danhSach){
                val diem = sv.score
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(16.dp)).padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                    ){
                    Column{
                        Text(sv.name , fontWeight = FontWeight.Bold)
                        if ( diem == null) {
                            Text("Chưa có điểm" )
                        }
                        else {
                            val loai = xepLoai(diem)
                            Text(loai ,color = mauXepLoai(loai))
                        }
                    }
                    if(diem == null )
                    {
                        Text("---")
                    }
                    else {
                        Text("$diem" ,fontSize = 24.sp , fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }



    }
}
class Student(val name: String , val score: Double?)
val danhSach = listOf(
    Student("Nguyễn Văn An", 9.0),
    Student("Trần Thị Bình", 7.2),
    Student("Lê Minh Chi", null),
    Student("Phạm Quốc Dũng", 4.5),
    Student("Võ Thanh Em", 5.8)
)
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
    Lab4Theme {
        UIListStudent()
    }
}
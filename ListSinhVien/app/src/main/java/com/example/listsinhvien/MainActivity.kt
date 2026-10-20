package com.example.listsinhvien

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listsinhvien.ui.theme.ListSinhVienTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListSinhVienTheme {
                BackGroundList()
            }
        }
    }
}

@Composable
fun BackGroundList(){
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF2F3F8))
    ){
        Box(
            modifier = Modifier.fillMaxWidth().background(Color(0xFF2C4A7C)).statusBarsPadding().padding(16.dp)
        ) {
            Text("Danh sách sinh viên" , color = Color.White)
        }
        // Danh sách
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            var countEmail = 0
            for (sinhVien in danhSach){
                if (sinhVien.email == null) countEmail++

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White,RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ){
                    Text(sinhVien.name , fontWeight = FontWeight.Bold)
                    Text("Email: ${sinhVien.email ?: "Chưa cập nhật"}")
                    Text("Sdt: ${sinhVien.phone ?: "Chưa cập nhật"}")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFE8EEF8), RoundedCornerShape(12.dp)).padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tổng: ${danhSach.size} sinh viên ")
                Text("Thiếu email : ${countEmail}")
            }
        }

    }
}

class SinhVien(val name : String , val email : String? ,val  phone : String?)

val danhSach = listOf(
    SinhVien("Nguyễn Văn An" , "an123@gmail.com", "0923113392"),
    SinhVien("Nguyễn Thế Bình" , "binh92315@gmail.com", phone = null),
    SinhVien("Lê Minh Chi", "chi@sv.edu.vn", null),
    SinhVien("Phạm Quốc Dũng", null, null),
    SinhVien("Võ Thanh Em", "em@sv.edu.vn", "0933 111 222")
    )


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListSinhVienTheme {
        BackGroundList()
    }
}
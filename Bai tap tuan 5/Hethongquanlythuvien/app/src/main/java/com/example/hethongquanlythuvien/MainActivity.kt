package com.example.hethongquanlythuvien

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hethongquanlythuvien.ui.theme.HethongquanlythuvienTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HethongquanlythuvienTheme {
                Main()
            }
        }
    }
}

@Composable
fun Main(vm: LibraryViewModel = viewModel()) {   // viewModel() tạo/lấy ViewModel dùng chung
    var selectedTab by rememberSaveable { mutableStateOf(0) }  // tab đang chọn, xoay máy không mất

    Scaffold(                                     // khung màn hình có sẵn chỗ cho thanh dưới
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Quản lý") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                    label = { Text("DS Sách") }

                )
                // tab 1 "DS Sách" dùng Icons.Default.List

                // tab 2 "Sinh viên" dùng Icons.Default.Person
                NavigationBarItem(
                    selected = selectedTab == 2 ,
                    onClick = {selectedTab = 2} ,
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = {Text("Sinh viên ")}
                )
            }
        }
    ) { innerPadding ->                           // innerPadding: khoảng chừa cho thanh dưới
        Box(Modifier.padding(innerPadding)) {
            when (selectedTab) {                  // when giống switch-case
                0 -> ManageScreen(vm)    // Phần 3 sẽ thay bằng ManageScreen(vm)
                1 -> BookListScreen(vm)     // Phần 4
                2 -> ListStudent()                // Phần 5
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HethongquanlythuvienTheme {
    }
}
package com.example.hethongquanlythuvien

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ManageScreen(vm : LibraryViewModel){
    var currentIndex by remember {mutableStateOf(0)}
    val checkedIds = remember { mutableStateListOf<Int>() }
    val student = vm.students[currentIndex]

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Hệ thống \n Quản lý thư viện " , fontSize = 22.sp, fontWeight = FontWeight.Bold ,textAlign = TextAlign.Center )
        Spacer(Modifier.height(24.dp))

        Text("Sinh viên" , fontWeight = FontWeight.Bold , modifier = Modifier.fillMaxWidth() , textAlign = TextAlign.Left)

        Row(verticalAlignment = Alignment.CenterVertically ) {
            OutlinedTextField(
                value = student.name,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.weight(1f))
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                currentIndex = (currentIndex + 1) % vm.students.size

            }) { Text("Thay đổi") }
        }
            Text("Danh sách sách " , fontWeight = FontWeight.Bold , modifier = Modifier.fillMaxWidth())
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f)
                    .background(Color(0xFFE0E0E0) , RoundedCornerShape(12.dp)).padding(8.dp)
            ){
                items(vm.books){ book ->
                    Card(Modifier.fillMaxWidth().padding(4.dp)){
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Checkbox(
                                checked = book.id in checkedIds,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        checkedIds.add(book.id)
                                    } else {
                                        checkedIds.remove(book.id)
                                    }
                                }
                            )
                            Text(book.title)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { vm.borrow(student.id, checkedIds.toList())
                    checkedIds.clear()
            }){
                Text("Thêm")
            }
            Spacer(Modifier.height(16.dp))
            Text("Sách đã mượn", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())

            val borrowedBooks = vm.books.filter { it.id in student.borrowedIds }
            if (borrowedBooks.isEmpty()) {
                Text("Chưa mượn sách nào")
            } else {
                borrowedBooks.forEach { book ->       // lặp qua từng cuốn, in ra một dòng
                    Text("• ${book.title}")           // ${...}: chèn giá trị biến vào chuỗi
                }
            }


        }
    }



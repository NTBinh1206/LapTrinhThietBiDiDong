package com.example.hethongquanlythuvien

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookListScreen(vm: LibraryViewModel) {
    var title by remember { mutableStateOf("") }      // nội dung ô nhập

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Danh sách sách", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Tên sách") },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                // TODO 1: nếu title không rỗng (gợi ý: title.isNotBlank())
                //         thì gọi vm.addBook(title) rồi xoá trắng ô nhập
                if(!title.isBlank()){
                    vm.addBook(title)
                    title = ""
                }
            }) { Text("Thêm") }
        }
        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(vm.books) { book ->
                // TODO 2: tìm những sinh viên đang mượn cuốn này
                //   gợi ý: vm.students.filter { book.id in it.borrowedIds }
                val borrowers = vm.students.filter {book.id in it.borrowedIds}

                Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(Modifier.padding(12.dp)) {
                        Text("${book.id}. ${book.title}", fontWeight = FontWeight.Bold)
                        // TODO 3: nếu borrowers rỗng → Text("Còn trong thư viện")
                        //         ngược lại → Text("Đang mượn: " + tên các sinh viên)
                        //   gợi ý: borrowers.joinToString(", ") { it.name }
                        if(borrowers.isEmpty()){
                            Text("Còn trong thư viện ")
                        }
                        else{
                            Text("Đang mượn: " + borrowers.joinToString(", ") { it.name })
                        }
                    }
                }
            }
        }
    }
}
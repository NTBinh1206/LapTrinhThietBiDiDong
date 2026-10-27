package com.example.hethongquanlythuvien

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class LibraryViewModel : ViewModel() {
    val books = mutableStateListOf(Book(1, "Sách 01") , Book(2,"Sách 02"))
    val students = mutableStateListOf(Student(1, "Nguyễn Văn A"), Student(2, "Nguyễn Văn B"))

    fun addBook(title : String){
        val newBook = Book(books.size + 1, title)
        books.add(newBook)
    }
    fun addStudent(name: String){
        val newStudent = Student(students.size + 1, name)
        students.add(newStudent)
    }
    fun borrow(studentId: Int , bookId : List<Int>){
        val i = students.indexOfFirst{it.id == studentId}
        if (i == -1) return
        students[i] = students[i].copy(
            borrowedIds = (students[i].borrowedIds + bookId).distinct()
        )
    }
}
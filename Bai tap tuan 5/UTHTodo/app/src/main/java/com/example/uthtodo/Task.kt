package com.example.uthtodo

enum class Category(val label: String) {
    STUDY("Học tập"),
    LIFE("Cuộc sống")
}

data class Task(
    val id: Int,
    val title: String,
    val note: String = "",
    val category: Category,
    val deadline: String = "",
    val isDone: Boolean = false
)

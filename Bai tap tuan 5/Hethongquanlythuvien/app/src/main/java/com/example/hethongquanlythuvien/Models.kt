package com.example.hethongquanlythuvien

data class Book(val id: Int, val title: String)
data class Student(val id: Int, val name: String, val borrowedIds: List<Int> = emptyList())
package com.example.uthtodo

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {
    val tasks = mutableStateListOf<Task>() // Compose tự vẽ lại khi list đổi
    private var nextId = 1

    // Vài việc mẫu để mở app là có dữ liệu xem ngay
    init {
        add("Làm bài tập Lập trình di động", "Bài UTH Todo V2", Category.STUDY, "23:00 28/10")
        add("Ôn bài Toán", "", Category.STUDY, "19:00 27/10")
        add("Đi chợ mua đồ ăn", "Trứng, rau, sữa", Category.LIFE, "")
    }

    fun add(title: String, note: String, category: Category, deadline: String) {
        tasks.add(Task(nextId++, title, note, category, deadline))
    }

    fun toggleDone(id: Int) {
        val i = tasks.indexOfFirst { it.id == id }
        if (i == -1) return
        tasks[i] = tasks[i].copy(isDone = !tasks[i].isDone)
    }

    fun delete(id: Int) {
        tasks.removeAll { it.id == id }
    }

    fun find(id: Int): Task? = tasks.find { it.id == id }
}

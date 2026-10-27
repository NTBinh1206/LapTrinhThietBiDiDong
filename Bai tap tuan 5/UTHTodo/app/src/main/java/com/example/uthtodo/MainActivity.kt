package com.example.uthtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uthtodo.ui.theme.UTHTodoTheme
import kotlinx.coroutines.delay

val UthBlue = Color(0xFF3B5BDB)
val UthBlueLight = Color(0xFFEEF1FD)
val TextGray = Color(0xFF6B7280)
val LineGray = Color(0xFFE5E7EB)
val LifeOrange = Color(0xFFD97706)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UTHTodoTheme {
                UTHTodo()
            }
        }
    }
}

// ================= NAVIGATION =================

@Composable
fun UTHTodo() {
    val navController = rememberNavController()
    val vm: TodoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onDone = {
                // popUpTo + inclusive: xoá splash khỏi back stack, bấm Back không quay lại được
                navController.navigate(Screen.Onboarding.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(onFinish = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Home.route) {
            HomeScreen(
                vm = vm,
                onAdd = { navController.navigate(Screen.Add.route) },
                onOpen = { id -> navController.navigate(Screen.Detail.createRoute(id)) }
            )
        }
        composable(Screen.Add.route) {
            AddScreen(vm = vm, onBack = { navController.popBackStack() })
        }
        composable(
            Screen.Detail.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: 0
            DetailScreen(vm = vm, id = id, onBack = { navController.popBackStack() })
        }
    }
}

// ================= DÙNG CHUNG =================

@Composable
fun AppLogo(size: Dp, background: Color, tint: Color) {
    Box(
        Modifier
            .size(size)
            .clip(RoundedCornerShape(size / 4))
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = tint, modifier = Modifier.size(size * 0.6f))
    }
}

fun Category.color(): Color = when (this) {
    Category.STUDY -> UthBlue
    Category.LIFE -> LifeOrange
}

@Composable
fun CategoryTag(text: String, color: Color) {
    Text(
        text,
        color = color,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}

// ================= SPLASH =================

@Composable
fun SplashScreen(onDone: () -> Unit) {
    var started by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (started) 1f else 0f,
        animationSpec = tween(2000),
        label = "progress"
    )

    LaunchedEffect(Unit) {
        started = true
        delay(2200)
        onDone()
    }

    Box(Modifier.fillMaxSize().background(UthBlue)) {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppLogo(88.dp, Color.White, UthBlue)
            Spacer(Modifier.height(20.dp))
            Text("UTH Todo", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text(
                "Việc học, việc nhà — gọn gàng mỗi ngày",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 14.sp
            )
        }
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.width(100.dp).height(4.dp).clip(RoundedCornerShape(2.dp)),
                color = Color.White,
                trackColor = Color.White.copy(alpha = 0.3f)
            )
            Spacer(Modifier.height(8.dp))
            Text("Phiên bản 2.1.0", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
        }
    }
}

// ================= ONBOARDING =================

enum class IllustrationType { CHECKLIST, CATEGORIES, REMINDER }

data class OnboardingPage(
    val title: String,
    val description: String,
    val illustration: IllustrationType
)

val onboardingPages = listOf(
    OnboardingPage(
        "Mọi việc, gọn trong một chỗ",
        "Ghi lại bài tập, việc nhà, lịch hẹn. Không còn quên việc nào.",
        IllustrationType.CHECKLIST
    ),
    OnboardingPage(
        "Chia theo Học tập và Cuộc sống",
        "Mỗi việc có nhóm riêng, nhìn là biết hôm nay cần lo gì.",
        IllustrationType.CATEGORIES
    ),
    OnboardingPage(
        "Nhắc đúng lúc",
        "Bật thông báo để UTH Todo nhắc bạn trước hạn chót.",
        IllustrationType.REMINDER
    )
)

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    var index by remember { mutableIntStateOf(0) }
    val page = onboardingPages[index]
    val isLast = index == onboardingPages.lastIndex

    // Bấm Back ở trang 2, 3 thì lùi về trang trước thay vì thoát app
    BackHandler(enabled = index > 0) { index-- }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
            .padding(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AppLogo(28.dp, UthBlue, Color.White)
            Spacer(Modifier.width(8.dp))
            Text("UTH ", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("Todo", color = UthBlue, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))
            Text(
                "Bỏ qua",
                color = TextGray,
                modifier = Modifier.clickable { onFinish() }.padding(8.dp)
            )
        }

        Spacer(Modifier.weight(1f))
        OnboardingIllustration(page.illustration, Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.weight(1f))

        Text(page.title, color = Color.Black, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(page.description, color = TextGray, fontSize = 15.sp)
        Spacer(Modifier.height(24.dp))
        DotsIndicator(total = onboardingPages.size, current = index)
        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { if (isLast) onFinish() else index++ },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = UthBlue, contentColor = Color.White)
        ) {
            Text(if (isLast) "Bắt đầu ngay" else "Tiếp tục", fontSize = 16.sp)
        }
    }
}

@Composable
fun DotsIndicator(total: Int, current: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(total) { i ->
            val selected = i == current
            Box(
                Modifier
                    .height(6.dp)
                    .width(if (selected) 22.dp else 6.dp)
                    .clip(CircleShape)
                    .background(if (selected) UthBlue else Color(0xFFD1D5DB))
            )
        }
    }
}

@Composable
fun OnboardingIllustration(type: IllustrationType, modifier: Modifier = Modifier) {
    Box(
        modifier
            .size(220.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(UthBlueLight)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        when (type) {
            IllustrationType.CHECKLIST -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                FakeTaskRow(done = true)
                FakeTaskRow(done = false)
                FakeTaskRow(done = false)
            }
            IllustrationType.CATEGORIES -> Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                FakeCategoryCard(Category.STUDY)
                FakeCategoryCard(Category.LIFE)
            }
            IllustrationType.REMINDER -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    Modifier.size(56.dp).clip(CircleShape).background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Notifications, contentDescription = null, tint = UthBlue)
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    "Ôn bài Toán lúc 19:00",
                    color = Color.Black,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun FakeTaskRow(done: Boolean) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(if (done) UthBlue else Color.White)
                .border(1.5.dp, if (done) UthBlue else Color(0xFFD1D5DB), CircleShape)
        )
        Spacer(Modifier.width(10.dp))
        Box(Modifier.height(6.dp).fillMaxWidth().clip(CircleShape).background(LineGray))
    }
}

@Composable
private fun FakeCategoryCard(category: Category) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(12.dp)
    ) {
        CategoryTag(category.label, category.color())
        Spacer(Modifier.height(8.dp))
        Box(Modifier.height(6.dp).fillMaxWidth().clip(CircleShape).background(LineGray))
    }
}

// ================= HOME =================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(vm: TodoViewModel, onAdd: () -> Unit, onOpen: (Int) -> Unit) {
    var filter by remember { mutableStateOf<Category?>(null) } // null = tất cả
    val shown = vm.tasks.filter { filter == null || it.category == filter }
    val remaining = vm.tasks.count { !it.isDone }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AppLogo(28.dp, UthBlue, Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text("UTH Todo", fontWeight = FontWeight.Bold)
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd, containerColor = UthBlue, contentColor = Color.White) {
                Icon(Icons.Default.Add, contentDescription = "Thêm việc")
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Text("Còn $remaining việc chưa xong", color = TextGray)
            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = filter == null, onClick = { filter = null }, label = { Text("Tất cả") })
                Category.entries.forEach { c ->
                    FilterChip(selected = filter == c, onClick = { filter = c }, label = { Text(c.label) })
                }
            }
            Spacer(Modifier.height(8.dp))

            if (shown.isEmpty()) {
                Text(
                    "Chưa có việc nào. Bấm + để thêm.",
                    color = TextGray,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 32.dp)
                )
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 88.dp)
            ) {
                items(shown, key = { it.id }) { task ->
                    TaskItem(
                        task = task,
                        onCheck = { vm.toggleDone(task.id) },
                        onClick = { onOpen(task.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onCheck: () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { onCheck() },
                colors = CheckboxDefaults.colors(checkedColor = UthBlue)
            )
            Column(Modifier.weight(1f)) {
                Text(
                    task.title,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = if (task.isDone) TextDecoration.LineThrough else null,
                    color = if (task.isDone) TextGray else Color.Unspecified
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CategoryTag(task.category.label, task.category.color())
                    if (task.deadline.isNotBlank()) {
                        Spacer(Modifier.width(8.dp))
                        Text("Hạn: ${task.deadline}", fontSize = 12.sp, color = TextGray)
                    }
                }
            }
        }
    }
}

// ================= ADD =================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(vm: TodoViewModel, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(Category.STUDY) }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thêm việc mới") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it; showError = false },
                label = { Text("Tên việc *") },
                isError = showError,
                supportingText = { if (showError) Text("Vui lòng nhập tên việc") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Ghi chú") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = deadline,
                onValueChange = { deadline = it },
                label = { Text("Hạn chót (vd: 19:00 27/10)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Nhóm", fontWeight = FontWeight.SemiBold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Category.entries.forEach { c ->
                    FilterChip(selected = category == c, onClick = { category = c }, label = { Text(c.label) })
                }
            }

            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    if (title.isBlank()) {
                        showError = true
                    } else {
                        vm.add(title.trim(), note.trim(), category, deadline.trim())
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = UthBlue, contentColor = Color.White)
            ) {
                Text("Lưu", fontSize = 16.sp)
            }
        }
    }
}

// ================= DETAIL =================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(vm: TodoViewModel, id: Int, onBack: () -> Unit) {
    val task = vm.find(id)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (task == null) {
                Text("Không tìm thấy việc này.", color = TextGray)
            } else {
                Text(task.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                CategoryTag(task.category.label, task.category.color())
                Spacer(Modifier.height(20.dp))

                InfoLine("Trạng thái", if (task.isDone) "Đã xong" else "Chưa xong")
                InfoLine("Hạn chót", task.deadline.ifBlank { "Không có" })
                InfoLine("Ghi chú", task.note.ifBlank { "Không có" })

                Spacer(Modifier.weight(1f))
                Button(
                    onClick = { vm.toggleDone(task.id) },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = UthBlue, contentColor = Color.White)
                ) {
                    Text(if (task.isDone) "Đánh dấu chưa xong" else "Đánh dấu đã xong")
                }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = {
                        vm.delete(task.id)
                        onBack()
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDC2626))
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Xoá việc")
                }
            }
        }
    }
}

@Composable
fun InfoLine(label: String, value: String) {
    Column(Modifier.padding(vertical = 6.dp)) {
        Text(label, fontSize = 13.sp, color = TextGray)
        Text(value, fontSize = 16.sp)
    }
}

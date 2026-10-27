package com.example.manhinhdangnhap

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.manhinhdangnhap.ui.theme.ManHinhDangNhapTheme

val BlueMain = Color(0xFF3D8BEA)
val TealUth = Color(0xFF2E8B8B)
val RedUth = Color(0xFFB22222)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManHinhDangNhapTheme {
                Surface(color = Color.White) { SmartTaskApp() }
            }
        }
    }
}

// forget --email--> verify --email, otp--> reset --email, otp, pass--> confirm
// Submit: ghi email/otp/pass vào màn "forget" rồi quay về màn đó
@Composable
fun SmartTaskApp() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "forget") {

        // ---------- MÀN 1 ----------
        composable("forget") { entry ->
            var email by remember { mutableStateOf("") }
            // Dữ liệu màn 4 gửi về (lần đầu là null)
            val kqEmail = entry.savedStateHandle.get<String>("email")
            val kqOtp = entry.savedStateHandle.get<String>("otp")
            val kqPass = entry.savedStateHandle.get<String>("pass")

            ScreenColumn {
                Logo()
                Title("Forget Password?", "Enter your Email, we will send you a verification code.")
                InputField(email, { email = it }, "Your Email")
                BlueButton("Next") {
                    if (email.isNotBlank()) nav.navigate("verify/${Uri.encode(email.trim())}")
                }
                if (kqEmail != null) {
                    Spacer(Modifier.height(32.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFDCEBFB), RoundedCornerShape(24.dp))
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Email: $kqEmail", color = RedUth, fontSize = 18.sp)
                        Text("OTP: $kqOtp", color = RedUth, fontSize = 18.sp)
                        Text("Pass: $kqPass", color = RedUth, fontSize = 18.sp)
                    }
                }
            }
        }

        // ---------- MÀN 2 ----------
        composable("verify/{email}") { entry ->
            val email = entry.arguments?.getString("email") ?: ""
            val digits = remember { mutableStateListOf("", "", "", "", "") }
            val focus = remember { List(5) { FocusRequester() } }

            ScreenColumn {
                BackButton { nav.popBackStack() }
                Logo()
                Title("Verify Code", "Enter the the code\nwe just sent you on your registered Email")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    for (i in 0 until 5) {
                        OutlinedTextField(
                            value = digits[i],
                            onValueChange = { v ->
                                val d = v.filter { it.isDigit() }.takeLast(1)
                                digits[i] = d
                                if (d.isNotEmpty() && i < 4) focus[i + 1].requestFocus()
                            },
                            modifier = Modifier.weight(1f).focusRequester(focus[i]),
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 20.sp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                BlueButton("Next") {
                    // Nhập số bất kỳ, chỉ cần đủ 5 ô
                    if (digits.all { it.isNotEmpty() }) {
                        nav.navigate("reset/${Uri.encode(email)}/${digits.joinToString("")}")
                    }
                }
            }
        }

        // ---------- MÀN 3 ----------
        composable("reset/{email}/{otp}") { entry ->
            val email = entry.arguments?.getString("email") ?: ""
            val otp = entry.arguments?.getString("otp") ?: ""
            var pass by remember { mutableStateOf("") }
            var confirm by remember { mutableStateOf("") }

            ScreenColumn {
                BackButton { nav.popBackStack() }
                Logo()
                Title("Create new password", "Your new password must be different form previously used password")
                InputField(pass, { pass = it }, "Password", isPassword = true)
                InputField(confirm, { confirm = it }, "Confirm Password", isPassword = true)
                if (confirm.isNotEmpty() && pass != confirm) {
                    Text("Mật khẩu không khớp", color = Color.Red, fontSize = 13.sp)
                    Spacer(Modifier.height(8.dp))
                }
                BlueButton("Next") {
                    if (pass.isNotEmpty() && pass == confirm) {
                        nav.navigate("confirm/${Uri.encode(email)}/$otp/${Uri.encode(pass)}")
                    }
                }
            }
        }

        // ---------- MÀN 4 ----------
        composable("confirm/{email}/{otp}/{pass}") { entry ->
            val email = entry.arguments?.getString("email") ?: ""
            val otp = entry.arguments?.getString("otp") ?: ""
            val pass = entry.arguments?.getString("pass") ?: ""

            ScreenColumn {
                BackButton { nav.popBackStack() }
                Logo()
                Title("Confirm", "We are here to help you!")
                InputField(email, {}, "", readOnly = true)
                InputField(otp, {}, "", readOnly = true)
                InputField(pass, {}, "", isPassword = true, readOnly = true)
                BlueButton("Submit") {
                    val home = nav.getBackStackEntry("forget")
                    home.savedStateHandle["email"] = email
                    home.savedStateHandle["otp"] = otp
                    home.savedStateHandle["pass"] = pass
                    nav.popBackStack("forget", inclusive = false)
                }
            }
        }
    }
}

// ================= Các phần dùng chung =================

@Composable
fun ScreenColumn(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
fun BackButton(onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = BlueMain),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(44.dp)
        ) {
            Text("<", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun Logo() {
    Spacer(Modifier.height(24.dp))
    Text("UTH", color = TealUth, fontWeight = FontWeight.Bold, fontSize = 40.sp)
    Text(
        "UNIVERSITY\nOF TRANSPORT\nHOCHIMINH CITY",
        color = RedUth, fontSize = 9.sp, fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center, lineHeight = 10.sp
    )
    Spacer(Modifier.height(8.dp))
    Text("SmartTasks", color = BlueMain, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(24.dp))
}

@Composable
fun Title(title: String, subtitle: String) {
    Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(8.dp))
    Text(subtitle, fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center)
    Spacer(Modifier.height(24.dp))
}

@Composable
fun BlueButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = BlueMain),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(text, fontSize = 16.sp)
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    readOnly: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        singleLine = true,
        readOnly = readOnly,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ManHinhDangNhapTheme {
        SmartTaskApp()
    }
}

package com.example.appdemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<MaterialButton>(R.id.buttonLogin)

        // Кнопка изначально отключена и серая
        loginButton.isEnabled = false
        loginButton.setBackgroundColor(0xFF555555.toInt()) // Серый

        // Функция проверки полей и изменения цвета кнопки
        fun updateButtonState() {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            val isValid = isValidEmail && password.isNotEmpty()

            loginButton.isEnabled = isValid

            // Меняем цвет в зависимости от состояния
            if (isValid) {
                // Зелёный когда всё заполнено правильно
                loginButton.setBackgroundColor(0xFF00FF7F.toInt())
            } else {
                // Серый когда что-то не так
                loginButton.setBackgroundColor(0xFF555555.toInt())
            }
        }

        // Слушатели на изменение текста Email
        emailEditText.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonState()
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        // Слушатели на изменение текста Пароля
        passwordEditText.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonState()
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        // Клик по "Войти"
        loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Клик по VK
        findViewById<MaterialButton>(R.id.buttonVK).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/"))
            startActivity(intent)
        }

        // Клик по OK
        findViewById<MaterialButton>(R.id.buttonOK).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ok.ru/"))
            startActivity(intent)
        }
    }
}
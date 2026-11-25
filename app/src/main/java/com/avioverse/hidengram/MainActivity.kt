package com.avioverse.hidengram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.styropyr0.prismal.components.PrismalGlassBottomTab
import com.styropyr0.prismal.components.PrismalGlassBottomTabs
import com.styropyr0.prismal.sources.prismalGlassLayer
import com.styropyr0.prismal.sources.rememberPrismalGlassLayer

private val Black = Color(0xFF000000)
private val Blue = Color(0xFF2196F3)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = android.graphics.Color.BLACK
        window.navigationBarColor = android.graphics.Color.BLACK
        setContent { MaterialTheme { HidenGramHome() } }
    }
}

private data class Chat(val name: String, val message: String, val time: String, val initials: String, val color: Color)
private val chats = listOf(
    Chat("Александр", "Ты уже посмотрел обновление?", "12:48", "А", Color(0xFF34495E)),
    Chat("Избранное", "Заметки и сохранённые сообщения", "12:31", "★", Color(0xFF455A64)),
    Chat("Дизайн-команда", "Макет отправил, гляньте", "11:56", "ДК", Color(0xFF51436D)),
    Chat("Максим", "Окей, договорились", "11:20", "М", Color(0xFF395C52)),
    Chat("Разработка", "Новая сборка готова", "Вчера", "Р", Color(0xFF5C4938)),
    Chat("Илья", "Спасибо!", "Вчера", "И", Color(0xFF3C526D)),
    Chat("Новости", "Сегодня вышел новый пост", "Вт", "Н", Color(0xFF60464B)),
    Chat("Артём", "Буду через 10 минут", "Пн", "А", Color(0xFF465A4A)),
    Chat("Тестовый чат 09", "Это демонстрационный диалог", "Пн", "09", Color(0xFF4D4D5C)),
    Chat("Тестовый чат 10", "Сообщения пока не подключены", "Вс", "10", Color(0xFF5B5140))
)

@Composable
private fun HidenGramHome() {
    var selected by remember { mutableIntStateOf(0) }
    val backdrop = rememberPrismalGlassLayer()
    Box(Modifier.fillMaxSize().background(Black)) {
        Column(
            Modifier.fillMaxSize()
                .prismalGlassLayer(backdrop)
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
        ) {
            Text("Чаты", Modifier.padding(start = 22.dp, top = 18.dp, bottom = 16.dp),
                color = Color.White, fontSize = 29.sp, fontWeight = FontWeight.Bold)
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 112.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) { items(chats) { ChatRow(it) } }
        }
        PrismalGlassBottomTabs(
            selectedTabIndex = { selected },
            onTabSelected = { selected = it },
            backdrop = backdrop,
            tabsCount = 4,
            modifier = Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth().padding(horizontal = 18.dp)
                .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 8.dp),
            tintDropletContent = true,
            dropletContentTint = Blue,
            blurRadius = 5.dp,
            refractionHeight = 17.dp,
            refractionAmount = 30.dp,
            dropletRefractionHeight = 15.dp,
            dropletRefractionAmount = 18.dp,
            dropletChromaticAberration = 0.32f,
            dropletContentScaleX = 0.94f,
            dropletContentScaleY = 0.94f,
            pressGlow = 0.55f,
            renderWithLowOverhead = false,
            hapticFeedback = true
        ) {
            Tab(Icons.Filled.ChatBubble, "Чаты") { selected = 0 }
            Tab(Icons.Filled.Contacts, "Контакты") { selected = 1 }
            Tab(Icons.Filled.Settings, "Настройки") { selected = 2 }
            Tab(Icons.Filled.Person, "Профиль") { selected = 3 }
        }
    }
}

@Composable
private fun RowScope.Tab(icon: ImageVector, title: String, click: () -> Unit) {
    PrismalGlassBottomTab(onClick = click) {
        Icon(icon, contentDescription = title, tint = Color.White, modifier = Modifier.size(27.dp))
        Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
    }
}

@Composable
private fun ChatRow(chat: Chat) {
    Row(Modifier.fillMaxWidth().height(76.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(54.dp).clip(CircleShape).background(chat.color), contentAlignment = Alignment.Center) {
            Text(chat.initials, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(13.dp))
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Text(chat.name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(5.dp))
            Text(chat.message, color = Color(0xFF85858B), fontSize = 13.sp, maxLines = 1)
        }
        Text(chat.time, color = Color(0xFF85858B), fontSize = 11.sp)
    }
}

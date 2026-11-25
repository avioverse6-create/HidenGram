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
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.styropyr0.prismal.components.LocalPrismalBottomTabHighlightedIndex
import com.styropyr0.prismal.components.PrismalGlassBottomTab
import com.styropyr0.prismal.components.PrismalGlassBottomTabs
import com.styropyr0.prismal.sources.prismalGlassLayer
import com.styropyr0.prismal.sources.rememberPrismalGlassLayer

private val Black = Color(0xFF000000)
private val AccentBlue = Color(0xFF2997FF)
private val HidenDark = darkColorScheme(
    primary = AccentBlue,
    background = Black,
    surface = Color(0xFF111114),
    onBackground = Color.White,
    onSurface = Color.White
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = android.graphics.Color.BLACK
        window.navigationBarColor = android.graphics.Color.BLACK
        window.decorView.systemUiVisibility = 0
        setContent { MaterialTheme(colorScheme = HidenDark) { HidenGramHome() } }
    }
}

private data class Chat(
    val name: String,
    val message: String,
    val time: String,
    val initials: String,
    val color: Color
)

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
        // Prismal's documented backdrop pattern: capture the actual screen
        // content behind the glass, then render its original tab component above it.
        Column(
            Modifier
                .fillMaxSize()
                .prismalGlassLayer(backdrop)
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
        ) {
            Text(
                "Чаты",
                Modifier.padding(start = 22.dp, top = 18.dp, bottom = 16.dp),
                color = Color.White,
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 124.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(chats) { ChatRow(it) }
            }
        }

        // Use PrismalAGSL's complete, unmodified bottom-tabs implementation
        // with its documented defaults: no custom overlay, tint or replacement lens.
        PrismalGlassBottomTabs(
            selectedTabIndex = { selected },
            onTabSelected = { selected = it },
            backdrop = backdrop,
            tabsCount = 4,
            tintDropletContent = false,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(
                    bottom = WindowInsets.navigationBars.asPaddingValues()
                        .calculateBottomPadding() + 8.dp
                )
        ) {
            Tab(index = 0, icon = Icons.Filled.ChatBubble, title = "Чаты") { selected = 0 }
            Tab(index = 1, icon = Icons.Filled.Contacts, title = "Контакты") { selected = 1 }
            Tab(index = 2, icon = Icons.Filled.Settings, title = "Настройки") { selected = 2 }
            Tab(index = 3, icon = Icons.Filled.Person, title = "Профиль") { selected = 3 }
        }
    }
}

@Composable
private fun RowScope.Tab(index: Int, icon: ImageVector, title: String, click: () -> Unit) {
    val highlightedIndex = LocalPrismalBottomTabHighlightedIndex.current()
    val selected = highlightedIndex == index
    val contentColor = if (selected) Color.White else Color(0xFFB8B8BD)

    PrismalGlassBottomTab(onClick = click) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = contentColor,
            modifier = Modifier.size(25.dp)
        )
        Text(
            text = title,
            color = contentColor,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Clip,
            letterSpacing = (-0.25).sp,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 1.dp)
        )
    }
}

@Composable
private fun ChatRow(chat: Chat) {
    Row(
        Modifier.fillMaxWidth().height(76.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier.size(54.dp).clip(CircleShape).background(chat.color),
            contentAlignment = Alignment.Center
        ) {
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

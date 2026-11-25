
package com.ultone.assistant.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.sqrt
import kotlin.random.Random

private val Gold = Color(0xFFD9AE50)
private val Dark = Color(0xFF080B14)
private val Wall = Color(0xFF68778A)
private val Floor = Color(0xFF34445A)
private val Floor2 = Color(0xFF26364B)
private val Wood = Color(0xFF96643D)
private val Cyan = Color(0xFF24DFFF)

private data class HouseAgent(
    val name: String,
    val color: Color,
    val room: Int,
    val x: Float,
    val y: Float,
    val tx: Float,
    val ty: Float
)

private val teamColors = listOf(
    Color(0xFF398DFF),
    Color(0xFF398DFF),
    Color(0xFF42D17A),
    Color(0xFF42D17A),
    Color(0xFFBD65FF),
    Color(0xFFBD65FF),
    Color(0xFFFFA044),
    Color(0xFFFFA044),
    Color(0xFFFF555F),
    Color(0xFFFF555F)
)

private val agentNames = listOf(
    "ORION", "AYRA",
    "BYTE", "NOVA",
    "PIXEL", "MIRA",
    "ECHO", "ZIVA",
    "TITAN", "ZARA"
)

@Composable
fun AgentsScreen() {

    val agents = remember {
        mutableStateListOf<HouseAgent>().apply {
            for (i in 0 until 10) {
                val room = i % 3
                val p = randomWalkPosition(room)
                add(
                    HouseAgent(
                        name = agentNames[i],
                        color = teamColors[i],
                        room = room,
                        x = p.first,
                        y = p.second,
                        tx = p.first,
                        ty = p.second
                    )
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(55)

            for (i in agents.indices) {
                val a = agents[i]
                val dx = a.tx - a.x
                val dy = a.ty - a.y
                val distance = sqrt(dx * dx + dy * dy)

                if (distance < 0.004f) {
                    val next = randomWalkPosition(a.room)
                    agents[i] = a.copy(
                        tx = next.first,
                        ty = next.second
                    )
                } else {
                    val speed = 0.0013f
                    agents[i] = a.copy(
                        x = a.x + dx / distance * speed,
                        y = a.y + dy / distance * speed
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark)
            .padding(horizontal = 8.dp, vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "ULTRON x LUNA",
                color = Gold,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "VIRTUAL AI HOUSE",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "10 AGENTS  •  ONLINE",
                color = Cyan,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF111C2B))
        ) {
            Canvas(Modifier.fillMaxSize()) {
                drawHouseMap()

                agents.forEach { agent ->
                    drawPixelAgent(
                        x = agent.x * size.width,
                        y = agent.y * size.height,
                        color = agent.color
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomItem("AGENTS", Cyan)
            BottomItem("TASKS", Gold)
            BottomItem("INVENTORY", Color.White)
            BottomItem("SETTINGS", Color.White)
        }
    }
}

@Composable
private fun BottomItem(
    title: String,
    color: Color
) {
    Text(
        text = title,
        color = color,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold
    )
}

private fun randomWalkPosition(room: Int): Pair<Float, Float> {
    return when (room) {
        0 -> Pair(
            Random.nextFloat() * 0.39f + 0.06f,
            Random.nextFloat() * 0.10f + 0.12f
        )

        1 -> Pair(
            Random.nextFloat() * 0.39f + 0.55f,
            Random.nextFloat() * 0.10f + 0.12f
        )

        else -> Pair(
            Random.nextFloat() * 0.38f + 0.06f,
            Random.nextFloat() * 0.12f + 0.70f
        )
    }
}

private fun DrawScope.drawHouseMap() {
    val w = size.width
    val h = size.height

    fun x(v: Float) = v * w
    fun y(v: Float) = v * h

    fun rect(
        color: Color,
        left: Float,
        top: Float,
        width: Float,
        height: Float
    ) {
        drawRect(
            color = color,
            topLeft = Offset(x(left), y(top)),
            size = Size(x(width), y(height))
        )
    }

    // Outer walls
    rect(Wall, 0.025f, 0.035f, 0.95f, 0.93f)
    rect(Floor, 0.035f, 0.05f, 0.93f, 0.90f)

    // Work room
    rect(Floor2, 0.045f, 0.06f, 0.445f, 0.43f)

    // Sleeping room
    rect(Floor2, 0.51f, 0.06f, 0.445f, 0.43f)

    // Relax room
    rect(Floor2, 0.045f, 0.53f, 0.445f, 0.40f)

    // Corridor and entrance
    rect(Color(0xFF8C6747), 0.51f, 0.53f, 0.435f, 0.40f)
    rect(Color(0xFFB78A5D), 0.52f, 0.55f, 0.415f, 0.36f)

    // Room divider walls with door openings
    rect(Wall, 0.49f, 0.05f, 0.018f, 0.17f)
    rect(Wall, 0.49f, 0.29f, 0.018f, 0.20f)

    rect(Wall, 0.045f, 0.495f, 0.18f, 0.025f)
    rect(Wall, 0.29f, 0.495f, 0.20f, 0.025f)

    rect(Wall, 0.51f, 0.495f, 0.17f, 0.025f)
    rect(Wall, 0.74f, 0.495f, 0.205f, 0.025f)

    // Work room title
    pixelText("WORK ROOM", x(0.265f), y(0.085f), 9f)

    // 10 workstations: 5 columns x 2 rows
    for (row in 0..1) {
        for (col in 0..4) {
            val sx = 0.075f + col * 0.080f
            val sy = 0.13f + row * 0.17f

            drawComputer(x(sx), y(sy), x(0.060f), y(0.075f))

            rect(
                Wood,
                sx - 0.004f,
                sy + 0.078f,
                0.067f,
                0.022f
            )

            // Chair
            rect(
                Color(0xFF65758B),
                sx + 0.018f,
                sy + 0.105f,
                0.025f,
                0.025f
            )
        }
    }

    // Storage cabinets
    rect(Wood, 0.435f, 0.14f, 0.035f, 0.12f)
    rect(Color(0xFFB78A5D), 0.438f, 0.15f, 0.029f, 0.045f)
    rect(Color(0xFFB78A5D), 0.438f, 0.205f, 0.029f, 0.045f)

    // Holographic research display
    rect(Color(0xFF102D43), 0.425f, 0.31f, 0.05f, 0.08f)
    rect(Cyan, 0.43f, 0.32f, 0.04f, 0.055f)

    // Sleeping room
    pixelText("SLEEPING ROOM", x(0.73f), y(0.085f), 9f)

    // 10 beds: 5 columns x 2 rows
    for (row in 0..1) {
        for (col in 0..4) {
            val bx = 0.535f + col * 0.080f
            val by = 0.13f + row * 0.17f
            drawBed(x(bx), y(by))

            // Bedside locker
            rect(
                Wood,
                bx + 0.054f,
                by + 0.015f,
                0.020f,
                0.035f
            )

            // Night lamp
            rect(
                Color(0xFFFFD76A),
                bx + 0.060f,
                by + 0.020f,
                0.008f,
                0.008f
            )
        }
    }

    // Wardrobe and clock
    rect(Wood, 0.90f, 0.34f, 0.035f, 0.12f)
    rect(Color(0xFF17243A), 0.91f, 0.35f, 0.015f, 0.09f)
    rect(Color(0xFFFFD76A), 0.87f, 0.08f, 0.035f, 0.035f)

    // Relax room title
    pixelText("RELAX ROOM", x(0.265f), y(0.565f), 9f)

    // Two sofas
    drawSofa(x(0.09f), y(0.64f))
    drawSofa(x(0.30f), y(0.64f))

    // TV
    rect(Color(0xFF101820), 0.18f, 0.83f, 0.14f, 0.055f)
    rect(Cyan, 0.19f, 0.838f, 0.12f, 0.038f)

    // Arcade
    rect(Color(0xFF663A91), 0.07f, 0.78f, 0.065f, 0.11f)
    rect(Color(0xFFBD65FF), 0.078f, 0.79f, 0.049f, 0.045f)

    // Aquarium
    rect(Color(0xFF193D55), 0.37f, 0.78f, 0.085f, 0.10f)
    rect(Color(0xFF24DFFF), 0.38f, 0.795f, 0.065f, 0.065f)

    // Bookshelf
    rect(Wood, 0.075f, 0.59f, 0.13f, 0.035f)
    rect(Color(0xFFFFD76A), 0.085f, 0.595f, 0.018f, 0.025f)
    rect(Color(0xFFBD65FF), 0.11f, 0.595f, 0.018f, 0.025f)
    rect(Color(0xFF24DFFF), 0.14f, 0.595f, 0.018f, 0.025f)

    // Refreshment corner
    rect(Wood, 0.35f, 0.59f, 0.10f, 0.05f)
    rect(Color(0xFF75E6A5), 0.37f, 0.575f, 0.025f, 0.02f)

    // Corridor title and entrance
    pixelText("MAIN CORRIDOR", x(0.73f), y(0.565f), 9f)

    // Crown emblem
    drawCrown(x(0.70f), y(0.65f))

    // Task board
    rect(Color(0xFF182B3C), 0.80f, 0.61f, 0.11f, 0.09f)
    rect(Cyan, 0.81f, 0.62f, 0.09f, 0.012f)
    rect(Color(0xFF75E6A5), 0.81f, 0.645f, 0.07f, 0.008f)
    rect(Color(0xFFFFD76A), 0.81f, 0.665f, 0.08f, 0.008f)

    // Charging station
    rect(Color(0xFF34445A), 0.56f, 0.77f, 0.09f, 0.09f)
    rect(Cyan, 0.575f, 0.79f, 0.06f, 0.012f)
    rect(Cyan, 0.575f, 0.815f, 0.06f, 0.012f)

    // Storage
    rect(Wood, 0.83f, 0.77f, 0.09f, 0.11f)
    rect(Color(0xFFB78A5D), 0.84f, 0.78f, 0.07f, 0.04f)
    rect(Color(0xFFB78A5D), 0.84f, 0.83f, 0.07f, 0.04f)

    // Entrance door
    rect(Color(0xFF5B3928), 0.69f, 0.89f, 0.11f, 0.035f)
    rect(Gold, 0.74f, 0.90f, 0.012f, 0.012f)

    // Plants
    drawPlant(x(0.535f), y(0.60f))
    drawPlant(x(0.91f), y(0.60f))

    // Decorative floor tiles
    for (i in 0..7) {
        rect(
            Color(0x2289A9C8),
            0.54f + i * 0.045f,
            0.73f,
            0.025f,
            0.008f
        )
    }
}

private fun DrawScope.drawComputer(
    x: Float,
    y: Float,
    width: Float,
    height: Float
) {
    drawRect(
        Color(0xFF101827),
        Offset(x, y),
        Size(width, height)
    )

    drawRect(
        Color(0xFF24DFFF),
        Offset(x + width * 0.10f, y + height * 0.10f),
        Size(width * 0.80f, height * 0.60f)
    )

    drawRect(
        Color(0xFFCBD5E1),
        Offset(x + width * 0.40f, y + height * 0.73f),
        Size(width * 0.20f, height * 0.12f)
    )
}

private fun DrawScope.drawBed(x: Float, y: Float) {
    drawRect(
        Wood,
        Offset(x, y),
        Size(size.width * 0.065f, size.height * 0.12f)
    )

    drawRect(
        Color(0xFFE2E8F0),
        Offset(x + size.width * 0.005f, y + size.height * 0.005f),
        Size(size.width * 0.055f, size.height * 0.035f)
    )

    drawRect(
        Color(0xFF526D91),
        Offset(x + size.width * 0.005f, y + size.height * 0.045f),
        Size(size.width * 0.055f, size.height * 0.065f)
    )
}

private fun DrawScope.drawSofa(x: Float, y: Float) {
    drawRect(
        Color(0xFF754B91),
        Offset(x, y),
        Size(size.width * 0.13f, size.height * 0.12f)
    )

    drawRect(
        Color(0xFFB77BC7),
        Offset(x + size.width * 0.012f, y + size.height * 0.015f),
        Size(size.width * 0.106f, size.height * 0.055f)
    )

    drawRect(
        Color(0xFF56366F),
        Offset(x + size.width * 0.015f, y + size.height * 0.08f),
        Size(size.width * 0.10f, size.height * 0.025f)
    )
}

private fun DrawScope.drawPlant(x: Float, y: Float) {
    drawRect(
        Wood,
        Offset(x, y + size.height * 0.035f),
        Size(size.width * 0.035f, size.height * 0.035f)
    )

    drawRect(
        Color(0xFF42D17A),
        Offset(x + size.width * 0.005f, y),
        Size(size.width * 0.025f, size.height * 0.045f)
    )
}

private fun DrawScope.drawCrown(x: Float, y: Float) {
    val gold = Color(0xFFD9AE50)

    drawRect(
        gold,
        Offset(x, y + size.height * 0.035f),
        Size(size.width * 0.065f, size.height * 0.025f)
    )

    drawRect(
        gold,
        Offset(x + size.width * 0.008f, y + size.height * 0.012f),
        Size(size.width * 0.012f, size.height * 0.025f)
    )

    drawRect(
        gold,
        Offset(x + size.width * 0.027f, y),
        Size(size.width * 0.012f, size.height * 0.035f)
    )

    drawRect(
        gold,
        Offset(x + size.width * 0.047f, y + size.height * 0.012f),
        Size(size.width * 0.012f, size.height * 0.025f)
    )
}

private fun DrawScope.drawPixelAgent(
    x: Float,
    y: Float,
    color: Color
) {
    val u = size.width * 0.007f

    // Shadow
    drawRect(
        Color(0x88000000),
        Offset(x - u, y + u * 4),
        Size(u * 5, u * 2)
    )

    // Head
    drawRect(
        color,
        Offset(x, y - u * 3),
        Size(u * 4, u * 3)
    )

    // Face
    drawRect(
        Color(0xFFFFD8B1),
        Offset(x + u, y - u * 2),
        Size(u * 2, u * 2)
    )

    // Body
    drawRect(
        color,
        Offset(x + u * 0.5f, y),
        Size(u * 3, u * 3)
    )

    // Legs
    drawRect(
        Color(0xFF202A3A),
        Offset(x + u, y + u * 3),
        Size(u, u * 2)
    )

    drawRect(
        Color(0xFF202A3A),
        Offset(x + u * 2.5f, y + u * 3),
        Size(u, u * 2)
    )
}

private fun DrawScope.pixelText(
    text: String,
    centerX: Float,
    centerY: Float,
    textSize: Float
) {
    drawContext.canvas.nativeCanvas.drawText(
        text,
        centerX,
        centerY,
        android.graphics.Paint().apply {
            color = android.graphics.Color.WHITE
            this.textSize = textSize * (size.width / 400f)
            textAlign = android.graphics.Paint.Align.CENTER
            typeface = android.graphics.Typeface.create(
                "monospace",
                android.graphics.Typeface.BOLD
            )
            isAntiAlias = false
        }
    )
}

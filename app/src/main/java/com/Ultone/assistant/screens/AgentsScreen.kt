
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.sqrt
import kotlin.random.Random

private val AgentGold = Color(0xFFD9AE50)
private val AgentDark = Color(0xFF080B14)
private val AgentCyan = Color(0xFF24DFFF)
private val AgentPurple = Color(0xFFBD65FF)

private val Floor = Color(0xFF263447)
private val FloorDark = Color(0xFF1B293D)
private val Wall = Color(0xFF64748B)
private val Wood = Color(0xFF98683D)
private val Furniture = Color(0xFF40536B)
private val ScreenGlow = Color(0xFF24DFFF)

private data class HouseAgent(
    val name: String,
    val color: Color,
    val x: Float,
    val y: Float,
    val targetX: Float,
    val targetY: Float,
    val room: Int
)

@Composable
fun AgentsScreen() {

    val agents = remember {
        mutableStateListOf(
            HouseAgent(
                "RESEARCH",
                AgentCyan,
                0.27f, 0.25f,
                0.34f, 0.30f,
                0
            ),
            HouseAgent(
                "WRITER",
                AgentPurple,
                0.70f, 0.25f,
                0.76f, 0.30f,
                0
            ),
            HouseAgent(
                "CREATIVE",
                Color(0xFFFFC66D),
                0.25f, 0.73f,
                0.32f, 0.80f,
                1
            ),
            HouseAgent(
                "HELPER",
                Color(0xFF75E6A5),
                0.73f, 0.73f,
                0.78f, 0.80f,
                2
            )
        )
    }

    // Random movement prototype
    LaunchedEffect(Unit) {
        while (true) {
            delay(65)

            for (i in agents.indices) {
                val agent = agents[i]

                val dx = agent.targetX - agent.x
                val dy = agent.targetY - agent.y
                val distance = sqrt(dx * dx + dy * dy)

                if (distance < 0.009f) {
                    val (newX, newY) = randomPosition(agent.room)

                    agents[i] = agent.copy(
                        targetX = newX,
                        targetY = newY
                    )
                } else {
                    val speed = 0.0025f
                    agents[i] = agent.copy(
                        x = agent.x + (dx / distance) * speed,
                        y = agent.y + (dy / distance) * speed
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AgentDark)
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {

        // HEADER
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "AGENT HEADQUARTERS",
                color = AgentGold,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Text(
                text = "Your AI agents are living here",
                color = Color.LightGray,
                fontSize = 11.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // HOUSE MAP
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF101A29))
        ) {

            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {

                drawHouse()

                agents.forEach { agent ->
                    drawAgent(
                        x = agent.x * size.width,
                        y = agent.y * size.height,
                        color = agent.color
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(9.dp))

        // AGENT STATUS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AgentStatus("4 AGENTS", AgentCyan)
            AgentStatus("RANDOM ACTIVITY", AgentPurple)
            AgentStatus("ONLINE", Color(0xFF75E6A5))
        }

        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
private fun AgentStatus(
    text: String,
    color: Color
) {
    Text(
        text = text,
        color = color,
        fontSize = 9.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp
    )
}

private fun randomPosition(room: Int): Pair<Float, Float> {

    return when (room) {

        // Work Room
        0 -> Pair(
            Random.nextFloat() * 0.60f + 0.18f,
            Random.nextFloat() * 0.20f + 0.17f
        )

        // Sleeping Room
        1 -> Pair(
            Random.nextFloat() * 0.28f + 0.12f,
            Random.nextFloat() * 0.19f + 0.68f
        )

        // Relax Room
        else -> Pair(
            Random.nextFloat() * 0.28f + 0.59f,
            Random.nextFloat() * 0.19f + 0.68f
        )
    }
}

private fun DrawScope.drawHouse() {

    val w = size.width
    val h = size.height

    fun px(x: Float) = x * w
    fun py(y: Float) = y * h

    // Outer floor
    drawRect(
        color = Wall,
        topLeft = Offset(px(0.055f), py(0.065f)),
        size = Size(px(0.89f), py(0.87f))
    )

    drawRect(
        color = Floor,
        topLeft = Offset(px(0.07f), py(0.08f)),
        size = Size(px(0.86f), py(0.84f))
    )

    // WORK ROOM
    drawRect(
        color = FloorDark,
        topLeft = Offset(px(0.085f), py(0.095f)),
        size = Size(px(0.83f), py(0.31f))
    )

    // Corridor
    drawRect(
        color = Wood,
        topLeft = Offset(px(0.085f), py(0.415f)),
        size = Size(px(0.83f), py(0.15f))
    )

    drawRect(
        color = Color(0xFFB1804D),
        topLeft = Offset(px(0.095f), py(0.425f)),
        size = Size(px(0.81f), py(0.13f)
        )
    )

    // Sleeping Room
    drawRect(
        color = FloorDark,
        topLeft = Offset(px(0.085f), py(0.575f)),
        size = Size(px(0.395f), py(0.33f))
    )

    // Relax Room
    drawRect(
        color = FloorDark,
        topLeft = Offset(px(0.52f), py(0.575f)),
        size = Size(px(0.395f), py(0.33f))
    )

    // Work Room Furniture: computers
    drawComputer(px(0.17f), py(0.17f), px(0.105f), py(0.075f))
    drawComputer(px(0.39f), py(0.17f), px(0.105f), py(0.075f))
    drawComputer(px(0.66f), py(0.17f), px(0.105f), py(0.075f))

    // Work desks
    drawRect(
        color = Wood,
        topLeft = Offset(px(0.15f), py(0.255f)),
        size = Size(px(0.14f), py(0.035f))
    )
    drawRect(
        color = Wood,
        topLeft = Offset(px(0.37f), py(0.255f)),
        size = Size(px(0.14f), py(0.035f))
    )
    drawRect(
        color = Wood,
        topLeft = Offset(px(0.64f), py(0.255f)),
        size = Size(px(0.14f), py(0.035f))
    )

    // Sleeping beds
    drawBed(px(0.13f), py(0.64f))
    drawBed(px(0.30f), py(0.64f))

    // Relax room sofa
    drawRect(
        color = Color(0xFF8B5B9B),
        topLeft = Offset(px(0.62f), py(0.66f)),
        size = Size(px(0.21f), py(0.075f))
    )

    drawRect(
        color = Color(0xFFB77BC7),
        topLeft = Offset(px(0.64f), py(0.67f)),
        size = Size(px(0.17f), py(0.035f))
    )

    // TV
    drawRect(
        color = Color(0xFF101820),
        topLeft = Offset(px(0.67f), py(0.80f)),
        size = Size(px(0.12f), py(0.045f))
    )

    drawRect(
        color = ScreenGlow,
        topLeft = Offset(px(0.68f), py(0.805f)),
        size = Size(px(0.10f), py(0.025f))
    )

    // Coffee table
    drawRect(
        color = Wood,
        topLeft = Offset(px(0.68f), py(0.86f)),
        size = Size(px(0.10f), py(0.025f))
    )

    // Room labels
    drawPixelText("WORK ROOM", px(0.50f), py(0.115f), 10f)
    drawPixelText("CORRIDOR", px(0.50f), py(0.475f), 10f)
    drawPixelText("SLEEP", px(0.28f), py(0.61f), 9f)
    drawPixelText("RELAX", px(0.72f), py(0.61f), 9f)

    // Room dividing walls
    drawRect(
        color = Wall,
        topLeft = Offset(px(0.075f), py(0.405f)),
        size = Size(px(0.85f), py(0.008f))
    )

    drawRect(
        color = Wall,
        topLeft = Offset(px(0.075f), py(0.565f)),
        size = Size(px(0.85f), py(0.008f))
    )

    drawRect(
        color = Wall,
        topLeft = Offset(px(0.49f), py(0.575f)),
        size = Size(px(0.015f), py(0.33f))
    )
}

private fun DrawScope.drawComputer(
    x: Float,
    y: Float,
    width: Float,
    height: Float
) {

    drawRect(
        color = Color(0xFF111827),
        topLeft = Offset(x, y),
        size = Size(width, height)
    )

    drawRect(
        color = ScreenGlow,
        topLeft = Offset(x + width * 0.10f, y + height * 0.10f),
        size = Size(width * 0.80f, height * 0.58f)
    )

    drawRect(
        color = Color(0xFF101827),
        topLeft = Offset(x + width * 0.42f, y + height * 0.68f),
        size = Size(width * 0.16f, height * 0.17f)
    )
}

private fun DrawScope.drawBed(
    x: Float,
    y: Float
) {

    drawRect(
        color = Wood,
        topLeft = Offset(x, y),
        size = Size(size.width * 0.115f, size.height * 0.15f)
    )

    drawRect(
        color = Color(0xFFCBD5E1),
        topLeft = Offset(x + size.width * 0.008f, y + size.height * 0.008f),
        size = Size(size.width * 0.099f, size.height * 0.05f)
    )

    drawRect(
        color = Color(0xFF526D91),
        topLeft = Offset(x + size.width * 0.008f, y + size.height * 0.062f),
        size = Size(size.width * 0.099f, size.height * 0.078f)
    )
}

private fun DrawScope.drawAgent(
    x: Float,
    y: Float,
    color: Color
) {

    val unit = size.width * 0.012f

    // Shadow
    drawRect(
        color = Color(0x66000000),
        topLeft = Offset(x - unit, y + unit * 2),
        size = Size(unit * 4, unit * 2)
    )

    // Head
    drawRect(
        color = color,
        topLeft = Offset(x, y - unit * 2),
        size = Size(unit * 3, unit * 3)
    )

    // Body
    drawRect(
        color = Color.White,
        topLeft = Offset(x + unit * 0.5f, y + unit),
        size = Size(unit * 2, unit * 2)
    )

    // Legs
    drawRect(
        color = color,
        topLeft = Offset(x + unit * 0.5f, y + unit * 3),
        size = Size(unit, unit * 2)
    )

    drawRect(
        color = color,
        topLeft = Offset(x + unit * 1.8f, y + unit * 3),
        size = Size(unit, unit * 2)
    )
}

private fun DrawScope.drawPixelText(
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

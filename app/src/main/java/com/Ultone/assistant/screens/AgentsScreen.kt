
package com.ultone.assistant.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.sqrt
import kotlin.random.Random

private val HouseGold = Color(0xFFD9AE50)
private val HouseDark = Color(0xFF080B14)
private val HouseBlue = Color(0xFF24DFFF)
private val HouseFloor = Color(0xFF29415F)
private val HouseFloor2 = Color(0xFF1C304A)
private val HouseWall = Color(0xFF8294A9)
private val HouseWood = Color(0xFF98683D)

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

private data class HouseAgent(
    val name: String,
    val color: Color,
    val room: Int,
    val x: Float,
    val y: Float,
    val tx: Float,
    val ty: Float
)

private val agentNames = listOf(
    "ORION", "AYRA",
    "BYTE", "NOVA",
    "PIXEL", "MIRA",
    "ECHO", "ZIVA",
    "TITAN", "ZARA"
)

private val teams = listOf(
    Triple("KNOWLEDGE DUO", "ORION  •  AYRA", Color(0xFF398DFF)),
    Triple("TECH TITANS", "BYTE  •  NOVA", Color(0xFF42D17A)),
    Triple("CREATIVE STUDIO", "PIXEL  •  MIRA", Color(0xFFBD65FF)),
    Triple("SOCIAL FORCE", "ECHO  •  ZIVA", Color(0xFFFFA044)),
    Triple("LIFE GUARDIANS", "TITAN  •  ZARA", Color(0xFFFF555F))
)

private fun bedPosition(index: Int): Pair<Float, Float> {
    val col = index % 5
    val row = index / 5
    return Pair(
        0.565f + col * 0.078f,
        if (row == 0) 0.19f else 0.35f
    )
}

private fun randomHousePosition(room: Int): Pair<Float, Float> {
    return when (room) {
        0 -> Pair(
            Random.nextFloat() * 0.35f + 0.08f,
            Random.nextFloat() * 0.27f + 0.13f
        )
        1 -> Pair(
            Random.nextFloat() * 0.34f + 0.56f,
            Random.nextFloat() * 0.25f + 0.13f
        )
        2 -> Pair(
            Random.nextFloat() * 0.35f + 0.08f,
            Random.nextFloat() * 0.22f + 0.65f
        )
        else -> Pair(
            Random.nextFloat() * 0.34f + 0.56f,
            Random.nextFloat() * 0.22f + 0.65f
        )
    }
}

@Composable
fun AgentsScreen() {

    var sleepMode by remember { mutableStateOf(false) }

    val agents = remember {
        mutableStateListOf<HouseAgent>().apply {
            for (i in 0 until 10) {
                val room = i % 4
                val position = randomHousePosition(room)

                add(
                    HouseAgent(
                        name = agentNames[i],
                        color = teamColors[i],
                        room = room,
                        x = position.first,
                        y = position.second,
                        tx = position.first,
                        ty = position.second
                    )
                )
            }
        }
    }

    val allSleeping = sleepMode && agents.all {
        val dx = it.tx - it.x
        val dy = it.ty - it.y
        sqrt(dx * dx + dy * dy) < 0.009f
    }

    LaunchedEffect(sleepMode) {
        while (true) {
            delay(55)

            for (i in agents.indices) {
                val agent = agents[i]

                val dx = agent.tx - agent.x
                val dy = agent.ty - agent.y
                val distance = sqrt(dx * dx + dy * dy)

                if (distance < 0.009f) {

                    if (!sleepMode) {
                        val next = randomHousePosition(agent.room)
                        agents[i] = agent.copy(
                            tx = next.first,
                            ty = next.second
                        )
                    }

                } else {
                    val speed = 0.0015f

                    agents[i] = agent.copy(
                        x = agent.x + dx / distance * speed,
                        y = agent.y + dy / distance * speed
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HouseDark)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {

        // HEADER

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(43.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "☰",
                color = HouseGold,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "ULTRON × LUNA",
                    color = HouseGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "VIRTUAL AI HOUSE",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Text(
                text = "⚙",
                color = HouseGold,
                fontSize = 25.sp
            )
        }

        Spacer(Modifier.height(2.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(HouseGold.copy(alpha = 0.8f))
        )

        Spacer(Modifier.height(7.dp))

        // LANDSCAPE HOUSE MAP

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(9.dp))
                .background(Color(0xFF111C2B))
        ) {
            Canvas(Modifier.fillMaxSize()) {

                drawPixelHouse(
                    bedroomDim = allSleeping
                )

                agents.forEachIndexed { index, agent ->

                    val dx = agent.tx - agent.x
                    val dy = agent.ty - agent.y
                    val sleeping = sleepMode &&
                        sqrt(dx * dx + dy * dy) < 0.009f

                    drawHouseCharacter(
                        x = agent.x * size.width,
                        y = agent.y * size.height,
                        color = agent.color,
                        sleeping = sleeping
                    )
                }
            }
        }

        Spacer(Modifier.height(7.dp))

        // SLEEP / WAKE BUTTON

        val buttonText = when {
            allSleeping -> "☀   WAKE ALL AGENTS"
            sleepMode -> "☾   AGENTS GOING TO BED..."
            else -> "☾   SLEEP ALL AGENTS"
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(43.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (allSleeping) Color(0xFF244C70)
                    else Color(0xFF173653)
                )
                .clickable {
                    if (sleepMode) {
                        sleepMode = false

                        agents.indices.forEach { i ->
                            val a = agents[i]
                            val next = randomHousePosition(a.room)
                            agents[i] = a.copy(
                                tx = next.first,
                                ty = next.second
                            )
                        }

                    } else {
                        sleepMode = true

                        agents.indices.forEach { i ->
                            val a = agents[i]
                            val bed = bedPosition(i)

                            agents[i] = a.copy(
                                tx = bed.first,
                                ty = bed.second
                            )
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                color = HouseGold,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.7.sp
            )
        }

        Spacer(Modifier.height(7.dp))

        // TEAM INFORMATION

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "OUR 5 TEAMS",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = if (allSleeping) "ALL ASLEEP"
                else if (sleepMode) "GOING TO SLEEP"
                else "10 AGENTS",
                color = if (allSleeping) HouseGold else HouseBlue,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(5.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            teams.forEachIndexed { index, team ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(7.dp))
                        .background(Color(0xFF142238))
                        .padding(horizontal = 10.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(team.third)
                    )

                    Spacer(Modifier.width(9.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = team.first,
                            color = team.third,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = team.second,
                            color = Color.LightGray,
                            fontSize = 9.sp
                        )
                    }

                    Text(
                        text = "2 AGENTS",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// --------------------------------------------------
// GBA STYLE PIXEL HOUSE MAP
// --------------------------------------------------

private fun DrawScope.drawPixelHouse(
    bedroomDim: Boolean
) {
    val w = size.width
    val h = size.height

    fun px(v: Float) = v * w
    fun py(v: Float) = v * h

    fun rect(
        color: Color,
        x: Float,
        y: Float,
        width: Float,
        height: Float
    ) {
        drawRect(
            color = color,
            topLeft = Offset(px(x), py(y)),
            size = Size(px(width), py(height))
        )
    }

    // Outer wall

    rect(HouseWall, 0.015f, 0.02f, 0.97f, 0.96f)
    rect(HouseFloor, 0.025f, 0.035f, 0.95f, 0.93f)

    // Work Room

    rect(HouseFloor2, 0.035f, 0.05f, 0.445f, 0.43f)

    // Sleeping Room

    rect(HouseFloor2, 0.52f, 0.05f, 0.445f, 0.43f)

    // Relax Room

    rect(HouseFloor2, 0.035f, 0.53f, 0.445f, 0.42f)

    // Corridor / Entrance

    rect(Color(0xFF9A714B), 0.52f, 0.53f, 0.445f, 0.42f)
    rect(Color(0xFFB88B5D), 0.53f, 0.55f, 0.425f, 0.38f)

    // Room walls

    rect(HouseWall, 0.49f, 0.05f, 0.02f, 0.17f)
    rect(HouseWall, 0.49f, 0.29f, 0.02f, 0.19f)

    rect(HouseWall, 0.035f, 0.49f, 0.18f, 0.025f)
    rect(HouseWall, 0.29f, 0.49f, 0.19f, 0.025f)

    rect(HouseWall, 0.52f, 0.49f, 0.17f, 0.025f)
    rect(HouseWall, 0.75f, 0.49f, 0.215f, 0.025f)

    // Room labels

    pixelText("WORK ROOM", px(0.25f), py(0.10f), 9f)
    pixelText("SLEEPING ROOM", px(0.74f), py(0.10f), 9f)
    pixelText("RELAX ROOM", px(0.25f), py(0.58f), 9f)
    pixelText("MAIN CORRIDOR", px(0.74f), py(0.58f), 9f)

    // Exactly 10 computer desks

    for (row in 0..1) {
        for (col in 0..4) {
            val dx = 0.07f + col * 0.078f
            val dy = 0.15f + row * 0.16f

            // Monitor
            rect(
                Color(0xFF101827),
                dx, dy, 0.060f, 0.060f
            )

            rect(
                HouseBlue,
                dx + 0.006f,
                dy + 0.006f,
                0.048f,
                0.041f
            )

            // Monitor stand
            rect(
                Color.LightGray,
                dx + 0.024f,
                dy + 0.061f,
                0.012f,
                0.012f
            )

            // Desk
            rect(
                HouseWood,
                dx - 0.006f,
                dy + 0.078f,
                0.073f,
                0.023f
            )

            // Chair
            rect(
                Color(0xFF65758B),
                dx + 0.018f,
                dy + 0.11f,
                0.025f,
                0.025f
            )
        }
    }

    // Storage cabinet

    rect(HouseWood, 0.425f, 0.15f, 0.035f, 0.13f)

    // Holographic display

    rect(Color(0xFF102C43), 0.425f, 0.33f, 0.045f, 0.08f)
    rect(HouseBlue, 0.432f, 0.34f, 0.031f, 0.055f)

    // Exactly 10 beds

    for (row in 0..1) {
        for (col in 0..4) {
            val bx = 0.545f + col * 0.078f
            val by = 0.145f + row * 0.16f

            // Bed frame
            rect(
                HouseWood,
                bx, by, 0.064f, 0.125f
            )

            // Pillow
            rect(
                Color(0xFFE5EAF2),
                bx + 0.006f,
                by + 0.006f,
                0.052f,
                0.032f
            )

            // Blanket
            rect(
                Color(0xFF477FC2),
                bx + 0.006f,
                by + 0.044f,
                0.052f,
                0.074f
            )

            // Bedside locker
            rect(
                HouseWood,
                bx + 0.060f,
                by + 0.025f,
                0.015f,
                0.035f
            )
        }
    }

    // Bedroom clock and wardrobe

    rect(HouseGold, 0.90f, 0.10f, 0.035f, 0.035f)
    rect(HouseWood, 0.90f, 0.30f, 0.04f, 0.13f)

    // Bedroom light dimming happens only after everyone reaches bed

    if (bedroomDim) {
        rect(
            Color(0x99000000),
            0.52f, 0.05f, 0.445f, 0.43f
        )
    }

    // Relax Room furniture

    // Bookshelf
    rect(HouseWood, 0.07f, 0.64f, 0.14f, 0.045f)

    rect(Color(0xFFFFD76A), 0.08f, 0.65f, 0.018f, 0.025f)
    rect(Color(0xFFBD65FF), 0.11f, 0.65f, 0.018f, 0.025f)
    rect(Color(0xFF24DFFF), 0.14f, 0.65f, 0.018f, 0.025f)

    // Aquarium
    rect(Color(0xFF102B42), 0.29f, 0.62f, 0.16f, 0.12f)
    rect(Color(0xFF24DFFF), 0.30f, 0.63f, 0.14f, 0.09f)

    // Sofa 1
    drawSofa(px(0.09f), py(0.78f))

    // Sofa 2
    drawSofa(px(0.29f), py(0.78f))

    // TV
    rect(Color(0xFF101827), 0.18f, 0.88f, 0.14f, 0.05f)
    rect(HouseBlue, 0.19f, 0.89f, 0.12f, 0.03f)

    // Arcade
    rect(Color(0xFF683D91), 0.07f, 0.70f, 0.055f, 0.12f)
    rect(Color(0xFFBD65FF), 0.078f, 0.715f, 0.039f, 0.05f)

    // Main corridor crown

    drawCrown(px(0.72f), py(0.68f))

    // Task board

    rect(Color(0xFF14243A), 0.82f, 0.64f, 0.11f, 0.10f)
    rect(HouseBlue, 0.83f, 0.65f, 0.09f, 0.012f)
    rect(Color(0xFF75E6A5), 0.83f, 0.68f, 0.07f, 0.009f)
    rect(HouseGold, 0.83f, 0.71f, 0.08f, 0.009f)

    // Charging station

    rect(Color(0xFF34445A), 0.56f, 0.78f, 0.08f, 0.09f)
    rect(HouseBlue, 0.57f, 0.80f, 0.06f, 0.015f)

    // Storage

    rect(HouseWood, 0.84f, 0.80f, 0.08f, 0.11f)

    // Entrance

    rect(Color(0xFF593A29), 0.69f, 0.91f, 0.12f, 0.035f)
    rect(HouseGold, 0.745f, 0.92f, 0.012f, 0.012f)
}

private fun DrawScope.drawSofa(
    x: Float,
    y: Float
) {
    drawRect(
        Color(0xFF754B91),
        Offset(x, y),
        Size(size.width * 0.13f, size.height * 0.12f)
    )

    drawRect(
        Color(0xFFB77BC7),
        Offset(x + size.width * 0.01f, y + size.height * 0.015f),
        Size(size.width * 0.11f, size.height * 0.055f)
    )

    drawRect(
        Color(0xFF56366F),
        Offset(x + size.width * 0.015f, y + size.height * 0.08f),
        Size(size.width * 0.10f, size.height * 0.025f)
    )
}

private fun DrawScope.drawCrown(
    x: Float,
    y: Float
) {
    drawRect(
        HouseGold,
        Offset(x, y + size.height * 0.04f),
        Size(size.width * 0.07f, size.height * 0.025f)
    )

    drawRect(
        HouseGold,
        Offset(x + size.width * 0.01f, y + size.height * 0.015f),
        Size(size.width * 0.012f, size.height * 0.03f)
    )

    drawRect(
        HouseGold,
        Offset(x + size.width * 0.03f, y),
        Size(size.width * 0.012f, size.height * 0.04f)
    )

    drawRect(
        HouseGold,
        Offset(x + size.width * 0.05f, y + size.height * 0.015f),
        Size(size.width * 0.012f, size.height * 0.03f)
    )
}

// --------------------------------------------------
// PIXEL CHARACTER
// --------------------------------------------------

private fun DrawScope.drawHouseCharacter(
    x: Float,
    y: Float,
    color: Color,
    sleeping: Boolean
) {
    val u = size.width * 0.008f

    // Shadow
    drawRect(
        Color(0x88000000),
        Offset(x - u, y + u * 4),
        Size(u * 5, u * 2)
    )

    // Head / Hair
    drawRect(
        color,
        Offset(x, y - u * 4),
        Size(u * 4, u * 4)
    )

    // Face
    drawRect(
        Color(0xFFFFD8B1),
        Offset(x + u, y - u * 3),
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

    if (sleeping) {
        drawContext.canvas.nativeCanvas.drawText(
            "Z",
            x + u * 4,
            y - u * 5,
            android.graphics.Paint().apply {
                color = android.graphics.Color.WHITE
                textSize = u * 5
                typeface = android.graphics.Typeface.DEFAULT_BOLD
                isAntiAlias = false
            }
        )
    }
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

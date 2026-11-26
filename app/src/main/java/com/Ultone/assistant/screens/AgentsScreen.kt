
package com.ultone.assistant

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

private val HouseBackground = Color(0xFF080B14)
private val Gold = Color(0xFFD9AE50)
private val Cyan = Color(0xFF24DFFF)
private val Purple = Color(0xFFBD65FF)

private data class HouseAgent(
    val name: String,
    val team: Int,
    val color: Color,
    var x: Float,
    var y: Float,
    var targetX: Float,
    var targetY: Float,
    var sleeping: Boolean = false
)

private data class AgentTeam(
    val name: String,
    val color: Color,
    val members: String,
    val description: String
)

private val agentTeams = listOf(
    AgentTeam(
        "KNOWLEDGE DUO",
        Color(0xFF398BFF),
        "ORION • AYRA",
        "Research, fact-checking, Quran, Hadith, Tafsir and Islamic knowledge."
    ),
    AgentTeam(
        "TECH TITANS",
        Color(0xFF42D66B),
        "BYTE • NOVA",
        "Website, Android apps, coding, debugging, security and technical systems."
    ),
    AgentTeam(
        "CREATIVE STUDIO",
        Color(0xFFB56BFF),
        "PIXEL • MIRA",
        "AI images, graphic design, posters, animation and video creation."
    ),
    AgentTeam(
        "SOCIAL FORCE",
        Color(0xFFFF983D),
        "ECHO • ZIVA",
        "YouTube, Instagram, Reels, captions, hashtags and social media performance."
    ),
    AgentTeam(
        "LIFE GUARDIANS",
        Color(0xFFFF555D),
        "TITAN • ZARA",
        "Daily planning, reminders, fitness, exercise, nutrition and wellness."
    )
)

@Composable
fun AgentsScreen() {

    var allSleeping by remember { mutableStateOf(false) }

    val agents = remember {
        mutableStateListOf(
            HouseAgent("ORION", 0, Color(0xFF398BFF), 0.10f, 0.22f, 0.10f, 0.22f),
            HouseAgent("AYRA", 0, Color(0xFF398BFF), 0.25f, 0.22f, 0.25f, 0.22f),
            HouseAgent("BYTE", 1, Color(0xFF42D66B), 0.65f, 0.22f, 0.65f, 0.22f),
            HouseAgent("NOVA", 1, Color(0xFF42D66B), 0.80f, 0.22f, 0.80f, 0.22f),
            HouseAgent("PIXEL", 2, Color(0xFFB56BFF), 0.10f, 0.73f, 0.10f, 0.73f),
            HouseAgent("MIRA", 2, Color(0xFFB56BFF), 0.25f, 0.73f, 0.25f, 0.73f),
            HouseAgent("ECHO", 3, Color(0xFFFF983D), 0.65f, 0.73f, 0.65f, 0.73f),
            HouseAgent("ZIVA", 3, Color(0xFFFF983D), 0.80f, 0.73f, 0.80f, 0.73f),
            HouseAgent("TITAN", 4, Color(0xFFFF555D), 0.43f, 0.45f, 0.43f, 0.45f),
            HouseAgent("ZARA", 4, Color(0xFFFF555D), 0.55f, 0.45f, 0.55f, 0.45f)
        )
    }

    LaunchedEffect(allSleeping) {
        if (!allSleeping) {
            while (true) {
                agents.forEach { agent ->
                    val room = when (agent.team) {
                        0, 1 -> 0
                        2 -> 1
                        3, 4 -> 2
                        else -> 0
                    }

                    val bounds = when (room) {
                        0 -> listOf(0.04f, 0.43f, 0.08f, 0.42f)
                        1 -> listOf(0.57f, 0.95f, 0.08f, 0.42f)
                        else -> listOf(0.04f, 0.43f, 0.57f, 0.91f)
                    }

                    agent.targetX = Random.nextFloat()
                        .coerceIn(bounds[0], bounds[1])
                    agent.targetY = Random.nextFloat()
                        .coerceIn(bounds[2], bounds[3])
                }

                repeat(8) {
                    agents.forEach { agent ->
                        agent.x += (agent.targetX - agent.x) * 0.12f
                        agent.y += (agent.targetY - agent.y) * 0.12f
                    }
                    delay(100)
                }

                delay(Random.nextLong(500, 1300))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HouseBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 18.dp)
    ) {

        // HEADER

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Gold
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "ULTRON x LUNA",
                    color = Gold,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "VIRTUAL AI HOUSE",
                    color = Color.LightGray,
                    fontSize = 9.sp,
                    letterSpacing = 2.sp
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Gold
                )
            }
        }

        HorizontalDivider(color = Gold.copy(alpha = 0.45f))

        Spacer(Modifier.height(12.dp))

        // HOUSE TITLE

        Text(
            "OUR VIRTUAL WORLD",
            modifier = Modifier.fillMaxWidth(),
            color = Gold,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(Modifier.height(10.dp))

        // VIRTUAL HOUSE

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .aspectRatio(1.72f)
                .border(
                    2.dp,
                    Gold.copy(alpha = 0.8f),
                    RoundedCornerShape(8.dp)
                )
                .background(Color(0xFF151C2B))
        ) {

            PixelHouse(
                agents = agents,
                sleeping = allSleeping,
                modifier = Modifier.fillMaxSize()
            )

            Text(
                "WORK ROOM",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 8.dp, top = 5.dp),
                color = Color(0xFF75B9FF),
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "SLEEP ROOM",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp, top = 5.dp),
                color = Color(0xFFFFD47A),
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "RELAX ROOM",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 8.dp, bottom = 5.dp),
                color = Color(0xFFCA8CFF),
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "CORRIDOR",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 8.dp, bottom = 5.dp),
                color = Color(0xFF8DE8D5),
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(12.dp))

        // AGENT STATUS

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "AGENTS: 10",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                if (allSleeping) "ALL AGENTS SLEEPING" else "AGENTS ACTIVE",
                color = if (allSleeping) Gold else Color(0xFF55E89A),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(10.dp))

        // SLEEP BUTTON

        Button(
            onClick = {
                allSleeping = !allSleeping

                agents.forEachIndexed { index, agent ->
                    agent.sleeping = allSleeping

                    if (allSleeping) {
                        agent.targetX = 0.64f + (index % 5) * 0.065f
                        agent.targetY = 0.15f + (index / 5) * 0.16f
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (allSleeping) Color(0xFF39445C)
                else Color(0xFF263C59)
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                if (allSleeping) "WAKE UP ALL AGENTS"
                else "SEND ALL AGENTS TO SLEEP",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        Spacer(Modifier.height(20.dp))

        // TEAM SECTION

        Text(
            "OUR FIVE TEAMS",
            modifier = Modifier.fillMaxWidth(),
            color = Gold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            letterSpacing = 2.sp
        )

        Spacer(Modifier.height(12.dp))

        agentTeams.forEach { team ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 5.dp)
                    .border(
                        1.dp,
                        team.color.copy(alpha = 0.65f),
                        RoundedCornerShape(12.dp)
                    )
                    .background(
                        Color(0xFF111827),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(13.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .background(
                                team.color,
                                RoundedCornerShape(2.dp)
                            )
                    )

                    Spacer(Modifier.width(9.dp))

                    Text(
                        team.name,
                        color = team.color,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(5.dp))

                Text(
                    team.members,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    team.description,
                    color = Color.LightGray,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(
            "TOGETHER, ALWAYS...",
            modifier = Modifier.fillMaxWidth(),
            color = Gold,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Text(
            "One Assistant • Two Personalities • Ten Agents",
            modifier = Modifier.fillMaxWidth(),
            color = Color.Gray,
            fontSize = 9.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

// -----------------------------------------------------
// PIXEL ART VIRTUAL HOUSE
// -----------------------------------------------------

@Composable
private fun PixelHouse(
    agents: List<HouseAgent>,
    sleeping: Boolean,
    modifier: Modifier = Modifier
) {

    Canvas(modifier = modifier) {

        val w = size.width
        val h = size.height

        fun rect(
            x: Float,
            y: Float,
            width: Float,
            height: Float,
            color: Color
        ) {
            drawRect(
                color = color,
                topLeft = Offset(w * x, h * y),
                size = Size(w * width, h * height)
            )
        }

        // WALLS AND FLOOR

        rect(0f, 0f, 1f, 1f, Color(0xFF242D3D))

        // WORK ROOM

        rect(0.015f, 0.035f, 0.47f, 0.44f, Color(0xFF243A52))
        rect(0.025f, 0.055f, 0.45f, 0.40f, Color(0xFF31516B))

        // WORK ROOM FLOOR TILES

        for (i in 0..5) {
            rect(
                0.025f + i * 0.075f,
                0.43f,
                0.07f,
                0.025f,
                Color(0xFF263F57)
            )
        }

        // TEN COMPUTERS

        for (i in 0 until 10) {
            val col = i % 5
            val row = i / 5
            val x = 0.045f + col * 0.083f
            val y = 0.13f + row * 0.13f

            // Desk
            rect(x, y + 0.065f, 0.065f, 0.025f, Color(0xFF79563D))

            // Monitor
            rect(x + 0.012f, y, 0.042f, 0.055f, Color(0xFF101722))
            rect(x + 0.017f, y + 0.008f, 0.032f, 0.036f, Color(0xFF39C9E8))
            rect(x + 0.025f, y + 0.055f, 0.016f, 0.01f, Color(0xFFB3C5D5))
        }

        // COMMAND TABLE

        rect(0.18f, 0.36f, 0.16f, 0.045f, Color(0xFFB28A4D))
        rect(0.20f, 0.37f, 0.12f, 0.012f, Color(0xFFFFD77B))

        // STORAGE CABINETS

        rect(0.035f, 0.34f, 0.04f, 0.07f, Color(0xFF6A493B))
        rect(0.42f, 0.34f, 0.04f, 0.07f, Color(0xFF6A493B))

        // SLEEPING ROOM

        rect(0.515f, 0.035f, 0.47f, 0.44f, Color(0xFF493A43))
        rect(0.525f, 0.055f, 0.45f, 0.40f, Color(0xFF634B50))

        // TEN BEDS

        for (i in 0 until 10) {
            val col = i % 5
            val row = i / 5
            val x = 0.54f + col * 0.083f
            val y = 0.12f + row * 0.15f

            // Bed frame
            rect(x, y, 0.07f, 0.085f, Color(0xFF30232C))

            // Mattress
            rect(x + 0.005f, y + 0.005f, 0.06f, 0.075f, Color(0xFFD5C4B4))

            // Pillow
            rect(x + 0.01f, y + 0.01f, 0.05f, 0.02f, Color(0xFFF2E8D9))

            // Blanket
            rect(x + 0.01f, y + 0.035f, 0.05f, 0.04f, Color(0xFF8D668C))

            // Bedside locker
            rect(x + 0.055f, y + 0.075f, 0.015f, 0.02f, Color(0xFFAD8451))
        }

        // RELAX ROOM

        rect(0.015f, 0.515f, 0.47f, 0.45f, Color(0xFF403458))
        rect(0.025f, 0.535f, 0.45f, 0.41f, Color(0xFF57416F))

        // SOFA ONE

        rect(0.07f, 0.64f, 0.17f, 0.075f, Color(0xFF9A65C7))
        rect(0.055f, 0.65f, 0.025f, 0.065f, Color(0xFFC18CEB))
        rect(0.24f, 0.65f, 0.025f, 0.065f, Color(0xFFC18CEB))
        rect(0.085f, 0.65f, 0.14f, 0.025f, Color(0xFFD8B7F1))

        // SOFA TWO

        rect(0.07f, 0.79f, 0.17f, 0.075f, Color(0xFF9A65C7))
        rect(0.055f, 0.80f, 0.025f, 0.065f, Color(0xFFC18CEB))
        rect(0.24f, 0.80f, 0.025f, 0.065f, Color(0xFFC18CEB))

        // TV

        rect(0.31f, 0.60f, 0.12f, 0.09f, Color(0xFF161C2B))
        rect(0.32f, 0.61f, 0.10f, 0.065f, Color(0xFF38D8E8))
        rect(0.35f, 0.69f, 0.04f, 0.015f, Color(0xFFB4A7C6))

        // GAMING ARCADE

        rect(0.32f, 0.77f, 0.045f, 0.10f, Color(0xFF29223D))
        rect(0.325f, 0.78f, 0.035f, 0.035f, Color(0xFFFF6CD6))
        rect(0.33f, 0.83f, 0.025f, 0.01f, Color(0xFFFFD75E))

        // AQUARIUM

        rect(0.39f, 0.77f, 0.065f, 0.08f, Color(0xFF172B3E))
        rect(0.395f, 0.78f, 0.055f, 0.06f, Color(0xFF39BBD6))
        rect(0.405f, 0.80f, 0.012f, 0.008f, Color(0xFFFFC85B))
        rect(0.43f, 0.82f, 0.012f, 0.008f, Color(0xFFFFC85B))

        // CENTRAL CORRIDOR

        rect(0.49f, 0.49f, 0.02f, 0.48f, Color(0xFFB28A4D))
        rect(0.015f, 0.485f, 0.97f, 0.025f, Color(0xFFB28A4D))

        // ENTRANCE / CORRIDOR FLOOR

        rect(0.49f, 0.515f, 0.495f, 0.45f, Color(0xFF344C4F))
        rect(0.51f, 0.535f, 0.46f, 0.41f, Color(0xFF3D6260))

        // CROWN EMBLEM

        rect(0.69f, 0.57f, 0.12f, 0.025f, Gold)
        rect(0.70f, 0.545f, 0.025f, 0.035f, Gold)
        rect(0.74f, 0.53f, 0.025f, 0.05f, Gold)
        rect(0.78f, 0.545f, 0.025f, 0.035f, Gold)

        // DIGITAL CLOCK

        rect(0.84f, 0.57f, 0.09f, 0.055f, Color(0xFF101722))
        rect(0.85f, 0.58f, 0.07f, 0.03f, Color(0xFF63F2C1))

        // PLANTS

        rect(0.55f, 0.82f, 0.035f, 0.045f, Color(0xFF79563D))
        rect(0.555f, 0.77f, 0.025f, 0.055f, Color(0xFF49B86A))
        rect(0.90f, 0.82f, 0.035f, 0.045f, Color(0xFF79563D))
        rect(0.905f, 0.77f, 0.025f, 0.055f, Color(0xFF49B86A))

        // SLEEP MODE DIM EFFECT

        if (sleeping) {
            rect(0f, 0f, 1f, 1f, Color(0xB900000A))

            // Bedroom remains illuminated
            rect(0.515f, 0.035f, 0.47f, 0.44f, Color(0x303F2B12))

            for (i in 0 until 10) {
                val col = i % 5
                val row = i / 5
                val x = 0.54f + col * 0.083f
                val y = 0.12f + row * 0.15f

                rect(x, y, 0.07f, 0.085f, Color(0xFFFFD47A))
                rect(x + 0.005f, y + 0.005f, 0.06f, 0.075f, Color(0xFFB79B8A))
                rect(x + 0.01f, y + 0.01f, 0.05f, 0.02f, Color(0xFFFFE9C2))
                rect(x + 0.01f, y + 0.035f, 0.05f, 0.04f, Color(0xFF8D668C))
            }
        }

        // AGENTS

        agents.forEach { agent ->

            val px = w * agent.x
            val py = h * agent.y

            val spriteWidth = w * 0.023f
            val spriteHeight = h * 0.075f

            // Shadow
            drawRect(
                Color(0x55000000),
                Offset(px - spriteWidth * 0.1f, py + spriteHeight * 0.85f),
                Size(spriteWidth * 1.2f, spriteHeight * 0.2f)
            )

            // Legs
            drawRect(
                Color(0xFF263044),
                Offset(px, py + spriteHeight * 0.62f),
                Size(spriteWidth * 0.38f, spriteHeight * 0.32f)
            )
            drawRect(
                Color(0xFF263044),
                Offset(px + spriteWidth * 0.55f, py + spriteHeight * 0.62f),
                Size(spriteWidth * 0.38f, spriteHeight * 0.32f)
            )

            // Body / uniform
            drawRect(
                agent.color,
                Offset(px - spriteWidth * 0.12f, py + spriteHeight * 0.32f),
                Size(spriteWidth * 1.25f, spriteHeight * 0.40f)
            )

            // Head
            drawRect(
                Color(0xFFE8B991),
                Offset(px + spriteWidth * 0.12f, py + spriteHeight * 0.05f),
                Size(spriteWidth * 0.75f, spriteHeight * 0.30f)
            )

            // Hair
            drawRect(
                Color(0xFF30252B),
                Offset(px + spriteWidth * 0.08f, py + spriteHeight * 0.02f),
                Size(spriteWidth * 0.85f, spriteHeight * 0.10f)
            )

            // Face detail
            drawRect(
                Color(0xFF33252A),
                Offset(px + spriteWidth * 0.65f, py + spriteHeight * 0.19f),
                Size(spriteWidth * 0.09f, spriteHeight * 0.04f)
            )

            if (sleeping) {
                // Small Z marks above sleeping agents
                drawRect(
                    Color(0xFFFFE7A1),
                    Offset(px + spriteWidth, py - spriteHeight * 0.12f),
                    Size(spriteWidth * 0.35f, spriteHeight * 0.08f)
                )
            }
        }
    }
}

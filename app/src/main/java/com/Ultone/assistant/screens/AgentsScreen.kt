
package com.ultone.assistant

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PixelBg = Color(0xFF080B14)
private val PixelGold = Color(0xFFD9AE50)

@Composable
fun AgentsScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PixelBg)
    ) {

        // HEADER

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = PixelGold
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "ULTRON x LUNA",
                    color = PixelGold,
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
                    tint = PixelGold
                )
            }
        }

        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(PixelGold.copy(alpha = 0.55f))
        )

        Spacer(Modifier.height(12.dp))

        Text(
            "OUR VIRTUAL WORLD",
            modifier = Modifier.fillMaxWidth(),
            color = PixelGold,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        // HOUSE MAP

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .aspectRatio(16f / 9f)
                .border(
                    2.dp,
                    PixelGold,
                    RoundedCornerShape(4.dp)
                )
                .background(Color(0xFF182332))
        ) {

            PixelArtHouse(
                modifier = Modifier.fillMaxSize()
            )

            PixelRoomLabel(
                "WORK ROOM",
                Color(0xFF8BD8FF),
                Modifier.align(Alignment.TopStart).padding(5.dp)
            )

            PixelRoomLabel(
                "SLEEP ROOM",
                Color(0xFFFFD78A),
                Modifier.align(Alignment.TopEnd).padding(5.dp)
            )

            PixelRoomLabel(
                "RELAX ROOM",
                Color(0xFFE0A4FF),
                Modifier.align(Alignment.BottomStart).padding(5.dp)
            )

            PixelRoomLabel(
                "ENTRANCE",
                Color(0xFF8DE8D5),
                Modifier.align(Alignment.BottomEnd).padding(5.dp)
            )
        }

        Spacer(Modifier.height(15.dp))

        Text(
            "OUR HOUSE • OUR WORLD",
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(7.dp))

        Text(
            "A HOME FOR ULTRON, LUNA & THEIR 10 AGENTS",
            modifier = Modifier.fillMaxWidth(),
            color = Color.Gray,
            fontSize = 9.sp,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            PixelLegend(Color(0xFF398BFF), "WORK")
            PixelLegend(Color(0xFFFFD47A), "SLEEP")
            PixelLegend(Color(0xFFB56BFF), "RELAX")
        }
    }
}

@Composable
private fun PixelRoomLabel(
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Text(
        label,
        modifier = modifier
            .background(Color(0xDD101722), RoundedCornerShape(2.dp))
            .padding(horizontal = 5.dp, vertical = 3.dp),
        color = color,
        fontSize = 7.sp,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
private fun PixelLegend(color: Color, name: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(9.dp)
                .background(color, RoundedCornerShape(1.dp))
        )
        Spacer(Modifier.width(5.dp))
        Text(
            name,
            color = Color.LightGray,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ==================================================
// TRUE LOW-RESOLUTION PIXEL ART HOUSE
// Logical drawing resolution: 320 x 180 pixels
// ==================================================

@Composable
private fun PixelArtHouse(modifier: Modifier = Modifier) {

    Canvas(modifier = modifier) {

        val unit = minOf(size.width / 320f, size.height / 180f)
        val offsetX = (size.width - 320f * unit) / 2f
        val offsetY = (size.height - 180f * unit) / 2f

        fun p(
            x: Int,
            y: Int,
            width: Int,
            height: Int,
            color: Color
        ) {
            drawRect(
                color = color,
                topLeft = Offset(
                    offsetX + x * unit,
                    offsetY + y * unit
                ),
                size = Size(width * unit, height * unit)
            )
        }

        // PALETTE

        val wall = Color(0xFF293D52)
        val darkWall = Color(0xFF172738)
        val wood = Color(0xFF986744)
        val woodDark = Color(0xFF593D31)
        val floorBlue = Color(0xFF345775)
        val floorPink = Color(0xFF76566C)
        val floorPurple = Color(0xFF654B7F)
        val floorGreen = Color(0xFF426F68)

        // OUTER WALL AND FLOOR

        p(0, 0, 320, 180, Color(0xFF111C29))
        p(3, 3, 314, 174, Color(0xFFB38A52))
        p(5, 5, 310, 170, Color(0xFF172331))

        // TOP-LEFT WORK ROOM

        p(7, 7, 148, 77, darkWall)
        p(10, 10, 142, 70, wall)
        p(10, 10, 142, 5, Color(0xFF688AA5))
        p(10, 10, 4, 70, Color(0xFF688AA5))
        p(148, 10, 4, 70, Color(0xFF1B2A3A))

        // WORK FLOOR TILES

        for (y in 17 until 80 step 8) {
            for (x in 14 until 149 step 8) {
                val c = if ((x + y) % 16 == 0)
                    Color(0xFF385F7B)
                else
                    floorBlue
                p(x, y, 7, 7, c)
            }
        }

        // TEN DESKS AND COMPUTERS

        for (i in 0 until 10) {

            val col = i % 5
            val row = i / 5

            val x = 17 + col * 25
            val y = 20 + row * 27

            // Desk shadow
            p(x - 1, y + 15, 21, 3, Color(0xFF233B50))

            // Desk top
            p(x, y + 11, 19, 4, wood)
            p(x, y + 11, 19, 1, Color(0xFFC08A57))
            p(x + 2, y + 15, 2, 4, woodDark)
            p(x + 15, y + 15, 2, 4, woodDark)

            // Monitor casing
            p(x + 4, y, 12, 10, Color(0xFF101923))
            p(x + 5, y + 1, 10, 7, Color(0xFF40C7E7))
            p(x + 6, y + 2, 8, 1, Color(0xFFB5F7FF))
            p(x + 9, y + 8, 2, 3, Color(0xFF899BAF))
        }

        // COMMAND TABLE

        p(67, 69, 61, 9, Color(0xFF533A2C))
        p(68, 68, 59, 6, Color(0xFFB68B55))
        p(72, 70, 50, 2, Color(0xFFE6BD75))
        p(75, 77, 3, 4, woodDark)
        p(117, 77, 3, 4, woodDark)

        // STORAGE CABINETS

        for (x in listOf(13, 132)) {
            p(x, 61, 12, 13, woodDark)
            p(x + 1, 62, 10, 5, wood)
            p(x + 1, 68, 10, 5, Color(0xFF80583D))
            p(x + 5, 64, 2, 2, PixelGold)
            p(x + 5, 70, 2, 2, PixelGold)
        }

        // RESEARCH HOLOGRAM

        p(109, 17, 31, 13, Color(0xFF17232D))
        p(111, 19, 27, 8, Color(0xFF47D7EB))
        p(114, 21, 18, 2, Color(0xFFB8FFFF))
        p(119, 30, 12, 2, Color(0xFF8C9BA8))

        // TASK BOARD

        p(119, 15, 27, 15, Color(0xFF563D30))
        p(121, 17, 23, 10, Color(0xFF243A48))
        p(124, 19, 16, 2, Color(0xFF66E0C1))
        p(124, 23, 12, 2, Color(0xFFFFD47A))

        // TOP-RIGHT SLEEPING ROOM

        p(165, 7, 148, 77, Color(0xFF44343D))
        p(168, 10, 142, 70, Color(0xFF604A50))
        p(168, 10, 142, 5, Color(0xFFAC817B))
        p(168, 10, 4, 70, Color(0xFFAC817B))

        // SLEEP ROOM FLOOR TILES

        for (y in 17 until 80 step 8) {
            for (x in 172 until 309 step 8) {
                val c = if ((x + y) % 16 == 0)
                    Color(0xFF73575B)
                else
                    floorPink
                p(x, y, 7, 7, c)
            }
        }

        // TEN BEDS

        for (i in 0 until 10) {

            val col = i % 5
            val row = i / 5

            val x = 174 + col * 27
            val y = 20 + row * 29

            // Bed shadow
            p(x - 1, y + 13, 22, 4, Color(0xFF332A34))

            // Bed frame
            p(x, y, 20, 16, Color(0xFF392832))
            p(x + 1, y + 1, 18, 14, Color(0xFFD7C1AA))

            // Pillow
            p(x + 3, y + 2, 14, 4, Color(0xFFFFE8CF))
            p(x + 4, y + 3, 12, 2, Color(0xFFF5DDC2))

            // Blanket
            p(x + 2, y + 7, 16, 7, Color(0xFF88638D))
            p(x + 2, y + 7, 16, 2, Color(0xFFB78AC1))
            p(x + 3, y + 11, 14, 1, Color(0xFF684A78))

            // Locker
            p(x + 17, y + 12, 5, 6, woodDark)
            p(x + 18, y + 13, 3, 3, wood)
            p(x + 19, y + 14, 1, 1, PixelGold)

            // Night lamp
            p(x + 18, y + 9, 3, 2, Color(0xFFFFD87A))
        }

        // WARDROBE

        p(290, 57, 16, 23, Color(0xFF50372D))
        p(292, 59, 6, 19, Color(0xFFAE7950))
        p(300, 59, 5, 19, Color(0xFF966443))
        p(298, 66, 2, 3, PixelGold)

        // WALL CLOCK

        p(273, 16, 17, 15, Color(0xFF2C2930))
        p(275, 18, 13, 11, Color(0xFFFFE4A6))
        p(281, 20, 1, 7, Color(0xFF5C4733))
        p(281, 23, 5, 1, Color(0xFF5C4733))

        // RELAX ROOM

        p(7, 94, 148, 80, Color(0xFF332A48))
        p(10, 97, 142, 74, Color(0xFF57416F))
        p(10, 97, 142, 4, Color(0xFFAE86C9))
        p(10, 97, 4, 74, Color(0xFFAE86C9))

        // RELAX FLOOR TILES

        for (y in 104 until 170 step 8) {
            for (x in 14 until 149 step 8) {
                val c = if ((x + y) % 16 == 0)
                    Color(0xFF735A8B)
                else
                    floorPurple
                p(x, y, 7, 7, c)
            }
        }

        // SOFA ONE

        p(20, 120, 68, 18, Color(0xFFB179D5))
        p(17, 122, 7, 14, Color(0xFFD49AEF))
        p(85, 122, 7, 14, Color(0xFFD49AEF))
        p(25, 123, 57, 5, Color(0xFFE2B9F4))
        p(27, 132, 53, 4, Color(0xFF8654B2))
        p(25, 138, 5, 3, woodDark)
        p(79, 138, 5, 3, woodDark)

        // SOFA TWO

        p(20, 145, 68, 17, Color(0xFFB179D5))
        p(17, 147, 7, 13, Color(0xFFD49AEF))
        p(85, 147, 7, 13, Color(0xFFD49AEF))
        p(25, 148, 57, 5, Color(0xFFE2B9F4))
        p(27, 157, 53, 3, Color(0xFF8654B2))

        // TELEVISION

        p(105, 113, 42, 29, Color(0xFF171A28))
        p(108, 116, 36, 22, Color(0xFF38CDE4))
        p(111, 119, 17, 2, Color(0xFFB5F9FF))
        p(132, 126, 8, 2, Color(0xFFB5F9FF))
        p(121, 142, 10, 3, Color(0xFF9C91A8))

        // ARCADE MACHINE

        p(108, 144, 17, 25, Color(0xFF241D35))
        p(110, 146, 13, 10, Color(0xFFFF70D8))
        p(112, 148, 9, 5, Color(0xFF24284C))
        p(113, 159, 3, 2, Color(0xFFFFD75E))
        p(119, 159, 3, 2, Color(0xFFFFD75E))
        p(113, 164, 8, 3, Color(0xFF171827))

        // AQUARIUM

        p(132, 145, 16, 23, Color(0xFF14283B))
        p(134, 147, 12, 19, Color(0xFF36BBD7))
        p(136, 151, 4, 2, Color(0xFFFFC85B))
        p(142, 156, 3, 2, Color(0xFFFFC85B))
        p(137, 162, 7, 2, Color(0xFF50C879))

        // BOOKSHELF

        p(13, 103, 11, 22, Color(0xFF51372F))
        p(15, 105, 7, 5, Color(0xFF469BDA))
        p(15, 111, 7, 5, Color(0xFFE6A34C))
        p(15, 117, 7, 5, Color(0xFF9D69CB))

        // REFRESHMENT CORNER

        p(130, 102, 17, 15, woodDark)
        p(132, 100, 13, 3, Color(0xFFB88A59))
        p(134, 105, 4, 6, Color(0xFFFFD47A))
        p(141, 105, 4, 6, Color(0xFF8DE8D5))

        // CENTRAL CORRIDOR

        p(153, 5, 12, 170, Color(0xFFB38A52))
        p(7, 86, 306, 8, Color(0xFFB38A52))

        // CORRIDOR WALKWAY

        p(165, 94, 148, 80, Color(0xFF294B4A))
        p(168, 97, 142, 74, floorGreen)

        for (y in 104 until 170 step 8) {
            for (x in 172 until 309 step 8) {
                val c = if ((x + y) % 16 == 0)
                    Color(0xFF4B7C72)
                else
                    floorGreen
                p(x, y, 7, 7, c)
            }
        }

        // CROWN EMBLEM BACKPLATE

        p(214, 105, 44, 8, Color(0xFF2B4148))

        // GOLD CROWN

        p(224, 113, 25, 4, PixelGold)
        p(221, 109, 5, 5, PixelGold)
        p(231, 105, 5, 9, PixelGold)
        p(242, 109, 5, 5, PixelGold)
        p(226, 117, 21, 3, Color(0xFFB08037))
        p(223, 108, 2, 2, Color.White)
        p(233, 104, 2, 2, Color.White)
        p(244, 108, 2, 2, Color.White)

        // DIGITAL CLOCK

        p(274, 105, 27, 16, Color(0xFF111925))
        p(277, 108, 21, 10, Color(0xFF173C3B))
        p(280, 112, 15, 2, Color(0xFF65F3C1))
        p(282, 110, 2, 1, Color(0xFF65F3C1))

        // TASK NOTIFICATION DISPLAY

        p(213, 131, 54, 22, Color(0xFF162530))
        p(216, 134, 48, 16, Color(0xFF284552))
        p(220, 137, 36, 2, Color(0xFF6FE2C1))
        p(220, 142, 28, 2, Color(0xFFFFD47A))
        p(220, 147, 32, 2, Color(0xFF8FCFFF))

        // CHARGING STATION

        p(274, 132, 29, 21, Color(0xFF1A2630))
        p(277, 135, 23, 15, Color(0xFF263B43))
        p(280, 138, 17, 2, Color(0xFF5BE7C1))
        p(280, 144, 17, 2, Color(0xFF5BE7C1))

        // PLANTS

        for (x in listOf(177, 300)) {
            p(x, 156, 12, 10, woodDark)
            p(x + 3, 151, 6, 7, Color(0xFF4DAE62))
            p(x, 153, 5, 5, Color(0xFF65D978))
            p(x + 8, 151, 5, 5, Color(0xFF65D978))
        }

        // ENTRANCE DOOR

        p(243, 156, 23, 17, Color(0xFF4B342A))
        p(245, 158, 9, 13, Color(0xFFB17C4E))
        p(256, 158, 8, 13, Color(0xFF986443))
        p(254, 163, 2, 2, PixelGold)

        // DECORATIVE FLOOR LIGHTS

        for (x in listOf(26, 94, 170, 199, 275)) {
            p(x, 91, 6, 2, Color(0xFFFFD47A))
        }

        // WALL SHADOWS AND DETAILS

        p(151, 8, 3, 73, Color(0xFF101B28))
        p(164, 8, 3, 73, Color(0xFF101B28))
        p(8, 83, 144, 2, Color(0xFF152432))
        p(168, 83, 143, 2, Color(0xFF342A35))

        // SMALL PIXEL FLOOR DETAILS

        for (i in 0 until 18) {
            val x = 14 + (i * 17) % 292
            val y = 87 + (i * 11) % 85

            p(
                x,
                y,
                2,
                2,
                if (i % 2 == 0)
                    Color(0xFF5D8278)
                else
                    Color(0xFF9B7B62)
            )
        }
    }
}

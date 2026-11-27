
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

private val HouseBg = Color(0xFF080B14)
private val HouseGold = Color(0xFFD9AE50)

@Composable
fun AgentsScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HouseBg)
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
                    tint = HouseGold
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ULTRON x LUNA",
                    color = HouseGold,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "VIRTUAL AI HOUSE",
                    color = Color.LightGray,
                    fontSize = 9.sp,
                    letterSpacing = 2.sp
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = HouseGold
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(HouseGold.copy(alpha = 0.55f))
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "OUR VIRTUAL WORLD",
            modifier = Modifier.fillMaxWidth(),
            color = HouseGold,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        // COMPLETE HOUSE MAP

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .aspectRatio(16f / 9f)
                .border(
                    2.dp,
                    HouseGold,
                    RoundedCornerShape(5.dp)
                )
                .background(Color(0xFF202A36))
        ) {

            HouseMapCanvas(
                modifier = Modifier.fillMaxSize()
            )

            // ROOM LABELS

            RoomLabel(
                text = "WORK ROOM",
                color = Color(0xFF75C9FF),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 5.dp, top = 4.dp)
            )

            RoomLabel(
                text = "SLEEP ROOM",
                color = Color(0xFFFFD47A),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 5.dp, top = 4.dp)
            )

            RoomLabel(
                text = "RELAX ROOM",
                color = Color(0xFFD6A0FF),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 5.dp, bottom = 4.dp)
            )

            RoomLabel(
                text = "ENTRANCE",
                color = Color(0xFF8DE8D5),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 5.dp, bottom = 4.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "OUR HOUSE • OUR WORLD",
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(5.dp))

        Text(
            text = "A HOME FOR ULTRON, LUNA & THEIR 10 AGENTS",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            color = Color.Gray,
            fontSize = 9.sp,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        // MAP LEGEND

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LegendItem(Color(0xFF398BFF), "WORK")
            LegendItem(Color(0xFFFFD47A), "SLEEP")
            LegendItem(Color(0xFFB56BFF), "RELAX")
        }

        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun RoomLabel(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier
            .background(
                Color(0xCC101722),
                RoundedCornerShape(3.dp)
            )
            .padding(horizontal = 4.dp, vertical = 2.dp),
        color = color,
        fontSize = 7.sp,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
private fun LegendItem(
    color: Color,
    name: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, RoundedCornerShape(2.dp))
        )

        Spacer(Modifier.width(5.dp))

        Text(
            text = name,
            color = Color.LightGray,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// --------------------------------------------------
// GBA STYLE HOUSE MAP
// --------------------------------------------------

@Composable
private fun HouseMapCanvas(
    modifier: Modifier = Modifier
) {

    Canvas(modifier = modifier) {

        val w = size.width
        val h = size.height

        fun block(
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

        // BASE FLOOR

        block(0f, 0f, 1f, 1f, Color(0xFF172331))

        // PIXEL FLOOR TILES

        for (row in 0 until 16) {
            for (col in 0 until 32) {

                val x = col / 32f
                val y = row / 16f

                val tileColor =
                    if ((row + col) % 2 == 0)
                        Color(0xFF263647)
                    else
                        Color(0xFF293A4B)

                block(
                    x, y,
                    1f / 32f,
                    1f / 16f,
                    tileColor
                )
            }
        }

        // WALL OUTLINE

        block(0.01f, 0.015f, 0.98f, 0.025f, Color(0xFFB38A52))
        block(0.01f, 0.96f, 0.98f, 0.025f, Color(0xFFB38A52))
        block(0.01f, 0.015f, 0.018f, 0.97f, Color(0xFFB38A52))
        block(0.972f, 0.015f, 0.018f, 0.97f, Color(0xFFB38A52))

        // WORK ROOM FLOOR

        block(0.025f, 0.055f, 0.455f, 0.405f, Color(0xFF31516B))

        // WORK ROOM WALLS

        block(0.025f, 0.055f, 0.455f, 0.025f, Color(0xFF7898AC))
        block(0.025f, 0.055f, 0.012f, 0.405f, Color(0xFF7898AC))
        block(0.468f, 0.055f, 0.012f, 0.405f, Color(0xFF7898AC))

        // WORK ROOM TILE DETAILS

        for (i in 0 until 6) {
            block(
                0.04f + i * 0.07f,
                0.41f,
                0.065f,
                0.025f,
                Color(0xFF29445D)
            )
        }

        // TEN COMPUTER DESKS

        for (i in 0 until 10) {

            val col = i % 5
            val row = i / 5

            val x = 0.045f + col * 0.084f
            val y = 0.12f + row * 0.135f

            // DESK

            block(
                x, y + 0.065f,
                0.065f, 0.026f,
                Color(0xFF79563D)
            )

            block(
                x + 0.005f, y + 0.085f,
                0.008f, 0.025f,
                Color(0xFF543C32)
            )

            block(
                x + 0.052f, y + 0.085f,
                0.008f, 0.025f,
                Color(0xFF543C32)
            )

            // MONITOR

            block(
                x + 0.012f, y,
                0.043f, 0.055f,
                Color(0xFF101722)
            )

            block(
                x + 0.016f, y + 0.006f,
                0.035f, 0.041f,
                Color(0xFF35C9E8)
            )

            block(
                x + 0.023f, y + 0.055f,
                0.020f, 0.010f,
                Color(0xFFB5C5D5)
            )

            // SCREEN LIGHT

            block(
                x + 0.021f, y + 0.013f,
                0.025f, 0.004f,
                Color(0xFFB1F7FF)
            )
        }

        // CENTRAL COMMAND TABLE

        block(0.18f, 0.36f, 0.16f, 0.045f, Color(0xFF815B3D))
        block(0.19f, 0.355f, 0.14f, 0.015f, Color(0xFFFFD47A))
        block(0.20f, 0.37f, 0.12f, 0.008f, Color(0xFF3C2C25))

        // STORAGE CABINETS

        for (i in 0 until 2) {
            val x = if (i == 0) 0.035f else 0.425f

            block(x, 0.335f, 0.038f, 0.075f, Color(0xFF604537))
            block(x + 0.004f, 0.343f, 0.030f, 0.027f, Color(0xFF986A48))
            block(x + 0.004f, 0.377f, 0.030f, 0.027f, Color(0xFF986A48))
            block(x + 0.017f, 0.353f, 0.005f, 0.005f, HouseGold)
            block(x + 0.017f, 0.388f, 0.005f, 0.005f, HouseGold)
        }

        // RESEARCH DISPLAY

        block(0.18f, 0.09f, 0.15f, 0.025f, Color(0xFF101722))
        block(0.185f, 0.095f, 0.14f, 0.015f, Color(0xFF4CE2F0))
        block(0.20f, 0.098f, 0.035f, 0.005f, Color.White)

        // TASK BOARD

        block(0.35f, 0.09f, 0.10f, 0.065f, Color(0xFF8C6745))
        block(0.36f, 0.10f, 0.08f, 0.045f, Color(0xFF263B48))
        block(0.37f, 0.11f, 0.05f, 0.005f, Color(0xFF6FE2C1))
        block(0.37f, 0.125f, 0.06f, 0.005f, Color(0xFFFFD47A)
        )

        // SLEEPING ROOM

        block(0.515f, 0.055f, 0.46f, 0.405f, Color(0xFF604B50))

        block(0.515f, 0.055f, 0.46f, 0.025f, Color(0xFFC49A80))
        block(0.515f, 0.055f, 0.012f, 0.405f, Color(0xFFC49A80))
        block(0.963f, 0.055f, 0.012f, 0.405f, Color(0xFFC49A80))

        // TEN INDIVIDUAL BEDS

        for (i in 0 until 10) {

            val col = i % 5
            val row = i / 5

            val x = 0.535f + col * 0.085f
            val y = 0.115f + row * 0.155f

            // BED FRAME

            block(
                x, y,
                0.072f, 0.09f,
                Color(0xFF33252B)
            )

            // MATTRESS

            block(
                x + 0.004f, y + 0.004f,
                0.064f, 0.082f,
                Color(0xFFD6C5B5)
            )

            // PILLOW

            block(
                x + 0.010f, y + 0.008f,
                0.052f, 0.022f,
                Color(0xFFFFEAD5)
            )

            // BLANKET

            block(
                x + 0.009f, y + 0.037f,
                0.054f, 0.041f,
                Color(0xFF87618E)
            )

            // BLANKET DETAIL

            block(
                x + 0.009f, y + 0.037f,
                0.054f, 0.005f,
                Color(0xFFB78CC2)
            )

            // BEDSIDE LOCKER

            block(
                x + 0.052f, y + 0.078f,
                0.018f, 0.023f,
                Color(0xFF986E4A)
            )

            // NIGHT LAMP

            block(
                x + 0.057f, y + 0.070f,
                0.009f, 0.008f,
                Color(0xFFFFE18A)
            )
        }

        // WARDROBE

        block(0.90f, 0.31f, 0.055f, 0.12f, Color(0xFF76513E))
        block(0.905f, 0.315f, 0.022f, 0.11f, Color(0xFFA87A51))
        block(0.932f, 0.315f, 0.018f, 0.11f, Color(0xFFA87A51))
        block(0.926f, 0.36f, 0.004f, 0.01f, HouseGold)

        // WALL CLOCK

        block(0.84f, 0.09f, 0.055f, 0.045f, Color(0xFF272A35))
        block(0.85f, 0.098f, 0.035f, 0.029f, Color(0xFFFFE2A1))
        block(0.867f, 0.102f, 0.003f, 0.015f, Color(0xFF41362C))

        // RELAX ROOM

        block(0.025f, 0.535f, 0.455f, 0.405f, Color(0xFF57416F))

        block(0.025f, 0.535f, 0.455f, 0.025f, Color(0xFFB28ACB))
        block(0.025f, 0.535f, 0.012f, 0.405f, Color(0xFFB28ACB))
        block(0.468f, 0.535f, 0.012f, 0.405f, Color(0xFFB28ACB))

        // SOFA ONE

        block(0.065f, 0.645f, 0.19f, 0.09f, Color(0xFF8956B4))
        block(0.055f, 0.65f, 0.025f, 0.075f, Color(0xFFC18CEB))
        block(0.245f, 0.65f, 0.025f, 0.075f, Color(0xFFC18CEB))
        block(0.08f, 0.655f, 0.16f, 0.025f, Color(0xFFD8B7F1))
        block(0.08f, 0.695f, 0.16f, 0.025f, Color(0xFF68418A))

        // SOFA TWO

        block(0.065f, 0.79f, 0.19f, 0.09f, Color(0xFF8956B4))
        block(0.055f, 0.795f, 0.025f, 0.075f, Color(0xFFC18CEB))
        block(0.245f, 0.795f, 0.025f, 0.075f, Color(0xFFC18CEB))
        block(0.08f, 0.80f, 0.16f, 0.025f, Color(0xFFD8B7F1))

        // TELEVISION

        block(0.315f, 0.60f, 0.125f, 0.095f, Color(0xFF111824))
        block(0.322f, 0.608f, 0.111f, 0.075f, Color(0xFF35CDE5))
        block(0.335f, 0.618f, 0.035f, 0.005f, Color(0xFFB7FAFF))
        block(0.375f, 0.64f, 0.035f, 0.005f, Color(0xFFB7FAFF))
        block(0.36f, 0.695f, 0.04f, 0.015f, Color(0xFF8793A5))

        // ARCADE MACHINE

        block(0.315f, 0.76f, 0.055f, 0.12f, Color(0xFF29223D))
        block(0.321f, 0.77f, 0.043f, 0.04f, Color(0xFFFF65D5))
        block(0.328f, 0.78f, 0.03f, 0.018f, Color(0xFF282B52))
        block(0.331f, 0.825f, 0.025f, 0.008f, Color(0xFFFFD75E)
        )
        block(0.332f, 0.85f, 0.02f, 0.025f, Color(0xFF181A28))

        // AQUARIUM

        block(0.39f, 0.765f, 0.07f, 0.095f, Color(0xFF152A3E))
        block(0.396f, 0.772f, 0.058f, 0.08f, Color(0xFF36B8D4))
        block(0.40f, 0.785f, 0.015f, 0.008f, Color(0xFFFFC85B))
        block(0.43f, 0.81f, 0.014f, 0.008f, Color(0xFFFFC85B))
        block(0.408f, 0.84f, 0.035f, 0.008f, Color(0xFF4FC77A))

        // BOOKSHELF

        block(0.035f, 0.56f, 0.035f, 0.075f, Color(0xFF79513C))
        block(0.04f, 0.565f, 0.025f, 0.015f, Color(0xFF3E9CDB))
        block(0.04f, 0.582f, 0.025f, 0.015f, Color(0xFFE6A34C))
        block(0.04f, 0.599f, 0.025f, 0.015f, Color(0xFF9D69CB))

        // REFRESHMENT CORNER

        block(0.38f, 0.565f, 0.075f, 0.055f, Color(0xFF76513E))
        block(0.39f, 0.55f, 0.055f, 0.02f, Color(0xFFB88A59))
        block(0.40f, 0.575f, 0.015f, 0.018f, Color(0xFFFFD47A))
        block(0.43f, 0.575f, 0.015f, 0.018f, Color(0xFF8DE8D5))

        // CENTRAL CORRIDOR

        block(0.485f, 0.48f, 0.03f, 0.48f, Color(0xFFB28A52))
        block(0.025f, 0.475f, 0.95f, 0.035f, Color(0xFFB28A52))

        // ENTRANCE FLOOR

        block(0.515f, 0.535f, 0.445f, 0.405f, Color(0xFF3D6260))

        // ENTRANCE WALL DECORATION

        block(0.65f, 0.565f, 0.17f, 0.025f, Color(0xFF253C48))

        // CROWN EMBLEM

        block(0.69f, 0.61f, 0.12f, 0.025f, HouseGold)
        block(0.70f, 0.575f, 0.025f, 0.04f, HouseGold)
        block(0.74f, 0.555f, 0.025f, 0.06f, HouseGold)
        block(0.78f, 0.575f, 0.025f, 0.04f, HouseGold)

        // CROWN JEWELS

        block(0.703f, 0.58f, 0.012f, 0.012f, Color.White)
        block(0.743f, 0.56f, 0.012f, 0.012f, Color.White)
        block(0.783f, 0.58f, 0.012f, 0.012f, Color.White)

        // DIGITAL CLOCK

        block(0.84f, 0.565f, 0.10f, 0.065f, Color(0xFF111925))
        block(0.848f, 0.575f, 0.084f, 0.045f, Color(0xFF183A3B))
        block(0.86f, 0.59f, 0.06f, 0.008f, Color(0xFF65F3C1))

        // TASK NOTIFICATION DISPLAY

        block(0.65f, 0.70f, 0.17f, 0.09f, Color(0xFF17252E))
        block(0.66f, 0.71f, 0.15f, 0.07f, Color(0xFF243F4C))
        block(0.675f, 0.725f, 0.12f, 0.006f, Color(0xFF6FE2C1))
        block(0.675f, 0.745f, 0.09f, 0.006f, Color(0xFFFFD47A))
        block(0.675f, 0.765f, 0.105f, 0.006f, Color(0xFF8FCFFF))

        // CHARGING STATION

        block(0.84f, 0.70f, 0.09f, 0.075f, Color(0xFF28323C))
        block(0.85f, 0.71f, 0.07f, 0.055f, Color(0xFF16212B))
        block(0.86f, 0.72f, 0.05f, 0.008f, Color(0xFF5BE7C1))
        block(0.86f, 0.74f, 0.05f, 0.008f, Color(0xFF5BE7C1))

        // PLANTS

        for (x in listOf(0.55f, 0.92f)) {

            block(x, 0.85f, 0.035f, 0.045f, Color(0xFF79563D))
            block(x + 0.005f, 0.81f, 0.025f, 0.045f, Color(0xFF49B86A))
            block(x - 0.008f, 0.825f, 0.018f, 0.018f, Color(0xFF60D77A))
            block(x + 0.023f, 0.825f, 0.018f, 0.018f, Color(0xFF60D77A))
        }

        // ENTRANCE DOOR

        block(0.735f, 0.88f, 0.075f, 0.065f, Color(0xFF69442F))
        block(0.743f, 0.885f, 0.059f, 0.055f, Color(0xFFB17C4E))
        block(0.79f, 0.91f, 0.006f, 0.006f, HouseGold)

        // ROOM DIVIDERS

        block(0.49f, 0.045f, 0.02f, 0.41f, Color(0xFFB28A52))
        block(0.025f, 0.46f, 0.455f, 0.02f, Color(0xFFB28A52))
        block(0.515f, 0.46f, 0.445f, 0.02f, Color(0xFFB28A52))

        // DOOR OPENINGS

        block(0.205f, 0.455f, 0.09f, 0.03f, Color(0xFF3D6260))
        block(0.68f, 0.455f, 0.09f, 0.03f, Color(0xFF3D6260))
        block(0.485f, 0.70f, 0.03f, 0.09f, Color(0xFF3D6260))

        // PIXEL LIGHT DETAILS

        for (i in 0 until 8) {

            val x = 0.08f + i * 0.115f

            block(
                x, 0.495f,
                0.018f, 0.008f,
                Color(0xFFFFD47A)
            )
        }
    }
}

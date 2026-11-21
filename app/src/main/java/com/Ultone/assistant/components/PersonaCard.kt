
package com.ultone.assistant.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ultone.assistant.Persona

private val CardGold = Color(0xFFD9AE50)
private val CardBackground = Color(0xFF1B293D)

@Composable
fun PersonaCard(
    persona: Persona,
    selected: Boolean,
    imageId: Int,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) CardGold else Color.DarkGray,
                shape = RoundedCornerShape(18.dp)
            )
            .background(
                CardBackground,
                RoundedCornerShape(18.dp)
            )
            .clickable {
                onClick()
            }
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageId),
            contentDescription = persona.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = persona.name,
            color = CardGold,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = subtitle,
            color = Color.LightGray,
            fontSize = 10.sp,
            lineHeight = 14.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selected) {
                    CardGold
                } else {
                    Color(0xFF34445B)
                },
                contentColor = if (selected) {
                    Color.Black
                } else {
                    Color.White
                }
            )
        ) {
            Text(
                text = if (selected) "✓ SELECTED" else "SELECT"
            )
        }
    }
}

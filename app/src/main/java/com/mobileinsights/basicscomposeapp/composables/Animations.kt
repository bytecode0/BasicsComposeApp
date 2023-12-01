package com.mobileinsights.basicscomposeapp.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TypeWriterScreen(text: String) {
    var visibleText by remember { mutableStateOf("") }

    LaunchedEffect(key1 = text) {
        for (i in text.indices) {
            visibleText = text.substring(0, i + 1)
            delay(100) // Adjust the delay for typing speed
        }
    }

    BasicTextField(
        value = TextFieldValue(visibleText),
        onValueChange = {},
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color.Black)
    )
}

@Composable
fun IntegerAnimatedScreen() {
    var earnedAmount by remember { mutableStateOf(0) }
    val maximumAmount = remember { 2500 }

    val earnedAmountAnimation = animateIntAsState(
        targetValue = earnedAmount,
        label = "earned amount animation",
        animationSpec = tween(durationMillis = 1000)
    ).value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Total Amount is : $maximumAmount",
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = earnedAmountAnimation.toString())
        }

        Button(
            onClick = { earnedAmount += 200 },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff279EFF))
        ) {
            Text(text = "Earn 200")
        }
    }
}

@Composable
fun SizeAnimatedScreen() {
    var expanded by remember { mutableStateOf(false) }
    var isHighlighted by remember { mutableStateOf(false) }

    val sizeState by animateSizeAsState(
        if (expanded) {
            Size(200f, 200f)
        } else {
            Size(100f, 100f)
        },
        animationSpec = SpringSpec(stiffness = Spring.StiffnessLow),
        label = "size animation"
    )

    val backgroundColor by animateColorAsState(
        if (isHighlighted) { Color.Red } else { Color.Blue },
        animationSpec = spring(),
        label = "background color animation"
    )

    val cornerRadius by animateDpAsState(
        targetValue = if (isHighlighted) 300.dp else 0.dp,
        animationSpec = spring(),
        label = "corner radius animation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(sizeState.width.dp, sizeState.height.dp)
                .clip(RoundedCornerShape(cornerRadius.value))
                .background(backgroundColor)
                .clickable {
                    expanded = !expanded
                    isHighlighted = expanded
                }
                .padding(16.dp)
        ) {
            // Content goes here
        }
    }
}
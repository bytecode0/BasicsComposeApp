package com.mobileinsights.basicscomposeapp.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class TextAnimationType {
    DEFAULT,
    SLIDE_UP,
    SLIDE_DOWN,
    SLIDE_IN_AND_OUT,
    FADE_IN_AND_OUT
}

enum class AnimationsScreen {
    MENU,
    SIMPLE_TEXT_ANIMATION,
    TYPEWRITER_ANIMATION,
    NUMBER_ANIMATION,
    CONTENT_COLOR_SIZE,
    CONTENT_SIZE,
    FADE_IN_FADE_OUT_HEART,
    ROTATION,
    INFINITE_TRANSITION
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimationMenuScreen() {
    var currentScreen by remember { mutableStateOf(AnimationsScreen.MENU) }

    when (currentScreen) {
        AnimationsScreen.MENU -> AnimationMenu(onScreenSelected = { screenSelected ->
            currentScreen = screenSelected
        })
        AnimationsScreen.SIMPLE_TEXT_ANIMATION -> SimpleTextAnimation()
        AnimationsScreen.TYPEWRITER_ANIMATION -> TypeWriterScreen(text = "Lorem ipsum dolor sit amet consectetur adipiscing elit primis enim, vulputate pretium sodales varius mi condimentum ante id vehicula, rhoncus metus sed aptent facilisis torquent aliquet arcu. Nisi himenaeos lacinia augue accumsan arcu taciti cursus ullamcorper enim tincidunt per, dapibus convallis pharetra varius etiam posuere et id dis nulla sociosqu habitasse, massa at sed donec ornare a natoque neque nunc scelerisque. Blandit feugiat leo ligula viverra congue sapien platea sollicitudin quis, montes cursus iaculis ante nunc senectus potenti.\n" +
                "\n" +
                "In suspendisse nullam justo et lobortis congue vulputate, primis inceptos himenaeos augue turpis consequat conubia, cursus iaculis fames malesuada leo quam. Quis molestie tincidunt torquent commodo aptent nec nisl scelerisque pretium conubia, est enim purus litora hendrerit vestibulum a varius malesuada, sapien laoreet himenaeos praesent nam mi inceptos suspendisse fames. Fusce maecenas montes nunc tellus velit parturient laoreet nulla ut nullam, netus taciti himenaeos senectus interdum potenti blandit a.")
        AnimationsScreen.NUMBER_ANIMATION -> IntegerAnimatedScreen()
        AnimationsScreen.CONTENT_COLOR_SIZE -> AnimatedContentColorSize()
        AnimationsScreen.CONTENT_SIZE -> AnimatedContentSize()
        AnimationsScreen.FADE_IN_FADE_OUT_HEART -> FadeInFadeOutHeart()
        AnimationsScreen.ROTATION -> RotationAnimation()
        AnimationsScreen.INFINITE_TRANSITION -> InfiniteTransition()
    }
}

@Composable
fun AnimationMenu(
    onScreenSelected: (AnimationsScreen) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedButton(onClick = { onScreenSelected(AnimationsScreen.SIMPLE_TEXT_ANIMATION) }) {
            Text(text = "Simple Text Animations")
        }
        ElevatedButton(onClick = { onScreenSelected(AnimationsScreen.TYPEWRITER_ANIMATION) }) {
            Text(text = "Typewriter Animation")
        }
        ElevatedButton(onClick = { onScreenSelected(AnimationsScreen.NUMBER_ANIMATION) }) {
            Text(text = "Number Animation")
        }
        ElevatedButton(onClick = { onScreenSelected(AnimationsScreen.CONTENT_COLOR_SIZE) }) {
            Text(text = "Content, Color and Size Animation")
        }
        ElevatedButton(onClick = { onScreenSelected(AnimationsScreen.CONTENT_SIZE) }) {
            Text(text = "Content an Size Animation")
        }
        ElevatedButton(onClick = { onScreenSelected(AnimationsScreen.FADE_IN_FADE_OUT_HEART) }) {
            Text(text = "Fade-In and Fade-Out Heart")
        }
        ElevatedButton(onClick = { onScreenSelected(AnimationsScreen.ROTATION) }) {
            Text(text = "Animate Lazy Colum")
        }
        ElevatedButton(onClick = { onScreenSelected(AnimationsScreen.INFINITE_TRANSITION) }) {
            Text(text = "Infinite Transition")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SimpleTextAnimation() {
    var currentAnimation by remember { mutableStateOf(TextAnimationType.DEFAULT) }

    Box(
        contentAlignment = Alignment.Center
    ) {
        var amount by remember { mutableStateOf(0) }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = amount,
                transitionSpec = {
                    when(currentAnimation) {
                        TextAnimationType.SLIDE_UP -> {
                            slideIntoContainer(
                                towards = AnimatedContentScope.SlideDirection.Up,
                                animationSpec = tween(durationMillis = 500)
                            ) with ExitTransition.None
                        }
                        TextAnimationType.SLIDE_DOWN -> {
                            slideIntoContainer(
                                towards = AnimatedContentScope.SlideDirection.Down,
                                animationSpec = tween(durationMillis = 500)
                            ) with ExitTransition.None
                        }
                        TextAnimationType.SLIDE_IN_AND_OUT -> {
                            slideIntoContainer(
                                towards = AnimatedContentScope.SlideDirection.Up,
                                animationSpec = tween(durationMillis = 500)
                            ) with
                                    slideOutOfContainer(
                                        towards = AnimatedContentScope.SlideDirection.Up,
                                        animationSpec = tween(durationMillis = 500)
                                    )
                        }
                        TextAnimationType.FADE_IN_AND_OUT -> {
                            fadeIn(animationSpec = tween(durationMillis = 500)) with
                                    fadeOut(animationSpec = tween(durationMillis = 500))
                        }
                        TextAnimationType.DEFAULT -> {
                            fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                                    scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90)) with
                                    fadeOut(animationSpec = tween(90))
                        }
                    }

                },
                contentAlignment = Alignment.Center,
                label = "Amount animation"
            ) { targetCount ->
                Text(
                    text = "$targetCount",
                    fontSize = 36.sp
                )
            }

            Button(onClick = { amount = amount + 100 }) {
                Text(text = "Increment")
            }

            Spacer(modifier = Modifier.size(8.dp))

            SelectableButton(
                onClick = { currentAnimation = it },
                text = "Slide-Up",
                animationType = TextAnimationType.SLIDE_UP,
                currentAnimation
            )

            SelectableButton(
                onClick = { currentAnimation = it },
                text = "Slide-Down",
                animationType = TextAnimationType.SLIDE_DOWN,
                currentAnimation
            )

            SelectableButton(
                onClick = { currentAnimation = it },
                text = "Slide In and Out",
                animationType = TextAnimationType.SLIDE_IN_AND_OUT,
                currentAnimation
            )

            SelectableButton(
                onClick = { currentAnimation = it },
                text = "Fade In and Out",
                animationType = TextAnimationType.FADE_IN_AND_OUT,
                currentAnimation
            )

            SelectableButton(
                onClick = { currentAnimation = it },
                text = "Default",
                animationType = TextAnimationType.DEFAULT,
                currentAnimation
            )
        }
    }
}

@Composable
fun SelectableButton(
    onClick: (TextAnimationType) -> Unit,
    text: String,
    animationType: TextAnimationType,
    currentAnimation: TextAnimationType
)
{
    Button(
        onClick = {
            onClick(animationType)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (currentAnimation == animationType) Color.Green else Color.Cyan
        )
    ) {
        Text(text = text)
    }
}
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
fun AnimatedContentColorSize() {
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

@Composable
fun RotationAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(2000, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            imageVector = Icons.Default.Favorite,
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.Red),
            modifier = Modifier
                .size(350.dp)
                .rotate(angle)
        )
    }
}

@Composable
fun InfiniteTransition() {
    val infiniteTransition = rememberInfiniteTransition()

    val color = infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Yellow,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(24.dp)
            .background(color.value)
    )
}

@Composable
fun slideInSlideOutAnimation() {
    Column(modifier = Modifier.fillMaxSize()) {
        var isVisible by remember { mutableStateOf(true) }

        Button(onClick = {
            isVisible = !isVisible
        }) {
            Text(text = if (isVisible) "Slide out" else "Slide in")
        }
        AnimatedVisibility(visible = isVisible, enter = slideIn(initialOffset = {
            IntOffset(it.width, it.height / 2)
        }, animationSpec = tween(2000, easing = LinearEasing)), exit = slideOut(targetOffset = {
            IntOffset(-it.width, it.height / 2)
        }, animationSpec = tween(2000, easing = LinearEasing))) {

            Box(
                modifier = Modifier
                    .background(Color.Yellow)
                    .size(400.dp)
            )
        }
    }
}

@Composable
fun FadeInFadeOutHeart() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var isVisible by remember {
            mutableStateOf(false)
        }

        Button(onClick = {
            isVisible = !isVisible
        }) {
            Text(text = if (isVisible) "Hide" else "Show")
        }

        Spacer(modifier = Modifier.size(150.dp))

        AnimatedVisibility(
            isVisible,
            exit = fadeOut(animationSpec = tween(2000, easing = LinearEasing)),
            enter = fadeIn(animationSpec = tween(2000, easing = LinearEasing))
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.Red, shape = heart())
            )
        }

    }
}

@Composable
fun AnimatedContentSize() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Color.Gray
                )
                .animateContentSize()
        ) {

            var isExpended by remember {
                mutableStateOf(false)
            }

            Row(modifier = Modifier
                .padding(16.dp)
                .clickable {
                    isExpended = !isExpended
                }) {

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "",
                    tint = Color.White
                )
                Text(
                    text = if (isExpended) "Hide more info" else "Click for more information...",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)
                )


            }

            if (isExpended)
                Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            isExpended = !isExpended

                        })

        }


    }
}
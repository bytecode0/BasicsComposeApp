package com.mobileinsights.basicscomposeapp

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobileinsights.basicscomposeapp.ui.theme.BasicsComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsComposeAppTheme(
                dynamicColor = false
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier
) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(
        modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        if (shouldShowOnboarding) {
            OnboardingScreen(
                onContinueClicked = {
                    shouldShowOnboarding = false
                }
            )
        } else {
            DashboardScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    tabSelected: Int = 0
) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(tabSelected) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Basics Compose App",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults
                    .centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = MaterialTheme.colorScheme.primary,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    listOf("List", "Gallery").forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(text = title)
                            },
                            selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                            }
                        )
                    }
                }

                when (selectedTabIndex) {
                    0 -> GreetingScreen()
                    1 -> GreetingGridScreen()
                }
            }
        }
    )
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit
) {
    val animateVisible = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        animateVisible.value = true
    }
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = animateVisible.value,
                enter = fadeIn(animationSpec = tween(2000)),
                exit = fadeOut(animationSpec = tween(2000))
            ) {
                Text(
                    text = "Welcome to the Basics Compose App",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(
                visible = animateVisible.value,
                enter = fadeIn(animationSpec = tween(4000)),
                exit = fadeOut(animationSpec = tween(4000))
            ) {
                Button(
                    onClick = onContinueClicked
                ) {
                    Text(text = "Continue")
                }
            }

        }
    }
}

@Composable
fun GreetingScreen(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                name = name
            )
        }
    }
}


@Composable
fun GreetingGridScreen(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(names) {
            Card (
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = modifier.padding(8.dp)
            ) {
                CardContent(name = it)
            }
        }
    }
}

@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    name: String,
    isExpanded: Boolean = false,
) {
    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.padding(8.dp)
    ) {
        ExpandableCardContent(name, isExpanded)
    }
}

@Composable
fun ExpandableCardContent(
    name: String,
    isExpanded: Boolean = false
) {
    val expanded = rememberSaveable { mutableStateOf(isExpanded) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "Welcome, ", style = MaterialTheme.typography.titleLarge)
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
                )
            }
            IconButton(
                onClick = {
                    expanded.value = !expanded.value
                }
            ) {
                if (expanded.value) {
                    Icon(Icons.Filled.ExpandLess, contentDescription = "ExpandLess")
                } else {
                    Icon(Icons.Filled.ExpandMore, contentDescription = "ExpandMore")
                }
            }
        }
        AnimatedVisibility(
            visible = expanded.value,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.ic_jetpack_compose),
                    contentDescription = "Launcher Background",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

@Composable
fun CardContent(
    name: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_jetpack_compose),
            contentDescription = "Launcher Background",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    BasicsComposeAppTheme {
        OnboardingScreen(
            onContinueClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardFirstTabSelectedPreview() {
    BasicsComposeAppTheme {
        DashboardScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardSecondTabSelectedPreview() {
    BasicsComposeAppTheme {
        DashboardScreen(
            tabSelected = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicsComposeAppTheme {
        Greeting(
            name = "Your Name",
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandedGreetingPreview() {
    BasicsComposeAppTheme {
        Greeting(
            name = "Your Name",
            isExpanded = true,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    BasicsComposeAppTheme {
        GreetingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingGridScreenPreview() {
    BasicsComposeAppTheme {
        GreetingGridScreen()
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Composable
fun DashboardFirstTabSelectedDarkPreview() {
    BasicsComposeAppTheme {
        DashboardScreen()
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Composable
fun GreetingScreenDarkPreview() {
    BasicsComposeAppTheme {
        GreetingScreen()
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Composable
fun GreetingGridScreenDarkPreview() {
    BasicsComposeAppTheme {
        GreetingGridScreen()
    }
}
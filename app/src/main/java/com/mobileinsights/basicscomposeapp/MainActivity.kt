package com.mobileinsights.basicscomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobileinsights.basicscomposeapp.ui.theme.BasicsComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsComposeAppTheme {
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
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(
                onContinueClicked = {
                    shouldShowOnboarding = false
                }
            )
        } else {
            GreetingsScreen()
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Compose App!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun GreetingsScreen() {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        val names = listOf(
            "Android Developer",
            "UX/UI Designer",
            "iOS Developer",
            "Software Engineer"
        )
        Column {
            names.forEach { name ->
                Greeting(
                    name = name,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false
) {
    val expanded = remember { mutableStateOf(isExpanded) }

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(text = "Welcome, ")
                    Text(text = name)
                }
                ElevatedButton(
                    onClick = {
                        expanded.value = !expanded.value
                    }
                ) {
                    Text(text = if (expanded.value) "Show less" else "Show more")
                }
            }
            if (expanded.value) {
                Row {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.ic_jetpack_compose),
                            contentDescription = "Image",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    BasicsComposeAppTheme {
        MyApp()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingsScreenPreview() {
    BasicsComposeAppTheme {
        GreetingsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicsComposeAppTheme {
        Greeting(name = "Bussiness Manager")
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandedGreetingPreview() {
    BasicsComposeAppTheme {
        Greeting(
            name = "Bussiness Manager",
            isExpanded = true
        )
    }
}
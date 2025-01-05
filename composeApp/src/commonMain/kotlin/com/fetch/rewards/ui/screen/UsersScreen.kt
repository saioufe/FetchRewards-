package com.fetch.rewards.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.fetch.rewards.ui.UsersEvent
import com.fetch.rewards.ui.UsersState
import com.fetch.rewards.ui.viewModel.UsersViewModel
import fetchrewards.composeapp.generated.resources.Res
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class UsersScreen() : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val usersViewModel: UsersViewModel = get()
        val state by usersViewModel.state.collectAsState()

        readingQuran(
            state = state,
            onEvent = usersViewModel::onEvent,
        )
    }
}


@Composable
fun readingQuran(
    state: UsersState,
    onEvent: (UsersEvent) -> Unit,
) {


    var showBottomSheet by remember { mutableStateOf(false) }
    var showPlayBar by remember { mutableStateOf(false) }
    var showIcons by remember { mutableStateOf(false) }
    var iconBoxPosition by remember { mutableStateOf(Offset(0f, 0f)) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) } // Declaring textLayoutResult
    var whichBottomSheet = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope() // Coroutine scope to launch suspend functions

    val density = LocalDensity.current
    val navigator = LocalNavigator.currentOrThrow
    val listState = rememberLazyListState()


    // Dismiss the bottom sheet when scrolling
    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
        }
    }

    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.Center)
                        .clickable { // Dismiss on click
                            coroutineScope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                            }
                        }, // Align Snackbar to the top center
                ) { data ->
                    Snackbar(
                        modifier = Modifier.padding(8.dp),
                        contentColor = Color.Black,
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center // Align content (text) to center
                        ) {
                            Text(
                                data.message,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        },
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 50.dp, start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp).align(Alignment.Top)
                        .clickable(
                            indication = null,  // This removes the ripple effect
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            navigator.pop()
                        },
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 7.dp),
                    textAlign = TextAlign.Center,
                    text = "saif",
                    fontSize = 20.sp,
                    color = Color(0xFF464646),

                    )
                Text(
                    modifier = Modifier.padding(horizontal = 7.dp).clickable {
                    },
                    textAlign = TextAlign.Center,
                    text = "Aa",
                    fontSize = 17.sp,
                    color = Color(0xFFB1B1B1),
                )
            }

        }
    ) { paddingValue ->
        // Start the spotlight


    }

}
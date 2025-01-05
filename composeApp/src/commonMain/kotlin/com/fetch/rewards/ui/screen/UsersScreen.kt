package com.fetch.rewards.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
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
import com.shulalab.fetch_rewards.domain.UserModel
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

        userCompose(
            state = state,
            onEvent = usersViewModel::onEvent,
        )
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun userCompose(
    state: UsersState,
    onEvent: (UsersEvent) -> Unit,
) {
    val navigator = LocalNavigator.currentOrThrow

    // Trigger fetching users when the screen is first composed
    LaunchedEffect(Unit) {
        onEvent(UsersEvent.GetUsers)
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { navigator.pop() },
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
                Text(
                    text = "Users Table",
                    fontSize = 20.sp,
                    color = Color(0xFF464646),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when {
                state.isLoading -> {
                    // Loading State
                    CircularWavyProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.isFailure -> {
                    // Failure State
                    Text(
                        text = state.errorMessage,
                        color = Color.Red,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.isSuccess -> {
                    // Success State: Display users in a table format
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = paddingValues,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        state.users.forEach { (listId, users) ->
                            // Header for each group (listId)
                            item {
                                Text(
                                    text = "List ID: $listId",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF464646),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.LightGray)
                                        .padding(8.dp)
                                )
                            }
                            // Rows for each user in the group
                            items(users) { user ->
                                UserRow(user)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserRow(user: UserModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
            .border(1.dp, Color.LightGray),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "ID: ${user.id}",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = user.name.orEmpty(),
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.weight(3f),
            textAlign = TextAlign.Start
        )
    }
}

package com.fetch.rewards.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.WavyProgressIndicatorDefaults
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
import androidx.compose.ui.graphics.drawscope.Stroke
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
import fetchrewards.composeapp.generated.resources.fetch
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { navigator.pop() },
                    tint = Color.Gray
                )
               Image(
                   painter = painterResource(Res.drawable.fetch),
                   contentDescription = "fetch logo",
                   modifier = Modifier.width(170.dp).height(70.dp)
               )
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onEvent(UsersEvent.GetUsers) },
                    tint = Color.Gray
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when {
                state.isLoading -> {
                    // Loading State
                    CircularWavyProgressIndicator(
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.Center),
                        color = Color(0xFF464646),
                        stroke = Stroke(width = 4f)
                    )
                }
                state.isFailure -> {
                    // Failure State
                    Text(
                        text = state.errorMessage,
                        color = Color.Red,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
                state.isSuccess -> {
                    // Success State: Display users in a table format with animations
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = paddingValues,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        state.users.forEach { (listId, users) ->
                            // Header for each group (listId)
                            item {
                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn() + expandVertically(),
                                    exit = fadeOut() + shrinkVertically()
                                ) {
                                    Box(modifier = Modifier.padding(10.dp)){
                                        Text(
                                            text = "List ID: $listId",
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(Color(0xff300d38), RoundedCornerShape(8.dp))
                                                .padding(15.dp)
                                        )
                                    }
                                }
                            }
                            // Rows for each user in the group
                            items(users) { user ->
                                Box(modifier = Modifier.padding(horizontal = 10.dp)){
                                    UserRow(
                                        user = user,
                                        onClick = { selectedUser ->
                                            // Handle click event
                                            //navigator.push(DetailScreen(selectedUser))
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserRow(user: UserModel, onClick: (UserModel) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded; onClick(user) }
            .animateContentSize(), // Smooth expand/collapse animation
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
        backgroundColor = Color.White,
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "ID: ${user.id}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFfba919)
            )

            if (isExpanded) {
                Text(
                    text = user.name.orEmpty(),
                    fontSize = 16.sp,
                    color = Color(0xfffba919),
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = user.listId.toString(),
                    fontSize = 14.sp,
                    color = Color(0xfffba919),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

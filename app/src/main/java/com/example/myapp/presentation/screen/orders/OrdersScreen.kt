package com.example.myapp.presentation.screen.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapp.domain.model.Order
import com.example.myapp.domain.model.OrderStatus
import com.example.myapp.presentation.screen.theme.AppColors
import com.example.myapp.presentation.screen.theme.Dimens
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrdersScreen(
    onOrderSelected: (Order) -> Unit,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // Listen for navigation side effects
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is OrdersSideEffect.NavigateToDetails -> onOrderSelected(effect.order)
            }
        }
    }

    // Load orders when the composable appears
    LaunchedEffect(Unit) {
        viewModel.onEvent(OrdersEvent.LoadOrders)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.background)
    ) {
        OrdersHeader()
        StatusTabs(
            current = state.currentStatus,
            onStatusSelected = { status ->
                viewModel.onEvent(OrdersEvent.SelectStatus(status))
            }
        )
        OrdersContent(
            orders = state.filteredOrders,
            isLoading = state.isLoading,
            onRefresh = { viewModel.onEvent(OrdersEvent.LoadOrders) },
            onOrderClick = { order ->
                viewModel.onEvent(OrdersEvent.ClickOrder(order))
            }
        )
    }
}

@Composable
private fun OrdersHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.spaceMd, vertical = Dimens.spaceSm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
        Text(
            text = "My Orders",
            fontSize = 20.sp,
            color = AppColors.onBackground,
            fontWeight = FontWeight.SemiBold
        )
        Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
    }
}

@Composable
private fun StatusTabs(
    current: OrderStatus,
    onStatusSelected: (OrderStatus) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.spaceSm),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OrderStatus.values().forEach { status ->
            val isSelected = status == current
            val background = if (isSelected) AppColors.primary else AppColors.surface
            val foreground = if (isSelected) AppColors.onPrimary else AppColors.onSurface
            Text(
                text = status.displayName(),
                color = foreground,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.small)
                    .background(background)
                    .clickable { onStatusSelected(status) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun OrdersContent(
    orders: List<Order>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onOrderClick: (Order) -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    SwipeRefresh(state = swipeRefreshState, onRefresh = onRefresh) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(orders, key = { it.id }) { order ->
                OrderCard(order = order, onClick = { onOrderClick(order) })
            }
            if (orders.isEmpty() && !isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillParentMaxSize()
                            .padding(top = Dimens.spaceXl),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No orders available",
                            color = AppColors.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderCard(order: Order, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.spaceMd, vertical = Dimens.spaceSm)
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.surface)
                .padding(Dimens.spaceMd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(60.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(
                        when (order.status) {
                            OrderStatus.PENDING -> AppColors.pending
                            OrderStatus.DELIVERED -> AppColors.delivered
                            OrderStatus.CANCELED -> AppColors.canceled
                        }
                    )
            )

            Spacer(modifier = Modifier.padding(horizontal = Dimens.spaceSm))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Order No #${order.orderNumber}",
                            color = AppColors.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = formatOrderDate(order.dateMillis),
                            color = AppColors.onSurface.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(MaterialTheme.shapes.small)
                                .background(
                                    when (order.status) {
                                        OrderStatus.PENDING -> AppColors.pending
                                        OrderStatus.DELIVERED -> AppColors.delivered
                                        OrderStatus.CANCELED -> AppColors.canceled
                                    }
                                )
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = order.status.displayName(),
                            color = AppColors.onSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Tracking number",
                            color = AppColors.onSurface.copy(alpha = 0.6f),
                            fontSize = 10.sp
                        )
                        Text(
                            text = order.trackingNumber,
                            color = AppColors.onSurface,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Quantity",
                            color = AppColors.onSurface.copy(alpha = 0.6f),
                            fontSize = 10.sp
                        )
                        Text(
                            text = order.quantity.toString(),
                            color = AppColors.onSurface,
                            fontSize = 12.sp
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Subtotal",
                            color = AppColors.onSurface.copy(alpha = 0.6f),
                            fontSize = 10.sp
                        )
                        Text(
                            text = "$${"%.2f".format(order.subtotal)}",
                            color = AppColors.onSurface,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Details",
                tint = AppColors.onSurface
            )
        }
    }
}

private fun formatOrderDate(timeMillis: Long): String {
    val date = Date(timeMillis)
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(date)
}

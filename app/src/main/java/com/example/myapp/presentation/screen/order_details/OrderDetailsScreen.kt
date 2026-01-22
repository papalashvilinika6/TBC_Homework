package com.example.myapp.presentation.screen.order_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapp.domain.model.Order
import com.example.myapp.domain.model.OrderStatus
import com.example.myapp.presentation.screen.orders.OrdersEvent
import com.example.myapp.presentation.screen.orders.OrdersViewModel
import com.example.myapp.presentation.screen.theme.AppColors
import com.example.myapp.presentation.screen.theme.Dimens
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun OrderDetailsScreen(
    order: Order,
    onNavigateBack: () -> Unit,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val canModify = order.status == OrderStatus.PENDING

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.background)
            .padding(Dimens.spaceMd),
        verticalArrangement = Arrangement.spacedBy(Dimens.spaceMd)
    ) {
        Text(
            text = "Order Details",
            fontSize = 22.sp,
            color = AppColors.onBackground,
            fontWeight = FontWeight.Bold
        )

        DetailRow(label = "Order number", value = order.orderNumber)
        DetailRow(label = "Date", value = formatOrderDate(order.dateMillis))
        DetailRow(label = "Tracking number", value = order.trackingNumber)
        DetailRow(label = "Quantity", value = order.quantity.toString())
        DetailRow(label = "Subtotal", value = "$${"%.2f".format(order.subtotal)}")
        DetailRow(label = "Status", value = order.status.displayName())

        if (canModify) {
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceMd)) {
                Button(
                    onClick = {
                        viewModel.onEvent(
                            OrdersEvent.UpdateOrderStatus(order.id, OrderStatus.DELIVERED)
                        )
                        onNavigateBack()
                    },
                    colors = ButtonDefaults.buttonColors(AppColors.delivered)
                ) {
                    Text(text = "Mark as Delivered", color = AppColors.onPrimary)
                }
                Button(
                    onClick = {
                        viewModel.onEvent(
                            OrdersEvent.UpdateOrderStatus(order.id, OrderStatus.CANCELED)
                        )
                        onNavigateBack()
                    },
                    colors = ButtonDefaults.buttonColors(AppColors.canceled)
                ) {
                    Text(text = "Cancel Order", color = AppColors.onPrimary)
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = AppColors.onSurface, fontWeight = FontWeight.Medium)
        Text(text = value, color = AppColors.onSurface, fontWeight = FontWeight.Normal)
    }
}

private fun formatOrderDate(timeMillis: Long): String {
    val date = Date(timeMillis)
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(date)
}

package com.ctucl.busstationsettings.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import com.ctucl.busstationsettings.domain.model.TransactionResponse
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TransactionCard(transaction: TransactionResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Transaction ID: ${transaction.id}")
            Text(text = "Code: ${transaction.code}")
            Text(text = "Type: ${transaction.type}")
            Text(text = "Date: ${transaction.date_card} ${transaction.time_card}")
            Text(text = "Place: ${transaction.place}")
            Text(text = "Cost: $${transaction.cost}")
            Text(text = "Previous: $${transaction.previous}")
            Text(text = "Balance: $${transaction.balance}")
            Text(text = "UUID: ${transaction.uuid}")
            Text(text = "Location: (${transaction.lat}, ${transaction.lon})")
            Text(text = "Transaction Date: ${transaction.date}")
        }
    }
}
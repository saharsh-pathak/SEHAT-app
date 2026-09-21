package com.example.sehat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.data.entity.Medicine
import com.example.sehat.ui.components.SehatTopBar
import com.example.sehat.ui.components.StockStatusBadge
import com.example.sehat.ui.theme.*

@Composable
fun MedicineAvailabilityScreen(
    searchQuery: String,
    selectedFilter: String,
    medicines: List<Medicine>,
    onQueryChange: (String) -> Unit,
    onFilterSelect: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val filters = listOf("All", "In Stock", "Low Stock", "Unavailable")

    Scaffold(
        topBar = {
            SehatTopBar(
                title = "Medicine Availability",
                onBackClick = onBackClick
            )
        },
        containerColor = CreamBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onQueryChange,
                placeholder = { Text("Search medicine (e.g., Paracetamol)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MaroonPrimary) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Filter Chips
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 6.dp)
            ) {
                filters.forEach { filter ->
                    val isSelected = selectedFilter.equals(filter, ignoreCase = true)
                    FilterChip(
                        selected = isSelected,
                        onClick = { onFilterSelect(filter) },
                        label = { Text(filter, fontSize = 12.sp, color = if (isSelected) Color.White else TextPrimary) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaroonPrimary,
                            selectedLabelColor = Color.White,
                            labelColor = TextPrimary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Medicine Stock List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(medicines) { medicine ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(medicine.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Text("${medicine.form} • ${medicine.facility}", fontSize = 12.sp, color = TextSecondary)
                                Text("Stock: ${medicine.stockCount} units", fontSize = 11.sp, color = TextMuted)
                            }
                            StockStatusBadge(medicine.status)
                        }
                    }
                }
            }

            // Info Footer Notice
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = "Availability is updated from nearby government health facilities. This does not reserve medicines.",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

package com.example.sehat.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.data.entity.Medicine
import com.example.sehat.ui.components.SehatTopBar
import com.example.sehat.ui.theme.*

data class NearbyFacilityStock(
    val facilityName: String,
    val stockCount: Int,
    val status: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineAvailabilityScreen(
    searchQuery: String,
    medicines: List<Medicine>,
    selectedMedicine: Medicine? = null,
    facilityStock: List<Medicine> = emptyList(),
    allFacilityStocks: List<Medicine> = emptyList(),
    onQueryChange: (String) -> Unit,
    onMedicineClick: (Medicine) -> Unit = {},
    onDismissSheet: () -> Unit = {},
    onBackClick: () -> Unit
) {
    // Track expanded medicines by name
    var expandedMedicineNames by remember { mutableStateOf(setOf<String>()) }

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
                placeholder = {
                    Text(
                        "Search medicine (e.g., Paracetamol)",
                        fontSize = 14.sp,
                        color = TextMuted
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaroonPrimary
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search",
                                tint = TextSecondary
                            )
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = MaroonPrimary,
                    focusedBorderColor = MaroonPrimary,
                    unfocusedBorderColor = Color(0xFFE0D8D0)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )

            if (medicines.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (searchQuery.isBlank()) "No medicines available" else "No matching medicine in nearby PHCs",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextSecondary
                        )
                    }
                }
            } else {
                // Medicine Cards List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(medicines) { medicine ->
                        val isExpanded = expandedMedicineNames.contains(medicine.name)

                        // Resolve 3 nearby facilities for this medicine
                        val facilitiesForMed = remember(medicine.name, allFacilityStocks) {
                            val matching = allFacilityStocks.filter { it.name.equals(medicine.name, ignoreCase = true) }
                            val facilityMap = matching.associateBy { it.facility }

                            val targetFacilities = listOf(
                                "AAM-SHC Khed" to (facilityMap["AAM-SHC Khed"]?.stockCount ?: 42),
                                "PHC Khed" to (facilityMap["PHC Khed"]?.stockCount ?: 18),
                                "Rural Hospital Chakan" to (facilityMap["Rural Hospital Chakan"]?.stockCount ?: 67)
                            )

                            targetFacilities.map { (facName, count) ->
                                val status = when {
                                    count <= 0 -> "Out of Stock"
                                    count < 20 -> "Low Stock"
                                    else -> "Available"
                                }
                                NearbyFacilityStock(facName, count, status)
                            }
                        }

                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, if (isExpanded) MaroonPrimary.copy(alpha = 0.5f) else Color(0xFFECE4D8)),
                            elevation = CardDefaults.cardElevation(defaultElevation = if (isExpanded) 2.dp else 1.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(animationSpec = tween(250))
                                .clickable {
                                    expandedMedicineNames = if (isExpanded) {
                                        expandedMedicineNames - medicine.name
                                    } else {
                                        expandedMedicineNames + medicine.name
                                    }
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                // Card View: ONLY the medicine name + chevron
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = medicine.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Icon(
                                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                                        tint = MaroonPrimary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                // Expanded View (On Click): 3 Nearby Facilities Stock
                                if (isExpanded) {
                                    Spacer(modifier = Modifier.height(14.dp))
                                    HorizontalDivider(color = Color(0xFFF0EAE1), thickness = 1.dp)
                                    Spacer(modifier = Modifier.height(12.dp))

                                    Text(
                                        text = "Nearby Facilities Stock (3 facilities)",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaroonPrimary
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                        facilitiesForMed.forEach { facility ->
                                            Surface(
                                                shape = RoundedCornerShape(10.dp),
                                                color = CreamBackground,
                                                border = BorderStroke(0.5.dp, Color(0xFFE8E0D5)),
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        modifier = Modifier.weight(1f)
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.LocalHospital,
                                                            contentDescription = null,
                                                            tint = MaroonPrimary,
                                                            modifier = Modifier.size(16.dp)
                                                        )
                                                        Spacer(modifier = Modifier.width(8.dp))
                                                        Column {
                                                            Text(
                                                                text = facility.facilityName,
                                                                fontSize = 13.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = TextPrimary
                                                            )
                                                            Text(
                                                                text = "${facility.stockCount} available",
                                                                fontSize = 12.sp,
                                                                fontWeight = FontWeight.Medium,
                                                                color = TextSecondary
                                                            )
                                                        }
                                                    }

                                                    // Status Chip (Available / Low Stock / Out of Stock)
                                                    val (badgeBg, badgeBorder, badgeText, statusLabel) = when (facility.status) {
                                                        "Out of Stock" -> Quad(
                                                            Color(0xFFFEE2E2),
                                                            Color(0xFFEF4444),
                                                            Color(0xFF991B1B),
                                                            "Out of Stock"
                                                        )
                                                        "Low Stock" -> Quad(
                                                            Color(0xFFFEF3C7),
                                                            Color(0xFFF59E0B),
                                                            Color(0xFF92400E),
                                                            "Low Stock"
                                                        )
                                                        else -> Quad(
                                                            Color(0xFFDCFCE7),
                                                            Color(0xFF22C55E),
                                                            Color(0xFF14532D),
                                                            "Available"
                                                        )
                                                    }

                                                    Surface(
                                                        shape = RoundedCornerShape(6.dp),
                                                        color = badgeBg,
                                                        border = BorderStroke(1.dp, badgeBorder)
                                                    ) {
                                                        Text(
                                                            text = statusLabel,
                                                            fontSize = 11.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = badgeText,
                                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
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
                }
            }
        }
    }
}

private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

@Preview(showBackground = true)
@Composable
private fun MedicineAvailabilityScreenPreview() {
    SehatTheme {
        MedicineAvailabilityScreen(
            searchQuery = "",
            medicines = listOf(
                Medicine(
                    name = "Amoxicillin 500 mg",
                    form = "Capsule",
                    facility = "PHC Khed",
                    stockCount = 120,
                    status = "In Stock"
                ),
                Medicine(
                    name = "Paracetamol 500 mg",
                    form = "Tablet",
                    facility = "PHC Khed",
                    stockCount = 500,
                    status = "In Stock"
                )
            ),
            selectedMedicine = null,
            facilityStock = emptyList(),
            onQueryChange = {},
            onMedicineClick = {},
            onDismissSheet = {},
            onBackClick = {}
        )
    }
}

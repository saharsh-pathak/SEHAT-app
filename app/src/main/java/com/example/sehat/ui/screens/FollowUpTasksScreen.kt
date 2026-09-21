package com.example.sehat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.data.entity.FollowUpTask
import com.example.sehat.ui.components.SehatTopBar
import com.example.sehat.ui.theme.*

@Composable
fun FollowUpTasksScreen(
    selectedTab: String,
    tasks: List<FollowUpTask>,
    onTabSelect: (String) -> Unit,
    onToggleTaskStatus: (FollowUpTask) -> Unit,
    onBackClick: () -> Unit
) {
    val tabs = listOf("Pending", "Completed", "All")

    Scaffold(
        topBar = {
            SehatTopBar(
                title = "Follow-up Tasks",
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
            // Tab Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(CreamSurface, shape = RoundedCornerShape(10.dp))
                    .padding(4.dp)
            ) {
                tabs.forEach { tab ->
                    val isSelected = selectedTab.equals(tab, ignoreCase = true)
                    Button(
                        onClick = { onTabSelect(tab) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) MaroonPrimary else Color.Transparent,
                            contentColor = if (isSelected) Color.White else TextPrimary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            tab,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSelected) Color.White else TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Task List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(tasks) { task ->
                    var isExpanded by remember { mutableStateOf(false) }

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isExpanded = !isExpanded }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(task.patientName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    Text(task.taskTitle, fontSize = 13.sp, color = MaroonPrimary, fontWeight = FontWeight.SemiBold)
                                    Text("Due: ${task.dueDate} | Village: ${task.village}", fontSize = 12.sp, color = TextSecondary)
                                }
                                Button(
                                    onClick = { onToggleTaskStatus(task) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (task.status == "Pending") SeverityModerateBg else SeverityMildBg,
                                        contentColor = if (task.status == "Pending") SeverityModerate else SeverityMild
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(task.status, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            if (isExpanded) {
                                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = SurfaceVariant)

                                Text("Follow-up Checklist", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Spacer(modifier = Modifier.height(6.dp))

                                val checklist = remember(task.checklistJson) {
                                    try {
                                        task.checklistJson
                                            .removeSurrounding("[", "]")
                                            .split("\",\"")
                                            .map { it.replace("\"", "") }
                                    } catch (e: Exception) {
                                        emptyList()
                                    }
                                }

                                checklist.forEach { item ->
                                    var checked by remember { mutableStateOf(task.status == "Completed") }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 2.dp)
                                    ) {
                                        Checkbox(
                                            checked = checked,
                                            onCheckedChange = { checked = it },
                                            colors = CheckboxDefaults.colors(checkedColor = MaroonPrimary)
                                        )
                                        Text(item, fontSize = 13.sp, color = TextPrimary)
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

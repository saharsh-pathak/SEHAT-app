package com.example.sehat.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.sehat.ui.screens.*
import com.example.sehat.viewmodel.*

object SehatRoutes {
    const val DASHBOARD = "dashboard"
    const val SEARCH_PATIENT = "search_patient"
    const val PATIENT_DETAILS = "patient_details/{abhaId}"
    const val SYMPTOMS = "symptoms/{abhaId}"
    const val BASIC_TESTS = "basic_tests/{episodeId}"
    const val TRIAGE_RESULT = "triage_result/{episodeId}"
    const val PATIENT_TIMELINE = "patient_timeline/{abhaId}"
    const val REFERRAL_MANAGEMENT = "referral_management/{episodeId}/{abhaId}"
    const val APPOINTMENT_CONFIRMATION = "appointment_confirmation/{referralId}"
    const val MEDICINE_AVAILABILITY = "medicine_availability"
    const val FOLLOW_UP_TASKS = "follow_up_tasks"
    const val LANGUAGE_SELECTION = "language_selection"
}

@Composable
fun SehatNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val dashboardViewModel: DashboardViewModel = viewModel()
    val patientViewModel: PatientViewModel = viewModel()
    val patientDetailsViewModel: PatientDetailsViewModel = viewModel()
    val careEpisodeViewModel: CareEpisodeViewModel = viewModel()
    val timelineViewModel: TimelineViewModel = viewModel()
    val referralViewModel: ReferralViewModel = viewModel()
    val medicineViewModel: MedicineViewModel = viewModel()
    val followUpViewModel: FollowUpViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = SehatRoutes.DASHBOARD
    ) {
        // Screen 1: Dashboard
        composable(
            route = SehatRoutes.DASHBOARD,
            deepLinks = listOf(navDeepLink { uriPattern = "sehat://dashboard" })
        ) {
            val pendingCount by dashboardViewModel.pendingFollowUpsCount.collectAsState()
            DashboardScreen(
                pendingFollowUpsCount = pendingCount,
                schedule = dashboardViewModel.todaySchedule,
                onSearchPatientClick = { navController.navigate(SehatRoutes.SEARCH_PATIENT) },
                onMedicineClick = { navController.navigate(SehatRoutes.MEDICINE_AVAILABILITY) },
                onFollowUpClick = { navController.navigate(SehatRoutes.FOLLOW_UP_TASKS) },
                onLanguageClick = { navController.navigate(SehatRoutes.LANGUAGE_SELECTION) }
            )
        }

        // Screen 2: Search Patient
        composable(
            route = SehatRoutes.SEARCH_PATIENT,
            deepLinks = listOf(navDeepLink { uriPattern = "sehat://search_patient" })
        ) {
            val query by patientViewModel.searchQuery.collectAsState()
            val patients by patientViewModel.patients.collectAsState()

            SearchPatientScreen(
                searchQuery = query,
                patients = patients,
                onQueryChange = { patientViewModel.updateSearchQuery(it) },
                onPatientSelect = { patient ->
                    navController.navigate("patient_details/${patient.abhaId}")
                },
                onCreateAbha = { name, age, gender, village, aadhaar, mobile ->
                    patientViewModel.createAbhaPatient(name, age, gender, village, aadhaar, mobile) { newPatient ->
                        navController.navigate("patient_details/${newPatient.abhaId}")
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Screen: Patient Details
        composable(
            route = SehatRoutes.PATIENT_DETAILS,
            arguments = listOf(navArgument("abhaId") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = "sehat://patient_details/{abhaId}" })
        ) { backStackEntry ->
            val abhaId = backStackEntry.arguments?.getString("abhaId") ?: ""
            val patientState by patientDetailsViewModel.patientState.collectAsState()

            LaunchedEffect(abhaId) {
                patientDetailsViewModel.loadPatientDetails(abhaId)
            }

            PatientDetailsScreen(
                state = patientState,
                onStartScreeningClick = {
                    careEpisodeViewModel.startEpisode(abhaId) { episodeId ->
                        navController.navigate("symptoms/$abhaId")
                    }
                },
                onVoiceScreeningClick = {
                    careEpisodeViewModel.startEpisode(abhaId) { episodeId ->
                        navController.navigate("symptoms/$abhaId")
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Screen 3: Symptom Collection
        composable(
            route = SehatRoutes.SYMPTOMS,
            arguments = listOf(navArgument("abhaId") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = "sehat://symptoms/{abhaId}" })
        ) { backStackEntry ->
            val abhaId = backStackEntry.arguments?.getString("abhaId") ?: ""
            val episodeState by careEpisodeViewModel.state.collectAsState()

            SymptomCollectionScreen(
                onNextClick = { transcript, manualText, selectedChips ->
                    careEpisodeViewModel.updateSymptoms(transcript, manualText, selectedChips)
                    navController.navigate("basic_tests/${episodeState.episodeId}")
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Screen 4: Basic Screening Tests
        composable(
            route = SehatRoutes.BASIC_TESTS,
            arguments = listOf(navArgument("episodeId") { type = NavType.LongType }),
            deepLinks = listOf(navDeepLink { uriPattern = "sehat://basic_tests/{episodeId}" })
        ) { backStackEntry ->
            val episodeId = backStackEntry.arguments?.getLong("episodeId") ?: 0L

            BasicScreeningTestsScreen(
                onNextClick = { bp, hr, spo2, temp, bg, hb, wt, ht, preg, mal, den, uri, tb, notes ->
                    careEpisodeViewModel.updateVitalsAndTests(
                        bp, hr, spo2, temp, bg, hb, wt, ht, preg, mal, den, uri, tb, notes
                    )
                    navController.navigate("triage_result/$episodeId")
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Screen 5: Severity Assessment / Triage Result
        composable(
            route = SehatRoutes.TRIAGE_RESULT,
            arguments = listOf(navArgument("episodeId") { type = NavType.LongType }),
            deepLinks = listOf(navDeepLink { uriPattern = "sehat://triage_result/{episodeId}" })
        ) { backStackEntry ->
            val episodeState by careEpisodeViewModel.state.collectAsState()

            TriageResultScreen(
                state = episodeState,
                onNotesChange = { careEpisodeViewModel.updateTriageNotes(it) },
                onConfirmClick = {
                    careEpisodeViewModel.saveFullEpisode {
                        navController.navigate("patient_timeline/${episodeState.abhaId}")
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Screen 6: Patient Timeline
        composable(
            route = SehatRoutes.PATIENT_TIMELINE,
            arguments = listOf(navArgument("abhaId") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = "sehat://patient_timeline/{abhaId}" })
        ) { backStackEntry ->
            val abhaId = backStackEntry.arguments?.getString("abhaId") ?: ""
            val episodeState by careEpisodeViewModel.state.collectAsState()
            val patient by timelineViewModel.patient.collectAsState()

            LaunchedEffect(abhaId) {
                timelineViewModel.loadPatient(abhaId)
            }

            PatientTimelineScreen(
                patient = patient,
                timelineItems = timelineViewModel.timelineItems,
                onCreateReferralClick = {
                    navController.navigate("referral_management/${episodeState.episodeId}/$abhaId")
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Screen 7: Referral Management
        composable(
            route = SehatRoutes.REFERRAL_MANAGEMENT,
            arguments = listOf(
                navArgument("episodeId") { type = NavType.LongType },
                navArgument("abhaId") { type = NavType.StringType }
            ),
            deepLinks = listOf(navDeepLink { uriPattern = "sehat://referral_management/{episodeId}/{abhaId}" })
        ) { backStackEntry ->
            val episodeId = backStackEntry.arguments?.getLong("episodeId") ?: 0L
            val abhaId = backStackEntry.arguments?.getString("abhaId") ?: ""

            ReferralManagementScreen(
                patientAbhaId = abhaId,
                onCreateReferral = { destinationFacility, notes ->
                    referralViewModel.createReferralAndAutoBook(episodeId, abhaId, destinationFacility, notes) { refId ->
                        navController.navigate("appointment_confirmation/$refId")
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Screen 8: Appointment Confirmation
        composable(
            route = SehatRoutes.APPOINTMENT_CONFIRMATION,
            arguments = listOf(navArgument("referralId") { type = NavType.LongType }),
            deepLinks = listOf(navDeepLink { uriPattern = "sehat://appointment_confirmation/{referralId}" })
        ) {
            val appointment by referralViewModel.createdAppointment.collectAsState()

            AppointmentConfirmationScreen(
                appointment = appointment,
                onDoneClick = {
                    navController.navigate(SehatRoutes.DASHBOARD) {
                        popUpTo(SehatRoutes.DASHBOARD) { inclusive = true }
                    }
                }
            )
        }

        // Screen 9: Medicine Availability
        composable(
            route = SehatRoutes.MEDICINE_AVAILABILITY,
            deepLinks = listOf(navDeepLink { uriPattern = "sehat://medicine_availability" })
        ) {
            val query by medicineViewModel.searchQuery.collectAsState()
            val medicines by medicineViewModel.medicines.collectAsState()
            val selectedMed by medicineViewModel.selectedMedicine.collectAsState()
            val facilityStock by medicineViewModel.facilityStock.collectAsState()

            MedicineAvailabilityScreen(
                searchQuery = query,
                medicines = medicines,
                selectedMedicine = selectedMed,
                facilityStock = facilityStock,
                onQueryChange = { medicineViewModel.updateSearch(it) },
                onMedicineClick = { medicineViewModel.selectMedicine(it) },
                onDismissSheet = { medicineViewModel.selectMedicine(null) },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Screen 10: Follow-up Tasks
        composable(
            route = SehatRoutes.FOLLOW_UP_TASKS,
            deepLinks = listOf(navDeepLink { uriPattern = "sehat://follow_up_tasks" })
        ) {
            val selectedTab by followUpViewModel.selectedTab.collectAsState()
            val tasks by followUpViewModel.tasks.collectAsState()

            FollowUpTasksScreen(
                selectedTab = selectedTab,
                tasks = tasks,
                onTabSelect = { followUpViewModel.selectTab(it) },
                onToggleTaskStatus = { followUpViewModel.toggleTaskStatus(it) },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Screen 11: Language Selection
        composable(
            route = SehatRoutes.LANGUAGE_SELECTION,
            deepLinks = listOf(navDeepLink { uriPattern = "sehat://language_selection" })
        ) {
            val language by settingsViewModel.selectedLanguage.collectAsState()

            LanguageSelectionScreen(
                selectedLanguage = language,
                onLanguageSelect = { settingsViewModel.selectLanguage(it) },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

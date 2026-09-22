package com.example.sehat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.sehat.data.dao.*
import com.example.sehat.data.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Patient::class,
        CareEpisode::class,
        Symptom::class,
        Vitals::class,
        ScreeningTest::class,
        TriageResult::class,
        Referral::class,
        Appointment::class,
        Medicine::class,
        FollowUpTask::class,
        SyncQueue::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SehatDatabase : RoomDatabase() {

    abstract fun patientDao(): PatientDao
    abstract fun careEpisodeDao(): CareEpisodeDao
    abstract fun symptomDao(): SymptomDao
    abstract fun vitalsDao(): VitalsDao
    abstract fun screeningTestDao(): ScreeningTestDao
    abstract fun triageResultDao(): TriageResultDao
    abstract fun referralDao(): ReferralDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun medicineDao(): MedicineDao
    abstract fun followUpTaskDao(): FollowUpTaskDao
    abstract fun syncQueueDao(): SyncQueueDao

    companion object {
        @Volatile
        private var INSTANCE: SehatDatabase? = null

        fun get(context: Context): SehatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SehatDatabase::class.java,
                    "sehat_db"
                )
                .addCallback(DatabaseCallback(context))
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                populateSampleData(get(context))
            }
        }
    }
}

private suspend fun populateSampleData(db: SehatDatabase) {
    // 1. Initial Patients
    val patients = listOf(
        Patient(abhaId = "1234 5678 9012", name = "Sita Devi", age = 34, gender = "Female", village = "Khed", mobile = "9876543210"),
        Patient(abhaId = "9876 5432 1098", name = "Ramesh Pawar", age = 52, gender = "Male", village = "Khed", mobile = "9876500001"),
        Patient(abhaId = "1111 2222 3333", name = "Lata Shinde", age = 28, gender = "Female", village = "Nandgaon", mobile = "9876500002")
    )
    db.patientDao().insertAll(patients)

    // 2. Initial Medicines (Distributed across nearby PHCs)
    val medicines = listOf(
        // Paracetamol 500 mg
        Medicine(name = "Paracetamol 500 mg", form = "Tablet", facility = "PHC Khed", stockCount = 500, status = "In Stock"),
        Medicine(name = "Paracetamol 500 mg", form = "Tablet", facility = "PHC Chakan", stockCount = 320, status = "In Stock"),
        Medicine(name = "Paracetamol 500 mg", form = "Tablet", facility = "PHC Alandi", stockCount = 210, status = "In Stock"),
        Medicine(name = "Paracetamol 500 mg", form = "Tablet", facility = "PHC Nandgaon", stockCount = 80, status = "In Stock"),

        // Amoxicillin 500 mg
        Medicine(name = "Amoxicillin 500 mg", form = "Capsule", facility = "PHC Khed", stockCount = 120, status = "In Stock"),
        Medicine(name = "Amoxicillin 500 mg", form = "Capsule", facility = "PHC Chakan", stockCount = 95, status = "In Stock"),
        Medicine(name = "Amoxicillin 500 mg", form = "Capsule", facility = "PHC Alandi", stockCount = 60, status = "In Stock"),

        // Cough Syrup
        Medicine(name = "Cough Syrup", form = "Syrup", facility = "PHC Khed", stockCount = 65, status = "In Stock"),
        Medicine(name = "Cough Syrup", form = "Syrup", facility = "PHC Alandi", stockCount = 40, status = "In Stock"),
        Medicine(name = "Cough Syrup", form = "Syrup", facility = "PHC Nandgaon", stockCount = 25, status = "In Stock"),

        // Cetirizine 10 mg
        Medicine(name = "Cetirizine 10 mg", form = "Tablet", facility = "PHC Chakan", stockCount = 85, status = "In Stock"),
        Medicine(name = "Cetirizine 10 mg", form = "Tablet", facility = "PHC Khed", stockCount = 50, status = "In Stock"),
        Medicine(name = "Cetirizine 10 mg", form = "Tablet", facility = "PHC Alandi", stockCount = 30, status = "In Stock"),

        // Iron Tablets (IFA)
        Medicine(name = "Iron Tablets (IFA)", form = "Tablet", facility = "PHC Nandgaon", stockCount = 140, status = "In Stock"),
        Medicine(name = "Iron Tablets (IFA)", form = "Tablet", facility = "PHC Khed", stockCount = 100, status = "In Stock"),
        Medicine(name = "Iron Tablets (IFA)", form = "Tablet", facility = "PHC Chakan", stockCount = 75, status = "In Stock"),

        // ORS
        Medicine(name = "ORS", form = "Sachet", facility = "PHC Khed", stockCount = 200, status = "In Stock"),
        Medicine(name = "ORS", form = "Sachet", facility = "PHC Chakan", stockCount = 150, status = "In Stock"),
        Medicine(name = "ORS", form = "Sachet", facility = "PHC Nandgaon", stockCount = 110, status = "In Stock"),

        // Azithromycin 500 mg
        Medicine(name = "Azithromycin 500 mg", form = "Tablet", facility = "PHC Alandi", stockCount = 95, status = "In Stock"),
        Medicine(name = "Azithromycin 500 mg", form = "Tablet", facility = "PHC Khed", stockCount = 70, status = "In Stock"),

        // Antacid Syrup
        Medicine(name = "Antacid Syrup", form = "Syrup", facility = "PHC Alandi", stockCount = 40, status = "In Stock"),
        Medicine(name = "Antacid Syrup", form = "Syrup", facility = "PHC Khed", stockCount = 35, status = "In Stock"),

        // Omeprazole 20 mg
        Medicine(name = "Omeprazole 20 mg", form = "Capsule", facility = "PHC Chakan", stockCount = 110, status = "In Stock"),
        Medicine(name = "Omeprazole 20 mg", form = "Capsule", facility = "PHC Khed", stockCount = 80, status = "In Stock")
    )
    db.medicineDao().insertAll(medicines)

    // 3. Initial Follow-up Tasks
    val tasks = listOf(
        FollowUpTask(
            episodeId = 1,
            patientAbhaId = "1234 5678 9012",
            patientName = "Sita Devi",
            village = "Khed",
            taskTitle = "Post-treatment follow-up",
            dueDate = "19 Sep 2025",
            status = "Pending",
            checklistJson = "[\"Medicine taken as prescribed\",\"Symptoms improved\",\"Any side effects\",\"Measure vital signs (BP / Sugar / Weight)\",\"Add follow-up notes\"]"
        ),
        FollowUpTask(
            episodeId = 2,
            patientAbhaId = "9876 5432 1098",
            patientName = "Ramesh Pawar",
            village = "Khed",
            taskTitle = "Hypertension follow-up",
            dueDate = "21 Sep 2025",
            status = "Pending",
            checklistJson = "[\"Measure Blood Pressure\",\"Check adherence to BP medicine\",\"Symptom check\"]"
        ),
        FollowUpTask(
            episodeId = 3,
            patientAbhaId = "1111 2222 3333",
            patientName = "Lata Shinde",
            village = "Nandgaon",
            taskTitle = "ANC follow-up",
            dueDate = "22 Sep 2025",
            status = "Completed",
            checklistJson = "[\"Check weight and blood pressure\",\"Check hemoglobin report\",\"Distribute IFA tablets\"]"
        )
    )
    db.followUpTaskDao().insertAll(tasks)
}

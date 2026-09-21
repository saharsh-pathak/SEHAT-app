package com.example.sehat.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.sehat.data.SehatDatabase
import kotlinx.coroutines.flow.first

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val db = SehatDatabase.get(applicationContext)
            val unsyncedItems = db.syncQueueDao().getUnsynced().first()

            for (item in unsyncedItems) {
                // Stub offline sync: simulate pushing to server
                db.syncQueueDao().markSynced(item.id)
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}

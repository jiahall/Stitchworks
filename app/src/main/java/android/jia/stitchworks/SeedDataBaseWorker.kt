package android.jia.stitchworks

import android.content.Context
import android.jia.stitchworks.data.Skein
import android.jia.stitchworks.data.AppDatabase
import android.util.Log
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.work.CoroutineWorker
import com.google.gson.stream.JsonReader

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
    ) : CoroutineWorker(context, workerParams) {
        override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
            try {
                val filename = inputData.getString(KEY_FILENAME)
                if (filename != null) {
                    applicationContext.assets.open(filename).use { inputStream ->
                        JsonReader(inputStream.reader()).use { jsonReader ->
                            val skeinType = object : TypeToken<List<Skein>>() {}.type
                            val skeinList: List<Skein> = Gson().fromJson(jsonReader, skeinType)

                            val database = AppDatabase.getInstance(applicationContext)
                            database.skeinDao.insertAll(skeinList)

                            Result.success()
                        }
                    }
                } else {
                    Log.e(TAG, "Error seeding database - no valid filename")
                    Result.failure()
                }
            } catch (ex: Exception) {
                Log.e(TAG, "Error seeding database", ex)
                Result.failure()
            }
        }

        companion object {
            private const val TAG = "SkeinDatabaseWorker"
            const val KEY_FILENAME = "SKEIN_DATA_FILENAME"
        }
    }
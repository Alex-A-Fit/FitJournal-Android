package com.example.fitjournal.core.data.repository

import com.example.fitjournal.FitJournal
import com.example.fitjournal.core.data.mockdata.MockData
import com.example.fitjournal.core.data.model.realmdb.workout.RealmWorkoutEntry
import com.example.fitjournal.core.data.util.getLatestResultViaQuery
import com.example.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class RealmWorkoutEntryRepositoryImpl @Inject constructor() : RealmWorkoutEntryRepository {
    private val realm = FitJournal.realm

    override suspend fun addMockDataToRealm() {
        // this is how we would write workout to realm db
        realm.write {
            val workouts: RealmList<RealmWorkoutEntry> = realmListOf()
            workouts.addAll(
                listOf(
                    MockData.weightTraining1(createWorkoutId()),
                    MockData.weightTraining2(createWorkoutId()),
                    MockData.calisthenics1(createWorkoutId()),
                    MockData.calisthenics2(createWorkoutId()),
                    MockData.calisthenics3(createWorkoutId()),
                    MockData.cardio1(createWorkoutId()),
                    MockData.cardio2(createWorkoutId())
                )
            )
            // running for each to simplify adding each individual workout entry
            workouts.forEach {
                copyToRealm(it, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }

    // query realm db and find values based on query
    // check if values exist
    override suspend fun getRealmWorkoutEntryList(): List<RealmWorkoutEntry> {
        val query = realm.query<RealmWorkoutEntry>().find()
        val list = query.toRealmList().toList()
        if (list.isNotEmpty()) {
            return list
        }
        return emptyList()
    }

    override suspend fun getSingleRealmWorkoutEntry(workoutId: String): RealmWorkoutEntry? {
        return realm.query<RealmWorkoutEntry>(
            RealmWorkoutEntry::class,
            query = "workoutId == $0",
            workoutId
        )
            .find()
            .firstOrNull()
    }

    override suspend fun addSingleWorkoutEntryToRealmDb(realmWorkoutEntry: RealmWorkoutEntry): Boolean {
        return realm.write {
            return@write try {
                copyToRealm(realmWorkoutEntry, updatePolicy = UpdatePolicy.ALL)
                true
            } catch (e: IllegalArgumentException) {
                // catch for copyToRealm() in case it throws error
                false
            } catch (e: Exception) {
                // general error catch
                false
            }
        }
    }

    override suspend fun updateSingleWorkoutEntryToRealmDb(
        updatedRealmWorkoutEntry: RealmWorkoutEntry
    ): Boolean {
        return realm.write {
            return@write try {
                val wasUpdateSuccessful: Boolean
                val originalRealmWorkoutEntry = this.getLatestResultViaQuery(
                    searchableClass = RealmWorkoutEntry::class,
                    query = "workoutId == $0",
                    queryValue = updatedRealmWorkoutEntry.workoutId
                )
                if (originalRealmWorkoutEntry != null) {
                    originalRealmWorkoutEntry.workout = updatedRealmWorkoutEntry.workout
                    copyToRealm(originalRealmWorkoutEntry, updatePolicy = UpdatePolicy.ALL)
                    wasUpdateSuccessful = true
                } else {
                    wasUpdateSuccessful = false
                }
                wasUpdateSuccessful
            } catch (e: IllegalArgumentException) {
                // catch for copyToRealm() in case it throws error
                false
            } catch (e: Exception) {
                // general error catch
                false
            }
        }
    }

    override suspend fun deleteWorkoutEntryFromRealmDb(realmWorkoutId: String): Boolean {
        return realm.write {
            return@write try {
                val wasWorkoutDeleted: Boolean
                this.getLatestResultViaQuery(
                    searchableClass = RealmWorkoutEntry::class,
                    query = "workoutId == $0",
                    queryValue = realmWorkoutId
                ).also {
                    wasWorkoutDeleted = if (it != null) {
                        delete(it)
                        true
                    } else {
                        false
                    }
                }
                wasWorkoutDeleted
            } catch (e: IllegalArgumentException) {
                // catch for copyToRealm() in case it throws error
                false
            } catch (e: Exception) {
                // general error catch
                false
            }
        }
    }

    private fun createWorkoutId(): String {
        return ObjectId().toHexString()
    }
}

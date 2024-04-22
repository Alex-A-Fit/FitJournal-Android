package com.example.fitjournal.core.data.repository

import com.example.fitjournal.FitJournal
import com.example.fitjournal.core.data.mockdata.MockData
import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import com.example.fitjournal.core.data.util.GetLatestQueryViaId
import com.example.fitjournal.core.domain.repository.RealmRepository
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import javax.inject.Inject

class RealmRepositoryImpl @Inject constructor() : RealmRepository {
    private val realm = FitJournal.realm

    override suspend fun addMockDataToRealm() {
        // this is how we would write workout to realm db
        realm.write {
            val workouts: RealmList<RealmWorkoutEntry> = realmListOf()
            workouts.addAll(
                listOf(
                    MockData.weightTraining1,
                    MockData.weightTraining2,
                    MockData.calisthenics1,
                    MockData.calisthenics2,
                    MockData.calisthenics3,
                    MockData.cardio1,
                    MockData.cardio2
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
                var wasUpdateSuccessful = false
                val originalRealmWorkoutEntry = this.GetLatestQueryViaId(
                    searchableClass = RealmWorkoutEntry::class,
                    query = "workoutId == $0",
                    objectId = updatedRealmWorkoutEntry.workoutId
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

    override suspend fun deleteWorkoutEntryFromRealmDb(realmWorkoutEntry: RealmWorkoutEntry): Boolean {
        return realm.write {
            return@write try {
                var wasWorkoutDeleted: Boolean = false
                this.GetLatestQueryViaId(
                    searchableClass = RealmWorkoutEntry::class,
                    query = "workoutId == $0",
                    objectId = realmWorkoutEntry.workoutId
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
}

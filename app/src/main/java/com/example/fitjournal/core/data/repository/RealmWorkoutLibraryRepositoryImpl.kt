package com.example.fitjournal.core.data.repository

import com.example.fitjournal.FitJournal
import com.example.fitjournal.core.data.mockdata.MockData
import com.example.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.example.fitjournal.core.data.util.getLatestResultViaQuery
import com.example.fitjournal.core.domain.repository.RealmWorkoutLibraryRepository
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import javax.inject.Inject

class RealmWorkoutLibraryRepositoryImpl @Inject constructor() : RealmWorkoutLibraryRepository {
    private val realm = FitJournal.realm

    override suspend fun addMockDataToRealm() {
        // this is how we would write workout to realm db
        realm.write {
            val workouts: MutableList<RealmWorkoutLibrary> = mutableListOf()
            workouts.addAll(
                listOf(
                    MockData.libraryItem(
                        "Bench",
                        "Weight Training"
                    ),
                    MockData.libraryItem(
                        "Squats",
                        "Weight Training"
                    ),
                    MockData.libraryItem(
                        "Lateral Raises",
                        "Weight Training"
                    ),
                    MockData.libraryItem(
                        "Elevated Goblet Squats",
                        "Weight Training"
                    ),
                    MockData.libraryItem(
                        "Burpees",
                        "Calisthenics"
                    ),
                    MockData.libraryItem(
                        "Bicep Curls",
                        "Weight Training"
                    ),
                    MockData.libraryItem(
                        "Box Jumps",
                        "Calisthenics"
                    ),
                    MockData.libraryItem(
                        "Mountain Climbers",
                        "Calisthenics"
                    ),
                    MockData.libraryItem(
                        "Medicine Ball Slams",
                        "Weight Training"
                    ),
                    MockData.libraryItem(
                        "Mountain Pose",
                        "Calisthenics"
                    ),
                    MockData.libraryItem(
                        "Modified Push-Ups",
                        "Calisthenics"
                    ),
                    MockData.libraryItem(
                        "Mason Twist",
                        "Calisthenics"
                    ),
                    MockData.libraryItem(
                        "Monkey Bars",
                        "Calisthenics"
                    ),
                    MockData.libraryItem(
                        "Modified Burpees",
                        "Calisthenics"
                    ),
                    MockData.libraryItem(
                        "Lunges",
                        "Calisthenics"
                    ),
                    MockData.libraryItem(
                        "Lat Pulldowns",
                        "Weight Training"
                    ),
                    MockData.libraryItem(
                        "Lying leg curls",
                        "Weight Training"
                    ),
                    MockData.libraryItem(
                        "L-sit",
                        "Weight Training"
                    ),
                    MockData.libraryItem(
                        "LandMine Twists",
                        "Weight Training"
                    ),
                    MockData.libraryItem(
                        "Running",
                        "Cardio"
                    ),
                    MockData.libraryItem(
                        "Walking",
                        "Cardio"
                    ),
                    MockData.libraryItem(
                        "Biking",
                        "Cardio"
                    )
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
    override suspend fun getRealmWorkoutLibraryList(): List<RealmWorkoutLibrary> {
        val query = realm.query<RealmWorkoutLibrary>().find()
        val list = query.toRealmList().toList()
        if (list.isNotEmpty()) {
            return list
        }
        return emptyList()
    }

    override suspend fun addWorkoutLibraryItemToRealmDb(realmWorkoutLibraryItem: RealmWorkoutLibrary): Boolean {
        return realm.write {
            return@write try {
                val originalRealmWorkoutEntry = this.getLatestResultViaQuery(
                    searchableClass = RealmWorkoutLibrary::class,
                    query = "name == $0",
                    queryValue = realmWorkoutLibraryItem.name
                )
                if (originalRealmWorkoutEntry == null) {
                    copyToRealm(realmWorkoutLibraryItem, updatePolicy = UpdatePolicy.ALL)
                    true
                } else {
                    false
                }
            } catch (e: IllegalArgumentException) {
                // catch for copyToRealm() in case it throws error
                false
            } catch (e: Exception) {
                // general error catch
                false
            }
        }
    }

    override suspend fun updateWorkoutLibraryItemToRealmDb(
        updatedRealmWorkoutLibraryItem: RealmWorkoutLibrary
    ): Boolean {
        return realm.write {
            return@write try {
                val wasUpdateSuccessful: Boolean
                val originalRealmWorkoutEntry = this.getLatestResultViaQuery(
                    searchableClass = RealmWorkoutLibrary::class,
                    query = "name == $0",
                    queryValue = updatedRealmWorkoutLibraryItem.name
                )
                if (originalRealmWorkoutEntry != null) {
                    originalRealmWorkoutEntry.name = updatedRealmWorkoutLibraryItem.name
                    originalRealmWorkoutEntry.type = updatedRealmWorkoutLibraryItem.type
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

    override suspend fun deleteWorkoutEntryFromRealmDb(
        realmWorkoutLibraryItem: RealmWorkoutLibrary
    ): Boolean {
        return realm.write {
            return@write try {
                val wasWorkoutDeleted: Boolean
                this.getLatestResultViaQuery(
                    searchableClass = RealmWorkoutLibrary::class,
                    query = "name == $0",
                    queryValue = realmWorkoutLibraryItem.name
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

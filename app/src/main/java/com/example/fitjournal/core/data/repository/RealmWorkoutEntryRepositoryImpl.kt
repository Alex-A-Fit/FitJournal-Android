package com.example.fitjournal.core.data.repository

import com.example.fitjournal.FitJournal
import com.example.fitjournal.core.data.model.realmdb.workout.RealmWorkoutEntry
import com.example.fitjournal.core.data.util.getLatestResultViaQuery
import com.example.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import javax.inject.Inject

class RealmWorkoutEntryRepositoryImpl @Inject constructor() : RealmWorkoutEntryRepository {
    private val realm = FitJournal.realm

    override var shouldViewModelFetchRealmData: Boolean = false

    // query realm db and find values based on query
    // check if values exist
    override suspend fun getRealmWorkoutEntryList(): List<RealmWorkoutEntry> {
        val list: MutableList<RealmWorkoutEntry> = mutableListOf()
        return realm.write {
            val query = realm.query<RealmWorkoutEntry>().find()
            list.addAll(query.toRealmList().toList())
            if (list.isEmpty()) {
                emptyList()
            } else {
                list.toList()
            }
        }
    }

    override suspend fun getRealmWorkoutEntryListWithName(workoutName: String): List<RealmWorkoutEntry> {
        val list: MutableList<RealmWorkoutEntry> = mutableListOf()
        return realm.write {
            val query = realm.query<RealmWorkoutEntry>().find()
            val filteredListByName = query.filter {
                it.workout?.name == workoutName
            }
            list.addAll(filteredListByName)
            if (list.isEmpty()) {
                emptyList()
            } else {
                list.toList()
            }
        }
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
                shouldViewModelFetchRealmData = true
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
                    originalRealmWorkoutEntry.timeStamp = updatedRealmWorkoutEntry.timeStamp
                    copyToRealm(originalRealmWorkoutEntry, updatePolicy = UpdatePolicy.ALL)
                    shouldViewModelFetchRealmData = true
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
                        shouldViewModelFetchRealmData = true
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

    override fun updateShouldViewModelFetchRealmData(shouldFetch: Boolean) {
        shouldViewModelFetchRealmData = shouldFetch
    }
}

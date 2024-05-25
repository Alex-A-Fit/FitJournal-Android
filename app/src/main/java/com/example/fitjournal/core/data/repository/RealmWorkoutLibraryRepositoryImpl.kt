package com.example.fitjournal.core.data.repository

import com.example.fitjournal.FitJournal
import com.example.fitjournal.core.data.mockdata.MockData
import com.example.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.example.fitjournal.core.data.util.getLatestResultViaQuery
import com.example.fitjournal.core.domain.repository.RealmWorkoutLibraryRepository
import com.example.fitjournal.library.domain.model.UpdateWorkoutLibraryModel
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import javax.inject.Inject

typealias wasUpdateSuccess = Boolean
class RealmWorkoutLibraryRepositoryImpl @Inject constructor() : RealmWorkoutLibraryRepository {
    private val realm = FitJournal.realm
    override var shouldViewModelFetchRealmData: Boolean = false

    override suspend fun addMockDataToRealm() {
        // this is how we would write workout to realm db
        realm.write {
            val workouts: MutableList<RealmWorkoutLibrary> = mutableListOf()
            workouts.addAll(
                MockData.mockLibraryList
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
                    shouldViewModelFetchRealmData = true
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
        updatedRealmWorkoutLibraryItem: UpdateWorkoutLibraryModel
    ): wasUpdateSuccess {
        return realm.write {
            return@write try {
                val originalRealmWorkoutLibraryItem = this.getLatestResultViaQuery(
                    searchableClass = RealmWorkoutLibrary::class,
                    query = "name == $0",
                    queryValue = updatedRealmWorkoutLibraryItem.originalWorkoutName
                )
                // check if workout exists
                // if not we cant update
                if (originalRealmWorkoutLibraryItem == null) return@write false
                // check if new workout name already exists
                updatedRealmWorkoutLibraryItem.newName?.let { newWorkoutName ->
                    val doesRealmWorkoutLibraryItemExist = this.getLatestResultViaQuery(
                        searchableClass = RealmWorkoutLibrary::class,
                        query = "name == $0",
                        queryValue = newWorkoutName
                    )
                    if (doesRealmWorkoutLibraryItemExist == null) {
                        // workout name already exists
                        originalRealmWorkoutLibraryItem.name = newWorkoutName
                    }
                }
                originalRealmWorkoutLibraryItem.type = updatedRealmWorkoutLibraryItem.newWorkoutTypeEnum
                copyToRealm(originalRealmWorkoutLibraryItem, updatePolicy = UpdatePolicy.ALL)
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

    override suspend fun deleteWorkoutEntryFromRealmDb(
        libraryWorkoutName: String
    ): Boolean {
        return realm.write {
            return@write try {
                val wasWorkoutDeleted: Boolean
                this.getLatestResultViaQuery(
                    searchableClass = RealmWorkoutLibrary::class,
                    query = "name == $0",
                    queryValue = libraryWorkoutName
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

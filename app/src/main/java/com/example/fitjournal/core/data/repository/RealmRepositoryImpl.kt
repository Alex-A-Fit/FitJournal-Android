package com.example.fitjournal.core.data.repository

import com.example.fitjournal.FitJournal
import com.example.fitjournal.core.data.model.realmdb.CalisthenicsSet
import com.example.fitjournal.core.data.model.realmdb.CardioSet
import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutModel
import com.example.fitjournal.core.data.model.realmdb.RealmWorkouts
import com.example.fitjournal.core.data.model.realmdb.StrengthTrainingSet
import com.example.fitjournal.core.data.model.realmdb.WorkoutProperties
import com.example.fitjournal.core.domain.repository.RealmRepository
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.query.RealmQuery
import javax.inject.Inject

class RealmRepositoryImpl @Inject constructor() : RealmRepository {

    private val realm = FitJournal.realm

    override suspend fun addMockDataToRealm() {
        // this is how we would write workout to realm db
        realm.write {
            val workoutList = RealmWorkouts()
            val weightTraining1 = RealmWorkoutEntry().apply {
                workout = RealmWorkoutModel().apply {
                    name = "Bench Press"
                    type = "Weight Training"
                    workoutProperties = WorkoutProperties().apply {
                        listOfWeightLiftingSets = realmListOf(
                            StrengthTrainingSet().apply {
                                reps = 10
                                sets = 2
                                weight = 365.0
                            }
                        )
                    }
                }
            }

            val weightTraining2 = RealmWorkoutEntry().apply {
                workout = RealmWorkoutModel().apply {
                    name = "Squats"
                    type = "Weight Training"
                    workoutProperties = WorkoutProperties().apply {
                        listOfWeightLiftingSets = realmListOf(
                            StrengthTrainingSet().apply {
                                reps = 8
                                sets = 3
                                weight = 220.0
                            },
                            StrengthTrainingSet().apply {
                                reps = 5
                                sets = 1
                                weight = 110.0
                            }
                        )
                    }
                }
            }

            val calisthenics1 = RealmWorkoutEntry().apply {
                workout = RealmWorkoutModel().apply {
                    name = "Push Ups"
                    type = "Calisthenics"
                    workoutProperties = WorkoutProperties().apply {
                        listOfCalisthenicsSet = realmListOf(
                            CalisthenicsSet().apply {
                                reps = 25
                                sets = 2
                                weight = 10.0
                                time = "00:03:00"
                            }
                        )
                    }
                }
            }

            val calisthenics2 = RealmWorkoutEntry().apply {
                workout = RealmWorkoutModel().apply {
                    name = "Mountain Climbers"
                    type = "Calisthenics"
                    workoutProperties = WorkoutProperties().apply {
                        listOfCalisthenicsSet = realmListOf(
                            CalisthenicsSet().apply {
                                reps = 12
                                sets = 2
                            },
                            CalisthenicsSet().apply {
                                reps = 6
                                sets = 2
                            }
                        )
                    }
                }
            }

            val calisthenics3 = RealmWorkoutEntry().apply {
                workout = RealmWorkoutModel().apply {
                    name = "Medicine Ball Slams"
                    type = "Calisthenics"
                    workoutProperties = WorkoutProperties().apply {
                        listOfCalisthenicsSet = realmListOf(
                            CalisthenicsSet().apply {
                                reps = 10
                                sets = 4
                            }
                        )
                    }
                }
            }

            val cardio1 = RealmWorkoutEntry().apply {
                workout = RealmWorkoutModel().apply {
                    name = "Running"
                    type = "Cardio"
                    workoutProperties = WorkoutProperties().apply {
                        listOfCardioSets = realmListOf(
                            CardioSet().apply {
                                distance = 3.2
                                time = "00:30:00"
                            }
                        )
                    }
                }
            }

            val cardio2 = RealmWorkoutEntry().apply {
                workout = RealmWorkoutModel().apply {
                    name = "Biking"
                    type = "Cardio"
                    workoutProperties = WorkoutProperties().apply {
                        listOfCardioSets = realmListOf(
                            CardioSet().apply {
                                distance = 3.2
                                distanceType = CardioDistanceType.KILOMETERS.stringValue
                                time = "00:30:00"
                                laps = 13.0
                            }
                        )
                    }
                }
            }
            workoutList.workouts.addAll(
                listOf(
                    weightTraining1,
                    weightTraining2,
                    calisthenics1,
                    calisthenics2,
                    calisthenics3,
                    cardio1,
                    cardio2
                )
            )
            copyToRealm(workoutList, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override suspend fun getWorkoutsFromRealmDb(): RealmQuery<RealmWorkoutEntry> {
        return realm.query<RealmWorkoutEntry>()
    }
}

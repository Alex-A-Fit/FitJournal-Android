package com.example.fitjournal.journalEntry.screen.journalEntry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.fitjournal.core.data.mockdata.MockData
import com.example.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.example.fitjournal.core.domain.managers.DateManager
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class JournalEntryViewModel : ViewModel() {

    private val workoutList = MockData.mockLibraryList

    var selectedWorkoutDetail: MutableState<RealmWorkoutLibrary?> = mutableStateOf(null)

    var sets: MutableState<String> = mutableStateOf("")
    var reps: MutableState<String> = mutableStateOf("")
    var weight: MutableState<String> = mutableStateOf("")
    var distance: MutableState<String> = mutableStateOf("")
    var duration: MutableState<String> = mutableStateOf("")
    var pickerDate: MutableState<String> = mutableStateOf(
        DateManager.formatDate(
            LocalDate.now().toString().format(
                DateTimeFormatter.ISO_LOCAL_DATE.withLocale(Locale.US)
            )
        )
    )

    var weightLiftingSets = SnapshotStateList<WeightLiftingModel>()
    var cardioSets = SnapshotStateList<CardioModel>()
    var calisthenicsSets = SnapshotStateList<CalisthenicsModel>()

    var isAddSetButtonEnabled: MutableState<Boolean> = mutableStateOf(false)
        get() {
            when (mapToWorkoutEnum()) {
                WorkoutTypeEnum.WEIGHT_TRAINING -> {
                    return mutableStateOf(reps.value.isNotEmpty() && weight.value.isNotEmpty() && sets.value.isNotEmpty())
                }
                WorkoutTypeEnum.CALISTHENICS -> {
                    return mutableStateOf(reps.value.isNotEmpty() && weight.value.isNotEmpty() && distance.value.isNotEmpty() && duration.value.isNotEmpty())
                }
                WorkoutTypeEnum.CARDIO -> {
                    return mutableStateOf(distance.value.isNotEmpty() && duration.value.isNotEmpty())
                }
                null -> return mutableStateOf(false)
            }
        }

    @Composable
    fun searchWorkout(searchValue: String): List<Pair<WorkoutTypeEnum, List<RealmWorkoutLibrary>>> {
        val listOfWeightLiftingWorkouts = workoutList.filter { it.type == WorkoutTypeEnum.WEIGHT_TRAINING.workoutTitle() }
        val listOfCardioWorkouts = workoutList.filter { it.type == WorkoutTypeEnum.CARDIO.workoutTitle() }
        val listOfCalisthenicsWorkouts = workoutList.filter { it.type == WorkoutTypeEnum.CALISTHENICS.workoutTitle() }

        return if (searchValue.isNotEmpty()) {
            workoutList.filter { it.name.contains(searchValue, ignoreCase = true) }
            listOf(
                Pair(WorkoutTypeEnum.WEIGHT_TRAINING, listOfWeightLiftingWorkouts.filter { it.name.contains(searchValue, ignoreCase = true) }),
                Pair(WorkoutTypeEnum.CARDIO, listOfCardioWorkouts.filter { it.name.contains(searchValue, ignoreCase = true) }),
                Pair(WorkoutTypeEnum.CALISTHENICS, listOfCalisthenicsWorkouts.filter { it.name.contains(searchValue, ignoreCase = true) })
            )
        } else {
            listOf(
                Pair(WorkoutTypeEnum.WEIGHT_TRAINING, listOfWeightLiftingWorkouts),
                Pair(WorkoutTypeEnum.CARDIO, listOfCardioWorkouts),
                Pair(WorkoutTypeEnum.CALISTHENICS, listOfCalisthenicsWorkouts)
            )
        }
    }

    @Composable
    fun getWorkoutTitle(): String {
        mapToWorkoutEnum()?.let {
             return it.workoutTitle()
         }
        return "Not Available"
    }

    fun mapToWorkoutEnum(): WorkoutTypeEnum? {
        if (selectedWorkoutDetail?.value?.type == "Weight Training") {
            return WorkoutTypeEnum.WEIGHT_TRAINING
        } else if (selectedWorkoutDetail?.value?.type == "Calisthenics") {
            return WorkoutTypeEnum.CALISTHENICS
        } else if (selectedWorkoutDetail?.value?.type == "Cardio") {
            return WorkoutTypeEnum.CARDIO
        } else {
            return null
        }
    }

    fun save() {

    }

    fun addSet() {
        when (mapToWorkoutEnum()) {
            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                weightLiftingSets.add(
                    WeightLiftingModel(
                        reps = reps.value.toInt(),
                        weight = weight.value.toDouble(),
                        sets = sets.value.toInt()
                    )
                )
                reps.value = ""
                sets.value = ""
                weight.value = ""
                isAddSetButtonEnabled.value = false
            }

            WorkoutTypeEnum.CALISTHENICS -> {
                calisthenicsSets.add(
                    CalisthenicsModel(
                        reps = reps.value.toInt(),
                        sets = sets.value.toInt(),
                        time = pickerDate.value,
                        weight = weight.value.toDouble()
                    )
                )
                reps.value = ""
                weight.value = ""
                distance.value = ""
                duration.value = ""
                isAddSetButtonEnabled.value = false
            }

            WorkoutTypeEnum.CARDIO -> {
                cardioSets.add(
                    CardioModel(
                        distance = distance.value.toDouble(),
                        time = duration.value,
                        laps = (cardioSets.count() + 1).toDouble()
                    )
                )
                distance.value = ""
                duration.value = ""
                isAddSetButtonEnabled.value = false
            }

            null -> {}
        }
    }

    fun deleteWeightLiftSet(index: Int) {
        weightLiftingSets.removeAt(index)
    }
}

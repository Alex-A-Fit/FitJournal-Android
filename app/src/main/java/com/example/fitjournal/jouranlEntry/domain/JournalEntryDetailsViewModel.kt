package com.example.fitjournal.jouranlEntry.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitjournal.core.domain.managers.DateManager
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.domain.model.WorkoutDetail
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class JournalEntryDetailsViewModelFactory(private val selectedWorkoutDetail: WorkoutDetail?) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        JournalEntryDetailsViewModel(selectedWorkoutDetail) as T
}

class JournalEntryDetailsViewModel(val selectedWorkoutDetail: WorkoutDetail?) : ViewModel() {

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
    var cardioSets = mutableStateOf(mutableListOf<CardioModel>())
    var calisthenicsSets = mutableStateOf(mutableListOf<CalisthenicsModel>())

    var isAddSetButtonEnabled: MutableState<Boolean> = mutableStateOf(false)
        get() {
            when (selectedWorkoutDetail?.workoutType) {
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

    fun save() {

    }

    fun addSet() {
        when (selectedWorkoutDetail?.workoutType) {
            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                weightLiftingSets.add(
                    WeightLiftingModel(
                        reps = reps.value.toInt(),
                        weight = weight.value.toDouble(),
                        time = pickerDate.value,
                        sets = sets.value.toInt()
                    )
                )
                reps.value = ""
                sets.value = ""
                weight.value = ""
                isAddSetButtonEnabled.value = false
            }

            WorkoutTypeEnum.CALISTHENICS -> {
                calisthenicsSets.value.add(
                    CalisthenicsModel(
                        reps = reps.value.toInt(),
                        time = pickerDate.value
                    )
                )
                reps.value = ""
                weight.value = ""
                distance.value = ""
                duration.value = ""
                isAddSetButtonEnabled.value = false
            }

            WorkoutTypeEnum.CARDIO -> {
                cardioSets.value.add(
                    CardioModel(
                        distance = distance.value.toDouble(),
                        time = duration.value,
                        laps = (cardioSets.value.count() + 1).toDouble()
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
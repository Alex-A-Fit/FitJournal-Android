package com.example.fitjournal.journalEntry.screen.journalEntry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fitjournal.core.data.mockdata.MockData
import com.example.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

class JournalEntryViewModel : ViewModel() {

    private val workoutList = MockData.mockLibraryList

    var selectedWorkoutDetail: MutableState<RealmWorkoutLibrary?> = mutableStateOf(null)

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

    fun save() {
    }
}

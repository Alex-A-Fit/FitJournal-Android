package com.alexafit.fitjournal.core.domain.usecase.editworkoutdialog

import com.alexafit.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.EditWorkoutSetCalisthenicsModel
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.EditWorkoutSetCardioModel
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.EditWorkoutSetWeightLiftingModel
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.WorkoutTypeDialog
import com.alexafit.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import javax.inject.Inject

class CreateModelForEditWorkoutDialogUseCase @Inject constructor() {
    operator fun invoke(
        workoutTypeEnum: WorkoutTypeEnum,
        workoutPropertiesModel: WorkoutPropertiesModel,
        index: Int
    ): WorkoutTypeDialog {
        return when (workoutTypeEnum) {
            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                val workoutList = workoutPropertiesModel.getWeightLiftingProps()
                val chosenWorkout = workoutList[index]
                WorkoutTypeDialog.WeightLifting(
                    editWorkoutSetWeightLiftingModel = EditWorkoutSetWeightLiftingModel(
                        reps = chosenWorkout.reps.toString(),
                        sets = chosenWorkout.sets.toString(),
                        weight = chosenWorkout.weight.toString(),
                        weightType = chosenWorkout.weightType,
                        index = index
                    )
                )
            }

            WorkoutTypeEnum.CALISTHENICS -> {
                val workoutList = workoutPropertiesModel.getCalisthenicsProps()
                val chosenWorkout = workoutList[index]
                WorkoutTypeDialog.Calisthenics(
                    editWorkoutSetCalisthenicsModel = EditWorkoutSetCalisthenicsModel(
                        reps = chosenWorkout.reps.toString(),
                        sets = chosenWorkout.sets.toString(),
                        weight = chosenWorkout.weight?.toString() ?: "",
                        weightType = chosenWorkout.weightType,
                        hr = chosenWorkout.time?.hours ?: "",
                        min = chosenWorkout.time?.minutes ?: "",
                        sec = chosenWorkout.time?.seconds ?: "",
                        index = index
                    )
                )
            }

            WorkoutTypeEnum.CARDIO -> {
                val workoutList = workoutPropertiesModel.getCardioProps()
                val chosenWorkout = workoutList[index]
                WorkoutTypeDialog.Cardio(
                    editWorkoutSetCardioModel = EditWorkoutSetCardioModel(
                        laps = chosenWorkout.laps?.toString() ?: "",
                        distance = chosenWorkout.distance.toString(),
                        distanceType = chosenWorkout.distanceType,
                        hr = chosenWorkout.time.hours,
                        min = chosenWorkout.time.minutes,
                        sec = chosenWorkout.time.seconds,
                        index = index
                    )
                )
            }
        }
    }
}

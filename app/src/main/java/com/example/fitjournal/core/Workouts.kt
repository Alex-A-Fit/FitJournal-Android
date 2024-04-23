package com.example.fitjournal.core

import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.journalEntry.domain.WorkoutDetail

object Workouts {

    // Source: https://www.fitstream.com/exercises/weight-training-exercises/
    // https://calisthenicsskills.com/exercises/
    var availableWorkouts = mutableListOf(
        WorkoutDetail("Barbell Squat", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Bench Press", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Bent Over Row", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Clean and Jerk", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Deadlift", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Farmer's Walk", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Dumbbell Pullover", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Dumbbell Shoulder Press", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Overhead Squat", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Hammer Curl", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Overhead Lunge", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Sled Pulls", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Renegade Row", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Tire Flip", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Triceps Extension", WorkoutTypeEnum.WEIGHT_TRAINING),
        WorkoutDetail("Weighted Step-up", WorkoutTypeEnum.WEIGHT_TRAINING),

        WorkoutDetail("Running", WorkoutTypeEnum.CARDIO),
        WorkoutDetail("Swimming", WorkoutTypeEnum.CARDIO),
        WorkoutDetail("Cycling", WorkoutTypeEnum.CARDIO),

        WorkoutDetail("Push Up", WorkoutTypeEnum.CALISTHENICS),
        WorkoutDetail("Pull Up", WorkoutTypeEnum.CALISTHENICS),
        WorkoutDetail("Pike Push Up", WorkoutTypeEnum.CALISTHENICS),
        WorkoutDetail("Inverted Row", WorkoutTypeEnum.CALISTHENICS),
        WorkoutDetail("Handstand", WorkoutTypeEnum.CALISTHENICS),
        WorkoutDetail("Parallel Bar Dip", WorkoutTypeEnum.CALISTHENICS),
        WorkoutDetail("L-Sit", WorkoutTypeEnum.CALISTHENICS),
        WorkoutDetail("Muscle Up", WorkoutTypeEnum.CALISTHENICS),
        WorkoutDetail("Straight Bar Dips", WorkoutTypeEnum.CALISTHENICS),
        WorkoutDetail("Dragon Flag", WorkoutTypeEnum.CALISTHENICS),
        WorkoutDetail("Hollow Body Hold", WorkoutTypeEnum.CALISTHENICS),
        WorkoutDetail("Front Lever", WorkoutTypeEnum.CALISTHENICS),
        WorkoutDetail("Crow Pose", WorkoutTypeEnum.CALISTHENICS),
        WorkoutDetail("Planche", WorkoutTypeEnum.CALISTHENICS)
    )
}

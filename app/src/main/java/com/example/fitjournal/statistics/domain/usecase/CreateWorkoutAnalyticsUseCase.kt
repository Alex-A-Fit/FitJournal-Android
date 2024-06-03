package com.example.fitjournal.statistics.domain.usecase

import co.yml.charts.common.model.Point
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.TimeModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.domain.util.HelperFunctions
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.util.constants.Constants
import com.example.fitjournal.core.util.extensions.roundToTwoDecimalPlaces
import com.example.fitjournal.core.util.extensions.toDoubleOrZero
import com.example.fitjournal.core.util.extensions.toIntOrZero
import com.example.fitjournal.home.presentation.mapper.reduceTimeValues
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType
import com.example.fitjournal.home.presentation.model.enum.WeightLiftingWeightType
import com.example.fitjournal.statistics.domain.model.DistinctDistanceForTime
import com.example.fitjournal.statistics.domain.model.DistinctWeightForReps
import com.example.fitjournal.statistics.domain.model.GraphAnalytics
import com.example.fitjournal.statistics.domain.model.GraphData
import com.example.fitjournal.statistics.domain.model.PersonalRecordAnalytics
import com.example.fitjournal.statistics.domain.model.PersonalRecords
import com.example.fitjournal.statistics.domain.model.WorkoutAnalytics
import com.example.fitjournal.statistics.domain.model.WorkoutsByTimeRange
import javax.inject.Inject

typealias WorkoutDate = String
class CreateWorkoutAnalyticsUseCase @Inject constructor(
    val getWorkoutsByTimeSelectedUseCase: GetWorkoutsByTimeSelectedUseCase
) {
    operator fun invoke(workouts: List<WorkoutModel>): WorkoutAnalytics {
        val workoutType = workouts.first().workoutDetailsModel.workoutTypeEnum
        return when (workoutType) {
            WorkoutTypeEnum.WEIGHT_TRAINING -> getWeightTrainingAnalytics(
                getWorkoutsByTimeSelectedUseCase(workouts)
            )

            WorkoutTypeEnum.CALISTHENICS -> getCalisthenicsAnalytics(
                getWorkoutsByTimeSelectedUseCase(workouts)
            )

            WorkoutTypeEnum.CARDIO -> getCardioAnalytics(getWorkoutsByTimeSelectedUseCase(workouts))
        }
    }
}

fun getWeightTrainingAnalytics(workouts: WorkoutsByTimeRange): WorkoutAnalytics {
    val graphData = GraphAnalytics(
        graphDataByWeek = getGraphDataForWeightTraining(workouts.week),
        graphDataByMonth = getGraphDataForWeightTraining(workouts.month),
        graphDataByYear = getGraphDataForWeightTraining(workouts.year),
        graphDataAllTime = getGraphDataForWeightTraining(workouts.allTime),
    )
    val pr = getPersonalRecords(graphData)
    return WorkoutAnalytics.WeightTraining(
        graphData = graphData,
        personalRecord = pr,
        bestDistinctWeightForReps = getDistinctWeightForReps(workouts.allTime)
    )
}

fun getCardioAnalytics(workouts: WorkoutsByTimeRange): WorkoutAnalytics {
    val graphData = GraphAnalytics(
        graphDataByWeek = getGraphDataForCardio(workouts.week),
        graphDataByMonth = getGraphDataForCardio(workouts.month),
        graphDataByYear = getGraphDataForCardio(workouts.year),
        graphDataAllTime = getGraphDataForCardio(workouts.allTime),
    )
    val pr = getPersonalRecords(graphData)
    return WorkoutAnalytics.Cardio(
        graphData = graphData,
        personalRecord = pr,
        bestDistinctDistanceForTime = getDistinctDistanceOverTime(workouts.allTime)
    )
}

fun getCalisthenicsAnalytics(workouts: WorkoutsByTimeRange): WorkoutAnalytics {
    val graphData = GraphAnalytics(
        graphDataByWeek = getGraphDataForCalisthenics(workouts.week),
        graphDataByMonth = getGraphDataForCalisthenics(workouts.month),
        graphDataByYear = getGraphDataForCalisthenics(workouts.year),
        graphDataAllTime = getGraphDataForCalisthenics(workouts.allTime)
    )
    val pr = getPersonalRecords(graphData)

    return WorkoutAnalytics.Calisthenics(
        graphData = graphData,
        personalRecord = pr
    )
}

fun getDistinctWeightForReps(workoutList: List<WorkoutModel>): List<DistinctWeightForReps> {
    val listOfWorkoutProps: MutableList<DistinctWeightForReps> = mutableListOf()
    workoutList.forEach { workout ->
        workout.workoutDetailsModel.workoutPropertiesModel.getWeightLiftingProps().forEach {
            listOfWorkoutProps.add(
                DistinctWeightForReps(
                    reps = it.reps,
                    weight = it.weight,
                    date = workout.date
                )
            )
        }
    }
    val groupedByReps = listOfWorkoutProps.groupBy { it.reps }
    val singularGroupByReps = groupedByReps.map { groupedValues ->
        val sortedValues = groupedValues.value.sortedByDescending { it.weight }
        DistinctWeightForReps(
            weight = sortedValues.first().weight,
            reps = groupedValues.key,
            date = groupedValues.value.first().date
        )
    }
    return singularGroupByReps.sortedBy { it.reps }
}

fun getDistinctDistanceOverTime(workoutList: List<WorkoutModel>): List<DistinctDistanceForTime> {
    val listOfWorkoutProps: MutableList<DistinctDistanceForTime> = mutableListOf()
    workoutList.forEach { workout ->
        workout.workoutDetailsModel.workoutPropertiesModel.getCardioProps().forEach {
            val time = getTotalTimeToDate(
                // ignoring date here
                dateFloat = 0.0f,
                totalSeconds = it.time.seconds.toIntOrZero().toString(),
                totalMinutes = it.time.minutes.toIntOrZero().toString(),
                totalHours = it.time.hours.toIntOrZero().toString()
            )
            val totalSeconds = time.y
            listOfWorkoutProps.add(
                DistinctDistanceForTime(
                    distance = it.distance, time = totalSeconds.toDouble(), date = workout.date
                )
            )
        }
    }
    val groupedByDistance = listOfWorkoutProps.groupBy { it.distance }
    val singularGroupByDistance = groupedByDistance.map { groupedValues ->
        val sortedValues = groupedValues.value.sortedByDescending { it.time }
        DistinctDistanceForTime(
            distance = groupedValues.value.first().distance,
            time = sortedValues.first().distance,
            date = groupedValues.value.first().date
        )
    }
    return singularGroupByDistance.sortedBy { it.distance }
}


fun getPersonalRecords(graphData: GraphAnalytics): PersonalRecordAnalytics {
    return PersonalRecordAnalytics(
        prByWeek = determinePR(graphData.graphDataByWeek),
        prByMonth = determinePR(graphData.graphDataByMonth),
        prByYear = determinePR(graphData.graphDataByYear),
        prByAllTime = determinePR(graphData.graphDataAllTime)
    )
}

fun determinePR(graphData: GraphData): PersonalRecords? {
    return when (graphData) {
        is GraphData.Calisthenics -> {
            val sortedGraphByReps = graphData.totalRepsToDate.sortedByDescending { it.y }
            val sortedGraphByWeight = graphData.totalWeightUsedToDate?.sortedByDescending { it.y }
            val sortedGraphByTime = graphData.totalTimeToDate?.sortedByDescending { it.y }
            if (sortedGraphByReps.isEmpty()) return null
            val mostReps = sortedGraphByReps.first().y.toString()
            val dateMostReps = sortedGraphByReps.first().x
            val mostWeightUsed = sortedGraphByWeight?.first()?.y?.toString()
            val dateMostWeightUsed = sortedGraphByWeight?.first()?.x
            val bestTime = sortedGraphByTime?.first()?.y?.toString()
            val dateBestTime = sortedGraphByTime?.first()?.x

            PersonalRecords.CalisthenicsPersonalRecord(
                mostReps = mostReps,
                dateMostReps = HelperFunctions.getDateStringFromEpochDays(dateMostReps),
                mostWeightUsed = mostWeightUsed,
                dateMostWeightUsed = if (dateMostWeightUsed != null) HelperFunctions.getDateStringFromEpochDays(
                    dateMostWeightUsed
                ) else null,
                bestTime = bestTime,
                dateBestTime = if (dateBestTime != null) HelperFunctions.getDateStringFromEpochDays(
                    dateBestTime
                ) else null
            )
        }

        is GraphData.Cardio -> {
            val sortedGraphByDistance = graphData.totalDistanceToDate.sortedByDescending { it.y }
            val sortedGraphBySpeed = graphData.averageSpeedToDate.sortedByDescending { it.y }
            if (sortedGraphByDistance.isEmpty() || sortedGraphBySpeed.isEmpty()) return null
            val farthestDistance = sortedGraphByDistance.first().y.toString()
            val dateFarthestDistance = sortedGraphByDistance.first().x
            val topSpeed = sortedGraphBySpeed.first().y.toString()
            val dateTopSpeed = sortedGraphBySpeed.first().x

            PersonalRecords.CardioPersonalRecord(
                farthestDistance = farthestDistance,
                dateFarthestDistance = HelperFunctions.getDateStringFromEpochDays(
                    dateFarthestDistance
                ),
                topSpeed = topSpeed,
                dateTopSpeed = HelperFunctions.getDateStringFromEpochDays(dateTopSpeed)
            )
        }

        is GraphData.WeightTraining -> {
            val sortedGraphByWeight = graphData.topWeightToDate.sortedByDescending { it.y }
            val sortedGraphByVolume = graphData.mostVolumeToDate.sortedByDescending { it.y }
            if (sortedGraphByWeight.isEmpty() || sortedGraphByVolume.isEmpty()) return null
            val highestWeight = sortedGraphByWeight.first().y.toString()
            val dateOfHighestWeight = sortedGraphByWeight.first().x
            val highestVolume = sortedGraphByVolume.first().y.toString()
            val dateOfHighestVolume = sortedGraphByVolume.first().x

            PersonalRecords.WeightTrainingPersonalRecord(
                weightLifted = highestWeight,
                dateWeightLifted = HelperFunctions.getDateStringFromEpochDays(dateOfHighestWeight),
                highestVolume = highestVolume,
                dateTotalVolume = HelperFunctions.getDateStringFromEpochDays(dateOfHighestVolume)
            )
        }
    }
}

private fun getGraphDataForCardio(workoutList: List<WorkoutModel>): GraphData {
    workoutList.forEach { workout ->
        val workoutSets = workout.workoutDetailsModel.workoutPropertiesModel.getCardioProps()
        val totalLaps = workoutSets.sumOf { it.laps ?: 0.0 }
        val totalDistanceInWorkout = workoutSets.sumOf {
            if (it.distanceType == CardioDistanceType.KILOMETERS) {
                it.distance.times(Constants.KILOMETERS_TO_MILES_CONVERSION_FACTOR)
            } else {
                it.distance
            }
        }
        val totalHours = workoutSets.sumOf { it.time.hours.toDoubleOrZero() }
        val totalMinutes = workoutSets.sumOf { it.time.minutes.toDoubleOrZero() }
        val totalSeconds = workoutSets.sumOf { it.time.seconds.toDoubleOrZero() }
        val totalTime = TimeModel(
            hours = if (totalHours == 0.0) "" else totalHours.toString(),
            minutes = if (totalMinutes == 0.0) "" else totalMinutes.toString(),
            seconds = if (totalSeconds == 0.0) "" else totalSeconds.toString(),
        )
        workout.workoutDetailsModel.workoutPropertiesModel =
            WorkoutPropertiesModel.CardioProps(
                listOf(
                    CardioModel(
                        distance = totalDistanceInWorkout,
                        distanceType = CardioDistanceType.MILES,
                        time = totalTime,
                        laps = totalLaps
                    )
                )
            )
    }
    val pairOfCardioAndDates: List<Pair<WorkoutDate, CardioModel?>> =
        workoutList.map { workout ->
            Pair(
                workout.date,
                workout.workoutDetailsModel.workoutPropertiesModel.getCardioProps().first()
            )
        }
    val groupedByDates = pairOfCardioAndDates.groupBy { it.first }
    val totalDistanceToDate: MutableList<Point> = mutableListOf()
    val averageSpeedToDate: MutableList<Point> = mutableListOf()
    groupedByDates.forEach { (workoutDate, listOfPairDateAndWorkouts) ->
        val date = HelperFunctions.parseDate(workoutDate)
        val dateFloat = date.toEpochDay().toFloat()
        val distance = listOfPairDateAndWorkouts.sumOf { it.second?.distance ?: 0.0 }
        val averageSpeedsForAllWorkoutsForToday: MutableList<Double> = mutableListOf()
        listOfPairDateAndWorkouts.forEach {
            val cardioModel = it.second
            val time = getTotalTimeToDate(
                dateFloat = dateFloat,
                totalSeconds = cardioModel?.time?.seconds?.toIntOrZero()?.toString() ?: "0",
                totalMinutes = cardioModel?.time?.minutes?.toIntOrZero()?.toString() ?: "0",
                totalHours = cardioModel?.time?.hours?.toIntOrZero()?.toString() ?: "0",
            )
            val totalSeconds = time.y
            val totalHours = totalSeconds.toDouble() / 3600.0
            val averageSpeed = cardioModel?.distance?.div(totalHours)?.roundToTwoDecimalPlaces()
            if (averageSpeed != null) {
                averageSpeedsForAllWorkoutsForToday.add(averageSpeed)
            }
        }
        val averageSpeed = averageSpeedsForAllWorkoutsForToday.average().roundToTwoDecimalPlaces()
        averageSpeedToDate.add(
            Point(x = dateFloat, y = averageSpeed.toFloat())
        )
        totalDistanceToDate.add(
            Point(x = dateFloat, y = distance.toFloat())
        )
    }

    return GraphData.Cardio(
        totalDistanceToDate = totalDistanceToDate,
        averageSpeedToDate = averageSpeedToDate
    )
}

private fun getGraphDataForCalisthenics(workoutList: List<WorkoutModel>): GraphData {
    workoutList.forEach { workout ->
        val workoutSets = workout.workoutDetailsModel.workoutPropertiesModel.getCalisthenicsProps()
        val totalRepsInWorkout = workoutSets.sumOf {
            it.reps * it.sets
        }
        val totalHours = workoutSets.sumOf { it.time?.hours?.toDoubleOrZero() ?: 0.0 }
        val totalMinutes = workoutSets.sumOf { it.time?.minutes?.toDoubleOrZero() ?: 0.0 }
        val totalSeconds = workoutSets.sumOf { it.time?.seconds?.toDoubleOrZero() ?: 0.0 }
        val totalTime = TimeModel(
            hours = if (totalHours == 0.0) "" else totalHours.toString(),
            minutes = if (totalMinutes == 0.0) "" else totalMinutes.toString(),
            seconds = if (totalSeconds == 0.0) "" else totalSeconds.toString(),
        )
        val mostWeightUsed = workoutSets.maxByOrNull {
            val weight = if (it.weightType == WeightLiftingWeightType.KILOGRAMS) {
                (it.weight?.times(Constants.KILOGRAMS_TO_POUNDS_CONVERSION_FACTOR))
            } else {
                it.weight
            }
            weight ?: 0.0
        }?.weight
        workout.workoutDetailsModel.workoutPropertiesModel =
            WorkoutPropertiesModel.CalisthenicsProps(
                listOf(
                    CalisthenicsModel(
                        reps = totalRepsInWorkout,
                        sets = 0,
                        time = totalTime,
                        weight = if (mostWeightUsed == null || mostWeightUsed == 0.0) null else mostWeightUsed,
                        weightType = WeightLiftingWeightType.POUNDS
                    )
                )
            )
    }
    val pairOfRepsAndDates: List<Pair<WorkoutDate, CalisthenicsModel?>> =
        workoutList.map { workout ->
            Pair(
                workout.date,
                workout.workoutDetailsModel.workoutPropertiesModel.getCalisthenicsProps().first()
            )
        }
    val groupedByDates = pairOfRepsAndDates.groupBy { it.first }
    val repsToDate: MutableList<Point> = mutableListOf()
    val heaviestWeightUsedToDate: MutableList<Point> = mutableListOf()
    val totalTimeToDate: MutableList<Point> = mutableListOf()

    groupedByDates.forEach { (workoutDate, listOfPairDateAndWorkouts) ->
        val date = HelperFunctions.parseDate(workoutDate)
        val dateFloat = date.toEpochDay().toFloat()
        val reps = listOfPairDateAndWorkouts.sumOf { it.second?.reps ?: 0 }
        val highestPoint = listOfPairDateAndWorkouts.maxByOrNull { it.second?.weight ?: 0.0 }
        val highestWeight = highestPoint?.second
        if (highestWeight != null) {
            heaviestWeightUsedToDate.add(
                getTopWeightToDate(
                    WeightLiftingModel(
                        reps = 0,
                        sets = 0,
                        weight = highestWeight.weight ?: 0.0,
                        weightType = highestWeight.weightType
                    ), dateFloat
                )
            )
        }
        repsToDate.add(
            Point(x = dateFloat, y = reps.toFloat())
        )
        totalTimeToDate.add(
            getTotalTimeToDate(
                dateFloat = dateFloat,
                totalSeconds = listOfPairDateAndWorkouts.sumOf {
                    it.second?.time?.seconds?.toIntOrZero() ?: 0
                }.toString(),
                totalMinutes = listOfPairDateAndWorkouts.sumOf {
                    it.second?.time?.minutes?.toIntOrZero() ?: 0
                }.toString(),
                totalHours = listOfPairDateAndWorkouts.sumOf {
                    it.second?.time?.hours?.toIntOrZero() ?: 0
                }.toString(),
            )
        )
    }

    return GraphData.Calisthenics(
        totalRepsToDate = repsToDate,
        totalTimeToDate = totalTimeToDate,
        totalWeightUsedToDate = heaviestWeightUsedToDate
    )
}

private fun getGraphDataForWeightTraining(workoutList: List<WorkoutModel>): GraphData {
    // get the distinct weights for each workout
    workoutList.forEach { workout ->
        workout.workoutDetailsModel.workoutPropertiesModel =
            WorkoutPropertiesModel.WeightLiftingProps(
                workout.workoutDetailsModel.workoutPropertiesModel.getWeightLiftingProps()
                    .distinctBy { it.weight }
            )
    }
    // create a list of weights with their associated dates
    val pairOfWeightsAndDates: List<Pair<WorkoutDate, WeightLiftingModel?>> =
        workoutList.map { workout ->
            Pair(
                workout.date,
                workout.workoutDetailsModel.workoutPropertiesModel.getWeightLiftingProps()
                    .maxByOrNull { it.weight })
        }
    val groupedByDates = pairOfWeightsAndDates.groupBy { it.first }
    val topWeightToDate: MutableList<Point> = mutableListOf()
    val mostVolumeToDate: MutableList<Point> = mutableListOf()
    groupedByDates.forEach { (workoutDate, listOfPairWorkoutDateAndWeight) ->
        val date = HelperFunctions.parseDate(workoutDate)
        val dateFloat = date.toEpochDay().toFloat()
        val highestPoint = listOfPairWorkoutDateAndWeight.maxByOrNull { it.second?.weight ?: 0.0 }
        val highestWeight = highestPoint?.second
        if (highestWeight != null) {
            topWeightToDate.add(
                getTopWeightToDate(highestWeight, dateFloat)
            )
        }
        val validWorkouts =
            listOfPairWorkoutDateAndWeight.filter { it.second != null }.map { it.second!! }
        mostVolumeToDate.add(
            getTotalVolumeToDate(weightLiftingModel = validWorkouts, dateFloat = dateFloat)
        )
    }
    return GraphData.WeightTraining(
        topWeightToDate = topWeightToDate,
        mostVolumeToDate = mostVolumeToDate
    )
}

private fun getTotalTimeToDate(
    dateFloat: Float,
    totalSeconds: String,
    totalMinutes: String,
    totalHours: String
): Point {
    val timeModel = if (totalSeconds == "0" && totalMinutes == "0" && totalHours == "0") {
        null
    } else {
        TimeModel(
            hours = totalHours,
            minutes = totalMinutes,
            seconds = totalSeconds
        )
    }
    val time = reduceTimeValues(timeModel)
    val hours = time?.hours?.toIntOrZero() ?: 0
    val minutes = time?.minutes?.toIntOrZero() ?: 0
    val seconds = time?.seconds?.toIntOrZero() ?: 0
    return Point(
        x = dateFloat,
        y = (hours * 3600 + minutes * 60 + seconds).toFloat()
    )
}

private fun getTopWeightToDate(
    weightLiftingModel: WeightLiftingModel,
    dateFloat: Float
): Point {
    val highestWeight =
        if (weightLiftingModel.weightType == WeightLiftingWeightType.KILOGRAMS) {
            (weightLiftingModel.weight * Constants.KILOGRAMS_TO_POUNDS_CONVERSION_FACTOR).toFloat()
        } else {
            weightLiftingModel.weight.toFloat()
        }
    return Point(
        x = dateFloat,
        y = highestWeight
    )
}

private fun getTotalVolumeToDate(
    weightLiftingModel: List<WeightLiftingModel>,
    dateFloat: Float
): Point {
    val listOfVolumes = weightLiftingModel.map {
        val weight = if (it.weightType == WeightLiftingWeightType.KILOGRAMS) {
            (it.weight * Constants.KILOGRAMS_TO_POUNDS_CONVERSION_FACTOR).toFloat()
        } else {
            it.weight.toFloat()
        }
        val reps = it.reps.toDouble()
        val sets = it.sets.toDouble()
        reps * sets * weight
    }
    return Point(
        x = dateFloat,
        y = (listOfVolumes.max()).toFloat()
    )

}
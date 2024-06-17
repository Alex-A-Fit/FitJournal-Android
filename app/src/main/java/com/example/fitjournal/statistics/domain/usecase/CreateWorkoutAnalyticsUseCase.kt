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
import com.example.fitjournal.core.util.constants.Constants.HOUR_TO_SECONDS_CONVERSION_FACTOR
import com.example.fitjournal.core.util.constants.Constants.MINUTE_TO_SECONDS_CONVERSION_FACTOR
import com.example.fitjournal.core.util.constants.Zero
import com.example.fitjournal.core.util.extensions.isInteger
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
import com.example.fitjournal.statistics.domain.model.GraphValues
import com.example.fitjournal.statistics.domain.model.PersonalRecord
import com.example.fitjournal.statistics.domain.model.PersonalRecordAnalytics
import com.example.fitjournal.statistics.domain.model.PersonalRecordType
import com.example.fitjournal.statistics.domain.model.WorkoutAnalytics
import com.example.fitjournal.statistics.domain.model.WorkoutsByTimeRange
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.text.Typography.nbsp

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

            WorkoutTypeEnum.CARDIO -> {
                getCardioAnalytics(
                    getWorkoutsByTimeSelectedUseCase(workouts)
                )
            }
        }
    }
}

fun getWeightTrainingAnalytics(workouts: WorkoutsByTimeRange): WorkoutAnalytics {
    val graphData = GraphAnalytics(
        graphDataByWeek = getGraphDataForWeightTraining(workouts.week),
        graphDataByMonth = getGraphDataForWeightTraining(workouts.month),
        graphDataByYear = getGraphDataForWeightTraining(workouts.year),
        graphDataAllTime = getGraphDataForWeightTraining(workouts.allTime)
    )
    val pr = getPersonalRecords(graphData)
    return WorkoutAnalytics.WeightTraining(
        graphData = graphData,
        personalRecord = pr,
        bestDistinctWeightForReps = getDistinctWeightForReps(workouts.allTime)
    )
}

fun getCardioAnalytics(workouts: WorkoutsByTimeRange): WorkoutAnalytics {
    val originalData = workouts.allTime
    val graphData = GraphAnalytics(
        graphDataByWeek = getGraphDataForCardio(workouts.week),
        graphDataByMonth = getGraphDataForCardio(workouts.month),
        graphDataByYear = getGraphDataForCardio(workouts.year),
        graphDataAllTime = getGraphDataForCardio(originalData)
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
                    weight = it.weight.toString(),
                    date = workout.date
                )
            )
        }
    }
    val groupedByReps = listOfWorkoutProps.groupBy { it.reps }
    val singularGroupByReps = groupedByReps.map { groupedValues ->
        val sortedValues = groupedValues.value.sortedByDescending { it.weight.toDoubleOrZero() }
        val weight = sortedValues.first().weight
        DistinctWeightForReps(
            weight = dropDecimalValue(weight),
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
            val totalSeconds = it.time.seconds.toDoubleOrNull()?.toInt()
            val totalMinutes = it.time.minutes.toDoubleOrNull()?.toInt()
            val totalHours = it.time.hours.toDoubleOrNull()?.toInt()

            var totalTime = ""
            if (totalHours != null && totalHours != Zero.INT) {
                totalTime += "$totalHours hrs$nbsp"
            }
            if (totalMinutes != null && totalMinutes != Zero.INT) {
                totalTime += "$totalMinutes min$nbsp"
            }
            if (totalSeconds != null && totalSeconds != Zero.INT) {
                totalTime += "$totalSeconds sec"
            }
            listOfWorkoutProps.add(
                DistinctDistanceForTime(
                    distance = it.distance,
                    time = totalTime,
                    date = workout.date
                )
            )
        }
    }
    val groupedByDistance = listOfWorkoutProps.groupBy { it.distance }
    val singularGroupByDistance = groupedByDistance.map { groupedValues ->
        val sortedValues = groupedValues.value.sortedByDescending { it.time }
        DistinctDistanceForTime(
            distance = groupedValues.value.first().distance,
            time = sortedValues.first().time,
            date = groupedValues.value.first().date
        )
    }
    return singularGroupByDistance.sortedBy { it.distance }
}

fun getPersonalRecords(graphData: GraphAnalytics): PersonalRecordAnalytics {
    val allTimePr = determinePR(graphData.graphDataAllTime)
    val calisthenicsAllTimePr =
        if (allTimePr is PersonalRecordType.Calisthenics) allTimePr else null
    val weightTrainingAllTimePr =
        if (allTimePr is PersonalRecordType.WeightTraining) allTimePr else null
    val cardioAllTimePr = if (allTimePr is PersonalRecordType.Cardio) allTimePr else null
    val prByMonth = determinePR(
        graphData = graphData.graphDataByMonth,
        calisthenicsAllTimePr = calisthenicsAllTimePr,
        weightTrainingAllTimePr = weightTrainingAllTimePr,
        cardioAllTimePr = cardioAllTimePr

    )
    val prByYear = determinePR(
        graphData = graphData.graphDataByYear,
        calisthenicsAllTimePr = calisthenicsAllTimePr,
        weightTrainingAllTimePr = weightTrainingAllTimePr,
        cardioAllTimePr = cardioAllTimePr
    )
    val prByWeek = determinePR(
        graphData = graphData.graphDataByWeek,
        calisthenicsAllTimePr = calisthenicsAllTimePr,
        weightTrainingAllTimePr = weightTrainingAllTimePr,
        cardioAllTimePr = cardioAllTimePr
    )
    return PersonalRecordAnalytics(
        prByWeek = prByWeek,
        prByMonth = prByMonth,
        prByYear = prByYear,
        prByAllTime = allTimePr
    )
}

fun determinePR(
    graphData: GraphData,
    calisthenicsAllTimePr: PersonalRecordType.Calisthenics? = null,
    weightTrainingAllTimePr: PersonalRecordType.WeightTraining? = null,
    cardioAllTimePr: PersonalRecordType.Cardio? = null
): PersonalRecordType? {
    return when (graphData) {
        is GraphData.Calisthenics -> {
            val sortedGraphByReps = graphData.totalRepsToDate?.sortedByDescending { it.point.y }
            val sortedGraphByWeight =
                graphData.totalWeightUsedToDate?.sortedByDescending { it.point.y }
            if (sortedGraphByReps.isNullOrEmpty()) return null
            val mostReps = sortedGraphByReps.first().point.y.roundToInt().toString()
            val dateMostReps = sortedGraphByReps.first().date
            val mostWeightUsed = sortedGraphByWeight?.first()?.point?.y?.toString()
            val dateMostWeightUsed = sortedGraphByWeight?.first()?.date

            val repPr = PersonalRecord(
                personalRecord = "$mostReps reps",
                personalRecordDate = dateMostReps,
                allTimePr = calisthenicsAllTimePr?.totalRepsPr?.personalRecord ?: "$mostReps reps",
                allTimePrDate = calisthenicsAllTimePr?.totalRepsPr?.personalRecordDate
                    ?: dateMostReps
            )
            val weightPr = if (mostWeightUsed != null && dateMostWeightUsed != null) {
                PersonalRecord(
                    personalRecord = "${dropDecimalValue(mostWeightUsed)} lbs",
                    personalRecordDate = dateMostWeightUsed,
                    allTimePr = calisthenicsAllTimePr?.totalWeightUsedPr?.personalRecord
                        ?: "${dropDecimalValue(mostWeightUsed)}lbs",
                    allTimePrDate = calisthenicsAllTimePr?.totalWeightUsedPr?.personalRecordDate
                        ?: dateMostWeightUsed
                )
            } else {
                null
            }
            PersonalRecordType.Calisthenics(
                totalRepsPr = repPr,
                totalWeightUsedPr = weightPr
            )
        }

        is GraphData.Cardio -> {
            val sortedGraphByDistance =
                graphData.totalDistanceToDate?.sortedByDescending { it.point.y }
            val sortedGraphBySpeed = graphData.averageSpeedToDate?.sortedByDescending { it.point.y }
            if (sortedGraphByDistance.isNullOrEmpty() || sortedGraphBySpeed.isNullOrEmpty()) return null
            val farthestDistance = sortedGraphByDistance.first().point.y.toString()
            val dateFarthestDistance = sortedGraphByDistance.first().date
            val topSpeed = sortedGraphBySpeed.first().point.y.toString()
            val dateTopSpeed = sortedGraphBySpeed.first().date

            val longestDistancePr = PersonalRecord(
                personalRecord = "${dropDecimalValue(farthestDistance)} mi",
                personalRecordDate = dateFarthestDistance,
                allTimePr = cardioAllTimePr?.totalDistancePr?.personalRecord
                    ?: "${farthestDistance}mi",
                allTimePrDate = cardioAllTimePr?.totalDistancePr?.personalRecordDate
                    ?: dateFarthestDistance
            )
            val bestSpeedPr = PersonalRecord(
                personalRecord = "${dropDecimalValue(topSpeed)} mi/hr",
                personalRecordDate = dateTopSpeed,
                allTimePr = cardioAllTimePr?.bestSpeedPr?.personalRecord ?: "$topSpeed mi/hr",
                allTimePrDate = cardioAllTimePr?.bestSpeedPr?.personalRecordDate ?: dateTopSpeed
            )
            PersonalRecordType.Cardio(
                totalDistancePr = longestDistancePr,
                bestSpeedPr = bestSpeedPr
            )
        }

        is GraphData.WeightTraining -> {
            val sortedGraphByWeight = graphData.topWeightToDate?.sortedByDescending { it.point.y }
            val sortedGraphByVolume = graphData.mostVolumeToDate?.sortedByDescending { it.point.y }
            if (sortedGraphByWeight.isNullOrEmpty() || sortedGraphByVolume.isNullOrEmpty()) return null
            val highestWeight = sortedGraphByWeight.first().point.y.toString()
            val dateOfHighestWeight = sortedGraphByWeight.first().date
            val highestVolume = sortedGraphByVolume.first().point.y.toString()
            val dateOfHighestVolume = sortedGraphByVolume.first().date

            val mostWeightLiftedPr = PersonalRecord(
                personalRecord = "${dropDecimalValue(highestWeight)} lbs",
                personalRecordDate = dateOfHighestWeight,
                allTimePr = weightTrainingAllTimePr?.mostWeightPr?.personalRecord
                    ?: "${highestWeight}lbs",
                allTimePrDate = weightTrainingAllTimePr?.mostWeightPr?.personalRecordDate
                    ?: dateOfHighestWeight
            )
            val mostVolumePr = PersonalRecord(
                personalRecord = "${dropDecimalValue(highestVolume)} lbs moved/workout",
                personalRecordDate = dateOfHighestVolume,
                allTimePr = weightTrainingAllTimePr?.mostVolumePr?.personalRecord
                    ?: "$highestVolume lbs moved/workout",
                allTimePrDate = weightTrainingAllTimePr?.mostVolumePr?.personalRecordDate
                    ?: dateOfHighestVolume
            )
            PersonalRecordType.WeightTraining(
                mostWeightPr = mostWeightLiftedPr,
                mostVolumePr = mostVolumePr
            )
        }
    }
}

private fun getGraphDataForCardio(workoutList: List<WorkoutModel>): GraphData {
    val cardioList: MutableList<Pair<String, CardioModel>> = mutableListOf()
    workoutList.forEach { workout ->
        val workoutSets = workout.workoutDetailsModel.workoutPropertiesModel.getCardioProps()
        val totalLaps = workoutSets.sumOf { it.laps ?: Zero.DOUBLE }
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
            hours = if (totalHours == Zero.DOUBLE) "" else totalHours.toString(),
            minutes = if (totalMinutes == Zero.DOUBLE) "" else totalMinutes.toString(),
            seconds = if (totalSeconds == Zero.DOUBLE) "" else totalSeconds.toString()
        )
        cardioList.add(
            Pair(
                workout.date,
                CardioModel(
                    distance = totalDistanceInWorkout,
                    distanceType = CardioDistanceType.MILES,
                    time = totalTime,
                    laps = totalLaps
                )
            )
        )
    }
    val groupedByDates = cardioList.groupBy { it.first }
    val totalDistanceToDate: MutableList<GraphValues> = mutableListOf()
    val averageSpeedToDate: MutableList<GraphValues> = mutableListOf()
    groupedByDates.forEach { (workoutDate, listOfPairDateAndWorkouts) ->
        val totalDistance = listOfPairDateAndWorkouts.sumOf { it.second.distance }
        val averageSpeedsForAllWorkoutsForToday: MutableList<Double> = mutableListOf()
        listOfPairDateAndWorkouts.forEach {
            val cardioModel = it.second
            val time = getTotalTimeToDate(
                totalSeconds = cardioModel.time.seconds.toDoubleOrZero().toInt().toString(),
                totalMinutes = cardioModel.time.minutes.toDoubleOrZero().toInt().toString(),
                totalHours = cardioModel.time.hours.toDoubleOrZero().toInt().toString()
            )
            val totalSeconds = time.y
            val totalHours = totalSeconds.toDouble() / HOUR_TO_SECONDS_CONVERSION_FACTOR
            val averageSpeed =
                if (totalHours == Zero.DOUBLE) {
                    null
                } else {
                    cardioModel.distance.div(totalHours).roundToTwoDecimalPlaces()
                }
            if (averageSpeed != null) {
                averageSpeedsForAllWorkoutsForToday.add(averageSpeed)
            }
        }
        val averageSpeed = averageSpeedsForAllWorkoutsForToday.average().roundToTwoDecimalPlaces()
        averageSpeedToDate.add(
            GraphValues(
                point = Point(x = Zero.FLOAT, y = averageSpeed.toFloat()),
                date = workoutDate
            )

        )
        totalDistanceToDate.add(
            GraphValues(
                point = Point(x = Zero.FLOAT, y = totalDistance.toFloat()),
                date = workoutDate
            )
        )
    }

    return GraphData.Cardio(
        totalDistanceToDate = HelperFunctions.filterPoints(totalDistanceToDate),
        averageSpeedToDate = HelperFunctions.filterPoints(averageSpeedToDate)
    )
}

private fun getGraphDataForCalisthenics(workoutList: List<WorkoutModel>): GraphData {
    workoutList.forEach { workout ->
        val workoutSets = workout.workoutDetailsModel.workoutPropertiesModel.getCalisthenicsProps()
        val totalRepsInWorkout = workoutSets.sumOf {
            if (it.sets != Zero.INT) {
                it.reps * it.sets
            } else {
                it.reps
            }
        }
        val totalHours = workoutSets.sumOf { it.time?.hours?.toDoubleOrZero() ?: Zero.DOUBLE }
        val totalMinutes = workoutSets.sumOf { it.time?.minutes?.toDoubleOrZero() ?: Zero.DOUBLE }
        val totalSeconds = workoutSets.sumOf { it.time?.seconds?.toDoubleOrZero() ?: Zero.DOUBLE }
        val totalTime = TimeModel(
            hours = if (totalHours == Zero.DOUBLE) "" else totalHours.toString(),
            minutes = if (totalMinutes == Zero.DOUBLE) "" else totalMinutes.toString(),
            seconds = if (totalSeconds == Zero.DOUBLE) "" else totalSeconds.toString()
        )
        val mostWeightUsed = workoutSets.maxByOrNull {
            val weight = if (it.weightType == WeightLiftingWeightType.KILOGRAMS) {
                (it.weight?.times(Constants.KILOGRAMS_TO_POUNDS_CONVERSION_FACTOR))
            } else {
                it.weight
            }
            weight ?: Zero.DOUBLE
        }?.weight
        workout.workoutDetailsModel.workoutPropertiesModel =
            WorkoutPropertiesModel.CalisthenicsProps(
                listOf(
                    CalisthenicsModel(
                        reps = totalRepsInWorkout,
                        sets = Zero.INT,
                        time = totalTime,
                        weight = if (mostWeightUsed == null || mostWeightUsed == Zero.DOUBLE) null else mostWeightUsed,
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
    val repsToDate: MutableList<GraphValues> = mutableListOf()
    val heaviestWeightUsedToDate: MutableList<GraphValues> = mutableListOf()

    groupedByDates.forEach { (workoutDate, listOfPairDateAndWorkouts) ->
        val reps = listOfPairDateAndWorkouts.sumOf { it.second?.reps ?: Zero.INT }.toFloat()
        val highestPoint =
            listOfPairDateAndWorkouts.maxByOrNull { it.second?.weight ?: Zero.DOUBLE }
        val highestWeight = highestPoint?.second
        if (highestWeight != null) {
            val weight = calculateWeightLiftedInPounds(
                WeightLiftingModel(
                    reps = Zero.INT,
                    sets = Zero.INT,
                    weight = highestWeight.weight ?: Zero.DOUBLE,
                    weightType = highestWeight.weightType
                )
            )
            if (weight != Zero.FLOAT) {
                heaviestWeightUsedToDate.add(
                    GraphValues(
                        point = Point(x = Zero.FLOAT, y = weight),
                        date = workoutDate
                    )
                )
            }
        }
        if (reps != Zero.FLOAT) {
            repsToDate.add(
                GraphValues(
                    point = Point(x = Zero.FLOAT, y = reps),
                    date = workoutDate
                )
            )
        }
    }

    return GraphData.Calisthenics(
        totalRepsToDate = HelperFunctions.filterPoints(repsToDate),
        totalWeightUsedToDate = HelperFunctions.filterPoints(heaviestWeightUsedToDate)
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
    val pairOfWeightsAndDates: List<Pair<WorkoutDate, List<WeightLiftingModel>>> =
        workoutList.map { workout ->
            Pair(
                workout.date,
                workout.workoutDetailsModel.workoutPropertiesModel.getWeightLiftingProps()
            )
        }
    val groupedByDates = pairOfWeightsAndDates.groupBy { it.first }
    val weightLiftedToDate: MutableList<GraphValues> = mutableListOf()
    val mostVolumeToDate: MutableList<GraphValues> = mutableListOf()
    groupedByDates.forEach { (workoutDate, listOfPairWorkoutDateAndWeight) ->
        listOfPairWorkoutDateAndWeight.forEach { pair ->
            pair.second.forEach { weight ->
                val calculatedWeight = calculateWeightLiftedInPounds(weight)
                weightLiftedToDate.add(
                    GraphValues(point = Point(Zero.FLOAT, calculatedWeight), date = workoutDate)
                )
                val volume = getTotalVolumeToDate(weightLiftingModel = pair.second)
                mostVolumeToDate.add(
                    GraphValues(point = Point(Zero.FLOAT, volume), date = workoutDate)
                )
            }
        }
    }
    return GraphData.WeightTraining(
        topWeightToDate = HelperFunctions.filterPoints(weightLiftedToDate),
        mostVolumeToDate = HelperFunctions.filterPoints(mostVolumeToDate)
    )
}

private fun getTotalTimeToDate(
    totalSeconds: String,
    totalMinutes: String,
    totalHours: String
): Point {
    val timeModel =
        if (totalSeconds == Zero.STRING &&
            totalMinutes == Zero.STRING &&
            totalHours == Zero.STRING
        ) {
            null
        } else {
            TimeModel(
                hours = totalHours,
                minutes = totalMinutes,
                seconds = totalSeconds
            )
        }
    val time = reduceTimeValues(timeModel)
    val hours = time?.hours?.toIntOrZero() ?: Zero.INT
    val minutes = time?.minutes?.toIntOrZero() ?: Zero.INT
    val seconds = time?.seconds?.toIntOrZero() ?: Zero.INT
    return Point(
        x = Zero.FLOAT,
        y = (hours * HOUR_TO_SECONDS_CONVERSION_FACTOR + minutes * MINUTE_TO_SECONDS_CONVERSION_FACTOR + seconds).toFloat()
    )
}

private fun calculateWeightLiftedInPounds(
    weightLiftingModel: WeightLiftingModel
): Float {
    val highestWeight =
        if (weightLiftingModel.weightType == WeightLiftingWeightType.KILOGRAMS) {
            (weightLiftingModel.weight * Constants.KILOGRAMS_TO_POUNDS_CONVERSION_FACTOR).toFloat()
        } else {
            weightLiftingModel.weight.toFloat()
        }
    return highestWeight
}

private fun getTotalVolumeToDate(
    weightLiftingModel: List<WeightLiftingModel>
): Float {
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
    return (listOfVolumes.max()).toFloat()
}

private fun dropDecimalValue(value: String): String {
    return if (value.toDouble().isInteger()) {
        value.toDouble().toInt().toString()
    } else {
        value
    }
}

package com.alexafit.fitjournal.statistics.presentation.model

sealed class GraphUiTypes {
    enum class CardioGraphs {
        DISTANCE_OVER_DATE,
        AVERAGE_SPEED_OVER_DATE
    }

    enum class WeightTrainingGraphs {
        WEIGHT_OVER_DATE,
        VOLUME_OVER_DATE
    }

    enum class CalisthenicsGraphs {
        REPS_OVER_DATE,
        TOTAL_WEIGHT_OVER_DATE
    }
}

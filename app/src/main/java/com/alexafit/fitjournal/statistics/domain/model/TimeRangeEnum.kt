package com.alexafit.fitjournal.statistics.domain.model

import com.alexafit.fitjournal.R

enum class TimeRangeEnum(val stringIdValue: Int) {
    WEEK(R.string.label_week),
    MONTH(R.string.label_month),
    YEAR(R.string.label_year),
    ALL_TIME(R.string.label_all_time)
}

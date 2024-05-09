package com.example.fitjournal.core.domain.usecase.workout

data class EditWorkoutUseCase(
    val addOrSubtractDoublesUseCase: AddOrSubtractDoublesUseCase,
    val addOrSubtractIntegersUseCase: AddOrSubtractIntegersUseCase,
    val isIntegerValidUseCase: IsIntegerValidUseCase,
    val isTimeValidUseCase: IsTimeValidUseCase,
    val adjustTimeValuesUseCase: AdjustTimeValuesUseCase,
    val adjustMandatoryTimeValuesUseCase: AdjustMandatoryTimeValuesUseCase,
    val isDoubleValidUseCase: IsDoubleValidUseCase
)

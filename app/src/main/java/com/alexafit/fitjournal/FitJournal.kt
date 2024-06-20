package com.alexafit.fitjournal

import android.app.Application
import com.alexafit.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.alexafit.fitjournal.core.data.model.realmdb.workout.CalisthenicsSet
import com.alexafit.fitjournal.core.data.model.realmdb.workout.CardioSet
import com.alexafit.fitjournal.core.data.model.realmdb.workout.RealmWorkout
import com.alexafit.fitjournal.core.data.model.realmdb.workout.RealmWorkoutEntry
import com.alexafit.fitjournal.core.data.model.realmdb.workout.RealmWorkoutProperties
import com.alexafit.fitjournal.core.data.model.realmdb.workout.StrengthTrainingSet
import dagger.hilt.android.HiltAndroidApp
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

@HiltAndroidApp
class FitJournal : Application() {
    companion object {
        lateinit var realm: Realm
    }

    override fun onCreate() {
        super.onCreate()
        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    RealmWorkoutProperties::class,
                    RealmWorkout::class,
                    RealmWorkoutEntry::class,
                    StrengthTrainingSet::class,
                    CalisthenicsSet::class,
                    CardioSet::class,
                    RealmWorkoutLibrary::class
                )
            )
        )
    }
}

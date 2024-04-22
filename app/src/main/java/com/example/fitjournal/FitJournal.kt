package com.example.fitjournal

import android.app.Application
import com.example.fitjournal.core.data.model.realmdb.CalisthenicsSet
import com.example.fitjournal.core.data.model.realmdb.CardioSet
import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutModel
import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutProperties
import com.example.fitjournal.core.data.model.realmdb.RealmWorkouts
import com.example.fitjournal.core.data.model.realmdb.StrengthTrainingSet
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
                    RealmWorkoutModel::class,
                    RealmWorkoutEntry::class,
                    RealmWorkouts::class,
                    StrengthTrainingSet::class,
                    CalisthenicsSet::class,
                    CardioSet::class
                )
            )
        )
    }
}

package com.example.mytraveldiary

import android.app.Application
import androidx.room.Room
import com.example.mytraveldiary.data.db.DiaryDao
import com.example.mytraveldiary.data.db.DiaryDatabase
import com.example.mytraveldiary.data.repository.DiaryRepository
import com.example.mytraveldiary.data.repository.DiaryRepositoryImpl
import com.example.mytraveldiary.ui.create.DiaryViewModel
import com.example.mytraveldiary.ui.create.DiaryViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton
import timber.log.Timber


//provider instances new instances each time
class MyTravelDiaryApplication : Application(), DIAware {
    override val di = DI.lazy {
        bind<FirebaseAuth>() with singleton {
            FirebaseAuth.getInstance()
        }
        bind<DiaryDatabase>() with singleton {
            Room.databaseBuilder(
                applicationContext,
                DiaryDatabase::class.java,
                "diary_database"
            ).allowMainThreadQueries().build()
        }
        bind<DiaryDao>() with singleton { instance<DiaryDatabase>().diaryDao() }
        bind<DiaryRepository>() with singleton { DiaryRepositoryImpl(instance()) }
        bind<DiaryViewModelFactory>() with provider { DiaryViewModelFactory(instance()) }
        bind<DiaryViewModel>() with provider { DiaryViewModel(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
package com.example.mytraveldiary.core.fragment


import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.kodein.di.DIAware
import org.kodein.di.DITrigger
import org.kodein.di.android.x.BuildConfig
import org.kodein.di.android.x.closestDI
import org.kodein.di.diContext


/*
Dependency resolution for debug builds:
By defining kodeinTrigger we can eagerly retrieve all dependencies in onCreate method. This allow us to be sure
that all dependencies have correctly been retrieved (there were no non-declared dependencies and no dependency
loops)

Dependency resolution for release builds:
By not using kodeinTrigger all dependencies will be resolved lazily. This allow to save some resources and speed up
the application by retrieving dependencies only when they are needed/used.
*/

/**
* By default expected content layout is compose XML fragments set their own layout id
*/
abstract class InjectionFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId), DIAware {

@SuppressWarnings("LeakingThisInConstructor")
override val diContext = diContext<Fragment>(this)

override val diTrigger: DITrigger? by lazy {
    if (BuildConfig.DEBUG) DITrigger() else super.diTrigger
}
final override val di by closestDI()

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    diTrigger?.trigger()
}

//    protected fun setUpGoBackAction(action: () -> Unit = { findNavController().popBackStack() }) {
//        view?.findViewById<TextView>(R.id.goBack)?.setOnClickListener {
//            action()
//        }
//    }

}

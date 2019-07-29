package com.nexters.android.pliary.di.annotation

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
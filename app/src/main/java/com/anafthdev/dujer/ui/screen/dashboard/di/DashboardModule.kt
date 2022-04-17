package com.anafthdev.dujer.ui.screen.dashboard.di

import com.anafthdev.dujer.ui.screen.dashboard.environment.DashboardEnvironment
import com.anafthdev.dujer.ui.screen.dashboard.environment.IDashboardEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DashboardModule {
	
	@Binds
	abstract fun provideEnvironment(
		environment: DashboardEnvironment
	): IDashboardEnvironment
	
}
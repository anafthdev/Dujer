package com.anafthdev.dujer.feature.setting.environment

import com.anafthdev.dujer.data.datastore.AppDatastore
import com.anafthdev.dujer.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class SettingEnvironment @Inject constructor(
	@Named(DiName.DISPATCHER_IO) override val dispatcher: CoroutineDispatcher,
	private val appDatastore: AppDatastore
): ISettingEnvironment {
	
	override suspend fun setIsUseBioAuth(useBioAuth: Boolean) {
		appDatastore.setUseBioAuth(useBioAuth)
	}
	
	override fun getIsUseBioAuth(): Flow<Boolean> {
		return appDatastore.isUseBioAuth
	}
	
	
}
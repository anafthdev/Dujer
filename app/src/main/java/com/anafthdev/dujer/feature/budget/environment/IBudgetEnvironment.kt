package com.anafthdev.dujer.feature.budget.environment

import com.anafthdev.dujer.data.FinancialGroupData
import com.anafthdev.dujer.data.GroupType
import com.anafthdev.dujer.data.SortType
import com.anafthdev.dujer.data.model.Budget
import com.anafthdev.dujer.data.model.Financial
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IBudgetEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getFinancial(id: Int): Flow<Financial>
	
	fun getBudget(): Flow<Budget>
	
	fun getSortType(): Flow<SortType>
	
	fun getGroupType(): Flow<GroupType>
	
	fun getFilterDate(): Flow<Pair<Long, Long>>
	
	fun getThisMonthExpenses(): Flow<Double>
	
	fun getAveragePerMonthExpense(): Flow<Double>
	
	fun getSelectedMonth(): Flow<List<Int>>
	
	fun getBarEntries(): Flow<List<BarEntry>>
	
	fun getTransactions(): Flow<FinancialGroupData>
	
	suspend fun setBudget(id: Int)
	
	suspend fun updateBudget(budget: Budget)
	
	suspend fun deleteBudget(budget: Budget)
	
	suspend fun setSortType(sortType: SortType)
	
	suspend fun setGroupType(groupType: GroupType)
	
	suspend fun setFilterDate(date: Pair<Long, Long>)
	
	suspend fun setSelectedMonth(selectedMonth: List<Int>)
	
}
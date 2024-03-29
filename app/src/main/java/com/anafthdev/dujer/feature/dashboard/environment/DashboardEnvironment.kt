package com.anafthdev.dujer.feature.dashboard.environment

import com.anafthdev.dujer.data.FinancialGroupData
import com.anafthdev.dujer.data.FinancialType
import com.anafthdev.dujer.data.GroupType
import com.anafthdev.dujer.data.SortType
import com.anafthdev.dujer.data.model.Category
import com.anafthdev.dujer.data.model.Financial
import com.anafthdev.dujer.data.model.Wallet
import com.anafthdev.dujer.data.repository.Repository
import com.anafthdev.dujer.foundation.common.AppUtil
import com.anafthdev.dujer.foundation.common.Quint
import com.anafthdev.dujer.foundation.common.financial_sorter.FinancialSorter
import com.anafthdev.dujer.foundation.di.DiName
import com.anafthdev.dujer.foundation.extension.*
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.sumOf

class DashboardEnvironment @Inject constructor(
	@Named(DiName.DISPATCHER_IO) override val dispatcher: CoroutineDispatcher,
	private val financialSorter: FinancialSorter,
	private val repository: Repository
): IDashboardEnvironment {
	
	private val _selectedSortType = MutableStateFlow(SortType.A_TO_Z)
	private val selectedSortType: StateFlow<SortType> = _selectedSortType
	
	private val _selectedGroupType = MutableStateFlow(GroupType.DEFAULT)
	private val selectedGroupType: StateFlow<GroupType> = _selectedGroupType
	
	private val _selectedMonth = MutableStateFlow(
		listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
	)
	private val selectedMonth: StateFlow<List<Int>> = _selectedMonth
	
	private val _filterDate = MutableStateFlow(AppUtil.filterDateDefault)
	private val filterDate: StateFlow<Pair<Long, Long>> = _filterDate
	
	private val _transactions = MutableStateFlow(FinancialGroupData.default)
	private val transactions: StateFlow<FinancialGroupData> = _transactions
	
	private val _highestExpenseCategory = MutableStateFlow(Category.default)
	private val highestExpenseCategory: StateFlow<Category> = _highestExpenseCategory
	
	private val _highestExpenseCategoryAmount = MutableStateFlow(0.0)
	private val highestExpenseCategoryAmount: StateFlow<Double> = _highestExpenseCategoryAmount
	
	private val _incomeEntry = MutableStateFlow(entryTemp.toList())
	private val incomeEntry: StateFlow<List<Entry>> = _incomeEntry
	
	private val _expenseEntry = MutableStateFlow(entryTemp.toList())
	private val expenseEntry: StateFlow<List<Entry>> = _expenseEntry
	
	init {
		CoroutineScope(dispatcher).launch {
			combine(
				selectedMonth,
				filterDate,
				selectedSortType,
				selectedGroupType,
				repository.getAllFinancial()
			) { month, year, sortType, groupType, financials ->
				Quint(month, year, sortType, groupType, financials)
			}.collect { (month, year, sortType, groupType, financials) ->
				val income = financials.filter { it.type.isIncome() }
				val expense = financials.filter { it.type.isExpense() }
				
				val transaction = financialSorter.beginSort(
					sortType = sortType,
					groupType = groupType,
					filterDate = year,
					selectedMonth = month,
					financials = financials
				)
				
				val (incomeEntry, expenseEntry) = getLineDataSetEntry(income, expense)
				
				_incomeEntry.emit(incomeEntry)
				_expenseEntry.emit(expenseEntry)
				_transactions.emit(transaction)
			}
		}
		
		CoroutineScope(dispatcher).launch {
			repository.getAllFinancial().collect { financialList ->
				var highest = Category.default.id to 0.0
				val financials = financialList.filter { it.type == FinancialType.EXPENSE }
				val categories = financials.map { it.category }
				val groupedFinancial = financials.groupBy { it.category.id }
				
				groupedFinancial.forEachMap { categoryID, list ->
					val amount = list.sumOf { it.amount }
					
					if (amount > highest.second) {
						highest = categoryID to amount
					}
				}
				
				_highestExpenseCategory.emit(
					categories.find { it.id == highest.first } ?: Category.default
				)
				
				_highestExpenseCategoryAmount.emit(
					highest.second
				)
			}
		}
	}
	
	override fun getSortType(): Flow<SortType> {
		return selectedSortType
	}
	
	override fun getGroupType(): Flow<GroupType> {
		return selectedGroupType
	}
	
	override fun getFilterDate(): Flow<Pair<Long, Long>> {
		return filterDate
	}
	
	override fun getSelectedMonth(): Flow<List<Int>> {
		return selectedMonth
	}
	
	override fun getTransactions(): Flow<FinancialGroupData> {
		return transactions
	}
	
	override fun getHighestExpenseCategory(): Flow<Category> {
		return highestExpenseCategory
	}
	
	override fun getHighestExpenseCategoryAmount(): Flow<Double> {
		return highestExpenseCategoryAmount
	}
	
	override fun getIncomeEntry(): Flow<List<Entry>> {
		return incomeEntry
	}
	
	override fun getExpenseEntry(): Flow<List<Entry>> {
		return expenseEntry
	}
	
	override suspend fun insertWallet(wallet: Wallet) {
		repository.insertWallet(wallet)
	}
	
	override suspend fun setSortType(sortType: SortType) {
		_selectedSortType.emit(sortType)
	}
	
	override suspend fun setGroupType(groupType: GroupType) {
		_selectedGroupType.emit(groupType)
	}
	
	override suspend fun setFilterDate(date: Pair<Long, Long>) {
		_filterDate.emit(date)
	}
	
	override suspend fun setSelectedMonth(selectedMonth: List<Int>) {
		_selectedMonth.emit(selectedMonth)
	}
	
	private fun getLineDataSetEntry(
		incomeList: List<Financial>,
		expenseList: List<Financial>
	): Pair<List<Entry>, List<Entry>> {
		val monthFormatter = SimpleDateFormat("MMM", deviceLocale)
		
		val incomeListEntry = ArrayList(entryTemp)
		val expenseListEntry = ArrayList(entryTemp)
		
		val monthGroupIncomeList = incomeList.groupBy { monthFormatter.format(it.dateCreated) }
		val monthGroupExpenseList = expenseList.groupBy { monthFormatter.format(it.dateCreated) }
		
		monthGroupIncomeList.forEachMap { k, v ->
			val totalAmount = v.sumOf { it.amount }
			val entryIndex = AppUtil.shortMonths.indexOf { it.contentEquals(k, true) }
			
			incomeListEntry[entryIndex] = Entry(
				entryIndex.toFloat(),
				totalAmount.toFloat(),
				totalAmount
			)
		}
		
		monthGroupExpenseList.forEachMap { k, v ->
			val totalAmount = v.sumOf { it.amount }
			val entryIndex = AppUtil.shortMonths.indexOf { it.contentEquals(k, true) }
			
			expenseListEntry[entryIndex] = Entry(
				entryIndex.toFloat(),
				totalAmount.toFloat(),
				totalAmount
			)
		}
		
		return incomeListEntry to expenseListEntry
	}
	
	companion object {
		val entryTemp = arrayListOf<Entry>().apply {
			add(Entry(0f, 0f, 0.0))
			add(Entry(1f, 0f, 0.0))
			add(Entry(2f, 0f, 0.0))
			add(Entry(3f, 0f, 0.0))
			add(Entry(4f, 0f, 0.0))
			add(Entry(5f, 0f, 0.0))
			add(Entry(6f, 0f, 0.0))
			add(Entry(7f, 0f, 0.0))
			add(Entry(8f, 0f, 0.0))
			add(Entry(9f, 0f, 0.0))
			add(Entry(10f, 0f, 0.0))
			add(Entry(11f, 0f, 0.0))
		}
	}
}
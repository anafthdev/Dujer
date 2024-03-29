package com.anafthdev.dujer.feature.budget

import com.anafthdev.dujer.data.FinancialGroupData
import com.anafthdev.dujer.data.GroupType
import com.anafthdev.dujer.data.SortType
import com.anafthdev.dujer.data.model.Budget
import com.anafthdev.dujer.foundation.common.AppUtil
import com.github.mikephil.charting.data.BarEntry

data class BudgetState(
	val budget: Budget = Budget.defalut,
	val sortType: SortType = SortType.A_TO_Z,
	val groupType: GroupType = GroupType.DEFAULT,
	val filterDate: Pair<Long, Long> = AppUtil.filterDateDefault,
	val thisMonthExpenses: Double = 0.0,
	val averagePerMonthExpenses: Double = 0.0,
	val selectedMonth: List<Int> = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
	val barEntries: List<BarEntry> = emptyList(),
	val transactions: FinancialGroupData = FinancialGroupData.default
)

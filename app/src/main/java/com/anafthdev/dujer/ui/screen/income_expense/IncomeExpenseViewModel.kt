package com.anafthdev.dujer.ui.screen.income_expense

import androidx.lifecycle.viewModelScope
import com.anafthdev.dujer.data.db.model.Financial
import com.anafthdev.dujer.foundation.viewmodel.StatefulViewModel
import com.anafthdev.dujer.ui.screen.income_expense.environment.IIncomeExpenseEnvironment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeExpenseViewModel @Inject constructor(
	incomeExpenseEnvironment: IIncomeExpenseEnvironment
): StatefulViewModel<IncomeExpenseState, IIncomeExpenseEnvironment>(IncomeExpenseState(), incomeExpenseEnvironment) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.getIncomeFinancialList()
				.combine(environment.getExpenseFinancialList()) { income, expense ->
					income to expense
				}.collect { pair ->
					setState {
						copy(
							incomeFinancialList = pair.first,
							expenseFinancialList = pair.second
						)
					}
				}
		}
	}
	
	fun delete(financial: Financial) {
		viewModelScope.launch(environment.dispatcher) {
			environment.deleteFinancial(financial)
		}
	}
	
}
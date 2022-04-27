package com.anafthdev.dujer.ui.change_currency

import com.anafthdev.dujer.model.Currency

sealed class ChangeCurrencyAction {
	data class Search(val query: String): ChangeCurrencyAction()
	data class ChangeCurrency(val currency: Currency): ChangeCurrencyAction()
}

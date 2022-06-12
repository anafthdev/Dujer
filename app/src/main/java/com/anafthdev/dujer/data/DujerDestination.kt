package com.anafthdev.dujer.data

import com.anafthdev.dujer.ui.category.data.CategorySwipeAction

sealed class DujerDestination(val route: String) {
	
	object IncomeExpense: DujerDestination("incomeExpense/{type}") {
		fun createRoute(type: FinancialType): String {
			return "incomeExpense/${type.ordinal}"
		}
	}
	
	object Category: DujerDestination("category/{action}/{id}") {
		fun createRoute(
			action: String = CategorySwipeAction.NOTHING,
			id: Int = com.anafthdev.dujer.data.db.model.Category.default.id
		): String {
			return "category/$action/$id"
		}
	}
	
	object Dashboard: DujerDestination("dashboard") {
		object Home: DujerDestination("${Dashboard.route}/home")
		
		object Export: DujerDestination("${Dashboard.route}/export")
		
		object Chart: DujerDestination("${Dashboard.route}/chart")
	}
	
	object Wallet: DujerDestination("wallet/{id}") {
		fun createRoute(walletID: Int): String {
			return "wallet/$walletID"
		}
	}
	
	object Statistic: DujerDestination("statistic/{walletID}") {
		fun createRoute(walletID: Int): String {
			return "statistic/$walletID"
		}
	}
	
	object CategoryTransaction: DujerDestination("category_transaction/{categoryID}") {
		fun createRoute(categoryID: Int): String {
			return "category_transaction/$categoryID"
		}
	}
	
	object Setting: DujerDestination("setting")
	
	object Currency: DujerDestination("currency")
}
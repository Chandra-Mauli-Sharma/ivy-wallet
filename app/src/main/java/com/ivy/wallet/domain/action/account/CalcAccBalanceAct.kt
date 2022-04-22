package com.ivy.wallet.domain.action.account

import arrow.core.nonEmptyListOf
import com.ivy.fp.action.FPAction
import com.ivy.fp.action.then
import com.ivy.wallet.domain.data.core.Account
import com.ivy.wallet.domain.pure.account.AccountValueFunctions
import com.ivy.wallet.domain.pure.account.calcAccValues
import com.ivy.wallet.domain.pure.data.ClosedTimeRange
import java.math.BigDecimal
import javax.inject.Inject

class CalcAccBalanceAct @Inject constructor(
    private val accTrnsAct: AccTrnsAct
) : FPAction<CalcAccBalanceAct.Input, CalcAccBalanceAct.Output>() {

    override suspend fun Input.compose(): suspend () -> Output = suspend {
        AccTrnsAct.Input(
            accountId = account.id,
            range = range
        )
    } then accTrnsAct then { accTrns ->
        calcAccValues(
            accountId = account.id,
            accountsTrns = accTrns,
            valueFunctions = nonEmptyListOf(AccountValueFunctions::balance)
        ).head
    } then { balance ->
        Output(
            account = account,
            balance = balance
        )
    }

    data class Input(
        val account: Account,
        val range: ClosedTimeRange = ClosedTimeRange.allTimeIvy()
    )

    data class Output(
        val account: Account,
        val balance: BigDecimal,
    )
}
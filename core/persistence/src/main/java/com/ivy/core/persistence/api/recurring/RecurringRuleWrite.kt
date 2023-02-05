package com.ivy.core.persistence.api.recurring

import com.ivy.core.data.RecurringRule
import com.ivy.core.data.RecurringRuleId
import com.ivy.core.persistence.api.WriteSyncable

interface RecurringRuleWrite : WriteSyncable<RecurringRule, RecurringRuleId>
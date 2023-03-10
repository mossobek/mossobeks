package com.stirkaparus.model

data class Report(
    var id: String = "",
    var count: Int = 0,
    var companyId: String = "",
    var total: Int = 0,
    var reported_time: Any? = null,
)

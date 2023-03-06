package com.stirkaparus.model

data class Order(
    var phone: String? = "",
    var id: String? = "",
    var company_id: String? = "",
     var status: String? = "",
    var address: String? = "",
    var sale_type: String? = "",
    var taken_user: String? = "",
    var created_user: String? = "",
    var comment: String? = "",
    var delivered: Boolean? = false,
    var reported: Boolean? = false,
    var reportId: String? = "",
    var count: Int? = 0,
    var washed_count: Int? = 0,
    var created_time: Any? = null,
    var taken_time: Any? = null,
    var delivered_time: Any? = null,
    var washed_time: Any? = null,
    var canceled_time: Any? = null,
    var finished_time: Any? = null,
    var delete_time: Any? = null,
    var total: Int? = 0,
    var square: Double? = 0.0
) {
}
package com.stirkaparus.model

data class User(
    var id:String? =  "",
    var name:String? =  "",
    var city:String? =  "",
    var role:String? =  "",
    var email:String? =  "",
    var phone:String? =  "",
    var company_id:String? =  "",
    var company_name:String? =  "",
    var active:Boolean? =  null,
)
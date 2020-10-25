package com.example.homecomfort

import com.google.android.gms.common.server.converter.StringToIntConverter
import java.lang.NullPointerException

data class Spuser(var name:String?=null,var cno:String?=null,var address:String?=null,var bday:String?=null,var service:String?=null,var email:String?=null,var loc:String?=null,var shift:String?=null,var time:String?=null,var exp:String?=null,var disc:String?=null) {
    constructor():this("","","","","","","","","","","")
}
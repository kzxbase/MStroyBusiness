package kz.xbase.mstroy.model.network

import java.io.Serializable

data class ResponseCheckuser(val isFirstTime:Boolean,val isRegistered:Boolean,val userRole:String) : Serializable

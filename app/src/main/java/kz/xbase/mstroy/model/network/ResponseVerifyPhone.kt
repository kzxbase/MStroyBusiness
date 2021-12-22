package kz.xbase.mstroy.model.network

import java.io.Serializable

data class ResponseVerifyPhone(val cityList: List<City>) : Serializable

package kz.xbase.a_pay.utils

import java.lang.StringBuilder

class TextConverter {
    fun getTenge():String{
        return "₸"
    }
    fun getBeautifulPhone(phone:String):String{
        val first = phone.subSequence(0,1)
        val second = phone.subSequence(1,4)
        val third = phone.subSequence(4,7)
        val fourth = phone.subSequence(7,9)
        val fifth = phone.subSequence(9,11)
        return "+"+first+" ("+second+") "+third+"-"+fourth+"-"+fifth
    }
    fun getBeautifulBalance(balance:Double):String{
        val sum = balance.toInt()
        var string = sum.toString()
        var spaceCount=0
        var temp = sum
        while (true){
            temp /= 1000
            if(temp<1){
                break
            }else{
                spaceCount++
            }
        }
        if(spaceCount>0){
            val sums:ArrayList<String> = arrayListOf()
            var count =0
            for(i in string.length-1 downTo 0){
                count++
                if(count==3){
                    count=0
                    sums.add(string.subSequence(i,i+3).toString())
                }
            }
            val preString = string.length%3
            if(preString>0){
                string=string.subSequence(0,preString).toString()
            }else{
                string=""
            }
            for (i in sums.size-1 downTo 0){
                string=string+" "+sums[i]
            }
        }
        return string
    }

    fun getOnlyDigits(text: String) : String {
        var out = text.replace("+7", "7")
        out = out.replace("[^\\d]".toRegex(), "")
        return out
    }


    fun getFormattedPhone(phoneNumber: String?) : String {
        return if (phoneNumber == null){
            ""
        } else {
            if (phoneNumber.length != 10) {
                "Некорректный номер"
            } else {
                "+7 ${phoneNumber.substring(0, 3)} ${phoneNumber.substring(
                    3,
                    6
                )}-${phoneNumber.substring(6, 8)}-${phoneNumber.substring(8, 10)}"
            }
        }
    }

    fun getFormattedDate(date:String):String{
        return  if (date.length==10){
            "${date.substring(8,10)}.${date.substring(5,7)}.${date.substring(0,4)}"
        }else{
            ""
        }
    }
    fun getMonthName(month:Int):String{
        return when(month){
            1 ->{
                "Январь"
            }
            2 -> {
                "Февраль"
            }
            3 -> {
                "Март"
            }
            4 -> {
                "Апрель"
            }
            5 -> {
                "Май"
            }
            6 -> {
                "Июнь"
            }
            7 -> {
                "Июль"
            }
            8 -> {
                "Август"
            }
            9 -> {
                "Сентябрь"
            }
            10 -> {
                "Октябрь"
            }
            11 -> {
                "Ноябрь"
            }
            12 -> {
                "Декабрь"
            }
            else -> {
                ""
            }
        }
    }
}
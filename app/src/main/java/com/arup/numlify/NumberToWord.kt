package com.arup.numlify

internal object NumberToWord {
    private fun ztn(`in`: Int): String {
        return when (`in`) {
            0 -> "Zero"
            1 -> "One"
            2 -> "Two"
            3 -> "Three"
            4 -> "Four"
            5 -> "Five"
            6 -> "Six"
            7 -> "Seven"
            8 -> "Eight"
            9 -> "Nine"
            else -> "Error"
        }
    }

    private fun convertToWord(numberString: String, type: Int): String {
        var input = numberString
        val zero = mapOf(
            "Zero(11)" to "",
            "Zero(10)" to "",
            "Zero(9)" to "",
            "Zero(8)" to "",
            "Zero(7)" to "",
            "Zero(6)" to "",
            "Zero(5)" to "",
            "Zero(4)" to "",
            "Zero(3)" to "",
            "Zero(2)" to "",
            "Zero(1)" to "",
        )
        val specialCases = mapOf(
            "Onety One" to "Eleven",
            "Onety Two" to "Twelve",
            "Onety Three" to "Thirteen",
            "Onety Four" to "Fourteen",
            "Onety Five" to "Fifteen",
            "Onety Six" to "Sixteen",
            "Onety Seven" to "Seventeen",
            "Onety Eight" to "Eighteen",
            "Onety Nine" to "Nineteen",
            "Twoty" to "Twenty",
            "Threety" to "Thirty",
            "Fourty" to "Forty",
            "Fivety" to "Fifty",
            "Eightty" to "Eighty",
            " Zeroty" to "",
            " Zero" to "",
            "Onety" to "Ten",
        )
        val replaceCases = if(type == 0) {
            mapOf(
                "(11)" to " Kharab ",
                "(10)" to "(1)Arab ",
                "(9)" to " Arab ",
                "(8)" to "(1)Crore ",
                "(7)" to " Crore ",
                "(6)" to "(1)Lakh ",
                "(5)" to " Lakh ",
                "(4)" to "(1)Thousand ",
                "(3)" to " Thousand ",
                "(2)" to " Hundred ",
                "(1)" to "ty ",
                "(0)" to "",
            )
        } else {
            mapOf(
                "(11)" to "(2)Billion ",
                "(10)" to "(1)Billion ",
                "(9)" to " Billion ",
                "(8)" to "(2)Million ",
                "(7)" to "(1)Million ",
                "(6)" to " Million ",
                "(5)" to "(2)Thousand ",
                "(4)" to "(1)Thousand ",
                "(3)" to " Thousand ",
                "(2)" to " Hundred ",
                "(1)" to "ty ",
                "(0)" to ""
            )
        }

        for ((key, value) in zero) {
            input = input.replace(key, value)
        }

        for ((key, value) in replaceCases) {
            input = input.replace(key, value)
        }
        for ((key, value) in specialCases) {
            input = input.replace(key.toRegex(), value)
        }
        return input
    }

    private fun getAfterPoint(number: String): String {
        return number.map { ztn(it - '0') }.joinToString(" ").dropLast(0)
    }

    private fun convert(numb: String, type: Int): String {
        val number = try {
            numb.toLong()
        } catch (_: Exception) {
            return numb
        }
        val numberString = number.toString().mapIndexed { i, c ->
            "${ztn(c - '0')}(${number.toString().length - i - 1})"
        }.joinToString("")
        return convertToWord(numberString, type)
    }

    @JvmStatic
    fun run(numb: String, type:Int): String {
        val numArr = numb.split('.')
        return when (numArr.size) {
            1 -> convert(numArr[0], type)
            2 -> {
                if(numArr[1].isEmpty()) {
                    convert(numArr[0], type)
                }
                else {
                    "${convert(numArr[0], type)} point ${getAfterPoint(numArr[1])}"
                }
            }
            else -> "Error, invalid number format"
        }
    }
}
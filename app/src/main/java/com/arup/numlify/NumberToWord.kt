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

    private fun convertToWord(numberString: String): String {
        var input = numberString
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
            "Thousand Hundred" to "Thousand",
            "Lakh Thousand" to "Lakh",
            "Crore Lakh" to "Crore"
        )
        val replaceCases = mapOf(
            "(0)" to "",
            "(1)" to "ty ",
            "(2)" to " Hundred ",
            "(3)" to " Thousand ",
            "(4)" to "ty ",
            "(5)" to " Lakh ",
            "(6)" to "ty ",
            "(7)" to " Crore ",
            "(8)" to "ty ",
            "(9)" to " Hundred ",
            "(10)" to " Thousand ",
            "(11)" to " Lakh "
        )

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

    private fun convert(numb: String): String {
        val number = try {
            numb.toLong()
        } catch (_: Exception) {
            return numb
        }
        val numberString = number.toString().mapIndexed { i, c ->
            "${ztn(c - '0')}(${number.toString().length - i - 1})"
        }.joinToString("")
        return convertToWord(numberString)
    }

    @JvmStatic
    fun run(numb: String): String {
        val numArr = numb.split('.')
        return when (numArr.size) {
            1 -> convert(numArr[0])
            2 -> {
                if(numArr[1].isEmpty()) {
                    convert(numArr[0])
                }
                else {
                    "${convert(numArr[0])} point ${getAfterPoint(numArr[1])}"
                }
            }
            else -> "Error, invalid number format"
        }
    }
}
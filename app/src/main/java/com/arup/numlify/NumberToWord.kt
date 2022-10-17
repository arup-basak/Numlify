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

    private fun convert(numberString: String): String {
        var input = numberString
        input = input.replace("(0)", "")
        input = input.replace("(1)", "ty ")
        input = input.replace("(2)", " Hundred ")
        input = input.replace("(3)", " Thousand ")
        input = input.replace("(4)", "ty ")
        input = input.replace("(5)", " Lakh ")
        input = input.replace("(6)", "ty ")
        input = input.replace("(7)", " Crore ")
        input = input.replace("(8)", "ty ")
        input = input.replace("(9)", " Hundred ")
        input = input.replace("(10)", " Thousand ")
        input = input.replace("(11)", " Lakh ")

        input = input.replace("Onety One".toRegex(), "Eleven")
        input = input.replace("Onety Two".toRegex(), "Twelve")
        input = input.replace("Onety Three".toRegex(), "Thirteen")
        input = input.replace("Onety Four".toRegex(), "Fourteen")
        input = input.replace("Onety Five".toRegex(), "Fifteen")
        input = input.replace("Onety Six".toRegex(), "Sixteen")
        input = input.replace("Onety Seven".toRegex(), "Seventeen")
        input = input.replace("Onety Eight".toRegex(), "Eighteen")
        input = input.replace("Onety Nine".toRegex(), "Nineteen")
        input = input.replace("Twoty".toRegex(), "Twenty")
        input = input.replace("Threety".toRegex(), "Thirty")
        input = input.replace("Fourty".toRegex(), "Forty")
        input = input.replace("Fivety".toRegex(), "Fifty")
        input = input.replace("Eightty".toRegex(), "Eighty")

        input = input.replace(" Zeroty".toRegex(), "")
        input = input.replace(" Zero".toRegex(), "")
        input = input.replace("Onety".toRegex(), "Ten")
        input = input.replace("Thousand Hundred".toRegex(), "Thousand")
        input = input.replace("Lakh Thousand".toRegex(), "Lakh")
        input = input.replace("Crore Lakh".toRegex(), "Crore")
        return input
    }

    @JvmStatic
    fun run(numb: String): String {
        var number: String = try {
            numb.toLong().toString()
        } catch (_: Exception) {
            numb
        }
        val len = number.length
        val sb = StringBuilder()
        var str: String
        for (i in 0 until len) {
            str = ztn(number[i].code - '0'.code)
            sb.append(str).append('(').append(len - i - 1).append(')')
        }
        number = sb.toString()
        return convert(number)
    }
}
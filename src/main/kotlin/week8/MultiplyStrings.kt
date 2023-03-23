package week8

class MultiplyStrings {
    fun multiply(string1: String, string2: String): String {
        return multiply1(string1, string2)
    }

    // faster and simpler for small ints, but incorrect for large ints
    private fun multiply2(string1: String, string2: String): String {
        if (string1 == "0" || string2 == "0")
            return "0"

        var multipliers = 1
        var sum = 0
        for (i in string1.indices.reversed()) {
            var carry = 0
            var current = 0
            var zeroes = multipliers
            for (j in string2.indices.reversed()) {
                // extract digit characters from the 2 strings and convert them to integers
                val digit1 = string1[i].toString().toInt()
                val digit2 = string2[j].toString().toInt()
                // calculate the product of the 2 digits
                val product = (digit1 * digit2) + carry
                // save the product and the carry
                current += zeroes * (product%10)
                carry = product / 10
                // multiply by 10 to get ready for the multiplication in the next loop
                zeroes *= 10
            }
            if (carry != 0)
                current += zeroes * carry

            sum += current

            multipliers *= 10
        }
        return sum.toString()
    }

    private fun multiply1(string1: String, string2: String): String {
        if (string1 == "0" || string2 == "0")
            return "0"

        val result = StringBuilder()
        for ((zeros, i) in string1.indices.reversed().withIndex()) {
            var carry = 0
            val product = StringBuilder()
            product.append("0".repeat(zeros))
            // calculate the product of the whole string2 with 1 digit in string1
            for (j in string2.indices.reversed()) {
                // extract one digit from each string, and convert the 2 digits
                val digit1 = string1[i].toString().toInt()
                val digit2 = string2[j].toString().toInt()
                // multiply the 2 digits
                val tmp = (digit1 * digit2) + carry
                // save the product's last digit
                product.insert(0, tmp % 10)
                // save the carry for the next iteration
                carry = tmp / 10
            }
            if (carry != 0)
                product.insert(0, carry)

            if (result.isEmpty())
                result.append(product)
            else {
                val s1 = result.toString()
                result.clear()
                val s2 = product
                var j = s1.length - 1
                var k = s2.length - 1
                carry = 0
                // add the product calculated above with the result accumulated so far
                while (j >= 0 && k >= 0) {
                    // extract one digit from each string, and convert the 2 digits
                    val digit1 = s1[j].toString().toInt()
                    val digit2 = s2[k].toString().toInt()
                    // add the 2 digits
                    val sum = digit1 + digit2 + carry
                    // save the product's last digit
                    result.insert(0, sum % 10)
                    // save the carry for the next iteration
                    carry = sum / 10
                    j--
                    k--
                }

                when {
                    j >= 0 -> {
                        // transfer the remainder of s1 into result
                        var substring = s1.substring(0, j+1)
                        if (carry != 0) {
                            substring = addOne(substring)
                        }
                        result.insert(0, substring)
                    }
                    k >= 0 -> {
                        // transfer the remainder of s2 into result
                        var substring = s2.substring(0, k+1)
                        if (carry != 0)
                            substring = addOne(substring)
                        result.insert(0, substring)
                    }
                    // both j == 0 and k == 0, but there's a carry: save the carray
                    carry != 0 -> result.insert(0, 1)
                }
            }
        }
        return result.toString()
    }

    private fun addOne(string: String): String {
        val result = StringBuilder()
        for (i in string.indices.reversed()) {
            if (string[i] < '9') {
                result.insert(0, string[i]+1)
                result.insert(0, string.substring(0, i))
                return result.toString()
            } else{
                result.insert(0, '0')
            }
        }
        result.insert(0, 1)
        return result.toString()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val nums1 = arrayOf("103", "2", "123")
            val nums2 = arrayOf("98", "3", "456")
            for (i in nums1.indices) {
                // print("${nums1[i]} * ${nums2[i]} = ")
                val multiply = MultiplyStrings().multiply(nums1[i], nums2[i])
                // println("$multiply")
            }
        }
    }
}
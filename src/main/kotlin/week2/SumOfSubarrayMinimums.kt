package week2

import java.util.*
import kotlin.math.pow


class SumOfSubarrayMinimums {
    var MOD = (1e9 + 7).toInt()

    // from https://leetcode.com/problems/sum-of-subarray-minimums/discuss/1202537/Java-Simple-and-easy-to-T-O(n)-S-O(n)-soln-clean-code-with-comments
    fun copied(arr: IntArray): Int {
        val n = arr.size

        // leftCount[i] = number of element greater than or equal to element at i till we find first smaller number
        val leftCount = getLeftCounts(arr)

        // rightCount[i] = number of element greater than element at i till we find first smaller or equal number
        val rightCount = getRightCounts(arr)
        var totalSum: Long = 0
        for (i in 0 until n) {
            // consider each number as mininum number
            // using this number calculate total number of possible subarray
            totalSum = (totalSum + arr[i].toLong() * leftCount[i] * rightCount[i]) % MOD
        }
        return totalSum.toInt()
    }

    private fun getLeftCounts(arr: IntArray): IntArray {
        val n = arr.size
        val counts = IntArray(n)
        val stack = LinkedList<Int>()
        for (i in arr.indices) {
            // remove all indices the value at which is greater than or equal to the current value
            while (stack.isNotEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop()
            }
            counts[i] = if (stack.isEmpty()) i - 0 + 1 else i - stack.peek()!!
            stack.push(i)
        }
        return counts
    }

    private fun getRightCounts(arr: IntArray): IntArray {
        val n = arr.size
        val counts = IntArray(n)
        val stack = LinkedList<Int>()
        for (i in n - 1 downTo 0) {
            // remove all the index which value is greater or equal than current value
            while (stack.isNotEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop()
            }
            counts[i] = if (stack.isEmpty()) n - i else stack.peek() - i
            stack.push(i)
        }
        return counts
    }

    fun sumSubarrayMins(arr: IntArray): Int {
        // return bruteForce(arr)
        return optimized(arr)
        // return copied(arr)
    }

    private fun bruteForce(arr: IntArray): Int {
        var sum = 0
        val modulo = 10.0.pow(9).toInt() + 7
        for (i in arr.indices) {
            for (j in i..arr.lastIndex) {
                val subset = arr.slice(i..j)
                val minHeap = PriorityQueue(subset)
                val min = minHeap.peek()
                sum = (sum + min) % modulo
            }
        }
        return sum
    }

    private fun optimized(array: IntArray): Int {
        val modulo = 10.0.pow(9).toInt() + 7
        var sum = 0
        // put all array contents into a minHeap
        val previous = PriorityQueue(array.toList())
        for (i in array.indices) {
            // remove element at the front of array, one at a time
            if (i > 0) {
                remove(array[i-1], previous)
            }

            // work on a copy of this minHeap
            val minHeap = PriorityQueue(previous)
            sum = (sum + minHeap.peek()) % modulo

            // chop elements from the end of array
            for (j in array.lastIndex downTo i+1) {
                remove(array[j], minHeap)
                sum = (sum + minHeap.peek()) % modulo
            }
        }
        return sum
    }

    private fun remove(number: Int, minHeap: PriorityQueue<Int>) {
        if (minHeap.peek() == number)
            minHeap.poll()
        else
            minHeap.remove(number)
    }

    private fun print(minHeap: PriorityQueue<Int>) {
        for (number in minHeap) {
            print("$number ")
        }
        println()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val big1 = intArrayOf(960,19470,5370,19627,29628,27547,13118,12946,8684,13213,28738,1136,14366,26872,3199,
                3502,24444,8485,23939,9055,970,24596,9955,13840,1796,19525,24869,6593,27997,19008,16446,6176,26283,
                12719,1280,27774,11066,8838,1741,993,1294,26249,26037,14092,2438,4178,28426,16822,23868,2305,1960,23314,
                3475,24622,28034,12985,3454,21529,13371,15043,26640,16196,4146,11176,25490,15344,2423,15197,1053,26380,
                7444,21876,29840,26891,15859,9725,20963,16399,27265,7266,20804,4575,12153,23322,10931,8509,10588,11210,
                240,4294,22218,20784,19575,4106,3475,14687,14518,3774,28975,24521,26167,5166,19697,24397,9706,29561,
                2853,23797,4339,7540,5686,3473,20127,7289,23978,23084,28723,23241,29701,16720,960,9961,11823,28990,
                12634,8106,23993,27594,1401,29430,21681,29873,19457,234,20350,6802,29654,27963,25823,21260,7602,27800,
                24679,19018,5129,5978,17008,17799,12241,24406,20352,7742,902,18204,9882,26696,7027,16459,5938,29002,
                19289,22593,26726,13617,16403,13490,18573,10043,24659,10957,23142,501,10911,14397,27032,21671,22015,
                19100,27023,21748,18945,27626,22926,444,26059,5646,22452,12402,11770,4616,12866,29984,24819,22103,19289,
                6441,1896,27982,27257,67,27333,22466,2963,4627,5292,7699,27085,12636,16155,12731,13923,7525,9454,7574,
                8547,18238,700,28119,15413,15064,12657,21931,22840,21672,28962,12435,2570,23951,21375,764,14785,24294,
                28449,10491,14153,13348,22323,21104,14248,5551,17615,750,10673,9236,2001,2922,23650,13595,3862,28020,
                9470,13207,20845,4636,23475,25360,14738,21284,21702,29757,23258,10824,15321,11816,16054,26873,2886,
                26827,15118,4363,28017,5837,810,14127,17211,1517,18867,16894,16895,2429,10703,3918,14038,11119,9896,
                26454,18728,3302,19722,29888,10959,23116,19715,14252,12139,6419,12533,8005,14590,20082,26607,4363,24294,
                22859,18843,5763,2152,4106,9656,8742,24312,10308,4869,29272,21147,24100,14917,2907,17588,13343,9542,
                2625,9484,8170,10688,21401,3500,11366,22766,9291,25680,25922,5677,19488,19183,5093,16734,14258,7223,
                21390,26862,26895,3174,11366,2678,13916,19896,21207,1732,3392,18965,25048,18226,2818,2456,17419,6508,
                22429,9064,24779,27063,15932,21080,22922,18801,19404,12111,26785,5317,4856,21732,6343,15604,27044,10728,
                22069,26073,19760,2724,15491,23912,29934,13760,1688,10475,14985,27883,1956,27475,13200,2986,22549,3633,
                4020,7106,16418,19810,25812,17693,19125,14436,15130,19889,8099,8880,18690,18653,5406,5872,16454,24998,
                9354,5220,1457,15905,15854,18380,16397,19769,26104,9525,23882,6803,9927,14832,15590,22131,782,22153,
                24184,42,16860,562,4150,26136,1649,27223,20025,22978,20257,22752,21959,26846,10796,11485,19483,15905,
                20356,11045,12562,11765,1190,22106,17069,26579,22138,12448,23264,1903,19084,27936,26749,9493,8418,8846,
                26600,10268,12401,9891,7987,26103,13311,16027,12041,10061,12108,26847,8410,20902,6422,27277,14008,8964,
                12858,22193,25693,24378,3895,29020,14320,28534,5125,28228,5789,26082,8121,25229,4915,25643,19841,3642,
                28106,13021,5511,25311,1632,1147,22118,2697,22393,2835,28190,4121,16889,19383,10987,2875,14649,10070,
                26729,6095,6714,17916,13320,8610,4211,25195,2867,27119,28214,3908,27740,7972,23775,9740,13368,21315,
                16388,22638,11890,1870,10304,26540,24070,27786,17676,9847,14452,14796,7351,26139,1773,9948,29345,16811,
                28315,187,24350,18766,25693,22039,29996,10133,22320,25339,20799,23739,24575,8154,8497,12135,714,27152,
                24333,3955,24559,9887,18205,14676,13341,18271,27222,12424,800,25542,28558,24216,4701,12636,22707,25909,
                26096,27710,19249,4160,12605,28785,3967,27989,10788,17727,24404,2788,3371,14352,27130,29109,19630,5745,
                23803,11287,7930,17698,13288,15576,29114,1152,26207,13005,25373,26283,22106,14213,15653,17883,11866,
                21407,25643,10109,28636,16882,8810,4883,21791,8433,24267,28409,10575,11340,9054,7670,29960,24832,25953,
                831,28572,24717,24699,27799,12352,2307,17084,16071,6478,22272,29556,21489,23825,23183,24316,4788,18168,
                18250,26221,13138,7806,16099,14809,505,1464,5644,14778,19757,15630,17247,29760,2630,11182,16362,23720,
                26512,18608,19668,26831,23760,17424,366,12379,2127,28890,24800,10380,3014,16923,10564,21698,25119,17910,
                24550,255,7004,12920,13951,24383,5266,17187,16138,19437,411,1312,2727,11802,17933,22021,19528,3226,
                25975,11836,24491,5135,11409,20114,18903,15564,27695,24347,17876,20330,12456,15388,18908,25123,6000,
                13732,5522,5917,1071,3576,22721,9899,9143,4317,17292,12453,1658,11478,24229,22704,29243,25725,14931,
                23772,23562,12301,11615,16803,13277,18689,24414,27570,19300,15745,6475,11988,6728,21866,16209,4599,4957,
                846,3414,18576,19130,15848,23684,16395,7693,22967,10993,1335,6485,13343,18394,23386,25844,23229,22544,
                17681,5796,19985,20832,8043,11134,8815,29870,20914,10776,5843,10217,7236,24924,5641,11354,16048,7544,
                5067,28842,27111,806,16008,1312,13012,3286,24136,6030,9019,2575,29047,15004,12666,16586,5385,23926,8614,
                25408,22176,8841,14582,8835,28206,25470,16170,20693,2331,24022,10749,8057,9832,26677,3099,29596,13908,
                18822,15200,1881,22973,12032,14464,24646,9348,4229,1369,20700,1991,17804,2045,3388,11817,8757,3218,
                28257,28395,28880,29413,27286,16982,12128,22255,15714,6820,23272,4892,19554,28493,16060,4363,10751,
                15323,12963,4473,17946,26401,29151,5442,19750,653,4495,15402,18193,7437,22042,26762,29103,3118,20501,
                15690,20943,28003,8594,26440,2719,1240,27753,7768,21320,6593,17649,10209,21690,22345,27071,24102,13505,
                3820,20142,13178,21265,29646,10044,13311,10299,9639,7276,10587,10861,10753,28438,11256,523,28946,17431,
                9247,23814,661,25639,3565,726,4302,25057,1522,27414,8755,5910,19652,7273,16958,22918,20031,9522,19000,
                18865,623,13826,18982,11448,4813,27380,326,25959,5002,29742,27321,2755,23030,20035,25940,14892,9363,
                23724,22093,16092,29563,23085,14915,27775,15175,25294,1499,3898,6389,6964,4213,1247,16789,17700,21796,
                18446,13595,13498,422,18492,19078,18773,28851,21099,6322,18319,16618,20185,17845)
            val arrays = arrayOf(intArrayOf(3,1,2,4), intArrayOf(11,81,94,43,3), big1)
            for (array in arrays) {
                val sum = SumOfSubarrayMinimums().sumSubarrayMins(array)
                // print("array: ${array.contentToString()}, ")
                println("sum: $sum")
            }
        }
    }
}
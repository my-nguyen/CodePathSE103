package week12

class InsertDeleteGetRandom {
    class RandomizedSet() {

        /** Initialize your data structure here. */


        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        fun insert(`val`: Int): Boolean {
            return true
        }

        /** Removes a value from the set. Returns true if the set contained the specified element. */
        fun remove(`val`: Int): Boolean {
            return true
        }

        /** Get a random element from the set. */
        fun getRandom(): Int {
            return 0
        }
    }

    /**
     * Your RandomizedSet object will be instantiated and called as such:
     * var obj = RandomizedSet()
     * var param_1 = obj.insert(`val`)
     * var param_2 = obj.remove(`val`)
     * var param_3 = obj.getRandom()
     */

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val methods = arrayOf("RandomizedSet", "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom")
            val params = arrayOf(null, 1, 2, 2, null, 1, 2, null)
        }
    }
}
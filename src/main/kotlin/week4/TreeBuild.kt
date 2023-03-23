package week4

class TreeBuild {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                /*arrayOf(1,2,3,4,null,2,4,null,null,4),*/
                arrayOf(5,4,8,11,null,13,4,7,2,null,null,null,1)
            )
            for (array in arrays) {
                val tree = TreeNode.build(array)
            }
        }
    }
}
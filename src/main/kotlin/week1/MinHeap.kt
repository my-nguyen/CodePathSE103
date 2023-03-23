package week1

class MinHeap() {
    private val list = mutableListOf<Int>()

    fun insert(new_element: Int) {
        list.add(new_element)
        bubbleUp(list.lastIndex)
    }

    fun bubbleUp(currentIndex: Int) {
        var currentIndex = currentIndex
        var parentIndex = (currentIndex-1) / 2
        while (parentIndex >= 0) {
            val currentElement = list[currentIndex]
            val parentElement = list[parentIndex]
            if (currentElement < parentElement) {
                list[parentIndex] = currentElement
                list[currentIndex] = parentElement
                currentIndex = parentIndex
                parentIndex = (parentIndex - 1) / 2
            } else {
                break
            }
        }
    }
}
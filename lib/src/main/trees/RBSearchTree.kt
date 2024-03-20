package main.trees
import main.vertexes.RBVertex

class RBSearchTree<K, V> : AbstractBinarySearchTree<K, V, RBVertex<K,V>> {

    override fun remove(key: K): V? { TODO()
//        var currentVertex: RBVertex<K, V>? = root
//        var parent: RBVertex<K, V>? = null
//        var isLeft: Boolean = false
//        val cpr = comparator
//
//        while (currentVertex != null) {
//            if (cpr != null) {
//
//                if (cpr.compare(currentVertex.key, key) == 0) {
//                    if (countChildren(currentVertex) < 2){
//                        parent?.key = currentVertex.key
//                        parent?.key = currentVertex.key
//                        parent?.key = currentVertex.key
//                    }
//                    break
//                }
//
//                parent = currentVertex
//                if (cpr.compare(currentVertex.key, key) < 0){
//                    currentVertex = currentVertex.rightSon
//                    isLeft = false
//                }
//
//                else if (cpr.compare(currentVertex.key, key) > 0){
//                    currentVertex = currentVertex.leftSon
//                    isLeft = true
//                }
//            } else {
//
//                val comparableKey = key as Comparable<K>
//                if (comparableKey.compareTo(currentVertex.key) == 0) {
//                    currentVertex.value = value
//                    break
//                }
//
//                parent = currentVertex
//                if (comparableKey.compareTo(currentVertex.key) < 0){
//                    currentVertex = currentVertex.rightSon
//                    isLeft = false
//                }
//
//                else if (comparableKey.compareTo(currentVertex.key) > 0){
//                    currentVertex = currentVertex.leftSon
//                    isLeft = true
//                }
//            }
//        }
    }

    override fun put(key: K, value: V, replaceIfExists: Boolean) {
        var currentVertex: RBVertex<K, V>? = root
        var parent: RBVertex<K, V>? = null
        var isLeft: Boolean = false
        val cpr = comparator

        while (currentVertex != null) {
            if (cpr != null) {

                if (cpr.compare(currentVertex.key, key) == 0) {
                    currentVertex.value = value
                    break
                }

                parent = currentVertex
                if (cpr.compare(currentVertex.key, key) < 0){
                    currentVertex = currentVertex.rightSon
                    isLeft = false
                }

                else if (cpr.compare(currentVertex.key, key) > 0){
                    currentVertex = currentVertex.leftSon
                    isLeft = true
                }
            } else {

                val comparableKey = key as Comparable<K>
                if (comparableKey.compareTo(currentVertex.key) == 0) {
                    currentVertex.value = value
                    break
                }

                parent = currentVertex
                if (comparableKey.compareTo(currentVertex.key) < 0){
                    currentVertex = currentVertex.rightSon
                    isLeft = false
                }

                else if (comparableKey.compareTo(currentVertex.key) > 0){
                    currentVertex = currentVertex.leftSon
                    isLeft = true
                }
            }
        }

        if (currentVertex == null){
            currentVertex = RBVertex(key, value, null, null, true, parent)
            if (isLeft) parent?.let { parent.leftSon = currentVertex }
            else parent?.let { parent.rightSon = currentVertex }
        }
    }

    private fun countChildren(vertex: RBVertex<K, V>): Int {
        var numOfChild = 0
        if (vertex.leftSon != null) ++numOfChild
        if (vertex.rightSon != null) ++numOfChild
        return numOfChild
    }

    constructor(comparator: Comparator<K>? = null) : super(comparator)

    constructor(map: Map<K, V>, comparator: Comparator<K>? = null) : super(map, comparator)
}

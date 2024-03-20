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

    //suppose that vertex has a rightSon
    private fun rotateLeft(vertex: RBVertex<K, V>) {
        val rightVertex: RBVertex<K, V>? = vertex.rightSon
        vertex.rightSon = rightVertex?.leftSon
        rightVertex?.leftSon.let { rightVertex?.leftSon?.parent = vertex }
        rightVertex?.parent = vertex.parent
        if (vertex.parent == null) root = rightVertex
        else if (vertex == vertex.parent?.leftSon) vertex.parent?.leftSon = rightVertex
        else vertex.parent?.rightSon = rightVertex
        vertex.parent = rightVertex
        rightVertex?.leftSon = vertex
    }

    //suppose that vertex has a leftSon
    private fun rotateRight(vertex: RBVertex<K, V>) {
        val leftVertex: RBVertex<K, V>? = vertex.leftSon
        vertex.leftSon = leftVertex?.rightSon
        leftVertex?.rightSon.let { leftVertex?.rightSon?.parent = vertex}
        leftVertex?.parent = vertex.parent
        if (vertex.parent == null) root = leftVertex
        else if (vertex == vertex.parent?.leftSon) vertex.parent?.leftSon = leftVertex
        else vertex.parent?.rightSon = leftVertex
        vertex.parent = leftVertex
        leftVertex?.rightSon = vertex
    }

    constructor(comparator: Comparator<K>? = null) : super(comparator)

    constructor(map: Map<K, V>, comparator: Comparator<K>? = null) : super(map, comparator)
}

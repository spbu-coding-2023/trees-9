package main.trees
import main.vertexes.SimpleBSTVertex

class SimpleBinarySearchTree<K, V> : AbstractBinarySearchTree<K, V, SimpleBSTVertex<K,V>> {

    override fun put(key: K, value: V, replaceIfExists: Boolean) {
        putRec(key, value, replaceIfExists)
    }

    private fun putRec(key: K, value: V, replaceIfExists: Boolean = true, vertex: SimpleBSTVertex<K, V>? = root) {
        if (vertex == null) {
            root = SimpleBSTVertex(key, value)
            return
        }

        val cpr = comparator
        if (cpr != null) {
            if (cpr.compare(key, vertex.key) == 0 && replaceIfExists) vertex.value = value
            else if (cpr.compare(key, vertex.key) == 0 && !replaceIfExists) return

            else if (cpr.compare(key, vertex.key) < 0) {
                if (vertex.leftSon == null) vertex.leftSon = SimpleBSTVertex(key, value)
                else putRec(key, value, replaceIfExists, vertex.leftSon)
            }
            else {
                if (vertex.rightSon == null) vertex.rightSon = SimpleBSTVertex(key, value)
                else putRec(key, value, replaceIfExists, vertex.rightSon)
            }
        }
        else {
            val comparableKey: Comparable<K> = key as Comparable<K>
            if (comparableKey.compareTo(vertex.key) == 0 && replaceIfExists) vertex.value = value
            else if (comparableKey.compareTo(vertex.key) == 0 && !replaceIfExists) return

            else if (comparableKey.compareTo(vertex.key) < 0) {
                if (vertex.leftSon == null) vertex.leftSon = SimpleBSTVertex(key, value)
                else putRec(key, value, replaceIfExists, vertex.leftSon)
            }
            else {
                if (vertex.rightSon == null) vertex.rightSon = SimpleBSTVertex(key, value)
                else putRec(key, value, replaceIfExists, vertex.rightSon)
            }
        }
    }

    override fun remove(key: K): V? {TODO()}

    private fun removeRec(key: K, vertex: SimpleBSTVertex<K, V>? = root): SimpleBSTVertex<K, V>? {
        if (vertex == null) return null
        val cpr = comparator
        if (cpr != null) {
            if (cpr.compare(key, vertex.key) < 0) vertex.leftSon = removeRec(key, vertex.leftSon)
            else if (cpr.compare(key, vertex.key) > 0) vertex.rightSon = removeRec(key, vertex.rightSon)

            else if (cpr.compare(key, vertex.key) == 0) {
                if (vertex.leftSon == null || vertex.rightSon == null) {
                    vertex = if (vertex.leftSon == null) vertex.rightSon else vertex.leftSon
//                    vertex.key = vertexForSubstitution.key
//                    vertex.value = vertexForSubstitution.value
//                    vertex.leftSon = vertexForSubstitution.leftSon
//                    vertex.rightSon = vertexForSubstitution.rightSon
                }
                else if (vertex.leftSon != null && vertex.rightSon != null) {
                    val minKeyInRightSubtreeNode: SimpleBSTVertex<K, V>? = getMinKeyNodeRec(vertex.rightSon)
                    vertex.key = minKeyInRightSubtreeNode.key
                    vertex.value = minKeyInRightSubtreeNode.value
                    vertex.rightSon = removeRec(minKeyInRightSubtreeNode.key, vertex.rightSon)
                }
            }
            else error("this key isn't in this SimpleBinarySearchTree\n")
        }
        else {
            TODO()
        }
        return vertex
    }

    constructor(comparator: Comparator<K>?) : super(comparator)

    constructor(map: Map<K, V>, comparator: Comparator<K>? = null) : super(map, comparator)
}

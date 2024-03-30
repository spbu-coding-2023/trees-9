package main.trees
import main.vertexes.SimpleBSTVertex

class SimpleBinarySearchTree<K, V> : AbstractBinarySearchTree<K, V, SimpleBSTVertex<K,V>> {

    override fun put(key: K, value: V, replaceIfExists: Boolean) {
        if (root == null) {
            root = SimpleBSTVertex(key, value)
            size++
        }
        putRec(key, value, replaceIfExists)
    }

    private fun putRec(key: K, value: V, replaceIfExists: Boolean = true, vertex: SimpleBSTVertex<K, V>? = root) {
        if (vertex == null) return
        when (compareKeys(key, vertex.key)) {
            0 -> if (replaceIfExists) vertex.value = value
            -1 -> {
                if (vertex.leftSon == null) {
                    vertex.leftSon = SimpleBSTVertex(key, value)
                    size++
                }
                else putRec(key, value, replaceIfExists, vertex.leftSon)
            }
            1 -> {
                if (vertex.rightSon == null) {
                    vertex.rightSon = SimpleBSTVertex(key, value)
                    size++
                }
                else putRec(key, value, replaceIfExists, vertex.rightSon)
            }
        }
    }

    override fun remove(key: K): V? {
        val (_, deletedValue, isRemoved)= removeRec(key)
        if (isRemoved) size--
        return deletedValue
    }

    private fun removeRec(key: K, vertex: SimpleBSTVertex<K, V>? = root): Triple<SimpleBSTVertex<K, V>?, V?, Boolean> {
        if (vertex == null) return Triple(null, null, false)

        when (compareKeys(key, vertex.key)) {
            -1 -> {
                val (updateLeftSon, deletedValue, isRemoved) = removeRec(key, vertex.leftSon)
                vertex.leftSon = updateLeftSon
                return Triple(vertex, deletedValue, isRemoved)
            }
            1 -> {
                val (updateLeftSon, deletedValue, isRemoved) = removeRec(key, vertex.rightSon)
                vertex.rightSon = updateLeftSon
                return Triple(vertex, deletedValue, isRemoved)
            }
            else -> {
                val deletedValue: V = vertex.value
                if (vertex.leftSon == null || vertex.rightSon == null) {
                    if (vertex.leftSon == null) return Triple(vertex.rightSon, deletedValue, true)
                    return Triple(vertex.leftSon, deletedValue, true)
                }
                val minKeyRightSubtreeNode: SimpleBSTVertex<K, V>? = getMinKeyNodeRec(vertex.rightSon)
                minKeyRightSubtreeNode?.let {
                    vertex.key = it.key
                    vertex.value = it.value
                    val (updatedRightSon, _, _) = removeRec(it.key, vertex.rightSon)
                    vertex.rightSon = updatedRightSon
                }
                return Triple(vertex, deletedValue, true)
            }
        }
    }

    constructor(comparator: Comparator<K>?) : super(comparator)

    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) : super(map, replaceIfExists, comparator)
}


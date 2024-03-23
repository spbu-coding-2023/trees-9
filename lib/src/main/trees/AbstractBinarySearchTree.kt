package main.trees
import main.vertexes.InterfaceBSTVertex
import main.iterator.TreeIterator

abstract class AbstractBinarySearchTree<K, V, N : InterfaceBSTVertex<K, V, N>> {

    protected var comparator: Comparator<K>? = null
    protected var size : Long = 0L
    protected var root : N? = null

    operator fun iterator(): Iterator<Pair<K, V>> {
        return TreeIterator(root)
    }

    fun size(): Long {return size}

    fun isEmpty(): Boolean {return size == 0L}
    
    fun get(key: K): V? {return getRec(key)}

    fun getPair(key: K): Pair<K, V>? {
        val value = get(key)
        return if (value == null) null else Pair(key, value)
    }

    fun getMin(): V? {
        val minKeyNode = getMinKeyNodeRec()
        return if (minKeyNode == null) null else minKeyNode.value
    }

    fun getMax(): V?{
        val maxKeyNode = getMaxKeyNodeRec()
        return if (maxKeyNode == null) null else maxKeyNode.value
    }

    fun getMinKeyPair(): Pair<K, V>? {
        val minKeyNode = getMinKeyNodeRec()
        return if (minKeyNode == null) null else Pair(minKeyNode.key, minKeyNode.value)
    }

    fun getMaxKeyPair(): Pair<K, V>? {
        val maxKeyNode = getMaxKeyNodeRec()
        return if (maxKeyNode == null) null else Pair(maxKeyNode.key, maxKeyNode.value)
    }

    abstract fun put(key: K, value: V, replaceIfExists : Boolean = true)
// возможно нужен комментарий

    fun putAll(map: Map<K, V>, replaceIfExists: Boolean = true) {
        for (pair in map) put(pair.key, pair.value, replaceIfExists)
    }

    abstract fun remove(key: K): V?

    fun removeAndReturnPair(key: K): Pair<K, V>? {
        val value = remove(key)
        return if (value == null) null else Pair(key, value)
    }

    private fun getRec(key: K, vertex: N? = root): V? {
        if (vertex == null) return null
        if (vertex.key == key) return vertex.value
        val cpr = comparator
        if (cpr != null) {
            return if (cpr.compare(key, vertex.key) < 0) getRec(key, vertex.leftSon)
            else getRec(key, vertex.rightSon)
        }
        else {
            val comparableKey: Comparable<K> = key as Comparable<K>
            return if (comparableKey.compareTo(vertex.key) < 0) getRec(key, vertex.leftSon)
            else getRec(key, vertex.rightSon)
        }
    }

    protected fun getMinKeyNodeRec(vertex: N? = root) : N? {
        if (vertex == null) return null
        else {
            return if (vertex.leftSon == null) vertex
            else getMinKeyNodeRec(vertex.leftSon)
        }
    }

    protected fun getMaxKeyNodeRec(vertex: N? = root) : N? {
        if (vertex == null) return null
        else {
            return if (vertex.rightSon == null) vertex
            else getMaxKeyNodeRec(vertex.rightSon)
        }
    }

    protected fun compareKeys(firstKey: K, secondKey: K): Int{
        val cpr = comparator
        return if (cpr != null) {
            when (cpr.compare(firstKey, secondKey)) {
                in Int.MIN_VALUE .. -1 -> -1
                0 ->  0
                else -> 1
        }
    }
        else {
            val comparableKey = firstKey as Comparable<K>
            when (comparableKey.compareTo(secondKey)) { 
                in Int.MIN_VALUE .. -1 -> -1
                0 -> 0
                else -> 1
            }
        }
    }

    constructor(comparator: Comparator<K>? = null) {
        this.comparator = comparator
    }

    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) {
        putAll(map, replaceIfExists)
        this.comparator = comparator
    }
}

abstract class AbstractBinarySearchTree<K, V, N : InterfaceBSTVertex<K, V, N>> {

    protected var comparator: Comparator<K>? = null
    protected var size : Long = 0L
    protected var root : InterfaceBSTVertex<K, V, N>? = null

    operator fun iterator(): Iterator<Pair<K, V>> {TODO()}

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

    abstract fun put(key: K, value: V)

    fun putAll(map: Map<K, V>) {
        for (pair in map) put(pair.key, pair.value)
    }

    abstract fun remove(key: K): V?

    fun removeAndReturnPair(key: K): Pair<K, V>? {
        val value = remove(key)
        return if (value == null) null else Pair(key, value)
    }

    protected fun getMinKeyNodeRec(vertex: InterfaceBSTVertex<K, V, N>? = root) : InterfaceBSTVertex<K, V, N>? {TODO()}

    protected fun getMaxKeyNodeRec(vertex: InterfaceBSTVertex<K, V, N>? = root) : InterfaceBSTVertex<K, V, N>? {TODO()}

    constructor(comparator: Comparator<K>? = null) {
        this.comparator = comparator
    }

    constructor(map: Map<K, V>, comparator: Comparator<K>? = null) {
        putAll(map)
        this.comparator = comparator
    }
}

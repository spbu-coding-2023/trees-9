abstract class AbstractBinarySearchTree<K, V, N : InterfaceBSTVertex<K, V, N>> {

    protected var comparator: Comparator<K>? = null
    protected var size : Long = 0L
    protected var root : InterfaceBSTVertex<K, V, N>? = null

    operator fun iterator(): Iterator<Pair<K, V>> {TODO()}

    fun size(): Long {return size}

    fun isEmpty(): Boolean {return size == 0L}
    
    fun get(key: K): V? {TODO()}

    fun getPair(key: K): Pair<K, V>? {TODO()}

    fun getMin(): V? {TODO()}

    fun getMax(): V?{TODO()}

    fun getMinKeyPair(): Pair<K, V>? {TODO()}

    fun getMaxKeyPair(): Pair<K, V>? {TODO()}

    abstract fun put(key: K, value: V)

    fun putAll(map: Map<K, V>){TODO()}

    abstract fun remove(key: K): V?

    fun removeAndReturnPair(key: K): Pair<K, V>? {TODO()}

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

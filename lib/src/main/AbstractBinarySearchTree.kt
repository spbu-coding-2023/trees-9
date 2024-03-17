abstract class AbstractBinarySearchTree<K, V, N : InterfaceBSTVertex<K, V, N>> {

    protected var comparator: Comparator<K>? = null
    protected var size : Long = 0L
    protected var root : InterfaceBSTVertex<K, V, N>? = null

    operator fun iterator(): Iterator<Pair<K, V>> {TODO()}

    fun size(): Long {TODO()}

    fun isEmpty(): Boolean {TODO()}
    
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

    private fun getRec(key: K, vertex: InterfaceBSTVertex<K,V,N>? = root): V? {
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

class AVLSearchTree<K, V> : AbstractBinarySearchTree<K, V, AVLVertex<K,V>> {

    override fun put(key: K, value: V, replaceIfExists: Boolean ) {TODO()}

    override fun remove(key: K): V? {TODO()}

    private fun rotateLeft(curVertex: AVLVertex<K,V>) {
        val rightSon : AVLVertex<K,V>  = (curVertex.rightSon as AVLVertex<K,V>)
        curVertex.rightSon = rightSon.leftSon
        rightSon.leftSon = curVertex
        when (rightSon.sonsHeightDiff){
            0 -> rightSon.sonsHeightDiff = 1
            -1 -> {curVertex.sonsHeightDiff = 0; rightSon.sonsHeightDiff = 0}
        }
    }
    
    constructor (comparator: Comparator<K>? = null) : super(comparator)

    constructor(map: Map<K, V>, comparator: Comparator<K>? = null) : super(map, comparator)
}

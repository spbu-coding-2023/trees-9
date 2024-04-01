import main.trees.SimpleBinarySearchTree
import main.vertexes.SimpleBSTVertex

class TestSimpleBST<K, V> : SimpleBinarySearchTree<K, V> {
    fun getTreeRoot(): SimpleBSTVertex<K, V>? {
        return root
    }

    constructor(comparator: Comparator<K>?) : super(comparator)
    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) : super(
        map,
        replaceIfExists,
        comparator
    )
}

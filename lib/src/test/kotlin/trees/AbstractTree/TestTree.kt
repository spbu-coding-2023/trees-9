package trees.AbstractTree

import main.trees.AbstractBinarySearchTree
import main.vertexes.SimpleBSTVertex

class TestTree<K,V> : AbstractBinarySearchTree<K,V, SimpleBSTVertex<K,V>> {
 
        var removeShouldReturns : V? = null
        var getShouldReturns : V? = null
        val putWasCalledWithParams : MutableList<Triple<K, V, Boolean>> = mutableListOf()

        override fun put(key: K, value: V, replaceIfExists : Boolean) {
                putWasCalledWithParams.add(Triple(key, value, replaceIfExists))
        }

        override fun remove(key: K) : V? {return removeShouldReturns}

        fun compareKeysT(firstKey: K, secondKey: K): Int {
                return super.compareKeys(firstKey, secondKey)
        }
        constructor (root: SimpleBSTVertex<K,V>, size: Long, comparator: Comparator<K>? = null) :
        super(comparator) {
           this.root = root
           this.size = size
        }

         constructor (removeShouldReturns : V?) : super() {this.removeShouldReturns = removeShouldReturns}
       
        constructor (comparator: Comparator<K>? = null) : super(comparator)

        constructor (map: Map<K, V>, replaceIfExists: Boolean = true,
                comparator: Comparator<K>? = null) : super(map, replaceIfExists, comparator)
}

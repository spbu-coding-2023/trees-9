package iterator

import vertexes.InterfaceBSTVertex
import java.util.Stack

/**
 * Iterator iterates over the vertices of the tree, visiting each vertex in the order of a depth-first traversal.
 * Iterator interface implementation.
 * @param K the type of keys in the tree
 * @param V the type of values associated with the keys
 * @param N the type of tree vertices implementing InterfaceBSTVertex
 */
open class TreeIterator<K, V, N : InterfaceBSTVertex<K, V, N>>(
    vertex: N?,
) : Iterator<Pair<K, V>> {
    protected val stack = Stack<N>()

    init {
        // Initialize the iterator with the given vertex by adding it to the stack
        vertex?.let { stack.add(it) }
    }

    /**
     * Returns true if the iterator has more elements.
     * This method checks if there are more vertices to traverse in the tree.
     * @return true if the iterator has more elements, otherwise false
     */
    override fun hasNext(): Boolean {
        return stack.isNotEmpty()
    }

    /**
     * Returns the next element in the iteration.
     * This method returns the next vertex in the depth-first traversal of the tree.
     * @return the next element in the iteration as a Pair containing the key and value of the vertex
     */
    override fun next(): Pair<K, V> {
        val nextVertex: N = stack.pop()
        nextVertex.leftSon?.let { stack.add(it) }
        nextVertex.rightSon?.let { stack.add(it) }
        return Pair(nextVertex.key, nextVertex.value)
    }
}

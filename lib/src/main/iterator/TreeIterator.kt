package main.iterator
import main.nodes.InterfaceBSTVertex
import main.pair.Pair
import java.util.LinkedList

internal class TreeIterator<K, V, N: InterfaceBSTVertex<K, V, N>>(
    vertex: InterfaceBSTVertex<K, V, N>?
): Iterator<Pair<K, V>> {
    private val stack = LinkedList<N>()

    init {
        vertex?.let { stack.add(it as N) }
    }

    override fun hasNext(): Boolean {TODO()}

    override fun next(): Pair<K, V> {TODO()}
}

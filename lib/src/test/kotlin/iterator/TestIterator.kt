package iterator

import vertexes.InterfaceBSTVertex
import java.util.Stack

internal class TestIterator<K, V, N : InterfaceBSTVertex<K, V, N>>(vertex: N?) : TreeIterator<K, V, N>(vertex) {
    fun getTreeStack(): Stack<N> {
        return stack
    }
}

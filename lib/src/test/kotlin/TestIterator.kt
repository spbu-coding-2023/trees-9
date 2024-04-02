import java.util.LinkedList
import main.vertexes.InterfaceBSTVertex
import main.iterator.TreeIterator

internal class TestIterator<K, V, N : InterfaceBSTVertex<K, V, N>>(vertex: N?) : TreeIterator<K, V, N>(vertex) {
    fun getTreeStack(): LinkedList<N> {
        return stack
    }
}

import java.util.LinkedList
import main.iterator.TreeIterator
import main.vertexes.InterfaceBSTVertex

internal class TestIterator<K, V, N : InterfaceBSTVertex<K, V, N>>(vertex: N?) : TreeIterator<K, V, N>(vertex) {
    fun getTreeStack(): LinkedList<N> {
        return stack
    }
}

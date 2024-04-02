import main.vertexes.InterfaceBSTVertex
import main.iterator.TreeIterator
import java.util.LinkedList

internal class TestIterator<K, V, N: InterfaceBSTVertex<K, V, N>>(vertex: N?) : TreeIterator<K, V, N>(vertex) {
        fun getTreeStack(): LinkedList<N> {return stack}
}
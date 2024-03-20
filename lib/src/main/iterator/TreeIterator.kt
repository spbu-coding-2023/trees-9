package main.iterator
import main.vertexes.InterfaceBSTVertex

internal class TreeIterator<K, V, N: InterfaceBSTVertex<K, V, N>>(
    vertex: InterfaceBSTVertex<K, V, N>?): Iterator<Pair<K, V>> {

    override fun hasNext(): Boolean {TODO()}

    override fun next(): Pair<K, V> {TODO()}
}

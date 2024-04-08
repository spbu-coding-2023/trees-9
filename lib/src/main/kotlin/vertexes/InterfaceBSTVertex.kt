package vertexes

/**
 * Represents a generic vertex in a binary search tree
 *
 * @param K key type
 * @param V value type
 * @param N child vertices type
 * @property key
 * @property value
 * @property leftSon `N?` type
 * @property rightSon `N?` type
 */
interface InterfaceBSTVertex<K, V, N> {
    var key: K
    var value: V
    var leftSon: N?
    var rightSon: N?
}

package vertexes

/**
 * Represents a generic vertex in a Binary Search Tree (BST).
 * @param K Type of keys.
 * @param V Type of values.
 * @param N Type of the child vertices.
 * @property key The key associated with this vertex.
 * @property value The value associated with this vertex.
 * @property leftSon The left child vertex of this vertex, of type N.
 * @property rightSon The right child vertex of this vertex, of type N.
 */
interface InterfaceBSTVertex<K, V, N> {
    var key: K
    var value: V
    var leftSon: N?
    var rightSon: N?
}

package main.trees
import main.vertexes.SimpleBSTVertex

/**
 * This class represents a simple implementation of a binary search tree.
 * It extends AbstractBinarySearchTree and uses SimpleBSTVertex as vertices.
 * @param K The type of keys in the tree.
 * @param V The type of values in the tree.
 * @property comparator The comparator used to order the keys. If null, keys are expected to be comparable.
 * @property size The number of elements in the tree.
 * @property root The root vertex of the tree.
 */
class SimpleBinarySearchTree<K, V> : AbstractBinarySearchTree<K, V, SimpleBSTVertex<K,V>> {

    /**
     * Associates the specified value with the specified key in this tree.
     * If parameter replaceIfExists is true and the key already exists, the value is replaced; otherwise, the value is ignored.
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @param replaceIfExists if true, replaces the value if the key already exists, otherwise ignores it
     */
    override fun put(key: K, value: V, replaceIfExists: Boolean) {TODO()}

    /**
     * Recursively inserts a key-value pair into the tree.
     * @param key The key to insert.
     * @param value The value associated with the key.
     * @param replaceIfExists If true, replaces the existing value for the key.
     * @param vertex The current vertex in the recursion.
     */

    /**
     * Removes the key-value pair associated with the specified key from the tree.
     * @param key The key to remove.
     * @return The value associated with the removed key, or null if the key is not found.
     */
    override fun remove(key: K): V? {TODO()}

    /**
     * Recursively removes the key-value pair associated with the specified key from the tree.
     * This method traverses the tree recursively to find the node with the given key and removes it.
     * If the key is found and the corresponding node has both left and right children,
     * the method replaces the node's key and value with those of the minimum key node in its right subtree,
     * and then removes the minimum key node from the right subtree.
     * If the key is not found, it returns a pair containing the vertex and null value.
     * @param key The key to remove.
     * @param vertex The current vertex being examined in the recursion.
     * @return A pair containing the updated vertex and the value associated with the removed key, or null if the key is not found.
     */

    /**
     * Constructs a new binary search tree with the specified comparator.
     * @param comparator the comparator to use for comparing keys, or null to use natural ordering
     */
    constructor(comparator: Comparator<K>?) : super(comparator)

    /**
     * Constructs a new binary search tree and initializes it with the mappings from the specified map.
     * @param map the map whose mappings are to be added to this tree
     * @param replaceIfExists if true, replaces the value if the key already exists, otherwise ignores it
     * @param comparator the comparator to use for comparing keys, or null to use natural ordering
     */
    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) : super(map, replaceIfExists, comparator)
}

package iterator

import org.junit.jupiter.api.Assertions.assertEquals
import trees.AVLSearchTree
import vertexes.SimpleBSTVertex
import kotlin.test.Test

class IteratorTests {
    @Test
    fun `add in stack`() {
        val vertex = SimpleBSTVertex(1, "one")
        val iterator = TestIterator(vertex)
        assertEquals(1, iterator.getTreeStack().removeLast().key)
    }

    @Test
    fun `hasNext if stack is not empty`() {
        val vertex = SimpleBSTVertex(1, "one")
        val iterator = TestIterator(vertex)
        assertEquals(true, iterator.hasNext())
    }

    @Test
    fun `check if method next() works correctly`() {
        val vertex = SimpleBSTVertex(1, "one")
        val iterator = TestIterator(vertex)
        val deletedVertex = iterator.next()
        assertEquals(Pair(1, "one"), deletedVertex)
    }

    @Test
    fun `hasNext if stack is empty`() {
        val vertex = SimpleBSTVertex(1, "one")
        val iterator = TestIterator(vertex)
        iterator.next()
        assertEquals(false, iterator.hasNext())
    }

    @Test
    fun `check if method iterator() works correctly`() {
        val tree = AVLSearchTree(mapOf(Pair(3, "three"), Pair(1, "one"), Pair(2, "two"), Pair(150, "one-five-zero")))
        val list: MutableList<Pair<Int, String>> = mutableListOf()
        for (pair in tree) {
            list.add(pair)
        }
        assertEquals(mutableListOf(Pair(2, "two"), Pair(3, "three"), Pair(150, "one-five-zero"), Pair(1, "one")), list)
    }
}

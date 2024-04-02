package trees.simpleBSTree

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SimpleBSTreeTest {
    private lateinit var tree: TestSimpleBST<Int, String>

    @BeforeEach
    fun setup() {
        tree = TestSimpleBST()
    }

    @Test
    fun `replacing value of existing key`() {
        tree.put(1, "old")
        tree.put(1, "new", true)
        assertEquals("new", tree.getTreeRoot()?.value)
        assertEquals(1, tree.size())
    }

    @Test
    fun `create tree from a map`() {
        val tree = TestSimpleBST(mapOf(Pair(1, "cat"), Pair(2, "dog")))
        assertEquals("cat", tree.getTreeRoot()?.value)
        assertEquals(2, tree.size())
    }

    @Test
    fun `non replacing value of existing key`() {
        tree.put(1, "old")
        tree.put(1, "new", false)
        assertEquals("old", tree.getTreeRoot()?.value)
        assertEquals(1, tree.size())
    }

    @Test
    fun `put key more than root key test`() {
        tree.put(1, "one")
        tree.put(2, "right")
        assertEquals("right", tree.getTreeRoot()?.rightSon?.value)
        assertEquals(2, tree.size())
    }

    @Test
    fun `put key less than root key test`() {
        tree.put(1, "one")
        tree.put(0, "left")
        assertEquals("left", tree.getTreeRoot()?.leftSon?.value)
        assertEquals(2, tree.size())
    }

    @Test
    fun `put many vertexes test`() {
        tree.put(0, "hello")
        for (key in 1..6) {
            tree.put(key, "hello")
        }
        assertEquals(7, tree.size())
    }

    @Test
    fun `put many vertexes with method putAll() test`() {
        tree.putAll(
            mapOf(
                Pair(1, "one"),
                Pair(4, "four"),
                Pair(0, "zero"),
                Pair(3, "three"),
                Pair(5, "five"),
                Pair(2, "two"),
            ),
        )
        assertEquals(6, tree.size())
    }

    @Test
    fun `put many vertexes with same key test`() {
        for (i in 1..6) {
            tree.put(1, "one")
        }
        assertEquals(1, tree.size())
    }

    @Test
    fun `remove no sons root test`() {
        tree.put(1, "one")
        tree.remove(1)
        assertEquals(null, tree.getTreeRoot())
        assertEquals(0, tree.size())
    }

    @Test
    fun `remove no sons vertex test`() {
        tree.putAll(mapOf(Pair(1, "one"), Pair(0, "zero")))
        tree.remove(0)
        assertEquals(null, tree.getTreeRoot()?.leftSon)
        assertEquals(1, tree.size())
    }

    @Test
    fun `remove one left son vertex test`() {
        tree.putAll(mapOf(Pair(1, "one"), Pair(0, "zero"), Pair(-1, "negative")))
        tree.remove(0)
        assertEquals(-1, tree.getTreeRoot()?.leftSon?.key)
        assertEquals(2, tree.size())
    }

    @Test
    fun `remove one right son vertex test`() {
        tree.putAll(mapOf(Pair(1, "one"), Pair(2, "two"), Pair(3, "three")))
        tree.remove(2)
        assertEquals(3, tree.getTreeRoot()?.rightSon?.key)
        assertEquals(2, tree.size())
    }

    @Test
    fun `remove two sons vertex test`() {
        tree.putAll(mapOf(Pair(1, "one"), Pair(2, "two"), Pair(0, "zero")))
        tree.remove(1)
        assertEquals(2, tree.getTreeRoot()?.key)
        assertEquals(2, tree.size())
    }

    @Test
    fun `remove two sons with right subtree vertex test`() {
        tree.putAll(
            mapOf(
                Pair(1, "one"),
                Pair(4, "four"),
                Pair(0, "zero"),
                Pair(3, "three"),
                Pair(5, "five"),
                Pair(2, "two"),
            ),
        )
        tree.remove(1)
        assertEquals(2, tree.getTreeRoot()?.key)
        assertEquals(5, tree.size())
    }

    @Test
    fun `remove many vertex test`() {
        tree.putAll(
            mapOf(
                Pair(1, "one"),
                Pair(4, "four"),
                Pair(0, "zero"),
                Pair(3, "three"),
                Pair(5, "five"),
                Pair(2, "two"),
            ),
        )
        for (key in 0..5) {
            tree.remove(key)
        }
        assertEquals(null, tree.getTreeRoot())
        assertEquals(0, tree.size())
    }

    @Test
    fun `remove non-existing value`() {
        tree.put(1, "one")
        val deletedValue = tree.remove(100)
        assertEquals(null, deletedValue)
        assertEquals(1, tree.size())
    }

    @Test
    fun `put vertex after remove root test`() {
        tree.put(1, "one")
        val deletedValue = tree.remove(1)
        assertEquals("one", deletedValue)
        tree.put(2, "two")
        assertEquals("two", tree.getTreeRoot()?.value)
        assertEquals(1, tree.size())
    }
}

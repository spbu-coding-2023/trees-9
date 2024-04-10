package trees.simpleBSTree

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestSimpleBST {
    private lateinit var tree: SimpleBSTForTest<Int, String>

    @BeforeEach
    fun setup() {
        tree = SimpleBSTForTest()
    }

    @Test
    fun `create tree from map`() {
        val map = mapOf(Pair(1, "cat"), Pair(2, "rabbit"))
        val tree = SimpleBSTForTest(map, true)

        assertEquals("cat", tree.getTreeRoot()?.value)
        assertEquals("rabbit", tree.getTreeRoot()?.rightSon?.value)
        assertEquals(2L, tree.size())
    }

    // put
    @Test
    fun `put key-value pair to empty tree`() {
        tree.put(1, "meow")

        assertEquals("meow", tree.getTreeRoot()?.value)
        assertEquals(1L, tree.size())
    }

    @Test
    fun `put existing key and do not replace it`() {
        tree.put(1, "old")
        tree.put(1, "new", false)

        assertEquals("old", tree.getTreeRoot()?.value)
        assertEquals(1L, tree.size())
    }

    @Test
    fun `put existing key and replace it`() {
        tree.put(1, "old")
        tree.put(1, "new", true)

        assertEquals("new", tree.getTreeRoot()?.value)
        assertEquals(1L, tree.size())
    }

    @Test
    fun `put key smaller than root key`() {
        tree.put(1, "one")
        tree.put(0, "zero")

//                  1
//                /  \
//              0    null

        assertEquals("zero", tree.getTreeRoot()?.leftSon?.value)
        assertEquals(2L, tree.size())
    }

    @Test
    fun `put key smaller than root key, leftSon != null and replace existing key`() {
        tree.put(3, "three", true)
        tree.put(2, "two dogs", true)
        tree.put(2, "two cats", true)
        tree.put(1, "one", true)

//                      3
//                    /  \
//                  2    null
//                /  \
//              1    null

        assertEquals("three", tree.getTreeRoot()?.value)
        assertEquals("two cats", tree.getTreeRoot()?.leftSon?.value)
        assertEquals("one", tree.getTreeRoot()?.leftSon?.leftSon?.value)
        assertEquals(3L, tree.size())
    }

    @Test
    fun `put key smaller than root key and leftSon != null and do not replace existing key`() {
        tree.put(3, "three", false)
        tree.put(2, "two dogs", false)
        tree.put(2, "two cats", false)
        tree.put(1, "one", false)

//                       3
//                     /  \
//                   2    null
//                 /  \
//               1    null

        assertEquals("three", tree.getTreeRoot()?.value)
        assertEquals("two dogs", tree.getTreeRoot()?.leftSon?.value)
        assertEquals("one", tree.getTreeRoot()?.leftSon?.leftSon?.value)
        assertEquals(3, tree.size())
    }

    @Test
    fun `put key more than root key`() {
        tree.put(1, "one")
        tree.put(2, "two")

        assertEquals("two", tree.getTreeRoot()?.rightSon?.value)
        assertEquals(2, tree.size())
    }

    @Test
    fun `put key more than root key, rightSon != null and replace existing key`() {
        tree.put(1, "one", true)
        tree.put(2, "two parrots", true)
        tree.put(2, "two rabbits", true)
        tree.put(3, "three", true)

//                      1
//                    /  \
//                 null   2
//                      /  \
//                   null   3

        assertEquals("one", tree.getTreeRoot()?.value)
        assertEquals("two rabbits", tree.getTreeRoot()?.rightSon?.value)
        assertEquals("three", tree.getTreeRoot()?.rightSon?.rightSon?.value)
        assertEquals(3, tree.size())
    }

    @Test
    fun `put key more than root key, rightSon != null and do not replace existing key`() {
        tree.put(1, "one", false)
        tree.put(2, "two parrots", false)
        tree.put(2, "two rabbits", false)
        tree.put(3, "three", false)

//                      1
//                    /  \
//                 null   2
//                      /  \
//                   null   3

        assertEquals("one", tree.getTreeRoot()?.value)
        assertEquals("two parrots", tree.getTreeRoot()?.rightSon?.value)
        assertEquals("three", tree.getTreeRoot()?.rightSon?.rightSon?.value)
        assertEquals(3, tree.size())
    }

    // remove
    @Test
    fun `remove vertex with null key from empty tree`() {
        val tree: SimpleBSTForTest<Int?, String> = SimpleBSTForTest()
        val returned = tree.remove(null)
        assertEquals(null, tree.getTreeRoot())
        assertEquals(null, returned)
        assertEquals(0, tree.size())
    }

    @Test
    fun `remove root vertex without sons`() {
        tree.put(5, "five")
        val returned = tree.remove(5)
        assertEquals(null, tree.getTreeRoot())
        assertEquals("five", returned)
        assertEquals(0, tree.size())
    }

    @Test
    fun `remove root vertex with one left son`() {
        tree.putAll(mapOf(Pair(5, "five"), Pair(0, "zero")))

//                  5
//                /  \
//              0    null

        val returned = tree.remove(5)
        assertEquals("zero", tree.getTreeRoot()?.value)
        assertEquals("five", returned)
        assertEquals(1, tree.size())
    }

    @Test
    fun `remove root vertex with one right son`() {
        tree.putAll(mapOf(Pair(5, "five"), Pair(6, "six")))

//                  5
//                /  \
//             null   6

        val returned = tree.remove(5)
        assertEquals("six", tree.getTreeRoot()?.value)
        assertEquals("five", returned)
        assertEquals(1, tree.size())
    }

    @Test
    fun `remove vertex with smaller key, vertex has not sons`() {
        tree.putAll(mapOf(Pair(5, "five"), Pair(0, "zero")))

//                   5
//                 /  \
//               0     null

        val returned = tree.remove(0)
        assertEquals("five", tree.getTreeRoot()?.value)
        assertEquals("zero", returned)
        assertEquals(1, tree.size())
    }

    @Test
    fun `remove vertex with smaller key, vertex has one right son`() {
        tree.putAll(
            mapOf(
                Pair(5, "five"),
                Pair(0, "zero"),
                Pair(4, "four"),
            ),
        )

//                        5
//                      /  \
//                    0    null
//                  /  \
//              null    4

        val returned = tree.remove(0)
        assertEquals("five", tree.getTreeRoot()?.value)
        assertEquals("four", tree.getTreeRoot()?.leftSon?.value)
        assertEquals("zero", returned)
        assertEquals(2, tree.size())
    }

    @Test
    fun `remove vertex with smaller key, vertex has one left son`() {
        tree.putAll(
            mapOf(
                Pair(5, "five"),
                Pair(0, "zero"),
                Pair(-1, "minus_one"),
            ),
        )

//                          5
//                        /  \
//                      0    null
//                    /  \
//                 -1     null

        val returned = tree.remove(0)
        assertEquals("five", tree.getTreeRoot()?.value)
        assertEquals("minus_one", tree.getTreeRoot()?.leftSon?.value)
        assertEquals("zero", returned)
        assertEquals(2, tree.size())
    }

    @Test
    fun `remove vertex with smaller key, vertex has two sons`() {
        tree.putAll(
            mapOf(
                Pair(5, "five"),
                Pair(0, "zero"),
                Pair(-1, "minus_one"),
                Pair(4, "four"),
                Pair(3, "three"),
            ),
        )

//                          5
//                        /  \
//                      0     null
//                    /  \
//                  -1    4
//                       /  \
//                      3    null

        val returned = tree.remove(0)
        assertEquals("five", tree.getTreeRoot()?.value)
        assertEquals("three", tree.getTreeRoot()?.leftSon?.value)
        assertEquals("zero", returned)
        assertEquals(4, tree.size())
    }

    @Test
    fun `remove vertex with more key, vertex has not sons`() {
        tree.putAll(mapOf(Pair(1, "one"), Pair(5, "five")))

//                      1
//                    /  \
//                null    5

        val returned = tree.remove(5)
        assertEquals("one", tree.getTreeRoot()?.value)
        assertEquals("five", returned)
        assertEquals(1, tree.size())
    }

    @Test
    fun `remove vertex with more key, vertex has one left son`() {
        tree.putAll(mapOf(Pair(1, "one"), Pair(5, "five"), Pair(2, "two")))

//                      1
//                    /  \
//                 null   5
//                      /  \
//                     2   null

        val returned = tree.remove(5)
        assertEquals("one", tree.getTreeRoot()?.value)
        assertEquals("two", tree.getTreeRoot()?.rightSon?.value)
        assertEquals("five", returned)
        assertEquals(2L, tree.size())
    }

    @Test
    fun `remove vertex with more key, vertex has one right son`() {
        tree.putAll(mapOf(Pair(1, "one"), Pair(5, "five"), Pair(10, "ten")))

//                      1
//                    /  \
//                 null   5
//                      /  \
//                   null   10

        val returned = tree.remove(5)
        assertEquals("one", tree.getTreeRoot()?.value)
        assertEquals("ten", tree.getTreeRoot()?.rightSon?.value)
        assertEquals("five", returned)
        assertEquals(2L, tree.size())
    }

    @Test
    fun `remove vertex with more key, vertex has two sons`() {
        tree.putAll(
            mapOf(
                Pair(1, "one"),
                Pair(5, "five"),
                Pair(10, "ten"),
                Pair(2, "two"),
                Pair(6, "six"),
            ),
        )

//                      1
//                    /  \
//                null    5
//                      /  \
//                     2    10
//                        /  \
//                       6   null

        val returned = tree.remove(5)
        assertEquals("one", tree.getTreeRoot()?.value)
        assertEquals("six", tree.getTreeRoot()?.rightSon?.value)
        assertEquals("five", returned)
        assertEquals(4L, tree.size())
    }

    @Test
    fun `remove vertex with non-existing key`() {
        tree.put(1, "cat")
        tree.remove(6)

        assertEquals(1L, tree.size())
    }
}

package trees.abstractTree

import main.vertexes.SimpleBSTVertex
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.Comparator

class AbstractTreeTest {
    private fun makeEmptyTree(): TestTree<IntArray, String> {
        return TestTree<IntArray, String>()
    }

    @Test
    fun `isEmpty() returns true if tree is empty `() {
        val tree = makeEmptyTree()
        assert(tree.isEmpty())
    }

    @Test
    fun `isNotEmpty() returns false if tree is empty `() {
        val tree = makeEmptyTree()
        assertFalse(tree.isNotEmpty())
    }
    @Test
    fun `size() returns 0 if tree is empty`() {
        val tree = makeEmptyTree()
        assert(tree.size() == 0L)
    }

    @Test
    fun `get() returns null if tree is empty`() {
        val tree = makeEmptyTree()
        assertNull(tree.get(intArrayOf(0)))
    }

    @Test
    fun `getMax() returns null if tree is empty`() {
        val tree = makeEmptyTree()
        assertNull(tree.getMax())
    }

    @Test
    fun `getMin() returns null if tree is empty`() {
        val tree = makeEmptyTree()
        assertNull(tree.getMin())
    }

    @Test
    fun `getMaxKeyPair() returns null if tree is empty`() {
        val tree = makeEmptyTree()
        assertNull(tree.getMaxKeyPair())
    }

    @Test
    fun `getMinKeyPair() returns null if tree is empty`() {
        val tree = makeEmptyTree()
        assertNull(tree.getMinKeyPair())
    }

    private fun makeTreeWithBothRootSSons(): TestTree<Char, String> {
        val leftSon = SimpleBSTVertex('1', "!", SimpleBSTVertex('0', ")"), null)
        val rightSon = SimpleBSTVertex('4', "$", SimpleBSTVertex('3', "#"), null)
        return TestTree(SimpleBSTVertex('2', "@", leftSon, rightSon), 5L)
    }

    @Test
    fun `isEmpty() returns false if tree is not empty `() {
        val tree = makeTreeWithBothRootSSons()
        assertFalse(tree.isEmpty())
    }

    @Test
    fun `isNotEmpty() returns true if tree is not empty `() {
        val tree = makeTreeWithBothRootSSons()
        assert(tree.isNotEmpty())
    }

    @Test
    fun `size() returns not 0 if tree is not empty`() {
        val tree = makeTreeWithBothRootSSons()
        assert(tree.size() != 0L)
    }

    @Test
    fun `get() returns null when tree doesn't contains given key`() {
        val tree = makeTreeWithBothRootSSons()
        assertNull(tree.get('z'))
    }

    @Test
    fun `getPair() returns null when tree doesn't contains given key`() {
        val tree = makeTreeWithBothRootSSons()
        assertNull(tree.getPair('z'))
    }

    @Test
    fun `get() returns correct value when tree contains given key in left subtree`() {
        val tree = makeTreeWithBothRootSSons()
        assert(tree.get('1') == "!")
    }

    @Test
    fun `getPair() returns correct value when tree contains given key in left subtree`() {
        val tree = makeTreeWithBothRootSSons()
        assert(tree.getPair('1') == ('1' to "!"))
    }

    @Test
    fun `get() returns correct value when tree contains given key in right subtree`() {
        val tree = makeTreeWithBothRootSSons()
        assert(tree.get('4') == "$")
    }

    @Test
    fun `get() returns correct value when root's key was given`() {
        val tree = makeTreeWithBothRootSSons()
        assert(tree.get('2') == "@")
    }

    @Test
    fun `get() returns correct value when leaf's key was given`() {
        val tree = makeTreeWithBothRootSSons()
        assert(tree.get('3') == "#")
    }

    @Test
    fun `getMin() returns correct value when root has two sons`() {
        val tree = makeTreeWithBothRootSSons()
        assert(tree.getMin() == ")")
    }

    @Test
    fun `getMax() returns correct value when root has two sons`() {
        val tree = makeTreeWithBothRootSSons()
        assert(tree.getMax() == "$")
    }

    @Test
    fun `getMinKeyPair() returns correct value when root has two sons`() {
        val tree = makeTreeWithBothRootSSons()
        assert(tree.getMinKeyPair() == ('0' to ")"))
    }

    @Test
    fun `getMaxKeyPair() returns correct value when root has two sons`() {
        val tree = makeTreeWithBothRootSSons()
        assert(tree.getMaxKeyPair() == ('4' to "$"))
    }

    private fun makeTreeWithOnlyLeftRootSSon(): TestTree<Char, String> {
        val leftSon = SimpleBSTVertex('1', "!", SimpleBSTVertex('0', ")"), SimpleBSTVertex('2', "@"))
        return TestTree(SimpleBSTVertex('3', "#", leftSon, null), 4L)
    }

    @Test
    fun `getMax() returns correct value when root has only left son`() {
        val tree = makeTreeWithOnlyLeftRootSSon()
        assert(tree.getMax() == "#")
    }

    private fun makeTreeWithOnlyRightRootSSon(): TestTree<Char, String> {
        val rightSon = SimpleBSTVertex('6', "^", SimpleBSTVertex('5', "%"), SimpleBSTVertex('8', "*"))
        return TestTree(SimpleBSTVertex('3', "#", null, rightSon), 4L)
    }

    @Test
    fun `getMin() returns correct value when root has only right son`() {
        val tree = makeTreeWithOnlyRightRootSSon()
        assert(tree.getMin() == "#")
    }

    @Test
    fun `removeAndReturnPair() returns null when remove() returned null`() {
        val tree = TestTree<Int, Int>(removeShouldReturns = null)
        assertNull(tree.removeAndReturnPair(1))
    }

    @Test
    fun `removeAndReturnPair() returns (given key) to (value that remove() returned) pair`() {
        val tree = TestTree<Int, Char>(removeShouldReturns = '1')
        assert(tree.removeAndReturnPair(3) == (3 to '1'))
    }

    @Test
    fun `putAll() do correct put() call for each map element (1)`() {
        val tree = TestTree<Char, Char>()
        val map = hashMapOf('a' to 'A', 'b' to 'B', 'c' to 'C', 'a' to 'A')
        tree.putAll(map)
        for (pair in map)
            assertNotNull(tree.putWasCalledWithParams.remove(Triple(pair.component1(), pair.component2(), true)))
    }

    @Test
    fun `putAll() do correct put() call for each map element (2)`() {
        val tree = TestTree<Char, Char>()
        val map = hashMapOf('a' to 'A', 'b' to 'B', 'c' to 'C', 'a' to 'A')
        tree.putAll(map, false)
        for (pair in map)
            assertNotNull(tree.putWasCalledWithParams.remove(Triple(pair.component1(), pair.component2(), false)))
    }

    @Test
    fun `compareKeys return 1 when key1 larger than key2 (keys are comparable)`() {
        val tree = TestTree<Int, Int>()
        assert(tree.compareKeysT(18, 14) == 1)
    }

    @Test
    fun `compareKeys return 0 when key1 equals key2 (keys are comparable)`() {
        val tree = TestTree<Int, Int>()
        assert(tree.compareKeysT(18, 18) == 0)
    }

    @Test
    fun `compareKeys return -1 when key1 lesser than key2 (keys are comparable)`() {
        val tree = TestTree<Int, Int>()
        assert(tree.compareKeysT(14, 18) == -1)
    }

    class CMP : Comparator<IntArray> {
        override fun compare(
                o1: IntArray,
                o2: IntArray,
        ): Int {
            return o1.sum() - o2.sum()
        }
    }

    @Test
    fun `compareKeys return 1 when key1 larger than key2 (comparator was given)`() {
        val tree = TestTree<IntArray, Int>(CMP())
        assert(tree.compareKeysT(intArrayOf(-18, 18), intArrayOf(-1)) == 1)
    }

    @Test
    fun `compareKeys return 0 when key1 equals key2 (comparator was given)`() {
        val tree = TestTree<IntArray, Int>(CMP())
        assert(tree.compareKeysT(intArrayOf(-18, 18), intArrayOf(0)) == 0)
    }

    @Test
    fun `compareKeys return -1 when key1 lesser than key2 (comparator was given)`() {
        val tree = TestTree<IntArray, Int>(CMP())
        assert(tree.compareKeysT(intArrayOf(-18, 18), intArrayOf(1)) == -1)
    }

    @Test
    fun `compareKeys fall when keys type doesn't implement comparable && comparator wasn't given`() {
        var didItFall = false
        val tree = TestTree<IntArray, Int>()
        try {
            tree.compareKeysT(intArrayOf(-18, 18), intArrayOf(1))
        } catch (e: Exception) {
            didItFall = true
        }
        assert(didItFall)
    }
}

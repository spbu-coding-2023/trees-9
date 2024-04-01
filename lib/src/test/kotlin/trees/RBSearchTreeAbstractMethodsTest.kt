package trees

import main.trees.RBSearchTree
import main.vertexes.RBVertex
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RBSearchTreeAbstractMethodsTest {
    private lateinit var rbTree: RBSearchTree<Int, Int>

    @BeforeEach
    fun setup() {
        rbTree = RBSearchTree()
    }

    @Test
    fun `test size after put`(){
        val segment = 1..10
        for (i in 0 until segment.random()) rbTree.put(segment.random(), segment.random())
        val previousSize = rbTree.size()
        val expectedResult = previousSize + 1
        rbTree.put(11, segment.random())
        assertEquals(expectedResult, rbTree.size())
    }

    @Test
    fun `test size after remove`(){
        val segment = 1..10
        val listOfKeys: MutableList<Int> = mutableListOf()
        for (i in 0 until segment.random()){
            val key = segment.random()
            listOfKeys.add(key)
            rbTree.put(key, segment.random())
        }
        val previousSize = rbTree.size()
        val expectedResult = previousSize - 1
        rbTree.remove(listOfKeys.random())
        assertEquals(expectedResult, rbTree.size())
    }

    @Test
    fun `size can't be negative`(){
        val segment = 1..10
        val listOfKeys: MutableList<Int> = mutableListOf()
        for (i in 0 until segment.random()){
            val key = segment.random()
            listOfKeys.add(key)
            rbTree.put(key, segment.random())
        }
        listOfKeys.forEach { rbTree.remove(it) }
        rbTree.remove(segment.random())
        assertEquals(0, rbTree.size())
    }

    @Test
    fun `check for empty`(){
        val segment = 1..10
        val listOfKeys: MutableList<Int> = mutableListOf()
        for (i in 0 until segment.random()){
            val key = segment.random()
            listOfKeys.add(key)
            rbTree.put(key, segment.random())
        }
        listOfKeys.forEach { rbTree.remove(it) }
        assertEquals(true, rbTree.isEmpty())
    }

    @Test
    fun `get`(){
        val segment = 1..10
        for (i in 0 until segment.random()) rbTree.put(segment.random(), segment.random())
        rbTree.put(7, 44536643)
        assertEquals(44536643, rbTree.get(7))
    }

    @Test
    fun `get but such vertex doesn't exist`(){
        val segment = 1..10
        for (i in 0 until segment.random()) rbTree.put(segment.random(), segment.random())
        assertEquals(null, rbTree.get(11))
    }

    @Test
    fun getPair(){
        val segment = 1..10
        for (i in 0 until segment.random()) rbTree.put(segment.random(), segment.random())
        rbTree.put(7, 44536643)
        assertEquals(Pair(7, 44536643), rbTree.getPair(7))
    }

    @Test
    fun `getPair but such vertex doesn't exist`(){
        val segment = 1..10
        for (i in 0 until segment.random()) rbTree.put(segment.random(), segment.random())
        assertEquals(null, rbTree.getPair(11))
    }

    @Test
    fun getMin(){
        val segment = 1..10
        for (i in 0 until segment.random()) rbTree.put(segment.random(), segment.random())
        rbTree.put(1, 44536643)
        assertEquals(44536643, rbTree.getMin())
    }

    @Test
    fun `getMin but tree is empty`(){
        assertEquals(null, rbTree.getMin())
    }

    @Test
    fun getMax(){
        val segment = 1..10
        for (i in 0 until segment.random()) rbTree.put(segment.random(), segment.random())
        rbTree.put(10, 44536643)
        assertEquals(44536643, rbTree.getMax())
    }

    @Test
    fun `getMax but tree is empty`(){
        assertEquals(null, rbTree.getMax())
    }

    @Test
    fun getMinKeyPair(){
        val segment = 1..10
        for (i in 0 until segment.random()) rbTree.put(segment.random(), segment.random())
        rbTree.put(1, 44536643)
        assertEquals(Pair(1, 44536643), rbTree.getMinKeyPair())
    }

    @Test
    fun `getMinKeyPair but tree is empty`(){
        assertEquals(null, rbTree.getMinKeyPair())
    }

    @Test
    fun getMaxKeyPair(){
        val segment = 1..10
        for (i in 0 until segment.random()) rbTree.put(segment.random(), segment.random())
        rbTree.put(10, 44536643)
        assertEquals(Pair(10, 44536643), rbTree.getMaxKeyPair())
    }

    @Test
    fun `getMaxKeyPair but tree is empty`(){
        assertEquals(null, rbTree.getMaxKeyPair())
    }

//    @Test
//    fun putAll(){
//        TODO()
//    }

    @Test
    fun removeAndReturnPair(){
        val segment = 1..10
        for (i in 0 until segment.random()) rbTree.put(segment.random(), segment.random())
        rbTree.put(7, 44536643)
        assertEquals(Pair(7, 44536643), rbTree.removeAndReturnPair(7))
        assertEquals(null, rbTree.get(7))
    }

    @Test
    fun `removeAndReturnPair but vertex doesn't exist`(){
        val segment = 1..10
        for (i in 0 until segment.random()) rbTree.put(segment.random(), segment.random())
        assertEquals(null, rbTree.removeAndReturnPair(11))
    }
}
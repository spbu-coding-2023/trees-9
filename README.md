<p><img alt="badge" src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white"/>
<img alt="badge" src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"/>
<img alt="badge" src="https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white"/></p>

# BinTreeKit


## Table of Contents
- [About Project](#about-project)
- [Usage](#usage)
- [Developers and Contacts](#developers-and-contacts)
- [License](#license)

[//]: # (- [Project Structure]&#40;#project-structure&#41;)


## About Project
`BinTreeKit` is a library that provides implementations for three types of trees: `SimpleBinarySearchTree`, `AVLSearchTree` and `RBSearchTree`. The library is designed to simplify the process of managing hierarchical data, allowing Kotlin developers to focus on building robust and scalable applications.


## Usage
### 1. **Importing Classes**:
```kotlin
import main.trees.SimpleBinarySearchTree
import main.trees.AVLSearchTree
import main.trees.RBSearchTree
```
### 2. **Instantiate Trees**:
```kotlin
// create a Simple Binary Search Tree
val bstTree = SimpleBinarySearchTree<Int, String>(mapOf(Pair(1, "cat"), Pair(2, "dog")))

// create an AVL Search Tree
val avlTree = AVLSearchTree<Int, String>(mapOf(Pair(1, "cat"), Pair(2, "dog")))

// create a Red-Black Search Tree
val rbTree = RBSearchTree<Int, String>(mapOf(Pair(1, "cat"), Pair(2, "dog")))
```
### 3. **Use Tree Methods**:
```kotlin
// put key-value pairs with different values of replaceIfExists perematers
bstTree.putAll(mapOf(Pair(2, "hamster"), Pair(3, "bird")), true)
rbTree.put(4, "horse", false)

// remove key-value pair from tree and return value 
println(rbTree.remove(4)) // output: horse
bstTree.remove(1)

// pairwise iteration
for (pair in avlTree) {
    print(pair)
} // output: (1, "cat")(2, "dog")
```


[//]: # (## Project Structure)


## Developers and Contacts
- [vicitori](https://github.com/vicitori) - Victoria Lutsyuk (Simple Binary Search Tree)
- [Szczucki](https://github.com/Szczucki) - Nikita Shchutskii (AVL Search Tree)
- [TerrMen](https://github.com/TerrMen) - Kostya Oreshin (RB Search Tree)

Project Link: [trees-9](https://github.com/spbu-coding-2023/trees-9.git)


## License
This project uses the **APACHE LICENSE, VERSION 2.0**. See the [LICENSE](LICENSE.md) for more info.


[//]: # (## How to Use )

[//]: # (___)

[//]: # (To use the Tree Library in your Kotlin project, follow these steps:)

[//]: # (1. **Add Dependency**: Add the Tree Library as a dependency in your `build.gradle.kts` file:)

[//]: # (   ```kotlin)

[//]: # (    dependencies {)

[//]: # (       implementation&#40;"com.example:tree-library:1.0.0"&#41;)

[//]: # (    })

[//]: # (2. **Import Classes**: Import the necessary classes from the library into your Kotlin files:)

[//]: # (   ```kotlin)

[//]: # (    import main.trees.*)

[//]: # (    import main.vertexes.InterfaceBSTVertex)

[//]: # (    import main.iterator.TreeIterator)

[//]: # (3. **Instantiate Trees**: Create instances of the tree structures provided by the library, such as Simple Binary Search Tree, AVL Tree, or Red-Black Tree.)

[//]: # (   ```kotlin)

[//]: # (   // Create a Simple Binary Search Tree)

[//]: # (   val bst = SimpleBinarySearchTree<Int, String>&#40;&#41;)

[//]: # (4. **Use Tree Methods**: Utilize the methods provided by the tree classes to insert, remove, search, and traverse tree elements according to your application requirements.)

[//]: # (   ```kotlin)

[//]: # (   // Insert key-value pairs)

[//]: # (   bst.put&#40;5, "Five"&#41;)

[//]: # (   bst.put&#40;3, "Three"&#41;)

[//]: # (   bst.put&#40;7, "Seven"&#41;)

[//]: # (   )
[//]: # (   // Retrieve values)

[//]: # (   println&#40;bst.get&#40;3&#41;&#41; // Output: Three)

[//]: # (   )
[//]: # (   // Remove a key-value pair)

[//]: # (   bst.remove&#40;3&#41;)

[//]: # (   )
[//]: # (   // Iterate over tree elements)

[//]: # (   for &#40;&#40;key, value&#41; in bst&#41; {)

[//]: # (   println&#40;"Key: $key, Value: $value"&#41;)

[//]: # (   })
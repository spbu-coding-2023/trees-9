<div align="center"><p><img alt="badge" src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white"/>
<img alt="badge" src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"/>
<img alt="badge" src="https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white"/></p> </div>


<h1 align="center"> BinTreeKit </h1>

## About Project
`BinTreeKit` is a library that provides implementations for three types of trees: `SimpleBinarySearchTree`, `AVLSearchTree` and `RBSearchTree`. The library is designed to simplify the process of managing hierarchical data, allowing Kotlin developers to focus on building robust and scalable applications.


#### Table of Contents
- [About Project](#about-project)
- [Usage](#usage)
- [Library Features](#library-features)
  - [Constructors](#constructors)
  - [Methods](#methods)
  - [Tree Properties](#tree-properties)
  - [Iterator](#iterator)
    - [Constructors](#constructors-1)
    - [Methods](#methods-1)
- [Developers](#developers)
- [Contributing](#contributing)
- [License](#license)


## Usage
1. **Importing Classes:**
```kotlin
import main.trees.SimpleBinarySearchTree
import main.trees.AVLSearchTree
import main.trees.rbTree
```

2. **Instantiate Trees:**
```kotlin
val map = mapOf(Pair(1, "cat"), Pair(2, "dog"))

// create a Simple Binary Search Tree
val emptyBstTree = SimpleBinarySearchTree()
val bstTree = SimpleBinarySearchTree(map)

// create an AVL Search Tree
val emptyAvlTree = AVLSearchTree()
val avlTree = AVLSearchTree(map)

// create a Red-Black Search Tree
val emptyRbTree = RBSearchTree()
val rbTree = RBSearchTree(map)
```


3. **Use Tree Methods:**
```kotlin
// put key-value pairs with different values of replaceIfExists perematers
bstTree.putAll(map, true)
rbTree.put(4, "horse", false)

// remove key-value pair from tree and return value 
println(rbTree.remove(4)) // output: horse
bstTree.remove(1)

//get key-value pair from tree
println(avlTree.getMin()) //output: cat

// pairwise iteration
for (pair in avlTree) {
    print("${pair.second}, ")
} // output: cat, dog
```


## Library Features


### Constructors
- `constructor(comparator: Comparator<K>? = null)` - constructs a new AbstractBinarySearchTree instance with the given comparator.


- `constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null)` - constructs a new AbstractBinarySearchTree instance initialized with the contents of the given map.

    > The `comparator` to be used optional for ordering keys in the tree.


### Methods


- `put(key: K, value: V, replaceIfExists : Boolean = true)` - inserts the specified key-value pair into the tree.

- `putAll(map: Map<K, V>, replaceIfExists: Boolean = true)` - inserts all key-value pairs from the given map into the tree.

  > `replaceIfExists` is an optional (default is true) Boolean flag indicating whether to replace the value if the key already exists in the tree.


- `remove(key: K): V?` - removes the key-value pair with the specified key from the tree and returns value associated with the removed key, or `null` if the key was not found in the tree.


- `get(key: K): V?` - retrieves the value associated with the specified key from the tree and returns value associated with the specified key, or `null` if the key was not found in the tree.

- `getPair(key: K): Pair<K, V>?` - retrieves the key-value pair associated with the specified key from the tree and returns the key-value pair associated with the specified key, or `null` if the key was not found in the tree.

- `getMin(): V?` - retrieves the value associated with the minimum key in the tree and returns the value associated with the minimum key, or `null` if the tree is empty.

- `getMax(): V?` - retrieves the value associated with the maximum key in the tree and returns the value associated with the maximum key, or `null` if the tree is empty.

- `getMinKeyPair(): Pair<K, V>?` - retrieves the key-value pair associated with the minimum key in the tree and returns the key-value pair associated with the minimum key, or `null` if the tree is empty.

- `getMaxKeyPair(): Pair<K, V>?` - retrieves the key-value pair associated with the maximum key in the tree and returns the key-value pair associated with the maximum key, or `null` if the tree is empty.


- `size(): Long` - returns the number of key-value pairs in the tree.

- `isEmpty(): Boolean` checks whether the tree is empty and returns `true` if the tree is empty, `false` otherwise.


### Tree Properties

- `size: Long` - the number of key-value pairs currently stored in the tree.


- `isEmpty: Boolean` - indicates whether the tree is empty.


### Iterator
###### Constructors
- `constructor(vertex: N?)` - constructs a new TreeIterator instance with the specified starting vertex.

  > `vertex` is the starting vertex of the iterator.

###### Methods

- `hasNext(): Boolean` - checks if there are more elements in the iteration and returns `true` if there are more elements, `false` otherwise.


- `next(): Pair<K, V>` - retrieves the next key-value pair in the iteration and returns the next key-value pair in the iteration.


## Developers
- [vicitori](https://github.com/vicitori) - Victoria Lutsyuk (Simple Binary Search Tree)
- [Szczucki](https://github.com/Szczucki) - Nikita Shchutskii (AVL Search Tree)
- [TerrMen](https://github.com/TerrMen) - Kostya Oreshin (RB Search Tree)


## Contributing

**Quick start**

1. Create new branch branching off `develop`, name it as feature you want to implement 
```bash
git checkout develop
git switch -c my_feature
```

2. Commit the changes and write messages according to [commits convention](https://www.conventionalcommits.org/en/v1.0.0/)
```bash
git commit -m "feat: implement new feature"
```

3. Push changes to a remote repository
```bash
git push origin my_feature
```

4. Open pull request to `develop` 


## License
This project uses the **APACHE LICENSE, VERSION 2.0**. See the [LICENSE](LICENSE.md) for more info.

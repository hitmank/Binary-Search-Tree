# Binary-Search-Tree
Java implementation of a Binary Search Tree. All basic operations available.
-------------------------------------------------------------------------------------------------------------------------------------------------
# Contains 2 Classes.
1. BST
		- Contains a rootNode property
		- Method to addNode, Search for a Node, Traverse in Inorder, preorder and postorder and Remove a node.
2. Node 
		- Just a container for each individual node in the binary search tree. 
		- Has 3 properties : Key, LeftChild and rightChild.

-------------------------------------------------------------------------------------------------------------------------------------------------
# 
Adding a node to a BST :

1. Check if tree is empty :
2. If yes, set the node as root and return.
3. If no, Compare the node.key to the currentNode and move to the left subtree if node.key < currentNode.key, else move to right subtree.
	 -> Continue this recursively, till you reach a leaf node. Insert the node here.
	 
-------------------------------------------------------------------------------------------------------------------------------------------------
# 
Searching for a Node in a BST:

1. Initial null check.
2. Compare the node.key to every node.key (starting from root). If node.key < currentNode.key move to the left subtree, else move to right subtree.
3. Repeat till you either find the node (Return true), or reach a null pointer(return false).

-------------------------------------------------------------------------------------------------------------------------------------------------
# 
Removing a Node from the BST:

1. Locate the node to be removed. (Use search)
2. We have to handle 3 cases.
    1. If the node is a leaf. -> Then simple, depending on which child it is, make the parents child node property = null
    2. If the node has one child. -> simple again. depending on which child it is, make the parents child node property point to the nodes one child.
    3. If the node has 2 children.
        a) For the given node, find a replacement from its right subtree. This would be the smallest element in the right subtree.(i.e traverse along the left childs of the right-subtree)
        b) Call this as the 'candidate node'. (One property that this candidate node has is that, it does not have a left child. It may only have a right child. or no children at all.)
        	 Now, depending on whether the  candidate node was a left or right child of its parent, Make the candidate node's parent child property point to the candidate nodes right child (null if it doesnt have one)
        	 Then, make the candidate's rightChild = the right child of the node you are going to replace.
        c) Now, effectively you have removed the node and replaced it with the candidate. however, the candidate is only linked to the right subtree of the replaced node.
        	 So, now make the left child of the candidate = the left child of the replaced node.
        d) finally, make the replaced node's parent point to the candidate. (Skip if replaced node is root).
				
-------------------------------------------------------------------------------------------------------------------------------------------------
# 
Traversals - Usual Inorder,preorder and postorder.
		




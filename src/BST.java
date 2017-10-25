import javax.swing.*;

class Node{
    int key;
    Node leftChild;
    Node rightChild;
    Node(int key){
        this.key = key;
    }
}
public class BST{

    Node rootNode;

    public void addNode(Node node){
        //If tree is empty, then insert at root
        if (this.rootNode == null){
            this.rootNode = node;
        }
        else{
            //The current node we are at.
            Node current_node = rootNode;
            //The parent of the current node.
            Node parent_node = rootNode;

            //Repeat until the correct slot for the node is found.
            while(true){
                parent_node = current_node;
                //If the node.key is smaller than currentNode.key, the node will be be in the left subtree of the current node.
                if (node.key < current_node.key){
                    current_node = current_node.leftChild;
                    //If we have have reached a leaf. Then we insert the node here.
                    if (current_node == null){
                        parent_node.leftChild = node;
                        return;
                    }
                }
                //If the node.key is larger or equal to the currentNode.key, the node will be be in the right subtree of the current node.
                else{
                    current_node = current_node.rightChild;
                    //If we have have reached a leaf. Then we insert the node here.
                    if (current_node == null){
                        parent_node.rightChild = node;
                        return;
                    }
                }
            }

        }
    }
    public void traverseInOrder(Node currentNode){

        if (currentNode == null){
            return;
        }
        else{
            traverseInOrder(currentNode.leftChild);
            System.out.println(currentNode.key);
            traverseInOrder(currentNode.rightChild);
        }

    }
    public void traversePreOrder(Node currentNode){
        if (currentNode == null){
            return;
        }
        else{
            System.out.println(currentNode.key);
            traversePreOrder(currentNode.leftChild);
            traversePreOrder(currentNode.rightChild);
        }
    }
    public void traversePostOrder(Node currentNode){
        if (currentNode == null){
            return;
        }
        else{
            traversePostOrder(currentNode.leftChild);
            traversePostOrder(currentNode.rightChild);
            System.out.println(currentNode.key);

        }
    }
    public boolean findNodeWithKey(int nodeKey){
        //If tree is empty, return false.
        if (this.rootNode == null){
            return false;
        }
        Node current_node = rootNode;
        while(current_node.key != nodeKey){
            //traverse to left subtree
            if (nodeKey < current_node.key){
                current_node = current_node.leftChild;
            }
            //traverse to right subtree
            else {
                current_node = current_node.rightChild;
            }
            //If reached a dead end. Node is not present.
            if (current_node == null){
                return false;
            }
        }
        //Would only reach here if node.key = current_node.key, which means the node is found.
        return true;

    }
    public boolean removeNodeWithKey(int nodeKey){
        //Again, check if tree is empty.
        if (this.rootNode == null){
            return false;
        }
        //Now, we first have to find the node to remove.
        //Once found, we have to remove it, but maintain the order in the tree by attaching its children to the nodes parent. etc.
        Node current_node = this.rootNode;
        Node parent = this.rootNode;
        boolean isLeftChild = true;
        while(current_node.key != nodeKey){
            parent = current_node;
            if (nodeKey < current_node.key){
                current_node = current_node.leftChild;
                isLeftChild = true;
            }
            else{
                current_node = current_node.rightChild;
                isLeftChild = false;
            }
            if (current_node == null){
                return false;
            }
        }
        //Now, if we reach here. the node is present in the tree.
        //We have to handle 3 cases.
        //1. If the node is a leaf. -> Then simple, depending on which child it is, make the parents child node property = null
        //2. If the node has one child. -> simple again. depending on which child it is, make the parents child node property point to the nodes one child.
        //3. If the node has 2 children.
        // -> This is more complicated.
        // a) For the given node, find a replacement from its right subtree. This would be the smallest element in the right subtree.(i.e traverse along the left childs of the subtree)
        // b) Call this as the 'candidate node'. One property that this candidate node has is that, it does not have a left child. It may only have a right child. or no children at all.
        // Now, depending on whether the  candidate node was a left or right child of its parent, Make the candidate node's parent child property point to the candidate nodes right child (null if it doesnt have one)
        // Then, make the candidate's rightChild = the right child of the node you are going to replace.
        // c) Now, effectively you have removed the node and replaced it with the candidate. however, the candidate is only linked to the right subtree of the replaced node.
        // So, now make the left child of the candidate = the left child of the replaced node.
        // d) finally, make the replaced node's parent point to the candidate. (Skip if replaced node is root).
        if (current_node.leftChild == null && current_node.rightChild == null){
            if (current_node == rootNode){
                rootNode = null;
            }
            else if (isLeftChild){
                parent.leftChild = null;
            }
            else {
                parent.rightChild = null;
            }
        }
        else if (current_node.leftChild == null){
            if (current_node == rootNode){
                rootNode = current_node.rightChild;
            }
            else{
                if (isLeftChild){
                    parent.leftChild = current_node.rightChild;
                }
                else{
                    parent.rightChild = current_node.rightChild;
                }
            }
        }
        else if (current_node.rightChild == null){
            if (current_node == rootNode){
                rootNode = current_node.leftChild;
            }
            else{
                if (isLeftChild){
                    parent.leftChild = current_node.leftChild;
                }
                else{
                    parent.rightChild = current_node.leftChild;
                }
            }
        }
        else{

            Node candidate = getReplacementCandidate(current_node);


            candidate.leftChild = current_node.leftChild;

            if (current_node == rootNode){
                rootNode = candidate;
            }
            else{
                if (isLeftChild){
                    parent.leftChild = candidate;
                }
                else{
                    parent.rightChild = candidate;
                }
            }
        }
        return true;
    }
    public Node getReplacementCandidate(Node nodeToBeRemoved){
        Node curr_candidate = nodeToBeRemoved.rightChild;
        Node candidate_parent = nodeToBeRemoved;

        while(true){
            if (curr_candidate.leftChild == null){
                break;
            }
            else{
                candidate_parent = curr_candidate;
                curr_candidate = curr_candidate.leftChild;
            }
        }

        if (candidate_parent != nodeToBeRemoved){
            candidate_parent.leftChild = curr_candidate.rightChild;
            curr_candidate.rightChild = nodeToBeRemoved.rightChild;
        }
        return curr_candidate;
    }

    public static void main(String Args[]){
        Node root = new Node(50);
        Node a = new Node(25);
        Node b = new Node(75);
        Node c = new Node(15);
        Node d = new Node(85);
        Node e = new Node(100);
        Node f = new Node(30);
        Node g = new Node(27);
        Node h = new Node(65);
        Node i = new Node(70);

        BST tree = new BST();
        tree.addNode(root);
        tree.addNode(a);
        tree.addNode(b);
        tree.addNode(c);
        tree.addNode(d);
        tree.addNode(e);
        tree.addNode(f);
        tree.addNode(g);
        tree.addNode(h);
        tree.addNode(i);
//
        System.out.println("The In Order Traversal of the tree :");
        tree.traverseInOrder(tree.rootNode);

        System.out.println("The Pre Order Traversal of the tree :");
        tree.traversePreOrder(tree.rootNode);

        System.out.println("The Post Order Traversal of the tree :");
        tree.traversePostOrder(tree.rootNode);

        System.out.println("Searching for 80 in the tree .. :");
        boolean result = tree.findNodeWithKey(80);
        System.out.println("Result : " + result);

        System.out.println("Searching for 15 in the tree .. :");
        result = tree.findNodeWithKey(15);
        System.out.println("Result : " + result);

        System.out.println("Removing node with key : 50");
        boolean res = tree.removeNodeWithKey(50);
        if (res) {
            System.out.println("Resulting tree after removal(Inorder traversal) :");
            tree.traverseInOrder(tree.rootNode);
        }
        else{
            System.out.println("Node could not be found. No change to tree");
        }

        System.out.println("Removing node with key : 30");
         res = tree.removeNodeWithKey(30);
        if (res) {
            System.out.println("Resulting tree after removal(Inorder traversal) :");
            tree.traverseInOrder(tree.rootNode);
        }
        else{
            System.out.println("Node could not be found. No change to tree");
        }

        System.out.println("Removing node with key : 75");
         res = tree.removeNodeWithKey(75);
        if (res) {
            System.out.println("Resulting tree after removal(Inorder traversal) :");
            tree.traverseInOrder(tree.rootNode);
        }
        else{
            System.out.println("Node could not be found. No change to tree");
        }

        System.out.println("Removing node with key : 101");
        res = tree.removeNodeWithKey(101);
        if (res) {
            System.out.println("Resulting tree after removal(Inorder traversal) :");
            tree.traverseInOrder(tree.rootNode);
        }
        else{
            System.out.println("Node could not be found. No change to tree");
        }
    }

}









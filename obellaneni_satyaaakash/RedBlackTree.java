import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

// RedBlackTree class represents a Red-Black Tree data structure for managing books and patrons.
class RedBlackTree {

    // Two maps to store the color of nodes before and after an operation.
    public static Map<Integer, NodeColor> hm1 = new HashMap<>();
    public static Map<Integer, NodeColor> hm2 = new HashMap<>();

    // Constants for an empty node and initializations.
    private final RedBlackNode nil = EmptyRBNode.nil;
    public int flipCount; // Counter to track color flip operations.
    private RedBlackNode root; // Root of the Red-Black Tree.

    // StringBuilder to accumulate results and print them later.
    public static StringBuilder resultString = new StringBuilder();

    // Constructor initializes an empty Red-Black Tree.
    public RedBlackTree() {
        this.root = nil;
        this.flipCount = 0;
    }

    // Inserts a new book into the Red-Black Tree.
    public void insertBook(int bookId, String bookName, String authorName, String isAvailable) {
        RedBlackNode book = new RedBlackNode(bookId, bookName, authorName, isAvailable.equals("Yes"));
        transferMap();
        // Set the color of the node based on whether the tree was empty.
        if (root == nil) {
            hm1.put(bookId, NodeColor.BLACK);
        } else {
            hm1.put(bookId, NodeColor.RED);
        }
        insert(book);
        populateLatestMap();
        colorFlipCount();
    }

    // Counts the number of color flips that occurred during the last operation.
    private void colorFlipCount() {
        for (Map.Entry<Integer, NodeColor> entry : hm1.entrySet()) {
            if (entry.getValue() != hm2.get(entry.getKey())) {
                this.flipCount++;
            }
        }
    }

    // Prints the color flip count to the result string.
    public void getColorFlipCount() {
        resultString.append("Color Flip Count : " + this.flipCount + "\n");
    }

    // Updates hm2 with the color information after an operation.
    private void populateLatestMap() {
        inorderTraversal(root);
    }

    // Performs an inorder traversal to update hm2 with the current color of each node.
    private void inorderTraversal(RedBlackNode root) {
        if (root == nil) {
            return;
        }
        inorderTraversal(root.left);
        hm2.put(root.bookId, root.color);
        inorderTraversal(root.right);
    }

    // Transfers the color information from hm2 to hm1.
    private void transferMap() {
        hm1.clear();
        hm1 = new HashMap<>(hm2);
        hm2.clear();
    }

    // Inserts a new node into the Red-Black Tree and fixes any violations of the Red-Black Tree properties.
    private void insert(RedBlackNode book) {
        RedBlackNode tempRoot = root;
        if (root == nil) {
            root = book;
            book.color = NodeColor.BLACK;
            book.parent = nil;
        } else {
            book.color = NodeColor.RED;
            while (true) {
                if (book.bookId < tempRoot.bookId) {
                    if (tempRoot.left == nil) {
                        tempRoot.left = book;
                        book.parent = tempRoot;
                        break;
                    } else {
                        tempRoot = tempRoot.left;
                    }
                } else if (book.bookId == tempRoot.bookId) {
                    return; // The book with the same ID is already in the tree.
                } else {
                    if (tempRoot.right == nil) {
                        tempRoot.right = book;
                        book.parent = tempRoot;
                        break;
                    } else {
                        tempRoot = tempRoot.right;
                    }
                }
            }
            fixInsertViolation(book);
        }
    }

    // Fixes any violations of the Red-Black Tree properties after an insertion.
    private void fixInsertViolation(RedBlackNode book) {
        while (book.parent.color == NodeColor.RED) {
            RedBlackNode uncle = nil;
            if (book.parent == book.parent.parent.left) {
                // If the parent is a left child, get the uncle from the right.
                uncle = book.parent.parent.right;

                if (uncle != nil && uncle.color == NodeColor.RED) {
                    // Case 1: Recoloring
                    book.parent.color = NodeColor.BLACK;
                    if (book.parent.parent.color != NodeColor.RED && book.parent.parent != root) {
                        book.parent.parent.color = NodeColor.RED;
                    }
                    uncle.color = NodeColor.BLACK;
                    book = book.parent.parent;
                    continue;
                }
                if (book == book.parent.right) {
                    // Case 2: Left rotation
                    book = book.parent;
                    rotateLeft(book);
                }
                // Case 3: Recoloring and right rotation
                book.parent.color = NodeColor.BLACK;
                book.parent.parent.color = NodeColor.RED;
                rotateRight(book.parent.parent);
            } else {
                // Symmetric cases for a right child.
                uncle = book.parent.parent.left;
                if (uncle != nil && uncle.color == NodeColor.RED) {
                    // Case 1: Recoloring
                    book.parent.color = NodeColor.BLACK;
                    book.parent.parent.color = NodeColor.RED;
                    uncle.color = NodeColor.BLACK;
                    book = book.parent.parent;
                    continue;
                }
                if (book == book.parent.left) {
                    // Case 2: Right rotation
                    book = book.parent;
                    rotateRight(book);
                }
                // Case 3: Recoloring and left rotation
                book.parent.color = NodeColor.BLACK;
                book.parent.parent.color = NodeColor.RED;
                rotateLeft(book.parent.parent);
            }
        }
        root.color = NodeColor.BLACK;
    }

    // Performs a left rotation on the given node.
    private void rotateLeft(RedBlackNode book) {
        if (book.parent != nil) {
            if (book == book.parent.left) {
                book.parent.left = book.right;
            } else {
                book.parent.right = book.right;
            }
            book.right.parent = book.parent;
            book.parent = book.right;
            if (book.right.left != nil) {
                book.right.left.parent = book;
            }
            book.right = book.right.left;
            book.parent.left = book;
        } else {
            // Special case: rotating the root.
            RedBlackNode right = root.right;
            root.right = right.left;
            right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = nil;
            root = right;
        }
    }

    // Performs a right rotation on the given node.
    private void rotateRight(RedBlackNode book) {
        if (book.parent != nil) {
            if (book == book.parent.left) {
                book.parent.left = book.left;
            } else {
                book.parent.right = book.left;
            }
            book.left.parent = book.parent;
            book.parent = book.left;
            if (book.left.right != nil) {
                book.left.right.parent = book;
            }
            book.left = book.left.right;
            book.parent.right = book;
        } else {
            // Special case: rotating the root.
            RedBlackNode left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = nil;
            root = left;
        }
    }

    // Searches for a book with the given ID in the Red-Black Tree and returns it.
    public RedBlackNode printBook(int bookId) {
        RedBlackNode temp = root;
        if (root.bookId == -1)
            return null;
        while (true) {
            if (bookId < temp.bookId) {
                if (temp.left == nil) {
                    return null;
                } else {
                    temp = temp.left;
                }
            } else if (bookId == temp.bookId) {
                return temp;
            } else {
                if (temp.right == nil) {
                    return null;
                } else {
                    temp = temp.right;
                }
            }
        }
    }

    // Deletes a book with the given ID from the Red-Black Tree and prints a message.
    public void deleteBook(int bookId) {
        RedBlackNode book = printBook(bookId);
        if (book == null) {
            resultString.append("Book " + bookId + " is no longer available.\n");
            return;
        }
        transferMap();
        hm1.remove(bookId);
        delete(book);
        populateLatestMap();
        colorFlipCount();
        if (book.minHeap.isEmpty()) {
            resultString.append("Book " + bookId + " is no longer available.\n");
        } else {
            resultString.append("Book " + bookId + " is no longer available. Reservations made by Patrons "
                    + book.minHeap + " have been cancelled!\n");
        }
    }

    // Deletes a node from the Red-Black Tree and fixes any violations of the Red-Black Tree properties.
    private boolean delete(RedBlackNode z) {
        RedBlackNode y = z;
        NodeColor y_original_color = y.color;
        RedBlackNode x;
        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = treeMaximum(z.left);
            y_original_color = y.color;
            x = y.left;
            if (y.parent == z)
                x.parent = y;
            else {
                transplant(y, y.left);
                y.left = z.left;
                y.left.parent = y;
            }
            transplant(z, y);
            y.right = z.right;
            y.right.parent = y;
            y.color = z.color;
        }
        if (y_original_color == NodeColor.BLACK) {
            fixDeleteViolation(x);
        }
        return true;
    }

    // Replaces one subtree with another in the Red-Black Tree.
    private void transplant(RedBlackNode u, RedBlackNode v) {
        if (u.parent == nil) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else
            u.parent.right = v;
        v.parent = u.parent;
    }

    // Finds the node with the minimum key in the subtree rooted at the given node.
    private RedBlackNode treeMinimum(RedBlackNode z) {
        while (z.left != nil) {
            z = z.left;
        }
        return z;
    }

    // Finds the node with the maximum key in the subtree rooted at the given node.
    private RedBlackNode treeMaximum(RedBlackNode z) {
        while (z.right != nil) {
            z = z.right;
        }
        return z;
    }

    // Fixes any violations of the Red-Black Tree properties after a deletion.
    

    private void fixDeleteViolation(RedBlackNode x) {
        while (x != root && x.color == NodeColor.BLACK) {
            if (x == x.parent.left) {
                RedBlackNode w = x.parent.right;
                if (w.color == NodeColor.RED) {
                    w.color = NodeColor.BLACK;
                    x.parent.color = NodeColor.RED;
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == NodeColor.BLACK && w.right.color == NodeColor.BLACK) {
                    w.color = NodeColor.RED;
                    x = x.parent;
                    continue;
                } else if (w.right.color == NodeColor.BLACK) {
                    w.left.color = NodeColor.BLACK;
                    w.color = NodeColor.RED;
                    rotateRight(w);
                    w = x.parent.right;
                }
                if (w.right.color == NodeColor.RED) {
                    w.color = x.parent.color;
                    x.parent.color = NodeColor.BLACK;
                    w.right.color = NodeColor.BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                RedBlackNode w = x.parent.left;
                if (w.color == NodeColor.RED) {
                    w.color = NodeColor.BLACK;
                    x.parent.color = NodeColor.RED;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == NodeColor.BLACK && w.left.color == NodeColor.BLACK) {
                    w.color = NodeColor.RED;
                    x = x.parent;
                    continue;
                } else if (w.left.color == NodeColor.BLACK) {
                    w.right.color = NodeColor.BLACK;
                    w.color = NodeColor.RED;
                    rotateLeft(w);
                    w = x.parent.left;
                }
                if (w.left.color == NodeColor.RED) {
                    w.color = x.parent.color;
                    x.parent.color = NodeColor.BLACK;
                    w.left.color = NodeColor.BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = NodeColor.BLACK;
    }

    public void printBooks(int bookId1, int bookId2) {
        List<RedBlackNode> listOfBooks = new ArrayList<>();
        inorder(this.root, bookId1, bookId2, listOfBooks, true);
        for (RedBlackNode book : listOfBooks)
            resultString.append(book + "\n");
    }

    public void findClosestBook(int targetId) {
        int minDiff = Integer.MAX_VALUE;
        List<RedBlackNode> listOfBooks = new ArrayList<>();
        inorder(this.root, -1, -1, listOfBooks, false);
        List<RedBlackNode> res = new ArrayList<>();
        for (RedBlackNode book : listOfBooks) {
            int diff = Math.abs(targetId - book.bookId);
            if (minDiff > diff) {
                minDiff = diff;
                res = new ArrayList<>();
                res.add(book);
            } else if (minDiff == diff)
                res.add(book);
        }
        Collections.sort(res, (x, y) -> {
            return x.bookId - y.bookId;
        });
        for (RedBlackNode book : res)
            resultString.append(book + "\n");
    }

    private void inorder(RedBlackNode book, int lower, int upper, List<RedBlackNode> listOfBooks, boolean flag) {
        if (book == nil)
            return;
        inorder(book.left, lower, upper, listOfBooks, flag);
        if (flag) {
            if (book.bookId >= lower && book.bookId <= upper)
                listOfBooks.add(book);
        } else {
            listOfBooks.add(book);
        }
        inorder(book.right, lower, upper, listOfBooks, flag);
    }


    public void borrowBook(int patronId, int bookId, int patronPriority) {
        RedBlackNode book = printBook(bookId);
        if (book == null)
            return;
        if (book.isAvailable) {
            book.borrowedBy = patronId;
            book.isAvailable = false;
            resultString.append("Book " + bookId + " Borrowed by Patron " + patronId + "\n");
        } else if (alreadyReservedByPatron(patronId, book)) {
            resultString.append("Book " + bookId + " Already Reserved by Patron " + patronId + "\n");
        } else {
            resultString.append("Book " + bookId + " Reserved by Patron " + patronId + "\n");
            book.minHeap.insertNode(new ReservationNode(patronId, patronPriority, new Date()));
        }
    }

    public boolean alreadyReservedByPatron(int patronId, RedBlackNode book) {
        for (ReservationNode reservation : book.minHeap.heap) {
            if (reservation != null && reservation.getPatronId() == patronId) {
                return true;
            }
        }
        return false;
    }

    public void returnBook(int patronId, int bookId) {
        RedBlackNode book = printBook(bookId);
        if (book == null)
            return;
        if (book.borrowedBy != patronId)
            return;
        if (book.isAvailable)
            return;
        book.borrowedBy = -1;
        book.isAvailable = true;
        resultString.append("Book " + bookId + " Returned by Patron " + patronId + "\n");
        if (!book.minHeap.isEmpty()) {
            ReservationNode latestReservation = book.minHeap.poll();
            if (latestReservation.getPatronId() == -1)
                return;
            book.borrowedBy = latestReservation.getPatronId();
            book.isAvailable = false;
            resultString.append("Book " + bookId + " Allotted to Patron " + latestReservation.getPatronId() + "\n");
        }
    }

    public void quit() {
        resultString.append("Program Terminated!!\n");
        this.root = null;
    }

}
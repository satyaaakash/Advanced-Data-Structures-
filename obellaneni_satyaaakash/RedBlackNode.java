/**
 * The `RedBlackNode` class represents a node in the Red-Black Tree used to manage books in a library.
 * Each node contains information about a book, including its ID, title, author, availability status,
 * borrower information, and reservations.
 */
public class RedBlackNode {
    int bookId;               // Unique identifier for the book
    String bookName;          // Title of the book
    String authorName;        // Author of the book
    boolean isAvailable;      // Availability status of the book
    int borrowedBy;           // ID of the patron who borrowed the book (-1 if not borrowed)
    RedBlackNode left, right, parent; // References to left child, right child, and parent nodes
    NodeColor color;          // Color of the node (RED or BLACK) for Red-Black Tree balancing
    MinHeap minHeap;          // MinHeap to manage reservations for the book

    // Constructor without parameters
    public RedBlackNode() {
    }

    /**
     * Constructor with parameters to initialize a book node.
     *
     * @param bookId      The unique identifier of the book.
     * @param bookName    The title of the book.
     * @param authorName  The author of the book.
     * @param isAvailable The availability status of the book.
     */
    public RedBlackNode(int bookId, String bookName, String authorName, boolean isAvailable) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.left = EmptyRBNode.nil;
        this.right = EmptyRBNode.nil;
        this.parent = EmptyRBNode.nil;
        this.color = NodeColor.BLACK;
        this.isAvailable = isAvailable;
        this.borrowedBy = -1;
        minHeap = new MinHeap(20); // Initialize the MinHeap with a capacity of 20 reservations
    }

    /**
     * Constructor with only bookId, used for creating a node with minimal information.
     *
     * @param bookId The unique identifier of the book.
     */
    public RedBlackNode(int bookId) {
        this.bookId = bookId;
        this.color = NodeColor.BLACK;
    }

    /**
     * Overrides the default toString method to provide a string representation of the book node.
     *
     * @return A string representation of the book node, including book details and reservations.
     */
    @Override
    public String toString() {
        return "BookID = " + bookId +
                "\nTitle = \"" + bookName + "\"" +
                "\nAuthor = \"" + authorName + "\"" +
                "\nAvailability = \"" + (isAvailable ? "Yes" : "No") + "\"" +
                "\nBorrowedBy = " + (borrowedBy == -1 ? "None" : borrowedBy) +
                "\nReservations = [" + minHeap + "]\n";
    }
}

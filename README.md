# Advanced-Data-Structures-
The repository contains all the assignments and projects related to course COP5536 - Advanced Data Structures
# GatorLibrary Management System

## Project Overview
The **GatorLibrary Management System** is a software solution designed for managing the operations of the fictional GatorLibrary. Utilizing Red-Black trees for efficient book management and Binary Min-heaps for managing book reservations, this system streamlines library operations such as adding, borrowing, and returning books, among other functionalities. Developed under the guidance of Dr. Sartaj Sahni, this system supports robust library management needs.

## Features
- **Efficient Book Management**: Utilizes Red-Black trees to manage books, ensuring efficient search, insertion, and deletion operations.
- **Reservation System**: Employs Binary Min-heaps to handle book reservations, prioritizing based on reservation time and patron priority.
- **Comprehensive Library Operations**: Supports operations for printing book details, inserting new books, borrowing and returning books, deleting books, finding the closest book, and monitoring color flips in the Red-Black tree.

## Technologies
- Java
- Visual Studio Code

## Setup Instructions
1. Extract the contents of `Obellaneni_SatyaAakash.zip` and navigate to the extracted folder.
2. Ensure Java is installed and properly configured on your system.

## Usage
To execute the GatorLibrary Management System, open Command Prompt or PowerShell in the project folder and run the following command:

```sh
java gatorLibrary <filename>


Code Structure
The project is divided into several key Java files:

EmptyRBNode.java: Defines a sentinel node for the Red-Black tree.
RedBlackNode.java: Represents a node in the Red-Black tree, containing book information and reservation data.
RedBlackTree.java: Manages the Red-Black tree structure for the library system.
MinHeap.java: Implements a Binary Min-Heap for managing book reservations.
ReservationNode.java: Represents a node within the MinHeap, containing reservation details.
NodeColor.java: An enumeration defining RED and BLACK colors used in the Red-Black tree.
gatorLibrary.java: The main class that reads input commands, executes library operations, and generates an output file.
Example Input and Output
Input Format
scss
Copy code
InsertBook(bookID, title, author, availability)
PrintBook(bookID)
...
Output Format
makefile
Copy code
BookID = <bookID>
Title = "<bookTitle>"
Author = "<authorName>"
Availability = "<Yes|No>"
...
For detailed examples, refer to the provided Report.pdf.

'''

##
Contributions are welcome. Please feel free to fork the repository and submit pull requests.

## License
This project is licensed under the MIT License - see the LICENSE.md file for details.

## Acknowledgments
Dr. Sartaj Sahni for project guidance
The University of Florida for the educational opportunity

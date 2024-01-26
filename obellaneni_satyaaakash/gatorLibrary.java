import java.io.*;

/**
 * The `gatorLibrary` class reads commands from a file and performs operations on a Red-Black Tree (rbTree).
 * Each line in the file represents a command, and the commands are parsed and executed accordingly.
 * The results are stored in an output file with the same name as the input file appended with "_output_file.txt".
 */
public class gatorLibrary {
    private static final String COMMA = ",";
    private static final String OPEN_PARENTHESIS = "(";
    private static final String CLOSED_PARENTHESIS = ")";

    /**
     * The main method reads the input file, processes each command, and generates an output file.
     *
     * @param args Command-line arguments (expects the input file path as args[0]).
     */
    public static void main(String[] args) {
        try {
            // Read the input file and initialize a Red-Black Tree.
            String fileName = args[0];
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
            RedBlackTree rbTree = new RedBlackTree();
            String row;

            // Process each command in the file.
            while ((row = bufferedReader.readLine()) != null) {
                parse(rbTree, row, fileName.substring(0, fileName.indexOf(".")));
            }

            // Create an output file with the results.
            createOutput(fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses a command and performs the corresponding operation on the Red-Black Tree.
     *
     * @param rbTree   The Red-Black Tree instance.
     * @param row      The command to be parsed.
     * @param fileName The name of the input file (used for output file naming).
     * @throws IOException If an I/O error occurs during parsing.
     */
    private static void parse(RedBlackTree rbTree, String row, String fileName) throws IOException {
        // Remove double quotes and extract command details.
        row = row.replaceAll("\"", "");
        int start = row.indexOf(OPEN_PARENTHESIS);
        int end = row.indexOf(CLOSED_PARENTHESIS);
        String[] argArray = row.substring(start + 1, end).split(COMMA);
        String operation = row.substring(0, start);

        // Execute the corresponding operation based on the command.
        if (operation.equals("InsertBook")) {
            rbTree.insertBook(Integer.parseInt(argArray[0].trim()), argArray[1].trim(), argArray[2].trim(), argArray[3].trim());
        } else if (operation.equals("PrintBook")) {
            RedBlackNode book = rbTree.printBook(Integer.parseInt(argArray[0].trim()));
            if (book == null) {
                RedBlackTree.resultString.append("Book " + Integer.parseInt(argArray[0].trim()) + " not found in the library\n");
            } else {
                RedBlackTree.resultString.append(book + "\n");
            }
        } else if (operation.equals("PrintBooks")) {
            rbTree.printBooks(Integer.parseInt(argArray[0].trim()), Integer.parseInt(argArray[1].trim()));
        } else if (operation.equals("BorrowBook")) {
            rbTree.borrowBook(Integer.parseInt(argArray[0].trim()), Integer.parseInt(argArray[1].trim()),
                    Integer.parseInt(argArray[2].trim()));
        } else if (operation.equals("ReturnBook")) {
            rbTree.returnBook(Integer.parseInt(argArray[0].trim()), Integer.parseInt(argArray[1].trim()));
        } else if (operation.equals("DeleteBook")) {
            rbTree.deleteBook(Integer.parseInt(argArray[0].trim()));
        } else if (operation.equals("FindClosestBook")) {
            rbTree.findClosestBook(Integer.parseInt(argArray[0].trim()));
        } else if (operation.equals("ColorFlipCount")) {
            rbTree.getColorFlipCount();
        } else if (operation.equals("Quit")) {
            // Quit the program, create an output file, and exit.
            rbTree.quit();
            createOutput(fileName);
            System.exit(0);
        } else {
            // Handle invalid commands.
            RedBlackTree.resultString.append("Invalid gatorLibrary operation\n");
        }
    }

    /**
     * Creates an output file with the results stored in the Red-Black Tree.
     *
     * @param fileName The name of the input file (used for output file naming).
     * @throws IOException If an I/O error occurs during file creation.
     */
    private static void createOutput(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName + "_output_file.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(String.valueOf(RedBlackTree.resultString));
        bufferedWriter.close();
        fileWriter.close();
    }
}

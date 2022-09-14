package View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class View {

    private final Path pathOutput;
    private final Path pathInput;

    public View() {
        pathOutput = Paths.get("../output.txt");
        pathInput = Paths.get("../input.txt");

    }

    public String[] askCommand() {
        String[] commandeTab;
        System.out.println("\nEnter your command (help to display commands) : ");
        Scanner clavier = new Scanner(System.in);
        String commande = clavier.nextLine();
        commande = commande.toUpperCase();
        commandeTab = commande.split("_");
        return commandeTab;
    }

    public void printHelp() {
        System.out.println();
        System.out.println("List of availables : ");
        System.out.println("Structure of a command : Action_Key_Message \n");

        System.out.println("* EXIT    : to exit the program.");
        System.out.println("* HELP    : to display the available commands.");
        System.out.println("* ENCODE  : to encode the given message using the given key.");
        System.out.println("* DECODE  : to decode the given message using the given key.");
        System.out.println("* CRACK   : to decode the given without any key.");
        System.out.println("* FILE    : to use both files input.txt and output.txt\n");

        System.out.println("    -> The files must be at the root of the project");
        System.out.println("    -> A Key of length 1 will refer as a Cesar en/decoding");
        System.out.println("    -> The Key used for Cesar must be an int");
        System.out.println("    -> The Key used for Vigenere must be a string.");
        System.out.println("    -> You can use both lower or upper case characters.");
        System.out.println("    -> Special characters and the space character are not taken into account.\n");

        System.out.println("* Example of Cesar encoding command : encode_5_I want to encode this message");
        System.out.println("* Example of Vigenere decoding command : decode_security_iazheazndoii");
        System.out.println("* Example of crack comamnd : crack_pleasedontcrackme\n\n");

    }

    public void printResultConsole(String output) {
        System.out.println("Output : " + output + "\n");
    }

    public void displayWelcome() {
        System.out.println();
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| Hello and welcome to the Frequential Analysis project |");
        System.out.println("|        made by Aurelian Henin and Ian Cotton          |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println();
    }

    public void shutDown() {
        System.out.println();
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|    Thanks for testing our project, hope you enjoyed   |");
        System.out.println("|               Have a nice day. Goodbye !              |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println();
    }

    public String[] getInputFile() throws IOException {
        String commande = Files.readString(pathInput);
        commande = commande.toUpperCase();
        String[] commandeTab = commande.split("_");
        return commandeTab;
    }

    public void printResultFile(String output) throws IOException {
        Files.delete(pathOutput);
        Files.createFile(pathOutput);
        File fout = new File(pathOutput.toString());
        FileOutputStream fos = new FileOutputStream(fout);
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            bw.write(output);
            bw.close();
        }
        System.out.println(" --> You can look at the result at the root of the project"
                + " in the outout.txt file");
        
    }

}

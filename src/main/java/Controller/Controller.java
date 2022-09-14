package Controller;

import Command.Command;
import Command.Factory;
import View.View;
import java.io.IOException;

public class Controller {

    private final View view;
    private Boolean wantExit;
    private Boolean wantFile;

    public Controller() {
        this.view = new View();
        this.wantExit = false;
        this.wantFile = false;
    }

    public void start() {
        view.displayWelcome();
        view.printHelp();
        Factory factory = new Factory();
        while (!wantExit) {
            try {
                String[] request = view.askCommand();
                if (request[0].equalsIgnoreCase("FILE")) {
                    this.wantFile = true;
                    request = this.view.getInputFile();
                }
                if (request[0].equalsIgnoreCase("HELP")) {
                    this.view.printHelp();
                } else if (request[0].equalsIgnoreCase("EXIT")) {
                    this.wantExit = true;
                } else {
                    Command cmd = factory.getCommand(request);
                    String output = cmd.execute();
                    if (wantFile) {
                        this.view.printResultFile(output);
                        this.wantFile = false;
                    } else {
                        this.view.printResultConsole(output);
                    }
                }
            } catch (IOException e) {
                System.out.println("The input file has not been found. input.txt"
                        + "must be placed at the root of the project.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur :" + e);
            }
        }
        view.shutDown();
    }

}

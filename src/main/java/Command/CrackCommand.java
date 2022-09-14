package Command;

import Model.Model;

public class CrackCommand implements Command {

    private final String textToCrack;

    public CrackCommand(String textToCrack) {
        this.textToCrack = textToCrack;
    }

    @Override
    public String execute() {
        return Model.frequentialAnalysisCrack(textToCrack);
    }
}

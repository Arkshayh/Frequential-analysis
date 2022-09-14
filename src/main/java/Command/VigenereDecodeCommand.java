package Command;

import Model.Model;

public class VigenereDecodeCommand implements Command {

    private final String key;
    private final String cypherText;

    public VigenereDecodeCommand(String key, String cypherText) {
        this.key = key;
        this.cypherText = cypherText;
    }

    @Override
    public String execute() {
        return Model.decryptionVigenere(cypherText, key);
    }
}

package Command;

import Model.Model;

public class VigenereEncodeCommand implements Command {

    private final String text;
    private final String key;

    public VigenereEncodeCommand(String key, String text) {
        this.text = text;
        this.key = key;
    }

    @Override
    public String execute() {
        return Model.encryptionVigenere(text, key);
    }
}

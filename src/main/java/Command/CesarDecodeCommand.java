package Command;

import Model.Model;

public class CesarDecodeCommand implements Command {

    private final String message;
    private final int key;

    public CesarDecodeCommand(int key, String message) {
        this.message = message;
        this.key = key;
    }

    @Override
    public String execute() {
        return Model.decryptionCesar(message, key);
    }
}

package Command;

import Model.Model;

public class CesarEncodeCommand implements Command {

    private final String message;
    private final int key;

    public CesarEncodeCommand(int key, String message) {
        this.message = message;
        this.key = key;
    }

    @Override
    public String execute() {
        return Model.encryptionCesar(message, key);
    }
}

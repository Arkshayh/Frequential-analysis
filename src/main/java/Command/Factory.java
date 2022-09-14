package Command;

public class Factory {

    static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public Command getCommand(String[] command) {
        for (int i = 0; i < command.length; i++) {
            command[i] = command[i].toUpperCase();
        }
        String verb = command[0];
        switch (verb) {
            case "ENCODE":
                if (command.length != 3) {
                    throw new IllegalArgumentException("Size of argument to encode is invalid");
                }
                checkKey(command[1]);
                command[2] = cleanMessage(command[2]);
                if (command[1].length() == 1) {
                    return new CesarEncodeCommand(Integer.parseInt(command[1]), command[2]);
                } else {
                    return new VigenereEncodeCommand(command[1], command[2]);
                }
            case "DECODE":
                if (command.length != 3) {
                    throw new IllegalArgumentException("Size of argument to decode is invalid");
                }
                checkKey(command[1]);
                if (command[1].length() == 1) {
                    return new CesarDecodeCommand(Integer.parseInt(command[1]), command[2]);
                } else {
                    return new VigenereDecodeCommand(command[1], command[2]);
                }

            case "CRACK":
                if (command.length != 2) {
                    throw new IllegalArgumentException("Argument invalid to crack");
                } else {
                    command[1] = cleanMessage(command[1]);
                    return new CrackCommand(command[1]);
                }

            default:
                throw new IllegalArgumentException("Verb isn't recognized");
        }
    }

    void checkKey(String key) {
        if (key.length() == 1) {
            try {
                int keyInt = Integer.parseInt(key);
                if (keyInt < 1 || keyInt > 25) {
                    throw new IllegalArgumentException(
                            "Given key for Cesar must be included between 1 and 25");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Given key for Cesar is not ok,"
                        + " when you use a key of length 1, The Cesar en/decode is"
                        + " called and the key must be an int between 1 and 25");
            }
        } else {
            for (int i = 0; i < key.length(); i++) {
                if (!ALPHABET.contains(String.valueOf(key.charAt(i)))) {
                    throw new IllegalArgumentException("Key invalid for Vigenere : "
                            + key + " at index " + i + " : " + key.charAt(i));
                }
            }
        }
    }

    private String cleanMessage(String message) {
        StringBuilder finalMessage = new StringBuilder(message.length());
        for (int i = 0; i < message.length(); i++) {
            if (ALPHABET.contains(String.valueOf(message.charAt(i)))) {
                finalMessage.append(message.charAt(i));
            }
        }
        return finalMessage.toString();
    }
}

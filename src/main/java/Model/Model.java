package Model;

import java.util.ArrayList;
import java.util.List;

public class Model {
    static final List<Double> expectedFrequence = new ArrayList<>(List.of(8.08, 1.67, 3.18, 3.99, 12.56, 2.17, 1.80, 5.27,
            7.24, 0.14, 0.63, 4.04, 2.60, 7.38, 7.47, 1.91, 0.09, 6.42, 6.59, 9.15, 2.79, 1.00, 1.89, 0.21,
            1.65, 0.07));

    /**
     * Cypher the text with the given key by the Caesar method
     * @param text String
     * @param key int
     * @return String
     */
    public static String encryptionCesar(String text, int key) {
        if (Math.abs(key) % 26 == 0) {
            throw new IllegalArgumentException("Your message won't be crypted");
        }
        if (Math.abs(key) > 25) {
            key = key % 26;
        }
        text = text.toUpperCase();
        StringBuilder result = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            result.append(toValidAlphabeticChar(ch, key));
        }
        return result.toString();
    }

    /**
     * Decrypt the cypher text with the given key
     * @param text String
     * @param key int
     * @return String
     */
    public static String decryptionCesar(String text, int key) {
        return encryptionCesar(text, -key);
    }

    /**
     * Cypher the text with the given key by the Vigenere method
     * @param text String
     * @param keyword String
     * @return String
     */
    public static String encryptionVigenere(String text, String keyword) {
        text = text.toUpperCase();
        keyword = keyword.toUpperCase();

        char[] msg = text.toCharArray();
        int msgLen = msg.length;
        int i, j;

        char[] key = new char[msgLen];
        char[] encryptedMsg = new char[msgLen];

        for (i = 0, j = 0; i < msgLen; ++i, ++j) {
            if (j == keyword.length()) {
                j = 0;
            }
            key[i] = keyword.charAt(j);
        }
        for (i = 0; i < msgLen; ++i) {
            encryptedMsg[i] = (char) (((msg[i] + key[i]) % 26) + 'A');
        }

        return String.valueOf(encryptedMsg);
    }

    /**
     * Decrypt the cipher text with the given key
     * @param cypherText String
     * @param keyword String
     * @return String
     */
    public static String decryptionVigenere(String cypherText, String keyword) {
        cypherText = cypherText.toUpperCase();
        keyword = keyword.toUpperCase();

        char[] msg = cypherText.toCharArray();
        int msgLen = msg.length;
        int i, j;

        char[] key = new char[msgLen];
        char[] encryptedMsg = cypherText.toCharArray();
        char[] decryptedMsg = new char[msgLen];

        for (i = 0, j = 0; i < msgLen; ++i, ++j) {
            if (j == keyword.length()) {
                j = 0;
            }
            key[i] = keyword.charAt(j);
        }
        for (i = 0; i < msgLen; ++i) {
            decryptedMsg[i] = (char) ((((encryptedMsg[i] - key[i]) + 26) % 26) + 'A');
        }

        return String.valueOf(decryptedMsg);
    }
    
    private static char toValidAlphabeticChar(char ch, int key) {
        if (ch >= 'A' && ch <= 'Z') {
            ch = (char) (ch + key);
            if (ch > 'Z') {
                ch = (char) (ch - 'Z' + 'A' - 1);
            } else if (ch < 'A') {
                ch = (char) (ch + 'Z' - 'A' + 1);
            }
            return ch;
        }
        return Character.MIN_VALUE;
    }

    /**
     * Try to crack the given text
     * @param cryptedMessage String
     * @return String
     */
    public static String frequentialAnalysisCrack(String cryptedMessage) {
        int key = findKeyViaIC(cryptedMessage);

        String[] cryptedSplit = splitCryptedText(key, cryptedMessage);

        return finalMethod(cryptedSplit);
    }

    /**
     * Compute the frequence of each letter of the given text
     * @param text String
     * @return List
     */
    private static List<Double> computeFrequence(String text){
        text = text.toUpperCase();
        double [] tab = new double[26];
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            tab[text.charAt(i) - 'A']++;
        }

        for (int i = 0; i < tab.length; i++) {
            tab[i] =  (tab[i] / text.length()) *100.0;
            list.add(Math.round(tab[i]*100.0)/100.0);
        }
        return list;
    }

    /**
     * Split the crypted text depending on the length of the key
     * @param keyLenght int
     * @param cryptedText String
     * @return String[]
     */
    private static String[] splitCryptedText(int keyLenght, String cryptedText){
        String[] split = new String[keyLenght];

        StringBuilder transi = new StringBuilder();
        for (int i = 0; i < keyLenght; i++) {
            for (int j = i; j < cryptedText.length(); j+=keyLenght) {
                transi.append(cryptedText.charAt(j));
            }
            split[i] = transi.toString();
            transi = new StringBuilder();
        }
        return split;
    }

    /**
     * Compute the chisquare depending on the given frequence.
     * @param ourFrequence List
     * @return double
     */
    private static double chiSquare(List<Double> ourFrequence){
        double chi = 0;

        for (int i = 0; i < ourFrequence.size(); i++) {
            chi += ((ourFrequence.get(i) - expectedFrequence.get(i)) * (ourFrequence.get(i) - expectedFrequence.get(i))) / expectedFrequence.get(i);
        }

        return chi;
    }

    /**
     * return the decryted text
     * @param split String[]
     * @return String
     */
    private static String finalMethod(String[] split){
        String [] finalSplit = new String[split.length];

        for (int i = 0; i < split.length; i++) {
            finalSplit[i] = cesarForSplit(split[i]);
        }

        return recombineSplited(finalSplit);
    }

    /**
     * Apply the caeser method on the given text
     * @param split String
     * @return String
     */
    private static String cesarForSplit(String split){
        List<String> cesarTest = new ArrayList<>();
        List<List<Double>> listeDeFrequence = new ArrayList<>();
        List<Double> chiForEachFrequence = new ArrayList<>();

        for (int i = 1; i < 26; i++) {
            cesarTest.add(encryptionCesar(split,i));
        }
        for (int i = 0; i < cesarTest.size(); i++) {
            listeDeFrequence.add(computeFrequence(cesarTest.get(i)));
            chiForEachFrequence.add(chiSquare(listeDeFrequence.get(i)));
        }

        return cesarTest.get(getIndexMinimum(chiForEachFrequence)).toUpperCase();
    }

    /**
     * return the index of the minimum in the given
     * @param liste List
     * @return int
     */
    private static int getIndexMinimum(List<Double> liste){
        int indexMin = 0;
        for (int i = 1; i < liste.size(); i++) {
            if(liste.get(indexMin) > liste.get(i)){
                indexMin = i;
            }
        }

        return indexMin;
    }

    /**
     * Find the length of the key
     * @param msg String
     * @return int
     */
    private static int findKeyViaIC(String msg){
        int key = 1;
        double limite = 0.065;
        while (compute_IC(msg, key) < limite){
            key++;
        }
        return key;
    }

    /**
     * calculates the coincidence index of our cipher text
     * @param msg String the crypted text
     * @param pas the current value of the key
     * @return double
     */
    private static double compute_IC(String msg, int pas){
        msg = msg.toUpperCase();
        List<Float> IC = new ArrayList<>();
        float [] nb_lettre;
        int compteur  = 0;


        for (int i = 0; i < pas; i++) {
            nb_lettre = new float[26];
            for (int j = 0; j < msg.length(); j+=pas) {
                nb_lettre[msg.charAt(j) - 'A']++;
                compteur++;
            }

            for (int j = 0; j < nb_lettre.length; j++) {
                nb_lettre[j] = sommeSpecial(nb_lettre[j]);
            }

            IC.add(somme(nb_lettre) / (float)(compteur * (compteur+1)) );
            compteur = 0;
        }
        return somme(IC) / (float)(IC.size());
    }

    /**
     * Do a special calcul
     * @param nb float
     * @return float
     */
    private static float sommeSpecial(float nb){
        return nb * (nb - 1);
    }

    /**
     * Sum each elem of an array
     * @param tab float[]
     * @return int
     */
    private static int somme(float [] tab){
        int som =0;
        for (float v : tab) {
            som += v;
        }
        return som;
    }

    /**
     * Sum each elem of an array
     * @param liste List[]
     * @return float
     */
    private static float somme(List<Float> liste){
        float som =0;
        for (Float aFloat : liste) {
            som += aFloat;
        }
        return som;
    }

    /**
     * Combine the different split in a string
     * @param splitedTexts String[]
     * @return String
     */
    private static String recombineSplited(String[] splitedTexts) {
        int nbSplits = splitedTexts.length;
        int totalLength = 0;
        int[] indexPick = new int[splitedTexts.length];
        for (String splitedText : splitedTexts) {
            totalLength = totalLength + splitedText.length();
        }
        StringBuilder output = new StringBuilder(totalLength);
        for (int indexOutput = 0; indexOutput < totalLength; indexOutput++) {
            for (int splited = 0; splited < nbSplits; splited++) {
                if (indexOutput % nbSplits == splited) {
                    output.append(splitedTexts[splited].charAt(indexPick[splited]));
                    indexPick[splited]++;
                }
            }
        }
        return output.toString();
    }
}

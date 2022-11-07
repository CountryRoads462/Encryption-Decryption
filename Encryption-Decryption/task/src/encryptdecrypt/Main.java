package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    static void encryptionDecryption(String mode, int key, String data, File outFile, String alg) {
        char[] chars = data.toCharArray();

        if (alg.equals("unicode")) {
            for (int i = 0; i < chars.length; i++) {
                if (mode.equals("enc")) {
                    chars[i] += key;
                } else {
                    chars[i] -= key;
                }
            }
        } else {
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] > 96 && chars[i] < 123 ||
                        chars[i] > 64 && chars[i] < 91) {
                    if (mode.equals("enc")) {
                        if (Character.isLowerCase(chars[i])) {
                            if (chars[i] + key > 122) {
                                chars[i] = (char) (chars[i] + key - 26);
                            } else {
                                chars[i] = (char) (chars[i] + key);
                            }
                        } else {
                            if (chars[i] + key > 90) {
                                chars[i] = (char) (chars[i] + key - 26);
                            } else {
                                chars[i] = (char) (chars[i] + key);
                            }
                        }
                    } else {
                        if (Character.isLowerCase(chars[i])) {
                            if (chars[i] - key < 97) {
                                chars[i] = (char) (chars[i] - key + 26);
                            } else {
                                chars[i] = (char) (chars[i] - key);
                            }
                        } else {
                            if (chars[i] - key < 65) {
                                chars[i] = (char) (chars[i] - key + 26);
                            } else {
                                chars[i] = (char) (chars[i] - key);
                            }
                        }
                    }
                }
            }
        }

        String result = String.valueOf(chars);

        if (outFile == null) {
            System.out.print(result);
        } else {
            try (FileWriter fileWriter = new FileWriter(outFile)) {
                fileWriter.write(result);
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }

    static boolean argsContainData(String[] args) {
        List<String> argsAsList = new ArrayList<>();
        Collections.addAll(argsAsList, args);
        return argsAsList.contains("-data");
    }

    public static void main(String[] args) {
        File outFile = null;
        String alg = "shift";
        String mode = "enc";
        int key = 0;
        String data = "";

        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-mode":
                    mode = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    data = args[i + 1];
                    break;
                case "-out":
                    outFile = new File(args[i + 1]);
                    break;
                case "-in":
                    if (!argsContainData(args)) {
                        File inFile = new File(args[i + 1]);
                        try (Scanner fileScanner = new Scanner(inFile)) {
                            while (fileScanner.hasNextLine()) {
                                data = fileScanner.nextLine();
                            }
                        } catch (FileNotFoundException e) {
                            System.out.println("Error");
                        }
                    }
                    break;
                case "-alg":
                    alg = args[i + 1];
                default:
                    break;
            }
        }

        encryptionDecryption(mode, key, data, outFile, alg);
    }
}

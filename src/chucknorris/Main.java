package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String operation;
        final String welcomeText = "Please input operation (encode/decode/exit):";
        boolean isExit = false;

        do {
            System.out.println(welcomeText);
            operation = scanner.nextLine();

            switch (operation) {
                case "encode" -> {
                    System.out.println("Input string:");
                    String encodedString = encodeString(scanner.nextLine());
                    System.out.println("Encoded string:");
                    System.out.println(encodedString);
                }
                case "decode" -> {
                    System.out.println("Input encoded string:");
                    try {
                        String decodedString = decodeString(scanner.nextLine());
                        System.out.println("Decoded string:");
                        System.out.println(decodedString);
                    } catch (EncodeError e) {
                        System.out.println("Encoded string is not valid.");
                    }
                }
                case "exit" -> {
                    isExit = true;
                    System.out.println("Bye!");
                }
                default -> System.out.printf("There is no '%s' operation\n", operation);
            }
            System.out.println();
        } while (!isExit);
    }

    public static String encodeString(String str) {
        char[] charArray = str.toCharArray();
        final int sizeBit = 7;
        StringBuilder strBin = new StringBuilder();

        for (char item : charArray) {
            String binNumb = Integer.toBinaryString(item);
            int len = binNumb.length();
            if (len < sizeBit) {
                String zeroTemplate = "%" + String.format("0%sd", sizeBit - len);
                strBin.append(String.format(zeroTemplate, 0)).append(binNumb);
            } else {
                strBin.append(binNumb);
            }
        }

        char[] charArrayBin = strBin.toString().toCharArray();
        StringBuilder strEncoded = new StringBuilder();
        char lastChar = ' ';

        for (char item : charArrayBin) {
            if (lastChar != item) {
                lastChar = item;
                String typeSymbol = item == '1' ? "0" : "00";
                strEncoded.append(" ").append(typeSymbol).append(" ");
            }
            strEncoded.append("0");
        }
        return strEncoded.toString().trim();
    }

    public static String decodeString(String str) throws EncodeError {
        String[] strArrEnc = str.split(" ");

        if (!validateEncodeStringArray(strArrEnc)) {
            throw new EncodeError();
        }
        StringBuilder strBin = new StringBuilder();
        StringBuilder strDecoded = new StringBuilder();

        for (int i = 0; i < strArrEnc.length; i = i + 2) {
            char type = strArrEnc[i].equals("0") ? '1' : '0';
            String sequenceDigit = strArrEnc[i + 1].replace('0', type);
            strBin.append(sequenceDigit);
        }

        for (int i = 0; i < strBin.length(); i = i + 7) {
            String binBlock = strBin.substring(i, i + 7);
            strDecoded.append((char) Integer.parseInt(binBlock, 2));
        }


        return strDecoded.toString();
    }

    public static Boolean validateEncodeStringArray(String[] strings) {
        boolean isValid = strings.length % 2 == 0;
        String regexPattern = "[^0\\s]+?";
        int lenMainData = 0;
        if (isValid) {
            for (int i = 0; i < strings.length; i = i + 2) {
                boolean a = strings[i].matches(regexPattern);
                if (strings[i].matches(regexPattern) || strings[i + 1].matches(regexPattern)) {
                    isValid = false;
                    break;
                }
                if (strings[i].length() > 2) {
                    isValid = false;
                    break;
                }
                lenMainData = lenMainData + strings[i + 1].length();
            }
            if (isValid && lenMainData % 7 != 0) {
                isValid = false;
            }
        }

        return isValid;
    }
}
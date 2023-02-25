package exercice;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PlayfairCipher {
    
    private char[][] keySquare = new char[5][5];
	private static Scanner scanner;
    
	public void setKey(String key) {
	    key = key.toUpperCase().replaceAll("[^A-Z]", "");
	    key = key.replace("J", "I");
	    boolean[] used = new boolean[26];
	    int index = 0;
	    for (int i = 0; i < key.length() && index < 25; i++) {
	        char c = key.charAt(i);
	        if (!used[c - 'A']) {
	            used[c - 'A'] = true;
	            keySquare[index / 5][index % 5] = c;
	            index++;
	        }
	    }
	    char c = 'A';
	    for (int i = index; i < 25; i++) {
	        if (c == 'J') {
	            c++;
	        }
	        if (!used[c - 'A']) {
	            keySquare[i / 5][i % 5] = c;
	            used[c - 'A'] = true;
	            index++;
	        }
	        c++;
	    }
	}

    
    public String encrypt(String plaintext) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        plaintext = plaintext.replace("J", "I");
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            char a = plaintext.charAt(i);
            char b = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : 'X';
            int row1 = -1, col1 = -1, row2 = -1, col2 = -1;
            for (int j = 0; j < 5 && (row1 == -1 || row2 == -1); j++) {
                for (int k = 0; k < 5 && (col1 == -1 || col2 == -1); k++) {
                    if (keySquare[j][k] == a) {
                        row1 = j;
                        col1 = k;
                    } else if (keySquare[j][k] == b) {
                        row2 = j;
                        col2 = k;
                    }
                }
            }
            if (row1 == row2) {
                col1 = (col1 + 1) % 5;
                col2 = (col2 + 1) % 5;
            } else if (col1 == col2) {
                row1 = (row1 + 1) % 5;
                row2 = (row2 + 1) % 5;
            } else {
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }
            ciphertext.append(keySquare[row1][col1]);
            ciphertext.append(keySquare[row2][col2]);
        }
        return ciphertext.toString();
    }
    
    public static void main(String[] args) {
        PlayfairCipher cipher = new PlayfairCipher();
        cipher.setKey("MONARCHY");

        System.out.print("Enter the file path for plaintext: ");
        try (Scanner scanner = new Scanner(System.in); Scanner fileScanner = new Scanner(new File(scanner.nextLine()))) {
            StringBuilder sb = new StringBuilder();
            while (fileScanner.hasNextLine()) {
                sb.append(fileScanner.nextLine());
            }
            String plaintext = sb.toString();
            String ciphertext = cipher.encrypt(plaintext);
            System.out.println("Ciphertext: " + ciphertext);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}

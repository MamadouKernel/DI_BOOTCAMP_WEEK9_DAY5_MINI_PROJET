package exercice2;

import java.util.Scanner;

public class PlayfairCipher {

    private char[][] keySquare = new char[5][5];

    public static void main(String[] args) {
        PlayfairCipher cipher = new PlayfairCipher();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key = sc.nextLine();
        cipher.setKey(key);
        System.out.print("Enter the plaintext: ");
        String plaintext = sc.nextLine();
        String ciphertext = cipher.encrypt(plaintext);
        System.out.println("Ciphertext: " + ciphertext);
        sc.close();
    }

    public void setKey(String key) {
        key = key.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.replace("J", "I");
        boolean[] used = new boolean[26];
        int i = 0, j = 0;
        for (char c : key.toCharArray()) {
            if (!used[c - 'A']) {
                used[c - 'A'] = true;
                keySquare[i][j] = c;
                j++;
                if (j == 5) {
                    i++;
                    j = 0;
                }
            }
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !used[c - 'A']) {
                keySquare[i][j] = c;
                j++;
                if (j == 5) {
                    i++;
                    j = 0;
                }
            }
        }
    }

    public String encrypt(String plaintext) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        plaintext = plaintext.replace("J", "I");
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            char a = plaintext.charAt(i);
            char b = i + 1 < plaintext.length() ? plaintext.charAt(i + 1) : 'X';
            if (a == b) {
                b = 'X';
                i--;
            }
            int row1 = -1, col1 = -1, row2 = -1, col2 = -1;
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    if (keySquare[j][k] == a) {
                        row1 = j;
                        col1 = k;
                    } else if (keySquare[j][k] == b) {
                        row2 = j;
                        col2 = k;
                    }
                    if (row1 != -1 && row2 != -1) {
                        break;
                    }
                }
            }
            if (row1 == row2) {
                ciphertext.append(keySquare[row1][(col1 + 1) % 5]);
                ciphertext.append(keySquare[row2][(col2 + 1) % 5]);
            } else if (col1 == col2) {
                ciphertext.append(keySquare[(row1 + 1) % 5][col1]);
                ciphertext.append(keySquare[(row2 + 1) % 5][col2]);
            } else {
                ciphertext.append(keySquare[row1][col2]);
                ciphertext.append(keySquare[row2][col1]);
            }
        }
        return ciphertext.toString();
    }
    
    

}

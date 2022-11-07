import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        String subString = scanner.nextLine();
        int count = 0;
        for (int i = 0; i < userInput.length() - subString.length() + 1; i++) {
            if (userInput.substring(i, i + subString.length()).equals(subString)) {
                count++;
                i = i + subString.length() - 1;
            }
        }
        System.out.println(count);
    }
}
import java.io.*;
import java.util.*;

public class RestaurantOrderingSystemMain {
    static final String FILE_PATH = "C:\\Users\\PC\\Documents\\users.txt";
    static final Scanner scanner = new Scanner(System.in);

    static final String[] items = { "Pizza", "Burger", "Fries" };
    static final int[] prices = { 120, 80, 50 };
    static final int[] quantities = new int[items.length];

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n🍽️ Welcome to the Restaurant Ordering System 🍽️");
            System.out.println("[1] Login");
            System.out.println("[2] Create Account");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    if (login()) {
                        orderMenu();
                        System.out.println("🧾 Thank you for your order!");
                        return;
                    }
                }
                case "2" -> createAccount();
                default -> System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }

    static void createAccount() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            System.out.print("Enter new username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Enter new password: ");
            String password = scanner.nextLine().trim();
            writer.write(username + "," + encrypt(password));
            writer.newLine();
            System.out.println("✅ Account successfully created!");
        } catch (IOException e) {
            System.out.println("❌ Error creating account.");
        }
    }

    static boolean login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username)) {
                    if (decrypt(parts[1]).equals(password)) {
                        System.out.println("✅ Login successful!");
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error during login.");
        }

        System.out.println("❌ Login failed. Invalid credentials.");
        return false;
    }

    static void orderMenu() {
        int choice;
        do {
            System.out.println("\n=== MENU ===");
            for (int i = 0; i < items.length; i++) {
                System.out.printf("[%d] %s - ₱%d%n", i + 1, items[i], prices[i]);
            }
            System.out.println("[4] Exit");

            System.out.print("Choose an item: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Enter a valid number: ");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= 1 && choice <= 3) {
                System.out.print("Enter quantity: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Enter a valid number: ");
                    scanner.next();
                }
                int qty = scanner.nextInt();
                scanner.nextLine();
                quantities[choice - 1] += qty;

            } else if (choice == 4) {
                break;
            } else {
                System.out.println("❌ Invalid menu choice.");
            }
        } while (true);

        printReceipt();
    }

    static void printReceipt() {
        System.out.println("\n🧾 === Order Summary ===");
        int total = 0;
        for (int i = 0; i < items.length; i++) {
            if (quantities[i] > 0) {
                int cost = quantities[i] * prices[i];
                System.out.printf("%s x%d = ₱%d%n", items[i], quantities[i], cost);
                total += cost;
            }
        }
        System.out.println("TOTAL: ₱" + total);
        System.out.println("Enjoy your meal! 🍕🍔🍟");
        // pwede rani sya i erase kaning enjoy your meal kung dli giyud sya pwede
        // gaka buang nako ani wa koy laing masaligan og pangutana man gud kasagara ana
        // AI rana pero dli tanan

    }

    static String encrypt(String plain) {
        StringBuilder result = new StringBuilder();
        for (char c : plain.toCharArray()) {
            result.append((char) (c + 3));
        }
        return result.toString();
    }

    static String decrypt(String encrypted) {
        StringBuilder result = new StringBuilder();
        for (char c : encrypted.toCharArray()) {
            result.append((char) (c - 3));
        }
        return result.toString();
    }
}

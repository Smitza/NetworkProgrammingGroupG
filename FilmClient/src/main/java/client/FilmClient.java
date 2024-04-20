package client;

import service.FilmService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FilmClient {
    private static final String ADDRESS = "localhost";
    private static final int PORT = 41235;

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        try (Socket dataSocket = new Socket(ADDRESS, PORT)) {
            try (Scanner input = new Scanner(dataSocket.getInputStream());
                 PrintWriter output = new PrintWriter(dataSocket.getOutputStream())) {
                boolean validSession = true;

                while (validSession) {
                    System.out.println("Enter your message ('exit' to quit)");
                    String message = generateRequest(userInput);
                    output.println(message);
                    output.flush();

                    String response = input.nextLine();
                    System.out.println("Received: " +response);
                    if(response.equals(FilmService.GOODBYE)){
                        validSession = false;
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Host cannot be found at this moment. Try again later");
        } catch (IOException e) {
            System.out.println("An IO Exception occurred: " + e.getMessage());
        }
    }


    public static void displayMenu() {
        System.out.println("Enter your choice:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Logout");
        System.out.println("4. Rate a film");
        System.out.println("5. Search film by name");
        System.out.println("6. Search film by genre");
        System.out.println("7. Add a film (admin only)");
        System.out.println("8. Remove a film (admin only)");
        System.out.println("9. Exit");
    }

    public static String generateRequest(Scanner userInput) {
        boolean valid = false;
        String request = null;

        while (!valid) {
            displayMenu();
            String choice = userInput.nextLine();
            int value = 0;
            switch (choice) {
                case "0":
                    System.out.println("Please enter your username to register: ");
                    String regusername = userInput.nextLine();
                    System.out.println("Please enter your password to register: ");
                    String regpassword = userInput.nextLine();
                    request = FilmService.REGISTER + FilmService.DELIMITER + regusername + FilmService.DELIMITER + regpassword;
                    break;
                case "1":
                    System.out.println("Please enter your username to login: ");
                    String username = userInput.nextLine();
                    System.out.println("Please enter your password to login: ");
                    String password = userInput.nextLine();
                    request = FilmService.LOGIN + FilmService.DELIMITER + username + FilmService.DELIMITER + password;
                    break;
                default:
                    System.out.println("Please select one of the options");
                    System.out.println("=========================================================");
                    continue;
            }
            valid = true;
        }
        return  request;
    }
}


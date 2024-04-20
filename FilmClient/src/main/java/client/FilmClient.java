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

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        try (Socket dataSocket = new Socket(FilmService.HOST, FilmService.PORT)) {
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
        System.out.println("3. Rate a film (Needs login)");
        System.out.println("4. Search film by name");
        System.out.println("5. Search film by genre");
        System.out.println("6. Add a film (admin only)");
        System.out.println("7. Remove a film (admin only)");
        System.out.println("8. Shutdown (admin only)");
        System.out.println("9. Logout");
        System.out.println("10. Exit");
    }

    public static String generateRequest(Scanner userInput) {
        boolean valid = false;
        String request = null;

        while (!valid) {
            displayMenu();
            String choice = userInput.nextLine();
            int value = 0;
            switch (choice) {
                case "1":
                    System.out.println("Please enter your username to register: ");
                    String regusername = userInput.nextLine();
                    System.out.println("Please enter your password to register: ");
                    String regpassword = userInput.nextLine();
                    request = FilmService.REGISTER + FilmService.DELIMITER + regusername + FilmService.DELIMITER + regpassword;
                    break;
                case "2":
                    System.out.println("Please enter your username to login: ");
                    String username = userInput.nextLine();
                    System.out.println("Please enter your password to login: ");
                    String password = userInput.nextLine();
                    request = FilmService.LOGIN + FilmService.DELIMITER + username + FilmService.DELIMITER + password;
                    break;
                case "3":
                    System.out.println("Title of film to rate: ");
                    String rateTitle = userInput.nextLine();
                    System.out.println("Rating out of 10: ");
                    String rating = userInput.nextLine();
                    request = FilmService.RATE + FilmService.DELIMITER + rateTitle + FilmService.DELIMITER + rating;
                    break;
                case "4":
                    System.out.println("Enter Title: ");
                    String searchTitle = userInput.nextLine();
                    request = FilmService.SEARCHNAME + FilmService.DELIMITER + searchTitle;
                    break;
                case "5":
                    System.out.println("Enter Genre: ");
                    String searchGenre = userInput.nextLine();
                    request = FilmService.SEARCHGENRE + FilmService.DELIMITER + searchGenre;
                    break;
                case "6":
                    System.out.println("Enter title of Film: ");
                    String filmTitle = userInput.nextLine();
                    System.out.println("Enter genre of Film: ");
                    String filmGenre = userInput.nextLine();
                    request = FilmService.ADD + FilmService.DELIMITER + filmTitle + FilmService.DELIMITER + filmGenre;
                    break;
                case "7":
                    System.out.println("Enter title of Film to remove: ");
                    String removeTitle = userInput.nextLine();
                    request = FilmService.REMOVE + FilmService.DELIMITER + removeTitle;
                    break;
                case "8":
                    request = FilmService.SHUTDOWN;
                    break;
                case "9":
                    request = FilmService.LOGOUT;
                    break;
                case "10":
                    request = FilmService.EXIT;
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

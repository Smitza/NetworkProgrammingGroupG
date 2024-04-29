package client;

import service.FilmService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class FilmClient {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        try (Socket dataSocket = new Socket(FilmService.HOST, FilmService.PORT)) {
            try (Scanner input = new Scanner(dataSocket.getInputStream());
                 PrintWriter output = new PrintWriter(dataSocket.getOutputStream())) {
                boolean validSession = true;
                boolean isAdmin = false;
                boolean isLoggedIn = false;

                //Menu for Client
                while (validSession) {
                    if (!isLoggedIn) {
                        System.out.println("Enter your choice:");
                        System.out.println("1. Register");
                        System.out.println("2. Login");
                        System.out.println("3. Search film by name");
                        System.out.println("4. Search film by genre");
                        System.out.println("5. Exit");
                        System.out.println("11. Show a random Film");
                        System.out.println("12. Search a genre for a random Film");
                    } else {
                        System.out.println("Enter your choice:");
                        System.out.println("3. Search film by name");
                        System.out.println("4. Search film by genre");
                        System.out.println("5. Exit");
                        System.out.println("6. Rate a film");
                        System.out.println("7. Logout");
                        System.out.println("11. Show a random Film");
                        System.out.println("12. Search a genre for a random Film");
                        if (isAdmin) {
                            System.out.println("8. Add a film (admin only)");
                            System.out.println("9. Remove a film (admin only)");
                            System.out.println("10. Shutdown (admin only)");
                        }
                    }

                    String choice = userInput.nextLine();
                    String request = null;

                    //Client requests

                    switch (choice) {
                        case "1":
                            if (!isLoggedIn) {
                                System.out.println("Please enter your username to register: ");
                                String regusername = userInput.nextLine();
                                System.out.println("Please enter your password to register: ");
                                String regpassword = userInput.nextLine();
                                request = FilmService.REGISTER + FilmService.DELIMITER + regusername + FilmService.DELIMITER + regpassword;
                            } else {
                                System.out.println("You are already logged in!");
                            }
                            break;
                        case "2":
                            if (!isLoggedIn) {
                                System.out.println("Please enter your username to login: ");
                                String username = userInput.nextLine();
                                System.out.println("Please enter your password to login: ");
                                String password = userInput.nextLine();
                                request = FilmService.LOGIN + FilmService.DELIMITER + username + FilmService.DELIMITER + password;
                            } else {
                                System.out.println("You are already logged in!");
                            }
                            break;
                        case "3":
                            System.out.println("Enter Title: ");
                            String searchTitle = userInput.nextLine();
                            request = FilmService.SEARCHNAME + FilmService.DELIMITER + searchTitle;
                            break;
                        case "4":
                            System.out.println("Enter Genre: ");
                            String searchGenre = userInput.nextLine();
                            request = FilmService.SEARCHGENRE + FilmService.DELIMITER + searchGenre;
                            break;
                        case "5":
                            request = FilmService.EXIT;
                            break;
                        case "6":
                            if (isLoggedIn) {
                                System.out.println("Title of film to rate: ");
                                String filmTitle = userInput.nextLine();
                                System.out.println("Rating out of 10: ");
                                String rating = userInput.nextLine();
                                request = FilmService.RATE + FilmService.DELIMITER + filmTitle + FilmService.DELIMITER + rating;
                            } else {
                                System.out.println("Please select one of the options");
                                System.out.println("=========================================================");
                            }
                            break;
                        case "7":
                            if (isLoggedIn) {
                                request = FilmService.LOGOUT;
                            } else {
                                System.out.println("Please select one of the options");
                                System.out.println("=========================================================");
                            }
                            break;
                        case "8":
                            if (isAdmin) {
                                System.out.println("Enter title of Film: ");
                                String addTitle = userInput.nextLine();
                                System.out.println("Enter genre of Film: ");
                                String filmGenre = userInput.nextLine();
                                request = FilmService.ADD + FilmService.DELIMITER + addTitle + FilmService.DELIMITER + filmGenre;
                            } else {
                                System.out.println("Please select one of the options");
                                System.out.println("=========================================================");
                            }
                            break;
                        case "9":
                            if (isAdmin) {
                                System.out.println("Enter title of Film to remove: ");
                                String removeTitle = userInput.nextLine();
                                request = FilmService.REMOVE + FilmService.DELIMITER + removeTitle;
                            } else {
                                System.out.println("Please select one of the options");
                                System.out.println("=========================================================");
                            }
                            break;
                        case "10":
                            if (isAdmin) {
                                request = FilmService.SHUTDOWN;
                                validSession = false; // Exit the loop immediately after sending the shutdown request
                            } else {
                                System.out.println("Please select one of the options");
                                System.out.println("=========================================================");
                            }
                            break;
                        default:
                            System.out.println("Please select one of the options");
                            System.out.println("=========================================================");
                            continue;
                        case "11":
                            request = FilmService.RANDOM_FILM;
                            break;
                        case "12":
                            System.out.println("Enter genre to search: ");
                            String searchGenreRnd = userInput.nextLine();
                            request = FilmService.RANDOM_GENRE_FILM + FilmService.DELIMITER + searchGenreRnd;
                            break;
                    }
                    output.println(request);
                    output.flush();

                    String response = input.nextLine();
                    isAdmin = processResponse(response);
                    isLoggedIn = response.equals(FilmService.SUCCESSADMIN) || response.equals(FilmService.SUCCESSUSER);
                    if (response.equals(FilmService.GOODBYE)) {
                        System.out.println("Goodbye!");
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

    /**
     * Handles responses from the Film Server to the client, and outputs them in a clean format.
     * Also, correctly tracks if the user is an admin.
     *
     * @param response
     * @return
     */
    private static boolean processResponse(String response) {
        String[] parts = response.split(FilmService.DELIMITER);
        switch (parts[0]) {
            case FilmService.ADDED:
                System.out.println("User registered successfully!");
                break;
            case FilmService.REJECTED:
                System.out.println("Registration failed.");
                break;
            case FilmService.SUCCESSADMIN:
                System.out.println("Logged in as admin!");
                return true;
            case FilmService.SUCCESSUSER:
                System.out.println("Logged in as user!");
                return false;
            case FilmService.FAILED:
                System.out.println("Login failed. Please try again.");
                break;
            case FilmService.LOGOUTOUT:
                System.out.println("Logged out successfully!");
                break;
            case FilmService.INVALIDRATING:
                System.out.println("Invalid rating supplied.");
                break;
            case FilmService.NOLOGIN:
                System.out.println("You are not logged in.");
                break;
            case FilmService.NOMATCH:
                System.out.println("No matches found.");
                break;
            case FilmService.GOODBYE:
                System.out.println("Goodbye!");
                break;
            case FilmService.REMOVED:
                System.out.println("Film removed successfully.");
                break;
            case FilmService.NOTFOUND:
                System.out.println("Film not found, or you are not an admin!");
                break;
            case FilmService.EXISTS:
                System.out.println("Film already exists!");
                break;
            case FilmService.NOPERMS:
                System.out.println("Insufficient permissions!");
                break;
            case "":
                break;
            default:
                System.out.println(response);
                break;
        }
        return false;
    }
}

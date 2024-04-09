package server;

import film_service.business.Film;
import film_service.business.FilmManager;
import film_service.business.UserManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FilmServer {

    private static final UserManager userManager = new UserManager();
    private static final FilmManager filmManager = new FilmManager();

    public static void main(String[] args) {
        try (ServerSocket listeningSocket = new ServerSocket(FilmService.PORT)) {
            while (true) {
                Socket dataSocket = listeningSocket.accept();
                handleClientSession(dataSocket);
            }
        } catch (BindException e) {
            System.out.println("BindException occurred when attempting to bind to port " + FilmService.PORT);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException occurred on server socket");
            System.out.println(e.getMessage());
        }
    }

    public static void handleClientSession(Socket dataSocket) {
        try (Scanner input = new Scanner(dataSocket.getInputStream());
             PrintWriter output = new PrintWriter(dataSocket.getOutputStream())) {
            boolean validSession = true;
            while (validSession) {
                String message = input.nextLine();
                System.out.println("Message received: " + message);
                String[] components = message.split(FilmService.DELIMITER);
                String response = null;
                switch (components[0]) {
                    case (FilmService.REGISTER):
                        response = register(components);
                        break;
                    case (FilmService.LOGIN):
                        response = login(components);
                        break;
                    case (FilmService.LOGOUT):
                        response = logout(components);
                        break;
                    case (FilmService.RATE):
                        response = rate(components);
                        break;
                    case (FilmService.SEARCHNAME):
                        response = searchName(components);
                        break;
                    case (FilmService.SEARCHGENRE):
                        response = searchGenre(components);
                        break;
                    default:
                        response = FilmService.INVALID;
                }
            }
        } catch (IOException e) {
            System.out.println("An IOException occured when trying to communicate with: " + dataSocket.getInetAddress() + ":" + dataSocket.getPort());
            System.out.println(e.getMessage());
        }
    }

    private static String register(String[] components) {
        String response;
        if (components.length < 3) {
            response = FilmService.REJECTED; // Invalid request
        } else {
            String username = components[1];
            String password = components[2];
            boolean registrationResult = userManager.addUser(username, password, false);

            if (registrationResult) {
                response = FilmService.ADDED; // User registered successfully
            } else {
                response = FilmService.REJECTED; // Registration failed (e.g., username already exists)
            }
        }
        return response;
    }

    private static String login(String[] components) {
        String response;

        return response;
    }

    private static String logout(String[] components) {
        return FilmService.LOGOUTOUT;
    }

    private static String rate(String[] components) {
        String response = null;
        return response;
    }

    private static String searchName(String[] components) {
        String response;
        if (components.length != 2) {
            response = FilmService.INVALID;
        } else {
            String title = components[1];
            Film filmResult = filmManager.searchByTitle(title);
            if (filmResult != null) {
                response = filmResult.getTitle() + FilmService.DELIMITER + filmResult.getGenre() + FilmService.DELIMITER + filmResult.getTotalRatings() + FilmService.DELIMITER + filmResult.getNumRatings();
            } else {
                response = FilmService.NOMATCH;
            }
        }
        return response;
    }

    private static String searchGenre(String[] components) {
        String response;
        if (components.length != 2) {
            response = FilmService.INVALID;

        } else {
            String genre = components[1];
           List<Film> filmResult = filmManager.searchByGenre(genre);
            if (!filmResult.isEmpty()) {
                StringBuilder results = new StringBuilder();
                for (Film film : filmResult) {
                    results.append(film.getTitle()).append(FilmService.DELIMITER)
                            .append(film.getGenre()).append(FilmService.DELIMITER)
                            .append(film.getTotalRatings()).append(FilmService.DELIMITER)
                            .append(film.getNumRatings()).append(" ");

                }
                response = results.toString();
            } else {
                response = FilmService.NOMATCH;
            }
        }
        return response;
    }
}

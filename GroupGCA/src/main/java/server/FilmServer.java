package server;

import film_service.business.Film;
import film_service.business.FilmManager;
import film_service.business.UserManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FilmServer {

    private static final UserManager userManager = new UserManager();
    private static final FilmManager filmManager = new FilmManager();
    private static boolean loggedin = false;


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
                output.println(response);
                output.flush();
            }
        } catch (NoSuchElementException e) {
            System.out.println("The user sent an invalid request: ");
            System.out.println(e.getMessage());
        }catch (IOException e) {
            System.out.println("An IOException occurred when trying to communicate with: " + dataSocket.getInetAddress() + ":" + dataSocket.getPort());
            System.out.println(e.getMessage());
        }
    }

    private static String register(String[] components) {
        String response;
        if (components.length != 2) {
            response = FilmService.REJECTED;
        } else {
            String username = components[1];
            String password = components[2];
            boolean registrationResult = userManager.addUser(username, password, false);

            if (registrationResult) {
                response = FilmService.ADDED; // User registered successfully
            } else {
                response = FilmService.REJECTED; // Registration failed (username already exists)
            }
        }
        return response;
    }

    private static String login(String[] components) {
        String response;
        if (components.length != 2) {
            response = FilmService.REJECTED;
        } else {
            String username = components[1];
            String password = components[2];
            boolean isUser = userManager.authenticateUser(username, password);
            if (isUser) {
                response = FilmService.SUCCESSUSER;
                loggedin = true;
            } else {
                response = FilmService.FAILED;
            }
        }

        return response;
    }

    private static String logout(String[] components) {
        String response;
        if (components.length != 1) {
            response = FilmService.REJECTED;
        } else {
            response = FilmService.LOGOUT;
            loggedin = false;
        }
        return response;
    }

    private static String rate(String[] components) {
        String response;
        if (components.length < 2) {
            response = FilmService.REJECTED;
        } else {
            String title = components[1];
            try {
                int rating = Integer.parseInt(components[2]);
                if (rating < 0 || rating > 10) {
                    response = FilmService.INVALIDRATING;
                } else {
                    if (loggedin) {
                        boolean rated = filmManager.rateFilm(title, rating);
                        if (rated) {
                            response = FilmService.SUCCESS;
                        } else {
                            response = FilmService.INVALIDRATING;
                        }
                    } else {
                        response = FilmService.NOLOGIN;
                    }
                }
            } catch (NumberFormatException e) {
                response = FilmService.INVALIDRATING;
            }
        }
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
                response = filmResult.encode("%%");
            } else {
                response = FilmService.NOMATCH;
            }
        }
        return response;
    }
}
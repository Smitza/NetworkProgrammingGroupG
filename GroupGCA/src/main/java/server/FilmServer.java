package server;

import film_service.business.UserManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class FilmServer {

    private static final UserManager userManager = new UserManager();

    public static void main(String[] args) {
        try(ServerSocket listeningSocket = new ServerSocket(FilmService.PORT)) {
            while(true) {
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
            while(validSession){
                String message = input.nextLine();
                System.out.println("Message received: "+ message);
                String [] components = message.split(FilmService.DELIMITER);
                String response = null;
                switch(components[0]){
                    case(FilmService.REGISTER):
                        response = register(components);
                        break;
                    case(FilmService.LOGIN):
                        response = login(components);
                        break;
                    case(FilmService.LOGOUT):
                        response = logout(components);
                        break;
                    case(FilmService.RATE):
                        response = rate(components);
                        break;
                    case(FilmService.SEARCHNAME):
                        response = searchName(components);
                        break;
                    case(FilmService.SEARCHGENRE):
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

        if(components.length < 3) {
            response = FilmService.REJECTED; // Invalid request
        } else {
            String username = components[1];
            String password = components[2];
            boolean loginResult = userManager.addUser(username, password, false);

            if (loginResult) {
                response = FilmService.SUCCESS; // user have been logged in successfully
            } else {
                response = FilmService.FAILED; // login failed user have not been logged in succesfully
            }
        }

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
        String response = null;
        if(components.length < 2){
            response = FilmService.REJECTED; // invalid response format
        } else {
            String name = components[1];
            String request = FilmService.SEARCHNAME + FilmService.DELIMITER + name;


        }
        return response;
    }

    private static String searchGenre(String[] components) {
        String response = null;
        return response;
    }
}

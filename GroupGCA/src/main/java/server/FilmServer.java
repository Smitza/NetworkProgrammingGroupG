package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class FilmServer {

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
        String response = null;
        return response;
    }

    private static String login(String[] components) {
        String response = null;
        return response;
    }

    private static String logout(String[] components) {
        String response = null;
        return response;
    }

    private static String rate(String[] components) {
        String response = null;
        return response;
    }

    private static String searchName(String[] components) {
        String response = null;
        return response;
    }

    private static String searchGenre(String[] components) {
        String response = null;
        return response;
    }
}

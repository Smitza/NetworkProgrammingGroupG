package server;

import film_service.business.FilmManager;
import film_service.business.UserManager;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
public class FilmServer {
    private static FilmManager filmManager;
    private static UserManager userManager;

    public static void main(String[] args) {
        try (ServerSocket listeningSocket = new ServerSocket(FilmService.PORT)) {
            userManager = new UserManager();
            filmManager = new FilmManager();
            while (true) {
                Socket dataSocket = listeningSocket.accept();
                FilmClientHandler clientHandler = new FilmClientHandler(dataSocket, userManager, filmManager);
                Thread wrapper = new Thread(clientHandler);
                wrapper.start();
            }
        } catch (BindException e) {
            System.out.println("BindException occurred when attempting to bind to port " + FilmService.PORT);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException occurred on server socket");
            System.out.println(e.getMessage());
        }
    }
}
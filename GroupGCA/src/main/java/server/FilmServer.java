package server;

import film_service.business.FilmManager;
import film_service.business.UserManager;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
public class FilmServer {
    private static final UserManager userManager = new UserManager();
    private static final FilmManager filmManager = new FilmManager();
    public static void main(String[] args) {
        try (ServerSocket listeningSocket = new ServerSocket(FilmService.PORT)) {
            while (true) {
                Socket dataSocket = listeningSocket.accept();
                FilmClientHandler clientHandler = new FilmClientHandler(dataSocket,userManager, filmManager);
                Thread worker = new Thread(clientHandler);
                worker.start();
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
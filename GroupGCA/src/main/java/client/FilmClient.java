package client;

import server.FilmService;

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
                while (validSession) {
                    System.out.println("Please enter a message to be sent (Send exit to end):");
                    String message = userInput.nextLine();
                    output.println(message);
                    output.flush();

                    String response = input.nextLine();
                    System.out.println("Received from server: " + response);
                    if (response.equalsIgnoreCase(FilmService.INVALID)) {
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
}

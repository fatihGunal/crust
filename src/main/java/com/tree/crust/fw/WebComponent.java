package com.tree.crust.fw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * WebComponent
 * Here, we implement @RestController annotation
 *
 * This is paused due more important fixing in the main logig of the IoC
 */
public class WebComponent {

    public void handleSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket clientSocket = serverSocket.accept();
        BufferedReader bReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter pWriter = new PrintWriter(clientSocket.getOutputStream(), true);

        String inputLine;
        while ((inputLine = bReader.readLine()) != null) {
            pWriter.println("Connection established!");
            System.out.println("Connection established! :)");
        }
    }
}

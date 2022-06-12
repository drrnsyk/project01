package vttp2022.project01.client;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, EOFException {
         
        // read from args parameter and split
        String[] inputArray = args[0].split("[@:]"); // this will split args[0] = fred@localhost:3000 into 0-fred 1-localhost 2-3000

        // connect to the server, load folder directory, load user cart
        System.out.println("Connected to shopping cart server at " + inputArray[1] + " on " + inputArray[0] + " port " + inputArray[2]);
        Socket sock = new Socket(inputArray[1] , Integer.parseInt(inputArray[2])); // estab;ished connection, server will load/create directory folder
        OutputStream os = sock.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(inputArray[0]); // send over the user name to load cart in directory folder
        dos.flush();
        // System.out.println(inputArray[0] + " shopping cart loaded");

        InputStream is = sock.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        String cartLoaded = dis.readUTF();
        System.out.println(cartLoaded);

        // get the input and output stream - data sent by bytes hence use input and output streams and not reader
        while (true) {
            
            System.out.println();

            is = sock.getInputStream();
            dis = new DataInputStream(is);
            String commandRequest = dis.readUTF();
            System.out.println(commandRequest); // received from server to input commands
            
            Console cons = System.console();
            String inputCommand = cons.readLine();
            os = sock.getOutputStream();
            dos = new DataOutputStream(os);
            dos.writeUTF(inputCommand);
            dos.flush();

            if (inputCommand.equalsIgnoreCase("end")) {
                // System.out.printf("cart contents saved to " + inputArray[0]);
                // close the stream
                is.close();
                os.close();
                // close the socket
                sock.close();
                break;
            }

            is = sock.getInputStream();
            dis = new DataInputStream(is);
            String feedback = dis.readUTF();
            System.out.printf(feedback);
            if (inputCommand.equalsIgnoreCase("save")) {
                System.out.printf(inputArray[0]);
            }
            System.out.println();

        }
    }
}

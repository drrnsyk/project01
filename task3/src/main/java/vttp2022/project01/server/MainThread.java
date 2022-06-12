package vttp2022.project01.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;


public class MainThread implements Runnable {

    private Socket sock;
    private String args;

    public MainThread (Socket s , String a) {
        sock = s;
        args = a;
    }

    @Override
    public void run() {
        System.out.println("Starting client thread"); 

        try {
            // declaration and initialization
            ShoppingCart myCart = new ShoppingCart();

            // parameter for directory name
            String directory = args;

            // check if directory exist, if not create one
            File myFold = new File("./" + directory); // instantiate the file class  
            if(myFold.mkdir()){  
                System.out.println("Folder not found, folder is created successfully");  
            }else{  
                System.out.println("Using " + directory + " directory for persistence");
                int fileCount = myFold.list().length;
                System.out.println("There are " + fileCount + " carts in " + directory + " directory");
            }  

            // read client input to load user name
            InputStream is = sock.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            String loadInput = dis.readUTF();

            // create file class
            File myObj = new File("./" + directory + "/" + loadInput + ".txt"); // create file if not found, put it into the directory based on current + parameter destination
            if (myObj.createNewFile()) {
                System.out.println("File does not exist, new file created: " + myObj.getName());
            } 

            // read from file class, to read the existing file an copy contents over to cart list
            Reader reader = new FileReader("./" + directory + "/" + loadInput + ".txt");
            BufferedReader br = new BufferedReader(reader);

            String line = "";

            while (true) {
                line = br.readLine();
                if (line == null)
                    break;
                myCart.readFromFile(line);
                //System.out.printf("%s\n", line.toUpperCase());
            }

            reader.close();

            // write to file class
            System.out.println("Hello " + myObj.getName() + "!" + " Connection received... ");
            FileWriter myWriter =  new FileWriter("./" + directory + "/" + loadInput + ".txt"); // this overwrites, otherwise append true to prevent overwrite of the file


            // output to client on successful load
            OutputStream os = sock.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(loadInput + " shopping cart loaded");
            

            while (true) {

                os = sock.getOutputStream();
                dos = new DataOutputStream(os);
                dos.writeUTF("Please enter a command: list, add, delete, end\n");

                is = sock.getInputStream();
                dis = new DataInputStream(is);
                String input = dis.readUTF(); 
                String[] strArray = input.split(" ");

                if (strArray[0].equals("list")) {
                    myCart.list();
                    os = sock.getOutputStream();
                    dos = new DataOutputStream(os);
                    dos.writeUTF(myCart.feedback);
                    myCart.feedback = "";
                }
                else if (strArray[0].equals("add")) {
                    myCart.addItem(strArray);
                    os = sock.getOutputStream();
                    dos = new DataOutputStream(os);
                    dos.writeUTF(myCart.feedback);
                    myCart.feedback = "";
                }
                else if (strArray[0].equals("delete")) {
                    myCart.deleteItem(strArray);
                    os = sock.getOutputStream();
                    dos = new DataOutputStream(os);
                    dos.writeUTF(myCart.feedback);
                    myCart.feedback = "";
                }
                else if (strArray[0].equals("save")) {
                    myCart.save();
                    os = sock.getOutputStream();
                    dos = new DataOutputStream(os);
                    dos.writeUTF(myCart.feedback);
                    myWriter.write(myCart.writeToFile());
                    myCart.feedback = "";
                }
                else if (strArray[0].equals("end")) {
                    myCart.end();
                    //myWriter.write(myCart.writeToFile());
                    myWriter.close();
                    dos.writeUTF("end");
                    is.close();
                    os.close();
                    sock.close();
                    break;
                }
                else 
                {
                    os = sock.getOutputStream();
                    dos = new DataOutputStream(os);
                    dos.writeUTF("Please enter a valid command");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }   
    }    
}        

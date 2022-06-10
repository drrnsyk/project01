package vttp2022.project01;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException{

        // declaration and initialization
        ShoppingCart myCart = new ShoppingCart();
        String input = "list";

        // parameter for directory name
        String directory = args[0];

        // check if directory exist, if not create one
        File myFold = new File("./" + directory); // instantiate the file class  
        if(myFold.mkdir()){  
           System.out.println("Folder not found, folder is created successfully");  
        }else{  
           System.out.println("Using " + directory + " directory for persistence");
           int fileCount = myFold.list().length;
           System.out.println("There are " + fileCount + " carts in " + directory + " directory");
        }  

        // user input to load user name
        Console cons = System.console();
        String loadInput = "";
        loadInput = cons.readLine("Enter user name to load: ");
        System.out.println();
        
        // create file class
        File myObj = new File("./" + directory + "/" + loadInput + ".txt"); // create file if not found, put it into the directory based on current + parameter destination
        if (myObj.createNewFile()) {
            System.out.println("File does not exist, new file created: " + myObj.getName());
        } 

        // read from file class
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
        System.out.println("Hello " + myObj.getName());
        FileWriter myWriter =  new FileWriter("./" + directory + "/" + loadInput + ".txt"); // this overwrites, otherwise append true to prevent overwrite of the file

        // read from console
        // Console cons = System.console();
        System.out.println("Welcome to your shopping cart");

        while (true) {
            input = cons.readLine("Please enter a command: list, add, delete, end\n");
            String[] strArray = input.split(" ");

            if (strArray[0].equals("list")) {
                myCart.list();
            }
            else if (strArray[0].equals("add")) {
                myCart.addItem(strArray);
            }
            else if (strArray[0].equals("delete")) {
                myCart.deleteItem(strArray);
            }
            else if (strArray[0].equals("end")) {
                myCart.end();
                myWriter.write(myCart.writeToFile());
                myWriter.close();
                break;
            }
        }
    }        
}

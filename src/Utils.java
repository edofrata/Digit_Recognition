import java.io.*;
import java.util.Scanner;

public class Utils {
  
    public int[][] train_dataset = new int[2810][64]; //training dataset
    public int[] train_labels = new int[2810];//training labels

    public int[][] test_dataset = new int[2810][64];//testing dataset
    public int[] test_labels = new int[2810];//testing labels

    public boolean the_end = false; //in order to make the software continue

    // it reads in tthe data
    public void Reader(String file, int[][] dataset, int[] labels){

        try {

                // string which keeps track of the current line
                String current_line;
                // Buffer reader for the txt file
                BufferedReader data_reader = new BufferedReader(new FileReader("Datasets/" + file.trim()));
   
                int row = 0; //row of the matrix
                int column = 0; //column of the matrix
                while ((current_line = data_reader.readLine()) != null) {
        
                    // need to trim what it reads, if there is any space,  and store as object into the arraylist
                    String[] split_line = current_line.trim().split(",");
                    for(int line =0; line < split_line.length - 1; line++, column++){
                        dataset[row][column] = Integer.parseInt(split_line[line]);
                    }
                        labels[row] = Integer.parseInt(split_line[split_line.length-1]);
                        row++; column = 0;
                        
                }
                // closing the buffer reader
                data_reader.close();

        } catch (IOException e) {

            System.out.println("No file found with that name");
        }

    }

// prints out the array 2d and a single array for debugging purposes
    public void print_array(int[][] array_2d, int[] array_1d){

        for(int i = 0; i < array_2d.length; i++){
            System.out.print(" IMAGE NUMBER " + i + " [");
            for(int j = 0; j < array_2d[i].length;j++ ){

                System.out.print(array_2d[i][j] + " ");
            }
            System.out.print( "] THE LABEL IS --> " + array_1d[i]);
            System.out.println();
        }

    }
// function that starts the software and keeps it running
    public void start(){

        System.out.println("\n-----Welcome on Digit Recognition-----");
        System.out.println("\nTo Train The Neural Network -----> 1");
        System.out.println("To Test The Neural Network  -----> 2");
        System.out.println("To Exit The Software        -----> 3");

        Scanner input_choice = new Scanner(System.in);
        System.out.print("Please Insert Your Choice: ");

        String usr_choice = input_choice.nextLine();

        switch(usr_choice){

            case "1":
                 // filling up the train dataset
                Reader("cw2DataSet1.csv", train_dataset, train_labels);
                print_array( train_dataset, train_labels);
                break;
            case "2":
                 // filling up the test dataset
                Reader("cw2DataSet2.csv", test_dataset, test_labels);
                print_array(test_dataset, test_labels);
                break;
            case "3": 
                System.out.println("\nHope You Enjoyed! GoodBye!");
                the_end = true;
                input_choice.close();
                System.exit(1);
            
            default:
                System.out.println("\nWRONG INPUT! PLEASE TRY AGAIN!");
                break;

        }
    
       
    }
}

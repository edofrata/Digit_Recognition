// import java.io.*;
import java.util.Scanner;

public class Utils {
  
    // protected static Sample[] train_dataset = new Sample[2810]; //training dataset
    // protected static Sample[] test_dataset = new Sample[2810];//testing dataset
    protected boolean the_end = false; //in order to make the software continue
  
    // // it reads in the data
    // public void Reader(String file, Sample[] dataset){

    //     try {

    //             // string which keeps track of the current line
    //             String current_line;
               
    //             // Buffer reader for the txt file
    //             BufferedReader data_reader = new BufferedReader(new FileReader("Datasets/" + file.trim()));
                
    //             int row = 0; //row of the matrix
    //             while ((current_line = data_reader.readLine()) != null) {
    //                 double[] dataset_tmp = new double[64];
    //                 // need to trim what it reads, if there is any space,  and store as object into the arraylist
    //                 String[] split_line = current_line.trim().split(",");
    //                 for(int line =0; line < split_line.length - 1; line++){
    //                     dataset_tmp[line] = Integer.parseInt(split_line[line]);

    //                 }
                    
    //                  dataset[row++] = new Sample(dataset_tmp, Integer.parseInt(split_line[split_line.length-1]));
        
    //             }
    //             // closing the buffer reader
    //             data_reader.close();

    //     } catch (IOException e) {

    //         System.out.println("No file found with that name");
    //     }

    // }

// prints out the array 2d and a single array for debugging purposes
    public void print_array(Sample[] array){

        for(int sample = 0; sample< array.length; sample++){
            System.out.print("\nIMAGE NUMBER " + sample + " [  ");
            for(int pixel_row = 0; pixel_row < array[sample].getImage2D().length; pixel_row++){
                for(int pixel_col = 0; pixel_col < array[sample].getImage2D().length; pixel_col++){
                    System.out.print(array[sample].getImage2D()[pixel_row][pixel_col] + " ");
                }
                System.out.println();
            }
            System.out.println(  "]" + " " + array[sample].getLabel());            
        }
  
    }
// function that starts the software and keeps it running
    public void start(){

        System.out.println("\n-----Welcome on Digit Recognition-----");
        System.out.println("\nTo Train The Neural Network -----> 1");
        System.out.println("To Exit The Software        -----> 2");

        Scanner input_choice = new Scanner(System.in);
        System.out.print("Please Insert Your Choice: ");

        String usr_choice = input_choice.nextLine();

        switch(usr_choice){

            case "1":
                CNN.control_settings();
                break;
            case "2": 
                System.out.println("\nHope You Enjoyed! GoodBye!");
                the_end = true;
                input_choice.close();
                System.exit(1);
            
            default:
                System.out.println("\nWRONG INPUT! PLEASE TRY AGAIN!");
                break;

        }
    
    }

    public static double[][] from_1d_to_2d(double[] array_1d, double[][] array_2d){
        
        int sqrt = (int) Math.sqrt(array_1d.length);
        int counter = 0;

        for(int row = 0; row < sqrt; row++){
            for(int column = 0; column < sqrt; column++){
                array_2d[row][column] = array_1d[counter++];
            }
        }
        
        return array_2d;
    }


}

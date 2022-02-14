import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
  
    public Sample[] train_dataset = new Sample[2810]; //training dataset
    public Sample[] test_dataset = new Sample[2810];//testing dataset

  
    public boolean the_end = false; //in order to make the software continue

    // it reads in tthe data
    public void Reader(String file, Sample[] dataset){

        try {

                // string which keeps track of the current line
                String current_line;
               
                // Buffer reader for the txt file
                BufferedReader data_reader = new BufferedReader(new FileReader("Datasets/" + file.trim()));
                
                int row = 0; //row of the matrix
                while ((current_line = data_reader.readLine()) != null) {
                    int[] dataset_tmp = new int[64];
                    // need to trim what it reads, if there is any space,  and store as object into the arraylist
                    String[] split_line = current_line.trim().split(",");
                    for(int line =0; line < split_line.length - 1; line++){
                        dataset_tmp[line] = Integer.parseInt(split_line[line]);
                   
                    }

                     dataset[row++] = new Sample(dataset_tmp, Integer.parseInt(split_line[split_line.length-1]), new ArrayList<Double>());
            


                }
                // closing the buffer reader
                data_reader.close();

        } catch (IOException e) {

            System.out.println("No file found with that name");
        }

    }

// prints out the array 2d and a single array for debugging purposes
    public void print_array(Sample[] array){

        for(int sample = 0; sample< array.length; sample++){
            System.out.print("\nIMAGE NUMBER " + sample + " [");
            for(int pixel = 0; pixel < array[sample].getImage().length; pixel++){
                System.out.print(array[sample].getImage()[pixel] + " ");
            }
            System.out.println(  "]" + " " + array[sample].getLabel());
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
                Reader("cw2DataSet1.csv", train_dataset);
                print_array(train_dataset);
                break;
            case "2":
                 // filling up the test dataset
                Reader("cw2DataSet2.csv", test_dataset);
                print_array(test_dataset);
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
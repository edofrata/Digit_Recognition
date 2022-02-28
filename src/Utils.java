// import java.io.*;
import java.util.Scanner;

public class Utils {

    protected boolean the_end = false; //in order to make the software continue
  
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

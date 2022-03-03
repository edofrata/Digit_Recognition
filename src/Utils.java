import java.util.Scanner;

public class Utils {

    protected boolean the_end = false; //in order to make the software continue
  
// prints out the array 2d and a single array for debugging purposes
    public void print_array(Sample[] array){
// looping through the array in order to enter all the objects inside
        for(int sample = 0; sample< array.length; sample++){
            System.out.print("\nIMAGE NUMBER " + sample + " [  ");
            // looping inside the object and in the row of the  array
            for(int pixel_row = 0; pixel_row < array[sample].getImage2D().length; pixel_row++){
                // looping thorugh the column of the array
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
// printing the welcome page
        System.out.println("\n-----Welcome on Digit Recognition-----");
        System.out.println("\nTo Train The Neural Network -----> 1");
        System.out.println("To Exit The Software        -----> 2");

        Scanner input_choice = new Scanner(System.in);
        System.out.print("Please Insert Your Choice: ");

        String usr_choice = input_choice.nextLine(); //getting the input of the user
// switch statement in order to classify the user input choice
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

// method which transforms a 1D array to a 2D array
    public static double[][] from_1d_to_2d(double[] array_1d, double[][] array_2d){
        
        int sqrt = (int) Math.sqrt(array_1d.length); //making a perfect square of the length of 1D array
        int counter = 0;//counter for the array1D
    
        // looping thorugh the array 2D and assigning the value
        for(int row = 0; row < sqrt; row++){
            for(int column = 0; column < sqrt; column++){
                array_2d[row][column] = array_1d[counter++];
            }
        }
        
        return array_2d;
    }

    // progress bar class
     public static class ProgressBar {
            
        private int counter; //counter for the bar
        public ProgressBar(){ counter = -1; } //counter starting point
        public void progress_bar(int size, int index){ progress_bar(size,index, 20, 100); }// progress bar constructor
        public void progress_bar(int size, int index,  int barLength, int updates){
            // statement for the bar when it needs to stop
            if (size > 0 && ++index <= size){ 
                int barMax = index * updates / size;
                    int actual_point = (short)((float)barLength / updates * barMax);
                // if the actual point is not the same as the counter it will not enter the statement
                if(actual_point != counter){
                    // counter which determines where to print
                    counter = actual_point; 
                    String arrow_1 = "=", dotted = "░", full_part = "", void_part = ">"; //declaring the style of the bar
                    int fueling_bar = (barLength-actual_point); //in order to fuel the progress bart

                    // if the size provided is greater than the index it is at, means that it still is progressing
                    if( size > index){
                        for(int point =0; point < actual_point;point++) { full_part += arrow_1; }
                        for(long bar=0; bar < fueling_bar; bar++){ void_part += dotted; }
                        
                        // printing the progress bar
                        String progress = " [ " + full_part + void_part + " ] " + index  + "/" + (size+1) + " Samples"  +"\r";
                        System.out.print( "\r" + progress + "\r");
        
                    }else {
                        counter = -1;
                        System.out.print( "\33[2K" + "\r"); // it deletes the line in order to make it progress
                    }
                }
            }
        }
    }

}

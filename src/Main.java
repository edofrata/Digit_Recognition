public class Main {
    
    public static void main(String[] args){
        //creating a new Util object
        Utils util = new Utils();
      
        // while loop to make the software carry on until the user presses "3"
        while(!util.the_end){
            util.start();
        }
        
        // CNN cnn = new CNN();
        // cnn.Weights_Matrix(new int[32][9]);
        // util.print_array(cnn.weights_conv2d);
    }
 
}

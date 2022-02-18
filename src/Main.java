public class Main {
    
    public static void main(String[] args){
        //creating a new Util object
        Utils util = new Utils();
      
        // while loop to make the software carry on until the user presses "3"
        while(!util.the_end){
            util.start();
        } 
        
        // Neuron neuron = new Neuron(2, 3,3);
        // neuron.fill_matrix(3, 3, 2);
        // for(double[] c : neuron.getMatrix()){
        //     for(double ciao : c){
        //         System.out.print(ciao + " ");
        //     }
        //     System.out.println();
        // }
    }
 
}

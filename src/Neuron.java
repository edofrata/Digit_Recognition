public class Neuron {

    // private double[][]   sample_input;
    private final double[][][] MATRIX_WEIGHT;   //weights of matrix (kernel)
    private double[][][] gradients;             //where all the gradients will be kept 
    private final Node[][] OUTPUTS;             //outputs given from linear transformation
    private final int OUTPUT_SIZE;             //number of inputs    
    private final double[][] BIAS;             // bias of the nuron
    private double[][] gradient_bias;          //gradient of bias

    
    public Neuron(int number_channels, int Y, int X, int number_inputs){
        int square_input = (int)Math.sqrt(number_inputs);
        this.OUTPUT_SIZE = (int) (square_input - Math.sqrt(X*Y)) + 1;   //calculating the output feature map
        
        this.MATRIX_WEIGHT   = new double[number_channels][Y][X];               
        this.gradients       = new double[number_channels][Y][X];
        this.OUTPUTS         = outputs_init(OUTPUT_SIZE, OUTPUT_SIZE);

        // bias
        this.BIAS            = new double[OUTPUT_SIZE][OUTPUT_SIZE];
        this.gradient_bias   = new double[OUTPUT_SIZE][OUTPUT_SIZE];
    }

// init neurons outputs
    private Node[][] outputs_init(final int Y, final int X){
       final Node[][] TMP_NODE = new Node[Y][X];

       for(int node_y = 0; node_y < TMP_NODE.length; node_y++){
        for(int node_x = 0; node_x < TMP_NODE[node_y].length; node_x++){
            TMP_NODE[node_y][node_x] = new Node();
        }
       }
        return TMP_NODE;
    }

    // -------------- GETTER NEURONS --------------------
// it retrieves the matrix weight
    public double getMatrixWeight(final int CHANNEL, final int KERNEL_Y, final int KERNEL_X){
        return this.MATRIX_WEIGHT[CHANNEL][KERNEL_Y][KERNEL_X];
    }

// it retrieves the bias
    public double getBias(final int INDEX_Y, final int INDEX_X){
        return this.BIAS[INDEX_Y][INDEX_X];
    }


// it retrieves the matrix outputs
    public Node[][] getOutputs(){
        return this.OUTPUTS;
    }


       // -------------- SETTER --------------------
// it sets the matrix outputs with new values
    public void setOutputs(int row, int column, final double VALUE){
         this.OUTPUTS[row][column].setNode_Value(VALUE);
    }

// it sets the matrix gradients with new values
    public void setGradients(int filter_index, int row, int column, double value){
        this.gradients[filter_index][row][column]= value;
    }
    

// it sets the 3d array of weight
    public void setWeights(final int CHANNEL_INDEX, final int Y, final int X, final double VALUE){
        this.MATRIX_WEIGHT[CHANNEL_INDEX][Y][X] = VALUE;
    }


// updating the weights
    public void weightsUpdate(final int BATCH, final double LEARNING_RATE){

        for(int channel = 0; channel < this.MATRIX_WEIGHT.length; channel++ ){
            for(int kernel_y = 0; kernel_y < this.MATRIX_WEIGHT[0].length; kernel_y++ ){
                for(int kernel_x = 0; kernel_x < this.MATRIX_WEIGHT[0][0].length; kernel_x++ ){
                    this.MATRIX_WEIGHT[channel][kernel_y][kernel_x] -= LEARNING_RATE * (this.gradients[channel][kernel_y][kernel_x] / (double)BATCH);
                }
            }
        }

// resetting the gradients
        this.gradients = new double[this.MATRIX_WEIGHT.length][this.MATRIX_WEIGHT[0].length][this.MATRIX_WEIGHT[0][0].length];

    }

// updating the weights
    public void biasUpdate(final int BATCH, final double LEARNING_RATE){
        
            for(int bias_y = 0; bias_y < this.BIAS.length; bias_y++ ){
                for(int bias_x = 0; bias_x < this.BIAS[0].length; bias_x++ ){
                    this.BIAS[bias_y][bias_x] -= LEARNING_RATE * (this.gradient_bias[bias_y][bias_x] / (double)BATCH);
                }
            }

// resetting the bias
        this.gradient_bias = new double[this.BIAS.length][this.BIAS[0].length];
    }


           // -------------- SUM --------------------
// it sets the matrix gradients with new values
    public void sumGradients(int filter_index, int row, int column, double value){
        this.gradients[filter_index][row][column] += value;
    }

// it sets the new value of the bias
    public void sumBiasGradient(final int INDEX_Y, final int INDEX_X,  final double VALUE){
        this.gradient_bias[INDEX_Y][INDEX_X] += VALUE;
    } 
}

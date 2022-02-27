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
        // this.sample_input = new double[square_input][square_input];
        
        this.OUTPUT_SIZE = (int) (square_input - Math.sqrt(X*Y)) + 1;   //calculating the output feature map
        
        this.MATRIX_WEIGHT   = new double[number_channels][Y][X];               
        this.gradients       = new double[number_channels][Y][X];
        this.OUTPUTS         = outputs_init(OUTPUT_SIZE, OUTPUT_SIZE);

        // bias
        this.BIAS            = new double[OUTPUT_SIZE][OUTPUT_SIZE];
        this.gradient_bias   = new double[OUTPUT_SIZE][OUTPUT_SIZE];
    }

    // initializing class for NODE
    public class Node{
        
        private double output_value;            // output of pixel
        private double forward_linearSum;    //output of the linear transformation in forward prop 
        private double back_linearSum;       //output of the linear transformation in back prop
        private double chain_rule;
        private double partial_derivative;
        private double derivative;

       public Node(){}

        // ------- SETTER METHODS --------
    
        // summing up in order to set output
        public void setNode_Value(final double VALUE){
            this.output_value       = VALUE;
            this.back_linearSum     = this.forward_linearSum;
            this.forward_linearSum  = 0;
        }
        // summing up in order to set chain rule
        public void addTo_Chain(final double VALUE){
            this.chain_rule += VALUE;
        }
        // summing up in order to set linear output
        public void addTo_Linear(final double VALUE){
            this.forward_linearSum += VALUE;
        }
        // summing up setin order to partial derivative
        public void setPartial_Derivative(final double VALUE){
            this.partial_derivative = VALUE;
            this.back_linearSum = 0;
        }
        // summing up in order to set the derivative
        public void set_DerivativeSum(){
            this.derivative = this.partial_derivative * this.chain_rule;
            this.chain_rule = 0;
        }
           
        // ------- GETTER METHODS --------
    
        // get output
        public double getOutputNode(){
            return this.output_value;
        }
        //  get  back linear output
        public double getBack_Linear(){
            return this.back_linearSum;
        }
        //  get forward linear output
        public double getForward_Linear(){
            return this.forward_linearSum;
        }
        // get the derivative
        public double getDerivative(){
            return this.derivative;
        }
    }


// init neurons outputs
    private Neuron.Node[][] outputs_init(final int Y, final int X){
       final Neuron.Node[][] TMP_NODE = new Neuron.Node[Y][X];

       for(int node_y = 0; node_y < TMP_NODE.length; node_y++){
        for(int node_x = 0; node_x < TMP_NODE[node_y].length; node_x++){
            TMP_NODE[node_y][node_x] = new Neuron.Node();
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
// // retrieving number of inputs
//     public int getNum_Inputs(){
//         return this.OUTPUT_SIZE;
//     }
// it retrieves the gradients
    // public double[][][] getGradients(){
    //     return this.gradients;
    // }

// it retrieves the matrix outputs
    public Node[][] getOutputs(){
        return this.OUTPUTS;
    }
// // retrieves bias gradient
//     public double getBiasGradient(final int INDEX_Y, final int INDEX_X){
//         return this.gradient_bias[INDEX_Y][INDEX_X];
//     }

// //retrieves the sample input given
//     public double[][] getInputImage(){
//         return this.sample_input;
//     }

       // -------------- SETTER --------------------
// it sets the matrix outputs with new values
    public void setOutputs(int row, int column, final double VALUE){
         this.OUTPUTS[row][column].setNode_Value(VALUE);
    }

// it sets the matrix gradients with new values
    public void setGradients(int filter_index, int row, int column, double value){
        this.gradients[filter_index][row][column]= value;
    }
    
// // it sets the new value of the bias
//     public void setBias(final int INDEX_Y, final int INDEX_X, final double VALUE){
//         this.BIAS[INDEX_Y][INDEX_X ]= VALUE;
//     }


// // it sets the new value of the bias
//     public void setBiasGradient(final int INDEX_Y, final int INDEX_X, double value){
//         this.gradient_bias[INDEX_Y][INDEX_X] = value;
//     }


// it sets the 3d array of weight
    public void setWeights(final int CHANNEL_INDEX, final int Y, final int X, final double VALUE){
        this.MATRIX_WEIGHT[CHANNEL_INDEX][Y][X] = VALUE;
    }



    
// //sets the sample input given
//     public double[][] setInputImage(double[][] image){
//         return this.sample_input = image;
//     }

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

public class Neuron {

    private double[][]   sample_input;
    private double[][][] matrix_weight;        //weights of matrix (kernel)
    private double[][][] gradients;           //where all the gradients will be kept 
    private double[][] outputs;              //outputs given from linear transformation
    private int number_inputs;
    private double bias;                  // bias of the nuron
    private double gradient_bias;        //gradient of bias

    public Neuron(int number_channels, int Y, int X, int number_inputs){
        int square_input = (int)Math.sqrt(number_inputs);
        this.sample_input = new double[square_input][square_input];
        
        this.number_inputs = (int) (square_input - Math.sqrt(X*Y)) + 1;   //calculating the output feature map
        
        this.matrix_weight   = new double[number_channels][Y][X];               
        this.gradients       = new double[number_channels][Y][X];
        this.outputs         = new double[number_inputs][number_inputs];

        // bias
        this.bias            = Math.random() * 1;
        this.gradient_bias   = 0;
    }

    // -------------- GETTER --------------------
// it retrieves the matrix weight
    public double[][][] getMatrix(){
        return this.matrix_weight;
    }
    
// it retrieves the bias
    public double getBias(){
        return this.bias;
    }
// retrieving number of inputs
    public int getNum_Inputs(){
        return this.number_inputs;
    }
// it retrieves the gradients
    public double[][][] getGradients(){
        return this.gradients;
    }

// it retrieves the matrix outputs
    public double[][] getOutputs(){
        return this.outputs;
    }
// retrieves bias gradient
    public double getBiasGradient(){
        return this.gradient_bias;
    }
//retrieves the sample input given
    public double[][] getInputImage(){
        return this.sample_input;
    }

       // -------------- SETTER --------------------
// it sets the matrix outputs with new values
    public void setOutputs(int row,int column, double value){
         this.outputs[row][column] = value;
    }

// it sets the matrix gradients with new values
    public void setGradients(int filter_index, int row, int column, double value){
        this.gradients[filter_index][row][column]= value;
    }
// it sets the new value of the bias
    public void setBias(double value){
        this.bias = value;
    }
// it sets the new value of the bias
    public void setBiasGradient(double value){
        this.gradient_bias = value;
    }

// it sets the 3d array of weight
    public void setWeights(final int CHANNEL_INDEX, final int Y, final int X, final double VALUE){
        this.matrix_weight[CHANNEL_INDEX][Y][X] = VALUE;
    }

//sets the sample input given
    public double[][] setInputImage(double[][] image){
        return this.sample_input = image;
    }

           // -------------- SUM --------------------
// it sets the matrix gradients with new values
    public void sumGradients(int filter_index, int row, int column, double value){
        this.gradients[filter_index][row][column] += value;
    }

// it sets the new value of the bias
    public void sumBiasGradient(double value){
        this.gradient_bias += value;
    }
  
}

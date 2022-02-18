public class Neuron {

    private double[][] matrix_weight;
    private double[] gradients;
    private double[][] outputs;
    private double bias;

    public Neuron(int number_inputs, int X, int Y){

        this.matrix_weight = new double[number_inputs][X * Y];
        this.gradients = new double[X * Y];
        this.outputs = new double[number_inputs][X*Y];
        this.bias = Math.random() * 1;
    }

    public double[][] fill_matrix( int X, int Y, int number_filters){
        return Weights_Matrix(X, Y, number_filters);
    }
// it retrieves the matrix weight
    public double[][] getMatrix(){
        return matrix_weight;
    }
// it retrieves the bias
    public double getBias(){
        return bias;
    }
// it sets the new value of the bias
    public void setBias(double value){
        bias = value;
    }
// it retrieves the gradients
    public double[] getGradients(){
        return gradients;
    }

// it retrieves the matrix outputs
    public double[][] getOutputs(){
        return outputs;
    }

// it sets the matrix outputs with new values
    public void setOutputs(int row,int column, double value){
         outputs[row][column] = value;
    }

// it sets the matrix gradients with new values
    public void setGradients(int index, double value){
        gradients[index] = value;
    }

// it sets the matrix gradients with new values
    public void sumGradients(int index, double value){
        gradients[index] += value;
    }

    /**
	 * 
	 * @param inputs which is yhe number of inputs which will receive
     * @param X which will be the X length of the kernel size
     * @param Y which will be the Y length of the kernel size
     * @param number_filters which will be the number of the filters desired
	 * @return double
	 */
    // initializing the matrix of weights
    public double[][] Weights_Matrix(int X, int Y, int number_filters) {
    // Relu Weights Inizialization
        Sample sample = new Sample();
        double weight_formula = Activation.Relu.weight_inizialization(sample.getImage().length);

    // HeNormal Weight initialization
        for(int weight_row = 0; weight_row < number_filters; weight_row++){
            for(int weight_col = 0; weight_col < (X * Y); weight_col++){

                matrix_weight[weight_row][weight_col] = Math.random() * weight_formula;
            }
        }
        return matrix_weight;
    }
}

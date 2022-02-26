public class Layers {

    private final Neuron[] INPUTS;          //number of inputs which the neurons will receive
    private final Neuron[] NEURONS;         //number of total neurons
    private final int KERNEL_Y;             //Y size of the kernel
    private final int KERNEL_X;             //X size of the kernel
    private final Activation ACTIVATION;    //Activation function which will be passed

//     Neuron[] neurons;

// // layer constructor
//     public Layers(int num_neurons) {
//         this.neurons = new Neuron[num_neurons];
//     };

// // retrieves the neuron array
//     public Neuron[] getNeurons(){
//         return this.neurons;
//     }


    public Layers( final int NODES, final int KERNEL_Y, final int KERNEL_X, final Activation ACTIVATION, final Sample SAMPLE){
        this(NODES, KERNEL_Y, KERNEL_X, ACTIVATION, new Neuron(1, SAMPLE.getImage2D().length, SAMPLE.getImage2D()[0].length, SAMPLE.getImage1D().length));
        sample_loader(SAMPLE);
    }
    public Layers(final int NODES, final int KERNEL_Y, final int KERNEL_X, final Activation ACTIVATION, final Neuron ... INPUT_NEURONS){
        this.INPUTS  = INPUT_NEURONS;
        this.KERNEL_Y = KERNEL_Y;
        this.KERNEL_X = KERNEL_X;
        this.NEURONS = neurons_init(NODES);
        this.ACTIVATION = ACTIVATION;
    }


    public Layers(final int NODES, final Activation ACTIVATION, final Sample SAMPLE){
        this( NODES, SAMPLE.getImage2D().length, SAMPLE.getImage2D()[0].length , ACTIVATION, SAMPLE);
    }
    public Layers(final int NODES, final Activation ACTIVATION, final Neuron ... INPUT_NEURONS){
        this(NODES, INPUT_NEURONS[0].getOutputs().length, INPUT_NEURONS[0].getOutputs()[0].length , ACTIVATION, INPUT_NEURONS);
    }

// ----------- ACTIVATION FUNCTIONS --------------------
    public static enum Activation {
        
        LINEAR{
            public void function(){}
            public void derivative(){}
            public double random_weight(final double VALUE){ return Activations.Linear.weight_inizialization((int)VALUE);}

        }, BINARY{
            public void function(){}
            public void derivative(){}
            public double random_weight(final double VALUE){ return Activations.Binary.weight_inizialization((int)VALUE);}
        }, SIGMOID{
            public void function(){}
            public void derivative(){}
            public double random_weight(final double VALUE){ return Activations.Sigmoid.weight_inizialization((int)VALUE);}
        }, TANH{
            public void function(){}
            public void derivative(){}
            public double random_weight(final double VALUE){ return Activations.Tanh.weight_inizialization((int)VALUE);}
        }, SWISH{
            public void function(){}
            public void derivative(){}
            public double random_weight(final double VALUE){ return Activations.Swish.weight_inizialization((int)VALUE);}
        }, RELU{
            public void function(){}
            public void derivative(){}
            public double random_weight(final double VALUE){ return Activations.Relu.weight_inizialization((int)VALUE);}
        }, LRELU{
            public void function(){}
            public void derivative(){}
            public double random_weight(final double VALUE){ return Activations.Lrelu.weight_inizialization((int)VALUE);}
        }, GELU{
            public void function(){}
            public void derivative(){}
            public double random_weight(final double VALUE){ return Activations.Gelu.weight_inizialization((int)VALUE);}
        }, SELU{
            public void function(){}
            public void derivative(){}
            public double random_weight(final double VALUE){ return Activations.Selu.weight_inizialization((int)VALUE);}
        }, PRELU{
            public void function(){}
            public void derivative(){}
            public double random_weight(final double VALUE){ return Activations.Prelu.weight_inizialization((int)VALUE);}
        }, ELU{
            public void function(){}
            public void derivative(){}
            public double random_weight(final double VALUE){ return Activations.Elu.weight_inizialization((int)VALUE);}
        }, SOFTPLUS{
            public void function(){}
            public void derivative(){}
            public double random_weight(final double VALUE){ return Activations.Softplus.weight_inizialization((int)VALUE);}
        }, SOFTMAX{
            public void function(){}
            public void derivative(){}
            public double random_weight(final double VALUE){ return Activations.Softmax.weight_inizialization((int)VALUE);}
        };


            public abstract void function();
            public abstract void derivative();
            public abstract double random_weight(final double VALUE);

    }

// class of Layer initialization
    public static class Layer_init {
       
        public final String LAYER;                  //Understanding if it is a convolutional layer or Dense layer
        public final int NODES;                     //Neurons that will be initialized
        public final int KERNEL_Y;                  //Y kernel size
        public final int KERNEL_X;                  //X kernel size
        public final Layers.Activation ACTIVATION;  //Activation Layer
 
// Layer init contructor which will be the first one to be initializied for Conv2d
        public Layer_init(final int NODES, final int KERNEL_Y, final int KERNEL_X, final Layers.Activation ACTIVATION){

            this.LAYER = (KERNEL_Y * KERNEL_X) == 0 ? "DENSE" : "CONV2D";
            this.NODES = NODES;   
            this.KERNEL_Y = KERNEL_Y;
            this.KERNEL_X = KERNEL_X;
            this.ACTIVATION = ACTIVATION;

        }
// Layer init constructor for Dense layer
        public Layer_init(final int NODES, final Layers.Activation ACTIVATION){
            this(NODES, 0, 0, ACTIVATION); //it calls the upper constructor method
        }

    }

// Convolutional layer constructor
    public static Layer_init Conv2d(final int NODES, final int KERNEL_Y, final int KERNEL_X, final Layers.Activation ACTIVATION){
        return new Layer_init(NODES, KERNEL_Y,KERNEL_X, ACTIVATION);
    }
// Dense Layer constrctor
    public static Layer_init Dense(final int NODES, final Layers.Activation ACTIVATION){
        return new Layer_init(NODES,ACTIVATION);
    }

// initializing all neurons that the layer needs
    public Neuron[] neurons_init(final int NEURONS_AMOUNT){
        final Neuron[] NEURONS_TMP = new Neuron[NEURONS_AMOUNT];

        for(int neuron = 0; neuron < NEURONS_AMOUNT; neuron++){
            Neuron perceptron = new Neuron(this.INPUTS.length, this.KERNEL_Y , this.KERNEL_X, (this.INPUTS[0].getOutputs().length * this.INPUTS[0].getOutputs()[0].length) );
            Weights_Matrix(perceptron);
            NEURONS_TMP[neuron] = perceptron;
        }
        return NEURONS_TMP;
    }

// function that creates the first layer
    public void sample_loader(final Sample SAMPLE){
        for(int output_y= 0; output_y < INPUTS[0].getOutputs().length; output_y++){
            for(int output_x = 0; output_x < INPUTS[0].getOutputs()[0].length; output_x++){
                this.INPUTS[0].setOutputs(output_y, output_x, SAMPLE.getImage2D()[output_y][output_x]);
            }
        } 
    }


  /**
	 * 
     * @param number_filters which will be the number of the filters desired
     * @param X which will be the X length of the kernel size
     * @param Y which will be the Y length of the kernel size
	 * @return void
	 */
    // initializing the matrix of weights
    public void Weights_Matrix(final Neuron NEURON) {
        // Relu Weights Inizialization

            double weight_formula = this.INPUTS.length * this.INPUTS[0].getOutputs().length * this.INPUTS[0].getOutputs()[0].length;
    
            // HeNormal Weight initialization
            for(int n_filters = 0; n_filters < this.INPUTS.length ; n_filters++){
                for(int weight_row = 0; weight_row < this.KERNEL_Y; weight_row++){
                    for(int weight_col = 0; weight_col < this.KERNEL_X; weight_col++){
                        NEURON.setWeights(n_filters, weight_row, weight_col, ACTIVATION.random_weight(weight_formula));
                    }
                }
    
            }
        }
// gets the outputs of the neurons
    public Neuron[] getOutputs(){
        return this.NEURONS;
    }

// // method that applies convoluttional to the input image
//       protected void Conv2d(int neurons, int n_InputsInNeuron, int X, int Y, double[] sample) {
//         if(counter_neuron < 2){ AddLayer(neurons, n_InputsInNeuron, X, Y, sample);}
    
//         //  works out the number of times of loop of feature maps
//         int n_product = (int) (Math.sqrt(sample.length) - Math.sqrt(X*Y));
//         double sum = 0; //the sum of linear transformation per pixel
//         counter_layer =  counter_layer > 1 ? 0 : counter_layer; // after the  all Conv2d for one sample it will become zero
//     // repeating the loop for all the neurons
//             for(int neuron = 0; neuron < neurons; neuron++){
                
//                 // looping though into the matrix of weights in a Neuron
//                 for(int n_matrix = 0; n_matrix < n_InputsInNeuron; n_matrix++){

//                     //repeating loop depending on how many times the feature maps
//                     //will take over the input image E.g sample(8x8),FMaps(3 X 3)
//                     //8 - 3 = 5 <--- which is how many it will happen (5 x 5)
//                     for(int n_sumRow = 0; n_sumRow <= n_product; n_sumRow++ ){
//                         for(int n_sumCol = 0; n_sumCol <= n_product; n_sumCol++){
//                             // looping inside the kernel sliding into the image
//                             for(int pixel_row = 0; pixel_row < X; pixel_row++){
//                                 for(int pixel_col =0; pixel_col < Y; pixel_col++){

//                                     //linear transformation
//                                     // sum +=  

//                                 }
//                             }
//                             // adding the bias to the sum
//                             sum+= layer_container[counter_layer].getNeurons()[neuron].getBias();
//                             // adding the sum to the output array inside the neuron
//                             layer_container[counter_layer].getNeurons()[neuron].getOutputs()[n_matrix][n_sumRow][n_sumCol] = sum;
//                         }
//                     }

//                 }
//             }
//             counter_neuron++; //keeping count of the conv2d
//             counter_layer++; //keeping count of the layer
//             return;
//     }

    // feedforward




    //backpropagation




    //update
}



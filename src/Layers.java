public class Layers {

    private final Neuron[] INPUTS;                  //number of inputs which the neurons will receive
    private final Neuron[] NEURONS;                 //number of total neurons
    private final int KERNEL_Y;                     //Y size of the kernel
    private final int KERNEL_X;                     //X size of the kernel
    private final Activation ACTIVATION;            //Activation function which will be passed
    private final Neuron.Node[] OUTPUT_NODES;       //output nodes relation for back prop
    private final Neuron.Node[][][][] NEURAL_LINK;  //bounds between neurons


//     Neuron[] neurons;

// // layer constructor
//     public Layers(int num_neurons) {
//         this.neurons = new Neuron[num_neurons];
//     };

// // retrieves the neuron array
//     public Neuron[] getNeurons(){
//         return this.neurons;
//     }

// layers for Convolutional
    public Layers(final int NEURONS, final int KERNEL_Y, final int KERNEL_X, final Activation ACTIVATION, final Sample SAMPLE){
        this(NEURONS, KERNEL_Y, KERNEL_X, ACTIVATION, new Neuron(1, 1, 1, SAMPLE.getImage1D().length));
        sample_loader(SAMPLE);
    }
    public Layers(final int NEURONS, final int KERNEL_Y, final int KERNEL_X, final Activation ACTIVATION, final Neuron ... INPUT_NEURONS){
        this.INPUTS         = INPUT_NEURONS;
        this.KERNEL_Y       = KERNEL_Y;
        this.KERNEL_X       = KERNEL_X;
        this.ACTIVATION     = ACTIVATION;
        this.NEURONS        = neurons_init(NEURONS);
        this.OUTPUT_NODES   = outputNodes_init();
        this.NEURAL_LINK    = neuraLink_init();
    }

// layers for Dense
    public Layers(final int NEURONS, final Activation ACTIVATION, final Sample SAMPLE){
        this( NEURONS, SAMPLE.getImage2D().length, SAMPLE.getImage2D()[0].length , ACTIVATION, SAMPLE);
    }
    public Layers(final int NEURONS, final Activation ACTIVATION, final Neuron ... INPUT_NEURONS){
        this(NEURONS, INPUT_NEURONS[0].getOutputs().length, INPUT_NEURONS[0].getOutputs()[0].length , ACTIVATION, INPUT_NEURONS);
    }

// ----------- ACTIVATION FUNCTIONS --------------------
    public static enum Activation {
        // Sigmoid Activation which will call the 
        SIGMOID{
            public void function(final Neuron.Node NODE, final Layers LAYER){NODE.setNode_Value(Activations.Sigmoid.function                (NODE.getForward_Linear()));}
            public void derivative(final Neuron.Node NODE, final Layers LAYER){NODE.setPartial_Derivative(Activations.Sigmoid.derivative    (NODE.getBack_Linear()));}
            public double random_weight(final Layers LAYER){return Activations.Sigmoid.weight_inizialization(LAYER.getInputs().length *     (LAYER.getKernel_Y() * LAYER.getKernel_X()));}
        },
        TANH{
            public void function(final Neuron.Node NODE, final Layers LAYER){NODE.setNode_Value(Activations.Tanh.function                   (NODE.getForward_Linear()));}
            public void derivative(final Neuron.Node NODE, final Layers LAYER){NODE.setPartial_Derivative(Activations.Tanh.derivative       (NODE.getOutputNode()));}
            public double random_weight(final Layers LAYER){ return Activations.Tanh.weight_inizialization(LAYER.getInputs().length * (LAYER.getKernel_Y() * LAYER.getKernel_X()));}
        }, 
        SWISH{
            public void function(final Neuron.Node NODE, final Layers LAYER){NODE.setNode_Value(Activations.Swish.function                  (NODE.getForward_Linear()));}
            public void derivative(final Neuron.Node NODE, final Layers LAYER){NODE.setPartial_Derivative(Activations.Swish.derivative      (NODE.getBack_Linear(), NODE.getOutputNode()));}
            public double random_weight(final Layers LAYER){ return Activations.Swish.weight_inizialization(LAYER.getInputs().length * (LAYER.getKernel_Y() * LAYER.getKernel_X()));}
        }, 
        RELU{
            public void function(final Neuron.Node NODE, final Layers LAYER){NODE.setNode_Value(Activations.Relu.function                   (NODE.getForward_Linear()));}
            public void derivative(final Neuron.Node NODE, final Layers LAYER){NODE.setPartial_Derivative(Activations.Relu.derivative       (NODE.getBack_Linear()));}
            public double random_weight(final Layers LAYER){ return Activations.Relu.weight_inizialization (LAYER.getInputs().length * (LAYER.getKernel_Y() * LAYER.getKernel_X()));}
        },
        LRELU{
            public void function(final Neuron.Node NODE, final Layers LAYER){NODE.setNode_Value(Activations.Lrelu.function                  (NODE.getForward_Linear()));}
            public void derivative(final Neuron.Node NODE, final Layers LAYER){NODE.setPartial_Derivative(Activations.Lrelu.derivative      (NODE.getBack_Linear()));}
            public double random_weight(final Layers LAYER){ return Activations.Lrelu.weight_inizialization(LAYER.getInputs().length * (LAYER.getKernel_Y() * LAYER.getKernel_X()));}
        },
        SELU{
            public void function(final Neuron.Node NODE, final Layers LAYER){NODE.setNode_Value(Activations.Selu.function                   (NODE.getForward_Linear()));}
            public void derivative(final Neuron.Node NODE, final Layers LAYER){NODE.setPartial_Derivative(Activations.Selu.derivative       (NODE.getBack_Linear()));}
            public double random_weight(final Layers LAYER){ return Activations.Selu.weight_inizialization(LAYER.getInputs().length * (LAYER.getKernel_Y() * LAYER.getKernel_X()));}
        },
        SOFTMAX{
            public void function(final Neuron.Node NODE, final Layers LAYER){NODE.setNode_Value(Activations.Softmax.function                (NODE, LAYER.getNodeArray()));}
            public void derivative(final Neuron.Node NODE, final Layers LAYER){ NODE.setPartial_Derivative(1.0); }
            public double random_weight(final Layers LAYER){ return Activations.Softmax.weight_inizialization(LAYER.getInputs().length * (LAYER.getKernel_Y() * LAYER.getKernel_X()));}
        };

        // 
            public abstract void function(final Neuron.Node NODE, final Layers LAYER);
            public abstract void derivative(final Neuron.Node NODE, final Layers LAYER);
            public abstract double random_weight(final Layers LAYER);

    }

// class of Layer initialization
    public static class Layer_init {
       
        public final String LAYER;                  //Understanding if it is a convolutional layer or Dense layer
        public final int NEURONS;                   //Neurons that will be initialized
        public final int KERNEL_Y;                  //Y kernel size
        public final int KERNEL_X;                  //X kernel size
        public final Layers.Activation ACTIVATION;  //Activation Layer
 
// Layer init contructor which will be the first one to be initializied for Conv2d
        public Layer_init(final int NEURONS, final int KERNEL_Y, final int KERNEL_X, final Layers.Activation ACTIVATION){
            this.LAYER = (KERNEL_Y * KERNEL_X) == 0 ? "DENSE" : "CONV2D";
            this.NEURONS = NEURONS;   
            this.KERNEL_Y = KERNEL_Y;
            this.KERNEL_X = KERNEL_X;
            this.ACTIVATION = ACTIVATION;

        }
// Layer init constructor for Dense layer
        public Layer_init(final int NEURONS, final Layers.Activation ACTIVATION){
            this(NEURONS, 0, 0, ACTIVATION); //it calls the upper constructor method
        }

    }

// Convolutional layer constructor
    public static Layer_init Conv2d(final int NEURONS, final int KERNEL_Y, final int KERNEL_X, final Layers.Activation ACTIVATION){
        return new Layer_init(NEURONS, KERNEL_Y,KERNEL_X, ACTIVATION);
    }
    
// Dense Layer constrctor
    public static Layer_init Dense(final int NEURONS, final Layers.Activation ACTIVATION){
        return new Layer_init(NEURONS,ACTIVATION);
    }

// initializing all neurons that the layer needs
    private Neuron[] neurons_init(final int NEURONS_AMOUNT){
        final Neuron[] NEURONS_TMP = new Neuron[NEURONS_AMOUNT];

        for(int neuron = 0; neuron < NEURONS_AMOUNT; neuron++){
            Neuron perceptron = new Neuron(this.INPUTS.length, this.KERNEL_Y , this.KERNEL_X, (this.INPUTS[0].getOutputs().length * this.INPUTS[0].getOutputs()[0].length));
            Weights_Matrix(perceptron);
            NEURONS_TMP[neuron] = perceptron;
        }
        return NEURONS_TMP;
    }

    // needs of adjustment
// generator of neurons link
    private Neuron.Node[][][][] link_gen(final int L_PAD_Y, final int L_PAD_X, final int R_PAD_Y, final int R_PAD_X, final int ACTMAP_SIZE){

        final Neuron.Node[][][][] TMP_NEURALINK = new Neuron.Node[this.INPUTS.length][this.KERNEL_Y][this.KERNEL_X][ACTMAP_SIZE];
        // cycling over the channels
        for(int channel=0; channel < this.INPUTS.length; channel++){
            final Neuron.Node[][] PREV_LAYERNODE = this.INPUTS[channel].getOutputs();
            int link_counter = 0;

            // cycling over the input image pixels
            for(int pad_y = L_PAD_Y; pad_y < R_PAD_Y; pad_y++){
                for(int pad_x = L_PAD_X; pad_x < R_PAD_X; pad_x++){

                    // cycling over the kernal weights
                    for(int kernel_y=0; kernel_y < this.KERNEL_Y; kernel_y++){
                        for(int kernel_X=0; kernel_X < this.KERNEL_X; kernel_X++){
                    
                    // storing the relations between weigths and inputs
                        try{    
                                TMP_NEURALINK[channel][kernel_y][kernel_X][link_counter] = PREV_LAYERNODE[pad_y+kernel_y][pad_x+kernel_X];
                            }catch(ArrayIndexOutOfBoundsException e){}
                        }

                    }
                    link_counter++; // incrementing the counter
                }
            }
        }

        return TMP_NEURALINK;

    }

// link between neurons for gradient descent
    private Neuron.Node[][][][] neuraLink_init(){
        // retrieving the size of activation map
        final int ACTIVATIONMAP_SIZE = this.NEURONS[0].getOutputs().length * this.NEURONS[0].getOutputs()[0].length;

        // working out padding
        final int ACTMAP_Y = this.INPUTS[0].getOutputs().length - this.KERNEL_Y;
        final int ACTMAP_X  = this.INPUTS[0].getOutputs().length - this.KERNEL_X;
        final int LEFT_ACTMAT_Y = ACTMAP_Y>= 0?0:ACTMAP_Y;
        final int LEFT_ACTMAP_X = ACTMAP_X>= 0?0:ACTMAP_X;
        final int RIGHT_ACTMAP_Y = ACTMAP_Y>= 0?ACTMAP_Y + 1 : 1;
        final int RIGHT_IMAGE_X = ACTMAP_X >= 0?ACTMAP_X + 1 : 1;

        return link_gen(LEFT_ACTMAT_Y, LEFT_ACTMAP_X, RIGHT_ACTMAP_Y, RIGHT_IMAGE_X, ACTIVATIONMAP_SIZE);
    }

// initializing the nodes output
    private Neuron.Node[] outputNodes_init(){
        final Neuron.Node[] TMP_NODES = new Neuron.Node[this.NEURONS.length * (this.NEURONS[0].getOutputs().length * this.NEURONS[0].getOutputs()[0].length)];
        int nodes_counter = 0; 

        for(int neuron = 0; neuron < this.NEURONS.length; neuron++){
            for(int actMap_y = 0; actMap_y < this.NEURONS[neuron].getOutputs().length; actMap_y++){
                for(int actMap_x = 0; actMap_x < this.NEURONS[neuron].getOutputs()[actMap_y].length; actMap_x++){

                    TMP_NODES[nodes_counter++] = this.NEURONS[neuron].getOutputs()[actMap_y][actMap_x];

                }
            }
        }

        return TMP_NODES;
    }

// calculating the derivative 
    private double derivative_gen(final Neuron.Node OUTPUT_NEURONS){

        this.ACTIVATION.derivative(OUTPUT_NEURONS, this); //calling the derivative
        OUTPUT_NEURONS.set_DerivativeSum();

        return OUTPUT_NEURONS.getDerivative();


    }
// need adjustment
// propagating the derivative for the other layers
   private void propagation_gradient(final Neuron NEURON, final double SUM_DERIVATIVE, final int CHANNEL, final int KERNEL_Y, final int KERNEL_X, final int SLIDE){
    try{
            // --------- GRADIENT DISCENT OPERATION -----------------
            // storing the gradient into this layer node 
            // summing this output pixel derivative times all its inputs (find new weight gradient)
            NEURON.sumGradients(CHANNEL,KERNEL_Y, KERNEL_X, SUM_DERIVATIVE * this.NEURAL_LINK[CHANNEL][KERNEL_Y][KERNEL_X][SLIDE].getOutputNode());

            // --------- BACK PROPAGATION OPERATION -----------------

            // storing the sum into the next layer node in back propagation way
            // summing this output pixel derivative times all its weights (find new input gradient)
            this.NEURAL_LINK[CHANNEL][KERNEL_Y][KERNEL_X][SLIDE].addTo_Chain(SUM_DERIVATIVE * NEURON.getMatrixWeight(CHANNEL, KERNEL_Y, KERNEL_X));

        }catch(NullPointerException e){}

   }

// function that creates the first layer
    public void sample_loader(final Sample SAMPLE){
        for(int output_y= 0; output_y < INPUTS[0].getOutputs().length; output_y++){
            for(int output_x = 0; output_x < INPUTS[0].getOutputs()[0].length; output_x++){
                this.INPUTS[0].setOutputs(output_y, output_x, SAMPLE.getImage2D()[output_y][output_x]);
            }
        } 
    }


// need to adjust
// forward Propagation
    public void forwardProp(){

        final int ACTIVATIONMAP_SIZE_Y = this.NEURONS[0].getOutputs().length; //size of y
        final int ACTIVATIONMAP_SIZE_X = this.NEURONS[0].getOutputs()[0].length; //size of x

        // cycling over all this layer nodes
        for(int neuron = 0; neuron < this.NEURONS.length; neuron++){
            final Neuron NEURON = this.NEURONS[neuron];
            int slide = 0;
            
            // cycling over all the "pixels" of the output matrix
            for(int pixel_y = 0; pixel_y < ACTIVATIONMAP_SIZE_Y; pixel_y++){
                for(int pixel_x = 0; pixel_x < ACTIVATIONMAP_SIZE_X; pixel_x++){
                    // getting the output of this activation map index
                    final Neuron.Node NODE = NEURON.getOutputs()[pixel_y][pixel_x];
                
                    // cycling over the all the kernel weights
                    for(int filter = 0; filter < this.INPUTS.length; filter++){

                        // cycling over this entire channel
                        for(int kernel_y = 0; kernel_y < this.KERNEL_Y; kernel_y++){
                            for(int kernel_x = 0; kernel_x < this.KERNEL_X; kernel_x++){
                                // because of the padding a try and catch
                                try{    
                                        // summing the input times the weight
                                        NODE.addTo_Linear((NEURON.getMatrixWeight(filter, kernel_y, kernel_x) * this.NEURAL_LINK[filter][kernel_y][kernel_x][slide].getOutputNode()));
                                    } catch(NullPointerException e){}

                            }
                        }                        
                    }
                    // summming the bias
                    NODE.addTo_Linear(NEURON.getBias(pixel_y, pixel_x));
                    // performing the activation function for this output / feature-map "pixel"
                    this.ACTIVATION.function(NODE, this);
                    slide++;
                }
            }
        }
    }

//backpropagation
    public void backProp(){

        final int ACTIVATIONMAP_SIZE_Y = this.NEURONS[0].getOutputs().length;       //size of y
        final int ACTIVATIONMAP_SIZE_X = this.NEURONS[0].getOutputs()[0].length;    //size of x

        // cycling overall the nodes
        for(int neuron = 0; neuron < this.NEURONS.length; neuron++){
            final Neuron NEURON = this.NEURONS[neuron]; //neuron object
            final Neuron.Node[][] OUTPUT_NEURONS = NEURON.getOutputs(); //getting theoutputs of the neurons
            int slide  = 0; //counter for the stride

            // cycling over all the "pixels" of the output matrix
            for(int pixel_y=0; pixel_y < ACTIVATIONMAP_SIZE_Y; pixel_y++){
                for(int pixel_x=0; pixel_x < ACTIVATIONMAP_SIZE_X; pixel_x++){

                    // calculationthe derivative of the non-linear to linear operation
                    final double VALUE_DERIVATIVE = derivative_gen(OUTPUT_NEURONS[pixel_y][pixel_x]);
                    // storing the biases gradients
                    NEURON.sumBiasGradient(pixel_y, pixel_x, VALUE_DERIVATIVE);

                    // cycling over the all the kernel weights
                    for(int filter=0; filter < this.INPUTS.length; filter++){
                        for(int kernel_y = 0; kernel_y < this.KERNEL_Y; kernel_y++){
                            for(int kernel_x = 0; kernel_x < this.KERNEL_X; kernel_x++){
                                // performing the chain runle operations
                                this.propagation_gradient(NEURON, VALUE_DERIVATIVE, filter, kernel_y, kernel_x, slide);         
                            }
                        }
                    }
                    slide++; //incrementing the stride for the convolutional
                }
            }
        }
    }


//update
    public void update(final int BATCH, final double LEARNING_RATE){
        
// looping through the neurons array
        for(final Neuron NEURON : this.NEURONS){
            NEURON.biasUpdate(BATCH, LEARNING_RATE);
            NEURON.weightsUpdate(BATCH, LEARNING_RATE);
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

            // HeNormal Weight initialization
            for(int n_filters = 0; n_filters < this.INPUTS.length ; n_filters++){
                for(int weight_row = 0; weight_row < this.KERNEL_Y; weight_row++){
                    for(int weight_col = 0; weight_col < this.KERNEL_X; weight_col++){
                        NEURON.setWeights(n_filters, weight_row, weight_col, 
                        
                        ACTIVATION.random_weight(this));
                    }
                }
            }
    }


// gets the inputs of the neurons
    public Neuron[] getInputs(){
        return this.INPUTS;
    }
// gets the outputs of the neurons
    public Neuron[] getOutputs(){
        return this.NEURONS;
    }
// gets the inputs of the neurons
    public int getKernel_Y(){
        return this.KERNEL_Y;
    }
// gets the inputs of the neurons
    public int getKernel_X(){
        return this.KERNEL_X;
    }
// retrieving output nodes 
    public Neuron.Node[] getNodeArray(){
        return OUTPUT_NODES;
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

 
}



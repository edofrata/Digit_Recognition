public class Layers {

    private final Neuron[] INPUTS;                  //number of inputs which the neurons will receive
    private final Neuron[] NEURONS;                 //number of total neurons
    private final int KERNEL_Y;                     //Y size of the kernel
    private final int KERNEL_X;                     //X size of the kernel
    private final Activation ACTIVATION;            //Activation function which will be passed
    private final Node[] OUTPUT_NODES;              //output nodes relation for back prop
    private final Node[][][][] NEURAL_LINK;         //bounds between neurons

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
        this.NEURONS        = neurons_init(NEURONS);    //initialising the neurons with the neurons passed as parameters
        this.OUTPUT_NODES   = outputNodes_init();       //initialising the output nodes 
        this.NEURAL_LINK    = neuraLink_init();         //initialising the link between the neurons
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
        // Swish Activation containing the linear transformation, non linear, and the weight inizialization
        SWISH{
            public void function(final Node NODE, final Layers LAYER){NODE.setNode_Value(Activations.Swish.function                  (NODE.getForward_Linear()));}
            public void derivative(final Node NODE, final Layers LAYER){NODE.setPartial_Derivative(Activations.Swish.derivative      (NODE.getBack_Linear(), NODE.getOutputNode()));}
            public double random_weight(final Layers LAYER){ return Activations.Swish.weight_inizialization(LAYER.getInputs().length * (LAYER.getKernel_Y() * LAYER.getKernel_X()));}
        }, 
        // RELU Activation containing the linear transformation, non linear, and the weight inizialization
        RELU{
            public void function(final Node NODE, final Layers LAYER){NODE.setNode_Value(Activations.Relu.function                   (NODE.getForward_Linear()));}
            public void derivative(final Node NODE, final Layers LAYER){NODE.setPartial_Derivative(Activations.Relu.derivative       (NODE.getBack_Linear()));}
            public double random_weight(final Layers LAYER){ return Activations.Relu.weight_inizialization (LAYER.getInputs().length * (LAYER.getKernel_Y() * LAYER.getKernel_X()));}
        },
        // Leaky RELU Activation containing the linear transformation, non linear, and the weight inizialization
        LRELU{
            public void function(final Node NODE, final Layers LAYER){NODE.setNode_Value(Activations.Lrelu.function                  (NODE.getForward_Linear()));}
            public void derivative(final Node NODE, final Layers LAYER){NODE.setPartial_Derivative(Activations.Lrelu.derivative      (NODE.getBack_Linear()));}
            public double random_weight(final Layers LAYER){ return Activations.Lrelu.weight_inizialization(LAYER.getInputs().length * (LAYER.getKernel_Y() * LAYER.getKernel_X()));}
        },
        // SOFTMAX Activation containing the linear transformation, non linear, and the weight inizialization
        SOFTMAX{
            public void function(final Node NODE, final Layers LAYER){NODE.setNode_Value(Activations.Softmax.function                (NODE, LAYER.getNodeArray()));}
            public void derivative(final Node NODE, final Layers LAYER){ NODE.setPartial_Derivative(1.0); }
            public double random_weight(final Layers LAYER){ return Activations.Softmax.weight_inizialization(LAYER.getInputs().length * (LAYER.getKernel_Y() * LAYER.getKernel_X()));}
        };
        // declaring all abstract methods in order not to make mistakes inside the activation functions
            public abstract void function(final Node NODE, final Layers LAYER);
            public abstract void derivative(final Node NODE, final Layers LAYER);
            public abstract double random_weight(final Layers LAYER);

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
    private Node[][][][] link_gen(final int L_PAD_Y, final int L_PAD_X, final int R_PAD_Y, final int R_PAD_X, final int ACTMAP_SIZE){

        final Node[][][][] TMP_NEURALINK = new Node[this.INPUTS.length][this.KERNEL_Y][this.KERNEL_X][ACTMAP_SIZE];
        // cycling over the channels
        for(int channel=0; channel < this.INPUTS.length; channel++){
            final Node[][] PREV_LAYERNODE = this.INPUTS[channel].getOutputs();
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
    private Node[][][][] neuraLink_init(){
        // retrieving the size of activation map
        final int ACTIVATIONMAP_SIZE = this.NEURONS[0].getOutputs().length * this.NEURONS[0].getOutputs()[0].length;
        // working out padding
        // ACTIVATION MAP FOR Y COLUMN                                                //ACTIVATION MAP FOR X ROW
        final int ACTMAP_Y = this.INPUTS[0].getOutputs().length - this.KERNEL_Y,      ACTMAP_X = this.INPUTS[0].getOutputs().length - this.KERNEL_X;
        // LEFT ACTIVATION MAP FOR Y                                                   //LEFT ACTIVATION MAP FOR X 
        final int LEFT_ACTMAT_Y  = ACTMAP_Y >= 0 ? 0 : ACTMAP_Y,                      LEFT_ACTMAP_X = ACTMAP_X >= 0 ? 0 : ACTMAP_X;
        // RIGHT ACTIVATION MAP FOR Y                                                 //RIGHT ACTIVATION MAP FOR X 
        final int RIGHT_ACTMAP_Y = ACTMAP_Y >= 0?ACTMAP_Y + 1 : 1,                    RIGHT_IMAGE_X = ACTMAP_X >= 0 ? ACTMAP_X + 1 : 1;

        return link_gen(LEFT_ACTMAT_Y, LEFT_ACTMAP_X, RIGHT_ACTMAP_Y, RIGHT_IMAGE_X, ACTIVATIONMAP_SIZE);
    }

// initializing the nodes output
    private Node[] outputNodes_init(){
        final Node[] TMP_NODES = new Node[this.NEURONS.length * (this.NEURONS[0].getOutputs().length * this.NEURONS[0].getOutputs()[0].length)];
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
    private double derivative_gen(final Node OUTPUT_NEURONS){

        this.ACTIVATION.derivative(OUTPUT_NEURONS, this); //calling the derivative
        OUTPUT_NEURONS.set_DerivativeSum(); //setting the derivative sum of the neurons

        return OUTPUT_NEURONS.getDerivative(); //it returns the output given from the derivative


    }

// propagating the derivative for the other layers
    private void propagation_gradient(final Neuron NEURON, final double SUM_DERIVATIVE, final int FILTER, final int KERNEL_Y, final int KERNEL_X, final int SLIDE){
     try{
            // forward propagation
            // storing the gradient into this layer node 
            // summing this output pixel derivative times all its inputs (find new weight gradient)
            NEURON.sumGradients(FILTER,KERNEL_Y, KERNEL_X, SUM_DERIVATIVE * this.NEURAL_LINK[FILTER][KERNEL_Y][KERNEL_X][SLIDE].getOutputNode());
          

            // back propagation
            // storing the sum into the next layer node in back propagation way
            // summing this output pixel derivative times all its weights (find new input gradient)
            this.NEURAL_LINK[FILTER][KERNEL_Y][KERNEL_X][SLIDE].addTo_Chain(SUM_DERIVATIVE * NEURON.getMatrixWeight(FILTER, KERNEL_Y, KERNEL_X));

        }catch(NullPointerException e){}

    }

// function that creates the first layer
    public void sample_loader(final Sample SAMPLE){
        // looping though all the outputs from the last layer so to get the next inputs length
        for(int output_y= 0; output_y < INPUTS[0].getOutputs().length; output_y++){
            for(int output_x = 0; output_x < INPUTS[0].getOutputs()[0].length; output_x++){
                this.INPUTS[0].setOutputs(output_y, output_x, SAMPLE.getImage2D()[output_y][output_x]);
            }
        } 
    }

// forward Propagation
    public void forwardProp(){
    //size of y                                                                 //size of x
        final int ACTIVATIONMAP_SIZE_Y = this.NEURONS[0].getOutputs().length,   ACTIVATIONMAP_SIZE_X = this.NEURONS[0].getOutputs()[0].length; 

        // cycling over all this layer nodes
        for(int neuron = 0; neuron < this.NEURONS.length; neuron++){
            final Neuron NEURON = this.NEURONS[neuron]; int slide = 0;
            // cycling over all the "pixels" of the output matrix
            for(int pixel_y = 0; pixel_y < ACTIVATIONMAP_SIZE_Y; pixel_y++){
                for(int pixel_x = 0; pixel_x < ACTIVATIONMAP_SIZE_X; pixel_x++){
                    // getting the output of this activation map index
                    final Node NODE = NEURON.getOutputs()[pixel_y][pixel_x];
                    // cycling over the all the kernel weights
                    for(int filter = 0; filter < this.INPUTS.length; filter++){
                        // cycling over this entire channel
                        for(int kernel_y = 0; kernel_y < this.KERNEL_Y; kernel_y++){
                            for(int kernel_x = 0; kernel_x < this.KERNEL_X; kernel_x++){
                                // because of the padding a try and catch
                                try{ 
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
    //size of Y                                                                 //size of X
        final int ACTIVATIONMAP_SIZE_Y = this.NEURONS[0].getOutputs().length,   ACTIVATIONMAP_SIZE_X = this.NEURONS[0].getOutputs()[0].length;
        // cycling overall the nodes
        for(int neuron = 0; neuron < this.NEURONS.length; neuron++){
            final Neuron NEURON = this.NEURONS[neuron]; //neuron object
            //getting theoutputs of the neurons                     //counter for the stride
            final Node[][] OUTPUT_NEURONS = NEURON.getOutputs();    int slide  = 0; 
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
        // looping through the neurons array and updating weights and biases
        for(final Neuron NEURON : this.NEURONS){
            NEURON.weightsUpdate(BATCH, LEARNING_RATE);
            NEURON.biasUpdate(BATCH, LEARNING_RATE);
        }
    }


  /**
	 * 
     * @param number_filters which will be the number of the filters desired
     * @param X which will be the X length of the kernel size
     * @param Y which will be the Y length of the kernel size
	 * @return void
	 */
// initializing the matrix of weights for Convolutional
    public void Weights_Matrix(final Neuron NEURON) {  
            // HeNormal Weight initialization
            for(int n_filters = 0; n_filters < this.INPUTS.length ; n_filters++){
                for(int weight_row = 0; weight_row < this.KERNEL_Y; weight_row++){
                    for(int weight_col = 0; weight_col < this.KERNEL_X; weight_col++){
                        NEURON.setWeights(n_filters, weight_row, weight_col,ACTIVATION.random_weight(this)); //setting the array weights
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
    public Node[] getNodeArray(){
        return OUTPUT_NODES;
    } 
}



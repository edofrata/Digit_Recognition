public class Model_Settings {

    private final Layers[] LAYERS;                  //layers of the model which will keep all the neurons inside the single layer
    private final Layer_init[] LAYERS_INIT;  //array of the layer init
    private int epochs;                             //epochs of the model
    private int batch;                              //batch of the model
    private double learning_rate;                   //fixed learning rate of the model 
    private double accuracy;                        //accuracy of the model
    private Dataset training;                       //training dataset used for the model
    private Dataset validation;                     //validation dataset used for the model
    private Losses loss;                            //loss getting from the model

// model settings constructor
    private Model_Settings(final Layer_init[] LAYERS_INIT){
        this.LAYERS_INIT = LAYERS_INIT; 
        this.LAYERS = new Layers[LAYERS_INIT.length];
    }

// simulates a constructor which will return the class
    public static Model_Settings Procedure(final Layer_init ... LAYERS){
        return new Model_Settings(LAYERS);
    }

// enum for the loss functions
    public static enum Losses {
        MSE{
            // normal loss calculation
            public void function(){


            }
            
            // working out the derivative 
            public void derivative(final Layers LAYER, final Sample SAMPLE){


            }
        },
        MAE{
            // normal loss calculation
            public void function(){

            }
   
            // working out the derivative 
            public void derivative(final Layers LAYER, final Sample SAMPLE){
                

            }
        }, 
        CROSS_ENTROPY{
            // normal loss calculation
            public void function(){

                
            }

            // working out the derivative 
            public void derivative(final Layers LAYER, final Sample SAMPLE){
               final Node[] NODES = LAYER.getNodeArray();
               for(int node = 0; node < NODES.length; node++){
                    inject_error(Loss.Cross_Entropy.derivative(NODES[node].getOutputNode(), SAMPLE.getOneHot_index(node)),NODES[node]);
               }
            }
        };

        public abstract void function();
        public abstract void derivative(final Layers LAYER, final Sample SAMPLE);
        // injecting the error to the chain rule
        private static void inject_error(final double ERROR, final Node NODE){
            NODE.addTo_Chain(ERROR);
        }
    }

// model initializer
    public void build_model(final Dataset TRAINING, final Dataset VALIDATION, final Losses LOSS){
// addressing the parameters to the appropriate attributes
        this.training = TRAINING;
        this.validation = VALIDATION;   
        this.loss = LOSS;
        
        Layers layer_tmp; //it will keep the previous layer inside
       
        if(LAYERS_INIT[0].LAYER == "DENSE") layer_tmp = new Layers(LAYERS_INIT[0].NEURONS, LAYERS_INIT[0].ACTIVATION, training.getSample(0));
        else layer_tmp = new Layers(LAYERS_INIT[0].NEURONS, LAYERS_INIT[0].KERNEL_Y, LAYERS_INIT[0].KERNEL_X, LAYERS_INIT[0].ACTIVATION, training.getSample(0));

        this.LAYERS[0] = layer_tmp;
// initialize layers
        for(int layer = 1; layer < this.LAYERS.length; layer++ ){
            if(LAYERS_INIT[layer].LAYER == "DENSE") this.LAYERS[layer] = new Layers(LAYERS_INIT[layer].NEURONS, LAYERS_INIT[layer].ACTIVATION, layer_tmp.getOutputs());
            else this.LAYERS[layer] = new Layers(LAYERS_INIT[layer].NEURONS, LAYERS_INIT[layer].KERNEL_Y, LAYERS_INIT[layer].KERNEL_X, LAYERS_INIT[layer].ACTIVATION, layer_tmp.getOutputs());
            layer_tmp = this.LAYERS[layer];
        }

    } 

// training function of the model
    public void training(final Dataset TRAINING, final int EPOCHS, final int MINI_BATCH, final double LEARNING_RATE){

        this.epochs = EPOCHS;
        this.batch = MINI_BATCH;
        this.learning_rate = LEARNING_RATE;
        final int DATA_CYCLE = TRAINING.getSize() - 1;

        for(int epoch = 0; epoch < epochs; epoch++){
            System.out.println("\nEpoch => " + (epoch + 1) + "/" + CNN.EPOCHS);
            final Utils.ProgressBar BAR = new Utils.ProgressBar();
            
            for(int sample = 0, counter = 1; sample <= DATA_CYCLE ; sample++, counter++){
                BAR.progress_bar(DATA_CYCLE, sample);
                forwardPropagation(TRAINING.getSample(sample));
                backpropagation(TRAINING.getSample(sample));

                if(counter >= MINI_BATCH || sample >= DATA_CYCLE ){ 
                    counter = 1;
                    update_weights();
                }
            }
        }
    }

// forward propagation process
    private void forwardPropagation(final Sample SAMPLE){
        // loads the samples into the model
        this.LAYERS[0].sample_loader(SAMPLE);
        // looping through the samples and doing the forward propagation
        for(final Layers LAYER : this.LAYERS)   LAYER.forwardProp();
    }

// backpropagation process
    private void backpropagation(final Sample SAMPLE){
        // getting the derivative of the loss 
        this.loss.derivative(this.LAYERS[this.LAYERS.length-1], SAMPLE);
        // working out the cost of the function
        for(int layer = this.LAYERS.length-1; layer >= 0; layer--){
            this.LAYERS[layer].backProp();
        }
    }

// update weights process
    private void update_weights(){
        // looping through the samples
        for(final Layers LAYER : this.LAYERS) LAYER.update(this.batch, this.learning_rate);
    }

// validate process
    public void validate(Dataset ... val_data){
        System.out.println("\nValidating...");
        Dataset validation = val_data.length > 0 ?  val_data[0]: this.validation;
        int correct_counter = 0;
// looping through the samples
        for(int sample = 0; sample < validation.getSize(); sample++){
            forwardPropagation(validation.getSample(sample));
            double predicted = this.LAYERS[this.LAYERS.length-1].getNodeArray()[0].getOutputNode();
            int index_predicted = 0;

            for(int outputs = 0; outputs < this.LAYERS[this.LAYERS.length-1].getNodeArray().length; outputs++){

               if(predicted < this.LAYERS[this.LAYERS.length-1].getNodeArray()[outputs].getOutputNode()){
                    predicted = this.LAYERS[this.LAYERS.length-1].getNodeArray()[outputs].getOutputNode();
                    index_predicted = outputs;
               }

            }
            // it will return a label which will be compared to the actual label of this sample
            if (validation.getLabel(index_predicted) == validation.getSample(sample).getLabel()){
                correct_counter++;
            }
        }

        this.accuracy = (double)correct_counter * (double)100 / (double)validation.getSize();
    }

    // GETTER METHOD

// retrieving accuracy
    public double accuracy(){
        return this.accuracy;
    }

}
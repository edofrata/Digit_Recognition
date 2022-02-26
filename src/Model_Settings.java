public class Model_Settings {

    private final Layers[] LAYERS;                  //layers of the model which will keep all the neurons inside the single layer
    private final Layers.Layer_init[] LAYERS_INIT;  //array of the layer init
    private int epochs;                             //epochs of the model
    private int batch;                              //batch of the model
    private double learning_rate;                   //fixed learning rate of the model 
    private double accuracy;                        //accuracy of the model
    private Dataset training;                       //training dataset used for the model
    private Dataset validation;                     //validation dataset used for the model
    private Loss loss;                              //loss getting from the model

// model settings constructor
    private Model_Settings(final Layers.Layer_init[] LAYERS_INIT){
        this.LAYERS_INIT = LAYERS_INIT; 
        this.LAYERS = new Layers[LAYERS_INIT.length];
    }

// simulates a constructor which will return the class
    public static Model_Settings Procedure(final Layers.Layer_init ... LAYERS){
        return new Model_Settings(LAYERS);
    }

// enum for the loss functions
    public static enum Loss {
        MSE{}, MAE{}, CROSS_ENTROPY{}, HUBER{}, KULLBACK{};
    }

// model initializer
    public void build_model(final Dataset TRAINING, final Dataset VALIDATION, final Loss LOSS){

        this.training = TRAINING;
        this.validation = VALIDATION;   
        this.loss = LOSS;
        
        Layers layer_tmp; //it will keep the previous layer inside
       
        if(LAYERS_INIT[0].LAYER == "DENSE") layer_tmp = new Layers(LAYERS_INIT[0].NODES, LAYERS_INIT[0].ACTIVATION, training.getSample(0));
        else layer_tmp = new Layers(LAYERS_INIT[0].NODES, LAYERS_INIT[0].KERNEL_Y, LAYERS_INIT[0].KERNEL_X, LAYERS_INIT[0].ACTIVATION, training.getSample(0));


// initialize layers
        for(int layer = 0; layer < this.LAYERS.length; layer++ ){
            if(LAYERS_INIT[layer].LAYER == "DENSE") this.LAYERS[layer] = new Layers(LAYERS_INIT[layer].NODES, LAYERS_INIT[layer].ACTIVATION, layer_tmp.getOutputs());
            else this.LAYERS[layer] = new Layers(LAYERS_INIT[layer].NODES, LAYERS_INIT[layer].KERNEL_Y, LAYERS_INIT[layer].KERNEL_X, LAYERS_INIT[layer].ACTIVATION, layer_tmp.getOutputs());
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
            for(int sample = 0, counter = 1; sample <= DATA_CYCLE ; epoch++, counter++){
                
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
    private void forwardPropagation(final Sample sample){


    }

// backpropagation process
    private void  backpropagation(final Sample sample){


    }
// update weights process
    private void update_weights(){


    }

// validate process
    public void validate(final Dataset VALIDATION){



    }

    // GETTER METHOD

// retrieving accuracy
    public double accuracy(){
        return this.accuracy;
    }





    // // kernel size 
    // protected static int X = 3, Y= 3;
    // // filter size of Convolutional Layer
    // protected static int filters = 32;
    // // counter for keeping the sample count
    // private int counter = 0;

    
    // Model_Settings(){}
    
    
    // public void Settings() {
    //     // CNN CNN = new CNN();
    
    //     for (int epoch = 0; epoch <= 20; epoch++) {

    //         System.out.println("Epoch number " + epoch);

    //         for (int sample = counter; sample < Utils.train_dataset.length;) {
    //             for (int mini_batch = 0; mini_batch <= 32; mini_batch++) {

    //                 //CNN.Conv2d(filters,1, X, Y, Utils.train_dataset[sample]);
    //                 // //Activation.ReLu(array);
    //                 //CNN.Conv2d(filters,32,X, Y, Utils.train_dataset[sample]);
    //                 // //CNN.Flatten(array);
    //                 // //CNN.DenseNet(512);
    //                 // //Activation.ReLu(array);
    //                 // //CNN.Linear_Transformation(array);
    //                 // //Activation.SoftMax(array);
    //                 // //Loss.Cross_Entropy(array);
    //                 // //Backpropagation();
    //                 counter++;

    //                 System.out.println(" Loss:   " + "loss" +  " -- " + " Accuracy: " +  "acc");       
    //             }
    //             // WeightUpdate();
    //         }

    //     }
    // }

}
public class CNN {
   
   
    private static final String TRAIN_DATASET = "cw2DataSet1.csv";  //training dataset
    private static final String VAL_DATASET   = "cw2DataSet2.csv";  //validation dataset
    private static final double LEARNING_RATE = 0.03;               //learning rate of the model
    private static final int    MINI_BATCH    = 8;                  //size of the mini batch 
    private static final int    EPOCHS        = 10;                 //epochs which will be changed according to the model needs


// model structure which can be changed according to the needs of the model
    private static final Model_Settings MODEL = Model_Settings.Procedure(

        Layers.Conv2d(16, 2, 2, Layers.Activation.LRELU), // Convolutional Layer composed from 32 neurons/filters, 3 x 3 kernel and activation layer 
        Layers.Conv2d(16, 2, 2, Layers.Activation.LRELU),
        // Layers.Dense(128,       Layers.Activation.LRELU), // Dense Layer, fully connected composed of 512 neurons and activation layer
        Layers.Dense(128,       Layers.Activation.LRELU), // Dense Layer, fully connected composed of 512 neurons and activation layer
        Layers.Dense(10,        Layers.Activation.SOFTMAX)

    );

    
// Control Settings
    public static void control_settings(){

        final Dataset TRAINING = new Dataset(TRAIN_DATASET); //creating the dataset object with the training dataset
        final Dataset VALIDATION = new Dataset(VAL_DATASET); //creating the dataset object with the validation dataset

        MODEL.build_model(TRAINING, VALIDATION, Model_Settings.Losses.CROSS_ENTROPY); 
        MODEL.training(TRAINING, EPOCHS, MINI_BATCH, LEARNING_RATE);
        MODEL.validate(VALIDATION);
        System.out.println("Accuracy: " + MODEL.accuracy());

    }
















    
    

// // it creates the neurons and adds them to the layer
//     protected Layers[] AddLayer(int neurons, int n_InputsInNeuron, int X, int Y, double[] sample){
//         Layers layer = new Layers(neurons); //initializing the layer
//         layer.neurons_init(neurons, n_InputsInNeuron, X, Y, sample.length); //initializing neurons
//         layer_container[length++] = layer; //adding the layer 
//         return layer_container;
//     }

}






















    // double sum = 0; // sum which will keep the tot sum of the features maps
    // int counter_X = 0, counter_Y = 0; //keeps the count for the image to slide
    // int[][] image_2d = new int[(int)Math.sqrt(sample.length)][(int)Math.sqrt(sample.length)];
    // //making the array to 2d
    // Utils.from_1d_to_2d(matrix.getImage(), image_2d);
    
    // // looping through how many filters have been inserted as input
    // for (int filter = 0; filter < filters; filter++) { 

    //     for(int n_matrix = 0; n_matrix < number_matrix; n_matrix++){
    //         for(int stride_row = 0; stride_row <= output_lenght; stride_row++){
    //             for(int stride_column = 0; stride_column <= output_lenght; stride_column++){
    //                 sum = 0; // sum which will keep the tot sum of the features maps
    //                 counter_X = stride_row; counter_Y = stride_column; 
    //                 // looping though every pixel in the dataset image sample
    //                 for (int pixel_row = 0; pixel_row <  X; pixel_row++) {
    //                     for(int pixel_col = 0; pixel_col < Y; pixel_col++){
    //                         // looping through the weights matrix
    //                         neuron.sumInputs(filter, stride_row, stride_column, image_2d[counter_X][counter_Y]);
    //                         sum += neuron.getMatrix()[n_matrix][pixel_row][pixel_col] * image_2d[counter_X][counter_Y++];
    //                     }
    //                     counter_X++; counter_Y = stride_column;
    //                 }
    //                     sum += neuron.getBias(); // adding the bias
    //                     neuron.getOutputs()[filter][stride_row][stride_column] = sum; //adding the output to the output array
    //             }
    //         }

    //     }

    // }
    // return;


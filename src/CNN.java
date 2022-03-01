public class CNN {
   
    private static final String TRAIN_DATASET = "cw2DataSet1.csv";  //training dataset
    private static final String VAL_DATASET   = "cw2DataSet2.csv";  //validation dataset

    // changeable paramters in order to make a different CNN or NN
    private static final double LEARNING_RATE = 0.02;               //learning rate of the model
    private static final int    MINI_BATCH    = 8;                  //size of the mini batch 
    protected static final int  EPOCHS        = 12;                 //epochs which will be changed according to the model needs

// Control Settings
    public static void control_settings(){
        //creating the dataset object with the training dataset
        final Dataset TRAINING = new Dataset(TRAIN_DATASET); 
        //creating the dataset object with the validation dataset
        final Dataset VALIDATION = new Dataset(VAL_DATASET); 

        // building the model with the parameters
        // passing the training, validation and the Loss function I would like to work with
        MODEL.build_model(TRAINING, VALIDATION, Model_Settings.Losses.CROSS_ENTROPY); 
        // training phase of the model where all the parameters are specified
        MODEL.training(TRAINING, EPOCHS, MINI_BATCH, LEARNING_RATE);
        // validation phase
        MODEL.validate(VALIDATION);
        
        // printing the model accuracy
        System.out.printf("\nAccuracy: %.2f", MODEL.accuracy());
        System.out.println("%");

    }


// model structure which can be changed according to the needs of the model
    private static final Model_Settings MODEL = Model_Settings.Procedure(
// Convolutional Layer composed from 32 neurons/filters, 3 x 3 kernel and activation layer 
        Layers.Conv2d(32, 3, 3, Layers.Activation.LRELU), 
        Layers.Conv2d(32, 3, 3, Layers.Activation.LRELU),

// Dense Layer, fully connected composed of 64 neurons and activation layer
        Layers.Dense(64, Layers.Activation.LRELU), 
        Layers.Dense(10, Layers.Activation.SOFTMAX)

    );

    

}

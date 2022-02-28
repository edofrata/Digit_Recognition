public class CNN {
   
    private static final String TRAIN_DATASET = "cw2DataSet1.csv";  //training dataset
    private static final String VAL_DATASET   = "cw2DataSet2.csv";  //validation dataset
    private static final double LEARNING_RATE = 0.03;               //learning rate of the model
    private static final int    MINI_BATCH    = 8;                  //size of the mini batch 
    private static final int    EPOCHS        = 12;                 //epochs which will be changed according to the model needs


// model structure which can be changed according to the needs of the model
    private static final Model_Settings MODEL = Model_Settings.Procedure(

        Layers.Conv2d(32, 3, 3, Layers.Activation.SWISH), // Convolutional Layer composed from 32 neurons/filters, 3 x 3 kernel and activation layer 
        Layers.Conv2d(32, 3, 3, Layers.Activation.SWISH),

        Layers.Dense(64,        Layers.Activation.SWISH), // Dense Layer, fully connected composed of 512 neurons and activation layer
        Layers.Dense(10,        Layers.Activation.SOFTMAX)

    );

// Control Settings
    public static void control_settings(){

        final Dataset TRAINING = new Dataset(TRAIN_DATASET); //creating the dataset object with the training dataset
        final Dataset VALIDATION = new Dataset(VAL_DATASET); //creating the dataset object with the validation dataset

        MODEL.build_model(TRAINING, VALIDATION, Model_Settings.Losses.CROSS_ENTROPY); 
        MODEL.training(TRAINING, EPOCHS, MINI_BATCH, LEARNING_RATE);
        MODEL.validate(VALIDATION);
        System.out.printf("Accuracy: %.2f", MODEL.accuracy());
        System.out.println("%");

    }
}

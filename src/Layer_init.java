// class of Layer initialization
public class Layer_init {
       
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
public class Sample {
    
    private final double[]      IMAGE_1D;   //array which keeps the image
    private final double        LABEL;      //label of the sample
    private final double[][]    IMAGE_2D;
    double[] one_hot;                       //adding the target value to the class
  
    public Sample(final double[] IMAGE, final double LABEL){
        final double SQR_ROOT   = Math.sqrt(IMAGE.length);
        this.IMAGE_1D           = IMAGE;
        this.LABEL              = LABEL;
        this.IMAGE_2D           = new double[(int)SQR_ROOT][(int)SQR_ROOT];
        Utils.from_1d_to_2d(IMAGE, IMAGE_2D);
        
    }

    // ------------- Getter --------------
 // it returns the image of the sample
    public double[] getImage1D(){
        return IMAGE_1D;
    }
 // returns the label of the sample
    public double getLabel(){
        return LABEL;
    }
 // returns the label of the sample
    public double[][] getImage2D(){
        return IMAGE_2D;
    }

// returns the label of the sample
    public double getOneHot_index(final int INDEX){
        return one_hot[INDEX];
    }

    // ------------- SETTER --------------
// setting the label in the output
    public void setOneHot(double[] label){
         this.one_hot = label;
    }
}

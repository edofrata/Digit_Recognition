import java.util.ArrayList;

public class Sample {
    
    int[] image = new int[64]; //array which keeps the image
    int label; //label of the sample
    ArrayList<Double> gradients = new ArrayList<>(); //Array which will contain the gradients
    
    public Sample(int[] image, int label,  ArrayList<Double> gradients){

        this.image = image;
        this.label = label;
        this.gradients = gradients;

    }

    // Getter methods
    // it returns the image of the sample
    public int[] getImage(){
        return image;
    }
    // returns the label of the sample
    public int getLabel(){
        return label;
    }
    // returns the gradients of the sample
    public ArrayList<Double> getGradients(){
        return gradients;
    }
}

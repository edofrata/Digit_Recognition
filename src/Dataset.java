import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;

public class Dataset {
    
    private final Sample[] COLLECTION;  //sample array
    private final double[] CLASSES;     //array for all the classes 10 digit
    private final String FILE_DATASET;  //name of the dataset


    public Dataset(final String DATASET_FILE){
        this.FILE_DATASET = DATASET_FILE;   //name of the dataset file
        this.COLLECTION   = reader();       //collection loader calling the reader
        this.CLASSES      = classes_init(); //classes for the output labels
        oneHot_Gen();                       //it generates the output label
    }

 // it reads in the data
      private Sample[] reader(){
        ArrayList<Sample> sample_list = new ArrayList<>(); //arraylist for the samples
        try {

                // string which keeps track of the current line
                String current_line;
               
                // Buffer reader for the txt file
                BufferedReader data_reader = new BufferedReader(new FileReader("Datasets/" + this.FILE_DATASET));
                
                while ((current_line = data_reader.readLine()) != null) {
                    // need to trim what it reads, if there is any space,  and store as object into the arraylist
                    String[] split_line = current_line.trim().split(",");
                    double[] dataset_tmp = new double[split_line.length - 1];
                    
                    for(int line =0; line < dataset_tmp.length; line++){
                        dataset_tmp[line] = Double.parseDouble(split_line[line]);
                    }
                    
                    sample_list.add(new Sample(dataset_tmp, Double.parseDouble(split_line[split_line.length-1])));
        
                }
                // closing the buffer reader
                data_reader.close();

        } catch (IOException e) {

            System.out.println("No file found with that name");
        }
        // it will return the list to array and then the array of the size of the list automatically with the zero
        return sample_list.toArray(new Sample[0]);
    }

// it finds out the output of the digits (10)
    private double[] classes_init(){

        final HashSet<Double> LABELS = new HashSet<>(); //labels container

        // adding the labels in HashSet as it does not accept doubles
        for(final Sample SAMPLE : this.COLLECTION){
            LABELS.add((double)SAMPLE.getLabel());
        }
        // creating a 
        final double[] CLASSES_TMP = new double[LABELS.size()];
        int counter = 0; //counter for hashset

        for(final double TARGET : LABELS){
            CLASSES_TMP[counter++] = TARGET;
        }
        Arrays.sort(CLASSES_TMP); //sorting the array

        return CLASSES_TMP;
    }

// takes the label value target
    private void oneHot_Gen(){
// it will loop through the whole collection in order to set 1.0 to the label of the sample
        for(final Sample SAMPLE : this.COLLECTION ){
            final double[] ONE_HOT = new double[this.CLASSES.length];
            for(int classes = 0; classes < this.CLASSES.length; classes++){
                ONE_HOT[classes] = SAMPLE.getLabel() == this.CLASSES[classes] ? 1.0 : 0.0;
            }  
            SAMPLE.setOneHot(ONE_HOT); //setting the output label
        }
    }


// ------------- GETTER METHODS -------------------

// retrieves the sample object
    public Sample getSample(final int INDEX){
        return this.COLLECTION[INDEX];
    }
// retrieves the size of the collection
    public int getSize(){
        return COLLECTION.length;
    }
// retrieves the class label
   public double getLabel(final int INDEX){
    return CLASSES[INDEX];
    }
 
}

public class Main {
    

    public static void main(String[] args){
        Utils util = new Utils();

        // filling up the train dataset
        util.Reader("cw2DataSet1.csv", util.train_dataset, util.train_labels);
        util.print_array(util.train_dataset, util.train_labels);

        // filling up the test dataset
        util.Reader("cw2DataSet2.csv", util.test_dataset, util.test_labels);
        util.print_array(util.test_dataset, util.test_labels);


    }
 

}

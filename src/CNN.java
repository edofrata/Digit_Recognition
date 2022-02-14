public class CNN {
    Model_Settings model = new Model_Settings();
    double[][] weights_conv2d = new double[model.filters][model.size_kernel[0] * model.size_kernel[1]]; //convolutional array of arrays

    // method that applies convoluttional to the input image
    public double Conv2d(int feature_maps, int[] size_kernel, int[] dataset) {

        


        
        return 0.0;
    }

    // method that flattens the array
    public double Flatten(int[][] array) {

        return 0.0;
    }

    public double Linear_Transformation(double pixel, double weight) {



        return 0.0;
    }
// initializing the matrix of weights
    public double[][] Weights_Matrix(int[][] kernel_size) {

        // Utils util = new Utils();
      
        // double weight_formula = Math.sqrt(2 / util.train_dataset[1].length);

    // HeNormal Weight initialization
        for(int weight_row = 0; weight_row < kernel_size.length; weight_row++){
            for(int weight_col = 0; weight_col < kernel_size[weight_row].length; weight_col++){

                weights_conv2d[weight_row][weight_col] = Math.random() * 0.176776695296637;
            }
        }

        return weights_conv2d;
    }
}

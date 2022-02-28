public class Loss {
    
    public static class MSE{

    	/**
		 * 
		 * @param classes_pred the output predicted from the model 
         * @param classes_act the output which should have been predicted
		 * @return double
		 */
        public static double function(final double[] classes_pred, final double[] classes_act){
            double sum =0.0;

        	for(int index = 0 ; index < classes_pred.length; index++){
				sum += Math.pow((classes_pred[index] - classes_act[index]), 2);
			}
            return sum/classes_pred.length;
        }
        /**
		 * 
		 * @param y_hat 
		 * @return
		 */
        public static double derivative(final double y_hat, final double y){ return 2*(y_hat - y); }
    }
    
    public static class MAE{

        /**
		 * 
		 * @param y_hat the output predicted from the model 
         * @param y the output which should have been predicted
         * @param classes array of all the output classes
		 * @return double
		 */
        public static double function(final double[] classes_pred, final double[] classes_act){
            double sum =0.0;

            for(int index = 0 ; index < classes_pred.length; index++){
                sum += Math.abs((classes_pred[index] - classes_act[index]));
            }

            return sum/classes_pred.length;
        }
        /**
		 * 
	     * @param y_hat the output predicted from the model 
         * @param T the output (target) which should have been predicted
		 * @return double
		 */
        public static double derivative(final double y_hat, final double T){ return y_hat > T ? 1 : -1; }
    }

    public static class Cross_Entropy{
        /**
		 * 
         * @param y_hat the output predicted from the model 
         * @param T the output (target) which should have been predicted
		 * @return double
		 */
        public static double function(final double y_hat, final double T){  
            if(T == 1) return -(Math.log(y_hat));
            else       return -(Math.log(1 - y_hat));
        }
        /**
         * 
         * @param PREV_Y output of the previous neuron (softmax output)
         * @param T target value
         * @return
         */
        public static double derivative(final double PREV_Y, final double T){ return PREV_Y - T; }
    }


}
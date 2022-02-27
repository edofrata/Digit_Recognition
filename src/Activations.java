public class Activations{

	public static class Sigmoid {
		public static double weight_inizialization(int number_inputs){ return Math.sqrt(2.0 / number_inputs);}
		/**
		 * 
		 * @param X input
		 * @return function
		 */
		public static double function(final double X){ return (1/(1 + Math.exp(-X))); }
		/**
		 * 
		 * @param F output
		 * @return derivative
		 */
		public static double derivative(final double F){ return F*(1-F); }
	}

	public static class Tanh {
		public static double weight_inizialization(int number_inputs){ return Math.sqrt(2.0 / number_inputs);}
		/**
		 * 
		 * @param X input
		 * @return function
		 */
		public static double function(final double X){ return (2/(1 + Math.exp(-2*X))) -1; }
		/**
		 * 
		 * @param F output
		 * @return derivative
		 */
		public static double derivative(final double F){ return 1- Math.pow(F, 2); }
	}

	public static class Swish {
		public static double weight_inizialization(int number_inputs){ return Math.sqrt(2.0 / number_inputs);}
		/**
		 * 
		 * @param X input
		 * @return function
		 */
		public static double function(final double X){ return X * Sigmoid.function(X); }
		/**
		 * 
		 * @param X input
		 * @param F output
		 * @return derivative
		 */
		public static double derivative(final double X, final double F){ return F + Sigmoid.function(X) * (1-F); }
	}

	public static class Relu{

		// weight initialization for the Relu, which is a rivisited formula from Xavier adapted for ReLu
		public static double weight_inizialization(int number_inputs){ 
			final double LOWER = -(1.0 / Math.sqrt(number_inputs));
			final double UPPER =  (1.0 / Math.sqrt(number_inputs));
			return LOWER + Math.random() * (UPPER - LOWER);
		}
		/**
		 * 
		 * @param X input
		 * @return function
		 */
		public static double function(final double X){ return X < 0? 0: X; }
		/**
		 * 
		 * @param X input
		 * @return derivative
		 */
		public static double derivative(final double X){ return X < 0? 0 : 1; }
	}

	public static class Lrelu {

	// weight initialization for the Relu, which is a rivisited formula from Xavier adapted for ReLu
		public static double weight_inizialization(int number_inputs){ 
			final double LOWER = -(1.0 / Math.sqrt(number_inputs));
            final double UPPER =  (1.0 / Math.sqrt(number_inputs));
			return LOWER + Math.random() * (UPPER - LOWER);
		}
		/**
		 * 
		 * @param X input
		 * @return function
		 */
		public static double function(final double X){ return Prelu.function(X, 0.01); }
		/**
		 * 
		 * @param X input
		 * @return derivative
		 */
		public static double derivative(final double X){ return Prelu.derivative(X, 0.01); }
	}

	public static class Selu {
		private static final double LAMBDA =1.05070098, ALPHA = 1.67326324;
		public static double weight_inizialization(int number_inputs){ return Math.sqrt(2.0 / number_inputs);}
		/**
		 * 
		 * @param X input
		 * @return function
		 */
		public static double function(final double X){
			return X < 0? LAMBDA * (ALPHA * (Math.exp(X) - 1)): LAMBDA * X;
		}
		/**
		 * 
		 * @param X input
		 * @return derivative
		 */
		public static double derivative(final double X){
			return X < 0? LAMBDA * (ALPHA * Math.exp(X)): LAMBDA;
		}
	}

	public static class Prelu {
		public static double weight_inizialization(int number_inputs){ return Math.sqrt(2.0 / number_inputs);}
		/**
		 * 
		 * @param X input
		 * @param A parameter
		 * @return function
		 */
		public static double function(final double X, final double A){ return X < 0? A*X: X; }
		/**
		 * 
		 * @param X input
		 * @param A parameter
		 * @return derivative
		 */
		public static double derivative(final double X, final double A){ return X < 0? A: 1; }
	}
	public static class Softplus {
		public static double weight_inizialization(int number_inputs){ return Math.sqrt(2.0 / number_inputs);}
		/**
		 * 
		 * @param X input
		 * @return function
		 */
		public static double function(final double X){ return Math.log(Math.exp(X) + 1); }
		/**
		 * 
		 * @param X input
		 * @return derivative
		 */
		public static double derivative(final double X){ return 1 / (1 + Math.exp(-X)); }
	}

	public static class Softmax {
		/**
         * Xavier Weight initialization
         * @param N_INPUTS umber of inputs of the current node
         * @return    Random double with a uniform probability distribution
         */
        public static double weight_inizialization(final int N_INPUTS){
            final double LOWER = -(1.0 / Math.sqrt(N_INPUTS));
            final double UPPER =  (1.0 / Math.sqrt(N_INPUTS));
            return LOWER + Math.random() * (UPPER - LOWER);
        }

		/**
		 * 
		 * @param I non-linear output
		 * @param classes non-linear output
		 * @return function
		 */
		public static double function(final Neuron.Node NODE, final Neuron.Node[] F_CLASSES){
			double sum = 0;
			double max_value = F_CLASSES[0].getForward_Linear(); 
			for(int index = 0 ; index < F_CLASSES.length; index++){
				max_value = Math.max(F_CLASSES[index].getForward_Linear(), max_value);
			}

			final double F = Math.exp(NODE.getForward_Linear() - max_value);
			for(int index = 0 ; index < F_CLASSES.length; index++){
				sum += Math.exp(F_CLASSES[index].getForward_Linear() - max_value);
			}

			return F/sum;
		}

		// Softmax it is non-differentiable, therefore has no derivative
	}
    
}
public class Activations{

	public static class Sigmoid {
		/**
		 * 
		 * @param X input
		 * @return function
		 */
		public static double function(final double X){ return (1.0 / (1.0 + Math.exp(-X))); }
		/**
		 * 
		 * @param F output
		 * @return derivative
		 */
		public static double derivative(final double F){ return F * (1.0 - F); }

		/**
		 * Xavier Weight initialization
		 * @param N_INPUTS umber of inputs of the current node
		 * @return	Random double with a uniform probability distribution
		 */
		public static double weight_inizialization(final int N_INPUTS){
			final double LOWER = -(1.0 / Math.sqrt(N_INPUTS));
			final double UPPER =(1.0 / Math.sqrt(N_INPUTS));
			return LOWER + Math.random() * (UPPER - LOWER);
		}
	}

	public static class Swish {
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
		public static double derivative(final double X, final double F){ return F + Sigmoid.function(X) * (1.0-F); }
		/**
		 * He Weight Initialization
		 * @param N_INPUTS number of inputs of the current node
		 * @return random double with a Gaussian probability
		 */
		public static double weight_inizialization(final int N_INPUTS){
			final double LOWER = -  Math.sqrt(2.0 / ((double)N_INPUTS));
			final double UPPER =    Math.sqrt(2.0 / ((double)N_INPUTS));
			return LOWER + Math.random() * (UPPER - LOWER);
		}
		/**
		 * Normalized He Weight Initialization
		 * @param N_INPUTS number of inputs of the current node
		 * @param N_OUPUTS number of output of the current node
		 * @return Random double with a uniform probability distribution
		 */
		public static double weight_inizialization(final int N_INPUTS, final int N_OUPUTS){
			final double LOWER = -  Math.sqrt(2.0 / ((double)N_INPUTS * N_OUPUTS));
			final double UPPER =    Math.sqrt(2.0 /  ((double)N_INPUTS * N_OUPUTS));
			return LOWER + Math.random() * (UPPER - LOWER);
		}
	}

	public static class Relu{

		/**
		 * 
		 * @param X input
		 * @return function
		 */
		public static double function(final double X){ return X < 0.0? 0.0: X; }
		/**
		 * 
		 * @param X input
		 * @return derivative
		 */
		public static double derivative(final double X){ return X > 0.0? 1.0 : 0.0; }
		/**
		 * He Weight Initialization
		 * @param N_INPUTS number of inputs of the current node
		 * @return random double with a Gaussian probability
		 */
		public static double weight_inizialization(final int N_INPUTS){
			final double LOWER = -  Math.sqrt(2.0 / ((double)N_INPUTS));
			final double UPPER =    Math.sqrt(2.0 / ((double)N_INPUTS));
			return LOWER + Math.random() * (UPPER - LOWER);
		}
		/**
		 * Normalized He Weight Initialization
		 * @param N_INPUTS number of inputs of the current node
		 * @param N_OUPUTS number of output of the current node
		 * @return Random double with a uniform probability distribution
		 */
		public static double weight_inizialization(final int N_INPUTS, final int N_OUPUTS){
			final double LOWER = -  Math.sqrt(2.0 / ((double)N_INPUTS * N_OUPUTS));
			final double UPPER =    Math.sqrt(2.0 /  ((double)N_INPUTS * N_OUPUTS));
			return LOWER + Math.random() * (UPPER - LOWER);
		}
	}

	public static class Lrelu {

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
		/**
		 * He Weight Initialization
		 * @param N_INPUTS number of inputs of the current node
		 * @return random double with a Gaussian probability
		 */
		public static double weight_inizialization(final int N_INPUTS){
			final double LOWER = -  Math.sqrt(2.0 / ((double)N_INPUTS));
			final double UPPER =    Math.sqrt(2.0 / ((double)N_INPUTS));
			return LOWER + Math.random() * (UPPER - LOWER);
		}
		/**
		 * Normalized He Weight Initialization
		 * @param N_INPUTS number of inputs of the current node
		 * @param N_OUPUTS number of output of the current node
		 * @return Random double with a uniform probability distribution
		 */
		public static double weight_inizialization(final int N_INPUTS, final int N_OUPUTS){
			final double LOWER = -  Math.sqrt(2.0 / ((double)N_INPUTS * N_OUPUTS));
			final double UPPER =    Math.sqrt(2.0 /  ((double)N_INPUTS * N_OUPUTS));
			return LOWER + Math.random() * (UPPER - LOWER);
		}

	}

	public static class Prelu {
	/**
		 * 
		 * @param X input
		 * @param A parameter
		 * @return function
		 */
		public static double function(final double X, final double A){ return X < 0.0? A*X: X; }
		/**
		 * 
		 * @param X input
		 * @param A parameter
		 * @return derivative
		 */
		public static double derivative(final double X, final double A){ return X < 0.0? A: 1.0; }
		/**
		 * He Weight Initialization
		 * @param N_INPUTS number of inputs of the current node
		 * @return random double with a Gaussian probability
		 */
		public static double weight_inizialization(final int N_INPUTS){
			final double LOWER = -  Math.sqrt(2.0 / ((double)N_INPUTS));
			final double UPPER =    Math.sqrt(2.0 / ((double)N_INPUTS));
			return LOWER + Math.random() * (UPPER - LOWER);
		}
		/**
		 * Normalized He Weight Initialization
		 * @param N_INPUTS number of inputs of the current node
		 * @param N_OUPUTS number of output of the current node
		 * @return Random double with a uniform probability distribution
		 */
		public static double weight_inizialization(final int N_INPUTS, final int N_OUPUTS){
			final double LOWER = -  Math.sqrt(2.0 / ((double)N_INPUTS * N_OUPUTS));
			final double UPPER =    Math.sqrt(2.0 /  ((double)N_INPUTS * N_OUPUTS));
			return LOWER + Math.random() * (UPPER - LOWER);
		}
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
		public static double function(final Node NODE, final Node[] F_CLASSES){
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
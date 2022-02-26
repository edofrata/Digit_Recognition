public class Activations{

	public static class Linear {
		public static double weight_inizialization(int number_inputs){ return Math.sqrt(2.0 / number_inputs);}
		/**
		 * 
		 * @param X input
		 * @param A parameter
		 * @return function
		 */
		public static double function(final double X, final double A){ return A*X; }
		/**
		 * 
		 * @param X input
		 * @param F output
		 * @return derivative
		 */
		public static double derivative(final double X, final double F){ return F/X; }
	}

	public static class Binary {
		public static double weight_inizialization(int number_inputs){ return Math.sqrt(2.0 / number_inputs);}
		/**
		 * 
		 * @param X intput
		 * @return function
		 */
		public static double function(final double X){ return X < 0? 0: 1; }
		/**
		 * 
		 * @param X input
		 * @return derivative
		 */
		public static double derivative(final double X){ return X != 0? 0: 1; }
	}

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

	public static class Gelu {
		public static double weight_inizialization(int number_inputs){ return Math.sqrt(2.0 / number_inputs);}
		/**
		 * 
		 * @param X input
		 * @return function
		 */
		public static double function(final double X){ 
			double cdf = 0.5 * (1.0 + erf(X / Math.sqrt(2.0)));
			return X * cdf;
		}
		/**
		 * 
		 * @return derivative
		 */
		public static double derivative(){ return 0.0; }
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


	public static class Elu {
		public static double weight_inizialization(int number_inputs){ return Math.sqrt(2.0 / number_inputs);}
		/**
		 * 
		 * @param X input
		 * @param A parameter
		 * @return function
		 */
		public static double function(final double X, final double A){ return X < 0? A*(Math.exp(X)-1): X; }
		/**
		 * 
		 * @param X input
		 * @param A parameter
		 * @param F output
		 * @return derivative
		 */
		public static double derivative(final double X, final double A, final double F){ return X < 0? F+A: 1; }
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
         * Normalized Xavier Weight initialization
         * @param N_INPUTS number of inputs of the current node
         * @param N_OUPUTS number of output of the current node
         * @return Random double with a uniform probability distribution
         */
        public static double weight_inizialization(final int N_INPUTS, final int N_OUPUTS){
            final double LOWER = -(6.0 / Math.sqrt(N_INPUTS + N_OUPUTS));
            final double UPPER =  (6.0 / Math.sqrt(N_INPUTS + N_OUPUTS));
            return LOWER + Math.random() * (UPPER - LOWER);
        }
		/**
		 * 
		 * @param I non-linear output
		 * @param classes non-linear output
		 * @return function
		 */
		public static double function(final int I, final double[] CLASSES){
			double sum = 0; 
			final double F = Math.exp(CLASSES[I]);
	
			for(int index = 0 ; index < CLASSES.length; index++){
				sum += Math.exp(CLASSES[index]);
			}

			return F/sum;
		}
		/**
		 * 
		 * @param I index of output from softmax interested class
		 * @param F_CLASSES output from softmax all classes
		 * @return derivative
		 */
		public static double derivative(final int I, final double[] F_CLASSES){
			double sum = 0;
			final double F = Math.exp(F_CLASSES[I]);

			for(int index = 0 ; index < F_CLASSES.length; index++){
				// sum += F == F_CLASSES[index]? F * (1 - F_CLASSES[index]): -F * F_CLASSES[index];
				sum += Math.exp(F_CLASSES[index]);
			}

			return F/sum;
		}
	}
    
	/**
	 * Gauss error function
	 * @param z subject to catastrophic cancellation when z in very close to 0
	 * @return Gauss error
	 */
	private static double erf(final double z) {
		
		double t = 1.0 / (1.0 + 0.5 * Math.abs(z));

		// use Horner's method
		double ans = 1 - t * Math.exp( -z*z   -   1.26551223 +
											t * ( 1.00002368 +
											t * ( 0.37409196 + 
											t * ( 0.09678418 + 
											t * (-0.18628806 + 
											t * ( 0.27886807 + 
											t * (-1.13520398 + 
											t * ( 1.48851587 + 
											t * (-0.82215223 + 
											t * ( 0.17087277))))))))));
		return z >= 0? ans: -ans;
    }
}
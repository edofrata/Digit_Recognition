
// initializing class for NODE
 public class Node{
        
        private double output_value;            // output of pixel
        private double forward_linearSum;    //output of the linear transformation in forward prop 
        private double back_linearSum;       //output of the linear transformation in back prop
        private double chain_rule;
        private double partial_derivative;
        private double derivative;

       public Node(){}

       
       // ------- SETTER METHODS --------
    
        // summing up in order to set output
        public void setNode_Value(final double VALUE){
            this.output_value       = VALUE;
            this.back_linearSum     = this.forward_linearSum;
            this.forward_linearSum  = 0;
        }
        // summing up in order to set chain rule
        public void addTo_Chain(final double VALUE){
            this.chain_rule += VALUE;
        }
        // summing up in order to set linear output
        public void addTo_Linear(final double VALUE){
            this.forward_linearSum += VALUE;
        }
        // summing up setin order to partial derivative
        public void setPartial_Derivative(final double VALUE){
            this.partial_derivative = VALUE;
            this.back_linearSum = 0;
        }
        // summing up in order to set the derivative
        public void set_DerivativeSum(){
            this.derivative = this.partial_derivative * this.chain_rule;
            this.chain_rule = 0;
        }
           
        // ------- GETTER METHODS --------
    
        // get output
        public double getOutputNode(){
            return this.output_value;
        }
        //  get  back linear output
        public double getBack_Linear(){
            return this.back_linearSum;
        }
        //  get forward linear output
        public double getForward_Linear(){
            return this.forward_linearSum;
        }
        // get the derivative
        public double getDerivative(){
            return this.derivative;
        }
    }


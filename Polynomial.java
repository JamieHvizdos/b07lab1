public class Polynomial {
    double [] coefficients;
    
    public Polynomial (){
        coefficients = new double[] {0};
    }

    public Polynomial (double [] values){
        coefficients = new double[values.length];
        for (int i = 0; i <values.length; i++){
            coefficients [i] = values [i];
        }
    }

    public Polynomial add (Polynomial other){
        int maxLength = 0;
        int minLength = 0;
        double polyArray[];
        boolean otherIsLonger;
        
        if (other.coefficients.length > coefficients.length){
            maxLength = other.coefficients.length;
            minLength = coefficients.length;
            otherIsLonger = true;
        } else {
            maxLength = coefficients.length;
            minLength = other.coefficients.length;
            otherIsLonger = false;
        }
        polyArray = new double[maxLength];
        
        for (int i = 0; i < maxLength; i++){
            if (i > minLength - 1){
                if (otherIsLonger){
                    polyArray [i] = other.coefficients[i];
                } else {
                    polyArray [i] = coefficients[i];
                }
            } else {
                polyArray[i] = coefficients[i] + other.coefficients[i];
            }
        }

        Polynomial newPolynomial = new Polynomial(polyArray);
        return newPolynomial;
    }

    public double evaluate (double x){
        double sum = 0;
        for (int i = 0; i < coefficients.length; i++){
           
           sum += coefficients[i] * Math.pow(x,i);
        }
        return sum;
    }

    public boolean hasRoot (double x){
        double sum = evaluate(x);
        if (sum == 0){
            return true;
        } else {
            return false;
        }
    }

}
/*
11.(Mandatory) Sum of Fractions 
Given two fraction in the form int[] {p, q} where p and q are integers. Find the sum of two fractions. (Note: The result should be in reduced form)  
Eg: Fraction 1 = {1, 2}, Fraction 2 = {1, 6}. Sum Expected = {2, 3} 
here numerators are f1[0] & f2[0]
denominators are f1[1] & f2[1] 


*/

public class SumOfFractions {

	public static void main(String[] args) {
		int fraction1[] = {1, 4};
		int fraction2[] = {2, 4};
		
		int result[] = sumOfFractions(fraction1, fraction2);
		System.out.print( "["+result[0]+ ", "+result[1]+"]");
	}

	private static int[] sumOfFractions(int[] f1, int[] f2) {
		int numerators;
		int denominators;
		int returnValue[] = new int[2];
		
		denominators = f1[1]*f2[1];
		numerators = (f1[0]*f1[1]) + (f2[0]*f2[1]);
		int divisor = gcdCalculation(numerators, denominators);
		returnValue[0] = numerators/divisor;
		returnValue[1] = denominators/divisor;
		
		return returnValue;
	}
	
	static int gcdCalculation(int numerators, int denominators) {
		if(numerators == 0)
			return denominators;
		
		return gcdCalculation((denominators % numerators), numerators);	
	}

}

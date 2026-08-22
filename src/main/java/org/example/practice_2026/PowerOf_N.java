/*
Find if given integer is a power of ten without using math.pow() function. 

input:  x=10   

output:  true   

explanation : 10^1 =10; 

100 :  10^2 

1000 : 10^3 

input: x=50   

output: false   

Example:
Let's x = 50 .506 -0
   log10(x) = log10(100)  = log10(10)^2 = 2(log10(10)) = 2 * 1 = 2

*/ 
public class PowerOf_N {
	public static void main(String[] args) {
		int num = 9;
		int base = 3;
		System.out.println(powerOf_N(num, base));
		//System.out.println(isPowerOf_n(num, x));
	}

	private static boolean powerOf_N(int num, int base) {
		if(num == 1) return true;
		boolean flag = false;
		while (num % base == 0) {
			if(num == base) {
				flag = true;
			}
			num /= base;		
		}		
		return flag;
	}
	
	/*
	public static boolean isPowerOf_n(int n, int base) {
        return base > 0 && n % base == 0;
    }
    */
}

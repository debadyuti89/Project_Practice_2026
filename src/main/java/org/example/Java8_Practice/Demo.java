package org.example.Java8_Practice;

public class Demo {

	int thousand=1000, fivehun=500, onehun=100;
	
	private static void countNotes(int amt) {
		int countThousand=0;
		int countFive=0;
		int countOne=0;
		if(amt>0) {
			if(amt>=1000) {
				countThousand = amt/1000;
				amt = amt%1000;
			}
			if(amt>=500){
				countFive = amt/500;
				amt = amt%500;
			}
			if(amt>=100) {
				countOne = amt/100;
				amt = amt%100;
			}
		}
		
		System.out.println("Thousand notes = "+countThousand+", Five hundred notes = "+countFive+", One hundred = "+countOne);
	}

	public static void main(String[] args) {
		countNotes(8670);
	}
}

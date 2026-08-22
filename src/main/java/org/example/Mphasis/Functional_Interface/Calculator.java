package org.example.Mphasis.Functional_Interface;

public class Calculator {

	public static void main(String[] args) {
		Addition addition = (a, b) -> a + b;
		System.out.println( addition.add(5, 10) );
	}
}

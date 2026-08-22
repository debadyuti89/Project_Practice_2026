package org.example.Mphasis;

public class MaximalRectangleHistogram {

	public static int maxRectangle(char input[][]){
        int temp[] = new int[input[0].length];
        LargestRectangleHistogram lrh = new LargestRectangleHistogram();
        int maxArea = 0;
        int area = 0;
        for(int i=0; i < input.length; i++){
            for(int j=0; j < temp.length; j++){
                if(input[i][j] == '0'){
                    temp[j] = 0;
                }else{
                    temp[j] +=  Character.getNumericValue(input[i][j]); //input[i][j];
                }
            }
            area = lrh.largestRectangleArea(temp);
            if(area > maxArea){
                maxArea = area;
            }
        }
        return maxArea;
    }
	
	public static void main(String[] args) {
		char input[][] = {{'1','1','1','0'},
		                {'1','1','1','1'},
		                {'0','1','1','0'},
		                {'0','1','1','1'},
		                {'1','0','0','1'},
		                {'1','1','1','1'}};
		System.out.println("Max rectangle is of size " + maxRectangle(input));
		System.out.println("Max rectangle is of size " + maxRectangle(new char[][] {{'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}}));
	}
}

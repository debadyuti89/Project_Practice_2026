package org.example.Mphasis;

import java.util.HashMap;
import java.util.Map;

/*
8. (Mandatory) You have been given a file of IP addresses.
There are some IP addresses like 10.0.0.1,10.0.0.2 etc. We need to find out which IP address is the most visited
INPUT: {"10.0.0.1 satish","10.0.0.2 Vinay","10.0.0.1 Deepa"};
O/P: "10.0.0.1"
INPUT: {"10.0.0.1 satish","10.0.0.2 Vinay","10.0.0.1 satish"};
O/P: "10.0.0.1"
*/

public class IP_Aaddresses {

	public static void main(String[] args) {
		String input[] = {"10.0.0.1 satish","10.0.0.2 Vinay","10.0.0.3 Deepa","10.0.0.4 Deepa"};
		
		System.out.println("Most visited IP address is "+mostVisitedIP(input) );
	}

	private static String mostVisitedIP(String[] inputString) {
		Map<String, Integer> mapIP = new HashMap<>(); // If we want ascending order ()
		int maxValue = Integer.MIN_VALUE;
		String maxIP = "";
		
		for(String eachString:inputString) {
			String[] splitElements = eachString.split(" ");
			mapIP.put(splitElements[0], mapIP.getOrDefault(splitElements[0], 0)+1);
		}
		
		//System.out.println(mapIP);
		
		for (Map.Entry<String, Integer> entry : mapIP.entrySet()) {
			System.out.println(entry.getKey());
			if(entry.getValue() > maxValue) {
				maxValue = entry.getValue();
				maxIP= entry.getKey();
			}
		}
		
		return maxIP;
	}

}

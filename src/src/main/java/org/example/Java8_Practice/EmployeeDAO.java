package org.example.Java8_Practice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeDAO {

	public static List<Employee> getAll(){
		
		return Stream.of(
				new Employee(101, "Debadyuti Banerjee", "IT", "A", Arrays.asList("9903003605", "7098640465"), 50356.89),
				new Employee(102, "Runu Banerjee", "HR", "A", Arrays.asList("44465544", "87828282"), 90356.00),
				new Employee(103, "Basudev Banerjee", "IT", "A", Arrays.asList("758775822", "042424424"), 70356.40),
				new Employee(104, "Paromita Banerjee", "Network", "C", Arrays.asList("3836987", "20544248"), 30356.00),
				new Employee(105, "Pronita Banerjee", "HR", "B", Arrays.asList("64546546", "82787789"), 40356.45),
				new Employee(106, "Sreya Roy", "Network", "B", Arrays.asList("8888888", "9999999"), 60356.00),
				new Employee(107, "Koel Bhattacharjee", "IT", "A", Arrays.asList("11111111", "55555555"), 70000.00)
				).collect(Collectors.toList());
	}
}

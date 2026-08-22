package org.example.Java8_Practice;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class EmployeeMain {
	
	long sum (int a, long b) {
		return a+b;
	}
	
	long sum (long a, int b) {
		return a+b;
		
	}
	
	static Employee getEmployeeByName(String name) throws Exception {
		
		List<Employee> employees = EmployeeDAO.getAll();
		
		return employees.parallelStream()
				.filter(emp -> emp.getEmpName().equalsIgnoreCase(name))
				.findAny()
				.orElseThrow(() -> new Exception("no name is present with this name = "+name));
		
	}

	public static void main(String[] args) throws Exception {
		
		double avgSalary = EmployeeDAO.getAll().parallelStream()
									.filter(emp -> emp.getGrade().equalsIgnoreCase("A"))
									.map(Employee::getSalary)
									.mapToDouble(i -> i)
									.average().getAsDouble();
		
		System.out.println("Average Salary = "+avgSalary);
		
		double sumSalary = EmployeeDAO.getAll().parallelStream()
									.filter(emp -> emp.getGrade().equalsIgnoreCase("A"))
									.map(Employee::getSalary)
									.mapToDouble(i -> i)
									.sum();

		System.out.println("Sum of A grade employee's Salary = "+sumSalary);
		
		double maxSalary = EmployeeDAO.getAll().parallelStream()
									.map(Employee::getSalary)
									.mapToDouble(i -> i)
									.min().getAsDouble();
		
		System.out.println("Employee's Max Salary = "+maxSalary);
		
		Map<String, Employee> topEmployees = EmployeeDAO.getAll().parallelStream()
									.collect(Collectors.toMap(
			                				Employee::getDepartment,
			                				e -> e,
			                                BinaryOperator.maxBy(Comparator.comparingDouble(Employee::getSalary)) 
					                ));
		Set<Entry<String, Employee>> entrySet = topEmployees.entrySet();
		
		System.out.println("print highest salary of an employee from each department "+ topEmployees);
		
//		Map<String, Double> topSalWithDep = EmployeeDAO.getAll().parallelStream()
//				.collect(Collectors.groupingBy(Employee::getDepartment, Collectors.collectingAndThen(Employee::getSalary)));
		
		//getEmployeeByName("Kallol");
		
		/*
		 * // creating a string array String[] str = new String[5];
		 * 
		 * // Setting value for 2nd index str[2] = "Deba";
		 * 
		 * // It returns an empty instance of Optional class Optional<String> empty =
		 * Optional.empty(); System.out.println(empty);
		 * 
		 * Optional<String> ofNullable = Optional.ofNullable(str[3]);
		 * System.out.println("ofNullable"+ofNullable);
		 * 
		 * // It returns a non-empty Optional Optional<String> value =
		 * Optional.of(str[3]); System.out.println(value);
		 */
        
        
        
	}

}

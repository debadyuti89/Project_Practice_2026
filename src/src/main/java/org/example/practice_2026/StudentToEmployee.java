package org.example.practice_2026;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class StudentToEmployee {

	public static void main(String[] args) {
		
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
		System.out.println(Arrays.asList(1, 2, 3, 4, 5).stream()
								.filter(t -> list.contains(4))
								.findAny()
								.orElse(null));
		
		List<Student> stuList = new LinkedList<>();
		stuList.add(new Student("Debadyuti", 1));
		stuList.add(new Student("Pritha", 2));
		stuList.add(new Student("Koyel", 3));
		stuList.add(new Student("Runu", 4));
		stuList.add(new Student("Basudev", 5));
		stuList.add(new Student("Priya", 6));
		stuList.add(new Student("Srinanda", 7));
		stuList.add(new Student("Srija", 8));
		stuList.add(new Student("Maitreye", 9));
		stuList.add(new Student("Kallol", 10));
		
		List<Employee> empList = new LinkedList<>();
		
		for(Student s : stuList) {
			Employee emp = new Employee();
			emp.setName(s.getName());
			emp.setId(s.getId());
			empList.add(emp);
		}
		
		stuList.parallelStream()
				.sorted(Comparator.comparing(Student::getId))
				.filter(t -> t.getName().startsWith("P"))
				.forEach(System.out::println);
		System.out.println(empList);
		
		
		
	}
	
	/*	public static void main(String[] args) {
	List<Employee> empolyee = new ArrayList<>();
	
	List<Employee> emp = empolyee.stream().filter(e -> e.getSalary()>10000 && e.getSalary()<50000).collect(Collectors.toList());
	emp.stream().forEach(System.out::println);
	
	System.out.println("Abc"+20+10); // Abc2010
	System.out.println(20+10+"Abc"); // 30Abc
}
*/

}

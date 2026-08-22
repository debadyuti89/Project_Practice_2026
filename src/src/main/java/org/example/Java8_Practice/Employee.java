package org.example.Java8_Practice;

import java.util.List;

public class Employee {

	private int empId; 
	private String empName;
	private String department;
	private String grade;
	private List<String> phone;
	private Double salary;
	
	public Employee() {}
	
	public Employee(int empId, String empName, String department, String grade, List<String> phone, Double salary) {
		this.empId = empId;
		this.empName = empName;
		this.department = department;
		this.grade = grade;
		this.phone = phone;
		this.salary = salary;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public List<String> getPhone() {
		return phone;
	}

	public void setPhone(List<String> phone) {
		this.phone = phone;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", department=" + department + ", grade=" + grade
				+ ", phone=" + phone + ", salary=" + salary + "]";
	}

	
	
	
}

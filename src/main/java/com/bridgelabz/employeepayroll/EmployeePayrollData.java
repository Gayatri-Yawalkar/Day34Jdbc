package com.bridgelabz.employeepayroll;
import java.time.LocalDate;
public class EmployeePayrollData {
	public Integer id;
	public String name;
	public Double salary;
	public LocalDate startDate;
	public EmployeePayrollData(Integer id,String name,Double salary) {
		this.id=id;
		this.name=name;
		this.salary=salary;
	}
	public EmployeePayrollData(Integer id,String name,Double salary,LocalDate startDate) {
		this(id,name,salary);
		this.startDate=startDate;
	}
	@Override
	public String toString() {
		return "id="+id+", Name='"+name+'\''+", Salary="+salary;
	}
}

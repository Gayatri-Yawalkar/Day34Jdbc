package com.bridgelabz.employeepayroll;
//Uc3
import java.util.List;
import java.util.Scanner;
public class EmployeePayrollService {
	public enum IOService {
		CONSOLE_IO,FILE_IO,DB_IO,REST_IO
	}
	public List<EmployeePayrollData> employeePayrollList;
	public EmployeePayrollDbService employeePayrollDbService;
	public EmployeePayrollService() {
		employeePayrollDbService=EmployeePayrollDbService.getInstance();
	}
	public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
		this();
		this.employeePayrollList=employeePayrollList;
	}
	public void writeEmployeePayrollData(IOService ioService) {
		if(ioService.equals(IOService.CONSOLE_IO)) {
			System.out.println("\nWriting Employee Payroll to Console\n"+employeePayrollList);
		} else if(ioService.equals(IOService.FILE_IO)) {
			new EmployeePayrollFileIOService().writeData(employeePayrollList);
		}
	}
	public void readEmployeePayrollData(Scanner sc) {
		System.out.println("Enter Employee id");
		int id=sc.nextInt();
		System.out.println("Enter Employee Name");
		String name=sc.next();
		System.out.println("Enter Employee Salary");
		double salary=sc.nextDouble();
		employeePayrollList.add(new EmployeePayrollData(id, name, salary));
	}
	public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) {
		if(ioService.equals(IOService.DB_IO)) {
			this.employeePayrollList=employeePayrollDbService.readData();
			return this.employeePayrollList;
		}
		return null;
	}
	public int updateEmployeeSalary(String name,double salary) {
		int result=employeePayrollDbService.updateSalary(name, salary);
		if(result==0) {
			return result;
		}
		EmployeePayrollData employeePayrollData=this.getEmployeePayrollData(name);
		if(employeePayrollData!=null) {
			employeePayrollData.salary=salary;
		}
		return result;
	}
	private EmployeePayrollData getEmployeePayrollData(String name) {
		EmployeePayrollData employeePayrollData;
		employeePayrollData=this.employeePayrollList.stream()
				.filter(employeePayrollDataItem->employeePayrollDataItem.name.equals(name))
				.findFirst()
				.orElse(null);
		return employeePayrollData;
	}
	public void printData(IOService ioService) {
		if(ioService.equals(IOService.FILE_IO)) {
			new EmployeePayrollFileIOService().printdata();
		}
	}
	public long countEntries(IOService ioService) {
		if(ioService.equals(IOService.FILE_IO)) {
			return new EmployeePayrollFileIOService().countEntries();
		}
		return 0;
	}
}

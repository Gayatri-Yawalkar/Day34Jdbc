package com.bridgelabz.junit;
//Uc6
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import com.bridgelabz.employeepayroll.EmployeePayrollData;
import com.bridgelabz.employeepayroll.EmployeePayrollService;
import com.bridgelabz.employeepayroll.EmployeePayrollService.IOService;
public class EmployeePayrollServiceDbTest {
	@Test
	public void givenEmployeePayrollDb_whenRetrived_shouldMatchEmployeeCount() {
		EmployeePayrollService employeePayrollService=new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData=employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		assertEquals(3,employeePayrollData.size());
	}
	@Test
	public void givenNewSalaryForEmployee_whenUpdated_shouldSyncWithDb() {
		EmployeePayrollService employeePayrollService=new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmployeeSalary("Gayatri",50002.00);
		boolean result=employeePayrollService.checkEmployeePayrollSyncWithDb("Gayatri");
		assertTrue(result);
	}
	@Test
	public void givenDateRange_whenRetrived_shouldMatchEmployeeCount() {
		EmployeePayrollService employeePayrollService=new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		LocalDate startDate=LocalDate.of(2020,04,01);
		LocalDate endDate=LocalDate.now();
		List<EmployeePayrollData> employeePayrollData=employeePayrollService.readEmployeePayrollForDateRange(IOService.DB_IO,startDate,endDate);
		assertEquals(3,employeePayrollData.size());
	}
	@Test
	public void givenPayrollData_whenAverageSalaryRetrievedByGender_shouldReturnProperValue() {
		EmployeePayrollService employeePayrollService=new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Map<String,Double> averageSalryByGender=employeePayrollService.readAverageSalaryByGender(IOService.DB_IO);
		assertTrue(averageSalryByGender.get("F").equals(30001.00) && averageSalryByGender.get("M").equals(20000.00));
	}
}

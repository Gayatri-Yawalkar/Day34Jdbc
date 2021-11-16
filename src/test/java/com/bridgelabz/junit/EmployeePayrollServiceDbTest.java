package com.bridgelabz.junit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
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
		List<EmployeePayrollData> employeePayrollData=employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmployeeSalary("Gayatri",50002.00);
		boolean result=employeePayrollService.checkEmployeePayrollSyncWithDb("Gayatri");
		assertTrue(result);
	}
}

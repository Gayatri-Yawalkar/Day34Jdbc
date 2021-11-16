package com.bridgelabz.junit;
//Uc2
import static org.junit.Assert.assertEquals;
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
}

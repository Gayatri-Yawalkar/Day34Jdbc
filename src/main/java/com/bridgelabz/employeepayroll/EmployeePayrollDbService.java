package com.bridgelabz.employeepayroll;
//Uc2
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class EmployeePayrollDbService {
	public List<EmployeePayrollData> readData(){
		String sql="SELECT * FROM employeepayroll;";
		List<EmployeePayrollData> employeePayrollList=new ArrayList<>();
		try {
			Connection connection=this.getConnection();
			Statement statement=connection.createStatement();
			ResultSet result=statement.executeQuery(sql);
			while(result.next()) {
				int id=result.getInt("id");
				String name=result.getString("name");
				double salary=result.getDouble("salary");
				LocalDate startDate=result.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name,salary,startDate));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return employeePayrollList;
	}
	private Connection getConnection() throws SQLException {
		String jdbcUrl="jdbc:mysql://localhost:3306/payrollservice?useSSL=false";
		String userName="root";
		String password="root";
		Connection con;
		System.out.println("Connecting to database:"+jdbcUrl);
		con=DriverManager.getConnection(jdbcUrl, userName, password);
		System.out.println("Coonection is successfull");
		return con;
	}
}

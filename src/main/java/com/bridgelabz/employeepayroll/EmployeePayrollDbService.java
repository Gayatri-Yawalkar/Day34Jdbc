package com.bridgelabz.employeepayroll;
//Uc6
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class EmployeePayrollDbService {
	private static EmployeePayrollDbService employeePayrollDbService;
	private PreparedStatement employeePayrollDataStatement;
	private EmployeePayrollDbService() {
	}
	public static EmployeePayrollDbService getInstance() {
		if(employeePayrollDbService==null) {
			employeePayrollDbService=new EmployeePayrollDbService();
		}
		return employeePayrollDbService;
	}
	public List<EmployeePayrollData> readData(){
		String sql="SELECT * FROM employeepayroll;";
		return this.getEmployeePayrollDataUsingDB(sql);
	}
	public List<EmployeePayrollData> getEmployeePayrollData(String name){
		List<EmployeePayrollData> employeePayrollList=null;
		if(this.employeePayrollDataStatement==null) {
			this.prepareStatementForEmployeeData();
		}
		try {
			employeePayrollDataStatement.setString(1,name);
			ResultSet resultSet=employeePayrollDataStatement.executeQuery();
			employeePayrollList=this.getEmployeePayrollData(resultSet);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	public List<EmployeePayrollData> getEmployeeDateRange(LocalDate startDate, LocalDate endDate) {
		String sql=String.format("SELECT * FROM employeepayroll WHERE start BETWEEN '%s' AND '%s';",
				       Date.valueOf(startDate),Date.valueOf(endDate));
		return this.getEmployeePayrollDataUsingDB(sql);
	}
	public Map<String,Double> getAverageSalaryByGender(){
		String sql="SELECT gender,AVG(salary) FROM employeepayroll GROUP BY gender;";
		Map<String,Double> genderToAverageSalaryMap=new HashMap<>();
		try (Connection connection=this.getConnection();){
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			while(resultSet.next()) {
				String gender=resultSet.getString("gender");
				Double salary=resultSet.getDouble("AVG(salary)");
				genderToAverageSalaryMap.put(gender, salary);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return genderToAverageSalaryMap;
	}
	private Connection getConnection() throws SQLException {
		String jdbcUrl="jdbc:mysql://localhost:3306/payrollservice?useSSL=false";
		String userName="root";
		String password="root";
		Connection con;
		System.out.println("Connecting to database:"+jdbcUrl);
		con=DriverManager.getConnection(jdbcUrl, userName, password);
		System.out.println("Connection is successfull");
		return con;
	}
	private List<EmployeePayrollData> getEmployeePayrollDataUsingDB(String sql) {
		List<EmployeePayrollData> employeePayrollList=new ArrayList<>();
		try (Connection connection=this.getConnection();){
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			employeePayrollList=this.getEmployeePayrollData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
		List<EmployeePayrollData> employeePayrollList=new ArrayList<>();
		try {
			while(resultSet.next()) {
				int id=resultSet.getInt("id");
				String name=resultSet.getString("name");
				double salary=resultSet.getDouble("salary");
				LocalDate startDate=resultSet.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name,salary,startDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	private void prepareStatementForEmployeeData() {
		try {
			Connection connection=this.getConnection();
			String sql="SELECT * FROM employeepayroll WHERE name=?";
			employeePayrollDataStatement=connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public int updateSalary(String name,double salary) {
		String sql=String.format("update employeepayroll set salary=%.2f where name='%s';",salary,name);
		try (Connection connection=this.getConnection();){
			Statement statement=connection.createStatement();
			return statement.executeUpdate(sql);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}

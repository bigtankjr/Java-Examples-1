
public class Employee
{	
	private double wage;
	private int hours;
	private String empLastName;
	private String empFirstName;
	private double empSalary;
	private int empNum;
	public int getEmpNum()
	{
		return empNum;
	}
	public void setEmpNum( int emp, int a )
	{	
		empNum = emp * a;
		
	}
	public String getEmpLastName()
	{
		return empLastName;
	}
	public String getEmpFirstName()
	{ return empFirstName;}
	
	public void setEmpFirstName(String name)
	{
		empFirstName = name;
	}
	public void setEmpLastName(String name)
	{
		empLastName = name;
	}
	public double getEmpSalary()
	{return empSalary;}
	
	
	public void setEmpSalary(double sal, int hours)
	{   
		empSalary = sal * hours;
	}
}

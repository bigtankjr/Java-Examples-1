import javax.swing.*;

	public class BicycleDemo
	{
		public static void main(String args[])
		{							
			Bicycle myBike = new Bicycle();
			
			/*myBike.height(26);
			myBike.model("Cyclone");
			myBike.wheels(3);
			myBike.model(108);
			Bicycle.height(24);
			Bicycle.model("Hurricane");
			Bicycle.int(3;
			Bicycle.model(108);
			Bicycle.wheels(2);
			Bicycle yourBike(myBike);*/
			Employee employ = new Employee();
			
			employ.setEmpNum( 12, 3 );
			System.out.println(employ.getEmpNum());
			
			CalculateFun calc = new CalculateFun();
			calc.addNum(2,3);
			System.out.println(calc.getNum());
			calc.subNum(12,7);
			System.out.println(calc.getNum());
			calc.multNum(2,7);
			System.out.println(calc.getNum());
			calc.divNum(12,2);
			System.out.println(calc.getNum());
			calc.modNum(16,3);
			System.out.println(calc.getNum());
			
			JOptionPane jop = new JOptionPane();
			
			employ.setEmpSalary(25.25, 2080);
			System.out.println(employ.getEmpSalary());
			employ.setEmpFirstName("Benjamin");
			employ.setEmpLastName("Levels");
			
		}
		
	}

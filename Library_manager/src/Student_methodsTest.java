import static org.junit.Assert.*;

import org.junit.Test;


public class Student_methodsTest {

	@Test
	public void testRegisterMember() {
		Student_methods std_method = new Student_methods();
		assertTrue(std_method.registerMember("1357973", "7777", "Odd", "Mathematics")==true);
	}

	@Test
	public void testLoginMember() {
		Student_methods std_method = new Student_methods();
		assertTrue(std_method.loginMember("1011234", "1234")==true);
	}
	
	@Test
	public void testIdValid(){
		Student_methods std_method = new Student_methods();
		assertTrue(std_method.idValid("1011234")==false);
	}
	
	@Test
	public  void testNumRent(){
		Student_methods std_method = new Student_methods();
		assertTrue(std_method.numRentRecord("1212919")==0);
	}
}

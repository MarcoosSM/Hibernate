package interfaces;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import control.Client_Managment;
import entities.Client;

public class MainTest {

	private static SessionFactory factory;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  try {
		      Configuration config = new Configuration(); 
			  factory = config.configure("/hibernate_config/hibernate.cfg.xml").buildSessionFactory();
		  } catch (Throwable ex) { 
		         System.err.println("Failed to create sessionFactory object." + ex);
		         throw new ExceptionInInitializerError(ex); 
		      }
		Session session = factory.openSession();
		
		Client_Managment cm1 = new Client_Managment(session);
		Client c1 = new Client("123454321"); 
		c1.setName("Client Prueba");
		c1.setAddress("Quien sabe");
		c1.setPhone("123454321");
		//cm1.addClient(c1);
		  
		//c1.setAddress("Calle modificacion");
		//cm1.modifyClient(c1, 1);
		//c1.setPhone("123454321");
		//cm1.modifyClient(c1, 2);
		//c1.setAddress("Calle modificacion");	
		//c1.setPhone("123454321");
		//cm1.modifyClient(c1, 3);
		
		cm1.removeClient(c1);
	}
}
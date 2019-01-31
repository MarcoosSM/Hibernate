package control;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import entities.Car;

@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class Car_Managment {

	//attributes
	Session session;
	
	//builder
	public Car_Managment(Session session) {
		this.session = session;
	}
	
	//methods
	public boolean addClient(Car car) {
		boolean added = false;
		Query query = session.createQuery("from Car where licensePlate = :licensePlate");
		query.setParameter("licensePlate", car.getLicensePlate());
		ArrayList<Car> list = (ArrayList<Car>) query.list();
		if (list.isEmpty()) {
			try {
				session.beginTransaction();
				session.save(car);
				session.getTransaction().commit();
				added = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}	
		}
		return added;
	}

	public boolean removeClient(Car car) {
		boolean removed = false;
		Query query = session.createQuery("from Car where licensePlate = :licensePlate");
		query.setParameter("licensePlate", car.getLicensePlate());
		ArrayList<Car> list = (ArrayList<Car>) query.list();
		if (list.isEmpty()) {
			try {
				session.beginTransaction();
				session.remove(car);
				session.getTransaction().commit();
				removed = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}	
		}
		return removed;
	}
	
	public boolean modifyClient(Car car) {
		boolean updated = false;
		Query query = session.createQuery("from Car where licensePlate = :licensePlate");
		query.setParameter("licensePlate", car.getLicensePlate());
		ArrayList<Car> list = (ArrayList<Car>) query.list();
		if (list.isEmpty()) {
			try {
				session.beginTransaction();
				session.update(car);
				session.getTransaction().commit();
				updated = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}	
		}
		return updated;
	}
}
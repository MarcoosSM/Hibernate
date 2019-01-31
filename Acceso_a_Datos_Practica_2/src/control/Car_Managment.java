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
	public boolean addCar(Car car) {
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

	public boolean removeCar(Car car) {
		boolean removed = false;
		Query query = session.createQuery("from Car where licensePlate = :licensePlate");
		query.setParameter("licensePlate", car.getLicensePlate());
		ArrayList<Car> list = (ArrayList<Car>) query.list();
		if (!list.isEmpty()) {
			try {
				session.beginTransaction();
				Car removedCar = (Car)session.get(Car.class, car.getLicensePlate());
				session.remove(removedCar);
				session.getTransaction().commit();
				removed = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}	
		}
		return removed;
	}
	
	public boolean modifyCar(Car car) {
		boolean updated = false;
		Query query = session.createQuery("from Car where licensePlate = :licensePlate");
		query.setParameter("licensePlate", car.getLicensePlate());
		ArrayList<Car> list = (ArrayList<Car>) query.list();
		if (!list.isEmpty()) {
			try {
				session.beginTransaction();
				Car updatedCar = (Car)session.get(Car.class, car.getLicensePlate());
				updatedCar.setBrand(car.getBrand());
				updatedCar.setColor(car.getColor());
				updatedCar.setModel(car.getModel());
				session.update(updatedCar);
				session.getTransaction().commit();
				updated = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}	
		}
		return updated;
	}
}
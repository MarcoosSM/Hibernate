package control;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import entities.Car;

@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class Car_Managment {

	// attributes
	Session session;

	// builder
	public Car_Managment(Session session) {
		this.session = session;
	}

	// methods
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
				Car removedCar = (Car) session.get(Car.class, car.getLicensePlate());
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
				Car updatedCar = (Car) session.get(Car.class, car.getLicensePlate());
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

	public DefaultTableModel carTable() {
		DefaultTableModel model = null;
		Query query = session.createQuery("from Car");
		ArrayList<Car> result = (ArrayList<Car>) query.list();
		model = new DefaultTableModel();
		model.addColumn("License Plate");
		model.addColumn("Model");
		model.addColumn("Color");
		model.addColumn("Brand");
		if (!result.isEmpty()) {
			for (int x = 0; x < result.size(); ++x) {
				Object[] row = new Object[4];
				row[0] = result.get(x).getLicensePlate();
				row[1] = result.get(x).getModel();
				row[2] = result.get(x).getColor();
				row[3] = result.get(x).getBrand();
				model.addRow(row);
			}
		}
		return model;
	}

	public DefaultTableModel searchByBrandTable(String brand) {
		DefaultTableModel model = null;
		Query query = session.createQuery("from Car where brand = :brand");
		query.setParameter("brand", brand);
		ArrayList<Car> result = (ArrayList<Car>) query.list();
		model = new DefaultTableModel();
		model.addColumn("License Plate");
		model.addColumn("Model");
		model.addColumn("Color");
		model.addColumn("Brand");
		if (!result.isEmpty()) {
			for (int x = 0; x < result.size(); ++x) {
				Object[] row = new Object[4];
				row[0] = result.get(x).getLicensePlate();
				row[1] = result.get(x).getModel();
				row[2] = result.get(x).getColor();
				row[3] = result.get(x).getBrand();
				model.addRow(row);
			}
		}
		return model;
	}

	public DefaultComboBoxModel<String> carBrandComboBox() {
		DefaultComboBoxModel<String> model = null;
		Query query = session.createQuery("from Car");
		ArrayList<Car> result = (ArrayList<Car>) query.list();
		model = new DefaultComboBoxModel<String>();
		ArrayList<String> brandList = new ArrayList<String>();
		if (!result.isEmpty()) {
			for (int x = 0; x < result.size(); ++x) {
				if (!brandList.contains(result.get(x).getBrand())) {
					brandList.add(result.get(x).getBrand());
				}
			}
			for (int y = 0; y < brandList.size(); ++y) {
				model.addElement(brandList.get(y));
			}
		}
		return model;
	}
}
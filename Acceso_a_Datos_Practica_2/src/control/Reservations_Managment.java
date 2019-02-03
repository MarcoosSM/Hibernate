package control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import entities.Car;
import entities.Client;
import entities.Reservation;

@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class Reservations_Managment {

	//attributes
	Session session;
	
	//builder
	public Reservations_Managment(Session session) {
		this.session = session;
	}
	
	//methods
	public boolean addReservation(Reservation reservation) {
		boolean added = false;
		Query query = session.createQuery("from Reservation where idReservation = :idReservation");
		query.setParameter("idReservation", reservation.getIdReservation());
		ArrayList<Reservation> list = (ArrayList<Reservation>) query.list();
		if (list.isEmpty()) {
			try {
				session.beginTransaction();
				session.save(reservation);
				session.getTransaction().commit();
				added = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}	
		}
		return added;
	}

	public boolean removeReservation(Reservation reservation) {
		boolean removed = false;
		Query query = session.createQuery("from Reservation where idReservation = :idReservation");
		query.setParameter("idReservation", reservation.getIdReservation());
		ArrayList<Reservation> list = (ArrayList<Reservation>) query.list();
		if (!list.isEmpty()) {
			try {
				session.beginTransaction();
				Reservation removedReservation = (Reservation)session.get(Reservation.class, reservation.getIdReservation());
				session.remove(removedReservation);
				session.getTransaction().commit();
				removed = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}	
		}
		return removed;
	}
	
	public boolean modifyReservation(Reservation reservation) {
		boolean updated = false;
		Query query = session.createQuery("from Reservation where idReservation = :idReservation");
		query.setParameter("idReservation", reservation.getIdReservation());
		ArrayList<Reservation> list = (ArrayList<Reservation>) query.list();
		if (!list.isEmpty()) {
			try {
				session.beginTransaction();
				Reservation updatedReservation = (Reservation)session.get(Reservation.class, reservation.getIdReservation());
				updatedReservation.setStartDate(reservation.getStartDate());
				updatedReservation.setEndDate(reservation.getEndDate());
				updatedReservation.setCars(reservation.getCars());
				updatedReservation.setClient(reservation.getClient());
				session.update(updatedReservation);
				session.getTransaction().commit();
				updated = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}	
		}
		return updated;
	}
	/*
	public ArrayList<String> reservedDates() {
		ArrayList<String> reservedDates = new ArrayList<String>();
		Query query = session.createQuery("from Reservation");
		ArrayList<Reservation> result = (ArrayList<Reservation>) query.list();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault());
		if (!result.isEmpty()) {	
			for(int x=0; x<result.size(); ++x) {
				LocalDate start = LocalDate.parse(result.get(x).getStartDate(), formatter);
				LocalDate end = LocalDate.parse(result.get(x).getEndDate(), formatter);
				ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
				start.datesUntil(end).sequential().forEach(dates::add);
				for (int i=0; i<dates.size(); ++i) {
					reservedDates.add(dates.get(i).format(formatter));
				}
			}
		}	
		return reservedDates;
	}
	*/
	public DefaultTableModel reservationTable() {
		DefaultTableModel model = null;
		Query query = session.createQuery("from Reservation");
		ArrayList<Reservation> result = (ArrayList<Reservation>) query.list();
		model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Client DNI");
		model.addColumn("License Plate(s)");
		model.addColumn("Start Date");
		model.addColumn("End Date");
		if (!result.isEmpty()) {	
			for(int x=0; x<result.size(); ++x) {
				Object[] row = new Object[5];
				row[0] = result.get(x).getIdReservation();
				row[1] = result.get(x).getClient().getDni();
			    Iterator<Car> itr = result.get(x).getCars().iterator();
			    String[] licensePlateArray = new String[result.get(x).getCars().size()];
			    int y=0;
			    while(itr.hasNext()){
			        licensePlateArray[y] = itr.next().getLicensePlate();
			        ++y;
			    }
				row[2] = Arrays.toString(licensePlateArray);
				row[3] = result.get(x).getStartDate();
				row[4] = result.get(x).getEndDate();
				model.addRow(row);
			}
		}	
		return model;
	}
	
	public DefaultTableModel searchReservationByDniTable(Client client) {
		DefaultTableModel model = null;
		Query query = session.createQuery("from Reservation where client = :client");
		query.setParameter("client", client);
		ArrayList<Reservation> result = (ArrayList<Reservation>) query.list();
		model = new DefaultTableModel();
		model.addColumn("Client DNI");
		model.addColumn("License Plate(s)");
		model.addColumn("Start Date");
		model.addColumn("End Date");
		if (!result.isEmpty()) {	
			for(int x=0; x<result.size(); ++x) {
				Object[] row = new Object[4];
				row[0] = result.get(x).getClient().getDni();
			    Iterator<Car> itr = result.get(x).getCars().iterator();
			    String[] licensePlateArray = new String[result.get(x).getCars().size()];
			    int y=0;
			    while(itr.hasNext()){
			        licensePlateArray[y] = itr.next().getLicensePlate();
			        ++y;
			    }
				row[1] = Arrays.toString(licensePlateArray);
				row[2] = result.get(x).getStartDate();
				row[3] = result.get(x).getEndDate();
				model.addRow(row);
			}
		}	
		return model;
	}
	
	public DefaultTableModel searchReservationByDateTable(String startDate) {
		DefaultTableModel model = null;
		Query query = session.createQuery("from Reservation where startDate = :startDate");
		query.setParameter("startDate", startDate);
		ArrayList<Reservation> result = (ArrayList<Reservation>) query.list();
		model = new DefaultTableModel();
		model.addColumn("Start Date");
		model.addColumn("License Plate(s)");
		model.addColumn("Client DNI");
		if (!result.isEmpty()) {	
			for(int x=0; x<result.size(); ++x) {
				Object[] row = new Object[3];
				row[0] = result.get(x).getStartDate();
				Iterator<Car> itr = result.get(x).getCars().iterator();
			    String[] licensePlateArray = new String[result.get(x).getCars().size()];
			    int y=0;
			    while(itr.hasNext()){
			        licensePlateArray[y] = itr.next().getLicensePlate();
			        ++y;
			    }
				row[1] = Arrays.toString(licensePlateArray);		
				row[2] = result.get(x).getClient().getDni();
				model.addRow(row);
			}
		}	
		return model;
	}
	
	public DefaultComboBoxModel<String> clientDniComboBox() {
		DefaultComboBoxModel<String> model = null; 
		Query query = session.createQuery("from Client");
		ArrayList<Client> result = (ArrayList<Client>) query.list();
		model = new DefaultComboBoxModel<String>();
		if (!result.isEmpty()) {
			for(int x=0; x<result.size(); ++x) {
				model.addElement(result.get(x).getDni());
			}
		}
		return model;
	}
	
	public DefaultComboBoxModel<String> carLicensePlateComboBox() {
		DefaultComboBoxModel<String> model = null; 
		Query query = session.createQuery("from Car");
		ArrayList<Car> result = (ArrayList<Car>) query.list();
		model = new DefaultComboBoxModel<String>();
		if (!result.isEmpty()) {
			for(int x=0; x<result.size(); ++x) {
				model.addElement(result.get(x).getLicensePlate());
			}
		}
		return model;
	}
	
	// this method add emails to the email list model
	public DefaultListModel<String> addCarPlateList(ArrayList<String> licensePlate) {
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (int i = 0; i < licensePlate.size(); ++i) {
			model.addElement(licensePlate.get(i));
		}
		return model;
	}

	// this method cleans the email list
	public DefaultListModel<String> cleanCarPlateList() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		return model;
	}
}
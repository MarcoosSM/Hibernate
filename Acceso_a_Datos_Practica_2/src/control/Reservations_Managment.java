package control;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

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
		if (list.isEmpty()) {
			try {
				session.beginTransaction();
				session.remove(reservation);
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
		if (list.isEmpty()) {
			try {
				session.beginTransaction();
				session.update(reservation);
				session.getTransaction().commit();
				updated = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}	
		}
		return updated;
	}
}
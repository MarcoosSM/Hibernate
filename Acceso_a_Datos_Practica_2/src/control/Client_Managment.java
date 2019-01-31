package control;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import entities.Client;

@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class Client_Managment {

	//attributes
	Session session;
	
	//builder
	public Client_Managment(Session session) {
		this.session = session;
	}
	
	//methods
	public boolean addClient(Client client) {
		boolean added = false;
		Query query = session.createQuery("from Client where dni = :dni");
		query.setParameter("dni", client.getDni());
		ArrayList<Client> list = (ArrayList<Client>) query.list();
		if (list.isEmpty()) {
			try {
				session.beginTransaction();
				session.save(client);
				session.getTransaction().commit();
				added = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}	
		}
		return added;
	}

	public boolean removeClient(Client client) {
		boolean removed = false;
		Query query = session.createQuery("from Client where dni = :dni");
		query.setParameter("dni", client.getDni());
		ArrayList<Client> list = (ArrayList<Client>) query.list();
		if (!list.isEmpty()) {
			try {
				session.beginTransaction();
				Client removedClient = (Client)session.get(Client.class, client.getDni());
				session.remove(removedClient);
				session.getTransaction().commit();
				removed = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}	
		}
		return removed;
	}
	
	public boolean modifyClient(Client client, int option) {
		boolean updated = false;
		Query query = session.createQuery("from Client where dni = :dni");
		query.setParameter("dni", client.getDni());
		ArrayList<Client> list = (ArrayList<Client>) query.list();
		if (!list.isEmpty()) {
			try {
				session.beginTransaction();
				Client updatedClient = (Client)session.get(Client.class, client.getDni());
				if (option == 1) {
					updatedClient.setAddress(client.getAddress());
				}
				if (option == 2 ) {
					updatedClient.setPhone(client.getPhone());
				}
				if (option == 3) {
					updatedClient.setAddress(client.getAddress());
					updatedClient.setPhone(client.getPhone());
				}
				session.update(updatedClient);
				session.getTransaction().commit();
				updated = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}	
		}
		return updated;
	}
}
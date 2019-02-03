package control;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import entities.Client;

@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class Client_Managment {

	// attributes
	Session session;

	// builder
	public Client_Managment(Session session) {
		this.session = session;
	}

	// methods
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
				Client removedClient = (Client) session.get(Client.class, client.getDni());
				session.remove(removedClient);
				session.getTransaction().commit();
				removed = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
		return removed;
	}

	public boolean modifyClient(Client client) {
		boolean updated = false;
		Query query = session.createQuery("from Client where dni = :dni");
		query.setParameter("dni", client.getDni());
		ArrayList<Client> list = (ArrayList<Client>) query.list();
		if (!list.isEmpty()) {
			try {
				session.beginTransaction();
				Client updatedClient = (Client) session.get(Client.class, client.getDni());
				updatedClient.setAddress(client.getAddress());
				updatedClient.setPhone(client.getPhone());
				session.update(updatedClient);
				session.getTransaction().commit();
				updated = true;
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
		return updated;
	}

	public DefaultTableModel clientTable() {
		DefaultTableModel model = null;
		Query query = session.createQuery("from Client");
		ArrayList<Client> result = (ArrayList<Client>) query.list();
		model = new DefaultTableModel();
		model.addColumn("DNI");
		model.addColumn("Name");
		model.addColumn("Address");
		model.addColumn("Phone");
		if (!result.isEmpty()) {
			for (int x = 0; x < result.size(); ++x) {
				Object[] row = new Object[4];
				row[0] = result.get(x).getDni();
				row[1] = result.get(x).getName();
				row[2] = result.get(x).getAddress();
				row[3] = result.get(x).getPhone();
				model.addRow(row);
			}
		}
		return model;
	}

	public DefaultTableModel searchByDniTable(String dni) {
		DefaultTableModel model = null;
		Query query = session.createQuery("from Client where dni = :dni");
		query.setParameter("dni", dni);
		ArrayList<Client> result = (ArrayList<Client>) query.list();
		model = new DefaultTableModel();
		model.addColumn("DNI");
		model.addColumn("Name");
		model.addColumn("Address");
		model.addColumn("Phone");
		if (!result.isEmpty()) {
			for (int x = 0; x < result.size(); ++x) {
				Object[] row = new Object[4];
				row[0] = result.get(x).getDni();
				row[1] = result.get(x).getName();
				row[2] = result.get(x).getAddress();
				row[3] = result.get(x).getPhone();
				model.addRow(row);
			}
		}
		return model;
	}
}
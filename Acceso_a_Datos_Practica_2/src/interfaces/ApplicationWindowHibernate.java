package interfaces;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import control.Car_Managment;
import control.Client_Managment;
import control.Reservations_Managment;
import entities.Car;
import entities.Client;
import entities.Reservation;
import javax.swing.DefaultComboBoxModel;

public class ApplicationWindowHibernate {

	private static SessionFactory factory;
	private static Car_Managment carManagment;
	private static Client_Managment clientManagment;
	private static Reservations_Managment reservationManagment;
	private ArrayList<String> licensePlateList = new ArrayList<String>();

	private JFrame frmHibernateApplication;
	private JTable tableCarPanel;
	private JTable tableClientPanel;
	private JTable tableReservationPanel;
	private JTextField textFieldPlateAdd;
	private JTextField textFieldModelAdd;
	private JTextField textFieldColorAdd;
	private JTextField textFieldBrandAdd;
	private JTextField textFieldDniAdd;
	private JTextField textFieldNameAdd;
	private JTextField textFieldAddressAdd;
	private JTextField textFieldPhoneAdd;
	private JTextField textFieldSearchClient;
	private JTextField textFieldSearchReservation;
	private JTextField textFieldStartDateAdd;
	private JTextField textFieldEndDateAdd;
	private JTextField textFieldDniModify;
	private JTextField textFieldNameModify;
	private JTextField textFieldAddressModify;
	private JTextField textFieldPhoneModify;
	private JComboBox<String> comboBoxClientDniAdd;
	private JComboBox<String> comboBoxCarPlateAdd;
	private JComboBox<String> comboBoxSearchCar;
	private JComboBox<String> comboBoxFilterSearch;
	private JButton btnDeleteCar;
	private JButton btnDeleteClient;
	private JButton btnModifyClient;
	private JButton btnDeleteReservation;
	private JList<String> carList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationWindowHibernate window = new ApplicationWindowHibernate();
					window.frmHibernateApplication.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			Configuration config = new Configuration();
			factory = config.configure("/hibernate_config/hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		Session session = factory.openSession();

		carManagment = new Car_Managment(session);
		clientManagment = new Client_Managment(session);
		reservationManagment = new Reservations_Managment(session);
	}

	/**
	 * Create the application.
	 */
	public ApplicationWindowHibernate() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHibernateApplication = new JFrame();
		frmHibernateApplication.setTitle("Hibernate Application");
		frmHibernateApplication.setResizable(false);
		frmHibernateApplication.setBounds(100, 100, 400, 315);
		frmHibernateApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHibernateApplication.getContentPane().setLayout(new CardLayout(0, 0));

		JPanel panelChooseOptions = new JPanel();
		frmHibernateApplication.getContentPane().add(panelChooseOptions, "optionsPanel");
		SpringLayout sl_panelChooseOptions = new SpringLayout();
		panelChooseOptions.setLayout(sl_panelChooseOptions);

		JLabel lblChooseOption = new JLabel("Choose an option to work with");
		sl_panelChooseOptions.putConstraint(SpringLayout.NORTH, lblChooseOption, 50, SpringLayout.NORTH,
				panelChooseOptions);
		lblChooseOption.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblChooseOption.setHorizontalAlignment(SwingConstants.CENTER);
		sl_panelChooseOptions.putConstraint(SpringLayout.WEST, lblChooseOption, 0, SpringLayout.WEST,
				panelChooseOptions);
		sl_panelChooseOptions.putConstraint(SpringLayout.EAST, lblChooseOption, 0, SpringLayout.EAST,
				panelChooseOptions);
		panelChooseOptions.add(lblChooseOption);

		JButton btnCarPanel = new JButton("Cars");
		btnCarPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel("carPanel");
				try {
					tableCarPanel.setModel(carManagment.carTable());
					comboBoxSearchCar.setModel(carManagment.carBrandComboBox());
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(frmHibernateApplication, "Database loading, wait a few seconds",
							"WARNING", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		sl_panelChooseOptions.putConstraint(SpringLayout.NORTH, btnCarPanel, 16, SpringLayout.SOUTH, lblChooseOption);
		sl_panelChooseOptions.putConstraint(SpringLayout.WEST, btnCarPanel, 100, SpringLayout.WEST, panelChooseOptions);
		sl_panelChooseOptions.putConstraint(SpringLayout.EAST, btnCarPanel, -100, SpringLayout.EAST,
				panelChooseOptions);
		panelChooseOptions.add(btnCarPanel);

		JButton btnClientPanel = new JButton("Clients");
		btnClientPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel("clientPanel");
				try {
					tableClientPanel.setModel(clientManagment.clientTable());
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(frmHibernateApplication, "Database loading, wait a few seconds",
							"WARNING", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		sl_panelChooseOptions.putConstraint(SpringLayout.NORTH, btnClientPanel, 16, SpringLayout.SOUTH, btnCarPanel);
		sl_panelChooseOptions.putConstraint(SpringLayout.WEST, btnClientPanel, 0, SpringLayout.WEST, btnCarPanel);
		sl_panelChooseOptions.putConstraint(SpringLayout.EAST, btnClientPanel, 0, SpringLayout.EAST, btnCarPanel);
		panelChooseOptions.add(btnClientPanel);
		JButton btnReservationPanel = new JButton("Reservations");
		btnReservationPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel("reservationPanel");
				try {
					tableReservationPanel.setModel(reservationManagment.reservationTable());
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(frmHibernateApplication, "Database loading, wait a few seconds",
							"WARNING", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		sl_panelChooseOptions.putConstraint(SpringLayout.NORTH, btnReservationPanel, 16, SpringLayout.SOUTH,
				btnClientPanel);
		sl_panelChooseOptions.putConstraint(SpringLayout.WEST, btnReservationPanel, 0, SpringLayout.WEST,
				btnClientPanel);
		sl_panelChooseOptions.putConstraint(SpringLayout.EAST, btnReservationPanel, 0, SpringLayout.EAST,
				btnClientPanel);
		panelChooseOptions.add(btnReservationPanel);

		JPanel panelCar = new JPanel();
		frmHibernateApplication.getContentPane().add(panelCar, "carPanel");
		SpringLayout sl_panelCar = new SpringLayout();
		panelCar.setLayout(sl_panelCar);

		JLabel lblCarPanel = new JLabel("Car Panel");
		sl_panelCar.putConstraint(SpringLayout.NORTH, lblCarPanel, 8, SpringLayout.NORTH, panelCar);
		sl_panelCar.putConstraint(SpringLayout.WEST, lblCarPanel, 0, SpringLayout.WEST, panelCar);
		sl_panelCar.putConstraint(SpringLayout.EAST, lblCarPanel, 0, SpringLayout.EAST, panelCar);
		lblCarPanel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCarPanel.setHorizontalAlignment(SwingConstants.CENTER);
		panelCar.add(lblCarPanel);

		tableCarPanel = new JTable();
		tableCarPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnDeleteCar.setEnabled(true);
			}
		});
		sl_panelCar.putConstraint(SpringLayout.NORTH, tableCarPanel, 8, SpringLayout.SOUTH, lblCarPanel);
		sl_panelCar.putConstraint(SpringLayout.WEST, tableCarPanel, 8, SpringLayout.WEST, panelCar);
		sl_panelCar.putConstraint(SpringLayout.SOUTH, tableCarPanel, -100, SpringLayout.SOUTH, panelCar);
		sl_panelCar.putConstraint(SpringLayout.EAST, tableCarPanel, -8, SpringLayout.EAST, panelCar);
		tableCarPanel.setBorder(UIManager.getBorder("Button.border"));

		JScrollPane scrollPaneCarTable = new JScrollPane(tableCarPanel);
		sl_panelCar.putConstraint(SpringLayout.NORTH, scrollPaneCarTable, 8, SpringLayout.SOUTH, lblCarPanel);
		sl_panelCar.putConstraint(SpringLayout.WEST, scrollPaneCarTable, 8, SpringLayout.WEST, panelCar);
		sl_panelCar.putConstraint(SpringLayout.SOUTH, scrollPaneCarTable, -100, SpringLayout.SOUTH, panelCar);
		sl_panelCar.putConstraint(SpringLayout.EAST, scrollPaneCarTable, -8, SpringLayout.EAST, panelCar);
		scrollPaneCarTable.setBorder(UIManager.getBorder("Button.border"));
		panelCar.add(scrollPaneCarTable);

		JSeparator separatorCarPanel = new JSeparator();
		sl_panelCar.putConstraint(SpringLayout.NORTH, separatorCarPanel, 8, SpringLayout.SOUTH, tableCarPanel);
		sl_panelCar.putConstraint(SpringLayout.WEST, separatorCarPanel, 0, SpringLayout.WEST, tableCarPanel);
		sl_panelCar.putConstraint(SpringLayout.EAST, separatorCarPanel, 0, SpringLayout.EAST, tableCarPanel);
		panelCar.add(separatorCarPanel);

		JButton btnAddCar = new JButton("Add Car");
		btnAddCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel("addCarPanel");
				btnDeleteCar.setEnabled(false);
			}
		});
		sl_panelCar.putConstraint(SpringLayout.NORTH, btnAddCar, 8, SpringLayout.SOUTH, separatorCarPanel);
		sl_panelCar.putConstraint(SpringLayout.WEST, btnAddCar, 0, SpringLayout.WEST, separatorCarPanel);
		sl_panelCar.putConstraint(SpringLayout.EAST, btnAddCar, -195, SpringLayout.EAST, separatorCarPanel);
		panelCar.add(btnAddCar);

		btnDeleteCar = new JButton("Delete Car");
		btnDeleteCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableCarPanel.getSelectedRow();
				Car deletedCar = new Car();
				deletedCar.setLicensePlate(tableCarPanel.getValueAt(row, 0).toString());
				carManagment.removeCar(deletedCar);
				tableCarPanel.setModel(carManagment.carTable());
				btnDeleteCar.setEnabled(false);
			}
		});
		sl_panelCar.putConstraint(SpringLayout.NORTH, btnDeleteCar, 8, SpringLayout.SOUTH, separatorCarPanel);
		sl_panelCar.putConstraint(SpringLayout.WEST, btnDeleteCar, 8, SpringLayout.EAST, btnAddCar);
		sl_panelCar.putConstraint(SpringLayout.EAST, btnDeleteCar, 0, SpringLayout.EAST, separatorCarPanel);
		btnDeleteCar.setEnabled(false);
		panelCar.add(btnDeleteCar);

		JButton btnBackCar = new JButton("Back");
		btnBackCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel("optionsPanel");
				btnDeleteCar.setEnabled(false);
			}
		});
		sl_panelCar.putConstraint(SpringLayout.SOUTH, btnBackCar, -8, SpringLayout.SOUTH, panelCar);
		sl_panelCar.putConstraint(SpringLayout.EAST, btnBackCar, 0, SpringLayout.EAST, separatorCarPanel);
		panelCar.add(btnBackCar);

		JLabel lblBrandSearch = new JLabel("Brand:");
		sl_panelCar.putConstraint(SpringLayout.NORTH, lblBrandSearch, 18, SpringLayout.SOUTH, btnAddCar);
		sl_panelCar.putConstraint(SpringLayout.WEST, lblBrandSearch, 10, SpringLayout.WEST, panelCar);
		panelCar.add(lblBrandSearch);

		comboBoxSearchCar = new JComboBox<String>();
		sl_panelCar.putConstraint(SpringLayout.NORTH, comboBoxSearchCar, 15, SpringLayout.SOUTH, btnAddCar);
		sl_panelCar.putConstraint(SpringLayout.WEST, comboBoxSearchCar, 6, SpringLayout.EAST, lblBrandSearch);
		sl_panelCar.putConstraint(SpringLayout.EAST, comboBoxSearchCar, 119, SpringLayout.EAST, lblBrandSearch);
		panelCar.add(comboBoxSearchCar);

		JButton btnSearchCar = new JButton("Search");
		btnSearchCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableCarPanel.setModel(carManagment
						.searchByBrandTable(comboBoxSearchCar.getItemAt(comboBoxSearchCar.getSelectedIndex())));
			}
		});
		sl_panelCar.putConstraint(SpringLayout.WEST, btnSearchCar, 6, SpringLayout.EAST, comboBoxSearchCar);
		sl_panelCar.putConstraint(SpringLayout.SOUTH, btnSearchCar, 4, SpringLayout.SOUTH, lblBrandSearch);
		panelCar.add(btnSearchCar);

		JPanel panelClient = new JPanel();
		frmHibernateApplication.getContentPane().add(panelClient, "clientPanel");
		SpringLayout sl_panelClient = new SpringLayout();
		panelClient.setLayout(sl_panelClient);

		JLabel lblClientPanel = new JLabel("Client Panel");
		lblClientPanel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClientPanel.setHorizontalAlignment(SwingConstants.CENTER);
		sl_panelClient.putConstraint(SpringLayout.NORTH, lblClientPanel, 8, SpringLayout.NORTH, panelClient);
		sl_panelClient.putConstraint(SpringLayout.WEST, lblClientPanel, 0, SpringLayout.WEST, panelClient);
		sl_panelClient.putConstraint(SpringLayout.EAST, lblClientPanel, 0, SpringLayout.EAST, panelClient);
		panelClient.add(lblClientPanel);

		tableClientPanel = new JTable();
		tableClientPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnModifyClient.setEnabled(true);
				btnDeleteClient.setEnabled(true);
			}
		});
		sl_panelClient.putConstraint(SpringLayout.SOUTH, tableClientPanel, -100, SpringLayout.SOUTH, panelClient);
		tableClientPanel.setBorder(UIManager.getBorder("Button.border"));
		sl_panelClient.putConstraint(SpringLayout.NORTH, tableClientPanel, 8, SpringLayout.SOUTH, lblClientPanel);
		sl_panelClient.putConstraint(SpringLayout.WEST, tableClientPanel, 8, SpringLayout.WEST, panelClient);
		sl_panelClient.putConstraint(SpringLayout.EAST, tableClientPanel, -8, SpringLayout.EAST, panelClient);

		JScrollPane scrollPaneClientTable = new JScrollPane(tableClientPanel);
		sl_panelClient.putConstraint(SpringLayout.NORTH, scrollPaneClientTable, 8, SpringLayout.SOUTH, lblClientPanel);
		sl_panelClient.putConstraint(SpringLayout.WEST, scrollPaneClientTable, 8, SpringLayout.WEST, panelClient);
		sl_panelClient.putConstraint(SpringLayout.SOUTH, scrollPaneClientTable, -100, SpringLayout.SOUTH, panelClient);
		sl_panelClient.putConstraint(SpringLayout.EAST, scrollPaneClientTable, -8, SpringLayout.EAST, panelClient);
		scrollPaneClientTable.setBorder(UIManager.getBorder("Button.border"));
		panelClient.add(scrollPaneClientTable);

		JSeparator separatorClientPanel = new JSeparator();
		sl_panelClient.putConstraint(SpringLayout.NORTH, separatorClientPanel, 8, SpringLayout.SOUTH, tableClientPanel);
		sl_panelClient.putConstraint(SpringLayout.WEST, separatorClientPanel, 0, SpringLayout.WEST, tableClientPanel);
		sl_panelClient.putConstraint(SpringLayout.EAST, separatorClientPanel, 0, SpringLayout.EAST, tableClientPanel);
		panelClient.add(separatorClientPanel);

		JButton btnAddClient = new JButton("Add Client");
		btnAddClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel("addClientPanel");
				btnDeleteClient.setEnabled(false);
				btnModifyClient.setEnabled(false);
			}
		});
		sl_panelClient.putConstraint(SpringLayout.NORTH, btnAddClient, 8, SpringLayout.SOUTH, separatorClientPanel);
		sl_panelClient.putConstraint(SpringLayout.WEST, btnAddClient, 8, SpringLayout.WEST, panelClient);
		panelClient.add(btnAddClient);

		btnModifyClient = new JButton("Modify Client");
		btnModifyClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chargeClientInfo();
				changePanel("modifyClientPanel");
				btnDeleteClient.setEnabled(false);
				btnModifyClient.setEnabled(false);
			}
		});
		sl_panelClient.putConstraint(SpringLayout.WEST, btnModifyClient, 120, SpringLayout.WEST, btnAddClient);
		sl_panelClient.putConstraint(SpringLayout.EAST, btnAddClient, -8, SpringLayout.WEST, btnModifyClient);
		sl_panelClient.putConstraint(SpringLayout.NORTH, btnModifyClient, 8, SpringLayout.SOUTH, separatorClientPanel);
		btnModifyClient.setEnabled(false);
		panelClient.add(btnModifyClient);

		btnDeleteClient = new JButton("Delete Client");
		btnDeleteClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableClientPanel.getSelectedRow();
				Client deletedClient = new Client();
				deletedClient.setDni(tableClientPanel.getValueAt(row, 0).toString());
				clientManagment.removeClient(deletedClient);
				tableClientPanel.setModel(clientManagment.clientTable());
				btnDeleteClient.setEnabled(false);
				btnModifyClient.setEnabled(false);
			}
		});
		sl_panelClient.putConstraint(SpringLayout.EAST, btnModifyClient, -8, SpringLayout.WEST, btnDeleteClient);
		sl_panelClient.putConstraint(SpringLayout.NORTH, btnDeleteClient, 8, SpringLayout.SOUTH, separatorClientPanel);
		sl_panelClient.putConstraint(SpringLayout.WEST, btnDeleteClient, 120, SpringLayout.WEST, btnModifyClient);
		sl_panelClient.putConstraint(SpringLayout.EAST, btnDeleteClient, 0, SpringLayout.EAST, separatorClientPanel);
		btnDeleteClient.setEnabled(false);
		panelClient.add(btnDeleteClient);

		JButton btnBackClient = new JButton("Back");
		btnBackClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel("optionsPanel");
				btnDeleteClient.setEnabled(false);
				btnModifyClient.setEnabled(false);
				reset();
			}
		});
		sl_panelClient.putConstraint(SpringLayout.SOUTH, btnBackClient, -8, SpringLayout.SOUTH, panelClient);
		sl_panelClient.putConstraint(SpringLayout.EAST, btnBackClient, -8, SpringLayout.EAST, panelClient);
		panelClient.add(btnBackClient);

		JLabel lblDniSearch = new JLabel("Dni:");
		sl_panelClient.putConstraint(SpringLayout.NORTH, lblDniSearch, 18, SpringLayout.SOUTH, btnAddClient);
		sl_panelClient.putConstraint(SpringLayout.WEST, lblDniSearch, 10, SpringLayout.WEST, panelClient);
		panelClient.add(lblDniSearch);

		textFieldSearchClient = new JTextField();
		sl_panelClient.putConstraint(SpringLayout.NORTH, textFieldSearchClient, 15, SpringLayout.SOUTH, btnAddClient);
		sl_panelClient.putConstraint(SpringLayout.WEST, textFieldSearchClient, 6, SpringLayout.EAST, lblDniSearch);
		sl_panelClient.putConstraint(SpringLayout.EAST, textFieldSearchClient, 119, SpringLayout.EAST, lblDniSearch);
		panelClient.add(textFieldSearchClient);

		JButton btnSearchClient = new JButton("Search");
		btnSearchClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clientManagment.searchByDniTable(textFieldSearchClient.getText()).getRowCount() > 0) {
					tableClientPanel.setModel(clientManagment.searchByDniTable(textFieldSearchClient.getText()));
				} else {
					JOptionPane.showMessageDialog(frmHibernateApplication, "Client not found", "WARNING",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		sl_panelClient.putConstraint(SpringLayout.WEST, btnSearchClient, 6, SpringLayout.EAST, textFieldSearchClient);
		sl_panelClient.putConstraint(SpringLayout.SOUTH, btnSearchClient, 4, SpringLayout.SOUTH, lblDniSearch);
		panelClient.add(btnSearchClient);

		JPanel panelReservation = new JPanel();
		frmHibernateApplication.getContentPane().add(panelReservation, "reservationPanel");
		SpringLayout sl_panelReservation = new SpringLayout();
		panelReservation.setLayout(sl_panelReservation);

		JLabel lblReservationPanel = new JLabel("Reservation Panel");
		lblReservationPanel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		sl_panelReservation.putConstraint(SpringLayout.NORTH, lblReservationPanel, 8, SpringLayout.NORTH,
				panelReservation);
		sl_panelReservation.putConstraint(SpringLayout.WEST, lblReservationPanel, 0, SpringLayout.WEST,
				panelReservation);
		sl_panelReservation.putConstraint(SpringLayout.EAST, lblReservationPanel, 0, SpringLayout.EAST,
				panelReservation);
		lblReservationPanel.setHorizontalAlignment(SwingConstants.CENTER);
		panelReservation.add(lblReservationPanel);

		tableReservationPanel = new JTable();
		tableReservationPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnDeleteReservation.setEnabled(true);
			}
		});
		sl_panelReservation.putConstraint(SpringLayout.SOUTH, tableReservationPanel, -100, SpringLayout.SOUTH,
				panelReservation);
		tableReservationPanel.setBorder(UIManager.getBorder("Button.border"));
		sl_panelReservation.putConstraint(SpringLayout.NORTH, tableReservationPanel, 8, SpringLayout.SOUTH,
				lblReservationPanel);
		sl_panelReservation.putConstraint(SpringLayout.WEST, tableReservationPanel, 8, SpringLayout.WEST,
				panelReservation);
		sl_panelReservation.putConstraint(SpringLayout.EAST, tableReservationPanel, -8, SpringLayout.EAST,
				panelReservation);

		JScrollPane scrollPaneReservationTable = new JScrollPane(tableReservationPanel);
		sl_panelReservation.putConstraint(SpringLayout.NORTH, scrollPaneReservationTable, 8, SpringLayout.SOUTH,
				lblReservationPanel);
		sl_panelReservation.putConstraint(SpringLayout.WEST, scrollPaneReservationTable, 8, SpringLayout.WEST,
				panelReservation);
		sl_panelReservation.putConstraint(SpringLayout.SOUTH, scrollPaneReservationTable, -100, SpringLayout.SOUTH,
				panelReservation);
		sl_panelReservation.putConstraint(SpringLayout.EAST, scrollPaneReservationTable, -8, SpringLayout.EAST,
				panelReservation);
		scrollPaneReservationTable.setBorder(UIManager.getBorder("Button.border"));
		panelReservation.add(scrollPaneReservationTable);

		JSeparator separatorReservationPanel = new JSeparator();
		sl_panelReservation.putConstraint(SpringLayout.NORTH, separatorReservationPanel, 8, SpringLayout.SOUTH,
				tableReservationPanel);
		sl_panelReservation.putConstraint(SpringLayout.WEST, separatorReservationPanel, 0, SpringLayout.WEST,
				tableReservationPanel);
		sl_panelReservation.putConstraint(SpringLayout.EAST, separatorReservationPanel, 0, SpringLayout.EAST,
				tableReservationPanel);
		panelReservation.add(separatorReservationPanel);

		JButton btnAddReservation = new JButton("Add Reservation");
		btnAddReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxClientDniAdd.setModel(reservationManagment.clientDniComboBox());
				comboBoxCarPlateAdd.setModel(reservationManagment.carLicensePlateComboBox());
				changePanel("addReservationPanel");
				btnDeleteReservation.setEnabled(false);
			}
		});
		sl_panelReservation.putConstraint(SpringLayout.NORTH, btnAddReservation, 8, SpringLayout.SOUTH,
				separatorReservationPanel);
		sl_panelReservation.putConstraint(SpringLayout.WEST, btnAddReservation, 0, SpringLayout.WEST,
				separatorReservationPanel);
		sl_panelReservation.putConstraint(SpringLayout.EAST, btnAddReservation, -195, SpringLayout.EAST,
				separatorReservationPanel);
		panelReservation.add(btnAddReservation);

		btnDeleteReservation = new JButton("Delete Reservation");
		btnDeleteReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableReservationPanel.getSelectedRow();
				Reservation deletedReservation = new Reservation();
				deletedReservation.setIdReservation((Integer) tableReservationPanel.getValueAt(row, 0));
				reservationManagment.removeReservation(deletedReservation);
				tableReservationPanel.setModel(reservationManagment.reservationTable());
				btnDeleteReservation.setEnabled(false);
			}
		});
		sl_panelReservation.putConstraint(SpringLayout.WEST, btnDeleteReservation, 8, SpringLayout.EAST,
				btnAddReservation);
		sl_panelReservation.putConstraint(SpringLayout.EAST, btnDeleteReservation, 0, SpringLayout.EAST,
				separatorReservationPanel);
		sl_panelReservation.putConstraint(SpringLayout.NORTH, btnDeleteReservation, 8, SpringLayout.SOUTH,
				separatorReservationPanel);
		btnDeleteReservation.setEnabled(false);
		panelReservation.add(btnDeleteReservation);

		JButton btnBackReservation = new JButton("Back");
		btnBackReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel("optionsPanel");
				btnDeleteReservation.setEnabled(false);
				reset();
			}
		});
		sl_panelReservation.putConstraint(SpringLayout.SOUTH, btnBackReservation, -8, SpringLayout.SOUTH,
				panelReservation);
		sl_panelReservation.putConstraint(SpringLayout.EAST, btnBackReservation, -8, SpringLayout.EAST,
				panelReservation);
		panelReservation.add(btnBackReservation);

		comboBoxFilterSearch = new JComboBox<String>();
		comboBoxFilterSearch.setModel(new DefaultComboBoxModel<String>(new String[] { "DNI", "Date" }));
		sl_panelReservation.putConstraint(SpringLayout.NORTH, comboBoxFilterSearch, 15, SpringLayout.SOUTH,
				btnAddReservation);
		sl_panelReservation.putConstraint(SpringLayout.WEST, comboBoxFilterSearch, 10, SpringLayout.WEST,
				panelReservation);
		panelReservation.add(comboBoxFilterSearch);

		textFieldSearchReservation = new JTextField();
		sl_panelReservation.putConstraint(SpringLayout.WEST, textFieldSearchReservation, 68, SpringLayout.WEST,
				panelReservation);
		sl_panelReservation.putConstraint(SpringLayout.EAST, comboBoxFilterSearch, -6, SpringLayout.WEST,
				textFieldSearchReservation);
		sl_panelReservation.putConstraint(SpringLayout.NORTH, textFieldSearchReservation, 15, SpringLayout.SOUTH,
				btnAddReservation);
		panelReservation.add(textFieldSearchReservation);

		JButton btnSearchReservation = new JButton("Search");
		btnSearchReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBoxFilterSearch.getSelectedItem().equals("DNI")) {
					Client client = new Client(textFieldSearchReservation.getText());
					if (reservationManagment.searchReservationByDniTable(client).getRowCount() > 0) {
						tableReservationPanel.setModel(reservationManagment.searchReservationByDniTable(client));
					} else {
						JOptionPane.showMessageDialog(frmHibernateApplication, "Client not found", "WARNING",
								JOptionPane.WARNING_MESSAGE);
					}
				}
				if (comboBoxFilterSearch.getSelectedItem().equals("Date")) {
					String searchDate = textFieldSearchReservation.getText();
					if (isValidFormat("dd-MM-yyyy", searchDate, Locale.getDefault())) {
						tableReservationPanel.setModel(reservationManagment.searchReservationByDateTable(searchDate));
					}
				}
			}
		});
		sl_panelReservation.putConstraint(SpringLayout.NORTH, btnSearchReservation, 13, SpringLayout.SOUTH,
				btnAddReservation);
		sl_panelReservation.putConstraint(SpringLayout.WEST, btnSearchReservation, 163, SpringLayout.WEST,
				panelReservation);
		sl_panelReservation.putConstraint(SpringLayout.EAST, textFieldSearchReservation, -6, SpringLayout.WEST,
				btnSearchReservation);
		panelReservation.add(btnSearchReservation);

		JPanel panelAddCar = new JPanel();
		frmHibernateApplication.getContentPane().add(panelAddCar, "addCarPanel");
		SpringLayout sl_panelAddCar = new SpringLayout();
		panelAddCar.setLayout(sl_panelAddCar);

		JLabel lblAddCar = new JLabel("Add Car");
		sl_panelAddCar.putConstraint(SpringLayout.NORTH, lblAddCar, 8, SpringLayout.NORTH, panelAddCar);
		lblAddCar.setHorizontalAlignment(SwingConstants.CENTER);
		sl_panelAddCar.putConstraint(SpringLayout.WEST, lblAddCar, 0, SpringLayout.WEST, panelAddCar);
		sl_panelAddCar.putConstraint(SpringLayout.EAST, lblAddCar, 0, SpringLayout.EAST, panelAddCar);
		lblAddCar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelAddCar.add(lblAddCar);

		JSeparator separatorAddCar = new JSeparator();
		sl_panelAddCar.putConstraint(SpringLayout.NORTH, separatorAddCar, 8, SpringLayout.SOUTH, lblAddCar);
		sl_panelAddCar.putConstraint(SpringLayout.WEST, separatorAddCar, 16, SpringLayout.WEST, panelAddCar);
		sl_panelAddCar.putConstraint(SpringLayout.EAST, separatorAddCar, -16, SpringLayout.EAST, panelAddCar);
		panelAddCar.add(separatorAddCar);

		JLabel lblPlateAdd = new JLabel("Plate:");
		sl_panelAddCar.putConstraint(SpringLayout.NORTH, lblPlateAdd, 16, SpringLayout.SOUTH, separatorAddCar);
		sl_panelAddCar.putConstraint(SpringLayout.WEST, lblPlateAdd, 16, SpringLayout.WEST, separatorAddCar);
		panelAddCar.add(lblPlateAdd);

		JLabel lblModelAdd = new JLabel("Model:");
		sl_panelAddCar.putConstraint(SpringLayout.NORTH, lblModelAdd, 16, SpringLayout.SOUTH, lblPlateAdd);
		sl_panelAddCar.putConstraint(SpringLayout.EAST, lblModelAdd, 0, SpringLayout.EAST, lblPlateAdd);
		panelAddCar.add(lblModelAdd);

		JLabel lblColorAdd = new JLabel("Color:");
		sl_panelAddCar.putConstraint(SpringLayout.NORTH, lblColorAdd, 16, SpringLayout.SOUTH, lblModelAdd);
		sl_panelAddCar.putConstraint(SpringLayout.EAST, lblColorAdd, 0, SpringLayout.EAST, lblModelAdd);
		panelAddCar.add(lblColorAdd);

		JLabel lblBrandAdd = new JLabel("Brand:");
		sl_panelAddCar.putConstraint(SpringLayout.NORTH, lblBrandAdd, 16, SpringLayout.SOUTH, lblColorAdd);
		sl_panelAddCar.putConstraint(SpringLayout.EAST, lblBrandAdd, 0, SpringLayout.EAST, lblColorAdd);
		panelAddCar.add(lblBrandAdd);

		textFieldPlateAdd = new JTextField();
		sl_panelAddCar.putConstraint(SpringLayout.NORTH, textFieldPlateAdd, -4, SpringLayout.NORTH, lblPlateAdd);
		sl_panelAddCar.putConstraint(SpringLayout.WEST, textFieldPlateAdd, 10, SpringLayout.EAST, lblPlateAdd);
		sl_panelAddCar.putConstraint(SpringLayout.EAST, textFieldPlateAdd, 0, SpringLayout.EAST, separatorAddCar);
		panelAddCar.add(textFieldPlateAdd);
		textFieldPlateAdd.setColumns(10);

		textFieldModelAdd = new JTextField();
		sl_panelAddCar.putConstraint(SpringLayout.NORTH, textFieldModelAdd, -4, SpringLayout.NORTH, lblModelAdd);
		sl_panelAddCar.putConstraint(SpringLayout.WEST, textFieldModelAdd, 0, SpringLayout.WEST, textFieldPlateAdd);
		sl_panelAddCar.putConstraint(SpringLayout.EAST, textFieldModelAdd, 0, SpringLayout.EAST, textFieldPlateAdd);
		panelAddCar.add(textFieldModelAdd);
		textFieldModelAdd.setColumns(10);

		textFieldColorAdd = new JTextField();
		sl_panelAddCar.putConstraint(SpringLayout.NORTH, textFieldColorAdd, -4, SpringLayout.NORTH, lblColorAdd);
		sl_panelAddCar.putConstraint(SpringLayout.WEST, textFieldColorAdd, 0, SpringLayout.WEST, textFieldModelAdd);
		sl_panelAddCar.putConstraint(SpringLayout.EAST, textFieldColorAdd, 0, SpringLayout.EAST, textFieldModelAdd);
		panelAddCar.add(textFieldColorAdd);
		textFieldColorAdd.setColumns(10);

		textFieldBrandAdd = new JTextField();
		sl_panelAddCar.putConstraint(SpringLayout.NORTH, textFieldBrandAdd, -4, SpringLayout.NORTH, lblBrandAdd);
		sl_panelAddCar.putConstraint(SpringLayout.WEST, textFieldBrandAdd, 0, SpringLayout.WEST, textFieldColorAdd);
		sl_panelAddCar.putConstraint(SpringLayout.EAST, textFieldBrandAdd, 0, SpringLayout.EAST, textFieldColorAdd);
		panelAddCar.add(textFieldBrandAdd);
		textFieldBrandAdd.setColumns(10);

		JButton btnSubmitAddCar = new JButton("Submit");
		btnSubmitAddCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String licensePlate = textFieldPlateAdd.getText();
				String model = textFieldModelAdd.getText();
				String color = textFieldColorAdd.getText();
				String brand = textFieldBrandAdd.getText();
				if (!licensePlate.isEmpty() && !model.isEmpty() && !color.isEmpty() && !brand.isEmpty()) {
					Car newCar = new Car(licensePlate);
					newCar.setModel(model);
					newCar.setColor(color);
					newCar.setBrand(brand);
					if (carManagment.addCar(newCar)) {
						JOptionPane.showMessageDialog(frmHibernateApplication, "Car added successfully!", "SUCCESSFUL",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(frmHibernateApplication, "Car already added", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(frmHibernateApplication, "Empty fields, please fill in all fields",
							"WARNING", JOptionPane.WARNING_MESSAGE);
				}
				reset();
			}
		});
		sl_panelAddCar.putConstraint(SpringLayout.NORTH, btnSubmitAddCar, 50, SpringLayout.SOUTH, textFieldBrandAdd);
		sl_panelAddCar.putConstraint(SpringLayout.WEST, btnSubmitAddCar, 100, SpringLayout.WEST, separatorAddCar);
		panelAddCar.add(btnSubmitAddCar);

		JButton btnResetAddCar = new JButton("Reset");
		btnResetAddCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		sl_panelAddCar.putConstraint(SpringLayout.NORTH, btnResetAddCar, 0, SpringLayout.NORTH, btnSubmitAddCar);
		sl_panelAddCar.putConstraint(SpringLayout.WEST, btnResetAddCar, 20, SpringLayout.EAST, btnSubmitAddCar);
		panelAddCar.add(btnResetAddCar);

		JButton btnBackAddCar = new JButton("Back");
		btnBackAddCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableCarPanel.setModel(carManagment.carTable());
				reset();
				changePanel("carPanel");
			}
		});
		sl_panelAddCar.putConstraint(SpringLayout.SOUTH, btnBackAddCar, -16, SpringLayout.SOUTH, panelAddCar);
		sl_panelAddCar.putConstraint(SpringLayout.EAST, btnBackAddCar, -16, SpringLayout.EAST, panelAddCar);
		panelAddCar.add(btnBackAddCar);

		JPanel panelAddClient = new JPanel();
		frmHibernateApplication.getContentPane().add(panelAddClient, "addClientPanel");
		SpringLayout sl_panelAddClient = new SpringLayout();
		panelAddClient.setLayout(sl_panelAddClient);

		JLabel lblAddClient = new JLabel("Add Client");
		sl_panelAddClient.putConstraint(SpringLayout.NORTH, lblAddClient, 8, SpringLayout.NORTH, panelAddClient);
		lblAddClient.setHorizontalAlignment(SwingConstants.CENTER);
		sl_panelAddClient.putConstraint(SpringLayout.WEST, lblAddClient, 0, SpringLayout.WEST, panelAddClient);
		sl_panelAddClient.putConstraint(SpringLayout.EAST, lblAddClient, 0, SpringLayout.EAST, panelAddClient);
		lblAddClient.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelAddClient.add(lblAddClient);

		JSeparator separatorAddClient = new JSeparator();
		sl_panelAddClient.putConstraint(SpringLayout.NORTH, separatorAddClient, 8, SpringLayout.SOUTH, lblAddClient);
		sl_panelAddClient.putConstraint(SpringLayout.WEST, separatorAddClient, 16, SpringLayout.WEST, panelAddClient);
		sl_panelAddClient.putConstraint(SpringLayout.EAST, separatorAddClient, -16, SpringLayout.EAST, panelAddClient);
		panelAddClient.add(separatorAddClient);

		JLabel lblDniAdd = new JLabel("DNI:");
		sl_panelAddClient.putConstraint(SpringLayout.NORTH, lblDniAdd, 16, SpringLayout.SOUTH, separatorAddClient);
		sl_panelAddClient.putConstraint(SpringLayout.WEST, lblDniAdd, 16, SpringLayout.WEST, separatorAddClient);
		panelAddClient.add(lblDniAdd);

		JLabel lblNameAdd = new JLabel("Name:");
		sl_panelAddClient.putConstraint(SpringLayout.NORTH, lblNameAdd, 16, SpringLayout.SOUTH, lblDniAdd);
		sl_panelAddClient.putConstraint(SpringLayout.EAST, lblNameAdd, 0, SpringLayout.EAST, lblDniAdd);
		panelAddClient.add(lblNameAdd);

		JLabel lblAddressAdd = new JLabel("Address:");
		sl_panelAddClient.putConstraint(SpringLayout.NORTH, lblAddressAdd, 16, SpringLayout.SOUTH, lblNameAdd);
		sl_panelAddClient.putConstraint(SpringLayout.EAST, lblAddressAdd, 0, SpringLayout.EAST, lblNameAdd);
		panelAddClient.add(lblAddressAdd);

		JLabel lblPhoneAdd = new JLabel("Phone:");
		sl_panelAddClient.putConstraint(SpringLayout.NORTH, lblPhoneAdd, 16, SpringLayout.SOUTH, lblAddressAdd);
		sl_panelAddClient.putConstraint(SpringLayout.EAST, lblPhoneAdd, 0, SpringLayout.EAST, lblAddressAdd);
		panelAddClient.add(lblPhoneAdd);

		textFieldDniAdd = new JTextField();
		sl_panelAddClient.putConstraint(SpringLayout.NORTH, textFieldDniAdd, -4, SpringLayout.NORTH, lblDniAdd);
		sl_panelAddClient.putConstraint(SpringLayout.WEST, textFieldDniAdd, 10, SpringLayout.EAST, lblDniAdd);
		sl_panelAddClient.putConstraint(SpringLayout.EAST, textFieldDniAdd, 0, SpringLayout.EAST, separatorAddClient);
		panelAddClient.add(textFieldDniAdd);
		textFieldDniAdd.setColumns(10);

		textFieldNameAdd = new JTextField();
		sl_panelAddClient.putConstraint(SpringLayout.NORTH, textFieldNameAdd, -4, SpringLayout.NORTH, lblNameAdd);
		sl_panelAddClient.putConstraint(SpringLayout.WEST, textFieldNameAdd, 0, SpringLayout.WEST, textFieldDniAdd);
		sl_panelAddClient.putConstraint(SpringLayout.EAST, textFieldNameAdd, 0, SpringLayout.EAST, textFieldDniAdd);
		panelAddClient.add(textFieldNameAdd);
		textFieldNameAdd.setColumns(10);

		textFieldAddressAdd = new JTextField();
		sl_panelAddClient.putConstraint(SpringLayout.NORTH, textFieldAddressAdd, -4, SpringLayout.NORTH, lblAddressAdd);
		sl_panelAddClient.putConstraint(SpringLayout.WEST, textFieldAddressAdd, 0, SpringLayout.WEST, textFieldNameAdd);
		sl_panelAddClient.putConstraint(SpringLayout.EAST, textFieldAddressAdd, 0, SpringLayout.EAST, textFieldNameAdd);
		panelAddClient.add(textFieldAddressAdd);
		textFieldAddressAdd.setColumns(10);

		textFieldPhoneAdd = new JTextField();
		sl_panelAddClient.putConstraint(SpringLayout.NORTH, textFieldPhoneAdd, -4, SpringLayout.NORTH, lblPhoneAdd);
		sl_panelAddClient.putConstraint(SpringLayout.WEST, textFieldPhoneAdd, 0, SpringLayout.WEST,
				textFieldAddressAdd);
		sl_panelAddClient.putConstraint(SpringLayout.EAST, textFieldPhoneAdd, 0, SpringLayout.EAST,
				textFieldAddressAdd);
		panelAddClient.add(textFieldPhoneAdd);
		textFieldPhoneAdd.setColumns(10);

		JButton btnSubmitAddClient = new JButton("Submit");
		btnSubmitAddClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dni = textFieldDniAdd.getText();
				String name = textFieldNameAdd.getText();
				String address = textFieldAddressAdd.getText();
				String phone = textFieldPhoneAdd.getText();
				if (!dni.isEmpty() && !name.isEmpty() && !address.isEmpty() && !phone.isEmpty()) {
					Client newClient = new Client(dni);
					newClient.setName(name);
					newClient.setAddress(address);
					newClient.setPhone(phone);
					if (clientManagment.addClient(newClient)) {
						JOptionPane.showMessageDialog(frmHibernateApplication, "Client added successfully!",
								"SUCCESSFUL", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(frmHibernateApplication, "Client already added", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(frmHibernateApplication, "Empty fields, please fill in all fields",
							"WARNING", JOptionPane.WARNING_MESSAGE);
				}
				reset();
			}
		});
		sl_panelAddClient.putConstraint(SpringLayout.NORTH, btnSubmitAddClient, 50, SpringLayout.SOUTH,
				textFieldPhoneAdd);
		sl_panelAddClient.putConstraint(SpringLayout.WEST, btnSubmitAddClient, 100, SpringLayout.WEST,
				separatorAddClient);
		panelAddClient.add(btnSubmitAddClient);

		JButton btnResetAddClient = new JButton("Reset");
		btnResetAddClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		sl_panelAddClient.putConstraint(SpringLayout.NORTH, btnResetAddClient, 0, SpringLayout.NORTH,
				btnSubmitAddClient);
		sl_panelAddClient.putConstraint(SpringLayout.WEST, btnResetAddClient, 20, SpringLayout.EAST,
				btnSubmitAddClient);
		panelAddClient.add(btnResetAddClient);

		JButton btnBackAddClient = new JButton("Back");
		btnBackAddClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableClientPanel.setModel(clientManagment.clientTable());
				reset();
				changePanel("clientPanel");
			}
		});
		sl_panelAddClient.putConstraint(SpringLayout.SOUTH, btnBackAddClient, -16, SpringLayout.SOUTH, panelAddClient);
		sl_panelAddClient.putConstraint(SpringLayout.EAST, btnBackAddClient, -16, SpringLayout.EAST, panelAddClient);
		panelAddClient.add(btnBackAddClient);

		JPanel panelAddReservation = new JPanel();
		frmHibernateApplication.getContentPane().add(panelAddReservation, "addReservationPanel");
		SpringLayout sl_panelAddReservation = new SpringLayout();
		panelAddReservation.setLayout(sl_panelAddReservation);

		JLabel lblAddReservation = new JLabel("Add Reservation");
		sl_panelAddReservation.putConstraint(SpringLayout.NORTH, lblAddReservation, 8, SpringLayout.NORTH,
				panelAddReservation);
		lblAddReservation.setHorizontalAlignment(SwingConstants.CENTER);
		sl_panelAddReservation.putConstraint(SpringLayout.WEST, lblAddReservation, 0, SpringLayout.WEST,
				panelAddReservation);
		sl_panelAddReservation.putConstraint(SpringLayout.EAST, lblAddReservation, 0, SpringLayout.EAST,
				panelAddReservation);
		lblAddReservation.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelAddReservation.add(lblAddReservation);

		JSeparator separatorAddReservation = new JSeparator();
		sl_panelAddReservation.putConstraint(SpringLayout.NORTH, separatorAddReservation, 8, SpringLayout.SOUTH,
				lblAddReservation);
		sl_panelAddReservation.putConstraint(SpringLayout.WEST, separatorAddReservation, 16, SpringLayout.WEST,
				panelAddReservation);
		sl_panelAddReservation.putConstraint(SpringLayout.EAST, separatorAddReservation, -16, SpringLayout.EAST,
				panelAddReservation);
		panelAddReservation.add(separatorAddReservation);

		JLabel lblClientDniAdd = new JLabel("Client Dni:");
		sl_panelAddReservation.putConstraint(SpringLayout.NORTH, lblClientDniAdd, 16, SpringLayout.SOUTH,
				separatorAddReservation);
		sl_panelAddReservation.putConstraint(SpringLayout.WEST, lblClientDniAdd, 16, SpringLayout.WEST,
				separatorAddReservation);
		panelAddReservation.add(lblClientDniAdd);

		JLabel lblCarPlateAdd = new JLabel("Car Plate:");
		sl_panelAddReservation.putConstraint(SpringLayout.NORTH, lblCarPlateAdd, 16, SpringLayout.SOUTH,
				lblClientDniAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.EAST, lblCarPlateAdd, 0, SpringLayout.EAST, lblClientDniAdd);
		panelAddReservation.add(lblCarPlateAdd);

		JLabel lblStartDateAdd = new JLabel("Start Date:");
		sl_panelAddReservation.putConstraint(SpringLayout.NORTH, lblStartDateAdd, 51, SpringLayout.SOUTH,
				lblCarPlateAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.EAST, lblStartDateAdd, 0, SpringLayout.EAST, lblCarPlateAdd);
		panelAddReservation.add(lblStartDateAdd);

		JLabel lblEndDateAdd = new JLabel("End Date:");
		sl_panelAddReservation.putConstraint(SpringLayout.NORTH, lblEndDateAdd, 16, SpringLayout.SOUTH,
				lblStartDateAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.EAST, lblEndDateAdd, 0, SpringLayout.EAST, lblStartDateAdd);
		panelAddReservation.add(lblEndDateAdd);

		comboBoxClientDniAdd = new JComboBox<String>();
		sl_panelAddReservation.putConstraint(SpringLayout.NORTH, comboBoxClientDniAdd, -4, SpringLayout.NORTH,
				lblClientDniAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.WEST, comboBoxClientDniAdd, 10, SpringLayout.EAST,
				lblClientDniAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.EAST, comboBoxClientDniAdd, 0, SpringLayout.EAST,
				separatorAddReservation);
		panelAddReservation.add(comboBoxClientDniAdd);

		comboBoxCarPlateAdd = new JComboBox<String>();
		sl_panelAddReservation.putConstraint(SpringLayout.NORTH, comboBoxCarPlateAdd, -4, SpringLayout.NORTH,
				lblCarPlateAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.WEST, comboBoxCarPlateAdd, 0, SpringLayout.WEST,
				comboBoxClientDniAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.EAST, comboBoxCarPlateAdd, -28, SpringLayout.EAST,
				comboBoxClientDniAdd);
		panelAddReservation.add(comboBoxCarPlateAdd);

		carList = new JList<String>();
		carList.setEnabled(false);
		carList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPaneCarList = new JScrollPane(carList);
		sl_panelAddReservation.putConstraint(SpringLayout.NORTH, scrollPaneCarList, 8, SpringLayout.SOUTH,
				comboBoxCarPlateAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.WEST, scrollPaneCarList, 0, SpringLayout.WEST,
				comboBoxCarPlateAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.EAST, scrollPaneCarList, 0, SpringLayout.EAST,
				separatorAddReservation);
		panelAddReservation.add(scrollPaneCarList);

		textFieldStartDateAdd = new JTextField();
		sl_panelAddReservation.putConstraint(SpringLayout.SOUTH, scrollPaneCarList, -8, SpringLayout.NORTH,
				textFieldStartDateAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.SOUTH, carList, -6, SpringLayout.NORTH,
				textFieldStartDateAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.NORTH, textFieldStartDateAdd, -4, SpringLayout.NORTH,
				lblStartDateAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.WEST, textFieldStartDateAdd, 10, SpringLayout.EAST,
				lblStartDateAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.EAST, textFieldStartDateAdd, -16, SpringLayout.EAST,
				panelAddReservation);
		panelAddReservation.add(textFieldStartDateAdd);
		textFieldStartDateAdd.setColumns(10);

		textFieldEndDateAdd = new JTextField();
		sl_panelAddReservation.putConstraint(SpringLayout.NORTH, textFieldEndDateAdd, -4, SpringLayout.NORTH,
				lblEndDateAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.WEST, textFieldEndDateAdd, 0, SpringLayout.WEST,
				textFieldStartDateAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.EAST, textFieldEndDateAdd, 0, SpringLayout.EAST,
				textFieldStartDateAdd);
		panelAddReservation.add(textFieldEndDateAdd);
		textFieldEndDateAdd.setColumns(10);

		JButton btnResetAddReservation = new JButton("Reset");
		btnResetAddReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		panelAddReservation.add(btnResetAddReservation);

		JButton btnBackAddReservation = new JButton("Back");
		btnBackAddReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableReservationPanel.setModel(reservationManagment.reservationTable());
				changePanel("reservationPanel");
				reset();
			}
		});
		sl_panelAddReservation.putConstraint(SpringLayout.SOUTH, btnBackAddReservation, -16, SpringLayout.SOUTH,
				panelAddReservation);
		sl_panelAddReservation.putConstraint(SpringLayout.EAST, btnBackAddReservation, -16, SpringLayout.EAST,
				panelAddReservation);
		panelAddReservation.add(btnBackAddReservation);

		JButton btnSubmitAddReservation = new JButton("Submit");
		btnSubmitAddReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String startDate = textFieldStartDateAdd.getText();
				String endDate = textFieldEndDateAdd.getText();
				if (isValidFormat("dd-MM-yyyy", startDate, Locale.getDefault())
						&& isValidFormat("dd-MM-yyyy", endDate, Locale.getDefault())) {
					String clientDni = (String) comboBoxClientDniAdd.getSelectedItem();
					Client reservationClient = new Client(clientDni);
					Set<Car> cars = new HashSet<Car>();
					for (int x = 0; x < licensePlateList.size(); ++x) {
						Car newCar = new Car(licensePlateList.get(x));
						cars.add(newCar);
					}
					if (!cars.isEmpty()) {
						Reservation newReservation = new Reservation();
						newReservation.setClient(reservationClient);
						newReservation.setCars(cars);
						newReservation.setStartDate(startDate);
						newReservation.setEndDate(endDate);
						reservationManagment.addReservation(newReservation);
						JOptionPane.showMessageDialog(frmHibernateApplication, "Reservation successful!", "SUCCESSFUL",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(frmHibernateApplication,
								"You must add at least one car to the reservation", "WARNING",
								JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(frmHibernateApplication,
							"Invalid date format (Valid format: dd-MM-yyyy)", "WARNING", JOptionPane.WARNING_MESSAGE);
				}
				reset();
			}
		});
		sl_panelAddReservation.putConstraint(SpringLayout.NORTH, btnSubmitAddReservation, 20, SpringLayout.SOUTH,
				textFieldEndDateAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.NORTH, btnResetAddReservation, 0, SpringLayout.NORTH,
				btnSubmitAddReservation);
		sl_panelAddReservation.putConstraint(SpringLayout.WEST, btnResetAddReservation, 20, SpringLayout.EAST,
				btnSubmitAddReservation);
		sl_panelAddReservation.putConstraint(SpringLayout.WEST, btnSubmitAddReservation, 100, SpringLayout.WEST,
				separatorAddReservation);
		panelAddReservation.add(btnSubmitAddReservation);

		JButton btnAddCarReservation = new JButton("+");
		btnAddCarReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!licensePlateList.contains((String) comboBoxCarPlateAdd.getSelectedItem())) {
					licensePlateList.add((String) comboBoxCarPlateAdd.getSelectedItem());
				} else {
					JOptionPane.showMessageDialog(frmHibernateApplication, "Car already selected", "WARNING",
							JOptionPane.WARNING_MESSAGE);
				}
				carList.setModel(reservationManagment.addCarPlateList(licensePlateList));
			}
		});
		sl_panelAddReservation.putConstraint(SpringLayout.SOUTH, btnAddCarReservation, 0, SpringLayout.SOUTH,
				comboBoxCarPlateAdd);
		sl_panelAddReservation.putConstraint(SpringLayout.EAST, btnAddCarReservation, 28, SpringLayout.EAST,
				comboBoxCarPlateAdd);
		btnAddCarReservation.setMargin(new Insets(1, 4, 1, 4));
		panelAddReservation.add(btnAddCarReservation);

		JPanel panelModifyClient = new JPanel();
		frmHibernateApplication.getContentPane().add(panelModifyClient, "modifyClientPanel");
		SpringLayout sl_panelModifyClient = new SpringLayout();
		panelModifyClient.setLayout(sl_panelModifyClient);

		JLabel lblModifyClient = new JLabel("Modify Client");
		sl_panelModifyClient.putConstraint(SpringLayout.NORTH, lblModifyClient, 8, SpringLayout.NORTH,
				panelModifyClient);
		sl_panelModifyClient.putConstraint(SpringLayout.WEST, lblModifyClient, 0, SpringLayout.WEST, panelModifyClient);
		sl_panelModifyClient.putConstraint(SpringLayout.EAST, lblModifyClient, 0, SpringLayout.EAST, panelModifyClient);
		lblModifyClient.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifyClient.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelModifyClient.add(lblModifyClient);

		JSeparator separatorModifyClient = new JSeparator();
		sl_panelModifyClient.putConstraint(SpringLayout.NORTH, separatorModifyClient, 8, SpringLayout.SOUTH,
				lblModifyClient);
		sl_panelModifyClient.putConstraint(SpringLayout.WEST, separatorModifyClient, 16, SpringLayout.WEST,
				panelModifyClient);
		sl_panelModifyClient.putConstraint(SpringLayout.EAST, separatorModifyClient, -16, SpringLayout.EAST,
				panelModifyClient);
		panelModifyClient.add(separatorModifyClient);

		JLabel lblDniModify = new JLabel("DNI:");
		sl_panelModifyClient.putConstraint(SpringLayout.NORTH, lblDniModify, 16, SpringLayout.SOUTH,
				separatorModifyClient);
		sl_panelModifyClient.putConstraint(SpringLayout.WEST, lblDniModify, 16, SpringLayout.WEST,
				separatorModifyClient);
		panelModifyClient.add(lblDniModify);

		JLabel lblNameModify = new JLabel("Name:");
		sl_panelModifyClient.putConstraint(SpringLayout.NORTH, lblNameModify, 16, SpringLayout.SOUTH, lblDniModify);
		sl_panelModifyClient.putConstraint(SpringLayout.EAST, lblNameModify, 0, SpringLayout.EAST, lblDniModify);
		panelModifyClient.add(lblNameModify);

		JLabel lblAddressModify = new JLabel("Address:");
		sl_panelModifyClient.putConstraint(SpringLayout.NORTH, lblAddressModify, 16, SpringLayout.SOUTH, lblNameModify);
		sl_panelModifyClient.putConstraint(SpringLayout.EAST, lblAddressModify, 0, SpringLayout.EAST, lblNameModify);
		panelModifyClient.add(lblAddressModify);

		JLabel lblPhoneModify = new JLabel("Phone:");
		sl_panelModifyClient.putConstraint(SpringLayout.NORTH, lblPhoneModify, 16, SpringLayout.SOUTH,
				lblAddressModify);
		sl_panelModifyClient.putConstraint(SpringLayout.EAST, lblPhoneModify, 0, SpringLayout.EAST, lblAddressModify);
		panelModifyClient.add(lblPhoneModify);

		textFieldDniModify = new JTextField();
		textFieldDniModify.setEditable(false);
		sl_panelModifyClient.putConstraint(SpringLayout.NORTH, textFieldDniModify, -4, SpringLayout.NORTH,
				lblDniModify);
		sl_panelModifyClient.putConstraint(SpringLayout.WEST, textFieldDniModify, 10, SpringLayout.EAST, lblDniModify);
		sl_panelModifyClient.putConstraint(SpringLayout.EAST, textFieldDniModify, 0, SpringLayout.EAST,
				separatorModifyClient);
		textFieldDniModify.setColumns(10);
		panelModifyClient.add(textFieldDniModify);

		textFieldNameModify = new JTextField();
		textFieldNameModify.setEnabled(false);
		sl_panelModifyClient.putConstraint(SpringLayout.NORTH, textFieldNameModify, -4, SpringLayout.NORTH,
				lblNameModify);
		sl_panelModifyClient.putConstraint(SpringLayout.WEST, textFieldNameModify, 0, SpringLayout.WEST,
				textFieldDniModify);
		sl_panelModifyClient.putConstraint(SpringLayout.EAST, textFieldNameModify, 0, SpringLayout.EAST,
				textFieldDniModify);
		textFieldNameModify.setColumns(10);
		panelModifyClient.add(textFieldNameModify);

		textFieldAddressModify = new JTextField();
		sl_panelModifyClient.putConstraint(SpringLayout.NORTH, textFieldAddressModify, -4, SpringLayout.NORTH,
				lblAddressModify);
		sl_panelModifyClient.putConstraint(SpringLayout.WEST, textFieldAddressModify, 0, SpringLayout.WEST,
				textFieldNameModify);
		sl_panelModifyClient.putConstraint(SpringLayout.EAST, textFieldAddressModify, 0, SpringLayout.EAST,
				textFieldNameModify);
		textFieldAddressModify.setColumns(10);
		panelModifyClient.add(textFieldAddressModify);

		textFieldPhoneModify = new JTextField();
		sl_panelModifyClient.putConstraint(SpringLayout.NORTH, textFieldPhoneModify, -4, SpringLayout.NORTH,
				lblPhoneModify);
		sl_panelModifyClient.putConstraint(SpringLayout.WEST, textFieldPhoneModify, 0, SpringLayout.WEST,
				textFieldAddressModify);
		sl_panelModifyClient.putConstraint(SpringLayout.EAST, textFieldPhoneModify, 0, SpringLayout.EAST,
				textFieldAddressModify);
		textFieldPhoneModify.setColumns(10);
		panelModifyClient.add(textFieldPhoneModify);

		JButton btnSubmitModifyClient = new JButton("Submit");
		btnSubmitModifyClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dni = textFieldDniModify.getText();
				String name = textFieldNameModify.getText();
				String address = textFieldAddressModify.getText();
				String phone = textFieldPhoneModify.getText();
				if (!address.isEmpty() && !phone.isEmpty()) {
					Client modifiedClient = new Client(dni);
					modifiedClient.setName(name);
					modifiedClient.setAddress(address);
					modifiedClient.setPhone(phone);
					clientManagment.modifyClient(modifiedClient);
					JOptionPane.showMessageDialog(frmHibernateApplication, "Client modification successful!",
							"SUCCESSFUL", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frmHibernateApplication, "Empty fields, please fill in all fields",
							"WARNING", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		sl_panelModifyClient.putConstraint(SpringLayout.NORTH, btnSubmitModifyClient, 50, SpringLayout.SOUTH,
				textFieldPhoneModify);
		sl_panelModifyClient.putConstraint(SpringLayout.WEST, btnSubmitModifyClient, 100, SpringLayout.WEST,
				separatorModifyClient);
		panelModifyClient.add(btnSubmitModifyClient);

		JButton btnResetModifyClient = new JButton("Reset");
		btnResetModifyClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		sl_panelModifyClient.putConstraint(SpringLayout.NORTH, btnResetModifyClient, 0, SpringLayout.NORTH,
				btnSubmitModifyClient);
		sl_panelModifyClient.putConstraint(SpringLayout.WEST, btnResetModifyClient, 20, SpringLayout.EAST,
				btnSubmitModifyClient);
		panelModifyClient.add(btnResetModifyClient);

		JButton btnBackModifyClient = new JButton("Back");
		btnBackModifyClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableClientPanel.setModel(clientManagment.clientTable());
				reset();
				changePanel("clientPanel");
			}
		});
		sl_panelModifyClient.putConstraint(SpringLayout.SOUTH, btnBackModifyClient, -16, SpringLayout.SOUTH,
				panelModifyClient);
		sl_panelModifyClient.putConstraint(SpringLayout.EAST, btnBackModifyClient, -16, SpringLayout.EAST,
				panelModifyClient);
		panelModifyClient.add(btnBackModifyClient);
	}

	private void changePanel(String panelName) {
		CardLayout cardLayout = (CardLayout) frmHibernateApplication.getContentPane().getLayout();
		cardLayout.show(frmHibernateApplication.getContentPane(), panelName);
	}

	private void reset() {
		textFieldSearchClient.setText("");
		textFieldSearchReservation.setText("");
		textFieldPlateAdd.setText("");
		textFieldModelAdd.setText("");
		textFieldColorAdd.setText("");
		textFieldBrandAdd.setText("");
		textFieldDniAdd.setText("");
		textFieldNameAdd.setText("");
		textFieldAddressAdd.setText("");
		textFieldPhoneAdd.setText("");
		textFieldStartDateAdd.setText("");
		textFieldEndDateAdd.setText("");
		textFieldAddressModify.setText("");
		textFieldPhoneModify.setText("");
		licensePlateList.clear();
		try {
			carList.setModel(reservationManagment.cleanCarPlateList());
		} catch (NullPointerException e) {

		}
	}

	public void chargeClientInfo() {
		int row = tableClientPanel.getSelectedRow();
		textFieldDniModify.setText(tableClientPanel.getValueAt(row, 0).toString());
		textFieldNameModify.setText(tableClientPanel.getValueAt(row, 1).toString());
		textFieldAddressModify.setText(tableClientPanel.getValueAt(row, 2).toString());
		textFieldPhoneModify.setText(tableClientPanel.getValueAt(row, 3).toString());
	}

	/*
	 * public void reservingDates(String startDate, String endDate) {
	 * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy",
	 * Locale.getDefault()); LocalDate start = LocalDate.parse(startDate,
	 * formatter); LocalDate end = LocalDate.parse(endDate, formatter);
	 * ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
	 * start.datesUntil(end).sequential().forEach(dates::add); for (int i=0;
	 * i<dates.size(); ++i) { reservationDates.add(dates.get(i).format(formatter));
	 * } }
	 * 
	 * public boolean containsDates(String startDate, String endDate) { boolean
	 * contains = false; DateTimeFormatter formatter =
	 * DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault()); LocalDate
	 * start = LocalDate.parse(startDate, formatter); LocalDate end =
	 * LocalDate.parse(endDate, formatter); ArrayList<LocalDate> dates = new
	 * ArrayList<LocalDate>(); ArrayList<String> tempList = new ArrayList<String>();
	 * start.datesUntil(end).sequential().forEach(dates::add); while (contains ==
	 * false) { for (int i=0; i<dates.size(); ++i) {
	 * tempList.add(dates.get(i).format(formatter)); if
	 * (reservationDates.contains(tempList.get(i))) { contains = true; } } } return
	 * contains; }
	 */
	public boolean isValidFormat(String format, String value, Locale locale) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
			LocalDate ld = LocalDate.parse(value, formatter);
			String result = ld.format(formatter);
			return result.equals(value);
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(frmHibernateApplication, "Invalid date format! (Valid format: dd-MM-yyyy)",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			reset();
		}
		return false;
	}
}

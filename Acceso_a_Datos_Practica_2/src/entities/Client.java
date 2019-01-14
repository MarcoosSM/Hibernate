package entities;

public class Client {
	
	//attributes
	private String name;
	private String address;
	private String dni;
	private String phone;
	
	//builder
	public Client(String name, String address, String dni, String phone) {
		this.name = name;
		this.address = address;
		this.dni = dni;
		this.phone = phone;
	}

	//getters and setters	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}

package uniandes.dpoo.modelo;

public class Ingrediente {

	private String nombre;
	private int costoAdicional;
	private int calorias;
	
	public Ingrediente(String nombre, int costoAdicional, int calorias) {
		this.costoAdicional = costoAdicional;
		this.nombre = nombre;
		this.calorias = calorias;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCostoAdicional() {
		return costoAdicional;
	}

	public void setCostoAdicional(int costoAdicional) {
		this.costoAdicional = costoAdicional;
	}
	
	public int getCalorias() {
		return calorias;
	}
	
}

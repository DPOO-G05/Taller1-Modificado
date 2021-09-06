package uniandes.dpoo.modelo;


public class ProductoMenu implements Producto {
	private String nombre;
	private int precioBase;
	private int calorias;
	
	public ProductoMenu(String nombreP, int precioP, int calorias) {
		this.nombre = nombreP;
		this.precioBase = precioP;
		this.calorias = calorias;
	}
	
	/**
	 * Permite convertir un objeto ProductoMenu a un String 
	 */
	@Override
	public String toString()
	{
		return this.nombre + "-" + this.precioBase;
	}

	@Override
	public int getPrecio() {
		return this.precioBase;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public String generarTextoFactura() {
		String base = getNombre();
		String texto = "Producto: " + base + "\n\t\tPrecio: " + String.valueOf(this.precioBase) + "\n\t\tIVA: " + String.format("%.2f\n", this.precioBase* 0.19 );
		texto += String.format("\n\t\tCalorias: %d\n", getCalorias());
		return texto;
	}

	@Override
	public int getCalorias() {
		return calorias;
	}
}

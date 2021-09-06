package uniandes.dpoo.modelo;


public class ProductoMenu implements Producto {
	private String nombre;
	private int precioBase;
	
	public ProductoMenu(String nombreP, int precioP) {
		this.nombre = nombreP;
		this.precioBase = precioP;
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
		return texto;
	}
}

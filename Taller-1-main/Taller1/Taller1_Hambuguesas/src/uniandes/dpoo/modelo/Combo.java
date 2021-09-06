package uniandes.dpoo.modelo;

import java.util.ArrayList;

public class Combo implements Producto {
	private String nombreCombo;
	private int calorias;
	private int precio;
	private double descuento;
	private ArrayList<ProductoMenu> itemsCombo;    // SIMPLIFICACION: Lista de nombres de los productos del menu que componen el combo. Revisar el diagrama de clases
	
	/**
	 * Constructor de la clase
	 */
	public Combo(String nombreP, double descuentoP)
	{
		this.nombreCombo = nombreP;
		this.descuento = descuentoP;
		this.itemsCombo = new ArrayList<ProductoMenu>();    

	}
	

	private void calcularCalorias() {
		for (ProductoMenu producto: itemsCombo) {
			calorias += producto.getCalorias();
		}

	}
	
	public int getCalorias() {
		calcularCalorias();
		return calorias;
	}


	public void setCalorias(int calorias) {
		this.calorias = calorias;
	}


	public String getNombreCombo() {
		return nombreCombo;
	}



	public void setNombreCombo(String nombreCombo) {
		this.nombreCombo = nombreCombo;
	}



	public double getDescuento() {
		return descuento;
	}



	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}



	public ArrayList<ProductoMenu> getItemsCombo() {
		return itemsCombo;
	}



	public void setItemsCombo(ArrayList<ProductoMenu> itemsCombo) {
		this.itemsCombo = itemsCombo;
	}



	/**
	 * Agrega el nombre de un productoMenu a un combo. Metodo simplificado.
	 * @param nombreProducto
	 * TODO: Cambiar esto para que agregue un objeto en vez de un string.
	 */
	public void agregarProducto(ProductoMenu nombreProducto)
	{
		this.itemsCombo.add(nombreProducto);
	}
	
	/**
	 * Permite convertir un objeto Combo a un String 
	 */
	@Override
	public String toString()
	{
		return this.nombreCombo + "-" + this.descuento + "-" + this.itemsCombo;
	}
	
	@Override
	public int getPrecio() {
		calcularPrecio();
		return this.precio;
	}

	@Override
	public String getNombre() {
		return toString();
	}

	@Override
	public String generarTextoFactura() {
		String base = getNombre();
		String texto = "Producto: " + base + "\n\t\tPrecio: " + String.valueOf(this.precio) + "\n\t\tIVA: " + String.format("%.2f\n", this.precio * 0.19 );
		texto += String.format("\n\t\tCalorias: %d\n", getCalorias());
		return texto;
	}
	
	private void calcularPrecio() {
		ArrayList<ProductoMenu> productos = this.itemsCombo;
		double descuento = this.descuento;
		
		int suma = 0;
		
		for (ProductoMenu producto: productos) {

			suma += producto.getPrecio();
		}
		
		this.precio = (int) (suma * (1-descuento/100));
	}

}

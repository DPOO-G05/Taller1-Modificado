package uniandes.dpoo.modelo;

import java.util.ArrayList;

public class ProductoAjustado implements Producto {

	private ProductoMenu base;
	private int precio;
	private ArrayList<Ingrediente> agregados;
	private ArrayList<Ingrediente> eliminados;

	
	public ProductoAjustado(ProductoMenu base) {
		this.base = base;
		this.precio = base.getPrecio();
		this.agregados = new ArrayList<Ingrediente>();
		this.eliminados = new ArrayList<Ingrediente>();
	}


	public ArrayList<Ingrediente> getAgregados() {
		return agregados;
	}


	public ArrayList<Ingrediente> getEliminados() {
		return eliminados;
	}


	public ProductoMenu getBase() {
		return base;
	}


	public void setBase(ProductoMenu base) {
		this.base = base;
	}


	@Override
	public int getPrecio() {
		return this.precio;
	}


	@Override
	public String getNombre() {
		String nombre = this.base.getNombre();
		
		for (Ingrediente ingrediente : this.eliminados) {
			nombre += " SIN " + ingrediente.getNombre();
		}
		
		for (Ingrediente ingrediente: this.agregados) {
			nombre += " ADICION " + ingrediente.getNombre();
		}
		
		
		return nombre;
	}


	@Override
	public String generarTextoFactura() {
		// TODO Auto-generated method stub
		String base = getNombre();
		String texto = "Producto: " + base + "\n\t\tPrecio: " + String.valueOf(this.precio) + "\n\t\tIVA: " + String.format("%.2f\n", this.precio* 0.19 );
		return texto;

	}
	
	
	public void modificarProducto(ArrayList<String> modificaciones, Restaurante restaurante) {

		ArrayList<Ingrediente> ingredientes = restaurante.getIngredientes();
		Ingrediente ingrediente;
		for (String modificacion: modificaciones) {
			String[] array = modificacion.split("");
			if (array[0].equals((String) "+")) {
				
				ingrediente = ingredientes.get(Integer.parseInt(array[1]) - 1);
				precio += ingrediente.getCostoAdicional();
				this.agregados.add(ingrediente);

				
			}
			else if (array[0].equals((String) "-")) {

				ingrediente = ingredientes.get(Integer.parseInt(array[1]) - 1);
				this.eliminados.add(ingrediente);
			}
			
			
			
			
		}
		
		
	}
	
	
	

}

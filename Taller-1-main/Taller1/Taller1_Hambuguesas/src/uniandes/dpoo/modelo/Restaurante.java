package uniandes.dpoo.modelo;

import java.io.File;
import java.lang.Math;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import uniandes.dpoo.procesamiento.LoaderInformacionArchivos;

public class Restaurante {

	private ArrayList<Ingrediente> ingredientes;
	private ArrayList<ProductoMenu> menuBase;
	private ArrayList<ProductoMenu> bebidas;
	private Pedido pedidoEnCurso;
	private ArrayList<Pedido> pedidos;
	private ArrayList<Combo> combos;
	
	public Restaurante () {
		this.pedidos = new ArrayList<>();
	}

	//Getters y Setters

	public ArrayList<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public ArrayList<ProductoMenu> getBebidas() {
		return bebidas;
	}

	public void setBebidas(ArrayList<ProductoMenu> bebidas) {
		this.bebidas = bebidas;
	}

	public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public ArrayList<ProductoMenu> getMenuBase() {
		return menuBase;
	}

	public void setMenuBase(ArrayList<ProductoMenu> menuBase) {
		this.menuBase = menuBase;
	}

	public Pedido getPedidoEnCurso() {

		if (this.pedidoEnCurso != null) {

			return pedidoEnCurso;

		}
		else {
			throw new NullPointerException("Debe iniciar un pedido antes de consultarlo!");
		}
	}

	public void setPedidoEnCurso(Pedido pedidoEnCurso) {
		this.pedidoEnCurso = pedidoEnCurso;
	}

	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(ArrayList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public ArrayList<Combo> getCombos() {
		return combos;
	}

	public void setCombos(ArrayList<Combo> combos) {
		this.combos = combos;
	}


	
	
	
	
	//Métodos
	
	
	public void iniciarPedido(String nombreCliente, String direccionCliente) {
		Pedido pedido = new Pedido(nombreCliente, direccionCliente);
		this.pedidoEnCurso = pedido;
		int id = (int) (Math.random() * 1_000_000);
		pedido.setIdPedido(id);
	}

	
	
	public String cerrarYGuardarPedido() {
		boolean respuesta = false;
		for(Pedido pedido: this.pedidos) {
			if (this.pedidoEnCurso.equals(pedido)) {
				respuesta = true;
			}
		}
		String iguales = respuesta ? "\nSi hay un pedido igual": "\nNo hay un pedido igual";
		this.pedidos.add(this.pedidoEnCurso);

		return this.pedidoEnCurso.guardarFactura() + iguales;
	}
	
	public void cargarInformacionRestaurante(String rutaIngredientes, String rutaMenu, String rutaCombos, String rutaBebidas) {
		
		cargarMenu(rutaMenu);
		cargarBebidas(rutaBebidas);
		cargarIngredientes(rutaIngredientes);
		cargarCombos(rutaCombos);
		
	}
	
	
	public Pedido consultarPedido(int id) {
		Pedido respuesta = null;
		if (this.pedidos != null){

			for (Pedido pedido: this.pedidos) {

				if (pedido.getIdPedido() == id){
					respuesta = pedido;
					break;
				}
			}
		
		if(respuesta == null) {
			throw new RuntimeException("No existe ningún pedido con ese ID");
		}
	}
	
		else {
			throw new NullPointerException("Debe inicializar la lista de pedidos");
		}
		
		return respuesta;
		
	}
	
	
	
	private void cargarIngredientes(String rutaIngredientes) {
	
		ArrayList<Ingrediente> ingredientes = new ArrayList<>();
	
		try
		{
			ingredientes = LoaderInformacionArchivos.leerInfoArchivoIngredientes(rutaIngredientes);
			System.out.println("OK Se carg� el archivo " + rutaIngredientes + " con informaci�n de los Ingredientes.");
			this.ingredientes = ingredientes;
			
			for (Ingrediente ingrediente: this.ingredientes) {
				System.out.printf("%s", ingrediente);
			}
			
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ERROR: el archivo " + rutaIngredientes + " no se encontr�.");
			System.out.println(e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("ERROR: hubo un problema leyendo el archivo " + rutaIngredientes);
			System.out.println(e.getMessage());
		}

		
		
		
	}
	
	
	private void cargarMenu(String rutaMenu) {
		
		/**
		 * Leer el archivo de Productos Menu y obtener su informacion en una lista de objetos ProductoMenu
		 * @param rutaArchivo
		 * @return Lista de objetos ProductoMenu
		 */
		
			ArrayList<ProductoMenu> productosMenu = new ArrayList<ProductoMenu>();
			try
			{
				productosMenu = LoaderInformacionArchivos.leerInfoArchivoProductosMenu(rutaMenu);
				System.out.println("OK Se carg� el archivo " + rutaMenu + " con informaci�n de los Productos Menu.");
				this.menuBase = productosMenu;
				for (ProductoMenu producto: this.menuBase) {
					System.out.printf("%s", producto.getNombre());
				}
			}
			catch (FileNotFoundException e)
			{
				System.out.println("ERROR: el archivo " + rutaMenu + " no se encontr�.");
				System.out.println(e.getMessage());
			}
			catch (IOException e)
			{
				System.out.println("ERROR: hubo un problema leyendo el archivo " + rutaMenu);
				System.out.println(e.getMessage());
			}


	}
	
	
	private void cargarCombos(String rutaCombos) {
		
		ArrayList<Combo> combos = new ArrayList<Combo>();
		try
		{
			ArrayList<ProductoMenu> temp = new ArrayList<>();
			for(ProductoMenu producto: getMenuBase()) {
				temp.add(producto);
			}

			temp.addAll(bebidas);

			combos = LoaderInformacionArchivos.leerInfoArchivoCombos(rutaCombos, temp);
			this.combos = combos;
			System.out.println("OK Se carg� el archivo " + rutaCombos + " con informaci�n de los Combos.");
			
			for (Combo combo: this.combos) {
				System.out.printf("%s", combo);
			}
			
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ERROR: el archivo " + rutaCombos+ " no se encontr�.");
			System.out.println(e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("ERROR: hubo un problema leyendo el archivo " + rutaCombos);
			System.out.println(e.getMessage());
		}

	}
	
	private void cargarBebidas(String rutaBebidas) {
		ArrayList<ProductoMenu> bebidas = new ArrayList<>();
		
		try
		{
			bebidas = LoaderInformacionArchivos.leerInfoArchivoProductosMenu(rutaBebidas);
			this.bebidas = bebidas;
			System.out.println("OK Se cargo el archivo " + rutaBebidas + " con informaci�n de las bebidas.");

			
			for (ProductoMenu bebida: this.bebidas) {
				System.out.printf("%s", bebida.getNombre());
			}
	
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
}

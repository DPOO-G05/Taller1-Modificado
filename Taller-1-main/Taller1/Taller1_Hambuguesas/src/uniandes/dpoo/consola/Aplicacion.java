package uniandes.dpoo.consola;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import uniandes.dpoo.modelo.Combo;
import uniandes.dpoo.modelo.Ingrediente;
import uniandes.dpoo.modelo.Pedido;
import uniandes.dpoo.modelo.Producto;
import uniandes.dpoo.modelo.ProductoAjustado;
import uniandes.dpoo.modelo.Restaurante;
import uniandes.dpoo.modelo.ProductoMenu;
import uniandes.dpoo.procesamiento.LoaderInformacionArchivos;;



public class Aplicacion {

	private Restaurante restaurante;

	public Aplicacion(Restaurante restaurante) {
		this.restaurante = restaurante;
	}
	
	public void mostrarMenuPrincipal() {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\nMenu:");
			System.out.println("1: Cargar Informacion");
			System.out.println("2: Iniciar Pedido");
			System.out.println("3: Agregar Producto");
			System.out.println("4: Agregar Combo");
			System.out.println("5: Verificar Estado Pedido");
			System.out.println("6: Terminar Pedido");
			System.out.println("7: Salir");
			int opcionSeleccionada = Integer.parseInt(input("Ingrese la opción deseada"));
			ejecutarOpcion(opcionSeleccionada);
			if (opcionSeleccionada == 7) {
				continuar = false;
			}
		}
	}
	
	private void mostrarMenuBase() {
		ArrayList<ProductoMenu> productos = this.restaurante.getMenuBase(); 

		System.out.println("Digite el número del producto que desea agregar a su orden:\n ");
		int size = productos.size();
		for (int i = 0; i < size ; i++) {
			ProductoMenu producto = productos.get(i);
			System.out.printf("%d\t%s\n", i + 1, producto.getNombre());
		}

		int posicion = Integer.parseInt(input("Numero")) - 1;
		int opcion = Integer.parseInt(input("¿Desea modificar su producto? Si: 1, No: 2"));
	
		boolean indicadora = opcion == 1 ? true: false;

		Pedido pedido = this.restaurante.getPedidoEnCurso();
		ProductoMenu producto = productos.get(posicion);
		if (indicadora) {
			ArrayList<String> modificaciones = mostrarMenuIngredientes();
			pedido.agregarProducto(producto, modificaciones, this.restaurante);
		}
		else {
			pedido.agregarProducto(producto);
		}
		
		System.out.println("Producto agregado exitosamente");
		
	}

	
	private ArrayList<String> mostrarMenuIngredientes() {

		ArrayList<String> modificaciones = new ArrayList<>(); 

		ArrayList<Ingrediente> ingredientes = this.restaurante.getIngredientes();
	
		boolean continuar = true;
		while (continuar) {
			System.out.println("Ingrese +<numero> para agregar o -<numero> para eliminar");
			
			for(int i = 0; i < ingredientes.size(); i++) {
				
				Ingrediente ingrediente = ingredientes.get(i);
				System.out.printf("%d\t%s\n",i + 1, ingrediente.getNombre());
			}
			
			String input = input(":");
			modificaciones.add(input.trim());
			int condicion = Integer.parseInt(input("Ingrese 1 para seguir modificando o 0 para retornar al menú principal"));
			
			if (condicion == 0) {
				continuar = false;
			}
		}
		
		return modificaciones;
	}
	
	
	private void mostrarEstadoPedido() {
		Pedido pedido = this.restaurante.getPedidoEnCurso();
		ArrayList<Producto> productos = pedido.getItemsPedido();
	
		System.out.println("\nEstado actual del pedido:\n");
		for (int i = 0; i < productos.size(); i++) {
			Producto producto = productos.get(i);
			System.out.printf("Producto %d\t%s\n", i + 1, producto.getNombre());
		}
	}
	
	
	private void mostrarMenuCombos() {

		ArrayList<Combo> combos = this.restaurante.getCombos();

		System.out.println("Digite el número del combo que desea agregar:");
		
		for(int i = 0; i < combos.size(); i++) {
			System.out.printf("%d\t%s\n", i + 1, combos.get(i));
		}
		
		int posicion = Integer.parseInt(input("Numero")) - 1;
		
		this.restaurante.getPedidoEnCurso().agregarProducto((Producto) combos.get(posicion));
		
	}
	
	
	private void cerrarYFacturar() {
		System.out.println(this.restaurante.cerrarYGuardarPedido());
	}
	
	
	public void ejecutarOpcion(int opcionSeleccionada) {
		// TODO: IMPLEMENTAR
	
		int o = opcionSeleccionada;
		switch (o) {
			case 1:
				String fileIngredientes = "./data/ingredientes.txt";
				String fileMenu = "./data/menu.txt";
				String fileCombos = "./data/combos.txt";
				this.restaurante.cargarInformacionRestaurante(fileIngredientes,fileMenu , fileCombos);
				break;

			case 2:
				String nombreCliente = input("Ingrese su nombre");
				String direccion = input("Ingrese su dirección");
				this.restaurante.iniciarPedido(nombreCliente.trim().toLowerCase(), direccion.trim().toLowerCase());
				break;

			case 3:
				mostrarMenuBase();
				mostrarEstadoPedido();
				break;
			case 4:
				mostrarMenuCombos();
				mostrarEstadoPedido();
				break;
			case 5:
				mostrarEstadoPedido();
				break;
			case 6:
				cerrarYFacturar();
				break;
			case 7:
				System.out.println("Gracias por tu compra!");
				break;
		}
	}
	
	/**
	 * Leer el archivo de combos y obtener su informacion en una lista de objetos Combo
	 * @param rutaArchivo
	 * @param productosMenu Lista de productos menu donde aparecen todos los posibles productos de un combo
	 * @return Lista de objetos Combo
	 */

	/**
	 * Este metodo sirve para imprimir un mensaje en la consola pidiendole
	 * informacion al usuario y luego leer lo que escriba el usuario.
	 * 
	 * @param mensaje El mensaje que se le mostrara al usuario
	 * @return La cadena de caracteres que el usuario escriba como respuesta.
	 */
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Inicio de ejecuci�n de la aplicaci�n");
		
		Restaurante restaurante = new Restaurante();
		Aplicacion app = new Aplicacion(restaurante);
		app.mostrarMenuPrincipal();

		
		}

}

package uniandes.dpoo.modelo;

import java.util.ArrayList;

public class Pedido {
	
	private static int numeroPedidos;
	private int idPedido;
	private String nombreCliente;
	private String direccionCliente;
	private ArrayList<Producto> itemsPedido; 
	
	
	public Pedido (String nombreCliente, String direccionCliente ) {
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.itemsPedido = new ArrayList<Producto>();
		numeroPedidos++;
	}
	
	//Getters y Setters

	
	
	// Métodos

	@Override public boolean equals(Object object) {
		boolean respuesta = true;
		
		if (object instanceof Pedido) {
			Pedido objeto = (Pedido) object;
			for(Producto producto1: objeto.getItemsPedido() ) {
				for(Producto producto2: this.getItemsPedido()) {
					if (!producto2.getNombre().equals(producto1.getNombre())) {
						respuesta = false;
						break;
					}
				}
			}
		}
		else {
			respuesta = false;
		}
		
		return respuesta;
	}
	
	
	public String guardarFactura() {
		return generarTextoFactura();
	}
	
	
	
	private String generarTextoFactura() {
		String header = "**************";
		int IVA = getPrecioIVAPedido();
		int neto = getPrecioNetoPedido();
		int total = getPrecioTotalPedido();
		int calorias = getCalorias();
		String cuerpo = "";
		
		for (Producto producto: itemsPedido) {
			cuerpo += producto.generarTextoFactura();
		}
		
		
		String factura = String.format("%s\n\t%s\n%s%s\nCALORIAS: %d\tTOTAL: %d\tNETO: %d\tIVA: %d", header, "FACTURA", cuerpo, header,calorias,total,neto,IVA); 
		return factura; 
	}
	
	public static int getNumeroPedidos() {
		return numeroPedidos;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getDireccionCliente() {
		return direccionCliente;
	}

	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	public ArrayList<Producto> getItemsPedido() {
		return itemsPedido;
	}

	public void setItemsPedido(ArrayList<Producto> itemsPedido) {
		this.itemsPedido = itemsPedido;
	}

	public void agregarProducto(Producto nuevoItem ) {
		this.itemsPedido.add(nuevoItem);
	}
	
	public void agregarProducto(Producto nuevoItem, ArrayList<String> modificaciones, Restaurante restaurante) {
		ProductoMenu base = (ProductoMenu) nuevoItem;
		ProductoAjustado ajustado = new ProductoAjustado(base);
		ajustado.modificarProducto(modificaciones, restaurante);
		this.itemsPedido.add(ajustado);
	}

	private int getPrecioNetoPedido() {
		int total = 0;

		for (Producto producto: this.itemsPedido) {
			total += producto.getPrecio();
		}
		
		
		return total;
	}
	
	
	private int getPrecioTotalPedido() {
		return getPrecioIVAPedido() + getPrecioNetoPedido();
	}
	
	
	private int getPrecioIVAPedido() {
		return (int) (getPrecioNetoPedido() * 0.19);
	}
	
	private int getCalorias() {
		int calorias = 0;
		for(Producto producto: this.itemsPedido) {
			calorias += producto.getCalorias();
		}
		return calorias;
	}

	
	
	
}

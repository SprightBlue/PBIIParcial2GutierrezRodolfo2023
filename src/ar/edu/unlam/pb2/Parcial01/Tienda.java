package ar.edu.unlam.pb2.Parcial01;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Tienda {

	/**
	 * En esta ocasion deberemos resolver un producto software que nos permita
	 * administrar la venta de productos o servicios de nuestra tienda. Venderemos
	 * entonces, productos como mouse o teclados y servicios como el soporte tecnico
	 * a domicilio. Sabemos que la tienda cuenta con items Vendibles que pueden ser
	 * del tipo Producto o Servicio. Ademas, podemos registrar el stock de los
	 * productos, los clientes a quienes les vendemos algun producto o servicio, las
	 * ventas y los vendedores de la tienda. Antes de realizar alguna operacion, se
	 * debera obtener el elemento correspondiente de las colecciones. Ejemplo: Si
	 * quisiera realizar alguna operacion con un cliente, el mismo debe obtenerse de
	 * la coleccion de clientes.
	 * 
	 * Cada Venta contiene renglones los cuales representa a los productos o
	 * servicios que se incluyen en la misma. Tambien cuenta con el Cliente y
	 * Vendedor que participan en la Venta. Cuando agregamos un vendible a una
	 * venta, lo haremos con 1 unidad. En una version posterior, admitiremos
	 * cantidades variables.
	 * 
	 * Cada Item debe compararse por nombre y precio, en caso de ser necesario.
	 * Recordar que los items deben ser Vendibles.
	 * 
	 */

	private String cuit;
	private String nombre;
	private Set<Vendible> vendibles;
	private Map<Producto, Integer> stock;
	private List<Cliente> clientes;
	private Set<Venta> ventas;
	private Set<Vendedor> vendedores;

	public Tienda(String cuit, String nombre) {
		this.setCuit(cuit);
		this.setNombre(nombre);
		this.vendibles = new TreeSet<Vendible>(new Comparator<Vendible>() {

			@Override
			public int compare(Vendible vendible1, Vendible vendible2) {
				return vendible1.getCodigo().compareTo(vendible2.getCodigo());
			}
			
		});
		this.stock = new TreeMap<Producto, Integer>( new Comparator<Producto>() {

			@Override
			public int compare(Producto producto1, Producto producto2) {
				return producto1.getCodigo().compareTo(producto2.getCodigo());
			}
			
		});
		this.clientes = new ArrayList<Cliente>();
		this.ventas = new TreeSet<Venta>(new Comparator<Venta>() {

			@Override
			public int compare(Venta venta1, Venta venta2) {
				return venta1.getCodigo().compareTo(venta2.getCodigo());
			}
			
		});
		this.vendedores = new TreeSet<Vendedor>(new Comparator<Vendedor>() {

			@Override
			public int compare(Vendedor vendedor1, Vendedor vendedor2) {
				return vendedor1.getDni().compareTo(vendedor2.getDni());
			}
			
		});
		// TODO: Completar el constructor para el correcto funcionamiento del software
	}

	// TODO: Completar con los getters y setters necesarios

	public Vendible getVendible(Integer codigo) {
		// TODO: Obtiene un producto o servicio de la coleccion de vendibles utilizando
		// el codigo. En caso de no existir devuelve null.
		Vendible vendibleBuscado = null;
		Iterator<Vendible> iterador = vendibles.iterator();
		boolean encontrado = false;
		while(!encontrado && iterador.hasNext()) {
			Vendible vendibleIterado = iterador.next();
			if(vendibleIterado.getCodigo().equals(codigo)) {
				vendibleBuscado = vendibleIterado;
				encontrado = true;
			}
		}
		return vendibleBuscado;
	}

	public void agregarProducto(Producto producto) {
		this.agregarProducto(producto, 0);
	}

	public void agregarProducto(Producto producto, Integer stockInicial) {
		// TODO: Agrega un producto a la coleccion de vendibles y pone en la coleccion
		// de stocks al producto con su stock inicial
		this.vendibles.add(producto);
		this.stock.put(producto, stockInicial);
	}

	public void agregarServicio(Servicio servicio) {
		// TODO: Agrega un servicio a la coleccion de vendibles
		this.vendibles.add(servicio);
	}

	public Integer getStock(Producto producto) {
		return stock.get(producto);
	}

	public void agregarStock(Producto producto, Integer incremento){
		Integer stock = this.getStock(producto);
		stock+=incremento;
		this.stock.put(producto, stock);
	}

	public void agregarCliente(Cliente cliente) {
		clientes.add(cliente);
	}

	public void agregarVendedor(Vendedor vendedor) {
		vendedores.add(vendedor);
	}

	public void agregarVenta(Venta venta) throws VendedorDeLicenciaException {
		// TODO: Agrega una venta a la coleccion correspondiente. En caso de que el
		// vendedor este de licencia, arroja una
		// VendedorDeLicenciaException
		if(venta.getVendedor().isDeLicencia()==true) {
			throw new VendedorDeLicenciaException();
		}
		this.ventas.add(venta);
	}

	public Producto obtenerProductoPorCodigo(Integer codigo) {
		// TODO: Obtiene un producto de los posibles por su codigo. En caso de no
		// encontrarlo se debera devolver null
		Producto productoBuscado = null;
		Iterator<Vendible> iterador = this.vendibles.iterator();
		boolean encontrado = false;
		while(!encontrado && iterador.hasNext()) {
			Vendible vendibleIterado = iterador.next();
			if(vendibleIterado instanceof Producto && vendibleIterado.getCodigo().equals(codigo)) {
				productoBuscado = (Producto) vendibleIterado;
			}
		}
		return productoBuscado;
	}

	public void agregarProductoAVenta(String codigoVenta, Producto producto) throws VendibleInexistenteException{

		// TODO: Agrega un producto a una venta. Si el vendible no existe (utilizando su
		// codigo), se debe lanzar una VendibleInexistenteException
		// Se debe actualizar el stock en la tienda del producto que se agrega a la
		// venta
		Vendible vendibleBuscado = this.obtenerProductoPorCodigo(producto.getCodigo());
		if(vendibleBuscado==null) {
			throw new VendibleInexistenteException();
		}
		Venta ventaBuscada = null;
		Iterator<Venta> iterador = this.ventas.iterator();
		boolean encontrado = false;
		while(!encontrado && iterador.hasNext()) {
			Venta ventaIterada = iterador.next();
			if(ventaIterada.getCodigo().equals(codigoVenta)) {
				ventaBuscada = ventaIterada;
				encontrado = true;
			}
		}
		ventaBuscada.agregarRenglon(vendibleBuscado, 1);
		Integer stock = this.getStock(producto);
		stock--;
		this.stock.put(producto, stock);
	}

	public void agregarServicioAVenta(String codigoVenta, Servicio servicio) throws VendibleInexistenteException {
		// TODO: Agrega un servicio a la venta. Recordar que los productos y servicios
		// se traducen en renglones
		Vendible vendibleBuscado = this.getVendible(servicio.getCodigo());
		if(vendibleBuscado==null) {
			throw new VendibleInexistenteException();
		}
		Venta ventaBuscada = null;
		Iterator<Venta> iterador = this.ventas.iterator();
		boolean encontrado = false;
		while(!encontrado && iterador.hasNext()) {
			Venta ventaIterada = iterador.next();
			if(ventaIterada.getCodigo().equals(codigoVenta)) {
				ventaBuscada = ventaIterada;
				encontrado = true;
			}
		}
		ventaBuscada.agregarRenglon(vendibleBuscado, 1);
	}

	public List<Producto> obtenerProductosCuyoStockEsMenorOIgualAlPuntoDeReposicion() {
		// TODO: Obtiene una lista de productos cuyo stock es menor o igual al punto de
		// reposicion. El punto de reposicion, es un valor que
		// definimos de manera estrategica para que nos indique cuando debemos reponer
		// stock para no quedarnos sin productos		
		ArrayList<Producto> productosStockMenor = new ArrayList<Producto>();
		Iterator<Vendible> iterador = this.vendibles.iterator();
		while(iterador.hasNext()) {
			Vendible vendibleIterado = iterador.next();
			if(vendibleIterado instanceof Producto && this.stock.get(vendibleIterado)<=((Producto) vendibleIterado).getPuntoDeReposicion()) {
				productosStockMenor.add((Producto) vendibleIterado);
			}
		}
		return productosStockMenor;
	}

	public List<Cliente> obtenerClientesOrdenadosPorRazonSocialDescendente() {
		TreeSet<Cliente> clientesOrdenados = new TreeSet<Cliente>(new Comparator<Cliente>() {

			@Override
			public int compare(Cliente cliente1, Cliente cliente2) {
				return -(cliente1.getRazonSocial().compareTo(cliente2.getRazonSocial()));
			}
			
		});
		clientesOrdenados.addAll(this.clientes);
		ArrayList<Cliente> clientesOrdenadosLista = new ArrayList<Cliente>();
		clientesOrdenadosLista.addAll(clientesOrdenados);
		return clientesOrdenadosLista;
	}

	public Map<Vendedor, Set<Venta>> obtenerVentasPorVendedor() {
		// TODO: Obtiene un mapa que contiene las ventas realizadas por cada vendedor.
		TreeMap<Vendedor, Set<Venta>> mapaVendedor = new TreeMap<Vendedor, Set<Venta>>(new Comparator<Vendedor>() {

			@Override
			public int compare(Vendedor vendedor1, Vendedor vendedor2) {
				return vendedor1.getDni().compareTo(vendedor2.getDni());
			}
			
		});
		for(Venta venta: this.ventas) {
			if(mapaVendedor.containsKey(venta.getVendedor())) {
				mapaVendedor.get(venta.getVendedor()).add(venta);
			}else {
				
				mapaVendedor.put(venta.getVendedor(), new HashSet<Venta>());
				mapaVendedor.get(venta.getVendedor()).add(venta);
			}
		}
		return mapaVendedor;
	}

	public Double obtenerTotalDeVentasDeServicios() {
		// TODO: obtiene el total acumulado de los vendibles que son servicios incluidos
		// en todas las ventas.
		// Si una venta incluye productos y servicios, solo nos interesa saber el total
		// de los servicios
		Double precioTotal = 0.0;
		for(Venta venta: this.ventas) {
			for(Vendible vendible: venta.getRenglones().keySet()) {
				if(vendible instanceof Servicio) {
					precioTotal+=(vendible.getPrecio());
				}
			}
		}
		return precioTotal;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}

package ar.edu.unlam.pb2.Parcial01;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.TreeMap;

public class TiendaTest {

	/**
	 * Resolver los siguientes tests
	 */
	
	Tienda tienda = new Tienda("01", "la tienda");

	@Test (expected = VendedorDeLicenciaException.class)
	public void queAlIntentarAgregarUnaVentaParaUnVendedorDeLicenciaSeLanceUnaVendedorDeLicenciaException() throws VendedorDeLicenciaException {
		Cliente cliente1 = new Cliente("01", "010");
		Vendedor vendedor1 = new Vendedor("43000", "juan");
		vendedor1.setDeLicencia(true);
		Venta venta1 = new Venta("01", cliente1, vendedor1);
		tienda.agregarVenta(venta1);
	}

	@Test (expected = VendibleInexistenteException.class)
	public void queAlIntentarAgregarUnVendibleInexistenteAUnaVentaSeLanceUnaVendibleInexistenteException() throws VendibleInexistenteException, VendedorDeLicenciaException{
		Cliente cliente1 = new Cliente("01", "010");
		Vendedor vendedor1 = new Vendedor("43000", "juan");
		Venta venta1 = new Venta("01", cliente1, vendedor1);
		tienda.agregarVenta(venta1);
		Producto producto1 = new Producto(11, "pelicula avengers", 100.0, 5);
		tienda.agregarProductoAVenta(venta1.getCodigo(), producto1);
	}

	@Test
	public void queSePuedaObtenerUnaListaDeProductosCuyoStockEsMenorOIgualAlPuntoDeReposicion() {
		Producto producto1 = new Producto(11, "pelicula avengers", 100.0, 0);
		Producto producto2 = new Producto(8, "videojuego mario", 250.0, 3);
		Producto producto3 = new Producto(12, "pelicula harry potter", 80.0, 2);
		Producto producto4 = new Producto(2, "album daft punk", 50.0, 4);
		tienda.agregarProducto(producto1, 2);
		tienda.agregarProducto(producto2);
		tienda.agregarProducto(producto3);
		tienda.agregarProducto(producto4);
		ArrayList<Producto> resultado = new ArrayList<Producto>();
		resultado.addAll(tienda.obtenerProductosCuyoStockEsMenorOIgualAlPuntoDeReposicion());
		assertFalse(resultado.contains(producto1));
		assertTrue(resultado.contains(producto2));
		assertTrue(resultado.contains(producto3));
		assertTrue(resultado.contains(producto4));
		
	}

	@Test
	public void queSePuedaObtenerUnSetDeClientesOrdenadosPorRazonSocialDescendente() {
		Cliente c1 = new Cliente("01", "A");
		Cliente c2 = new Cliente("02", "F");
		Cliente c3 = new Cliente("10", "C");
		Cliente c4 = new Cliente("04", "D");
		tienda.agregarCliente(c1);
		tienda.agregarCliente(c2);
		tienda.agregarCliente(c3);
		tienda.agregarCliente(c4);
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		clientes.addAll(tienda.obtenerClientesOrdenadosPorRazonSocialDescendente());
		assertEquals(c2, clientes.get(0));
		assertEquals(c1, clientes.get(3));
	}

	@Test
	public void queSePuedaObtenerUnMapaDeVentasRealizadasPorCadaVendedor() throws VendedorDeLicenciaException, VendibleInexistenteException {
		// TODO: usar como key el vendedor y Set<Venta> para las ventas
		Cliente cliente1 = new Cliente("01", "010");
		Vendedor vendedor1 = new Vendedor("43000", "juan");
		Venta venta1 = new Venta("01", cliente1, vendedor1);
		tienda.agregarVenta(venta1);
		tienda.agregarVendedor(vendedor1);
		Vendedor vendedor2 = new Vendedor("45000", "Damian");
		tienda.agregarVendedor(vendedor2);
		Servicio servicio1 = new Servicio (6, "adobe", 20.0, "2023-01-01", "2023-12-31");
		Producto producto2 = new Producto(8, "videojuego mario", 250.0, 3);
		Producto producto3 = new Producto(12, "pelicula harry potter", 80.0, 2);
		Producto producto4 = new Producto(2, "album daft punk", 50.0, 4);
		tienda.agregarServicio(servicio1);
		tienda.agregarProducto(producto2, 2);
		tienda.agregarProducto(producto3, 4);
		tienda.agregarProducto(producto4, 2);
		Venta venta2 = new Venta("03", cliente1, vendedor2);
		Venta venta3 = new Venta("05", cliente1, vendedor1);
		tienda.agregarVenta(venta3);
		tienda.agregarVenta(venta2);
		tienda.agregarProductoAVenta(venta1.getCodigo(), producto4);
		tienda.agregarProductoAVenta(venta2.getCodigo(), producto2);
		tienda.agregarServicioAVenta(venta3.getCodigo(), servicio1);
		Map<Vendedor, Set<Venta>> mapa = tienda.obtenerVentasPorVendedor();
		assertTrue(mapa.get(vendedor1).contains(venta1));
		assertTrue(mapa.get(vendedor1).contains(venta3));
		assertTrue(mapa.get(vendedor2).contains(venta2));
		assertFalse(mapa.get(vendedor2).contains(venta1));
}

	@Test
	public void queSePuedaObtenerElTotalDeVentasDeServicios() throws VendedorDeLicenciaException, VendibleInexistenteException {
		Cliente cliente1 = new Cliente("01", "010");
		Vendedor vendedor1 = new Vendedor("43000", "juan");
		Venta venta1 = new Venta("01", cliente1, vendedor1);
		tienda.agregarVenta(venta1);	
		Servicio servicio1 = new Servicio (6, "adobe", 20.0, "2023-01-01", "2023-12-31");
		Servicio servicio2 = new Servicio (7, "office", 30.0, "2023-01-01", "2023-12-31");
		tienda.agregarServicio(servicio1);
		tienda.agregarServicio(servicio2);
		Venta venta2 = new Venta("02", cliente1, vendedor1);
		tienda.agregarVenta(venta2);
		tienda.agregarServicioAVenta(venta1.getCodigo(), servicio1);
		tienda.agregarServicioAVenta(venta2.getCodigo(), servicio2);
		Double esperado = 50.0;
		assertEquals(esperado, tienda.obtenerTotalDeVentasDeServicios());
	}

	@Test
	public void queAlRealizarLaVentaDeUnProductoElStockSeActualiceCorrectamente() throws VendedorDeLicenciaException, VendibleInexistenteException {
		Cliente cliente1 = new Cliente("01", "010");
		Vendedor vendedor1 = new Vendedor("43000", "juan");
		Venta venta1 = new Venta("01", cliente1, vendedor1);
		tienda.agregarVenta(venta1);
		Producto producto1 = new Producto (11, "pelicula avengers", 100.0, 0);
		tienda.agregarProducto(producto1, 2);
		tienda.agregarProductoAVenta(venta1.getCodigo(), producto1);
		assertEquals((Integer)1, tienda.getStock(producto1));
	}
}

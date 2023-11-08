package ar.edu.unlam.pb2.Parcial01;

public abstract class Item implements Vendible{

	private Integer codigo;
	private String nombre;
	private Double precio;
	
	public Item(Integer codigo, String nombre, Double precio) {
		// TODO: Completar el constructor para el correcto funcionamiento del software
		this.setCodigo(codigo);
		this.setNombre(nombre);
		this.setPrecio(precio);
	}

	// TODO: Completar con los getters y setters necesarios
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
}

package ar.edu.unlam.pb2.Parcial01;

public class Vendedor {

	private String dni;
	private String nombre;
	private boolean deLicencia;
	
	public Vendedor (String dni, String nombre) {
		// TODO: Completar el constructor para el correcto funcionamiento del software
		// Por defecto, los vendedores no estan de licencia
		this.setDni(dni);
		this.setNombre(nombre);
		this.setDeLicencia(false);
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isDeLicencia() {
		return deLicencia;
	}

	public void setDeLicencia(boolean deLicencia) {
		this.deLicencia = deLicencia;
	}

	// TODO: Completar con los getters y setters necesarios
}

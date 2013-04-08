package es.ieci.plusvalias.api;

public class Sujeto
{
	private String identificador;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String nombreCompleto;
	private String calle;
	private String poblacion;
	private String cp;
	private String nif;
	private int edad;
	private int numDerecho;

	public Sujeto()
	{
		super();
	}

	public Sujeto(String identificador, String nombre, String calle, String poblacion, String cp, String nif, int edad)
	{
		super();
		this.nombre = nombre;
		this.calle = calle;
		this.poblacion = poblacion;
		this.cp = cp;
		this.nif = nif;
		this.edad = edad;
	}
	
	public String getIdentificador()
	{
		return identificador;
	}

	public void setIdentificador(String identificador)
	{
		this.identificador = identificador;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String getCalle()
	{
		return calle;
	}

	public void setCalle(String calle)
	{
		this.calle = calle;
	}

	public String getPoblacion()
	{
		return poblacion;
	}

	public void setPoblacion(String poblacion)
	{
		this.poblacion = poblacion;
	}

	public String getCp()
	{
		return cp;
	}

	public void setCp(String cp)
	{
		this.cp = cp;
	}

	public String getNif()
	{
		return nif;
	}

	public void setNif(String nif)
	{
		this.nif = nif;
	}

	public int getEdad()
	{
		return edad;
	}

	public void setEdad(int edad)
	{
		this.edad = edad;
	}

	public void setApellido1(String apellido1)
	{
		this.apellido1 = apellido1;
	}

	public String getApellido1()
	{
		return apellido1;
	}

	public void setApellido2(String apellido2)
	{
		this.apellido2 = apellido2;
	}

	public String getApellido2()
	{
		return apellido2;
	}

	public void setNombreCompleto(String nombreCompleto)
	{
		this.nombreCompleto = nombreCompleto;
	}

	public String getNombreCompleto()
	{
		if (nombreCompleto == null)
		{
			String nombreC = "";
			if (getApellido1() != null)
			{
				nombreC = getApellido1();
			}

			if (getApellido2() != null)
			{
				nombreC = nombreC + " " + getApellido2();
			}

			if (getNombre() != null)
			{
				nombreC = nombreC + ", " + getNombre();
			}
			nombreCompleto = nombreC;
		}
		return nombreCompleto;
	}
	
	public int getNumDerecho()
	{
		return numDerecho;
	}

	public void setNumDerecho(int numDerecho)
	{
		this.numDerecho = numDerecho;
	}
}

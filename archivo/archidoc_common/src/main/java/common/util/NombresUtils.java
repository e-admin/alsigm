package common.util;

/**
 * Utilidad para el tratamiento de nombres.
 */
public class NombresUtils
{

    /**
     * Contruye una cadena con el nombre completo.
     * @param nombre Nombre.
     * @param apellidos Apellidos.
     * @return Nombre completo: [apellidos] + ", " + [nombre]
     */
    public static String getNombreCompleto(String nombre, String apellidos)
    {
        StringBuffer nombreCompleto = new StringBuffer();
        
        if (StringUtils.isNotBlank(apellidos))
            nombreCompleto.append(apellidos);
        
        if (StringUtils.isNotBlank(nombre))
        {
            if (nombreCompleto.length() > 0)
                nombreCompleto.append(", ");
            
            nombreCompleto.append(nombre);
        }
        
        return nombreCompleto.toString();
    }


    /**
     * Contruye una cadena con el nombre completo.
     * @param nombre Nombre.
     * @param apellido1 Primer apellido.
     * @param apellido2 Segundo apellido.
     * @return Nombre completo: [apellido1] + " " + [apellido2] + ", " + [nombre]
     */
    public static String getNombreCompleto(String nombre, String apellido1,
            String apellido2)
    {
        StringBuffer nombreCompleto = new StringBuffer();
        
        if (StringUtils.isNotBlank(apellido1))
            nombreCompleto.append(apellido1);
        
        if (StringUtils.isNotBlank(apellido2))
        {
            if (nombreCompleto.length() > 0)
                nombreCompleto.append(" ");
            
            nombreCompleto.append(apellido2);
        }

        if (StringUtils.isNotBlank(nombre))
        {
            if (nombreCompleto.length() > 0)
                nombreCompleto.append(", ");
            
            nombreCompleto.append(nombre);
        }

        return nombreCompleto.toString();
    }

    
    /**
     * Contruye una cadena con los dos apellidos.
     * @param apellido1 Primer apellido.
     * @param apellido2 Segundo apellido.
     * @return Apellidos: [apellido1] + " " + [apellido2]
     */
    public static String getApellidos(String apellido1, String apellido2)
    {
        StringBuffer apellidos = new StringBuffer();
        
        if (StringUtils.isNotBlank(apellido1))
            apellidos.append(apellido1);
        
        if (StringUtils.isNotBlank(apellido2))
        {
            if (apellidos.length() > 0)
                apellidos.append(" ");
            
            apellidos.append(apellido2);
        }

        return apellidos.toString();
    }

}
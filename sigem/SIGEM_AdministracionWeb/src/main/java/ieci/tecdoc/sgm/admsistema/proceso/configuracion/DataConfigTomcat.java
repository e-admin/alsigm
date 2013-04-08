package ieci.tecdoc.sgm.admsistema.proceso.configuracion;

/*
 * esto vendria a ser un bean con los datos de una entrada en un Context xml de tomcat
 */

public class DataConfigTomcat {

    // nombare JNDI
    private String name;

    private String username;
    private String password;
    private String driverClassName;
    private String url;


    public String getName() {
	return name;
    }

    public void setName(String s) {
	name=s;
    }


    public String getUsername() {
	return username;
    }

    public void setUsername(String s) {
	username=s;
    }


    public String getPassword() {
	return password;
    }

    public void setPassword(String s) {
	password=s;
    }


    public String getDriverClassName() {
	return driverClassName;
    }

    public void setDriverClassName(String s) {
	driverClassName=s;
    }


    public String getUrl() {
	return url;
    }

    public void setUrl(String s) {
	url=s;
    }
}

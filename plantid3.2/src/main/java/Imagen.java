import java.util.TreeMap;
//en esta clase podremos obtener los atributos organ, citation y url de la respuesta de PlantNet
public class Imagen {
	
	private String organ;
	private String citation;
	//creamos un treemap para la url, que nos permitirá obtener el valor de los atributos o, m y s de url
	//es decir los links para la imagen, en tamaño original, mediano y small. el map nos ahorra tener que hacer
	//otra clase especificamente para url, y directamente nos permite obtener el valor para las claus mencionadas (o,m y s)
	private TreeMap<String, String> url;

	//creamos un constructor vacío
	public Imagen(){

	}

	//generamos los getters y setters correspondientes
	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getCitation() {
		return citation;
	}

	public void setCitation(String citation) {
		this.citation = citation;
	}

	public TreeMap<String, String> getUrl() {
	    return url;
	}

	public void setUrl(TreeMap<String, String> url) {
	    this.url = url;
	}


}


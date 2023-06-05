import java.util.List;
//en esta clase obtenemos el scientificName y los commonNames(en una lista) que se encuentran dentro de species en la respuesta de la URL
//esta clase es accesible gracias al atributo tipo Especie llamado species de la clase Resultat
public class Especie {
	private String scientificName;
	private List<String> commonNames;
	//creamos un constructor vacío de la clase
	public Especie() {
	}
	//generacion de los getters y setters para que sean accesibles desde las demas clases 
	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}
	public List<String> getCommonNames() {
		return commonNames;
	}

	public void setCommonNames(List<String> commonNames) {
		this.commonNames = commonNames;
	}

}
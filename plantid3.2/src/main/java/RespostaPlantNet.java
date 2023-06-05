
import java.util.List;
//creamos clase Resposta PlantNet con los atributos simples que queremos extraer de la cadena JSON de la respuesta de PlantNet
//y además creamos un List con el atributo results, que es el complejo de la respuesta, que contiene a su vez arrays de objetos imbrincados
public class RespostaPlantNet {
	private String bestMatch;
	private String language;
	private String preferedReferential;
	private String version;
	private String remainingIdentificationRequests;
	//el list results (nombre literal de la respuesta de PlantNet) es de tipo Resultat, clase a la que a través de este List
	//podremos acceder para obtener sus atributos que se encuentran dentro de results
	private List<Resultat> results; 

	//creamos un constructor vacío de la clase RespostaPlantNet
	public RespostaPlantNet() {
	}

	//creamos los getters y settes correspondientes a cada atributo y lista para que sean accesibles desde otras clases
	public String getBestMatch() {
		return bestMatch;
	}
	public void setBestMatch(String bestMatch) {
		this.bestMatch = bestMatch;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPreferedReferential() {
		return preferedReferential;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getRemainingIdentificationRequests() {
		return remainingIdentificationRequests;
	}
	public void setRemainingIdentificationRequests(String remainingIdentificationRequests) {
		this.remainingIdentificationRequests = remainingIdentificationRequests;
	}
	public void setPreferedReferential(String preferedReferential) {
		this.preferedReferential = preferedReferential;
	}
	public List<Resultat> getResults() {
		return results;
	}
	public void setResults(List<Resultat> results) {
		this.results = results;
	}



}


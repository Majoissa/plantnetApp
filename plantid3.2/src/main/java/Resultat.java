import java.util.List;

//Esta clase es accesible gracias a que enla clase RespostaPlantNet se creó un atributo results que es de tipo Resultat
//creamos los atributos implicados en la Respuesta dada por la URL PlantNet, que tienen el mismo nombre literalmente: scorre, species e images
//repetando el tipo de cada uno. A su vez, species contiene otros atributos que queremos obtener, por tal motivo hemos creado otra clase
//llamada Especie a la cual accedemos desde el atributo de tipo Especie llamado species
public class Resultat {
	private Double score;
	private Especie species;
	//A su vez, desde la lista de tipo Imagen (clase previamente creada) llamada images, podremos acceder a ver los atributos de las imagenes
	//de la respuesta de la URL, que los que queremos desserializar son organ, citation y url (o, m, s)
	private List<Imagen> images;
	
	//creamos un constructor vacío de la clase Resultat
	public Resultat() {
	}
	
	//creacion de getters y setters de los atributos y de la lista images, para que sean accesibles desde otras clases 
	public Double getScore() {
		return score;
	}

	public List<Imagen> getImages() {
		return images;
	}

	public void setImages(List<Imagen> images) {
		this.images = images;
	}


	public void setScore(Double score) {
		this.score = score;
	}

	public Especie getSpecies() {
		return species;
	}

	public void setSpecies(Especie species) {
		this.species = species;
	}
	
}

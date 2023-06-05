import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonParser;


public class Test {
	private static final String PROJECT = "all"; 
	private static final String URL =  "https://my-api.plantnet.org/v2/identify/" + PROJECT + "?lang=es&include-related-images=true&api-key=2b10Q7GeqtUKp1PgTFJWdwzPb";
	private static PreparedStatement consulta;


	public static void main(String[] args) {

		//Creamos un objeto JFileChooser para que el usuario seleccione archivos
		JFileChooser selectorDeArchivos = new JFileChooser("imagenes");
		selectorDeArchivos.setMultiSelectionEnabled(true);// metodo de JFileChooser que siendo true permite seleccionar multiples archivos

		// con el metodo showOpenDialog mostramos el dialogo para seleccionar archivos
		int resultado = selectorDeArchivos.showOpenDialog(null);

		//establecemos coneccion con la base de datos de postgre sql
		try {
			// Nos conectamos a una base de dades postgres sql
			Connection conn = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "150396+Majo");
			// Preparar la declaración SQL para la inserción de registros
			String sql = "INSERT INTO consultas (fecha_hora, especies) VALUES (?, ?)";
			consulta = conn.prepareStatement(sql);
		}catch (SQLException sqle) {
			System.out.println("Error al establecer la coneccion: " + sqle.getMessage());
			JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos", "Informatica Aromatica", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		//Si showOpenDialog devuelve APROVE_OPTION se crea un array de tipo File llamado files que almacena los archivos seleccionados
		if (resultado == JFileChooser.APPROVE_OPTION) {
			File[] files = selectorDeArchivos.getSelectedFiles();

			// Verificar si el directorio principal existe (sino, procedemos a su creacion)
			Path directorioPrincipal = Paths.get("id_card");
			if (!Files.exists(directorioPrincipal)) {
				try {
					// Crear el directorio principal
					Files.createDirectory(directorioPrincipal);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error al crear el directorio", "Informatica Aromatica", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}

			// Verificar si el directorio "imágenes" dentro del directorio principal existe
			Path directorioImagenes = Paths.get("id_card", "imagenes");
			if (!Files.exists(directorioImagenes)) {
				try {
					// Crear el directorio "imágenes"
					Files.createDirectory(directorioImagenes);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error al crear el directorio", "Informatica Aromatica", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}


			//creacion de objeto tipo MultipartEntityBuilder con el metodo .create()
			//esta clase se usa para crear entidades http para enviar contenido en una solicitud http
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();

			// Bucle que se repite una vez por cada archivo seleccionado por el usuario.
			for (File f : files) {
				//creamos objeto tipo Image, escalamos la imagen, creamos un JLabel que la contiene, usamos metodos de clase File para 
				//obtener nombre y tamalo de los archivos, y creamos un array que proporciona opciones en forma de lista desplegable
				ImageIcon imagen = new ImageIcon(f.getAbsolutePath());
				imagen.setImage(imagen.getImage().getScaledInstance(600, -1, Image.SCALE_SMOOTH));

				JLabel label = new JLabel(imagen); //creamos objeto JLabel que contiene a la imagen
				String nombreArchivo = f.getName(); 
				long tamanoArchivo = f.length();            
				double tamanoEnMB = tamanoArchivo / (1024.0 * 1024.0);
				String[] opciones = new String[] {"Leaf", "Flower", "Fruit", "Bark"};

				//Se usa un JOptionPane para mostrar un cuadro de dialogo con la imagen y se pide al usuario que seleccione el organo de la planta que mas se vea
				//Almacena el valor seleccionado en la variable respuesta. Se usa tambien JOptionPane.PLAIN_MESSAGE para mostrar el cuadro simple y sin iconos
				String respuesta = (String) JOptionPane.showInputDialog(null, label, String.format("%s - %s Bytes - %.1f MBytes", nombreArchivo, tamanoArchivo, tamanoEnMB),
						JOptionPane.PLAIN_MESSAGE, null, opciones, "Leaf");

				// Si el usuario ha confirmado la respuesta (ha pulsado el botón OK), pasamos el valor a minúsculas (la API lo requiere).
				if (respuesta != null) {
					respuesta = respuesta.toLowerCase();
					// Si el usuario finaliza pulsando Cancelar, pasaremos el valor "auto" para que el servidor lo intente detectar.
				} else {
					respuesta = "auto";
				}
				// Agregar la imagen y la respuesta al contenido de la petición y con el build se finaliza la construccion del objeto HttpEntity
				builder.addPart("images", new FileBody(f))//filebody se usa para representar un archivo en una solicitud HTTP
				.addTextBody("organs", respuesta)
				.build();
			}

			//Llamamos al metodo build del objeto builder para construir la entidad http completa
			HttpEntity entity= builder.build(); 


			//Se crea una solicitud HTTP POST (envia la solicitud) con la URL del servicio (enviamos los datos al servidor)
			HttpPost solicitud = new HttpPost(URL);
			//Establecer el contenido de la peticion en la solicitud HTTP POST
			solicitud.setEntity(entity);
			//Se crea un objeto httpclient configurado listo para enviar la solicitud
			HttpClient cliente = HttpClientBuilder.create().build();
			//Enviar la solicitud y obtener la respuesta
			HttpResponse response;

			//dentro del try se ejecuta la solicitud para obtener la respuesta
			try {
				response = cliente.execute(solicitud);
				//Se obtiene el contenido de la respuesta como una cadena de texto
				String jsonString = EntityUtils.toString(response.getEntity());
				//Creamos un objeto Gson configurado para generar una representacion JSON en modo "pretty printing"
				//Gson gson = new GsonBuilder().setPrettyPrinting().create();
				//Mostramos la conversion a String del JSON que nos ha retornado el servidor, en modo pretty printing
				//System.out.println(gson.toJson(JsonParser.parseString(jsonString)));

				// Hacemos la desserializacion de la respuesta obtenida en el servidor
				// (conversion de JSON a objeto de tipo respuesta de JAVA).
				RespostaPlantNet r = new Gson().fromJson(jsonString, RespostaPlantNet.class);
				// Mostramos el valor de los atributos per consola.
				System.out.print("Best Match de la respuesta: ");
				System.out.println(r.getBestMatch());
				System.out.print("\nLanguage de la respuesta: ");
				System.out.println(r.getLanguage());
				System.out.print("\nPrefered Referencial de la respuesta :");
				System.out.println(r.getPreferedReferential());
				System.out.print("\nVersion :");
				System.out.println(r.getVersion());
				System.out.print("\nRemaining Identification Requests :");
				System.out.println(r.getRemainingIdentificationRequests());



				String rutaArchivo = "id_card/results.html"; // Ruta y nombre del archivo results.html
				//creamos un objeto printWriter, y ademas leemos el contenido de los archivos HTML de templates, para luego escribirlos en results.html
				try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) {
					String headerContent = Files.readString(Paths.get("templates/header.html"));
					String resultTemplateContent = Files.readString(Paths.get("templates/result_template.html"));
					String footerContent = Files.readString(Paths.get("templates/footer.html"));
					String imageContent = Files.readString(Paths.get("templates/image_template.html"));
					writer.println(headerContent); // Escribir el contenido de header.html en results.html


					//mostramos el sccore en tanto porciento y el nombre cientifico de todos los resultados que contiene la respuesta que hemos recibido
					//iteramos sobre cada objeto Resultat en la lista r.getResults().
					//Resultat es la clase que contiene la información principal que queremos imprimir.
					int numResultado = 1;
					for (Resultat res : r.getResults()) {
						System.out.printf("\n- INFORMACION -"
								+"\n-----------------------"
								+ "\nNombre cientifico: %s"
								+ "\nNombre común: %s"
								+ "\nPorcentaje de probabilidad: (%.2f%%)\n",
								res.getSpecies().getScientificName(),
								res.getSpecies().getCommonNames(),
								res.getScore()*100
								);

						//seleccionamos las imagenes que tienen mas de 5% de probabilidad
						if (res.getScore() >= 0.05) {
							//convertimos la lista commonNames a string, separando los elementos con punto y comas
							String commonNamesString = String.join("; ", res.getSpecies().getCommonNames());
							double score = res.getScore();
							int numImagen = 1;
							String imagenesHTML = " ";
							for (Imagen imagen : res.getImages()) {
								if (score >= 0.05) {
									System.out.println("->Organ: " + imagen.getOrgan());
									System.out.println("->Citation: " + imagen.getCitation());
									// Obtener las URLs de la imagen
									TreeMap<String, String> url = imagen.getUrl();
									String mValue = url.get("m");

									try {
										// Crear un objeto URL para la imagen de tamaño medio
										URL urlImagen = new URL(mValue);
										// Obtener el InputStream asociado al objeto URL
										InputStream inputStream = urlImagen.openStream();
										// Crear un objeto Path para el directorio de destino y el nombre de archivo
										String nombreArchivo = String.format("%03d.%03d.jpg", numResultado, numImagen);
										Path destino = Paths.get("id_card", "imagenes", nombreArchivo);
										// Copiar el contenido del InputStream al archivo de destino
										Files.copy(inputStream, destino, StandardCopyOption.REPLACE_EXISTING);
										// Cerrar el InputStream
										inputStream.close();
										System.out.println("Imagen descargada: " + destino.toString());
									} catch (IOException e) {
										e.printStackTrace();
										JOptionPane.showMessageDialog(null, "Error al descargar la imagen", "Informatica Aromatica", JOptionPane.ERROR_MESSAGE);
									}
								}

								// Reemplazar las marcas en image_template.html con los valores correspondientes
								String imageTemplateContent = imageContent
										.replace("%%organ%%", imagen.getOrgan())
										.replace("%%image%%", String.format("%03d.%03d.jpg", numResultado, numImagen))
										.replace("%%citation%%", imagen.getCitation());
								
								imagenesHTML += imageTemplateContent;  
								numImagen++;
							}
							// Reemplazar las marcas en result_template.html con los valores correspondientes
							String resultContent = resultTemplateContent
									.replace("%%scientificName%%", res.getSpecies().getScientificName())
									.replace("%%commonNames%%", commonNamesString)
									.replace("%%score%%", String.format("%.2f%%", res.getScore() * 100))
									.replace("%%images%%", imagenesHTML);
							
							// Escribir el contenido de result_template.html en results.html
							writer.println(resultContent);
							numResultado++;
						}

					}
					writer.println(footerContent); // Escribir el contenido de footer.html en results.html
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al escribir en el archivo", "Informatica Aromatica", JOptionPane.ERROR_MESSAGE);
				}

				System.out.println("\nEl archivo results.html se ha creado correctamente.");

				//obtenemos los datos para insertar en la base de datos de pgsql
				//creamos un atributo booleano que luego servirá para verificar si la especie con mejor score ya se inserto, para no intentar 
				//insertar mas resultados en la base de datos
				boolean primerResultadoInsertado = false;
				//iteramos sobre cada resultado en busca del mejor score de la respuesta
				for (Resultat res : r.getResults()) {
					try {
						// Obtener el nombre científico de la especie con el resultado más alto
						String especieResultadoMasAlto = " ";
						String mejorScore = r.getBestMatch();

						// Verificar si es el primer resultado y aún no se ha insertado
						if (!primerResultadoInsertado) {
							if (mejorScore.contains(res.getSpecies().getScientificName())) {
								especieResultadoMasAlto = mejorScore;
								//obtenemos la fecha y la hora en que se obtuvo el resultado con best score con timestamp
								Timestamp fechaHoraActual = new Timestamp(System.currentTimeMillis());

								// Establecer los valores en la sentencia SQL preparada
								consulta.setTimestamp(1, fechaHoraActual);
								consulta.setString(2, especieResultadoMasAlto);

								// Ejecutar la sentencia SQL para insertar el registro en la base de datos
								consulta.executeUpdate();

								// Marcar el primer resultado como insertado
								primerResultadoInsertado = true;

								//verificamos si la consulta no devuelve resultado, si es null 
							} else if (res.getSpecies().getScientificName() == null) {
								especieResultadoMasAlto = "Identificacion fallida";
								//obtenemos fecha y hora del resultado obtenido
								Timestamp fechaHoraActual = new Timestamp(System.currentTimeMillis());

								// Establecer los valores en la sentencia SQL preparada
								consulta.setTimestamp(1, fechaHoraActual);
								consulta.setString(2, especieResultadoMasAlto);

								// Ejecutar la sentencia SQL para insertar el registro en la base de datos
								consulta.executeUpdate();

								// Marcar el primer resultado como insertado
								primerResultadoInsertado = true;
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error al insertar el registro en la base de datos", "Informatica Aromatica", JOptionPane.ERROR_MESSAGE);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en la conexión con el servidor", "Informatica Aromatica", JOptionPane.ERROR_MESSAGE);
			}

		}

	}
}
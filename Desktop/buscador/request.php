<?php
    session_start();
    
     $enlace = mysqli_connect("127.0.0.1", "JoanLS", "Mass_Effect3", "cercador");
					if (!$enlace){ 
						echo "Error: No se pudo conectar a MySQL." . PHP_EOL;
						echo "errno de depuración: " . mysqli_connect_errno() . PHP_EOL;
						echo "error de depuración: " . mysqli_connect_error() . PHP_EOL;
						exit;
					}
				
				$x = 1;
				
			
				
				mkdir('respostes/'.session_id());
			
				
		$sql=mysqli_query($enlace, "SELECT * FROM aerolinies");  
					
					while ($row=mysqli_fetch_array($sql)) {
						
						$url = "http://".$row[1]."/vullvols.php?data_s=".$_GET['data_s']."&aeroport_s=".$_GET['aeroport_s']."&aeroport_a=".$_GET['aeroport_a'];			
						$xml=file_get_contents($url);
						
										
					    file_put_contents('respostes/'.session_id().'/'.$x.'.xml', $xml); 
						
						$x++;
																		
					}
		
			$dir = 'respostes/'.session_id(); //aqui tiene que ir el directorio 'respostes/'.session_id()
			$files = scandir($dir);
	
					$combined_xml = '<?xml version="1.0" encoding="UTF-8"?>' . "\n" .
					'<?xml-stylesheet href="ticket.xsl"	type="text/xsl"?>' . "\n" .
					'<VOLS>';
									
					foreach ($files as $file){
						
						$xml = file_get_contents("$dir/$file");
						$xml = str_replace("<VOLS>", "", $xml);
						$xml = str_replace("</VOLS>", "", $xml);
						$combined_xml .= $xml;
						
					}
					
					$combined_xml .= '</VOLS>' . "\n";

					// Escribir el contenido del archivo combinado en un nuevo archivo
					$file_path = 'respostes/'.session_id().'/Vols.xml';
					file_put_contents($file_path, $combined_xml);
					
					$xsl = file_get_contents('ticket.xsl');
					$css = file_get_contents('Style.css');					//se copian los ficheros ticket.xsl y Style.css a la carpeta de cada sesion
					$xsl_path = 'respostes/'.session_id().'/ticket.xsl';
					$css_path = 'respostes/'.session_id().'/Style.css';
					$xsl_session = $xsl;
					$css_session = $css;
					file_put_contents($xsl_path, $xsl_session);
					file_put_contents($css_path, $css_session);
					
					
					header('Location: respostes/'.session_id().'/Vols.xml');
?>
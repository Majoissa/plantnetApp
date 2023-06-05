<!DOCTYPE html>
<html lang="en">
<head>
  <title>Skyline Airways</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
  <style>
  .contenedor-navegacion{
    border-top: 1px solid #e1e1e1;


}


.nav-principal{
    padding: 10px 10px 10px 10px ;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}


h1 span{
    color: #00307E;
}
h2{
	color: #00307E;
	font-family: 'Playfair-display', serif;
}

h3{
    font-family: 'Playfair-display', serif;
}


a{
    text-decoration: none;
    color: #00307E;
}



.nombre-sitio{
    text-align: center;
	margin-top: -60px;
	color: #FFEA52;
}

    .carousel-item {
      position: relative;
      height: 440px; 
    }
    .carousel-item img {
      height: 100%;
      width: 100%;
      object-fit: cover;
    }
    .carousel-item .overlay {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.5);
      display: flex;
      justify-content: center;
      align-items: center;
    }
	
	.carousel-control-prev, .carousel-control-next {
      height: 50px;
      width: 50px;
      top: 50%;
      transform: translateY(-50%);
    }
    
	#demo {
      position: relative;
    }
    .form-card {
      position: relative;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      padding: 20px;
      background-color: white;
      max-width: 1200px;
	  border-radius: 10px;
      z-index: 1;
	  margin-top: -12%;
    }
	.btn{
	background-color: #FFEA52;
	text-transform: uppercase;
    font-weight: 900;
    display: block;
    color: #ffffff;
    margin: 1rem;
    transition: background-color .3s ease-out;
    text-align: center;
    border:none;
	}
	.btn:hover{
    background-color: #D60000;
	color: #ffffff;
    cursor:pointer;
}

.logo{
	width: 150px;
}


.categorias-de-productos{
    padding-top: 4rem;
    padding-bottom: 2rem;
}


.categorias{
    display: grid;
    grid-template-columns:repeat(3, 1fr);
    gap: 20px;
	text-align:center;
}


.categorias-de-productos{
    text-align: center;
}


.categorias a{
	background-color: #FFEA52;
	color: #ffffff;
    display: block;
    text-align: center;
	max-width: 475px;
    padding: 15px;
    font-size: 25px;
	border-bottom-right-radius: 10px;
	text-align:center;
	margin-left: 12%;
	
}


.categorias a:hover{
    border-bottom-right-radius: 10px;
	background-color: #D60000;
	color: #ffffff;
    cursor:pointer;
	text-align:center;
	margin-left:12%;
	
}

.caja-categoria{
	margin-bottom: 3rem;
	}
.categotias img: hover{
	
}

  </style>
</head>
<body>
<img class="logo" src="imagenes/LOGO.png" alt="logo">
<header>
    <h1 class="nombre-sitio">Aerolinea<span> Skyline Airways</span></h1> 
    </header>
    
    <div class="contenedor-navegacion">
        <nav  class="nav-principal contenedor">
    <a href="flightfounder.php">Inicio</a>
    <a href="">Viaja</a>
    <a href="">Tus vuelos</a>
    <a href="">Antes de viajar</a>
    <a href="">Experiencia Skyline</a>
    <a href="">Contacto</a>  
    </nav>
    </div>
  <!-- Carousel -->
  <div id="demo" class="carousel slide" data-bs-ride="carousel">
    <!-- Indicators/dots -->
    <div class="carousel-indicators">
      <button type="button" data-bs-target="#demo" data-bs-slide-to="0" class="active"></button>
      <button type="button" data-bs-target="#demo" data-bs-slide-to="1"></button>
      <button type="button" data-bs-target="#demo" data-bs-slide-to="2"></button>
    </div>
    <!-- The slideshow/carousel -->
    <div class="carousel-inner">
      <div class="carousel-item active">
        <img src="imagenes/londres.jpg" alt="Maldivas">
      </div>
      <div class="carousel-item">
        <img src="imagenes/cancun.jpg" alt="Legzira">
      </div>
      <div class="carousel-item">
        <img src="imagenes/NewYork.jpg" alt="NewYork">
      </div>
    </div>
  </div>

  <!-- Formulario -->
  <div class="form-card">
    <form action="request.php" method="GET">
      <div class="row">
        <div class="col">
          <div class="mb-3">
            <label for="date-picker" class="form-label">Fecha de ida</label>
            <input type="date" class="form-control" id="data_s" name="data_s">
          </div>
        </div>
        <div class="col">
          <div class="mb-3">
            <label for="date-picker2" class="form-label">Fecha de vuelta</label>
            <input type="date" class="form-control" id="data_t" name="data_t">
          </div> 
        </div>
		<div class="col">
          <div class="mb-3">
           <label for="sel1" class="form-label">Origen:</label>
              <select class="form-select" id="aeroport_s" name="aeroport_s">
			  <?php
			   session_start();
			   
    $enlace = mysqli_connect("127.0.0.1", "JoanLS", "Mass_Effect3", "cercador");
    if (!$enlace) {
        echo "Error: No se pudo conectar a MySQL." . PHP_EOL;
        echo "errno de depuración: " . mysqli_connect_errno() . PHP_EOL;
        echo "error de depuración: " . mysqli_connect_error() . PHP_EOL;
        exit;
    }

                                            $sql="SELECT * FROM aeroport ORDER BY name";
                                            $resultado = mysqli_query($enlace, $sql);


                                            while ($row = mysqli_fetch_array($resultado)){

                                            echo "<option value=".$row[1].">".$row[4].", ".$row[5]." (".$row[2].")"."</option>";


                                            }

                    
                    ?>
            </select>
    </div>
        </div> 
		<div class="col">
          <div class="mb-3">
           <label for="sel1" class="form-label">Llegada:</label>
              <select class="form-select" id="aeroport_a" name="aeroport_a">
			  <?php
	

                                            $sql="SELECT * FROM aeroport ORDER BY name";
                                            $resultado = mysqli_query($enlace, $sql);


                                            while ($row = mysqli_fetch_array($resultado)){

                                            echo "<option value=".$row[1].">".$row[4].", ".$row[5]." (".$row[2].")"."</option>";


                                            }

                    mysqli_close($enlace);
                    ?>
            </select>
    </div>
        </div> 
        </div>
		<button type="submit" class="btn">Enviar</button>
      </div>
    </form>
    
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#demo" data-bs-slide="prev">
    <span class="carousel-control-prev-icon"></span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#demo" data-bs-slide="next">
    <span class="carousel-control-next-icon"></span>
    </button>
	<!-- Categoria promociones-->
	<section class="contenedor caja-categoria" >
    <h2 class="categorias-de-productos">PROMOCIONES</h2>

    <div class="categorias">
<div>
    <img src="Imagenes/patagonia.jpg" width="475px" alt="Imagen Categoria">
    <a href="Oficina.html">Excursiones</a>
</div>

<div>
    <img src="Imagenes/egipto.jpg" width="475px" alt="Imagen Categoria">
    <a href="Hogar.html">Destinos exoticos</a>
</div>

<div>
    <img src="Imagenes/india.jpg" width="475px" alt="Imagen Categoria">
    <a href="Cocina.html">Maravillas del mundo</a>
</div>
    </div>
    </section>
 
</body>

  </html>

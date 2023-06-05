<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
  <head>
        <title>Skiline Airways</title>
		<link href="Style.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>

  <xsl:for-each select="VOLS/VOL">
  <div class="ticket">
    <div class="box">
  <ul class="left">
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
  </ul>
  
  <ul class="right">
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
  </ul>
  </div>
    <span class="airline">Skyline Airways <img class="logo" src="imagenes/LOGOO.png" alt="logo"/> </span>
    <span class="airline airlineslip">Vol: <xsl:value-of select="idvol"/></span>
    <!--<span class="boarding">Boarding pass</span>-->
    <div class="content">
      <span class="jfk"><xsl:value-of select="AEROPORT_S"/></span>
      <span class="plane"><svg clip-rule="evenodd" fill-rule="evenodd" height="60" width="60" image-rendering="optimizeQuality" shape-rendering="geometricPrecision" text-rendering="geometricPrecision" viewBox="0 0 500 500" xmlns="http://www.w3.org/2000/svg"><g stroke="#222"><line fill="none" stroke-linecap="round" stroke-width="30" x1="300" x2="55" y1="390" y2="390"/><path d="M98 325c-9 10 10 16 25 6l311-156c24-17 35-25 42-50 2-15-46-11-78-7-15 1-34 10-42 16l-56 35 1-1-169-31c-14-3-24-5-37-1-10 5-18 10-27 18l122 72c4 3 5 7 1 9l-44 27-75-15c-10-2-18-4-28 0-8 4-14 9-20 15l74 63z" fill="#222" stroke-linejoin="round" stroke-width="10"/></g></svg></span>
      <span class="sfo"><xsl:value-of select="AEROPORT_A"/></span>
      
      <span class="jfk jfkslip"><xsl:value-of select="AEROPORT_S"/></span>
      <span class="plane planeslip"><svg clip-rule="evenodd" fill-rule="evenodd" height="50" width="50" image-rendering="optimizeQuality" shape-rendering="geometricPrecision" text-rendering="geometricPrecision" viewBox="0 0 500 500" xmlns="http://www.w3.org/2000/svg"><g stroke="#222"><line fill="none" stroke-linecap="round" stroke-width="30" x1="300" x2="55" y1="390" y2="390"/><path d="M98 325c-9 10 10 16 25 6l311-156c24-17 35-25 42-50 2-15-46-11-78-7-15 1-34 10-42 16l-56 35 1-1-169-31c-14-3-24-5-37-1-10 5-18 10-27 18l122 72c4 3 5 7 1 9l-44 27-75-15c-10-2-18-4-28 0-8 4-14 9-20 15l74 63z" fill="#222" stroke-linejoin="round" stroke-width="10"/></g></svg></span>
      <span class="sfo sfoslip"><xsl:value-of select="AEROPORT_A"/></span>
      <div class="sub-content">
        <span class="watermark">Skyline</span>
		 <span class="name">SORTIDA: <span><xsl:value-of select="concat(substring(DATA_S, 3), '&#8594;')"/><xsl:value-of select="substring(DATA_A, 3)"/></span></span>
		 
        <span class="gate">PLAZAS E.<span><xsl:value-of select="PLACES_A"/></span></span>
        <span class="seat">PLAZAS B.<span><xsl:value-of select="PLACES_B"/></span></span>
        <span class="boardingtime">ARRIBADA: <span><xsl:value-of select="concat(HORA_S, '-Dia: ')"/> <xsl:value-of select="substring(DATA_A, 3)"/></span></span>
        <span class="boardingtime-a">DURADA: <span><xsl:value-of select="DURACIO"/></span></span>
         <span class="flight flightslip">PREU E: <span><xsl:value-of select="PREU_E"/></span></span>
         <span class="name nameslip">PREU B: <span><xsl:value-of select="PREU_B"/></span></span>
      </div>
    </div>
    <div class="barcode"></div>
    <div class="barcode slip"></div>
  </div>
  <br/>
  </xsl:for-each>


</body>
</html>
</xsl:template>
</xsl:stylesheet>

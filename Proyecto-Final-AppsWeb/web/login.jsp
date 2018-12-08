<%-- 
    Document   : login
    Created on : 6/12/2018, 03:35:15 AM
    Author     : Eduardo Ramírez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
 Cookie loginCookie = null;
    	Cookie[] cookies = request.getCookies();
    	if(cookies != null){
    	for(Cookie cookie : cookies){
    		if(cookie.getName().equals("usuario")){
    			response.sendRedirect("index.jsp");
    			break;
    		}
    	}
    	}
%>
<html>

<head>

  <meta charset="UTF-8">

  <title>Iniciar sesión</title>

        <link href="css/Estilos.css" rel="stylesheet" type="text/css"/>

</head>

<body>
<div class="contenido">
    
  <div class="form-style-8">
    <h2>Iniciar Sesión</h2><br>
    <form method="POST" action="controlPrincipal">
    <input type="text" name="usuario" placeholder="Usuario" required>
    <input type="password" name="contra" placeholder="Contraseña" required>
    <input type="submit" name="tarea" class="login login-submit" value="LOGIN">
     <p>(La se sesión expirará en 30 minutos)</p>
  </form>

  </div>
</div>

<!-- <div id="error"><img src="https://dl.dropboxusercontent.com/u/23299152/Delete-icon.png" /> Your caps-lock is on.</div> -->

  <script src='http://codepen.io/assets/libs/fullpage/jquery_and_jqueryui.js'></script>

</body>

</html>
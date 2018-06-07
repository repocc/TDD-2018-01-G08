<?php
require_once("libs/funciones.php");
define("SUCCESSFULL_CODE", 200);

$message = "";
session_start();
if($_POST){
	$endpointRoot = "http://localhost:9000/tickets/users/";
	$endpoint = (isset($_POST["btn_registrar"]))? "create" : "login";
	$fullEndPoint = $endpointRoot.$endpoint;	
	$data = array( 
			"userName" => $_POST["userName"],
		        "password" => $_POST["password"]
		);

	$result = curlPost($fullEndPoint, $data);
	
	if ($result["code"] == SUCCESSFULL_CODE){
		session_start();
		$_SESSION["userName"] = $_POST["userName"];
		header('Location:main.php');	
	}
	
	$message = $result["message"];

}else if (isset($_GET["logout"])){
	 session_destroy();
}
?>
<!DOCTYPE html>
<html lang="es">
  <head>
    <link rel="icon" href="../../favicon.ico">
    <title>Sistema de Tickets</title>
    <link href="libs/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/signin.css" rel="stylesheet">
    <script src="js/jquery-3.1.1.min.js"></script>
    <script>
	var message = "<?=$message;?>";
	$( document ).ready(function() {
	    if (message)alert( message );
	});
    </script>  	
  </head>
  <body>
    <div class="container">
      <form class="form-signin" method="POST">
        <h2 class="form-signin-heading">Sistema de Tickets</h2>
        <label class="sr-only">Usuario</label>
        <input type="text" name="userName" id="userName" class="form-control" placeholder="Usuario" required autofocus><br>
        <label class="sr-only">Contraseña</label>
        <input type="password" name="password" id="password" class="form-control" placeholder="Contraseña" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit" name="btn_ingresar">Ingresar</button>
	<button class="btn btn-lg btn-primary btn-block" type="submit" name="btn_registrar" id="btn_registrar">Registrarse</button>
      </form>
    </div>
  </body>
</html>


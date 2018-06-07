<?php
require_once("libs/funciones.php");
session_start();
$message = "";

if($_POST){
	$endpoint = "http://localhost:9000/dashboards/create";
	$data = array( 
			"id"  => "",
			"name"  => $_POST["dashboardName"],
			"authUserName"  => $_POST["authUserName"],
			"createdDate"  => getFullDate(),
			"enable"  => $_POST["enable"]
		);

	$result = curlPost($endpoint, $data);
	
	$dashboardId = $result["id"];
	if ($dashboardId >= 0){
		header('Location:create_dashboard_step1.php?id='.$dashboardId);	
	}
	
	$message = $result["message"];
}
?>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="utf-8">
    <link rel="icon" href="../../favicon.ico">
    <title>SM - Menu Principal</title>
    <link href="libs/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/navbar.css" rel="stylesheet">
    <script src="js/jquery-3.1.1.min.js"></script>
     <script>
	var message = "";
	$( document ).ready(function() {
	    if (message)alert( message );
	});
    </script>
  </head>
  <body>

    <div class="container">
      <nav class="navbar navbar-default">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"></a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
              <li><a href="main.php">Home</a></li>
              <li><a href="dashboards.php">Mis Dashboards</a></li>
              <li class="active"><a href="create_dashboard.php">Crear Dashboard</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
              <li><a href="index.php?logout=1">Cerrar Sesión</a></li>
            </ul>
          </div>
        </div>
      </nav>
	<form method="POST">
	      <div style="min-height:350px;" class="jumbotron">
		<h3>Datos Generales</h3><br>
		<p>
			<span>Nombre:</span> 
			<input type="text" name="dashboardName">
		</p>
		<p>
			<span>Creador:</span> 
			<input type="text" value="<?=$_SESSION["userName"]?>" name="authUserName" readonly>
		</p>
		<p>
			<span>Habilitado:</span> 
			<select name="enable">
				<option value="true">SI</option>
				<option value="false">NO</option>
			</select>
		</p>
		<br>        
		<p>
		  <input name="submit_dashboard0" type="submit" class="btn btn-lg btn-primary" role="button" value="Siguiente">
		</p>
	      </div>
	</form>
    </div>
  </body>
</html>


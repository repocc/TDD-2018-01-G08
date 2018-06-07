<?php
require_once("libs/funciones.php");
session_start();
$message = "";

if($_POST){
	$dataRcv = $_POST;
	$dashboardId = $_GET["id"];	

	$numCounts = floor(count($dataRcv)/2);
	$endpoint = "http://localhost:9000/dashboards/$dashboardId/addRule";
	for($i=0; $i < $numCounts; $i++){
		$data = array(	
				"authUserName" => $_SESSION["userName"],
				"name" => $dataRcv["counterFieldName".$i],
				"query" => $dataRcv["counterFieldQuery".$i],
				"enable" => "true"
			);
		curlPost($endpoint, $data);
	}
	
	header('Location:dashboards.php');	
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
    <script src="js/create_dashboard_step3.js"></script>
    	
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
		<h3>Reglas</h3><br>
		<p>
			<h4>Contadores</h4> 
			<button type="button" class="btn btn-group-sm btn-success" id = "addCounterFieldsButton">Agregar</button>
			<button type="button" class="btn btn-group-sm btn-warning" id = "removeCounterFieldsButton">Quitar</button>			
			<div id="counterFields">
				<br>
				<div id="counterFields0">			
					<span>Nombre:</span><input type="text" name="counterFieldName0">&nbsp;
					<span>Consulta:</span><input type="text" name="counterFieldQuery0" style='width:500px;' value="(define-counter <counter-name> <args> <expr>)">
				</div>
			</div>
		</p>        
		<p>
		  <input name="submit_dashboard3" type="submit" class="btn btn-lg btn-primary" role="button" value="Finalizar">
		</p>
	      </div>
	</form>
    </div>
  </body>
</html>


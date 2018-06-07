<?php
require_once("libs/funciones.php");
session_start();
$message = "";
$projectId = $_GET["id"];	
$projectName = $_GET["name"];

$endpoint = "http://localhost:9000/tickets/projects/$projectId/tickets/all";
$takeDisplay = false;
$finishDisplay = false;

if ($_GET["myTickets"] == "true"){
	$endpoint = "http://localhost:9000/tickets/projects/$projectId/ticketsCreated/".$_SESSION["userName"];
}elseif ($_GET["myTicketsTaken"] == "true"){
	$endpoint = "http://localhost:9000/tickets/projects/$projectId/ticketsTaken/".$_SESSION["userName"];
	$finishDisplay = true;
}elseif ($_GET["ticketsAvailable"] == "true"){
	$takeDisplay = true;
	$endpoint = "http://localhost:9000/tickets/projects/$projectId/tickets/free";
}

$result = curlGet($endpoint, $data);
$tickets_html = getTicketsHTML($projectId, $result, $takeDisplay, $finishDisplay);
?>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="utf-8">
    <link rel="icon" href="../../favicon.ico">
    <title>ST - Menu Principal</title>
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
              <li><a href="projects.php">Mis Proyectos</a></li>
              <li><a href="create_project.php">Crear Proyecto</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
              <li><a href="index.php?logout=1">Cerrar Sesión</a></li>
            </ul>
          </div>
        </div>
      </nav>
	<form method="POST">
	      <div style="min-height:350px;" class="jumbotron">
		<h3>Proyecto <?=$projectName?> - Tickets</h3><br>
		<button type="button" class="btn btn-group-sm btn-warning" onclick="window.location='project_detail.php?id=<?=$projectId?>&name=<?=$projectName?>'">Volver</button><br><br>		
		<?=$tickets_html?>
	      </div>
	</form>
    </div>
  </body>
</html>


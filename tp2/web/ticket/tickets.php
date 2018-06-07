<?php
require_once("libs/funciones.php");
session_start();
$message = "";
$projectId = $_GET["id"];
$projectName = $_GET["name"];

if($_POST){	
	$endpoint = "http://localhost:9000/tickets/projects/$projectId/addTicket";
	$data = array( 
			"id" => "",
			"title" => $_POST["title"],
			"description" => $_POST["description"],
			"type" => $_POST["type"],
			"important" => $_POST["important"],
			"createdDate" => getFullDate(),
			"lastModifiedDate" => "",
			"authorUserName" => $_SESSION["userName"],
			"takenUserName" => ""
		);

	$result = curlPost($endpoint, $data);
	
	$ticketId = $result["id"];
	if ($ticketId >= 0){
		header('Location:project_detail.php?id='.$projectId.'&name='.$_GET["name"]);	
	}
	
	$message = $result["message"];
}
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
              <li class="active"><a href="create_project.php">Crear Proyecto</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
              <li><a href="index.php?logout=1">Cerrar Sesión</a></li>
            </ul>
          </div>
        </div>
      </nav>
	<form method="POST">
	      <div style="min-height:350px;" class="jumbotron">
		<h3>Nuevo Ticket</h3><br>
		<p>
			<span>Título:</span> 
			<input type="text" name="title">
		</p>
		<p>
			<span>Creador:</span> 
			<input type="text" value="<?=$_SESSION["userName"]?>" name="authUserName" readonly>
		</p>
		<p>
			<span>Descripción:</span> 
			<input type="text" name="description">
		</p>
		<p>
			<span>Tipo:</span> 
			<input type="text" name="type">
		</p>
		<p>
			<span>Importante:</span>
			<select name="important">
				<option value="true" selected>SI</option>
				<option value="false">NO</option>
			</select>
		</p>
		<br>        
		<p>
		<button type="button" class="btn btn-lg btn-warning" onclick="window.location='project_detail.php?id=<?=$projectId?>&name=<?=$projectName?>'">Volver</button>
		  <input name="submit_tickets" type="submit" class="btn btn-lg btn-primary" role="button" value="Crear">
		</p>
	      </div>
	</form>
    </div>
  </body>
</html>


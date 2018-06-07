<?php
require_once("libs/funciones.php");
session_start();
$message = "";

$html_users = getUsersHTML($_SESSION["userName"]);	

if($_POST){
	$projectId = $_GET["id"];	
	$endpoint = "http://localhost:9000/tickets/projects/".$projectId."/addUser";	
	$projectUsers = $_POST;

	foreach($projectUsers as $k => $v){

		if ($v == 'on'){
			$data = array( "userName" => $k, "password" => "");
			$res = curlPost($endpoint, $data);
		}	
	}	
	
	
	header('Location:create_project_step2.php?id='.$projectId);	
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
		<h3>Usuarios</h3><br>
		<?=$html_users?>
		<br><br><br>        
		<p>
		  <input name="submit_project1" type="submit" class="btn btn-lg btn-primary" role="button" value="Siguiente">
		</p>
	      </div>
	</form>
    </div>
  </body>
</html>


<?php
require_once("libs/funciones.php");
session_start();
$message = "";
$options = getTypesOptions();
if($_POST){
	$data = $_POST;
	$projectId = $_GET["id"];	
	$endpoint = "http://localhost:9000/tickets/projects/".$projectId."/addTicketType";
	$data = array(
			"authUserName" => $_POST["authUserName"],
			"type" => $_POST["type"],
			"requiredFields" => array(),
			"optionalFields" => array(),
			"states" => array()
		);	
	
	$data = addFieldsData($_POST, $data);
	
	$res = curlPost($endpoint, $data);
	
	header('Location:projects.php');	
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
             var options = "<?=$options?>";
    </script>
    <script src="js/create_project_step2.js"></script>
    	
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
		<h3>Tipos de Tickets</h3><br>
		<p>
			<span>Nombre:</span> 
			<input type="text" name="type">
		</p>
		<p>
			<span>Creador:</span> 
			<input type="text" name="authUserName" value="<?=$_SESSION["userName"]?>" readonly>
		</p> 
		<p>
			<h4>Campos Obligatorios</h4> 
			<button type="button" class="btn btn-group-sm btn-success" id = "addRequiredFieldsButton">Agregar</button>
			<button type="button" class="btn btn-group-sm btn-warning" id = "removeRequiredFieldsButton">Quitar</button>			
			<div id="requiredFields">
				<br>
				<div id="requiredFields0">			
					<span>Nombre:</span><input type="text" name="reqFieldName0">&nbsp;
					<span>Tipo:</span><select name="reqFieldType0"><?=$options?></select>
				</div>
			</div>
		</p>
		<p>
			<h4>Campos Opcionales</h4> 
			<button type="button" class="btn btn-group-sm btn-success" id = "addOptionalFieldsButton">Agregar</button>
			<button type="button" class="btn btn-group-sm btn-warning" id = "removeOptionalFieldsButton">Quitar</button>			
			<div id="optionalFields">
				<br>
				<div id="optionalFields0">			
					<span>Nombre:</span><input type="text" name="optFieldName0">&nbsp;
					<span>Tipo:</span><select name="optFieldType0"><?=$options?></select>
				</div>
			</div>
		</p>
		<p>
			<h4>Estados</h4> 
			<button type="button" class="btn btn-group-sm btn-success" id = "addStateFieldsButton">Agregar</button>
			<button type="button" class="btn btn-group-sm btn-warning" id = "removeStateFieldsButton">Quitar</button>			
			<div id="stateFields">
				<br>
				<div id="stateFields0">			
					<span>Orden:</span><input type="text" value="0" name="stateOrder0" readonly>&nbsp;
					<span>Nombre:</span><input type="text" name="stateName0">
				</div>
				<div id="stateFields1">			
					<span>Orden:</span><input type="text" value="1" name="stateOrder1" readonly>&nbsp;
					<span>Nombre:</span><input type="text" name="stateName1">
				</div>
			</div>
		</p>        
		<p>
		  <input name="submit_project2" type="submit" class="btn btn-lg btn-primary" role="button" value="Finalizar">
		</p>
	      </div>
	</form>
    </div>
  </body>
</html>

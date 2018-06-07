<?php
require_once("libs/funciones.php");
session_start();
$userName = $_SESSION["userName"];	
$endpoint = "http://localhost:9000/dashboards/users/$userName";
$result = curlGet($endpoint);

$dashboardsHTML = getDashboardsHTML($result);
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
              <li class="active"><a href="dashboards.php">Mis Dashboards</a></li>
              <li><a href="create_dashboard.php">Crear Dashboard</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
              <li><a href="index.php?logout=1">Cerrar Sesión</a></li>
            </ul>
          </div>
        </div>
      </nav>

      <div style="min-height:350px;" class="jumbotron">
        <h3>Mis Dashboards</h3><br>
	<?=$dashboardsHTML;?>	
      </div>
    </div>
  </body>
</html>


<?php
	require_once("libs/funciones.php");
	session_start();
	$idProject = $_GET["idProject"];
	$idTicket = $_GET["idTicket"];
	$endpoint = "http://localhost:9000/tickets/projects/$idProject/endState/$idTicket";
	$data = array("userName" => $_SESSION["userName"]);
	curlPost($endpoint, $data);
	header('Location:show_tickets.php?id='.$idProject);	
?>

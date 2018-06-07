<?php

function curlPost($endpoint, $data){
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, $endpoint);
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type:application/json'));
	curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$result = json_decode(curl_exec($ch), true);

	curl_close($ch);
	return $result;
}

function curlGet($endpoint){
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, $endpoint);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$result = json_decode(curl_exec($ch), true);
	
	curl_close($ch);
	return $result;
}

function getFullDate(){
	date_default_timezone_set('America/Argentina/Buenos_Aires');
	return date("Y-m-d H:i:s");
}

function getUsersHTML($userName){
	$getEndpoint = "http://localhost:9000/dashboards/users/all";
	$users = curlGet($getEndpoint);
	$html_chks = "";
	$chck_readonly = "";
	foreach ($users as $k => $v){
		if ($v == $userName){
	 		$chck_readonly = "readonly checked";	
		}
		$html_chks .= "<input name='$v' type='checkBox' $chck_readonly>&nbsp;$v<br>";
		$chck_readonly = "";
	}
	return $html_chks;
}

function getProjectsHTML($userName){
	$getEndpoint = "http://localhost:9000/tickets/users/$userName/projects";
	$projects = curlGet($getEndpoint);
	$html = "<span>Proyecto:</span> <select name='project'>";
	foreach ($projects as $k => $v){
		$html .= "<option value='".$v["id"]."'>".$v["name"]."</option>";
	}
	$html .= "</select>";
	return $html;
}

function getTypesOptions(){
	$options = "<option value='int'>Entero</option><option value='string'>Texto</option>";
	return $options;
}

function getDashboardsHTML($result){
	$response = "";
	$CANT_FILA = 5;
	$c = 0;
	foreach($result as $k => $v){
		$response .= "<button class='btn btn-group-sm btn-success' style='margin-right:20px;' onclick='window.location=\"dashboard_detail.php?id=".$v['id']."&name=".$v['name']."\";' type='button'>".$v['name']."</button>";
		$c++;
		if ($c == $CANT_FILA){
			$response .= "<br>";
			$c = 0;
		}
	}
	return $response;
}

function getResultsHTML($result){

	$response = '<table class="table">
			<tr class="warning">
				<td>Nombre</td>
				<td>Valor</td>
		    	</tr>';	
	foreach($result as $k => $v){
		$response .= '<tr class="success">
  				<td>'.$v["name"].'</td>
  				<td>'.$v["value"].'</td>
			    </tr>';
	}

	$response .= '</table>';
	return $response;
}

function getRulesHTML($result){

	$response = '<table class="table">
			<tr class="warning">
				<td>Nombre</td>
				<td>Consulta</td>
		    	</tr>';
	foreach($result as $k => $v){
		$response .= '<tr class="success">
  				<td>'.$v["name"].'</td>
  				<td>'.$v["query"].'</td>
			    </tr>';
	}

	$response .= '</table>';
	return $response;
}
?>

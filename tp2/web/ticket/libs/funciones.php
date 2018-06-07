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
	$getEndpoint = "http://localhost:9000/tickets/users/all";
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

function getProjectsHTML($result){
	$response = "";
	$CANT_FILA = 5;
	$c = 0;
	foreach($result as $k => $v){
		$response .= "<button class='btn btn-group-sm btn-success' style='margin-right:20px;' onclick='window.location=\"project_detail.php?id=".$v['id']."&name=".$v['name']."\";' type='button'>".$v['name']."</button>";
		$c++;
		if ($c == $CANT_FILA){
			$response .= "<br>";
			$c = 0;
		}
	}
	return $response;
}

function getTicketsHTML($idProject, $result, $takeColumnDisplay, $finishColumnDisplay){

	$response = '<table class="table">
			<tr class="warning">
				<td>Creador</td>
				<td>Asignado</td>
				<td>F. Creación</td>
				<td>Título</td>
				<td>Descripción</td>
				<td>Tipo</td>
				<td>Importante</td>
				<td>Estado</td>';
	
	if ($takeColumnDisplay) $response .= "<td></td>";
	if ($finishColumnDisplay) $response .= "<td></td>";
	
	$response .=		'<td></td>
		    	</tr>';	
	foreach($result as $k => $v){
		$important = ($v["important"] == "true")?"SI":"NO";
		$response .= '<tr class="success">
  				<td>'.$v["authorUserName"].'</td>
  				<td>'.$v["takenUserName"].'</td>
  				<td>'.$v["createdDate"].'</td>
  				<td>'.$v["title"].'</td>
  				<td>'.$v["description"].'</td>
  				<td>'.$v["type"].'</td>
				<td>'.$important.'</td>
  				<td>'.$v["state"].'</td>';
		
		if ($takeColumnDisplay) $response .= '<td><button type="button" class="btn btn-group-sm btn-warning" onclick="window.location=\'take_tickets.php?idProject='.$idProject.'&idTicket='.$v["ticketId"].'\'">Tomar</button></td>';
		if ($finishColumnDisplay) $response .= '<td><button type="button" class="btn btn-group-sm btn-warning" onclick="window.location=\'resolve_ticket.php?idProject='.$idProject.'&idTicket='.$v["ticketId"].'\'">Resuelto</button></td>';

		$response .= '<td><button type="button" class="btn btn-group-sm btn-danger" onclick="window.location=\'comments.php?idProject='.$idProject.'&idTicket='.$v["ticketId"].'\'">Comentarios</button></td>
			    </tr>';
	}

	$response .= '</table>';
	return $response;
}

function getTypesOptions(){
	$options = "<option value='int'>Entero</option><option value='string'>Texto</option>";
	return $options;
}

function addStateFields($data){
	$parts = explode("state", $k);
	if (count($parts) > 1){
		$fieldName = explode("Name", $parts[1]);
		if (count($fieldName) > 1){
			$order = $fieldName[1];
			$data["states"][$order]["name"] = $v;
		}else{
			$fieldOrder = explode("Order", $parts[1]);
			if (count($fieldOrder) > 1){
				$order = $fieldOrder[1];
				$data["states"][$order]["order"] = $v;
			}
		
		}
	}
	return $data;
}

function addReqFields($data){
	$parts = explode("reqField", $k);
	if (count($parts) > 1){
		$fieldName = explode("Name", $parts[1]);
		if (count($fieldName) > 1){
			$order = $fieldName[1];
			$data["requiredFields"][$order]["name"] = $v;
		}else{
			$fieldType = explode("Type", $parts[1]);
			if (count($fieldType) > 1){
				$order = $fieldType[1];
				$data["requiredFields"][$order]["type"] = $v;
			}
		
		}
	}
	return $data;
}

function addOptFields($data){
	$parts = explode("optField", $k);
	if (count($parts) > 1){
		$fieldName = explode("Name", $parts[1]);
		if (count($fieldName) > 1){
			$order = $fieldName[1];
			$data["optionalFields"][$order]["name"] = $v;
		}else{
			$fieldType = explode("Type", $parts[1]);
			if (count($fieldType) > 1){
				$order = $fieldType[1];
				$data["optionalFields"][$order]["type"] = $v;
			}
		
		}
	}
	return $data;
}

function addFieldsData($entryData, $data){
	foreach ($entryData as $k => $v){
		$data = addStateFields($data);
		$data = addReqFields($data);
		$data = addOptFields($data);
	}
	return $data;
}

?>

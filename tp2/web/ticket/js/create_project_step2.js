var message = "";
$(document).ready(function() {
    if (message)alert( message );
    var MAX_FIELDS = 4;
    var wrapperReqFields = $("#requiredFields");
    var addReqButton = $("#addRequiredFieldsButton");
    var removeReqButton = $("#removeRequiredFieldsButton");

    var x = 0;
    addReqButton.click(function(e){
	if(x < MAX_FIELDS){
	    x++;
	    $(wrapperReqFields).append("<div id='requiredFields"+x+"'>"+			
				"<span>Nombre:</span><input type='text' name='reqFieldName"+x+"'>&nbsp;&nbsp;"+
				"<span>Tipo:</span><select name='reqFieldType"+x+"'>"+options+"</select>"+
			"</div>");
	}
    });
   
    removeReqButton.click(function(e){
		if (x > 0){			
			$("#requiredFields"+x).remove(); 
			x--;
		}
	});
    
    var wrapperOptFields = $("#optionalFields");
    var addOptButton = $("#addOptionalFieldsButton");
    var removeOptButton = $("#removeOptionalFieldsButton");

    var y = 0;
    addOptButton.click(function(e){
	if(y < MAX_FIELDS){
	    y++;
	    $(wrapperOptFields).append("<div id='optionalFields"+y+"'>"+			
				"<span>Nombre:</span><input type='text' name='optFieldName"+y+"'>&nbsp;&nbsp;"+
				"<span>Tipo:</span><select name='optFieldType"+y+"'>"+options+"</select>"+
			"</div>");
	}
    });
   
    removeOptButton.click(function(e){
		if (y > 0){			
			$("#optionalFields"+y).remove(); 
			y--;
		}
	});

    var wrapperStateFields = $("#stateFields");
    var addStateButton = $("#addStateFieldsButton");
    var removeStateButton = $("#removeStateFieldsButton");

    var z = 1;
    addStateButton.click(function(e){
	if(z < MAX_FIELDS){
	    z++;
	    $(wrapperStateFields).append("<div id='stateFields"+z+"'>"+			
				"<span>Orden:</span><input type='text' name='stateOrder"+z+"' value='"+z+"' readonly>&nbsp;&nbsp;"+
				"<span>Nombre:</span><input type='text' name='stateName"+z+"'>"+
			"</div>");
	}
    });
   
    removeStateButton.click(function(e){
		if (z > 1){			
			$("#stateFields"+z).remove(); 
			z--;
		}
	});	
});

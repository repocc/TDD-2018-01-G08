var message = "";
$(document).ready(function() {
    if (message)alert( message );
    var MAX_FIELDS = 9;
    var wrapperCounterFields = $("#counterFields");
    var addCounterButton = $("#addCounterFieldsButton");
    var removeCounterButton = $("#removeCounterFieldsButton");

    var x = 0;
    addCounterButton.click(function(e){
	if(x < MAX_FIELDS){
	    x++;
	    $(wrapperCounterFields).append("<div id='counterFields"+x+"'>"+			
				"<span>Nombre:</span><input type='text' name='counterFieldName"+x+"'>&nbsp;&nbsp;"+
				"<span>Consulta:</span><input style='width:500px;' value='(define-counter <counter-name> <args> <expr>)' name='counterFieldQuery"+x+"'>"+
			"</div>");
	}
    });
   
    removeCounterButton.click(function(e){
		if (x > 0){			
			$("#counterFields"+x).remove(); 
			x--;
		}
	});	
});

	$("#submitBtn").click(function(e){ 
		if($("#address").val() == "" ||
		   $("#city").val() =="" ||
		   $("#state").val() == "") {
			$("#warnLabel").fadeIn(200);
			e.preventDefault();
			return false;
		}
		$("#warnLabel").hide();
		$("#factsInfo").fadeIn(500);
		/*-----------DeepSearch--------------*/
		$.ajax({ 
		    type: 'POST', 
		    url: 'Engine', 
		    data: { oper: 'findHouse', 
		    	address:$("#address").val(), 
		    	city: $("#city").val(),
		    	state: $("#state").val()}, 
		    dataType: 'json',
		    success: function (data) { 
		    	$("#alertInfo").hide();
		    	$("#estimatePrice").html("<h1>$"+data+"</h1>");
		    	$("#estimatePrice").fadeIn(500);
		    },
		    error: function(e) {
		    	console.log("Error: "+JSON.stringify(e));
		    }
		});	
		/*-------------Chart Value--------------*/
		$.ajax({ 
		    type: 'POST', 
		    url: 'Engine', 
		    data: { oper: 'getChartEstimate', 
		    	address:$("#address").val(), 
		    	city: $("#city").val(),
		    	state: $("#state").val()}, 
		    dataType: 'text',
		    success: function (data) { 
		    	console.log(data)
		    	$("#estimateChart").html("<img src='"+data+"' />");
		    },
		    error: function(e) {
		    	console.log("Error: "+JSON.stringify(e));
		    }
		});	
		/*-------------Recently sold within area--------------*/
		$.ajax({ 
		    type: 'POST', 
		    url: 'Engine', 
		    data: { oper: 'getHousesSold', 
		    	city: $("#city").val(),
		    	state: $("#state").val()}, 
		    dataType: 'json',
		    success: function (data) { 
		    	console.log(JSON.stringify(data));
		    	$('#loadingId').modal('hide');
		    },
		    error: function(e) {
		    	$('#loadingId').modal('hide');
		    	console.log("Error: "+JSON.stringify(e));
		    }
		});	
	})
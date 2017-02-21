//	$("#factsInfo").fadeIn(500);
//	$("#estimatedValue").fadeIn(500);
//	$("#moreFacts").show();
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
		    	$("#estimatePrice").html("<h1>$"+data+"</h1>");
		    	$("#moreFacts").show();
		    	$("#estimatedValue").fadeIn(500);
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
		    	console.log(JSON.stringify(data[2].nearByInfo));
		        for(var i=0; i < data[2].nearByInfo.length; i++) {
		        	var item = data[2].nearByInfo[i];
		        	if(!item.includes("Zip")) {
			        	var town = item.substring(0,item.indexOf("$"));
			        	var price = item.substring(item.indexOf("$"),item.length);
			        	if(!(town == "" || town == null)) {
			        		$('#areaStatsBody').append(
				        			 "<tr class='child'>"
				        			 +'<td  align="left">'+town+'</td>'
				        			 +'<td  align="left">'+price+'</td>'
				        			 +'</tr>');
			        	} 
		        	}
		        }
		        
		    	$('#loadingId').modal('hide');
		    },
		    error: function(e) {
		    	$('#loadingId').modal('hide');
		    	console.log("Error: "+JSON.stringify(e));
		    }
		});	
	})
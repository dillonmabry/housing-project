	getVersion().done(function(manifestVersion) {
	    $("#footer").html(
				'<div class="container" style="margin:10px;">'
		      	+'<p style="color:#fff; text-align:center">'
		      	+'Housing Price Estimator '+manifestVersion+' | Calculations assisted using <a target="_blank" href="http://zillow.com">Zillow</a></p>'
		      	+'</div>'
		
		)
	}).fail(function(error) {
	   console.log(error);
	});

	//hide other options on click
	$(".sellingBtn").click(function(){
		$("#buySection").hide();
		$("#factsInfo").hide();
		$(".moreFacts").hide();
		$("#estimatedValue").hide();
		$("#estimateSection").show();
		$(".allHomes").hide();
	})
	
	//hide other options on click
	$(".purchaseBtn").click(function(){
		$("#estimateSection").hide();
		$("#factsInfo").hide();
		$("#estimatedValue").hide();
		$(".moreFacts").hide();
		$("#maxPrice").html("$"+$("#price-max").val())
		$("#minPrice").html("$"+$("#price-min").val())
		$("#buySection").show();
		$(".allHomes").show();
	})
	//export to pdf
	$('#exportPDF').click(function() {
		exportPDF();
	});
	
	$("#exportCSV").click(function(){
		$("#allHomesTable").table2excel({
			    exclude: ".noExl",
			    name: "Worksheet Name",
			    filename: "All Homes" 
			  });
	});
	
	function outputUpdate(num) {
		$("#minPrice").html("$"+num);
	}
	
	function outputUpdateMax(num) {
		$("#maxPrice").html("$"+num);
	}
	
	/* ----------- Handle user input validation ----------------*/
	var inputQuantity = [];
    $(function() {
      $(".quantity").each(function(i) {
        inputQuantity[i]=this.defaultValue;
         $(this).data("idx",i); // save this field's index to access later
      });
      $(".quantity").on("keyup", function (e) {
        var $field = $(this),
        val=this.value,
        $thisIndex=parseInt($field.data("idx"),10); // retrieve the index
        if (this.validity && this.validity.badInput || isNaN(val) || $field.is(":invalid") ) {
            this.value = inputQuantity[$thisIndex];
            return;
        } 
        if (val.length > Number($field.attr("maxlength"))) {
          val=val.slice(0, 5);
          $field.val(val);
        }
        inputQuantity[$thisIndex]=val;
      });      
    });
      
/* --------------- Run Analysis ------------------ */
$("#submitBtn").click(function(e){ 
		if($("#address").val() == "" ||
		   $("#city").val() =="" ||
		   $("#state").val() == "") {
			$(".warnLabel").fadeIn(200);
			e.preventDefault();
			return false;
		}
		$(".warnLabel").hide();
		$(".areaRank").hide();
		$(".valueTime").show();
		$(".homesSale").hide();
		$(".crimeArea").hide();
		$(".recentSold").show();
		$("#factsInfo").show();
		$("#areaAggregate").hide();
		$("#distChartDiv").show();
		$("#exportCSV").hide();
		/*-----------DeepSearch--------------*/
		$.ajax({ 
		    type: 'POST', 
		    url: 'Houses', 
		    data: { oper: 'findHouse', 
		    	address:$("#address").val(), 
		    	city: $("#city").val(),
		    	state: $("#state").val()}, 
		    dataType: 'json',
		    success: function (data) {     	
		    	$("#factsInfo").fadeIn(500);
		    	$(".moreFacts").show();
		    	$("#estimatedValue").fadeIn(500);
		    	$("#estimatedValue").offset().top - 10;
		    	$("#estimatePrice").html("<h1 style='font-weight:400'>$"+data.toLocaleString()+"</h1>");
		    },
		    error: function(e) {
		    	$('#loadingId').modal('hide');
		    	Command: toastr["error"]("Cannot find property specified or check connectivity", "Error!")

		    	toastr.options = {
		    	  "closeButton": true,
		    	  "debug": false,
		    	  "newestOnTop": false,
		    	  "progressBar": false,
		    	  "positionClass": "toast-top-right",
		    	  "preventDuplicates": false,
		    	  "onclick": null,
		    	  "showDuration": "300",
		    	  "hideDuration": "1000",
		    	  "timeOut": "3000",
		    	  "extendedTimeOut": "1000",
		    	  "showEasing": "swing",
		    	  "hideEasing": "linear",
		    	  "showMethod": "fadeIn",
		    	  "hideMethod": "fadeOut"
		    	}
		    	console.log("Error: "+JSON.stringify(e));
		    }
		});	
		/*-------------Chart Value--------------*/
		$.ajax({ 
		    type: 'POST', 
		    url: 'Houses', 
		    data: { oper: 'getChartEstimate', 
		    	address:$("#address").val(), 
		    	city: $("#city").val(),
		    	state: $("#state").val()}, 
		    dataType: 'text',
		    success: function (data) { 
		    	trainingEstimate = data;
		    	//console.log(data)
		    	$("#estimateChart").html("<img src='"+data+"' />");
		    },
		    error: function(e) {
		    	$('#loadingId').modal('hide');
		    	console.log("Error: "+JSON.stringify(e));
		    }
		});	
		/*-------------Recently sold within area--------------*/
		$.ajax({ 
		    type: 'POST', 
		    url: 'Houses', 
		    data: { oper: 'getHousesSold', 
		    	city: $("#city").val(),
		    	state: $("#state").val()}, 
		    dataType: 'json',
		    success: function (data) { 
		    	//console.log(JSON.stringify(data));
		    	//add append check
		    	$('#areaStatsBody').html("");
		    	$('#recentBody').html("");
		    	$("#distChart").html("");
		    	//console.log(JSON.stringify(data));
		    	
		        for(var i=0; i < data[2].nearByInfo.length; i++) {
		        	var item = data[2].nearByInfo[i];
		        	if(!item.includes("Zip")) {
			        	var town = item.substring(0,item.indexOf("$"));
			        	var price = item.substring(item.indexOf("$")+1,item.length);
			        	if(!(town == "" || town == null || 
			        		town =="undefined" || town == undefined || price == "" || 
			        		price == undefined || price == "undefined")) {
			        		$('#areaStatsBody').append(
				        			 "<tr class='child'>"
				        			 +'<td  align="left">'+town+'</td>'
				        			 +'<td  align="left">$'+price+'</td>'
				        			 +'</tr>');
			        	} 
		        	}
		        }
		         //validate data on client side
		    	 $("#areaStatsBody tr td:nth-child(2)").each(function () {
		    		 var neighborhood = $(this).closest('tr').find('td:eq(0)').text();
		    		 var avgValue = $(this).closest('tr').find('td:eq(1)').text();
		    		 console.log(neighborhood);
		    		 if(neighborhood.includes("Neighborhood") || neighborhood.includes("neighborhood") ||
		    				 neighborhood.includes("Average") || avgValue.length > 15 || !isNaN(parseInt(neighborhood))) {
		    			 $(this).closest('tr').hide();
		    		 }
		    	 });
		        
		        var underOne = 0;
		        var onetoTwo = 0;
		        var twotoFive = 0;
		        var overFive = 0;
		        for(var i=0; i < data[0].soldValues.length; i++) {
		        	var soldItem = data[0].soldValues[i];
		        	var infoItem = data[1].houseInfo[i];
		        	if(soldItem.includes("SOLD")) {
			        	var price = soldItem.substring(soldItem.indexOf("$")+1,soldItem.length);
			        	var intPrice = parseInt(soldItem.substring(soldItem.indexOf("$")+1,soldItem.length).replace(/,/g , ""));
			        	if(!isNaN(intPrice)) {
				        	if(intPrice <= 100000) {
				        		underOne++;
				        	} else if(intPrice >= 100000 && intPrice <= 250000) {
				        		onetoTwo++;
				        	} else if(intPrice >= 250000 && intPrice <= 500000) {
				        		twotoFive++;
				        	}  else if(intPrice >= 500000) {
				        		overFive++;
				        	}
			        	}
			        	var beds = infoItem.substring(infoItem.indexOf("bds")-2,infoItem.indexOf("bds")-1);
			        	var baths = infoItem.substring(infoItem.indexOf("ba")-2,infoItem.indexOf("ba")-1);
			        	if(beds == "" || beds == null || beds == '') {
			        		$('#recentBody').append(
				        			 "<tr class='child'>"
				        			 +'<td  align="left">$'+price+'</td>'
				        			 +'<td  align="left">-</td>'
				        			 +'<td  align="left">'+baths+'</td>'
				        			 +'</tr>');
			        	}
			        		$('#recentBody').append(
				        			 "<tr class='child'>"
				        			 +'<td  align="left">$'+price+'</td>'
				        			 +'<td  align="left">'+beds+'</td>'
				        			 +'<td  align="left">'+baths+'</td>'
				        			 +'</tr>');
			        	} 
		        }
		        $('#moreFactsModal').on('shown.bs.modal', function (e) {
		        	$("#distChart").html("");
		        	 Morris.Donut({
			        	  element: 'distChart',
			        	  data: [
			        	    {label: "Under $100,000", value: underOne},
			        	    {label: "$100,000-$250,000", value: onetoTwo},
			        	    {label: "$250,000-$500,000", value: twotoFive},
			        	    {label: "$500,000+", value: overFive}
			        	  ]
			        	});
		        	});
		       
		    	$('#loadingId').modal('hide');
		    },
		    error: function(e) {
		    	$('#loadingId').modal('hide');
		    	console.log("Error: "+JSON.stringify(e));
		    }
		});	
	});
	/*---------------- End Run Analysis ---------------- */

	/*---------------- Purchasing Analysis ------------- */
	$("#submitPurchase").click(function(e){
		$(".valueTime").hide();
		if(
		   $("#cityPurchase").val() =="" ||
		   $("#statePurchase").val() == "" ||
		   $("#bedsInputForm").val() == "" ||
		   $("#bathsInputForm").val() == "" ||
		   $("#price-min").val() == "" ||
		   $("#price-max").val() == "")
		   {
				$(".warnLabel").fadeIn(200);
				e.preventDefault();
				return false;
		   }
		$(".warnLabel").hide();
		$(".areaRank").show();
		$(".homesSale").show();
		$(".crimeArea").show();
		$("#factsInfo").show();
		$(".recentSold").hide();
		$("#distChart").html("");
		$("#exportCSV").show();
		var cityFinal = $("#cityPurchase").val();
		var stateFinal = $("#statePurchase").val();
		var bedsFinal = $("#bedsInputForm").val();
		var bathsFinal = $("#bathsInputForm").val();
		var minFinal = $("#price-min").val();
		var maxFinal = $("#price-max").val();
		/*-------------Available within area--------------*/
		$.ajax({ 
		    type: 'POST', 
		    url: 'Houses', 
		    data: { oper: 'getHousesSale', 
		    	city: $("#cityPurchase").val(),
		    	state: $("#statePurchase").val()}, 
		    dataType: 'json',
		    success: function (data) { 
		    	
		    	//add append check
		    	$('#areaStatsBody').html("");
		    	$("#homesSaleBody").html("");
		    	$('#recentBody').html("");
		    	$("#areaAggregate").html("");
		    	$("#areaAggregate").show();
		    	$(".otherFactStats").show();
		    	$("#crimeReport").show();
		    	var rawAverage = 0;
		    	var sumAverageRaw = 0;
		    	var averageLength = 0;
		        for(var i=0; i < data[1].nearByInfo.length; i++) {
		        	var item = data[1].nearByInfo[i];
		        	if(!item.includes("Zip")) {
			        	var town = item.substring(0,item.indexOf("$"));
			        	var price = item.substring(item.indexOf("$")+1,item.length);
			        	var intPrice = parseInt(item.substring(item.indexOf("$")+1,item.length).replace(',', ''));
			        	if(!isNaN(intPrice)) {
			        		if(intPrice < maxFinal && intPrice > minFinal ) {
			        			if(intPrice != undefined || intPrice != "undefined") {
			        				sumAverageRaw += intPrice;
			        				averageLength++;
			        			}
			        			if(!(town == "" || town == null || 
						        		town =="undefined" || town == undefined || price == "" || 
						        		price == undefined || price == "undefined")) {
						        		$('#areaStatsBody').append(
							        			 "<tr class='child'>"
							        			 +'<td  align="left">'+town+'</td>'
							        			 +'<td  align="left">$'+price+'</td>'
							        			 +'</tr>');
						        	} 
			        		}
			        	}
		        	}
		        }
		         //validate data on client side
		    	 $("#areaStatsBody tr td:nth-child(2)").each(function () {
		    		 var neighborhood = $(this).closest('tr').find('td:eq(0)').text();
		    		 var avgValue = $(this).closest('tr').find('td:eq(1)').text();
		    		 console.log(neighborhood);
		    		 if(neighborhood.includes("Neighborhood") || neighborhood.includes("neighborhood") ||
		    				 neighborhood.includes("Average") || avgValue.length > 15 || !isNaN(parseInt(neighborhood))) {
		    			 $(this).closest('tr').hide();
		    		 }
		    	 });
		      
		        var rawAverage = sumAverageRaw/averageLength;
		        var viewAverage = Math.round(rawAverage);
		        $("#areaAggregate").html("Raw Average of All Cities: $"+viewAverage.toLocaleString('en-US'));
		        var found = false;
		        for(var i=0; i < data[0].soldValues.length; i++) {
		        	var soldItem = data[0].soldValues[i];
		        	var price = soldItem.substring(soldItem.indexOf("$"),soldItem.indexOf("bds")-2);
			        var intPrice = parseInt(soldItem.substring(soldItem.indexOf("$")+1,soldItem.indexOf("bds")-2).replace(/,/g , ""));
			        var ahref = soldItem.substring(soldItem.lastIndexOf(",")+1,soldItem.length);
			        if(intPrice < 25000) {
			        	
			        } else {
			        	var baths = soldItem.substring(soldItem.indexOf("bd")+5,soldItem.indexOf("ba")-1);
			        	var beds = soldItem.substring(soldItem.indexOf("bd")-2,soldItem.indexOf("bd")-1);
			        	if(!isNaN(intPrice) && !isNaN(parseInt(beds))) {
			        		if(!(beds == "" || beds == null || beds == '')) {
				        		if(intPrice < maxFinal && intPrice > minFinal && parseInt(beds) == bedsFinal &&
				        				parseInt(baths) == bathsFinal 
				        		) {
				        			$('#homesSaleBody').append(
						        			 "<tr class='child'>"
						        			 +'<td  align="left">'+price+'</td>'
						        			 +'<td  align="left">'+beds+'</td>'
						        			 +'<td  align="left">'+baths+'</td>'
						        			 +'<td  align="left"><a href="'+ahref+'" target="_blank">View Home</a></td>'
						        			 //TODO: Add link to specific property via Zillow here 
						        			 +'</tr>');
				        			found = true;
				        		}
			        		}
			        	}
			        }
			        
		        }
		        if(found != true) {
		        	$('#homesSaleBody').html("");
		        	Command: toastr["info"]("Search results not found!", "Info")
    				toastr.options = {
    				  "closeButton": false,
    				  "debug": false,
    				  "newestOnTop": false,
    				  "progressBar": false,
    				  "positionClass": "toast-top-right",
    				  "preventDuplicates": false,
    				  "onclick": null,
    				  "showDuration": "300",
    				  "hideDuration": "1000",
    				  "timeOut": "3000",
    				  "extendedTimeOut": "1000",
    				  "showEasing": "swing",
    				  "hideEasing": "linear",
    				  "showMethod": "fadeIn",
    				  "hideMethod": "fadeOut"
    				}
		        }
		        $('#allHomesBody').html("");
				$.ajax({ 
				    type: 'POST', 
				    url: 'Houses', 
				    data: { oper: 'getHousesSale', 
				    	city: $("#cityPurchase").val(),
				    	state: $("#statePurchase").val()}, 
				    dataType: 'json',
				    success: function (data) {    
				    	console.log(JSON.stringify(data[0].soldValues));
				    	 for(var i=0; i < data[0].soldValues.length; i++) {
					        	var soldItem = data[0].soldValues[i];
					        	var price = soldItem.substring(soldItem.indexOf("$"),soldItem.indexOf("bds")-2);
						        var intPrice = parseInt(soldItem.substring(soldItem.indexOf("$")+1,soldItem.indexOf("bds")-2).replace(/,/g , ""));
						        var ahref = soldItem.substring(soldItem.lastIndexOf(",")+1,soldItem.length);
						        var address = soldItem.substring(0, soldItem.indexOf(","));
						        if(intPrice < 25000) {
						        } else {
						        	var baths = soldItem.substring(soldItem.indexOf("bd")+5,soldItem.indexOf("ba")-1);
						        	var beds = soldItem.substring(soldItem.indexOf("bd")-2,soldItem.indexOf("bd")-1);
						        	if(!isNaN(intPrice) && !isNaN(parseInt(beds))) {
						        		if(!(beds == "" || beds == null || beds == '' || beds == '-')) {
							        			$('#allHomesBody').append(
									        			 "<tr class='child'>"
									        			 +'<td  align="left">'+price+'</td>'
									        			 +'<td  align="left">'+beds+'</td>'
									        			 +'<td  align="left">'+baths+'</td>'
									        			 +'<td  align="left">'+address+'</td>'
									        			 +'<td  align="left"><a href="'+ahref+'" target="_blank">View Home</a></td>'
									        			 //TODO: Add link to specific property via Zillow here 
									        			 +'</tr>');
						        		}
						        	}
						        }
						        
					        }
				    	 //validate data on client side
				    	 $("#allHomesBody tr td:nth-child(2)").each(function () {
				    		 var bathElement = $(this).closest('tr').find('td:eq(2)').text();
				    		 var bedElement = $(this).closest('tr').find('td:eq(1)').text();
				    		 var bath = parseInt(bathElement);
				    		 var bed = parseInt(bedElement);
				    		 if(isNaN(bath) || isNaN(bed)) {
				    			 $(this).closest('tr').hide();
				    		 }
				    	 });
				    },
				    error: function(e) {
				    	Command: toastr["error"]("Error, homes not found!", "Error!")

				    	toastr.options = {
				    	  "closeButton": true,
				    	  "debug": false,
				    	  "newestOnTop": false,
				    	  "progressBar": false,
				    	  "positionClass": "toast-top-right",
				    	  "preventDuplicates": false,
				    	  "onclick": null,
				    	  "showDuration": "300",
				    	  "hideDuration": "1000",
				    	  "timeOut": "3000",
				    	  "extendedTimeOut": "1000",
				    	  "showEasing": "swing",
				    	  "hideEasing": "linear",
				    	  "showMethod": "fadeIn",
				    	  "hideMethod": "fadeOut"
				    	}
				    	console.log("Error: "+JSON.stringify(e));
				    }
				});	
		    	$('#loadingId').modal('hide');
		    },
		    error: function(e) {
		    	$('#loadingId').modal('hide');
		    	console.log("Error: "+JSON.stringify(e));
		    }
		});	
		/* get nearby schools and ratings */
		$.ajax({ 
		    type: 'POST', 
		    url: 'Houses', 
		    data: { oper: 'getSchools', 
		    	city: $("#cityPurchase").val(),
		    	state: $("#statePurchase").val()}, 
		    dataType: 'json',
		    success: function (data) {  
		    	//console.log(JSON.stringify(data));
		    	//add append check
		    	$('#areaRanksBody').html("");
		    	for(var i=0; i < data.schoolName.length; i++) {
		    		//console.log(data.schoolName[i]);
		    		$('#areaRanksBody').append(
		        			 "<tr class='child'>"
		        			 +'<td  align="left">'+data.schoolRank[i]+'</td>'
		        			 +'<td  align="left">'+data.schoolName[i]+'</td>'
		        			 +'<td  align="left">'+data.schoolGrade[i]+'</td>'
		        			 +'<td  align="left">'+data.schoolNumStudents[i]+'</td>'
		        			 +'</tr>');
		        }
		    	
		    },
		    error: function(e) {
		    	$('#loadingId').modal('hide');
		    	Command: toastr["error"]("Cannot find area specified or check connectivity", "Error!")

		    	toastr.options = {
		    	  "closeButton": true,
		    	  "debug": false,
		    	  "newestOnTop": false,
		    	  "progressBar": false,
		    	  "positionClass": "toast-top-right",
		    	  "preventDuplicates": false,
		    	  "onclick": null,
		    	  "showDuration": "300",
		    	  "hideDuration": "1000",
		    	  "timeOut": "3000",
		    	  "extendedTimeOut": "1000",
		    	  "showEasing": "swing",
		    	  "hideEasing": "linear",
		    	  "showMethod": "fadeIn",
		    	  "hideMethod": "fadeOut"
		    	}
		    	console.log("Error: "+JSON.stringify(e));
		    }
		});	
		/* get neighborgood safety information */
		$.ajax({ 
		    type: 'POST', 
		    url: 'Houses', 
		    data: { oper: 'getSafety', 
		    	city: $("#cityPurchase").val(),
		    	state: $("#statePurchase").val()}, 
		    dataType: 'json',
		    success: function (data) {  
		    	//console.log(JSON.stringify(data.safestPlaces.length));
		    	//add append check
		    	$('#crimeBody').html("");
		    	$("#crimeReport").html("");
		    	var indexFinal = data.crimeIndex[0].substring(data.crimeIndex[0].indexOf("x")+1,data.crimeIndex[0].indexOf("("));
		    	var intIndexFinal = parseInt(indexFinal);
		    	if(intIndexFinal < 25) {
		    		$("#crimeReport").append("Crime Index: "+intIndexFinal+" (Safer than "+intIndexFinal+"% of U.S. Cities)");
		    		$("#crimeReport").css("background-color","#a94442");
		    	} else if (intIndexFinal > 20 && intIndexFinal < 30) {
		    		$("#crimeReport").append("Crime Index: "+intIndexFinal+" (Safer than "+intIndexFinal+"% of U.S. Cities)");
		    		$("#crimeReport").css("background-color","#f89406");
		    	} else if (intIndexFinal > 30) {
		    		$("#crimeReport").append("Crime Index: "+intIndexFinal+" (Safer than "+intIndexFinal+"% of U.S. Cities)");
		    		$("#crimeReport").css("background-color","green");
		    	}
		    	for(var i=0; i < data.safestPlaces.length; i++) {
		    		$('#crimeBody').append(
		        			 "<tr class='child'>"
		        			 +'<td  align="left">'+data.safestPlaces[i]+'</td>'
		        			 +'</tr>');
		        }
		    	$("#factsInfo").fadeIn(500);
		    	$(".moreFacts").show();
		    },
		    error: function(e) {
		    	$('#loadingId').modal('hide');
		    	$('#crimeBody').append(
	        			 "<tr class='child'>"
	        			 +'<td  align="left">'+e.responseText+'</td>'
	        			 +'</tr>');
		    	$("#areaAggregate").hide();
		    	$("#crimeReport").hide();
		    	console.log("Error: "+JSON.stringify(e));
		    }
		});	
		
	})

	/* Convert JSON to CSF */
	function JSONToCSVConvertor(JSONData, ReportTitle, ShowLabel) {
	    //If JSONData is not an object then JSON.parse will parse the JSON string in an Object
	    var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;
	    
	    var CSV = '';    
	    //Set Report title in first row or line
	    
	    CSV += ReportTitle + '\r\n\n';
	
	    //This condition will generate the Label/Header
	    if (ShowLabel) {
	        var row = "";
	        
	        //This loop will extract the label from 1st index of on array
	        for (var index in arrData[0]) {
	            
	            //Now convert each value to string and comma-seprated
	            row += index + ',';
	        }
	
	        row = row.slice(0, -1);
	        
	        //append Label row with line break
	        CSV += row + '\r\n';
	    }
	    
	    //1st loop is to extract each row
	    for (var i = 0; i < arrData.length; i++) {
	        var row = "";
	        
	        //2nd loop will extract each column and convert it in string comma-seprated
	        for (var index in arrData[i]) {
	            row += '"' + arrData[i][index] + '",';
	        }
	
	        row.slice(0, row.length - 1);
	        
	        //add a line break after each row
	        CSV += row + '\r\n';
	    }
	
	    if (CSV == '') {        
	        alert("Invalid data");
	        return;
	    }   
	    
	    //Generate a file name
	    var fileName = "MyReport_";
	    //this will remove the blank-spaces from the title and replace it with an underscore
	    fileName += ReportTitle.replace(/ /g,"_");   
	    
	    //Initialize file format you want csv or xls
	    var uri = 'data:text/csv;charset=utf-8,' + escape(CSV);
	    
	    // Now the little tricky part.
	    // you can use either>> window.open(uri);
	    // but this will not work in some browsers
	    // or you will not get the correct file extension    
	    
	    //this trick will generate a temp <a /> tag
	    var link = document.createElement("a");    
	    link.href = uri;
	    
	    //set the visibility hidden so it will not effect on your web-layout
	    link.style = "visibility:hidden";
	    link.download = fileName + ".csv";
	    
	    //this part will append the anchor tag and remove it after automatic click
	    document.body.appendChild(link);
	    link.click();
	    document.body.removeChild(link);
	}

	function exportPDF() {
		var divContents = $("#statsPanel").html();
        var printWindow = window.open('', '', 'height=400,width=800');
        printWindow.document.write('<html><head><title>Export Contents</title>');
        printWindow.document.write('</head><style>*{ font-family:sans-serif;}</style><body><h1>Area Information</h1>'
        		
        );
        printWindow.document.write(divContents);
        printWindow.document.write('</body></html>');
        printWindow.document.close();
        printWindow.print();
	}
	
	// calculate the median
	function calculateMedian(arr){
	  arr.sort(function(a, b) {
	    return a - b;
	  });
	  var i = arr.length/2;
	  i % 1 === 0 ? med = arr[i-1] : med = (arr[Math.floor(i)-1] + arr[Math.floor(i)])/2;
	  return med;
	}
	
	function getVersion() {
	    return $.ajax({
			type : 'GET',
			url : 'Houses',
			data : {
				oper : "getVersion"
			},
			dataType : 'text',
			success : function(data) {
				
			},
			error : function(e) {
				console.log("Error: " + JSON.stringify(e));
			}
		});
	}

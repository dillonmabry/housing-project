<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>ITCS 3162 - Group H2</title>
<!-- icon -->
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/scrollingnav.css" rel="stylesheet">

<!-- Custom CSS -->
<!-- <link href="css/main-theme.min.css" rel="stylesheet"> -->
<link href="css/second-theme.min.css" rel="stylesheet">
<link href="css/toastr.min.css" rel="stylesheet">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<!-- The #page-top ID is part of the scrolling feature - the data-spy and data-target are part of the built-in Bootstrap scrollspy function -->
<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">
	<!-- Navigation -->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="padding-top:10px">
		<div class="container">
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand page-scroll" style="padding-bottom:50px" href="#page-top">
				<img src="img/home-icon.png" height="50px" width="45px"
				 style='color: #fff;filter: brightness(0) invert(1);position:relative;top:-16px'/></a>
				 <a class="navbar-brand page-scroll">Housing Price Estimator</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					<!-- Hidden li included to remove active class from about link when scrolled up past about section -->
					<li class="hidden"><a class="page-scroll" href="#page-top"></a>
					</li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>

	<!-- Intro Section -->
	<section id="intro" class="intro-section">
	    <div class="clearfix">
	        <div class="col-md-12" style="padding: 0">
	            <div id="carousel" class="carousel slide" data-ride="carousel" data-interval="5000">
	                <div class="carousel-inner" role="listbox" style=" width:100%; height: 800px !important;">
	                    <div class="item active">
	                        <img class="first-slide" src="img/house2.jpeg" width="100%" height="auto">
	                    </div>
	                    <div class="item">
	                       <img class="first-slide" src="img/house3.jpeg" width="100%" height="auto">
	                    </div>
	                    <div class="item">
	                        <img class="first-slide" src="img/house1.jpg" width="100%" height="100%">
	                    </div>
	                </div>
	            </div>
	            <div class="main-text hidden-xs">
	                <div class="col-md-12 text-center">
	                   <h1 style="text-shadow: 1px 2px 1px #505050  ; font-weight: 400;">Estimate 
								Housing Prices</h1>
							<h4 style="text-shadow: 1px 1px 1px #505050 ; font-weight: 300;">
								<strong>Usage Instructions:</strong> 
								For buying or selling select your choice, enter your inputs, and click Run Analysis!
							</h4>
							<p>
								<a class="btn btn-primary page-scroll" style="opacity: 0.9;" href="#estimateNow">Start
									Estimating!</a>
							</p>
	                </div>
	            </div>
	        </div>
	    </div>
	    <div class="bottom-divider"></div>
	</section>
	
	<!-- About Section -->
	<div id='estimateNow'></div>
	<section id="estimateSection" class="about-section">
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3"style="padding-top:60px">
				<button class="btn btn-primary sellingBtn" >Zestimate</button>
				<button class="btn btn-default purchaseBtn" id='purchasingBtn'>Purchasing</button>
				<button class="btn btn-default regBtn" id='regBtn'>Estimate</button>
					<h1 class='page-header'>Zestimate Price</h1>
					<form>
						<div class="form-group">
							<label for="address">Street Address: </label> <input type="text"
								class="form-control" id="address" placeholder="Enter address..."
								required>
						</div>
						<div class="form-group">
							<label for="city">City: </label> <input type="text"
								class="form-control" id="city" placeholder="Enter city...">
						</div>
						<div class="form-group">
							<label for="state">State: </label> <select id="state"
								class="form-control">
								<option value="AL">Alabama</option>
								<option value="AK">Alaska</option>
								<option value="AZ">Arizona</option>
								<option value="AR">Arkansas</option>
								<option value="CA">California</option>
								<option value="CO">Colorado</option>
								<option value="CT">Connecticut</option>
								<option value="DE">Delaware</option>
								<option value="DC">District Of Columbia</option>
								<option value="FL">Florida</option>
								<option value="GA">Georgia</option>
								<option value="HI">Hawaii</option>
								<option value="ID">Idaho</option>
								<option value="IL">Illinois</option>
								<option value="IN">Indiana</option>
								<option value="IA">Iowa</option>
								<option value="KS">Kansas</option>
								<option value="KY">Kentucky</option>
								<option value="LA">Louisiana</option>
								<option value="ME">Maine</option>
								<option value="MD">Maryland</option>
								<option value="MA">Massachusetts</option>
								<option value="MI">Michigan</option>
								<option value="MN">Minnesota</option>
								<option value="MS">Mississippi</option>
								<option value="MO">Missouri</option>
								<option value="MT">Montana</option>
								<option value="NE">Nebraska</option>
								<option value="NV">Nevada</option>
								<option value="NH">New Hampshire</option>
								<option value="NJ">New Jersey</option>
								<option value="NM">New Mexico</option>
								<option value="NY">New York</option>
								<option value="NC" selected>North Carolina</option>
								<option value="ND">North Dakota</option>
								<option value="OH">Ohio</option>
								<option value="OK">Oklahoma</option>
								<option value="OR">Oregon</option>
								<option value="PA">Pennsylvania</option>
								<option value="RI">Rhode Island</option>
								<option value="SC">South Carolina</option>
								<option value="SD">South Dakota</option>
								<option value="TN">Tennessee</option>
								<option value="TX">Texas</option>
								<option value="UT">Utah</option>
								<option value="VT">Vermont</option>
								<option value="VA">Virginia</option>
								<option value="WA">Washington</option>
								<option value="WV">West Virginia</option>
								<option value="WI">Wisconsin</option>
								<option value="WY">Wyoming</option>
							</select>
						</div>
					</form>
					<br />
					<button id='submitBtn' type="submit" class="btn btn-primary"
						data-toggle="modal" data-target="#loadingId">Run Analysis</button>
<!-- 					<a  class="btn btn-primary page-scroll moreFacts" -->
<!-- 						href="#factsInfo" style="display: none;">See More Facts</a> -->
					<button  class="btn btn-primary moreFacts"
						data-toggle="modal" data-target="#moreFactsModal" style="display: none;">View Results</button>
					<div>
						<br />
						<h4>
							<span class="label label-default warnLabel"
								style="display: none;">Please enter all required preferences!</span>
						</h4>
					</div>
					<hr />
					<div id='estimatedValue' style="display: none;">
						<h3>Your Estimated Value</h3>
						<div id='estimatePrice' style="color: green;"></div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- Buy section -->
	<section id="buySection" class="about-section" style="display:none;">
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3" style="padding-top:60px">
				<button class="btn btn-primary sellingBtn" >Zestimate</button>
				<button class="btn btn-primary purchaseBtn" id='purchasingBtn'>Purchasing</button>
				<button class="btn btn-primary regBtn" id='regBtn'>Estimate</button>
					<h1 class='page-header'>Find Homes</h1>
					<form>
						<div class="form-group">
							<label for="city">City: </label> <input type="text"
								class="form-control" id="cityPurchase" placeholder="Enter city...">
						</div>
						<div class="form-group">
							<label for="state">State: </label> <select id="statePurchase"
								class="form-control">
								<option value="AL">Alabama</option>
								<option value="AK">Alaska</option>
								<option value="AZ">Arizona</option>
								<option value="AR">Arkansas</option>
								<option value="CA">California</option>
								<option value="CO">Colorado</option>
								<option value="CT">Connecticut</option>
								<option value="DE">Delaware</option>
								<option value="DC">District Of Columbia</option>
								<option value="FL">Florida</option>
								<option value="GA">Georgia</option>
								<option value="HI">Hawaii</option>
								<option value="ID">Idaho</option>
								<option value="IL">Illinois</option>
								<option value="IN">Indiana</option>
								<option value="IA">Iowa</option>
								<option value="KS">Kansas</option>
								<option value="KY">Kentucky</option>
								<option value="LA">Louisiana</option>
								<option value="ME">Maine</option>
								<option value="MD">Maryland</option>
								<option value="MA">Massachusetts</option>
								<option value="MI">Michigan</option>
								<option value="MN">Minnesota</option>
								<option value="MS">Mississippi</option>
								<option value="MO">Missouri</option>
								<option value="MT">Montana</option>
								<option value="NE">Nebraska</option>
								<option value="NV">Nevada</option>
								<option value="NH">New Hampshire</option>
								<option value="NJ">New Jersey</option>
								<option value="NM">New Mexico</option>
								<option value="NY">New York</option>
								<option value="NC" selected>North Carolina</option>
								<option value="ND">North Dakota</option>
								<option value="OH">Ohio</option>
								<option value="OK">Oklahoma</option>
								<option value="OR">Oregon</option>
								<option value="PA">Pennsylvania</option>
								<option value="RI">Rhode Island</option>
								<option value="SC">South Carolina</option>
								<option value="SD">South Dakota</option>
								<option value="TN">Tennessee</option>
								<option value="TX">Texas</option>
								<option value="UT">Utah</option>
								<option value="VT">Vermont</option>
								<option value="VA">Virginia</option>
								<option value="WA">Washington</option>
								<option value="WV">West Virginia</option>
								<option value="WI">Wisconsin</option>
								<option value="WY">Wyoming</option>
							</select>
						</div>
						<div class="form-group">
							<label for="beds">Number of Bedrooms: </label>
							<input id="bedsInputForm"  style="text-align: center;"class="form-control quantity" type="number" min="1" max="10" step="1" value="3" >
						</div>
						<div class="form-group">
							<label for="baths">Number of Bathrooms: </label>
							<input id="bathsInputForm" style="text-align: center;" class="form-control quantity" type="number" min="1" max="10" step="1" value="2">
						</div>
						<div class="form-group">
							<div id="priceInputForm" data-role="rangeslider">
						        <label for="price-min">Min Price: </label>
						        <output for="price-min" id='minPrice'></output>
						        <input type="range" name="price-min" id="price-min" value="50000" step="10000" min="0" max="100000" oninput="outputUpdate(value) ">
						        <label for="price-max">Max Price: </label><div id='maxPrice'></div>
						        <output for="price-max" id='maxPrice'></output>
						        <input type="range" name="price-max" id="price-max" value="500000"step="10000"  min="100000" max="5000000" oninput=" outputUpdateMax(value)">
						      </div>
						</div>
					</form>
					<br />
					<button id='submitPurchase' type="submit" class="btn btn-primary"
						data-toggle="modal" data-target="#loadingId">Run Analysis</button>
<!-- 					<a class="btn btn-primary page-scroll moreFacts" -->
<!-- 						href="#factsInfo" style="display: none;">See More Facts</a> -->
					<button  class="btn btn-primary moreFacts"
						data-toggle="modal" data-target="#moreFactsModal" style="display: none;">View Results</button>
					<div>
						<br />
						<h4>
							<span class="label label-default warnLabel"
								style="display: none;">Please enter all required preferences!</span>
						</h4>
					</div>
					<hr />
					<div id='estimatedValue' style="display: none;">
						<h3>Your Estimated Value</h3>
						<div id='estimatePrice' style="color: green;"></div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- Buy section -->
	<section id="estimatePriceReg" class="about-section" style="display:none;">
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3" style="padding-top:60px">
				<button class="btn btn-primary sellingBtn">Zestimate</button>
				<button class="btn btn-primary purchaseBtn" id='purchasingBtn'>Purchasing</button>
				<button class="btn btn-primary regBtn" id='regBtn'>Estimate</button>
					<h1 class='page-header'>Estimate Price</h1>
					<form>
						<div class="form-group">
							<label for="city">City: </label> <input type="text"
								class="form-control" id="cityReg" placeholder="Enter city...">
						</div>
						<div class="form-group">
							<label for="state">State: </label> <select id="stateReg"
								class="form-control">
								<option value="AL">Alabama</option>
								<option value="AK">Alaska</option>
								<option value="AZ">Arizona</option>
								<option value="AR">Arkansas</option>
								<option value="CA">California</option>
								<option value="CO">Colorado</option>
								<option value="CT">Connecticut</option>
								<option value="DE">Delaware</option>
								<option value="DC">District Of Columbia</option>
								<option value="FL">Florida</option>
								<option value="GA">Georgia</option>
								<option value="HI">Hawaii</option>
								<option value="ID">Idaho</option>
								<option value="IL">Illinois</option>
								<option value="IN">Indiana</option>
								<option value="IA">Iowa</option>
								<option value="KS">Kansas</option>
								<option value="KY">Kentucky</option>
								<option value="LA">Louisiana</option>
								<option value="ME">Maine</option>
								<option value="MD">Maryland</option>
								<option value="MA">Massachusetts</option>
								<option value="MI">Michigan</option>
								<option value="MN">Minnesota</option>
								<option value="MS">Mississippi</option>
								<option value="MO">Missouri</option>
								<option value="MT">Montana</option>
								<option value="NE">Nebraska</option>
								<option value="NV">Nevada</option>
								<option value="NH">New Hampshire</option>
								<option value="NJ">New Jersey</option>
								<option value="NM">New Mexico</option>
								<option value="NY">New York</option>
								<option value="NC" selected>North Carolina</option>
								<option value="ND">North Dakota</option>
								<option value="OH">Ohio</option>
								<option value="OK">Oklahoma</option>
								<option value="OR">Oregon</option>
								<option value="PA">Pennsylvania</option>
								<option value="RI">Rhode Island</option>
								<option value="SC">South Carolina</option>
								<option value="SD">South Dakota</option>
								<option value="TN">Tennessee</option>
								<option value="TX">Texas</option>
								<option value="UT">Utah</option>
								<option value="VT">Vermont</option>
								<option value="VA">Virginia</option>
								<option value="WA">Washington</option>
								<option value="WV">West Virginia</option>
								<option value="WI">Wisconsin</option>
								<option value="WY">Wyoming</option>
							</select>
						</div>
						<div class="form-group">
							<label for="beds">Number of Bedrooms: </label>
							<input id="bedsReg"  style="text-align: center;"class="form-control quantity" type="number" min="1" max="10" step="1" value="3" >
						</div>
						<div class="form-group">
							<label for="baths">Number of Bathrooms: </label>
							<input id="bathsReg" style="text-align: center;" class="form-control quantity" type="number" min="1" max="10" step="1" value="2">
						</div>
						<div class="form-group">
							<label for="sqft">Square Footage: </label>
							<input id="sqftReg" style="text-align: center;" class="form-control quantity" type="number" min="500" max="20000" step="250" value="2000">
						</div>
					</form>
					<br />
					<button id='submitReg' type="submit" class="btn btn-primary"
						data-toggle="modal" data-target="#loadingId">Run Analysis</button>
					<a id="downloadBtn" style="display:none" href="FileModule"><button class="btn btn-primary">Download Results</button></a>
					<button  class="btn btn-primary moreFacts"
						data-toggle="modal" data-target="#moreFactsModal" style="display: none;">View Results</button>
					<div>
						<br />
						<h4>
							<span class="label label-default warnLabel"
								style="display: none;">Please enter all required preferences!</span>
						</h4>
					</div>
					<div class="estimatedValue" style="display: none;">
						<hr />
						<h3>Your Estimated Value</h3>
						<div class="estimatePrice" style="color: green;"></div>
					</div>
					
					<!-- TODO: Add google maps -->
					<hr/>
					<div id="homesMaps" style="display:none">
						<h3>Homes for Best Value</h3>
						<div id="map" style="width:100%; height: 500px;"></div> 
					</div>
				</div>
			</div>
		</div>
	</section>

	<br/>

	<!-- Loading Spinner Modal -->
	<div class="modal fade" id="loadingId" tabindex="-1" role="dialog"
		aria-labelledby="loadingIndicator" aria-hidden="true" data-backdrop="static" data-keyboard="false"> 
		<div class="modal-dialog" role="document">
			<i class="fa fa-spinner fa-spin" style="font-size: 150px;"></i>
		</div>
	</div>
	<!-- More Facts Modal -->
	<div class="modal fade estimateModal" id="moreFactsModal" tabindex="-1"
		role="dialog" aria-labelledby="moreFactsModal" aria-hidden="true">
		<div class="modal-dialog estimateModalDialog" role="document"
			style="width: 95%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h1 class="modal-title" style="text-align: center">Other Facts</h1>
				</div>
				<div class="modal-body">
					<div id='factsInfo'>
						<div class="col-md-6 col-md-offset-3">
							<button id='exportCSV' class="btn btn-primary exportCSV">
								<span class="glyphicon glyphicon-export"></span> Export All CSV
							</button>
							<button id='exportExcel' class="btn btn-primary exportExcel" style="display:none">
								<span class="glyphicon glyphicon-th-list"></span> Export All Excel
							</button>
							<button id='exportPDF' class="btn btn-primary exportPDF">
								<span class="glyphicon glyphicon-open-file"></span> Export Report
							</button>
						</div>
						<div id="statsPanel">
							<div class="row valueTime">
								<div id="valueTimeDiv" class="col-md-6">
									<h3>Property Value Over Time</h3>
									<div id="estimateChart"></div>
								</div>
								<!-- 			<div id="regionValue" class="col-md-6 "> -->
								<!-- 				<h3>Region Value Over Time</h3> -->
								<!-- 				<div id="estimateRegion"></div> -->
								<!-- 			</div> -->
								<div id='distChartDiv' class='col-md-6'>
									<h3>Housing Price Distribution</h3>
									<div id='distChart'></div>
								</div>
							</div>
							<div class="row areaRank">
								<div class="col-md-6">
									<h3>Top School Rankings</h3>
									<div id="areaRanks">
										<div id="areaRanksDiv" class="well" style="width: 100%;">
											<table id="areaRanksTable" class="table">
												<thead>
													<tr>
														<th>School Rank</th>
														<th>School Name</th>
														<th>Grade Level</th>
														<th>No. of Students</th>
													</tr>
												</thead>
												<tbody id='areaRanksBody'>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="col-md-6 homesSale">
									<h3>Search Results</h3>
									<div id="homesSale">
										<div id="homesSaleDiv" class="well" style="width: 100%;">
											<table id="homesSaleTable" class="table">
												<thead>
													<tr>
														<th>Sold Value</th>
														<th>No. Beds</th>
														<th>No. Baths</th>
														<th>URL</th>
													</tr>
												</thead>
												<tbody id='homesSaleBody'>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<hr />
							<div class="row neighborStats">
								<div class="col-md-6">
									<h3>Neighborhood Statistics</h3>
									<div id="areaStats">
										<h4 id='areaAggregate' class="alert alert-info"
											style="background-color: #2780e3"></h4>
										<div id="areaStatsTableDiv" class="well" style="width: 100%;">
											<table id="areaStatsTable" class="table">
												<thead>
													<tr>
														<th>Neighborhood/Zip</th>
														<th>Average Home Value</th>
													</tr>
												</thead>
												<tbody id='areaStatsBody'>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="col-md-6 crimeArea">
									<h3>Crime/Safety Statistics</h3>
									<div id="crimeArea">
										<h4 id='crimeReport' class="alert alert-info"
											style="background-color: #2780e3"></h4>
										<div id="crimeAreaDiv" class="well" style="width: 100%;">
											<table id="crimeTable" class="table">
												<thead>
													<tr>
														<th>Safest Areas</th>
													</tr>
												</thead>
												<tbody id='crimeBody'>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="col-md-6 recentSold">
									<h3>Top Recently Sold Homes</h3>
									<div id="recentSold">
										<div id="recentSoldTableDiv" class="well" style="width: 100%;">
											<table id="recentSoldTable" class="table">
												<thead>
													<tr>
														<th>Sold Value</th>
														<th>No. Beds</th>
														<th>No. Baths</th>
													</tr>
												</thead>
												<tbody id='recentBody'>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 allHomes" style="display:none">
								<hr />
									<h3>Most Recent Homes for Sale</h3>
									<div id="allHomes">
										<div id="allHomesDiv" class="well" style="width: 100%;">
											<table id="allHomesTable" class="table">
												<thead>
													<tr>
														<th>Sold Value</th>
														<th>No. Beds</th>
														<th>No. Baths</th>
														<th>Address</th>
														<th>URL</th>
													</tr>
												</thead>
												<tbody id='allHomesBody'>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<div id="footer" class="navbar-fixed-bottom">
<!--       <div class="container" style="margin:10px;"> -->
<!--       	<p style="color:#fff; text-align:center">Housing Price Estimator 1.0.0 | Calculations assisted using <a target="_blank" href="http://zillow.com">Zillow</a></p> -->
<!--       </div> -->
    </div>
	<!-- Scripts and other -->
	<!-- jQuery -->
	<script src="js/jquery-3.1.0.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCx_I_velDvIEDsCpNgjUwIMrUsJMyYSGI&callback=map"></script>
	<!-- Scrolling Nav JavaScript -->
	<script src="js/jquery.easing.min.js"></script>
	<script src="js/scrolling-nav.js"></script>
	<script src="js/toastr.min.js"></script>
	<script src="js/raphael.min.js"></script>
	<script src="js/morris.min.js"></script>
	<script src="js/papaparse.min.js"></script>
	<script src="js/table2excel.js"></script>
	<script src="js/table2csv.js"></script>
	<script src="js/common.js"></script>

</body>

</html>

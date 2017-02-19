<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>ITCS 3162 - Group H2</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/scrollingnav.css" rel="stylesheet">

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
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand page-scroll" href="#page-top">Housing
					Price Estimator</a>
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
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<h1>Estimate Housing Prices!</h1>
					<h4>
						<strong>Usage Instructions:</strong> To estimate a price, enter
						the corresponding inputs and click the Run Analysis button!
					</h4>
					<a class="btn btn-primary page-scroll" href="#estimateSection">Start
						Estimating!</a>
				</div>
			</div>
		</div>
	</section>

	<!-- About Section -->
	<section id="estimateSection" class="about-section">
		<!--     	<img src="img/ajax-loader.gif" id="ajaxImg" /> -->
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<h1 class='page-header'>Estimate Price</h1>
					<form>
						<div class="form-group">
							<label for="address">Street Address: </label> <input type="text"
								class="form-control" id="address" placeholder="Enter address..." required>
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
								<option value="NC">North Carolina</option>
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
					<button id='submitBtn' type="submit" class="btn btn-primary"
						data-toggle="modal" data-target="#loadingId">Run Analysis</button>
					<div>
						<br/>
						<h4><span id='warnLabel' class="label label-default" style="display:none;">Please enter an address, city, and state!</span></h4>
					</div>
					<hr />
					<h3>Your Estimated Value</h3>
					<div class="alert alert-info" id='alertInfo'>
						<h2>???</h2>
					</div>
					<div class="alert alert-success" id='estimatePrice'
						style="display: none;"></div>
				</div>
			</div>
		</div>
	</section>

	<section id='factsInfo' class='services-section' style="display: none;">
		<div class="col-md-6 col-md-offset-3">
			<h1 class='page-header'>Other Facts</h1>
		</div>
		<div class="col-md-6" class="well">
			<h3>Value Over Time</h3>
			<div id="estimateChart"></div>
		</div>
		<div class="col-md-6" class="well">
			<h3>Neighborhood Statistics</h3>
			<div id="areaStats"></div>
		</div>
	</section>

	<!-- Modal -->
	<div class="modal fade" id="loadingId" tabindex="-1" role="dialog"
		aria-labelledby="loadingIndicator" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<i class="fa fa-spinner fa-spin" style="font-size: 150px;"></i>
		</div>
	</div>

	<!-- jQuery -->
	<script src="js/jquery-3.1.0.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

	<!-- Scrolling Nav JavaScript -->
	<script src="js/jquery.easing.min.js"></script>
	<script src="js/scrolling-nav.js"></script>
	<script src="js/common.js"></script>

</body>

</html>
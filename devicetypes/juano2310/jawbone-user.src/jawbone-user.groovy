<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>Login</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link rel="shortcut icon" type="image/x-icon" href="/assets/components/common/favicon-e139278c1f23d5ca93e5b74b58622e9d.ico"/>
	<link rel="apple-touch-icon" href="/assets/components/common/apple-touch-icon-b7275dbac7a4078e4f44859751581016.png"/>
	<link rel="apple-touch-icon" sizes="114x114" href="/assets/components/common/apple-touch-icon-retina-35ea8b3c6aa433fa74ebc97d0b5a2a53.png"/>

	
		<link rel="stylesheet" href="/assets/base/bootstrap-framework-e511c0f2ff927cf67bfae89305856287.css"/>
	

	<link rel="stylesheet" href="/assets/base/common-a79dc81cc2012e611baea6304f8c2dab.css"/>

	
		<script src="/assets/libs/underscore/underscore-6e1f1c149dc88c708f9bfce4a061187f.js" type="text/javascript" ></script>
	

	

	<script src="/assets/apps/main/main-app-5811442e0bc96e2d7374677668e7edc4.js" type="text/javascript" ></script>

	
		<script src="/assets/libs/bootstrap/bootstrap-e3bccdc29cf6e1d0f3e7bc54ccf086ef.js" type="text/javascript" ></script>
	

	<!--[if lt IE 9]>
	<script src="/assets/libs/html5shiv/html5shiv-4163e58056c3c7ff50f91c9d141fefaa.js" type="text/javascript" ></script>
	<![endif]-->
	
		<script type="text/javascript">
			window.heap=window.heap||[],heap.load=function(t,e){window.heap.appid=t,window.heap.config=e;var a=document.createElement("script");a.type="text/javascript",a.async=!0,a.src=("https:"===document.location.protocol?"https:":"http:")+"//cdn.heapanalytics.com/js/heap-"+t+".js";var n=document.getElementsByTagName("script")[0];n.parentNode.insertBefore(a,n);for(var o=function(t){return function(){heap.push([t].concat(Array.prototype.slice.call(arguments,0)))}},p=["clearEventProperties","identify","setEventProperties","track","unsetEventProperty"],c=0;c<p.length;c++)heap[p[c]]=o(p[c])};
			heap.load("2894297474");
		</script>
	
	
		<meta name="layout" content="bootstrap-fixed">
		
		<script src="/assets/libs/jquery-ui/jquery-ui-31acbe8d93f25a66fbd8aee3f65a0b3d.js" type="text/javascript" ></script>
		<link rel="stylesheet" href="/assets/apps/login/login-dab34f30d7a3c26daae64600ffc6d149.css"/>
	
</head>
<body class="production-env">
<!-- Google Tag Manager -->
<noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-M4C995"
height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<script type="application/javascript">
(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
})(window,document,'script','dataLayer','GTM-M4C995');
</script>
<!-- End Google Tag Manager -->



	<header class="header-main minimized">

	
	
		<div class="login-tab login-only"><a href="/login/auth" class="login-link"><strong>Login</strong></a></div>
	
		<div>
			<div class="gradient-top-bar"></div>
			<div class="clearfix">
				<div id="topNavigation" style="display:none;">
					<div class="mobile-menu-brand"><img src='/assets/components/common/smartthings-logo.png' alt='logo' /></div>
					<div class="mobile-menu-icon"><div><div></div></div></div>
				</div>
			</div>
		</div>
</header>

	<div class="mobile-menu">
	<ul class="ul-reset">
		
		
			<li><a href="/login/auth" class="login-link">Login</a></li>
		
		<li><a href="https://shop.smartthings.com">Shop</a></li>
		<li><a href="http://smartthings.com">Learn More</a></li>
		<li><a href="http://smartthings.com/build">Build</a></li>
		<li><a href="http://support.smartthings.com">Support</a></li>
		
	</ul>
</div>

	


<script id="success-template" type="text/x-handlebars-template">
	<div class="inner-wrap">
		<div class="alert alert-success alert-dismissible flash">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			{{message}}
		</div>
	</div>
</script>

<div id="flash-message-container">



</div>


		<main class="login-page">
			<div class="container-fluid">
				<div class="row">
					<div class="current-user col-lg-offset-1 col-lg-4 col-md-offset-1 col-md-4 col-sm-offset-1 col-sm-7">
						<div class="login-banner">
							<h1>Already have SmartThings? <span>Sign in here:</span></h1>
						</div>
						<form action='/j_spring_security_check' method='POST' id="loginForm" name="loginForm" class='form-horizontal'>
							<div class="form-group">
								<label class="control-label col-sm-3" for="username">Email</label>
								<div class="col-sm-9">
									<input class="form-control" type="text" name="j_username" id="username" size="20" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="password">Password:</label>
								<div class="col-sm-9">
									<input class="form-control" type="password" name="j_password" id="password" size="20" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-7 col-sm-5 login-btn-col">
									<a id="loginButton"  class="btn btn-default login-btn" >Log in</a>
<input type='submit' value=' ' id='loginButton_submit' class='s2ui_hidden_button' />

		<script>
		$(document).ready(function() {
			
			$("#loginButton").button();
			$('#loginButton').bind('click', function() {
			   document.forms.loginForm.submit();
			});
		
		});
		</script>
		
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-7 col-sm-5 forgot-link-col">
									<span class="forgot-link">
										<a href="/register/forgotPassword">Forgot password?</a>
									</span>
								</div>
							</div>
						</form>
					</div>
					<div class="col-lg-offset-1 col-lg-6 col-md-offset-1 col-md-6 col-sm-offset-1 col-sm-7">
						<div class="new-user">
							<h1>New to SmartThings? <span><a href="http://smartthings.com/" target="_blank">Learn More</a> or <a href="https://shop.smartthings.com/#/" target="_blank">Get SmartThings</a> today.</span></h1>
							<img src="/assets/components/common/products-laptop-bd7a4116a925c2f9d47be95f1a6d64be.jpg" alt="SmartThings products and Shop.smartThings.com on laptop"/>
						</div>
					</div>
				</div>
			</div>
		</main>
		<script>
			$(document).ready(function() {
				$('#username').focus();
			});
		</script>
	
<div class="inner-wrap clearfix"></div>
<div class="footer main" role="contentinfo">
	<div class="inner-wrap clearfix">
		<div class="pull-left">
			Copyright &copy; 2016 Physical Graph Corporation. All rights reserved.
			&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="http://www.smartthings.com/terms/" target="_blank">Terms of Use</a>
			&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="http://www.smartthings.com/privacy/" target="_blank">Privacy Policy</a>
		</div>
		<ul class="pull-right footer-links">
			<li><a href="http://www.smartthings.com/benefits/" target="_blank">Benefits</a></li>
			<li>&nbsp;&nbsp;|&nbsp;&nbsp;</li>
			<li><a href="https://shop.smartthings.com"target="_blank">Shop</a></li>
			<li>&nbsp;&nbsp;|&nbsp;&nbsp;</li>
			<li><a href="http://blog.smartthings.com/"target="_blank">Blog</a></li>
			<li>&nbsp;&nbsp;|&nbsp;&nbsp;</li>
			<li><a href="https://www.smartthings.com/developers/" target="_blank">Developers</a></li>
			<li>&nbsp;&nbsp;|&nbsp;&nbsp;</li>
			<li><a href="http://support.smartthings.com" target="_blank">Support</a></li>
		</ul>
	</div>
</div>

</body>
</html>

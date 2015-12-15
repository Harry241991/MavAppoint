<%@include file="templates/header.jsp"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>

<div class="container">
	<form action="#" method="post"
		onsubmit="return validateConfirmPassword()">

		<script language="javascript">
			var ieDOM = false, nsDOM = false;
			var stdDOM = document.getElementById;
			function initMethod() {
				//Determine the browser support for the DOM
				if (!stdDOM) {
					ieDOM = document.all;

					if (!ieDOM) {
						nsDOM = ((navigator.appName.indexOf('Netscape') != -1) && (parseInt(navigator.appVersion) == 4));
					}
				}

				passwordChanged();
			}

			function getObject(objectId) {
				if (stdDOM)
					return document.getElementById(objectId);
				if (ieDOM)
					return document.all[objectId];
				if (nsDOM)
					return document.layers[objectId];
			}

			function getObjectStyle(objectId) {
				if (nsDOM)
					return getObject(objectId);

				var obj = getObject(objectId);
				return obj.style;
			}

			function showDefault(objectId) {
				showCell(objectId, "#E2E2E2", "#E2E2E2");
			}

			function showCell(objectId, foreColor, backColor) {
				getObjectStyle(objectId).color = foreColor;
				getObjectStyle(objectId).backgroundColor = backColor;
			}

			function showWeak() {
				showCell("pwWeak", "Black", "#FF6666");

				showDefault("pwMedium");
				showDefault("pwStrong");
			}

			function showMedium() {
				showCell("pwWeak", "#FFCC66", "#FFCC66");
				showCell("pwMedium", "Black", "#FFCC66");

				showDefault("pwStrong");
			}

			function showStrong() {
				showCell("pwWeak", "#80FF80", "#80FF80");
				showCell("pwMedium", "#80FF80", "#80FF80");
				showCell("pwStrong", "Black", "#80FF80");
			}

			function showUndetermined() {
				showDefault("pwWeak");
				showDefault("pwMedium");
				showDefault("pwStrong");
			}

			function passwordChanged() {
				var strongRegex = new RegExp(
						"^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$",
						"g");
				var mediumRegex = new RegExp(
						"^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$",
						"g");
				var enoughRegex = new RegExp("(?=.{6,}).*", "g");

				var pwd = getObject("password").value;
				if (false == enoughRegex.test(pwd)) {
					showUndetermined();
				} else if (strongRegex.test(pwd)) {
					showStrong();
				} else if (mediumRegex.test(pwd)) {
					showMedium();
				} else {
					showWeak();
				}
			}

			function validateConfirmPassword() {
				if (password.value != repeatPassword.value) {
					alert("Password Doesnt Match");
					repeatPassword.focus();
					return false;

				}
			}
		</script>
		
		<div class="row">
			<div class="col-md-4 col-lg-4">
				<div class="form-group">
					<label for="userid">User ID</label> <input type="text"
						class="form-control" id=userid
						placeholder="1000xxxxxx or 6000xxxxxx" name="userid"
						required="required"> <label for="emailAddress">Email
						Address</label> <input type="email" class="form-control" id=emailAddress
						name="emailAddress" placeholder="firstname.lastname@mavs.uta.edu"
						required="required"> <label for="password">Password</label>
					<input type="password" class="form-control" id=password
						name="password" onkeyup="return passwordChanged();"
						required="required">
					<TABLE
						style="BORDER-RIGHT: black thin solid; BORDER-TOP: black thin solid; FONT-SIZE: 75%; BORDER-LEFT: black thin solid; BORDER-BOTTOM: black thin solid"
						cellSpacing="0" cellPadding="0" width="100%">

						<TR>

							<TD id="pwWeak" style="BORDER-RIGHT: black thin solid"
								align="center" width="34%" title="Has at least six characters">Weak</TD>

							<TD id="pwMedium" style="BORDER-RIGHT: black thin solid"
								align="center" width="33%"
								title="Has a mix of numbers, lower & upper case characters.">Medium</TD>

							<TD id="pwStrong" align="center" width="33%"
								title="Has numbers, special characters, lower & upper case characters.">Strong</TD>

						</TR>

					</TABLE>
					<label for="repeatPassword">Repeat Password</label> <input
						type="password" class="form-control" id=repeatPassword name="repeatPassword"
						required="required">
				</div>
			</div>
			
			
			
		</div>
		
		<%
         ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LcHIBITAAAAAOKejWrKR2lKpuZW5RGk3wkmqORl", "6LcHIBITAAAAAKWdOa7_VYcPcGe2quNiUnBy2z--", false);
         out.print(c.createRecaptchaHtml(null, null));
       %>

		<button type="submit" class="btn btn-primary">Submit</button>
		</p>
	</form>

	<%@include file="templates/footer.jsp"%>
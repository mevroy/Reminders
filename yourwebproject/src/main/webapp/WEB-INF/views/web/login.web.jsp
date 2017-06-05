<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="jumbotron">
	<div>
		<div>
			<form name="loginUserForm2" action="loginUser" method="post"
				id="loginUserForm2">
				<fieldset>


					<h2>User Login</h2>
					<br />



					<div>
						<div class="form-group" id="usernameCtl">
							<label class="control-label" for="usernameLabel">Username
								</label>

							<div class="controls">
								<input type="text" name="username" class="form-control"
									id="username" placeholder="Enter Username" />
							</div>
						</div>
						<div class="form-group" id="passwordCtl">
							<label class="control-label" for="passwordLabel">Password</label>

							<div class="controls">
								<input type="password" name="password" class="form-control"
									id="password" placeholder="Enter Password" />
							</div>
						</div>
						

					</div>
				</fieldset>
				<div class="form-group">
				<input class="btn btn-primary btn-large" type="submit"
					value="SUBMIT" />
				<a href="${pageContext.request.contextPath}/${sessionScope.groupCode}/" class="btn btn-large btn-default">CANCEL</a>
				</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(
			function() {
		        $("#loginUserForm2").validate({
		            rules:{
		            	username:{
		                    required:true
		                },
		                password:{
		                    required:true
		                }
		            },
							errorClass : "control-group has-error",
							validClass : "control-group has-success",
							errorElement : "span",
							highlight : function(element, errorClass,
									validClass) {
								if (element.type === 'radio') {
									this.findByName(element.name).parent("div")
											.parent("div").removeClass(
													validClass).addClass(
													errorClass);
								} else {
									$(element).parent("div").parent("div")
											.removeClass(validClass).addClass(
													errorClass);
								}
							},
							unhighlight : function(element, errorClass,
									validClass) {
								if (element.type === 'radio') {
									this.findByName(element.name).parent("div")
											.parent("div").removeClass(
													errorClass).addClass(
													validClass);
								} else {
									$(element).parent("div").parent("div")
											.removeClass(errorClass).addClass(
													validClass);
								}
							}
						})

			});

</script>

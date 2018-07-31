<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="jumbotron">

	<div>
		<div>

			<c:choose>
				<c:when test="${sessionScope.groupName ne null}">
					<h2>
						<c:out value="${sessionScope.groupName}" />

					</h2>
					<br />
					<p>
						Welcome to
						<c:out value="${sessionScope.groupName}" />
						portal.
					</p>
					<p>
						If you are interested to join
						<c:out value="${sessionScope.groupName}" />
						, please feel free to register your interest and someone from our
						team will be in touch with you.
					</p>
					
					
					<a href="registerInterest?interestType=Join Group"  class="btn btn-primary btn-lg" >REGISTER INTEREST</a>&nbsp;<c:if test="${sessionScope.user eq null || empty sessionScope.user}" ><a href="login" class="btn btn-primary btn-lg" >LOGIN</a> </c:if>
				<!-- 	<button type="button" id="myButton" data-loading-text="<span class='spinner'><i  class='icon-spin icon-repeat icon-play-circle'></i></span> Loading.." class="btn btn-primary btn-large has-spinner"  >
  Loading state
</button>
  <a class="btn btn-large btn-primary has-spinner" id="refresh">
    <span class="spinner"><i class="icon-spin icon-refresh"></i></span>
    Foo
  </a>  -->
					<!--     <p>Always forget to wish friends on their birthdays or anniversaries? No worries, we got your back</p>
            <br/>
            <p>Features:</p>
            <ul style="font-size:14px;line-height:20px;">
                <li>Add your friends birthdays and anniversaries.</li>
                <li>Set up auto-email wishing feature to wish your friends on their special day.</li>
                <li>Set up daily personal reminders via email.</li>
                <li>Features to add custom wishing templates</li>
            </ul>  -->
					<br />
					<!-- a class="btn btn-large btn-primary" href="userRegistration">Register
						Now!</a> 
						<a tabindex="0" class="btn btn-lg btn-danger" role="button" data-toggle="popover" data-animation="true" data-trigger="focus" title="EMAIL ACTIVITY" data-content="Hi" data-emailID="bf26021c-6296-4cfc-b166-7d8bfcd1017d">EMAIL ACTIVITY</a>
			 -->	</c:when>
				
				<c:otherwise>
					<h2>Page Not Found!</h2>
					<br />
					<p>Oops! You seem to have landed on an unknown page.</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
<script>
  $('#myButton').on('click', function () {
  	 var $btn = $(this).button('loading')
   $(this).toggleClass('active');
    // business logic...
  //  $btn.button('reset')
  })
</script>
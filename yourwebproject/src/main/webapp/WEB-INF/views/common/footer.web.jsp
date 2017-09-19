<div>
    <h4>Copyright &copy; 2015 - 2017 MRD</h4>
</div>
<script>
var timers = [30000,60000];
var messages = ["Somebody just booked 3 Gatsby Tickets online!", "Somebody just booked 1 Gatsby Ticket online!", "Somebody just booked 4 Gatsby Tickets online!", "Somebody just booked 2 Gatsby Tickets online!","Somebody just booked 1 Gatsby Ticket online!", "Somebody just booked 4 Gatsby Tickets online!", "Somebody just booked 2 Gatsby Tickets online!", "Somebody just booked 3 Gatsby Tickets online!" , "Somebody just booked 1 Gatsby Tickets online!", "Somebody just booked 2 Gatsby Tickets online!"   ];
var x = Math.floor((Math.random() * 10) + 1);
var y = Math.floor((Math.random() * 2) + 1);
var message = messages[x-1];
var timer = timers[y-1];
var messageAudit = " tickets booked in the last 24-48 hours!"
var count = 0;
//setTimeout(function(){$.notify(message,   { position:"bottom right",  clickToHide: true,  autoHide: true, autoHideDelay: 25000 }); }, timer);
$(function() {
	getAuditLogs('/app/MKC/completePayment','POST', function(cnt)
			{
//		setTimeout(function(){$.notify(cnt+messageAudit,   { position:"bottom right",  clickToHide: true,  autoHide: true, autoHideDelay: 9000 }); }, 1);
			});
	    setInterval(function(){getAuditLogs('/app/MKC/completePayment','POST', function(cnt)
				{
	    			if(cnt!==count)
	    			{
	    		//		$.notify(cnt+messageAudit,   { position:"bottom right",  clickToHide: true,  autoHide: true, autoHideDelay: 29000 }); 	
	    				count = cnt;
	    			}
				})}, timer);
});
</script>

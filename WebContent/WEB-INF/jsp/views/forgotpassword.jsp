<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<div class="container">
	<div class="jumbotron">
  		<form action="forgotpassword" method="post">

    <table class="table table-striped custab">
    <thead>
        <tr>
        	<th>Student Id</th>
            <th>Email</th>
			<th class="text-center">Action</th>
        </tr>
    </thead>
            		
		    		<!-- begin processing appointments  -->
		    		
							<tr>
                				<td><input type="text" name="userid" value="" /></td>
								<td><input type="email" name="emailAddress" value="" /></td>

								<td class="text-center"><input type=submit value="Submit"/></td>
							</tr>
					
								
						
					 <!-- end processing advisors -->	 
					</table>
				</form>
				
			</div>
		</div>
		</body>
	</div>
</div>
<%@include file="templates/footer.jsp" %>
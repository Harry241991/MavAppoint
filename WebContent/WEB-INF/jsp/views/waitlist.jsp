<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />

<div class="container">
    <div class="btn-group">
    
	<form action="waitlist" method="post">

    <table class="table table-striped custab">
    <thead>
        <tr>
        	<th>Advisor Name</th>
            <th>Appointment Date</th>
			<th>Advising Type</th>
			<th>Description</th>
			<th>UTA Student ID</th>
            <th>Student Email</th>
            <th class="text-center">Action</th>
        </tr>
    </thead>
            		
		    		<!-- begin processing appointments  -->
		    		
							<tr>
                				<td><input type="text" name="advisor_name" value="" /></td>
								<td><input type="text" name="appointment_date" value="" /></td>
								<td><input type="text" name="type" value="" /></td>
								<td><input type="text" name="description" value="" /></td>
								<td><input type="text" name="student_id" value="" /></td>
								<td><input type="text" name="email" value="" /></td>
								<td class="text-center"><input type=submit value="Submit"/></td>
							</tr>
					
								
						
					 <!-- end processing advisors -->	 
					</table>
				</form>
				
			</div>
		</div>
<%@include file="templates/footer.jsp" %>
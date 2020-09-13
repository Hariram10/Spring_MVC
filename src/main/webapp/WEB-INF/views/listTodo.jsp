
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

Hi ${name}<BR/>
	<div class="container">
		<table class = "table table-striped" >
			<caption>Your Todos are</caption>

			<thead>
				<tr>
					<th>Description</th>
					<th>Date</th>
					<th>Completed</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${todos}" var="todo">
					<tr>
						<td>${todo.description}</td>
						
						<td><fmt:formatDate pattern = "dd/MM/yyyy" value="${todo.targetDate}"/> 
						<td>${todo.done}</td>
						<td><a href="/delete-todo?id=${todo.id}" class="btn btn-danger">Delete</a></td>
						<td><a href="/update-todo?id=${todo.id}" class="btn btn-info">Update</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<a class="btn btn-success" href="/add-todo">Add</a>
		</div>
	</div>
<%@ include file="common/footer.jspf"%>
	
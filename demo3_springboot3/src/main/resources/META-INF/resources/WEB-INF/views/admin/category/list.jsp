<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<a href="/admin/categories/add">Add Category</a>
<table>
	<tr>
		<th>STT</th>
		<th>Images</th>
		<th>Category Name</th>
		<th>Status</th>
		<th>Action</th>
	</tr>
	
	<c:forEach items = "${list}" var="cate" vatStatus="stt">
		<tr>
			<td>${STT.index+1 }</td>
			<td>${cate.images}</td>
			<td>${cate.name}</td>
			<td>${cate.status}</td>
			<td>
				<a href="/admin/categories/edit/${cate.id}">Edit</a>
				<a href="/admin/categories/delete/${cate.id}">Delete</a>
			</td>
		</tr>
			
	</c:forEach>
	<tr>
		
	</tr>


</table>

<%-- <a href="${pageContext.request.contextPath}/admin/category/add">Add Category</a>
<form action="${pageContext.request.contextPath}/admin/category/search" method="get">
    <input type="text" name="keyword" placeholder="Nhập từ khóa tìm kiếm">
    <input type="submit" value="Tìm kiếm">
</form>
<table border="1" width="100%">
	<tr>
		<th>STT</th>
		<th>Images</th>
		<th>CategoryID</th>
		<th>CategoryName</th>
		<th>Status</th>
		<th>Action</th>
	</tr>
	<c:forEach items="${listcate}" var="cate" varStatus="STT">
		<tr>
			<td>${STT.index+1 }</td>
			<td>
				<c:if test = "${cate.images.substring(0,5) != 'https' }">
					<c:url value="/image?fname=${cate.images}" var="imgUrl"></c:url>
				</c:if>
				<c:if test = "${cate.images.substring(0,5) == 'https' }">
					<c:url value="${cate.images }" var="imgUrl"></c:url>
				</c:if>
				<img height="150" width="200" src="${imgUrl}" />			
			</td>			
			<td>${cate.categoryid }</td>
			<td>${cate.categoryname }</td>
			<td>
			<c:if test="${cate.status==1 }">
				<span>Hoạt động</span>
			</c:if>
			<c:if test="${cate.status !=1 }">
				<span>Khóa</span>
			</c:if>
			</td>
			<td><a
				href="<c:url value='/admin/category/edit?categoryid=${cate.categoryid }'/>">Sửa</a>
				| <a
				href="<c:url value='/admin/category/delete?categoryid=${cate.categoryid }'/>">Xóa</a></td>
		</tr>
	</c:forEach>
</table> --%>
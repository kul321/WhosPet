<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/headerUser.jsp" %>
<div id="boardList"  class="text-center">
<h1>유저 게시판 리스트</h1>
<table class="table table-bordered table-striped table-condensed">
	<tr>
		<td><strong>글번호</strong></td>
		<td><strong>게시글 제목</strong></td>
		<td><strong>조회수</strong></td>
		<td><strong>작성자</strong></td>
		<td><strong>작성일</strong></td>
	</tr>
	
	<c:forEach var="board" begin="0" end="${listSize - 1}" items="${list }">
	<tr>
		<td style="width: 9%">${board.B_NO }</td>
		<td style="width: 46%"><a href="#">${board.B_TITLE }</a></td>
		<td style="width: 9%">${board.B_HIT }</td>
		<td style="width: 20%">${board.U_NICK }</td>
		<td style="width: 16%"><fmt:formatDate value="${board.B_WRITE_DATE }" pattern="yyyy/MM/dd"/></td>
	</tr>
	</c:forEach>
</table>
</div>
<%@ include file="/WEB-INF/views/util/boardPaging.jsp" %>
<%@ include file="/WEB-INF/views/layout/footerUser.jsp" %>
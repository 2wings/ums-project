<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="user.create.title" /></title>
</head>
<body>
	<h1>
		<spring:message code="user.create.page.title" />
	</h1>
	<div>
		<form: form action="/users/create" commandName="user" method="POST">
			<div>
				<form:label path=firstName">
					<spring:message code="user.label.firstname" />:</form:label>
				<form:input path="firstName" size="20" />
				<form:errors path="firstName" cssClass="error" element="div" />
			</div>
			<div>
				<form:label path=lastName">
					<spring:message code="user.label.lastName" />:</form:label>
				<form:input path="lastName" size="20" />
				<form:errors path="lastName" cssClass="error" element="div" />
			</div>
			<div>
				<form:label path=username">
					<spring:message code="user.label.username" />:</form:label>
				<form:input path="username" size="20" />
				<form:errors path="username" cssClass="error" element="div" />
			</div>
			<div>
				<form:label path=password">
					<spring:message code="user.label.password" />:</form:label>
				<form:input path="password" size="20" />
				<form:errors path="password" cssClass="error" element="div" />
			</div>
			<div>
				<form:label path=role">
					<spring:message code="user.label.role" />:</form:label>
				<form:select path="role">
					<form:option value="NONE" label="--- Select ---" />
					<form:options items="${roleList}" />
				</form:select>
				<form:errors path="role" cssClass="error" element="div" />

			</div>
		</form:>
	</div>
</body>
</html>
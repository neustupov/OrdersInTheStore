<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <c:if test="${param.error}">
            <div class="error">
                    ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </c:if>
        <c:if test="${not empty param.message}">
            <div class="message">
                <spring:message code="${param.message}"/>
            </div>
        </c:if>
        <br/>
        <p>
            <a class="btn btn-primary mr-2" href="register"><spring:message code="app.register"/> &raquo;</a>
            <button type="submit" class="btn btn-primary mr-2" onclick="setCredentials('seller@yandex.ru', 'seller')">
                <spring:message code="app.enter"/> Seller
            </button>
            <button type="submit" class="btn btn-primary mr-2" onclick="setCredentials('manager@yandex.ru', 'manager')">
                <spring:message code="app.enter"/> Manager
            </button>
            <button type="submit" class="btn btn-primary mr-2" onclick="setCredentials('admin@yandex.ru', 'admin')">
                <spring:message code="app.enter"/> Admin
            </button>
        </p>
        <br/>
        <div class="container">
            <div class="lead">
                &nbsp;&nbsp;&nbsp;<a href="https://github.com/neustupov/OrdersInTheStore">Java Enterprise проект</a> с
                регистрацией/авторизацией и интерфейсом на основе ролей (SELLER, MANAGER, ADMIN).
            </div>
            <br/>
            <div class="lead">
                Продавец (SELLER) может добавлять запрос цены (PriceRequest) на определённый вид товара (Product),
                добавлять клиентов (Client), добавлять новые товары (Type, Brand, Model).
            </div>
            <br/>
            <div class="lead">
                После обработки менеджером (MANAGER) запроса, продавец может составить заказ на один или несколько
                товаров под определённого клиента. После добавления нового заказа продавец может распечатать бланк
                заказа в формате PDF.
            </div>
            <br/>
            <div class="lead">
                Менеджер и Администратор могут редактировать и удалять запросы цен, товары, клиентов и заказы.
                Администратор может создавать/редактировать/удалять дополнительных пользователей, а пользователи -
                управлять своим профилем.
            </div>
            <br/>
            <div class="lead">
                Приложение работает через UI (по AJAX) и по REST интерфейсу с базовой авторизацией.
                Весь REST интерфейс покрывается JUnit тестами, используя Spring MVC Test и Spring Security Test.
            </div>
        </div>
    </div>
    <br />
    <div class="container">
        <div class="row">
            <div class="col.12">
                <img src="resources/images/schema_order_actual_original.png" class="img-fluid">
            </div>
        </div>
    </div>
    <br />
    <div class="lead text-center">
        <p>Стек технологий: <a href="http://projects.spring.io/spring-security/">Spring Security</a>,
            <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring
                MVC</a>,
            <a href="http://projects.spring.io/spring-data-jpa/">Spring Data JPA</a>,
            <a href="http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security">Spring Security
                Test</a>,
            <a href="http://hibernate.org/orm/">Hibernate ORM</a>,
            <a href="http://hibernate.org/validator/">Hibernate Validator</a>,
            <a href="http://www.slf4j.org/">SLF4J</a>,
            <a href="https://github.com/FasterXML/jackson">Json Jackson</a>,
            <a href="http://ru.wikipedia.org/wiki/JSP">JSP</a>,
            <a href="http://en.wikipedia.org/wiki/JavaServer_Pages_Standard_Tag_Library">JSTL</a>,
            <a href="http://tomcat.apache.org/">Apache Tomcat</a>,
            <a href="http://www.webjars.org/">WebJars</a>,
            <a href="http://datatables.net/">DataTables plugin</a>,
            <a href="http://ehcache.org">EHCACHE</a>,
            <a href="http://www.postgresql.org/">PostgreSQL</a>,
            <a href="http://junit.org/">JUnit</a>,
            <a href="http://hamcrest.org/JavaHamcrest/">Hamcrest</a>,
            <a href="http://jquery.com/">jQuery</a>,
            <a href="http://ned.im/noty/">jQuery notification</a>,
            <a href="http://getbootstrap.com/">Bootstrap</a>.</p>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
<script type="text/javascript">
    <c:if test="${not empty param.username}">
    setCredentials("${param.username}", "");
    </c:if>
    function setCredentials(username, password) {
        $('input[name="username"]').val(username);
        $('input[name="password"]').val(password);
    }
</script>
</body>
</html>
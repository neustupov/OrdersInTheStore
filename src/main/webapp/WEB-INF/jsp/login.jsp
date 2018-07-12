<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<!-- Modal -->
<div class="modal fade bd-example-modal-lg" id="image-modal" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalCenterTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Просмотр изображения</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <img class="img-responsive center-block" src="" alt="">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

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
        <p>Стек технологий: <a href="http://projects.spring.io/spring-security/">Spring Security</a>,
            <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring MVC</a>,
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
    <div class="container">
        <div class="row">
            <div class="col">
                <a href="#" class="thumbnail">
                    <img src="resources/images/schema_order_actual.png" class="img-fluid" >
                </a>
            </div>
            <div class="col">
                <a href="#" class="thumbnail">
                    <img src="resources/images/schema_order_actual.png" class="img-fluid">
                </a>
            </div>
            <div class="col">
                <a href="#" class="thumbnail">
                    <img src="resources/images/schema_order_actual.png" class="img-fluid">
                </a>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <div class="lead">
        &nbsp;&nbsp;&nbsp;<a href="https://github.com/neustupov/votingForRestaurants">Java Enterprise проект</a> с
        регистрацией/авторизацией и интерфейсом на основе ролей (USER, ADMIN).
        Администратор может создавать/редактировать/удалять рестораны, меню, новые блюда, а пользователи -
        управлять своим профилем и голосовать за понравившийся ресторан через UI (по AJAX) и по REST интерфейсу с
        базовой авторизацией.
        Весь REST интерфейс покрывается JUnit тестами, используя Spring MVC Test и Spring Security Test.
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
<script>
    // После загрузки DOM-дерева (страницы)
    $(function() {
        //при нажатии на ссылку, содержащую Thumbnail
        $('a.thumbnail').click(function(e) {
            //отменить стандартное действие браузера
            e.preventDefault();
            //присвоить атрибуту scr элемента img модального окна
            //значение атрибута scr изображения, которое обёрнуто
            //вокруг элемента a, на который нажал пользователь
            $('#image-modal .modal-body img').attr('src', $(this).find('img').attr('src'));
            //открыть модальное окно
            $("#image-modal").modal('show');
        });
        //при нажатию на изображение внутри модального окна
        //закрыть его (модальное окно)
        $('#image-modal .modal-body img').on('click', function() {
            $("#image-modal").modal('hide')
        });
    });
</script>
</body>
</html>
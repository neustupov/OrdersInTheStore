<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/priceRequestDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <div class="page-header">
            <h3><spring:message code="priceRequest.title"/></h3>
        </div>
        <br/>
        <br/>
        <button class="btn btn-primary mr-2" onclick="add()">
            <span class="fa fa-plus" aria-hidden="true"></span>
            <spring:message code="priceRequest.add"/>
        </button>
        <button class="btn btn-primary mr-2" onclick="getAllVotes()">
            <span class="glyphicon" aria-hidden="true"></span>
            <spring:message code="common.orders"/>
        </button>
        <table class="table table-striped display" id="priceRequestDatatable">
            <thead>
            <tr>
                <th>id</th>
                <th><spring:message code="priceRequest.user"/></th>
                <th><spring:message code="priceRequest.dateTime"/></th>
                <th><spring:message code="priceRequest.client"/></th>
                <th><spring:message code="priceRequest.products"/></th>
                <th><spring:message code="priceRequest.ready"/></th>
                <th><spring:message code="common.edit"/></th>
                <th><spring:message code="common.delete"/></th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<%--<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 id="modalTitle" class="modal-title"></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form:form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="name" class="col-form-label"><spring:message code="restaurant.name"/></label>

                        <input type="text" class="form-control" id="name" name="name"
                               placeholder="<spring:message code="restaurant.name"/>">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                            <span class="fa fa-close" aria-hidden="true"></span>
                        </button>
                        <button type="button" onclick="save()" class="btn btn-primary">
                            <span class="fa fa-check" aria-hidden="true"></span>
                        </button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>--%>

<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp"/>
<script type="text/javascript">
    i18n["addTitle"] = '<spring:message code="common.add"/>';
    i18n["editTitle"] = '<spring:message code="common.edit"/>';
</script>
</html>

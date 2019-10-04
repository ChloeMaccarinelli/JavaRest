<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'annonce.label', default: 'Annonce')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
<body>
<a href="#list-annonce" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="list-annonce" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

%{--
<f:table collection="${annonceList}" />
--}%

    <table>
        <thead>
        <th class="sortable"><a href="/annonce/index?sort=title&amp;max=10&amp;order=asc">Title</a></th>
        <th class="sortable">Description</th>
        <th class="sortable">Valid Till</th>
        <th class="sortable"><a href="/annonce/index?sort=illustrations&amp;max=10&amp;order=asc">Illustration</a></th>
        <th class="sortable">State</th>
        <th class="sortable"><a href="/annonce/index?sort=author&amp;max=10&amp;order=asc">Author</a></th>
        </thead>
        <g:each in="${annonceList}" var="instance">
            <tr>
                <td><g:link controller="annonce" action="show" id="${instance.id}">${instance.title}</g:link></td>
                <td>${instance.description}</td>
                <td>${instance.validTill}</td>
                <td><img style="width : 100px; height : 100px" src="../../assets/${instance.illustration.filename}"/></td>
                <td>${instance.state}</td>
                <td><g:link controller="user" action="show" id="${instance.author.id}">${instance.author.username}</g:link></td>
            </tr>
        </g:each>
    </table>

    <div class="pagination">
        <g:paginate total="${annonceCount ?: 0}" />
    </div>
</div>
</body>
</html>
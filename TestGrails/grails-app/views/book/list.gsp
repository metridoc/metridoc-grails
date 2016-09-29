<%--
  Created by IntelliJ IDEA.
  User: zhixu
  Date: 9/22/16
  Time: 2:29 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<g:sortableColumn property="title" title="Title"/>

<g:sortableColumn property="title" title="Title"
                  style="width: 200px"/>

<g:sortableColumn property="title" titleKey="book.title"/>

<g:sortableColumn property="releaseDate"
                  defaultOrder="desc" title="Release Date"/>

<g:sortableColumn property="releaseDate" defaultOrder="desc"
                  title="Release Date" titleKey="book.releaseDate"/></body>
</html>


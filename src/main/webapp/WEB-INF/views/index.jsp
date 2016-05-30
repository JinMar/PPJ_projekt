<%@page contentType="text/html" pageEncoding="windows-1250"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>

<html>
<head>
    <title>Semestrální projekt</title>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <spring:url value="/css/main.css" var="coreCss"/>
    <spring:url value="/img/logo-fm.png" var="logo"/>
    <link href="${coreCss}" rel="stylesheet" media="all"/>
</head>
<body>
<header>
    <div id="Logo">
        <h1><a href="#">Semestrální projekt</a></h1>
    </div>

</header>
<section id="Preview">

    <div class="img_Border"><a href="www.fm.tul.cz"><img class="def" src=${url} alt=""/></a></div>
</section>
<section>
    <article class="cont">

        <header>
            <h2>Vítejte na stránkách semestrální práce z pøedmìtu Pokroèilé programování na platformì Java</h2>
        </header>
        <div class="rating">
            <form:form method="GET" action="/likeimg/id=${current}&next=${next}&prv=${previous}">
                <input type="submit" value="Like: ${likes} "/>
            </form:form>
            <form:form method="GET" action="/dislikeimg/id=${current}&next=${next}&prv=${previous}">
                <input type="submit" value="Dislike: ${dislikes}"/>
            </form:form>
        </div>
        <div class="buts">


            <p>
                <c:forEach items="${tagses}" var="tg">

                    || ${tg}


                </c:forEach>||
            </p>

            <p>${infodata}</p>


            <c:choose>
                <c:when test="${!empty previous}">
                    <div class="left-but">
                        <form:form method="GET" action="/${previous}">
                            <input type="submit" value="PØEDCHOZÍ"/>
                        </form:form>
                    </div>

            </c:when></c:choose>
            <c:choose>
                <c:when test="${!empty next}">
                    <div class="right-but">
                        <form:form method="GET" action="/${next}">
                            <input type="submit" value="DALŠÍ"/>
                        </form:form>
                    </div>
                </c:when></c:choose>

        </div>
        <div class="comments">
            <ul>
                <c:forEach items="${comemnt}" var="post">


                    <li class="heg">
                        <div class="item">
                            <div class="rating">
                                <form:form method="GET"
                                           action="/like/id=${current}&com=${post.id}&next=${next}&prv=${previous}">
                                    <input type="submit" value="Like: ${post.likes}"/>
                                </form:form>
                                <form:form method="GET"
                                           action="/dislike/id=${current}&com=${post.id}&next=${next}&prv=${previous}">
                                    <input type="submit" value="Dislike ${post.dislikes}"/>
                                </form:form>

                            </div>
                            <p>${post.text}</p>
                        </div>
                        <div class="clr"></div>
                    </li>
                </c:forEach>
            </ul>
            <form:form method="POST" modelAttribute="mybox"
                       action="/saveComment/id=${current}/next=${next}/prev=${previous}">

                <form:textarea path="text"/>


                <input type="submit" value="Uložit koment"/>
            </form:form>
        </div>

    </article>


</section>
<footer>
    <p>&copy; Semestrální projekt 2016. All rights reserved. Design by Marek Jindrák.</p>
</footer>

</body>
</html>

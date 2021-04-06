<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../components/head.jsp" %>
</head>

<body>
    <%@ include file="../components/header.jsp" %>
    <div class="main">
        <div class="white_box ">
            <div class="perfil-row">
                <img src="/imagens/minion-ranking.png" alt="ranking-logo" class="add-question-img" />
                <h3 class="title_next_appt">Ranking</h3>
            </div>

            <div class="row">

                <section class="content-area middle-row">
                    <table class="table">

                        <thead>
                            <tr class="ranking-table-title">
                                <th>Posição</th>
                                <th>Username</th>
                                <th>Score</th>
                            </tr>
                        </thead>
                        <tbody>

                            <!-- For -->
                            <c:set var="pos" value="1" scope="page" />
                            <c:forEach var="jogo" items="${jogos}">
                                <tr class="ranking-table-details">
                                    <td>${pos}</td>
                                    <td>${jogo.getUser().getUsername()}</td>
                                    <td>${jogo.getGameScore()}</td>
                                </tr>
                                <c:set var="pos" value="${pos + 1}" scope="page"/>
                            </c:forEach>
                        </tbody>
                    </table>
                </section>
            </div>
        </div>
    </div>

    <%@ include file="../components/footer.jsp" %>

</body>

</html>
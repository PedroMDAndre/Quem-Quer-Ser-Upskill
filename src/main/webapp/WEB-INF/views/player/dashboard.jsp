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
    <div class="row middle-row3">
        <div class="col-md-4">
            <div class="card_box2">
                <div class="card2">
                    <div class="card-body2">
                        <h2>
                            <span>${nrJogadores}</span>
                        </h2>

                        <h3>Jogadores registados</h3>
                        <img src="/imagens/icon-player.svg" alt="controller_logo" class="dash_icon" />

                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card_box2">
                <div class="card2">
                    <div class="card-body2">
                        <h2>
                            <span>${nrPerguntas}</span>
                        </h2>

                        <h3>Perguntas registadas</h3>
                        <img src="/imagens/icon-question.svg" alt="question_logo" class="dash_icon" />

                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card_box2">
                <div class="card2">
                    <div class="card-body2">
                        <h2>
                            <span>#${posicaoRanking} - ${nrPontos} xp</span>
                        </h2>
                        <h3>Posição actual no ranking
                        </h3>
                        <img src="/imagens/icon-trophy.svg" alt="trophy_logo" class="dash_icon" />

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="logotipo_dash_icon">
        <a href="/player/game" class="start-text">
            <img src="/imagens/icon_play.svg" alt="start_logo" class="logotipo_dash" />
            <span class="start-text2">START QUIZ </span>
        </a>
    </div>

</div>

<%@ include file="../components/footer.jsp" %>

</body>

</html>
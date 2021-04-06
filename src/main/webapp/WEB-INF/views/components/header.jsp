<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<header class="px-3 py-2 bg-light text-black">
    <div class="container">
        <div class="d-flex align-items-center justify-content-center justify-content-lg-start">
            <a href="/player/dashboard" class="text-black-50 text-decoration-none header_logo" title="Página Inicial">
                <img src="/imagens/logotipo.svg" alt="quiz_logo" class="logo" />
            </a>
            <div class="header_logo welcome"> ${user.getUsername()}</div>
            <ul class="nav text-small header_buttons">
                <c:if test="${load.equals(true)}">
                <li>
                    <a href="/player/game/continue" class="nav-link hover_buttons">
                        <img src="/imagens/icon_play.svg" alt="start_logo" class="header_icon" />
                        Continuar jogo anterior
                    </a>
                </li>
                 </c:if>
                <li>
                    <a href="/player/dashboard" class="nav-link hover_buttons">
                        <img src="/imagens/home-icon.svg" alt="ranking_logo" class="header_icon" />
                        Página Inicial
                    </a>
                </li>
                <li>
                    <a href="/player/ranking" class="nav-link hover_buttons">
                        <img src="/imagens/icon_ranking.svg" alt="ranking_logo" class="header_icon" />
                        Ranking
                    </a>
                </li>
                <li>
                    <a href="/player/add-question" class="nav-link hover_buttons">
                        <img src="/imagens/patch-question.svg" alt="question_logo" class="header_icon" />
                        Adicionar pergunta
                    </a>
                </li>
                <li>
                    <a href="/logout" class="nav-link hover_buttons">
                        <img src="/imagens/icon_logout.svg" alt="logout_logo" class="header_icon" />
                        Terminar Sessão
                    </a>
                </li>
            </ul>
        </div>
    </div>
</header>
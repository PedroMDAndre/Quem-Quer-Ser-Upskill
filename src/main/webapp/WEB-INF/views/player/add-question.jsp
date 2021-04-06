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
            <img src="/imagens/minion-add-question.svg" alt="add-question" class="add-question-img"/>
            <h3 class="title_next_appt">Adicionar pergunta</h3>

        </div>

        <form method="post" action="/player/add-question">
            <div class="conjunto-respostas">
                <div class="card-body">
                    <input required type="text" class="form-control add-pergunta" placeholder="Pergunta" name="pergunta"
                    <c:if test="${not empty pergunta}"> value="${pergunta}"</c:if>>

                    <div class="input-group">
                        <div class="input-group-text">
                            <input class="" type="radio" name="respostaCerta" value=1
                            <c:if test="${respostaCerta == 1}"> checked </c:if>
                            <c:if test="${empty respostaCerta}"> checked </c:if>>
                        </div>
                        <input required type="text" class="form-control" placeholder="Resposta 1" name="resposta1"
                        <c:if test="${not empty resposta1}"> value="${resposta1}"</c:if>>
                    </div>

                    <div class="input-group">
                        <div class="input-group-text">
                            <input class="" type="radio" name="respostaCerta" value=2
                            <c:if test="${respostaCerta == 2}"> checked </c:if>>
                        </div>
                        <input required type="text" class="form-control" placeholder="Resposta 2" name="resposta2"
                        <c:if test="${not empty resposta2}"> value="${resposta2}"</c:if>>
                    </div>

                    <div class="input-group">
                        <div class="input-group-text">
                            <input class="" type="radio" name="respostaCerta" value=3
                            <c:if test="${respostaCerta == 3}"> checked </c:if>>
                        </div>
                        <input required type="text" class="form-control" placeholder="Resposta 3" name="resposta3"
                        <c:if test="${not empty resposta3}"> value="${resposta3}"</c:if>>
                    </div>

                    <div class="input-group">
                        <div class="input-group-text">
                            <input class="" type="radio" name="respostaCerta" value=4
                            <c:if test="${respostaCerta == 4}"> checked </c:if>>
                        </div>
                        <input required type="text" class="form-control" placeholder="Resposta 4" name="resposta4"
                        <c:if test="${not empty resposta4}"> value="${resposta4}"</c:if>>
                    </div>
                </div>
                <div>
                    <select required class="form-select select-dificuldade" name="dificuldadeType">
                        <option selected disabled>Escolha o nível de dificuldade</option>
                        <c:forEach var="dificuldade" items="${dificuldades}">
                            <option value=${dificuldade.getType()}
                                    <c:if test="${dificuldadeType==dificuldade.getType()}"> selected </c:if>>
                                    ${dificuldade.getButtonText()}
                            </option>
                        </c:forEach>
                    </select>

                </div>
            </div>
            <button type="submit" class="btn btn-green  btn-w40">Guardar</button>


            <div class="error">${msgError}</div>
        </form>
    </div>
</div>

<%@ include file="../components/footer.jsp" %>

</body>

</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../components/head.jsp" %>
</head>
<body>
<%@ include file="../components/header-zero.jsp" %>


<div class="main">
    <div class="white_box">

        <div class="perfil-row">
            <img src="/imagens/minion-recover.png" alt="recover-img" class="add-question-img" />
            <h3 class="title_next_appt">Recuperar Palavra-passe</h3>
        </div>

        <div class="main-col">
            <form class="recoverPass-form" action="/public/recover-password" method="post">
                <div class="input-row">
                    <input class="form-input input1" type="text" name="username" id="user_name_id" required
                           placeholder="*Introduza o seu username" value="${username}">
                    <input class="form-input input1" type="text" name="email" id="email_id" required
                           placeholder="*Introduza o seu email" value="${email}">
                </div>

                <div class="input-row">
                    <input class="form-input pass1" type="password" name="password1" id="password_id1" required
                           placeholder="*Nova palavra-passe">
                    <input class="form-input pass1" type="password" name="password2" id="password_id2" required
                           placeholder="*Repetir palavra-passe">
                </div>

                <div class="input-row">
                    <button type="submit" class="btn-green">Guardar alterações e iniciar sessão</button>
                </div>
                <span style="color: red">${errorMessage}</span>
            </form>

        </div>
    </div>
</div>
<%@ include file="../components/footer.jsp" %>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set scope="session" var="perfil" value='<%=request.getSession().getAttribute("perfil")%>'></c:set>

<nav class="pcoded-navbar">
	<div class="sidebar_toggle">
		<a href="#">
			<i class="icon-close icons"></i>
		</a>
	</div>

	<div class="pcoded-inner-navbar main-menu">
		<div class="">
				<div class="main-menu-header">
					<c:if test="${imagemUser != '' && imagemUser != null}">
						<img class="img-80 img-radius" src="${imagemUser}" alt="User-Profile-Image">
					</c:if>
					
					<c:if test="${imagemUser == '' ||  imagemUser == null}">
						<img class="img-80 img-radius"
									src="<%=request.getContextPath()%>/assets/images/avatar-4.jpg"
									alt="User-Profile-Image">
					</c:if>
					
					<div class="user-details">
						<span id="more-details"><%=request.getSession().getAttribute("usuario")%>
							<i class="fa fa-caret-down"></i>
						</span>
					</div>
				</div>
	
				<div class="main-menu-content">
					<ul>
						<li class="more-details">
							<a href="<%=request.getContextPath()%>/ServletLogin?acao=logout">
								<i class="ti-layout-sidebar-left"></i>Sair
							</a>
						</li>
					</ul>
				</div>
		</div>
		
		<ul class="pcoded-item pcoded-left-item">
			<li class="active">
				<a href="<%=request.getContextPath()%>/principal/principal.jsp" class="waves-effect waves-dark" style="margin-top: 10px;">
					<span class="pcoded-micon">
						<i class="ti-home"></i>
							<b>D</b>
					</span>
					<span class="pcoded-mtext" data-i18n="nav.dash.main">Início</span>
					<span class="pcoded-mcaret"></span>
				</a>
			</li>
			
			<li class="pcoded-hasmenu">
				<a href="javascript:void(0)" class="waves-effect waves-dark">
					<span class="pcoded-micon">
						<i class="ti-layout-grid2-alt"></i>
					</span>
					<span class="pcoded-mtext" data-i18n="nav.basic-components.main">Cadastro</span>
					<span class="pcoded-mcaret"></span>
				</a>

				<ul class="pcoded-submenu">
					<c:if test="${perfil == 'ADMIN'}">
						<li class=" ">
							<a href="<%=request.getContextPath()%>/ServletUsuarioController?acao=listarUser" class="waves-effect waves-dark">
								<span class="pcoded-micon">
									<i class="ti-angle-right"></i>
								</span>
								<span class="pcoded-mtext" data-i18n="nav.basic-components.alert">Usuário</span>
								<span class="pcoded-mcaret"></span>
							</a>
						</li>
					</c:if>
				</ul>
			</li>
		</ul>
		
		<div class="pcoded-navigation-label" data-i18n="nav.category.forms">Relatório</div>
			<ul class="pcoded-item pcoded-left-item">
				<li>
					<a href="<%=request.getContextPath()%>/principal/reluser.jsp" class="waves-effect waves-dark">
						<span class="pcoded-micon">
							<i class="ti-layers"></i>
								<b>FC</b>
						</span>
						<span class="pcoded-mtext" data-i18n="nav.form-components.main">Usuário</span>
						<span class="pcoded-mcaret"></span>
					</a>
				</li>
	
				<li>
					<a href="<%=request.getContextPath()%>/principal/relusergrafico.jsp" class="waves-effect waves-dark">
						<span class="pcoded-micon">
							<i class="ti-layers"></i>
								<b>FC</b>
						</span>
						<span class="pcoded-mtext" data-i18n="nav.form-components.main">Gráfico Salário</span>
						<span class="pcoded-mcaret"></span>
					</a>
				</li>
			</ul>
			
	</div>
</nav>
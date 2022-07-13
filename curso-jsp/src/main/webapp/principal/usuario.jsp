<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>
	<jsp:include page="theme-loader.jsp"></jsp:include>

	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="nav-bar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="navbar-mainmenu.jsp"></jsp:include>

					<div class="pcoded-content">
						<!-- Page-header start -->
						<jsp:include page="page-header.jsp"></jsp:include>

						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">


										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card end -->
												<!-- Input Grid card start -->
												<div class="card">

													<div class="card-block">
														<h4 class="sub-title">Cad. Usuário</h4>


														<form class="form-material"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post" id="formUser">
															
															<input type="hidden" name="acao" id="acao" value=" ">
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" readonly="readonly"
																	value="${modelLogin.id}"> <span
																	class="form-bar"></span> <label class="float-label">ID:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="nome" Id="nome"
																	class="form-control" required="required"
																	value="${modelLogin.nome}"> <span
																	class="form-bar"></span> <label class="float-label">Nome:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="email" name="email" Id="email"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.email}">
																<span class="form-bar"></span> <label
																	class="float-label">Email:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="login" Id="login"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.login}">
																<span class="form-bar"></span> <label
																	class="float-label">Login</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="password" name="senha" Id="senha"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.senha}">
																<span class="form-bar"></span> <label
																	class="float-label">Senha</label>
															</div>

															<button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparForm()">Novo</button>
															<button type="submit" class="btn btn-success waves-effect waves-light">Salvar</button>
															<button type="button" class="btn btn-info waves-effect waves-light" onclick="criarDelete()">Excluir</button>
														</form>
														</div>
													</div>
													</div>
													</div>
													</div>
														<span>${msg}</span>
													
													
													<!-- Page-body end -->
												</div>
												<div id="styleSelector"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<jsp:include page="javascript-file.jsp"></jsp:include>
<script type="text/javascript">

function criarDelete() {
    var form = document.getElementById('formUser');
    var acao = document.getElementById('acao');
    acao.value = 'deletar';
    form.method = 'get';
    form.submit();	
}

function limparForm() {
	var elementos = document.getElementById("formUser").elements; /*retorna elementos HTML dentro do form*/
	
	for (p = 0; p < elementos.length; p++) {
		elementos[p].value = '';
	}
}
</script>
</body>

</html>

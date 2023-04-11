# Cadastro feito em JSP, Servlets e Bootstrap
Esse é um cadastro básico utilizando as operações de banco de dados criar, consultar, atualizar e deletar. Há também o download de relatório em pdf utilizando o Jaspersoft Studio e Gráfico de linhas utilizando Google Charts.

# Tela de Login
Esta é a tela de login, aonde é possível o usuário acessar com suas credenciais e caso alguma das duas informações (login e senha) estarem erradas, um erro é mostrado acima do título do cadastro conforme o segundo print.

![tela_de_login](https://user-images.githubusercontent.com/105288563/230689483-5416ad35-e86f-47d7-88b0-ca25c29cc612.jpg)

![tela_de_login_erro](https://user-images.githubusercontent.com/105288563/230689644-5bf6d2b9-e754-4d3c-ab75-1559b6129b56.jpg)

# Página inicial
Após o usuário ser logado no sistema, é diretamente logado para a página inicial do projeto. Segue abaixo um print com enumeração de características da tela demonstrando cada funcionalidade da página inicial.

1. Logo do sistema;
2. Oculta a parte lateral esquerda do sistema ou habilita o aparecimento dela (nesse caso está habilita para exibição);
3. Maximiza/minimiza a tela;
4. Nome do usuário logado;
5. É exibido a opção de sair do sistema ao clicar na seta (↓);
6. Funcionalidades 4 & 5 (logo acima está a foto do usuário logado);
7. Ir para a tela inicial do sistema;
8. Realizar o cadastro de usuário;
9. Ir para tela de geração do relatório em pdf ou em tela;
10. Ir para a tela de geração do gráfico de linhas (o gráfico utiliza como parâmetro os cargos dos funcionários/usuários x salários);
11. Mensagem de boas vindas após o log in;
12. Página inicial aonde existiria a possibilidade de ter outros dados.

![tela_inicial](https://user-images.githubusercontent.com/105288563/230690080-74a561bd-d4e0-40b2-8dcd-19715e5e8f57.jpg)

# Cadastro de usuário
Acesso a aba de cadastro de usuário, é possível cadastrar um novo usuário com as informações exibidas nos prints abaixo. Há formatação de moeda no campo salarial utilziando jQuery, calendário datepicker e o consumo de API Via Cep para os campos de endereço (Ao digitar o cep no campo, automaticamente as informações são preenchidas quando o foco da linha é trocado).

O Perfil de usuário pode ser dividido entre administrador, secretário ou auxiliar.

Ao clicar no botão de pesquisar, é possível digitar um nome na barra de pesquisa e então é permitido visualizar as informações cadastradas do usuário e/ou editar.

Logo abaixo da tela de cadastro é possivel ver a lista de usuários cadastrados no qual há uma renderização de usuários carregados, a cada 5 usuários mostrados em tela, uma nova página é criada para otimização do sistema.

![tela_de_cadastro](https://user-images.githubusercontent.com/105288563/230695006-b222dc2a-be87-4571-954f-fb31e493eb37.jpg)

![tela_de_cadastro2](https://user-images.githubusercontent.com/105288563/230695016-6e04aa6b-9151-443c-8f26-8eea57232ab1.jpg)

![tela_de_cadastro3](https://user-images.githubusercontent.com/105288563/230695018-b7c0c965-ae97-4339-a494-52b0f396ac10.jpg)

![tela_de_cadastro4](https://user-images.githubusercontent.com/105288563/230695024-d198b240-339f-4f99-8299-22b5515976e8.jpg)

![tela_pesquisa_de_usuario](https://user-images.githubusercontent.com/105288563/230696190-afbf4ed9-7124-4efc-b20f-4111da041250.jpg)

![lista_paginada](https://user-images.githubusercontent.com/105288563/230696595-d9ba13f0-0e17-431f-94e6-85bb6bec4e1c.jpg)

![lista_paginada2](https://user-images.githubusercontent.com/105288563/230696600-b7290a2c-62d8-4fdf-b2c0-630a54d6946c.jpg)

# Relatório de usuário
Por essa tela é possível realizar a impressão do relatório em tela ou em formato pdf. Os dados exibidos são o ID e Nome de cada funcionário com seus telefones logo abaixo (caso tenham).

É possível fazer a impressão do relatório filtrando por um périodo inicial e final de data de nascimento ou um relatório geral.

![tela_de_relatorio1](https://user-images.githubusercontent.com/105288563/230696408-5200b06e-4902-4ab4-94de-fbae76b216fc.jpg)

![relatorio_em_pdf](https://user-images.githubusercontent.com/105288563/230696417-c4a11594-6535-4640-aa7e-ac9063e467ef.jpg)

# Gráfico de usuário
Por essa tela é possível realizar a impressão do relatório em tela filtrando por um périodo inicial e final de data de nascimento. Os dados exibidos no gráfico são os cargos (eixo x) e a média salarial entre cada um (eixo y).

![grafico](https://user-images.githubusercontent.com/105288563/230696745-b149f7b7-d9f8-461a-8ce7-a3a4d8097fa9.jpg)

![grafico2](https://user-images.githubusercontent.com/105288563/230696753-029fa9d1-340e-4d1d-90bc-2d98aab600e0.jpg)

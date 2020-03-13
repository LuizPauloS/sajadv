# SAJ ADV

A aplicação **SAJ ADV** é um módulo para gerenciamento de pessoas.

# Tecnologias

Para criar o sistema foram utilizados as seguintes ferramentas/frameworks:

- Backend com Spring-boot v2.2 e Java v11

- Frontend com Angular v8

- Banco de dados utilizando o PostgreSQL

# Composição da Stack

A Stack do Newcredit é composta por 4 aplicações, são elas:

- sajadv-db: Pacote que contém as devidas configurações para executar banco de dados PostgreSQL

- sajadv-api: Pacote que contém o serviço com conexão ao banco de dados, gerenciamento da comunicação com a tela e configurações docker para execução

- sajadv-app: Pacote que contém o projeto frontend em Angular

- sajadv-build: Pacote que contém as devidas configurações para build do projeto

## O que preciso para subir a aplicação

- Sistema Operacional Linux

- Docker e docker-compose instalados

- Gerenciador de Pacotes Maven

## Rodando a aplicação

Para rodar a aplicação você precisa abrir um terminal e ir até a pasta raiz do projeto e executar o arquivo start.sh, com o comando :

`sudo ./start.sh`

Após rodar o comando e subir todos os serviços é só acessar o endereço :

http://localhost:4200

## Como usar o sistema
Após subir toda a stack via docker e acessar a aplicação você verá uma tela bem intuitiva com três menus sendo :
- Dashboard : Sempre te leva para a pagina inicial da aplicação
- Proposta : Pagina para cadastro de uma nova proposta
- Consulta Proposta: Pagina para consultar uma proposta por CPF

## Documentação
Com a finalidade de documentar as APIs foi adicionado o swagger para tal finalidade. O mesmo pode ser acessado pelos endereços abaixo:

- newcredit : http://localhost:8080
- newcredit-engine : http://localhost:8081

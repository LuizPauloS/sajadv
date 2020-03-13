# SAJ ADV

A aplicação **SAJ ADV** é um módulo para gerenciamento de pessoas.

# Tecnologias

Para criar o sistema foram utilizados as seguintes ferramentas/frameworks:

- Backend com Spring-boot v2.2 e Java v11

- Frontend com Angular v9

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

- Angular CLI

## Rodando a aplicação

Para rodar a aplicação você precisa abrir um terminal e ir até a pasta raiz do projeto e executar o arquivo start.sh, com o comando :

`sudo ./start.sh`

Após rodar o comando e subir todos os serviços é só acessar o endereço :

http://localhost:4200

## Como usar o sistema
Após subir toda a stack via docker e acessar a aplicação você verá uma tela com um botão indicando acesso ao sistema, 
após clicar na opção "Acessar" você será redirecionado para a listagem de registros com as seguintes ações/opções :

- Listagem de Registros: Tela com a listagem de registros e opções
- Visualizar Detalhes : Tela para visualização de detalhes do registro
- Editar Registro : Tela de edição do registro escolhido
- Remover Registro: Botão com modal que verifica se você realmente deseja efetuar a remoção (lógica)

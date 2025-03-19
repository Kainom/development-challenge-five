<h1 align="center"> 
  <p>Rest API to Patient Management</p> 
</h1> 

<p> 
  <img src="https://img.shields.io/badge/Challenge%20Completed-8A2BE2">  
</p> 


## Descrição 

Este sistema é uma API REST simples, desenvolvida com Spring Boot. A comunicação com a API é realizada por meio de requisições HTTP, e os dados são estruturados em formato JSON, assegurando fácil integração e uso em diferentes aplicações.

Permite o gerenciamento de registros de pacientes.
Implementa as operações de CRUD (Criar, Ler, Atualizar e Deletar) para pacientes.
Inclui um sistema de autenticação para segurança.

## Índice 

- [Funcionalidades](#funcionalidades) 
- [Como executar](#como-executar) 
- [Tecnologias usadas](#tecnologias-usadas) 
- [Autores](#autores) 

## Funcionalidades 

:fire: **Firebase:** para a persistência dos dados foi usado o sistema de banco de dados Firestore, um recurso do Firebase, o qual é mantido pela Google 

:page_with_curl: **Paginação:** os dados são carregados em páginas

:mailbox_with_mail: **ViaCEP:** para a validação dos CEPs foi utilizado a API do ViaCEP 

https://github.com/MateusPitura/development-challenge-nine/assets/119008106/de1bda45-19ab-47ba-b434-d59fb005c52c

## Como executar 

**Para devs | cliente:** 

1. Clone o repositório e entre na pasta do client

2. `npm install` 

3. `npm start`

**Para devs | servidor:** 

1. Clone o repositório e entre na pasta do server

2. `npm install`

3. No arquivo `server.js` altere o caminho da `key.json` (uma chave privada fornecida pelo Firebase para conexão) no trecho de código `const credentials = require("../../key.json")`

4. `npm start`

## Tecnologias usadas 

:heavy_check_mark: React 

:heavy_check_mark: Node 
 
:heavy_check_mark: Firebase  

## Autores 

| Mateus Pitura | 
|------| 
| <p align="center"><img src="https://user-images.githubusercontent.com/119008106/227821967-fac62c31-0d62-485b-829e-ef56c033e21a.jpeg" width="100" height="100"></p> | 
| <a href="https://www.linkedin.com/in/mateuspitura/"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"> |

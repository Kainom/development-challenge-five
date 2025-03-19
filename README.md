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

## Adendos Tecnológicos 

🐘**postgresql**: Persiste os dados em um banco de dados postgresql no docker(img postgres:latest) 

🔐**JWT**: A segurança é feita com tokens via JWT

🗺️**ViaCEP**: Para a validação do endereço foi utilizado a API pública ViaCEP


## Preview Da Estrutura
|        |
|------|
| <img src="https://github.com/user-attachments/assets/6814fdc9-f33a-44d5-9b7a-7903972428a0"/>



## Como executar 

**Crie um env com a seguint estrutura:** 
``` env
APP_PORT=port
DB_URL=jdbc:postgresql://localhost:5434/your_database
DB_USER=your_user
DB_PASSWORD=uour_password
```
**Observações**
- O env não é estritamente necessário,use os dados diretamente no properties se não quiser usá-lo
- Recomendo a utilização da porta 8000 ou 8001,isso pode evitar problemas com outras portas
- A api possui migrations,cuidado para não editar migrations ja realizadas no banco de dados

**Executando direto com spring**

1. clone o repositório e entre no folder challenge
2. `mvn install`
3. `mvn spring-boot:run`

**Executando com jar**
1. clone o repositório e entre no folder challenge
2. `mvn clean package`
3. `java -jar target/challenge-1.0.0-Release.jar` 

## Algumas Das Tecnologias Usadas 

☕ java 23 

🐘 postgress (postgress last image) 
 
🐳 Docker (para usar o banco)  

## Autor

💻 Kainã Felipe
<div> 
</a>
<a href = "mailto:contato.eleuteriokaina@gmail.com"> <img src="https://img.shields.io/badge/-Gmail-%23333?style=for-the-badge&logo=gmail&logoColor=white" target="_blank"></a>
<a href="https://www.linkedin.com/in/kain%C3%A3-eleut%C3%A9rio-9528a3208/" target="_blank"><img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white"  target="_blank"></a> 
</div>&nbsp;&nbsp;
 

# 🖥️ Proyecto de Pruebas API

Este proyecto tiene como objetivo la automatización de pruebas API utilizando **Rest Assured**, **JUnit 5** y **Java Faker**. Se enfoca en la verificación de servicios RESTful relacionados con la gestión de usuarios y cuentas bancarias. Las pruebas cubren la creación de usuarios, validación de correos electrónicos, actualizaciones de cuentas y la realización de transacciones.

## 🚀 Tecnologías Utilizadas
- **Java 20**
- **JUnit 5**
- **Rest Assured**
- **Java Faker**
- **Maven**

## 📌 Estructura del Proyecto
📂 org.globant.apiTest  
 ├── 📂 tests  
 │   ├── ApiTest.java  
 ├── 📂 model  
 │   ├── Usuario.java  
 ├── pom.xml  (Maven dependencies)  
 ├── README.md (Este archivo)  

## ⚙️ Instalación y Configuración
1. Clona este repositorio:  
   `git clone https://github.com/tu-usuario/tu-repositorio.git`  
   `cd tu-repositorio`  

2. Asegúrate de tener **Java 20** instalado. Puedes verificarlo con el siguiente comando:  
   `java -version`  

3. Instala las dependencias del proyecto con Maven:  
   `mvn clean install`  

## 🛠️ Cómo Ejecutar las Pruebas  
Ejecuta las pruebas con Maven:  
`mvn test`  

## 📖 Descripción de las Pruebas  
Las pruebas automatizadas verifican los siguientes casos:  

🔹 **verifyAndClean**: Verifica si existen usuarios y limpia el endpoint.  
🔹 **createUsersValidateUniqueEmail**: Crea usuarios aleatorios y valida que no haya correos duplicados.  
🔹 **noDuplicatedEmails**: Verifica que no haya correos duplicados en la base de datos.  
🔹 **updateNumberAccount**: Verifica que existan cuentas bancarias.  
🔹 **validateDeposit**: Realiza un depósito en una cuenta y valida el saldo.  
🔹 **validateWithdrawal**: Realiza un retiro de una cuenta y valida el saldo.  

## 🌍 Endpoints Utilizados  
Los endpoints de la API se basan en **MockAPI** para la gestión de usuarios y cuentas bancarias.  
- **GET /users**: Obtiene todos los usuarios.  
- **POST /users**: Crea un nuevo usuario.  
- **DELETE /users**: Elimina todos los usuarios.  
- **GET /accounts**: Obtiene todas las cuentas bancarias.  
- **GET /accounts/{id}**: Obtiene una cuenta bancaria por su ID.  
- **PUT /accounts/{id}**: Actualiza una cuenta bancaria con nuevos valores.  
- **MockAPI**: El proyecto interactúa con los endpoints de la API de **MockAPI** ubicada en `https://67e40c9a2ae442db76d2d123.mockapi.io/api/v1`.

## 🏆 Autor  
Desarrollado por **Michael Pizarro Llanos**  
GitHub:   https://github.com/MichaelPizarro

🤖 **¡Happy Testing!**

# MAGISTRA - Java Escritorio (POO + MVC + DAO + JDBC + MySQL)

## Requisitos
- JDK 17 o posterior (el proyecto compila para Java 17)
- MySQL Server 8.0 o superior
- NetBeans (recomendado)

## Driver JDBC MySQL
El `pom.xml` declara **MySQL Connector/J 26.10.0**. Maven descarga el driver y lo agrega al classpath al compilar o ejecutar el proyecto.

## Base de datos
1. Ejecute `sql/00_instalacion_completa.sql` en MySQL Workbench o MySQL Client.
2. El script crea `magistra`, tablas, relaciones, categorías Ascenso/Nombramiento, temas, recursos y preguntas demo: cinco preguntas por cada tema de práctica.
3. Si la base ya tiene los cuatro temas, abra `sql/03_preguntas_adicionales.sql` en esa conexión y ejecute el archivo completo como script. En DBeaver para Windows, deje el editor sin texto seleccionado y pulse **Alt+X** (Ejecutar script SQL). El archivo agrega tres preguntas por tema y se puede volver a ejecutar sin duplicarlas.
4. Los datos demo no representan un banco oficial de preguntas.

## Configuración de conexión
La aplicación se conecta a `localhost:3306/magistra`. La versión publicada trae la constante `CLAVE` vacía. Para configurar tu conexión, edita `URL`, `USUARIO` y `CLAVE` en tu copia local de `src/main/java/pe/edu/magistra/util/ConexionBD.java` y vuelve a compilar. La contraseña queda incluida en el código fuente y en el JAR compilado.

## Ejecutar
Con Maven:
`mvn clean compile exec:java`

Con NetBeans:
abra la carpeta del proyecto como proyecto Maven y ejecute `pe.edu.magistra.app.Main`.

## Arquitectura
- `model`: entidades y POO.
- `dao`: contratos/interfaces.
- `dao.impl`: implementaciones JDBC/MySQL.
- `controller`: controladores MVC.
- `view`: vistas Java Swing.
- `util`: conexión, sesión y hash de contraseña PBKDF2.

## Funcionalidades MVP
Registro, login, dashboard Ascenso/Nombramiento, temas, preguntas de opción múltiple, temporizador, calificación, historial, progreso, recursos y cierre de sesión.

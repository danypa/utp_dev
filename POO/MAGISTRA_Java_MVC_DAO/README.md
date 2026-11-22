# MAGISTRA - Java Escritorio (POO + MVC + DAO + JDBC + MySQL)

## Requisitos
- JDK 17
- MySQL Server 8.0 o superior
- NetBeans (recomendado)

## Driver JDBC MySQL
El `pom.xml` declara **MySQL Connector/J 26.10.0**. Maven descarga el driver y lo agrega al classpath al compilar o ejecutar el proyecto.

## Base de datos
1. Ejecute `sql/00_instalacion_completa.sql` en MySQL Workbench o MySQL Client.
2. El script crea `magistra`, tablas, relaciones, categorías Ascenso/Nombramiento, temas, recursos y preguntas demo: cinco preguntas por cada tema de práctica.
3. Si la base ya existe, ejecute `sql/03_preguntas_adicionales.sql` para agregar tres preguntas por tema; el script se puede volver a ejecutar sin duplicarlas.
4. Los datos demo no representan un banco oficial de preguntas.

## Configuración de conexión
La aplicación se conecta a `localhost:3306/magistra` con el usuario `root`. Puedes cambiar la URL y el usuario en `ConexionBD.java`.

Si tu servidor requiere contraseña, crea `conexion.local.properties` junto a `pom.xml` y escribe la clave después del signo `=`.

```properties
password=
```

Ejecuta el proyecto desde esta carpeta para que encuentre el archivo local. También puedes definir la variable de entorno `MAGISTRA_DB_PASSWORD`, que tiene prioridad sobre el archivo. Si ambas opciones están sin configurar, la aplicación usa una contraseña vacía.

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

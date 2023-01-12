# Obtener tarifa a aplicar

El objetivo el endpoint desarrollado es obtener la tarifa a aplicar a un producto en una rama (Brand).

# Historias de usuario

1. Como el vendedor Quiero obtener el precio aplicar Para cerrar una venta.

Para ello se habilitará un endpoint al que se le pasaran la fecha de aplicación la rama y el producto, este deberá devolver el precio a aplicar o un error cuando no encuentre precio para la información suministrada.

Cuando se encuentren varios precios a aplicar, se deberá elegir el precio con mayor prioridad.

# Diseño técnico

1. Requisitos

- Java sdk 11 (jdk-11.0.10)
- Maven (apache-maven-3.8.7)
- Postman
- Idle (IntelliJ IDEA 2022.3.1 (Community Edition)). Es occional.
- Git

1. Introducción

La aplicación esta desarrollada en Java y se usa principalmente el ecosistema de Spring Boot.

A continuación, describo lo utilizado en el desarrollo de la aplicación

Java 11. Permite programación funcional y otras ventajas.

Spring Boot: se ha usado la versión 2.7.7. Dentro del ecosistema de spring boot hemos usado los starters siguientes:

- spring-boot-starter-web: Empaqueta el spring framework web y es necesario para poder desarrollar el servicio Rest
- spring-boot-starter-data-jpa: empaqueta las funciones para acceso a base de datos. Contiene hibernate, un ORM que mapea las clases de java a tablas de base de datos.
- spring-boot-starter-test: empaqueta las funcionalidades para realizar test

Hay un repository del proyecto en GitHub [ralapont/api-tarifas: ejercicio api rest de tarifas (github.com)](https://github.com/ralapont/api-tarifas) donde se puede encontrar el proyecto

También usamos lo siguiente:

- Lombok: facilita la creación y mantenimiento de los javabeans.
- Modelmapper: facilita el mapeo entre javabeans. Mapea los dtos con las entities y viceversa.
- jackson-datatype-jsr310: permite el uso de los tipos de java 8 en los procesos de serialización de objetos
- Junit jupiter: creación de los test.

1. Base de datos

Como base de datos, en esta aplicación usamos H2 embebida. Los parámetros de conexión serán los siguiente:

- Datasource: jdbc:h2:mem:testdb
- Driver: org.h2.Driver
- Username: sa
- Password: password

Con la aplicación arrancada, se puede acceder al UI de H2 desde un browser con la url [http://localhost:8080/h2-ui](http://localhost:8080/h2-ui)

![](RackMultipart20230112-1-u2003w_html_f20d2b6d905f03a4.png)

Las tablas se crean a partir de las clases Entities que definamos en el proyecto, en nuestro caso BrandEntity que almacena las ramas y PricesEntity que almacenara los precios por rama, producto y fechas de aplicación entre otros.

También estas tablas serán pobladas al arrancar la aplicación con los datos de ejemplo que nos suministran en la definición de la prueba. Para esto, creamos un fichero SQL que contiene los insert a las tablas data.sql que situamos en la carpeta de recursos.

![](RackMultipart20230112-1-u2003w_html_59c2bd66b7ac0072.png)

![](RackMultipart20230112-1-u2003w_html_14c5015c97a172ce.png)

Estas tablas están relacionadas como muestra el siguiente diagrama entidad relación:

![](RackMultipart20230112-1-u2003w_html_9675fa2545078bef.png)

En las siguientes tablas describimos los campos de las tablas

| BRAND |
| --- |
| Campo | Tipo | Descripción | Pk | Fk |
| Id | BigInt | Identificador de Brand | ![](RackMultipart20230112-1-u2003w_html_eaa17d7a4a49bbbf.png) |
|
| Description | Character Varying | Nombre de Brand |
|
|

| PRICES |
| --- |
| Campo | Tipo | Descripción | Pk | Fk |
| Id | BigInt | Identificador de Price | ![](RackMultipart20230112-1-u2003w_html_eaa17d7a4a49bbbf.png) |
|
| Brand\_id | BigInt | Identificador de Brand |
| ![](RackMultipart20230112-1-u2003w_html_eaa17d7a4a49bbbf.png) |
| Start\_date | Timestamp | Fecha y hora de comienzo de aplicación del precio |
|
|
| End\_date | Timestamp | Fecha y hora de fin de aplicación del precio |
|
|
| Price\_list | Integer | Identificador de la tarifa aplicable |
|
|
| priority | Integer | Desambiguador de aplicación de precios. Si dos tarifas coinciden en un rango de fechas se aplica la de mayor prioridad (mayor valor numérico) |
|
|
| Price | Double Precisión | Precio final de venta |
|
|
| Curr | Charecter Varying | Codigo iso de la moneda |
|
|

1. Implementación

La estructura de paquetes de la aplicación la podemos representar en el siguiente diagrama:

![](RackMultipart20230112-1-u2003w_html_a61d9a29f971f06.png)

- Controller: Implemeta el Servicio Rest (PriceController) y el tratamiento de errores lanzado por el controller (PriceControllerAdvice). Usa el paquete Dtos para mandar información al exterior. Usa el paquete Exception para comunicar errores de negocio con el exterior
- Service: Implementa la capa de negocio (PriceService). Usa el paquete entities para interaccionar con la base de datos y el paquete Dtos para interaccionar con el Controller
- Repository: Implementa el acceso a la base de datos (PricesRepository). Usa el paquete Entities donde se definen los datos a mapear con la base de datos.

Para obtener los precios por Brand, producto y fecha de aplicación se usa una query nombrada en JPQL, esta query se puede encontrar en la clase PriceEntity. La podemos ver a continuación

![Shape1](RackMultipart20230112-1-u2003w_html_9c465b03e316049e.gif)

_select p_

_from PRICES p_

_where p.brandId.id = ?1_

_and p.productId = ?2_

_and p.startDate \<= ?3_

_and p.endDate \> ?3_

También tenemos los siguientes paquetes:

- Config: paquetes que instancian y configuran Beans de spring para inyectarlos en los anteriores (ejemplo el Modelmaper)

El siguiente gráfico, muestra los paquetes y sus clases implementadas en la aplicación

![](RackMultipart20230112-1-u2003w_html_b9c20dd409cf850e.png)

1. Pruebas

Para la implementación de los test se ha usado la herramienta Junit 5 jupiter.

En la clase PricesControllerTest se implementan los test de integración del endpoint. No se mockea y en el primer test que aparece, getRateToApplyTest, se ejecutan los cinco test requeridos, es decir:

- Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)
- Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)
- Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)
- Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)
- Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)

En el segundo test se prueba que pasa si con los datos de entrada, no se obtiene ningún precio.

En la siguiente figura, se muestra los resultados del test ejecutados desde el idle.

![](RackMultipart20230112-1-u2003w_html_4fc960e11bee5d0b.png)

En la clase PriceServiceTest se implementan dos test

En el primer test se mockea las llamadas al repository y al modelmapper.

En la segunda prueba se testea el caso de que no se encuentren precios para los datos de entrada.

En la siguiente figura se puede ver la ejecución de los test del servicio

![](RackMultipart20230112-1-u2003w_html_3908601072edf5c.png)

En la clase PriceRespository se hace la prueba de la query creada para obtener los precios por rama, producto y fecha de aplicación.

En la siguiente figura, se muestra la ejecución de este test desde el idle.

![](RackMultipart20230112-1-u2003w_html_69eac68d273bd91e.png)

Con los test implementados, se cubre el 100% del Controller, Service y Repository

![](RackMultipart20230112-1-u2003w_html_a71fd6ca1da0b937.png)

1. Operativa

Para la construcción y ejecución de la aplicación se usa la herramienta Mavel.

En el fichero pom.xml se pueden encontrar las dependencias, etc.

A continuación, describimos como compilar, desplegar y ejecutar la aplicación usando comandos Maven desde la consola.

Situandose en el directorio root del proyecto:

mvn clean package: compila la aplicación y genera el fichero jar

![](RackMultipart20230112-1-u2003w_html_3a9e696f659bf0fe.png)

mvn test: ejecuta los test

![](RackMultipart20230112-1-u2003w_html_2db6dc44a88035ee.png)

mvn exec:java -Dexec.mainClass="com.example.apitarifas.ApiTarifasApplication": ejecuta la aplicación

![](RackMultipart20230112-1-u2003w_html_b5b47d7165b2e6b.png)

A continuación, desde la herramienta Postman se puede invocar al servicio rest

![](RackMultipart20230112-1-u2003w_html_17a11711d09edcba.png)
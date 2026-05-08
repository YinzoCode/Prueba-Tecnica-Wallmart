# Automation Framework — Playwright + Cucumber + Java

Proyecto de automatización de pruebas funcionales construido con **Playwright**, **Cucumber (Gherkin)** y **Java**, aplicando buenas prácticas de la industria como Page Object Model (POM), inyección de dependencias y reutilización de clases.

---
## Flujos seleccionados para pruebas
Login exitoso
Verificar que un usuario con credenciales válidas puede iniciar sesión correctamente es fundamental para el negocio porque garantiza que los clientes pueden acceder a la plataforma sin fricciones. Si este flujo falla, el usuario no puede comprar, gestionar su cuenta ni completar ninguna transacción — lo que se traduce directamente en pérdida de ventas y clientes. 

Login fallido con control de errores
Probar que el sistema rechaza correctamente credenciales inválidas, vacías o con formato incorrecto es igual de crítico que probar el acceso exitoso. Desde el punto de vista del negocio, un sistema que no valida correctamente los errores de login expone la plataforma a riesgos de seguridad — como accesos no autorizados o ataques de fuerza bruta — y daña la confianza del usuario si no recibe mensajes claros cuando se equivoca. Además, un manejo deficiente de errores puede dejar al cliente atrapado sin saber qué hacer, aumentando la tasa de abandono y generando costos en soporte al cliente.

Carrito de compras y confirmacion de calculo de valores
El carrito de compras es el núcleo del proceso de venta en cualquier tienda en línea, por lo que garantizar que funcione correctamente es una prioridad de negocio. Si un producto no se agrega al carrito o la cantidad no se refleja bien, el cliente abandona la compra y busca una alternativa en la competencia, lo que se traduce en pérdida directa de ingresos. Igualmente crítica es la precisión en los cálculos de precios — un error en el precio unitario, en la multiplicación por cantidad o en el total del carrito puede resultar en cobros incorrectos al cliente, generando devoluciones, disputas y pérdida de confianza, o bien en pérdidas económicas para el negocio si se cobra de menos. Automatizar estas validaciones permite detectar cualquier regresión de forma inmediata cada vez que el sistema cambia, asegurando que el cliente siempre vea información veraz y que el flujo de compra funcione de extremo a extremo.

## Tecnologías utilizadas

| Herramienta | Versión | Propósito |
|---|---|---|
| Java (JDK) | 17+ | Lenguaje base del proyecto |
| Maven | 3.9+ | Gestión de dependencias y ejecución |
| Playwright | 1.44.0 | Control del navegador (Chromium, Firefox, WebKit) |
| Cucumber | 7.18.0 | Lectura e interpretación de escenarios Gherkin |
| Gherkin | — | Lenguaje para escribir escenarios en inglés legible |
| JUnit | 4.13.2 | Motor de ejecución de pruebas |
| PicoContainer | 2.15 | Inyección de dependencias entre clases de Steps |

---

## Buenas prácticas aplicadas

###  Page Object Model (POM)
Cada página web tiene su propia clase Java que centraliza todos los selectores y acciones. Los Steps nunca interactúan directamente con el DOM — solo llaman métodos de las Pages.

```
pages/
├── HomePage.java         ← selectores y acciones de la home
├── LoginPage.java        ← selectores y acciones del login
└── ShoppingCartPage.java ← selectores y acciones del carrito
```

### Inyección de dependencias con PicoContainer
En lugar de herencia (prohibida por Cucumber para clases con hooks), se usa `BrowserContext` como clase compartida inyectada automáticamente por PicoContainer en cada clase de Steps. Esto garantiza que todas las clases compartan la misma instancia del navegador sin duplicar código.

### Reutilización de Steps
Steps genéricos como `"the user clicks on {string}"` son compartidos entre múltiples features usando un `switch` que delega a la Page correcta. Esto evita duplicación de código.

### Background para pasos comunes
Los pasos que se repiten en todos los escenarios de un feature (como navegación inicial o login) se centralizan en un `Background`, ejecutándose automáticamente antes de cada escenario.

###  Tags para filtrado de ejecución
Cada escenario tiene tags (`@loginSuccessfully`, `@loginFailed`, `@addToCart`) que permiten ejecutar subconjuntos específicos de pruebas sin modificar código.

###  DataTable para datos estructurados
Los datos de formularios y cantidades se pasan desde el `.feature` usando tablas de Cucumber, manteniendo los datos en Gherkin y la lógica en Java.

###  Locators robustos
Se evitan selectores frágiles como XPath absolutos o IDs dinámicos. Se usan atributos estables (`data-original-title`, `name^=`, clases CSS) combinados con `.nth()` de Playwright para selección por posición.

---

##  Estructura del proyecto

```
mi-proyecto-playwright/
├── pom.xml                                    ← dependencias y configuración Maven
└── src/
    └── test/
        ├── java/
        │   └── com/miprueba/
        │       ├── pages/                     ← Page Objects (una clase por página)
        │       │   ├── HomePage.java
        │       │   ├── LoginPage.java
        │       │   └── ShoppingCartPage.java
        │       ├── steps/                     ← Step Definitions
        │       │   ├── Base.java              ← hooks @Before y @After
        │       │   ├── BrowserContext.java    ← contexto compartido del navegador
        │       │   ├── LoginSteps.java        ← pasos de login
        │       │   └── ShoppingCartSteps.java ← pasos del carrito
        │       └── runner/
        │           └── TestRunner.java        ← configuración de Cucumber
        └── resources/
            └── features/                      ← escenarios Gherkin
                ├── login.feature
                └── shoppingCart.feature
```

### Descripción de cada carpeta

**`pages/`** — Implementa el patrón POM. Cada archivo representa una página web y contiene sus locators como constantes `private static final` y sus acciones como métodos públicos. Nunca contiene lógica de validación.

**`steps/`** — Contiene los Step Definitions que conectan Gherkin con Java. `Base.java` maneja la apertura y cierre del navegador. `BrowserContext.java` es la clase de contexto compartida entre Steps via PicoContainer.

**`runner/`** — `TestRunner.java` configura Cucumber: dónde están los features, dónde están los Steps, qué tags ejecutar y qué formato de reporte generar.

**`features/`** — Archivos `.feature` escritos en Gherkin. Contienen los escenarios de prueba en inglés legible, con datos en tablas DataTable o Scenario Outline.

---

## Requisitos previos

### 1. Java JDK 17+
Descarga desde: https://adoptium.net

Verifica la instalación:
```bash
java -version
```

### 2. Maven 3.9+
Descarga desde: https://maven.apache.org/download.cgi

Descomprime en `C:\maven` y agrega a variables de entorno:
- Variable nueva: `MAVEN_HOME` = `C:\maven\apache-maven-3.9.x`
- Agrega a `Path`: `%MAVEN_HOME%\bin`

Verifica la instalación:
```bash
mvn -version
```

### 3. Visual Studio Code (recomendado)
Descarga desde: https://code.visualstudio.com

Extensiones requeridas:
- **Extension Pack for Java** (Microsoft)
- **Cucumber (Gherkin) Full Support**
- **Maven for Java**

---

##  Instalación y ejecución

### 1. Clonar el repositorio
```bash
git clone https://github.com/TuUsuario/mi-proyecto-playwright.git
cd mi-proyecto-playwright
```

### 2. Descargar dependencias
```bash
mvn install -DskipTests
```

### 3. Instalar navegadores de Playwright
Este paso solo se hace una vez:
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### 4. Ejecutar todas las pruebas
```bash
mvn clean test
```

### 5. Ejecutar por tag específico
```bash
# Solo pruebas de login exitoso
mvn clean test -Dcucumber.filter.tags="@loginSuccessfully"

# Solo pruebas de login fallido
mvn clean test -Dcucumber.filter.tags="@loginFailed"

# Solo pruebas del carrito
mvn clean test -Dcucumber.filter.tags="@addToCart"
```

---

## Reporte de resultados

Después de ejecutar las pruebas, el reporte HTML se genera automáticamente en:
```
target/report.html
```

Ábrelo en cualquier navegador para ver el resultado detallado de cada escenario.

---

## Aplicación bajo prueba

Las pruebas apuntan a: https://opencart.abstracta.us

**Escenarios cubiertos:**

- Login exitoso con credenciales válidas
- Login fallido con credenciales inválidas o vacías
- Agregar productos al carrito y modificar cantidades
- Validación de totales y precios en el carrito

---

## Notas

El navegador se ejecuta en modo **visible** (`headless: false`) para facilitar la observación de las pruebas durante el desarrollo. Para CI/CD, cambiar a `headless: true` en `BrowserContext.java`.

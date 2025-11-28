# Oficina Virtual 2.0

Automatizaciones de pruebas para Oficina Virtual (BCI Seguros)

## Descripción
Proyecto con pruebas automatizadas (TestNG + Selenium) para el flujo de Oficina Virtual.

## Requisitos
- Java 17+ (o la versión indicada en `pom.xml`)
- Maven
- Google Chrome (compatible con la versión de ChromeDriver usada)
- Conexión a Internet (WebDriverManager descarga driver automáticamente)

## Ejecutar pruebas
Desde la raíz del proyecto ejecuta:

```powershell
mvn test
```

Los reportes y capturas quedan en `test-output/` y `target/surefire-reports/`.

## Notas
- El proyecto usa `WebDriverManager` para administrar ChromeDriver.
- Datos de prueba en `src/main/resources/testdata/Principal.json`.

## Repositorio
Clonado desde: `https://github.com/IgnacioRiquelme/Oficina_Virtual2.0`

## Contacto
Ignacio Riquelme
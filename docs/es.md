Este plugin proporciona un conjunto de pasos para realizar pruebas de rendimiento a través de JMeter DSL 

---
## Tabla de contenido

---
## Configuración

###  `jmeter.baseURL`
Establece la URL base para las pruebas de JMeter, donde se definirá el punto de inicio para todas las llamadas HTTP que se realizen. 

Valor por defecto:
```
 `http://localhost:8080`
```

Ejemplo:
```yaml
jmeter:
  baseURL: https://example.org/api/v2
```

<br /><br />

## Salidas del plugin
JMeter puede configurarse para enviar resultados a diferentes destinos como InfluxDB, archivos CSV y reportes HTML.

###  `jmeter.output.influxdb.enabled`
Determina si se habilita la salida de resultados a InfluxDB. Esto te permite mantener un registro de todas las estadísticas de ejecución a través de Grafana, además de ver las métricas que te interesen de una manera gráfica.
Es importante saber que se usa un servidor InfluxDB y Grafana existente, por lo que si se desea probar localmente, se deberá ejecutar un 'docker compose up' dentro de [este](https://github.com/abstracta/jmeter-java-dsl/tree/master/docs/guide/reporting/real-time/influxdb) directorio.
Una vez los contenedores se hayan iniciado, puedes abrir grafana en [ http://localhost:3000]( http://localhost:3000).


Valor por defecto:
```
 `false`
```

Ejemplo:
```yaml
jmeter:
  output:
    influxdb:
      enabled: true
```

<br /><br />

### `jmeter.output.influxdb.url`
Define la URL del endpoint de InfluxDB a la que JMeter enviará los resultados. 

Valor por defecto:
```
 `"http://localhost:8086/write?db=jmeter"`
```

Ejemplo:
```yaml
jmeter:
  output:
    influxdb:
      url: http://tuServidorInfluxDB.com/write?db=nombreDeTuBaseDeDatos
```

<br /><br />

### `jmeter.output.csv.enabled`
Habilita la generación de resultados de pruebas en un archivo CSV. El archivo lo podrás visualizar a través de excel, a través de datos -> -> obtener datos -> de un archivo ->  de texto/csv y ahi seleccionas el archivo generado.

Valor por defecto:
```
 `true`
```

Ejemplo:
```yaml
jmeter:
  output:
    csv:
      enabled: false
```

<br /><br />

### `jmeter.output.csv.path`
Especifica la ruta donde se guardará el archivo CSV con los resultados de las pruebas.

Valor por defecto:
```
 `"./test-results-csv"`
```

Ejemplo:
```yaml
jmeter:
  output:
    csv:
      path: ./resultadosDePruebas/pruebas-csv
```

<br /><br />


### `jmeter.output.html.enabled`
Habilita la creación de un reporte en formato HTML con los resultados de las pruebas.

Valor por defecto:
```
 `false`
```

Ejemplo:
```yaml
jmeter:
  output:
    html:
      enabled: true
```

<br /><br />

### `jmeter.output.html.path`
Especifica la ruta donde se guardará el reporte HTML con los resultados de las pruebas.

Valor por defecto:
```
 `"./test-results-html"`
```

Ejemplo:
```yaml
jmeter:
  output:
    html:
      path: ./resultadosDePruebas/pruebas-html
```

---
## Pasos

### Definir URL base
```
la URL base {text}
```
Establece la ruta base. Este paso es equivalente a configurar la propiedad [`rest.baseURL`](#restbaseurl).

#### Parámetros:
| Nombre | Wakamiti type | Descripción |
|--------|---------------|-------------|
| `url`  | `text`        | URL base    |

#### Ejemplos:
```gherkin
  Dada la URL base https//example.org/api
```

<br /><br />

### Definir entrada CSV
```
un fichero con los siguientes datos {fichero:text}
```
Establece un fichero CSV como entrada para las pruebas de JMeter.

#### Parámetros:
| Nombre     | Wakamiti type | Descripción                                  |
|------------|---------------|----------------------------------------------|
| `fichero`  | `text`        | Ruta al fichero CSV con los datos de entrada |

#### Ejemplos:
```gherkin
   Dado un fichero con los siguientes datos './usuarios.csv'
```

<br /><br />

### Definir entrada CSV con variables
```
un fichero con los siguientes datos {fichero:text} trabajando con las variables:
```
Establece un fichero CSV como entrada y especifica las variables a utilizar durante las pruebas de JMeter. Permite filtrar y usar solo ciertas columnas del fichero CSV como variables dentro de la prueba.

#### Parámetros:
| Nombre     | Wakamiti type | Descripción                                       |
|------------|---------------|---------------------------------------------------|
| `fichero`  | `text`        | Ruta al fichero CSV con los datos de entrada      |
|            | `DataTable`   | Una tabla que especifica las variables a utilizar |

#### Ejemplos:
```gherkin
Dado un fichero con los siguientes datos 'usuarios.csv' trabajando con las variables:
   | username |
   | password |
```

<br /><br />

### Realizar petición GET
```
hago un GET al endpoint {service:text}
```
Realiza una petición GET al endpoint especificado.

#### Parámetros:
| Nombre    | Wakamiti type | Descripción  |
|-----------|---------------|--------------|
| `service` | `text`        | Segmento URL |

#### Ejemplos:
```gherkin
 hago un GET al endpoint '/api/usuarios'
```

<br /><br />

### Realizar petición PUT
```
hago un PUT al endpoint {service:text} con el siguiente mensaje:
```
Realiza una petición GET al endpoint especificado.

#### Parámetros:
| Nombre    | Wakamiti type | Descripción              |
|-----------|---------------|--------------------------|
| `service` | `text`        | Segmento URL             |
|           | `document`    | El cuerpo de la petición |

#### Ejemplos:
```gherkin
 hago un PUT al endpoint '/api/usuarios/123' con el siguiente mensaje:
    """
    {
        "nombre": "Ana",
        "apellido": "Lopez"
    }
    """
```

<br /><br />

### Realizar petición POST
```
hago un POST al endpoint {service:text} con el siguiente mensaje:
```
Realiza una petición GET al endpoint especificado.

#### Parámetros:
| Nombre    | Wakamiti type | Descripción              |
|-----------|---------------|--------------------------|
| `service` | `text`        | Segmento URL             |
|           | `document`    | El cuerpo de la petición |

#### Ejemplos:
```gherkin
 hago un POST al endpoint '/api/usuarios' con el siguiente mensaje:
    """
    {
        "nombre": "Carlos",
        "apellido": "Garcia"
    }
    """
```

<br /><br />

### Ejecutar prueba de humo
```
ejecuto una prueba de humo
```
Ejecuta una prueba de humo básica para verificar que el sistema bajo prueba está operativo.

#### Ejemplos:
```gherkin
 Cuando ejecuto una prueba de humo
```

<br /><br />

### Ejecutar prueba de carga
```
ejecuto una prueba de carga con {usuarios:int} usuarios durante {duracion:int} minutos
```
Ejecuta una prueba de carga simulando la actividad de un número específico de usuarios durante un tiempo determinado.

#### Parámetros:
| Nombre    | Wakamiti type | Descripción              |
|-----------|---------------|--------------------------|
| `usuarios`|   `int`       | Número de usuarios       |
| `duracion`|   `int`       | Tiempo de la prueba      |

#### Ejemplos:
```gherkin
 Cuando ejecuto una prueba de carga con 500 usuarios durante 10 minutos
```

<br /><br />

### Ejecutar prueba de estrés
```
ejecuto una prueba de estrés comenzando con {usuarios:int} usuarios, incrementando en {incrementoUsuarios:int} hasta {maxUsuarios:int} usuarios durante {duracion:int} minutos
```
Ejecuta una prueba de estrés incrementando gradualmente la carga de usuarios y manteniendo dicha carga para identificar el punto de ruptura del sistema.

#### Parámetros:
| Nombre              | Wakamiti type | Descripción                                   |
|---------------------|---------------|-----------------------------------------------|
| `usuarios`          |   `int`       | Número inicial de usuarios                    |
| `incrementoUsuarios`|   `int`       | Incremento de usuarios por intervalo          |
| `maxUsuarios`       |   `int`       | Máximo número de usuarios                     |
| `duracion`          |   `int`       | Tiempo que se mantiene la carga por intervalo |

#### Ejemplos:
```gherkin
 Cuando ejecuto una prueba de estrés comenzando con 100 usuarios, incrementando en 100 hasta 2000 usuarios durante 5 minutos
```

<br /><br />

### Ejecutar prueba de picos
```
ejecuto una prueba de picos con {numeroPicos:int} picos de {usuariosPico:int} usuarios, bajando a {usuariosFueraPico:int} usuarios durante {duracion:int} minutos
```
Ejecuta una prueba de picos para simular cargas irregulares en el sistema, alternando entre un número alto y bajo de usuarios.

#### Parámetros:
| Nombre              | Wakamiti type | Descripción                                              |
|---------------------|---------------|----------------------------------------------------------|
| `numeroPicos`       |   `int`       | Número de picos de carga                                 |
| `usuariosPico`      |   `int`       | Número de usuarios en cada pico                          |
| `usuariosFueraPico` |   `int`       | Número de usuarios fuera de los picos                    |
| `duracion`          |   `int`       | Tiempo que se mantiene la carga antes de simular un pico |

#### Ejemplos:
```gherkin
 Cuando ejecuto una prueba de picos con 3 picos de 1000 usuarios, bajando a 200 usuarios durante 5 minutos
```

<br /><br />

### Ejecutar prueba de límite operativo
```
ejecuto una prueba de límite operativo comenzando con {usuarios:int} usuarios, incrementando en {incrementoUsuarios:int} hasta {maxUsuarios:int} usuarios con rampas de subida de {duracion:int} minutos
```
Ejecuta una prueba para encontrar el límite operativo del sistema, aumentando progresivamente el número de usuarios.

#### Parámetros:
| Nombre              | Wakamiti type | Descripción                                              |
|---------------------|---------------|----------------------------------------------------------|
| `usuarios   `       |   `int`       | Número inicial de usuarios                               |
| `incrementoUsuarios`|   `int`       | Incremento de usuarios                                   |
| `usuariosFueraPico` |   `int`       | Número máximo de usuarios                                |
| `duracion`          |   `int`       | Tiempo de rampa de subida antes de aumentar los usuarios |

#### Ejemplos:
```gherkin
 Cuando ejecuto una prueba de límite operativo comenzando con 100 usuarios, incrementando en 100 hasta 5000 usuarios con rampas de subida de 2 minutos
```

<br /><br />

### Comprobar percentil de tiempo de respuesta
```
comprueba que el percentil {percentil:int} de tiempo de respuesta es menor que {duracionTest:int} segundos
```
Verifica que el percentil especificado del tiempo de respuesta sea menor que la duración dada.

#### Parámetros:
| Nombre        | Wakamiti type | Descripción                                   |
|---------------|---------------|-----------------------------------------------|
| `percentil`   |   `int`       | Percentil del tiempo de respuesta a comprobar |
| `duracionTest`|   `int`       | Duración máxima esperada                      |

#### Ejemplos:
```gherkin
 Entonces comprueba que el percentil 95 de tiempo de respuesta es menor que 2 segundos
```

<br /><br />

### Comprobar tiempo de respuesta medio
```
comprueba que el tiempo de respuesta medio es menor que {duracionTest:int} segundos
```
Verifica que el tiempo de respuesta medio sea menor que la duración dada.

#### Parámetros:
| Nombre        | Wakamiti type | Descripción                                   |
|---------------|---------------|-----------------------------------------------|
| `duracionTest`|   `int`       | Duración máxima esperada                      |

#### Ejemplos:
```gherkin
 Entonces comprueba que el tiempo de respuesta medio es menor que 2 segundos
```

<br /><br />

### Comprobar errores en las peticiones
```
comprueba que el número de peticiones que han devuelto error es menor que {errores:int}
```
Verifica que el número de peticiones que han devuelto un error es menor que el número especificado.

#### Parámetros:
| Nombre   | Wakamiti type | Descripción               |
|----------|---------------|---------------------------|
| `errores`|   `int`       | Errores máximos esperados |

#### Ejemplos:
```gherkin
 Entonces comprueba que el número de peticiones que han devuelto error es menor que 10
```


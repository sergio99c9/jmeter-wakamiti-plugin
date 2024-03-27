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
| 'duracion'|   `int`       | Tiempo de la prueba      |

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
| 'duracion'          |   `int`       | Tiempo que se mantiene la carga por intervalo |

#### Ejemplos:
```gherkin
 Cuando ejecuto una prueba de estrés comenzando con 100 usuarios, incrementando en 100 hasta 2000 usuarios durante 5 minutos
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
| 'duracion'          |   `int`       | Tiempo que se mantiene la carga por intervalo |

#### Ejemplos:
```gherkin
 Cuando ejecuto una prueba de estrés comenzando con 100 usuarios, incrementando en 100 hasta 2000 usuarios durante 5 minutos
```

<br /><br />









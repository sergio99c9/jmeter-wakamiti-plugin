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


#language: es
Característica: : jmeter

 #   Scenario: Prueba de humo
  #     Given la URL base 'http://localhost:8888'
  #     When ejecuto una prueba de humo
  #     Then comprueba que el percentil 99 de tiempo de respuesta es menor que 50 segundos

    Escenario: Prueba de carga
       Dada la URL base 'http://localhost:8888'
       Cuando ejecuto una prueba de carga con 400 usuarios durante 2 minutos
       Entonces comprueba que el percentil 99 de tiempo de respuesta es menor que 2 segundos


  #   Scenario: Prueba de límite operativo
  #     Given la URL base 'http://localhost:8888'
  #     When ejecuto una prueba de límite operativo comenzando con 1000 usuarios, incrementando en 1000 hasta 5000 usuarios con con rampas de subida de 1 minutos
  #     Then comprueba que el percentil 99 de tiempo de respuesta es menor que 50 segundos



 #  Scenario: Prueba de estres
  #    Given la URL base 'http://localhost:8888'
  #    When ejecuto una prueba de estrés comenzando con 100 usuarios, incrementando en 100 hasta 500 usuarios durante 2 minutos
  #    Then comprueba que el percentil 99 de tiempo de respuesta es menor que 5 segundos


 #  Scenario: Prueba de picos
  #    Given la URL base 'http://localhost:8888'
  #    When ejecuto una prueba de picos con 2 picos de 100 usuarios, bajando a 20 usuarios durante 3 minutos
 #     Then comprueba que el percentil 99 de tiempo de respuesta es menor que 5 segundos




#language: es
Característica: : jmeter

  #   Escenario: Prueba de humo
  #     Dada la URL base 'http://localhost:8888'
  #     Cuando se ejecuta una prueba de humo
  #     Entonces comprueba que el percentil 99 de tiempo de respuesta es menor que 50 segundos

    Escenario: Prueba de carga
       Dada la URL base 'http://localhost:8888'
       Cuando se ejecuta una prueba de carga con 200 usuarios durante 2 minutos
       Entonces comprueba que el percentil 99 de tiempo de respuesta es menor que 3 segundos


  #   Escenario: Prueba de límite operativo
  #     Dada la URL base 'http://localhost:8888'
  #     Cuando se ejecuta una prueba de límite operativo comenzando con 1000 usuarios, incrementando en 1000 hasta 5000 usuarios con con rampas de subida de 1 minutos
  #     Entonces comprueba que el percentil 99 de tiempo de respuesta es menor que 50 segundos



   Escenario: Prueba de estres
      Dada la URL base 'http://localhost:8888'
      Cuando se ejecuta una prueba de estrés comenzando con 50 usuarios, incrementando en 50 hasta 200 usuarios durante 1 minutos
      Entonces comprueba que el percentil 99 de tiempo de respuesta es menor que 3 segundos


   Escenario: Prueba de picos
      Dada la URL base 'http://localhost:8888'
      Cuando se ejecuta una prueba de picos con 2 picos de 200 usuarios, bajando a 50 usuarios durante 1 minutos
      Entonces comprueba que el percentil 99 de tiempo de respuesta es menor que 3 segundos



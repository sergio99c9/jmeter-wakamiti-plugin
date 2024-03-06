#
# Fill this file with different scenarios using the steps from the plugin
#
Feature: jmeter


   Scenario: Prueba de carga
      Given la URL base 'http://localhost'
      When ejecuto una prueba de carga con 50 usuarios durante 1 minutos
      Then comprueba que el percentil 99 de tiempo de respuesta es menor que 5 segundos


   Scenario: Prueba de estres
      Given la URL base 'http://localhost'
      When ejecuto una prueba de estrés comenzando con 10 usuarios, incrementando en 10 hasta 30 usuarios durante 1 minutos
      Then comprueba que el percentil 99 de tiempo de respuesta es menor que 5 segundos


   Scenario: Prueba de picos
      Given la URL base 'http://localhost'
      When ejecuto una prueba de picos con 2 picos de 50 usuarios, bajando a 20 usuarios durante 1 minutos
      Then comprueba que el percentil 99 de tiempo de respuesta es menor que 5 segundos



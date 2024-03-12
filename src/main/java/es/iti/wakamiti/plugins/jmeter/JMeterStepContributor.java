/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package es.iti.wakamiti.plugins.jmeter;

import static org.assertj.core.api.Assertions.assertThat;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;

import us.abstracta.jmeter.javadsl.core.DslTestPlan;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;
import es.iti.commons.jext.Extension;
import es.iti.wakamiti.api.annotations.I18nResource;
import es.iti.wakamiti.api.annotations.Step;
import es.iti.wakamiti.api.extensions.StepContributor;
import es.iti.wakamiti.api.util.WakamitiLogger;
import org.slf4j.Logger;
import us.abstracta.jmeter.javadsl.core.threadgroups.DslDefaultThreadGroup;


@Extension(provider = "es.iti.wakamiti", name = "jmeter", version = "1.1")
@I18nResource("es_iti_wakamiti_jmeter")
public class JMeterStepContributor implements StepContributor {

    private final Logger logger = WakamitiLogger.forClass(JMeterStepContributor.class);


    protected String baseUrl;
    public TestPlanStats lastTestStats;
    private boolean influxDBEnabled;
    private boolean csvEnabled;
    private String influxDBUrl;
    private String csvPath;

    public void configureOutputOptions(boolean influxDBEnabled, boolean csvEnabled, String influxDBUrl, String csvPath) {
        this.influxDBEnabled = influxDBEnabled;
        this.csvEnabled = csvEnabled;
        this.influxDBUrl = influxDBUrl;
        this.csvPath = csvPath;
    }
    @Step(value = "jmeter.define.baseURL")
    public void setBaseURL(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Step(value = "jmeter.test.loadtest", args = {"usuarios:int", "duracion:int"})
    public void EjecutarPruebaCarga(Integer usuarios, Integer duracion) throws IOException {


        DslDefaultThreadGroup threadGroup = threadGroup(usuarios, Duration.ofMinutes(duracion));

        if (influxDBEnabled) {
            threadGroup.children(influxDbListener("http://localhost:8086/write?db=jmeter"));
        }

        if (csvEnabled) {
            threadGroup.children(jtlWriter(csvPath));
        }

        lastTestStats = testPlan(
                threadGroup.children(
                        httpSampler(baseUrl)
                )).run();
    }
    @Step(value = "jmeter.test.stresstest", args = {"usuarios:int", "incrementoUsuarios:int", "maxUsuarios:int", "duracion:int"})
    public void EjecutarPruebaEstres(Integer usuarios, Integer incrementoUsuarios, Integer maxUsuarios, Integer duracion) throws IOException {

        // Calcula el número total de pasos necesarios para llegar de 'usuarios' a 'maxUsuarios' en incrementos de 'incrementoUsuarios'
        int totalPasos = (maxUsuarios - usuarios) / incrementoUsuarios;
        // Crea el grupo de hilos con los usuarios iniciales, incrementando usuarios cada periodo de tiempo especificado
        DslDefaultThreadGroup threadGroup = threadGroup();
        int usuariosActuales = usuarios;
        for (int paso = 0; paso <= totalPasos; paso++) {
            threadGroup = threadGroup.rampToAndHold(usuariosActuales, Duration.ofSeconds(10), Duration.ofMinutes(duracion));
            usuariosActuales += incrementoUsuarios;
        }

        //Disminuir a 0 'usuarios'
        threadGroup = threadGroup.rampTo(0, Duration.ofSeconds(20));

        if (influxDBEnabled) {
            threadGroup.children(influxDbListener("http://localhost:8086/write?db=jmeter"));
        }

        if (csvEnabled) {
            threadGroup.children(jtlWriter(csvPath));
        }
        // Ejecutar el plan de prueba
        lastTestStats = testPlan(
                threadGroup.children(
                        httpSampler(baseUrl)
                )).run();
    }
    @Step(value = "jmeter.test.peaktest", args = {"numeroPicos:int", "usuariosPico:int", "usuariosFueraPico:int", "duracion:int"})
    public void EjecutarPruebaPico(Integer numeroPicos, Integer usuariosPico, Integer usuariosFueraPico, Integer duracion) throws IOException {

        DslDefaultThreadGroup threadGroup = threadGroup(
                usuariosFueraPico,
                Duration.ofMinutes(duracion)
        );

        for (int i = 0; i < numeroPicos; i++) {
            // Sube al pico
            threadGroup = threadGroup.rampTo(usuariosPico, Duration.ofSeconds(20));
            // Baja al número de usuarios fuera del pico
            threadGroup = threadGroup.rampTo(usuariosFueraPico, Duration.ofSeconds(20));
            // Mantiene el número de usuarios fuera del pico
            threadGroup = threadGroup.holdFor(Duration.ofMinutes(duracion));
        }

        //Disminuir a 0 'usuarios'
        threadGroup = threadGroup.rampTo(0, Duration.ofSeconds(20));

        if (influxDBEnabled) {
            threadGroup.children(influxDbListener("http://localhost:8086/write?db=jmeter"));
        }

        if (csvEnabled) {
            threadGroup.children(jtlWriter(csvPath));
        }

        // Ejecutar el plan de prueba
        lastTestStats = testPlan(
                threadGroup.children(
                        httpSampler(baseUrl)
                )).run();

    }
    @Step(value = "jmeter.test.percentil", args = {"percentil:int", "duracionTest:int"})
    public void setPruebaPercentil(Integer percentil, Integer duracionTest) throws IOException {

        if (lastTestStats == null) {
            throw new IllegalStateException("No hay resultados de pruebas almacenados para verificar el percentil 99.");
        }

        switch (percentil){
            case 50:
                assertThat(lastTestStats.overall().sampleTime().median()).isLessThan(Duration.ofSeconds(duracionTest));
                break;
            case 90:
                assertThat(lastTestStats.overall().sampleTime().perc90()).isLessThan(Duration.ofSeconds(duracionTest));
                break;
            case 95:
                assertThat(lastTestStats.overall().sampleTime().perc95()).isLessThan(Duration.ofSeconds(duracionTest));
                break;
            case 99:
                assertThat(lastTestStats.overall().sampleTime().perc99()).isLessThan(Duration.ofSeconds(duracionTest));
                break;
            default:
                throw new IllegalArgumentException("Percentil no soportado: " + percentil);
        }
    }
    @Step(value = "jmeter.test.responseTime", args = "duracionTest:int")
    public void setPruebaResponseTime(Integer duracionTest){

        if (lastTestStats == null) {
            throw new IllegalStateException("No hay resultados de pruebas almacenados para verificar el tiempos de respuesta.");
        }

        assertThat(lastTestStats.overall().sampleTime().mean()).isLessThan(Duration.ofSeconds(duracionTest));

    }

    @Step(value = "jmeter.test.errors", args = "errores:int")
    public void setPruebaResponseErrors(Integer errores){

        if (lastTestStats == null) {
            throw new IllegalStateException("No hay resultados de pruebas almacenados para verificar el tiempos de respuesta.");
        }

        assertThat(lastTestStats.overall().errorsCount()).isLessThan(errores);

    }

}
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


    @Step(value = "jmeter.define.baseURL")
    public void setBaseURL(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /*@Step(value = "jmeter.define.loadtest", args = {"usuarios:int", "duracion:int"})
    public void PruebaCargaBasica(int usuarios, int duracion) throws IOException {

        this.usuarios = usuarios;
        this.duracion = duracion;

    }

    /*@Step(value = "jmeter.define.stresstest", args = {"usuarios:int", "incrementoUsuarios:int", "maxUsuarios:int", "duracion:int"})
    public void PruebaEstresBasica(int usuarios, int maxUsuarios, int incrementoUsuarios, int duracion) throws IOException {

        this.usuarios = usuarios;
        this.incrementoUsuarios = incrementoUsuarios;
        this.maxUsuarios = maxUsuarios;
        this.duracion = duracion;

    }


    @Step(value = "jmeter.define.peaktest", args = {"numeroPicos:int", "usuariosPico:int", "usuariosFueraPico:int", "duracion:int"})
    public void PruebaPicosBasica(int numeroPicos, int usuariosPico, int usuariosFueraPico, int duracion) throws IOException {

        this.numeroPicos = numeroPicos;
        this.usuariosPico = usuariosPico;
        this.usuariosFueraPico = usuariosFueraPico;
        this.duracion = duracion;

    }*/

    @Step(value = "jmeter.test.loadtest", args = {"usuarios:int", "duracion:int"})
    public void EjecutarPruebaCarga(Integer usuarios, Integer duracion) throws IOException {

         lastTestStats = testPlan(threadGroup(usuarios, Duration.ofMinutes(duracion), httpSampler(baseUrl)))
                .run();

    }
    @Step(value = "jmeter.test.stresstest", args = {"usuarios:int", "incrementoUsuarios:int", "maxUsuarios:int", "duracion:int"})
    public void EjecutarPruebaEstres(Integer usuarios, Integer incrementoUsuarios, Integer maxUsuarios, Integer duracion) throws IOException {

        // Calcula el número total de pasos necesarios para llegar de 'usuarios' a 'maxUsuarios' en incrementos de 'incrementoUsuarios'
        int totalPasos = (maxUsuarios - usuarios) / incrementoUsuarios;
        // Crea el grupo de hilos con los usuarios iniciales, incrementando usuarios cada periodo de tiempo especificado
        DslDefaultThreadGroup threadGroup = threadGroup();
        int usuariosActuales = usuarios;
        for (int paso = 0; paso <= totalPasos; paso++) {
            threadGroup = threadGroup.rampToAndHold(usuariosActuales, Duration.ofSeconds(30), Duration.ofMinutes(duracion));
            usuariosActuales += incrementoUsuarios;
        }

        //Disminuir a 0 'usuarios'
        threadGroup = threadGroup.rampTo(0, Duration.ofSeconds(20));

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

        // Ejecutar el plan de prueba
        lastTestStats = testPlan(
                threadGroup.children(
                        httpSampler(baseUrl) // Utiliza la URL base definida previamente
                )).run();

    }
    @Step(value = "jmeter.test.percentile99", args = "duracionTest:int")
    public void setPruebaPercentil(Integer duracionTest) throws IOException {

        if (lastTestStats == null) {
            throw new IllegalStateException("No hay resultados de pruebas almacenados para verificar el percentil 99.");
        }

        assertThat(lastTestStats.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(duracionTest));

    }

}
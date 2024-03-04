/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package es.iti.wakamiti.plugins.jmeter;

import es.iti.commons.jext.Extension;
import es.iti.wakamiti.api.annotations.I18nResource;
import es.iti.wakamiti.api.annotations.Step;
import es.iti.wakamiti.api.extensions.StepContributor;
import es.iti.wakamiti.api.util.WakamitiLogger;
import org.slf4j.Logger;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;


@Extension(provider = "es.iti.wakamiti", name = "jmeter", version = "1.1")
@I18nResource("es_iti_wakamiti_jmeter")
public class JMeterStepContributor implements StepContributor {

    private final Logger logger = WakamitiLogger.forClass(JMeterStepContributor.class);


    private String baseUrl;
    private int usuarios;
    private int duracion;
    private int incrementoUsuarios;
    private int maxUsuarios;
    private int usuariosPico;
    private int usuariosFueraPico;
    private int numeroPicos;
    private TestPlanStats lastTestStats;
    private int duracionTest;


    @Step(value = "jmeter.define.baseURL", args = "baseUrl:text")
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    @Step(value = "jmeter.define.loadtest", args = {"usuarios:int", "duracion:int"})
    public void PruebaCargaBasica(int usuarios, int duracion) {

        this.usuarios = usuarios;
        this.duracion = duracion;

    }


    @Step(value = "jmeter.define.stresstest", args = {"usuarios:int", "maxUsuarios:int", "incrementoUsuarios:int", "duracion:int"})
    public void PruebaEstresBasica(int usuarios, int maxUsuarios, int incrementoUsuarios, int duracion) {

        this.usuarios = usuarios;
        this.maxUsuarios = maxUsuarios;
        this.incrementoUsuarios = incrementoUsuarios;
        this.duracion = duracion;

    }


    @Step(value = "jmeter.define.peaktest", args = {"numeroPicos:int", "usuariosPico:int", "usuariosFueraPico:int", "duracion:int"})
    public void PruebaPicosBasica(int numeroPicos, int usuariosPico, int usuariosFueraPico, int duracion) {

        this.numeroPicos = numeroPicos;
        this.usuariosPico = usuariosPico;
        this.usuariosFueraPico = usuariosFueraPico;
        this.duracion = duracion;

    }
    @Step(value = "jmeter.test.loadtest")
    public void EjecutarPruebaCarga() {

        TestPlanStats lastTestStats = testPlan(threadGroup(usuarios, Duration.ofMinutes(duracion), httpSampler(baseUrl)))
                .run();

    }
    @Step(value = "jmeter.test.stresstest")
    public void EjecutarPruebaEstres(int duracionTest) {

        this.duracionTest = duracionTest;

    }
    @Step(value = "jmeter.test.peaktest")
    public void EjecutarPruebaPico(int duracionTest) {

        this.duracionTest = duracionTest;

    }
    @Step(value = "jmeter.test.percentile99", args = "duracionTest:int")
    public void setPruebaPercentil(int duracionTest) {

        this.duracionTest = duracionTest;

    }

}
/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package es.iti.wakamiti;

import es.iti.commons.jext.Extension;
import es.iti.wakamiti.api.annotations.I18nResource;
import es.iti.wakamiti.api.annotations.Step;
import es.iti.wakamiti.api.extensions.StepContributor;
import es.iti.wakamiti.api.util.WakamitiLogger;
import org.slf4j.Logger;


@Extension(provider = "es.iti.wakamiti", name = "jmeter", version = "1.1")
@I18nResource("es_iti_wakamiti_jmeter")
public class JMeterStepContributor implements StepContributor {

    private final Logger logger = WakamitiLogger.forClass(JMeterStepContributor.class);


    // the following is an example of step definitions


    @Step("jmeter.step1")
    public void step1() {
        //
    }


    @Step("jmeter.step2")
    public void step2() {
        //
    }


    @Step("jmeter.step3")
    public void step3() {
        //
    }

}
/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package es.iti.wakamiti.plugins.jmeter;



import imconfig.Configuration;
import imconfig.Configurer;
import es.iti.commons.jext.Extension;
import es.iti.wakamiti.api.extensions.ConfigContributor;


@Extension(
        provider =  "es.iti.wakamiti",
        name = "jmeter-config",
        version = "1.1",
        extensionPoint =  "es.iti.wakamiti.api.extensions.ConfigContributor"
)
public class JMeterConfigContributor implements ConfigContributor<JMeterStepContributor> {

    private static final Configuration DEFAULTS = Configuration.factory().fromPairs(
        "jmeter.property1", "value1"
    );


    @Override
    public boolean accepts(Object contributor) {
        return contributor instanceof JMeterStepContributor;
    }



    @Override
    public Configuration defaultConfiguration() {
        return DEFAULTS;
    }


    @Override
    public Configurer<JMeterStepContributor> configurer() {
        return this::configure;
    }


    private void configure(JMeterStepContributor contributor, Configuration configuration) {
        // TODO
    }



}
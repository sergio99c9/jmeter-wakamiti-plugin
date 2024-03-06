import es.iti.wakamiti.api.extensions.ConfigContributor;
import es.iti.wakamiti.api.extensions.StepContributor;

open module es.iti.wakamiti {

    exports es.iti.wakamiti.plugins.jmeter;

    requires es.iti.wakamiti.api;
    requires jmeter.java.dsl;
    requires org.assertj.core;
    requires junit;

    uses ConfigContributor;
    uses StepContributor;

    provides es.iti.wakamiti.api.extensions.StepContributor with es.iti.wakamiti.plugins.jmeter.JMeterStepContributor;
    provides es.iti.wakamiti.api.extensions.ConfigContributor with es.iti.wakamiti.plugins.jmeter.JMeterConfigContributor;

}
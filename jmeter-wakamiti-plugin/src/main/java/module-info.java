open module es.iti.wakamiti {

    exports es.iti.wakamiti;

    requires es.iti.wakamiti.api;

    provides es.iti.wakamiti.api.extensions.StepContributor with es.iti.wakamiti.JMeterStepContributor;
    provides es.iti.wakamiti.api.extensions.ConfigContributor with es.iti.wakamiti.JMeterConfigContributor;

}
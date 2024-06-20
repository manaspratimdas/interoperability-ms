package processor.handler;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import processor.service.AuditService;
import processor.service.EhrPublishService;
import processor.service.InteroperabilityService;
import shared.events.PatientRecordEvent;

@Component
public class PatientRecordReciever {

	@Autowired
	private KafkaListenerEndpointRegistry registry;

	@Autowired
	private InteroperabilityService interoperabilityService;

	@Autowired
	EhrPublishService ehrPublishService;

	@Autowired
	AuditService auditService;

	String patientData;
	String encryptedPatientInfo;

	private static final Logger logger = LogManager.getLogger(PatientRecordReciever.class);

	@KafkaListener(topics = "patient-record-upsert-event", groupId = "patient-record-created-events")
	public void handler(PatientRecordEvent patientRecordEvent) throws ParseException {

		logger.info("event received : " + patientRecordEvent);

		auditService.postAuditEvent(encryptedPatientInfo);

		patientData = interoperabilityService.translateData(patientRecordEvent);

		encryptedPatientInfo = interoperabilityService.encryptData(patientData);

		ehrPublishService.publish(encryptedPatientInfo);

	}

}

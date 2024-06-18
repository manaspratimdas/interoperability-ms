package processor.handler;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

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
	
	String patientData;
	String encryptedPatientInfo;
	
	@KafkaListener(topics = "patient-record-upsert-event", groupId = "patient-record-created-events")
//	@KafkaListener(topics = "swipe-events", groupId = "swipe-created-events", autoStartup = "false")

	public void handler(PatientRecordEvent patientRecordEvent) throws ParseException {

		System.out.println("event received : " + patientRecordEvent);
		
		patientData=interoperabilityService.translateData(patientRecordEvent);
		
		encryptedPatientInfo=interoperabilityService.encryptData(patientData);
		
		ehrPublishService.publish(encryptedPatientInfo);
		
		
		
		
		
	}

}

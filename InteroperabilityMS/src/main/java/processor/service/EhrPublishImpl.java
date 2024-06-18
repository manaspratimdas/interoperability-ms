package processor.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import shared.dto.PatientDTO;

@Service
public class EhrPublishImpl implements EhrPublishService {

	final String externalMessageBorker="patient-publish-external-event";
	
	@Autowired
	KafkaTemplate<String, PatientDTO> kafkaTemplate;

	@Override
	public String publish(String encryptedPatientInfo) {
		
	
		String eventId = UUID.randomUUID().toString();
		
		PatientDTO patientDTO=new PatientDTO(eventId,"15-May-2024",encryptedPatientInfo);
		
		System.out.println("patientDTO   " + patientDTO);
		
		CompletableFuture<SendResult<String, PatientDTO>> future = kafkaTemplate
				.send(externalMessageBorker, eventId, patientDTO).completable();
		
		
		future.whenComplete((result, exception) -> {
			if (exception != null) {
				System.out.println("exception " + exception.getMessage());
			} else {
				System.out.println("Message sent sucessfully to message broker external vendor - Compelte");
			}
		});
		
		System.out.println("Processing compelted sent to external vendor -Mans");

		return "success";
		
		
		
		
		
	}

}

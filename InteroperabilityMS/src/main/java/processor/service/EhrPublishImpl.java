package processor.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import shared.dto.PatientDTO;

@Service
public class EhrPublishImpl implements EhrPublishService {


	@Autowired
	KafkaTemplate<String, PatientDTO> kafkaTemplate;
	
	
	@Value("${op.externalMessageBorker}")
	private String EXTERNAL_MESSAGE_BROKER;
	
	private final String FORMATTER = "yyyyMMdd HH:mm:ss";

	private static final Logger logger = LogManager.getLogger(EhrPublishImpl.class);

	@Override
	public String publish(String encryptedPatientInfo) {
		
	
		String eventId = UUID.randomUUID().toString();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(FORMATTER);		
		
		PatientDTO patientDTO=new PatientDTO(eventId,LocalDateTime.now().format(dtf),encryptedPatientInfo);
		
		logger.info("patientDTO: {}  ", patientDTO);
		
		CompletableFuture<SendResult<String, PatientDTO>> future = kafkaTemplate
				.send(EXTERNAL_MESSAGE_BROKER, eventId, patientDTO).completable();
		
		
		future.whenComplete((result, exception) -> {
			if (exception != null) {
				logger.info("exception " + exception.getMessage());
			} else {
				logger.info("Message processed");
			}
		});
		
		logger.info("Processing compelted sent to external vendor");

		return "success";
		
		
		
		
		
	}

}

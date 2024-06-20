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

import audit.AuditEvent;

@Service
public class AuditService {

	@Autowired
	KafkaTemplate<String, AuditEvent> kafkaTemplate;

	private static final Logger logger = LogManager.getLogger(AuditService.class);

	@Value("${op.audit-message-broker-name}")
	private String auditMessageBrokerName;

	@Value("${op.microservice-name}")
	private String MICROSERVICE;

	private final String FORMATTER = "yyyyMMdd HH:mm:ss";

	private final String userName="PatientRecord";
	
	

	public String postAuditEvent(String event) {

		String eventId = UUID.randomUUID().toString();

	
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(FORMATTER);
		LocalDateTime now = LocalDateTime.now();
		AuditEvent auditEvent = new AuditEvent(eventId, userName, now.format(dtf), MICROSERVICE, event);

		CompletableFuture<SendResult<String, AuditEvent>> future = kafkaTemplate
				.send(auditMessageBrokerName, eventId, auditEvent).completable();

		future.whenComplete((result, exception) -> {
			if (exception != null) {
				logger.info("Error sending message to broker " + exception.getMessage());
			} else {
				logger.info("Message process and sent to broker");
			}
		});

		logger.info("Audit event sent to message queue {} at {}", auditMessageBrokerName, LocalDateTime.now());

		return "success";

	}

}

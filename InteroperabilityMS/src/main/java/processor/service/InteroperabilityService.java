package processor.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Patient.ContactComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.context.FhirContext;
import processor.util.EncryptorDecryptor;
import shared.events.PatientRecordEvent;

@Service
public class InteroperabilityService {

	public enum AdministrativeGender {
		MALE, FEMALE, OTHER, UNKNOWN
	}
	
	@Value("${op.num}")
	private String KEY;

	private static final Logger logger = LogManager.getLogger(InteroperabilityService.class);
	public String translateData(PatientRecordEvent patientRecordEvent) {
		
		logger.info("Transalating patient record : " + patientRecordEvent);

		Patient patient = new Patient();
		HumanName humanName = new HumanName().setText(patientRecordEvent.getPatientId());
		ContactComponent contactComponent = new ContactComponent().addTelecom(new ContactPoint().setValue(patientRecordEvent.getHealthRecords()))		;

		patient.setId(patientRecordEvent.getPatientId());

		patient.addName(humanName);
		patient.addContact(contactComponent);

		FhirContext ctx = FhirContext.forR4();

		String patientString = ctx.newJsonParser().encodeResourceToString(patient);
		
		logger.info("patientString   " + patientString);

		return patientString;

	}

	public String encryptData(String patientData) {

		logger.info("Encrypting patient data");
		
		String encryptedPatientInfo = EncryptorDecryptor.encrypt(patientData, KEY);
		System.out.println(encryptedPatientInfo);

		return encryptedPatientInfo;

	}

}

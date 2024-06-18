package processor.service;

import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Patient.ContactComponent;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.context.FhirContext;
import processor.util.EncryptorDecryptor;
import shared.events.PatientRecordEvent;

@Service
public class InteroperabilityService {

	public enum AdministrativeGender {
		MALE, FEMALE, OTHER, UNKNOWN
	}

	public String translateData(PatientRecordEvent patientRecordEvent) {

		Patient patient = new Patient();
		HumanName humanName = new HumanName().setText(patientRecordEvent.getPatientId());
		ContactComponent contactComponent = new ContactComponent().addTelecom(new ContactPoint().setValue("78789"))

		;

		patient.setId(patientRecordEvent.getPatientId());

		patient.addName(humanName);
		patient.addContact(contactComponent);

		FhirContext ctx = FhirContext.forR4();

		String patientString = ctx.newJsonParser().encodeResourceToString(patient);
		System.out.println("patientString   " + patientString);
		
		return patientString;

	}
	
	
	public String encryptData(String patientData) {
		
		
		String secretKey = "1234567890123456"; // This should be 16 characters
		String encryptedPatientInfo = EncryptorDecryptor.encrypt(patientData, secretKey);
		System.out.println(encryptedPatientInfo);
		
		
		return encryptedPatientInfo;
		
	}
	

}

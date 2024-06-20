package processor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import processor.service.InteroperabilityService;
import shared.events.PatientRecordEvent;

@SpringBootTest
public class InteroperabilityServiceTest {
	
	@MockBean
    private InteroperabilityService interoperabilityService;
	


    @Test
    public void testTranslateData() {

    	PatientRecordEvent patientRecordEvent = new PatientRecordEvent();
        // Set properties of the patientRecordEvent
        patientRecordEvent.setPatientId("1");
        patientRecordEvent.setHealthRecords("Health Records");

        String result = interoperabilityService.translateData(patientRecordEvent);

        assertNotNull(result);
        assertTrue(result.contains(patientRecordEvent.getPatientId()));
        assertTrue(result.contains(patientRecordEvent.getHealthRecords()));

       
    }

}

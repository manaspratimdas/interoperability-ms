package processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import audit.AuditEvent;
import processor.service.EhrPublishImpl;
import shared.dto.PatientDTO;

@SpringBootTest
public class EhrPublishImplTest {
	
	
	@MockBean
    private KafkaTemplate<String, PatientDTO> kafkaTemplate;

    @Autowired
    private EhrPublishImpl ehrPublishImpl;

    @Test
    public void testPublishSuccess() {
        String encryptedPatientInfo = "encryptedInfo";

        


//        // create a mock ListenableFuture
//        ListenableFuture<SendResult<String, PatientDTO>> future = Mockito.mock(ListenableFuture.class);
//        // create a mock CompletableFuture
//        CompletableFuture<SendResult<String, PatientDTO>> completableFuture = new CompletableFuture<>();
//
//        // when kafkaTemplate.send is called, return the mock future
//        when(kafkaTemplate.send(anyString(), anyString(), any(PatientDTO.class))).thenReturn(future);
//        // when completable() is called on the mock future, return the mock CompletableFuture
//        when(future.completable()).thenReturn(completableFuture);
//        
//        String result = ehrPublishImpl.publish(encryptedPatientInfo);
//
//        assertEquals("success", result);
//        verify(kafkaTemplate, times(1)).send(anyString(), anyString(), any(PatientDTO.class));
   }

}

package lk.gov.govtech.covid19.service;

import lk.gov.govtech.covid19.firebase.FCMService;
import lk.gov.govtech.covid19.dto.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PushNotificationService {

	@Value("${firebase.notifications.topic}")
	private String topic;

	@Value("${firebase.notifications.title}")
	private String title;

	@Value("${firebase.notifications.message}")
	private String message;

	@Value("${firebase.notifications.token}")
	private String token;

	@Value("${firebase.notifications.payloadMessageId}")
	private String payloadMessageId;

	@Value("${firebase.notifications.payloadData}")
	private String payloadData;

	private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
	private FCMService fcmService;

	public PushNotificationService(FCMService fcmService) {
		this.fcmService = fcmService;
	}

	@Scheduled(initialDelay = 60000, fixedDelay = 60000)
	public void sendSamplePushNotification() {
		try {
			fcmService.sendMessageWithoutData(getSamplePushNotificationRequest());
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e.getMessage());
		}
	}

	public void sendPushNotification(PushNotificationRequest request) {
		try {
			fcmService.sendMessage(getSamplePayloadData(), request);
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e.getMessage());
		}
	}

	public void sendPushNotificationWithoutData(PushNotificationRequest request) {
		try {
			fcmService.sendMessageWithoutData(request);
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e.getMessage());
		}
	}

	public void sendPushNotificationToToken(PushNotificationRequest request) {
		try {
			fcmService.sendMessageToToken(request);
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e.getMessage());
		}
	}

	public void sendPushNotificationWithData(Map<String, String> data, PushNotificationRequest request){
		try {
			fcmService.sendMessage(data, request);
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e.getMessage());
		}
	}

	private Map<String, String> getSamplePayloadData() {
		Map<String, String> pushData = new HashMap<>();
		pushData.put("messageId", payloadMessageId);
		logger.info("payloadMessageId:" + payloadMessageId);
		pushData.put("text", payloadData + " " + LocalDateTime.now());
		return pushData;
	}

	private PushNotificationRequest getSamplePushNotificationRequest() {
		PushNotificationRequest request = new PushNotificationRequest(title, message, topic);
		return request;
	}

}

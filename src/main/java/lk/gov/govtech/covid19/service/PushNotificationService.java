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

	private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
	private FCMService fcmService;

	public PushNotificationService(FCMService fcmService) {
		this.fcmService = fcmService;
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


}

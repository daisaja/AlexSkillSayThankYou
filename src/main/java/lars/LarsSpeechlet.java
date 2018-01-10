package lars;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;

public class LarsSpeechlet implements Speechlet {
	private static final Logger log = LoggerFactory.getLogger(LarsSpeechlet.class);
	private static final String A_SESSION_KEY_FOR_VALUE = "a_key";
	private static final String INTENT_SAYTHANKYOU = "saythankyou";
	private static final String INTENT_WHOISBEST = "whoisbest";

	public LarsSpeechlet () {
        System.out.println("XXX: LarsSpeechlet initalized.");
        log.info("XXX: INFO");
        log.debug("XXX: DEBUG");
        log.warn("XXX: WARN");
        log.error("XXX: ERROR");
    }

	@Override
	public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
		log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        System.out.println("XXX: Session started.");
    }

	@Override
	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        System.out.println("XXX: onLaunch");
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText("hallo ich bin lars");
		return SpeechletResponse.newAskResponse(speech, createRepromptSpeech());
	}

	@Override
	public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
		log.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		System.out.println("Session:"+session+ " Intent:"+request.getIntent().getName());
        System.out.println("XXX: onIntent");
		String intentName = request.getIntent().getName();
		if (INTENT_SAYTHANKYOU.equals(intentName)) {
			return handleSayThankYou(request.getIntent(), session);
		} else if (INTENT_WHOISBEST.equals(intentName)) {
			return handleWhoIsBest(session);
		} else if ("AMAZON.HelpIntent".equals(intentName)) {
			return handleHelpIntent();
		} else if ("AMAZON.StopIntent".equals(intentName)) {
			return handleStopIntent();
		} else {
			throw new SpeechletException("Invalid Intent");
		}
	}

	@Override
	public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
		log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
	}

	private SpeechletResponse handleWhoIsBest(Session session) {
		String aValue = (String) session.getAttribute(A_SESSION_KEY_FOR_VALUE);

		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText("Vielen Dank Jana, du bist die Beste.");
		return SpeechletResponse.newAskResponse(speech, createRepromptSpeech());
	}
	
	private SpeechletResponse handleStopIntent() {
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText("auf wiedersehen.");
		return SpeechletResponse.newTellResponse(speech);
	}

	private SpeechletResponse handleHelpIntent() {
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText("soll ich mich bedanken oder sagen wer die beste ist?");
		return SpeechletResponse.newTellResponse(speech);
	}

	private SpeechletResponse handleSayThankYou(Intent intent, Session session) {
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();

		speech.setText("Vielen Dank Jana, du bist die Beste.");
		return SpeechletResponse.newAskResponse(speech, createRepromptSpeech());
	}

	private Reprompt createRepromptSpeech() {
		PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText("ich habe dich nicht verstanden");
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);
		return reprompt;
	}
}

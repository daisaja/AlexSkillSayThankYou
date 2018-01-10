package lars;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;


public class LarsSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> supportedApplicationIds;

    static {
        supportedApplicationIds = new HashSet<String>();
        supportedApplicationIds.add("amzn1.ask.skill.592b50bd-a0b9-4fee-8f43-b9ae1300d641");
    }

    public LarsSpeechletRequestStreamHandler() {
        super(new LarsSpeechlet(), supportedApplicationIds);
        System.out.println("Handler intialized 1.");
    }

    public LarsSpeechletRequestStreamHandler(Speechlet speechlet,
                                             Set<String> supportedApplicationIds) {
        super(speechlet, supportedApplicationIds);
        System.out.println("Handler intialized 2.");
    }

}

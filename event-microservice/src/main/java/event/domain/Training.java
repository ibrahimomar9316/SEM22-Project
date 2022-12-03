package event.domain;

import java.util.List;

public class Training extends Event {
    private String admin;
    private List<String> participants;

    public Training(String admin, List<String> participants) {
        this.admin = admin;
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "This is a training session created by: " + admin + "\n" +
                "Number of participants: " + numberOfParticipants();
    }
}

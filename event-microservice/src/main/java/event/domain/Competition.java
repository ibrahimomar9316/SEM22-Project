package event.domain;

import java.util.List;

public class Competition extends Event{

    private String admin;
    private List<String> participants;

    public Competition(String admin, List<String> participants) {
        this.admin = admin;
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "This is a competitive event created by: " + admin + "\n" +
                "Number of participants: " + numberOfParticipants();
    }
}

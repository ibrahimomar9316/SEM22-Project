package event.domain;

import java.util.List;
import java.util.Objects;

public abstract class Event {
    private String admin;
    private List<String> participants;

    public String getAdmin() {
        return admin;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public void addParticipant(String participant) {
        this.participants.add(participant);
    }

    public int numberOfParticipants() {
        return this.participants.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(admin, event.admin) && Objects.equals(participants, event.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(admin, participants);
    }

}

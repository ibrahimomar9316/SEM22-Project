package user.domain.objects;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AvDates {

    @Column
    private LocalDateTime dateFrom;


    @Column
    private LocalDateTime dateTo;

    public AvDates() {
    }

    public AvDates(LocalDateTime from, LocalDateTime to) {
        this.dateFrom = from;
        this.dateTo = to;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AvDates that = (AvDates) o;
        return Objects.equals(dateFrom, that.dateFrom) && Objects.equals(dateTo, that.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFrom, dateTo);
    }

    @Override
    public String toString() {
        return "AvailableDates " + " from: " + dateFrom
                + ", to: " + dateTo + '}';
    }
}



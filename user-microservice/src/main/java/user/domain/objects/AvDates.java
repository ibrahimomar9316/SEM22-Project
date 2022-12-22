package user.domain.objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvDates that = (AvDates) o;
        return Objects.equals(dateFrom, that.dateFrom) && Objects.equals(dateTo, that.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFrom, dateTo);
    }

    @Override
    public String toString() {
        return "AvailableDates" +
                "from=" + dateFrom +
                ", to=" + dateTo +
                '}';
    }
}



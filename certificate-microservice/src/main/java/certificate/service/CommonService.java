package certificate.service;

import javax.transaction.Transactional;

import certificate.domain.enums.Gender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class used to generate the hash for desired events and users.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommonService {

    /**
     * Function that generates the hash for the desired event/user based on multiple values.
     * Every digit of the generated hash represents the encoding of those rules/preferences.
     *
     * @param gender enum representing the gender
     * @param isCompetitive boolean representing if the event/user is competitive or not
     * @param position String stating the position in the boat
     * @param certificate String representing the certificates required/obtained
     * @return an int representing the hashed values of those events
     */
    public int generateId(Gender gender, boolean isCompetitive, String position, String certificate) {
        int id = 0;
        if (gender.equals(Gender.MALE)) {
            id += 1;
        } else if (gender.equals(Gender.FEMALE)) {
            id += 2;
        }
        id *= 10;
        if (isCompetitive) {
            id += 1;
        }
        id *= 10;
        if (position.contains("COX")) {
            switch (certificate) {
                case "C4":
                    id += 1;
                    break;
                case "FOURPLUS":
                    id += 2;
                    break;
                case "EIGHTPLUS":
                    id += 3;
                    break;
                default:
                    id += 0;
                    break;
            }
        }
        return id;
    }
}

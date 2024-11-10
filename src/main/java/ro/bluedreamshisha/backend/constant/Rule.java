package ro.bluedreamshisha.backend.constant;

import org.apache.commons.lang3.tuple.Pair;

public interface Rule {

    Pair<Integer, Integer> usernameLength = Pair.of(2, 128);
    Pair<Integer, Integer> passwordLength = Pair.of(8, 256);
    String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).+$";

}

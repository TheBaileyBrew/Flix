package com.thebaileybrew.flix.model;

public class Credit {

    private final String creditCharacterName;
    private final String creditActorName;
    private final String creditPath;

    public Credit(String creditCharacterName, String creditActorName, String creditPath) {
        this.creditCharacterName = creditCharacterName;
        this.creditActorName = creditActorName;
        this.creditPath = creditPath;
    }

    public String getCreditCharacterName() {
        return creditCharacterName;
    }

    public String getCreditActorName() {
        return creditActorName;
    }

    public String getCreditPath() {
        return creditPath;
    }
}

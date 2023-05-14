package niffler.db.entity;

import java.util.UUID;

public class AuthorityEntity {

    private UUID id;
    private Authority authority;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

}

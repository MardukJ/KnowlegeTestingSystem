package ua.epam.rd.domain;

import javax.persistence.*;
import java.util.Random;

/**
 * Created by Mykhaylo Gnylorybov on 25.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Entity
@Table(name = "tokens")
public class Token {
    private static final int TOKEN_LENGTH = 20;

    @Id
    @GeneratedValue
    @Column(name = "id_token")
    Long id;

    @Column(name = "token")
    String token;

    @Enumerated(EnumType.STRING)
    TokenType type;

    @OneToOne(mappedBy = "token", fetch = FetchType.LAZY)
    User user;

    public String generateToken(TokenType type) {
        this.type = type;
        Random rnd = new Random(System.currentTimeMillis());
        StringBuffer tokenBuf = new StringBuffer();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            tokenBuf.append(Integer.toHexString(Math.abs(rnd.nextInt()) % 16));
        }
        token = tokenBuf.toString().toUpperCase().trim();
        return token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }
}

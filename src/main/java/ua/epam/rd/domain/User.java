package ua.epam.rd.domain;

/**
 * Created by Mykhaylo Gnylorybov on 23.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 * <p>
 * User DAO
 * no personal information here
 */

import org.springframework.web.client.ResourceAccessException;

import javax.persistence.*;
import java.security.MessageDigest;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id_user")
    Long id;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "blocked")
    Boolean blocked;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_token")
    Token token;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = encrypt(password);
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String encrypt(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            byte byteData[] = md.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new ResourceAccessException("md5");
        }
    }

    public boolean tryPassword(String pass) {
        if (password == null) throw new IllegalStateException("user has no password");
        if (pass == null) return false;
        return password.equals(encrypt(pass));
    }

    public String newRandomPassword() {
        String passwordBuffer = "pass" + (long) (Math.random() * 1000000.0d);
        password = encrypt(passwordBuffer);
        return passwordBuffer;
    }

    public String verifyMe() {
        //null if all field r correct
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", blocked=" + blocked +
                '}';
    }
}

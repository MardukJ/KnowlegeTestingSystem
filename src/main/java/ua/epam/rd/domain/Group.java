package ua.epam.rd.domain;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mykhaylo Gnylorybov on 28.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */

@Entity
@Table(name = "groups")
public class Group {

    private final static String GROUP_NAME_PATTERN = "[a-z0-9.]+";

    @Id
    @GeneratedValue
    Long id_group;

    @Column(name = "group_name")
    String groupName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "join_users_groups",
            joinColumns = {@JoinColumn(name = "id_group_ref", referencedColumnName = "id_group")},
            inverseJoinColumns = {@JoinColumn(name = "id_user_ref", referencedColumnName = "id_user")}
    )
    List<User> members = new LinkedList<User>();

    @Column
    Boolean blocked = Boolean.FALSE;

    public Long getId() {
        return id_group;
    }

    public void setId(Long id_group) {
        throw new UnsupportedOperationException();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<User> getMembers() {
        return members;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public boolean verifyMe() {
        groupName = groupName.toLowerCase().trim();
        Pattern p = Pattern.compile(GROUP_NAME_PATTERN);
        Matcher m = p.matcher(groupName);
        return m.matches();
    }
}

package ua.epam.rd.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Mykhaylo Gnylorybov on 28.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */

@Entity
public class Group {

    @Id
    @GeneratedValue
    Long id_group;

    @Column(name = "group_name")
    String groupName;

//    @ManyToMany (optional = false, fetch = FetchType.LAZY, orphanRemoval = false)
//    @JoinTable (name = "id_member")
//    User user;
}

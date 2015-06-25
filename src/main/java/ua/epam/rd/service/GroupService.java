package ua.epam.rd.service;

import ua.epam.rd.domain.Group;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 28.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public interface GroupService {
    Long registerNew(Group newGroup);

    List<Group> getAllNow();
    int getAllTotalPagesWFiler(Boolean blocked, String regexp);
    List<Group> getAllFromPageWFilter(int page, Boolean blocked, Boolean sort, String regexp);

    public Group getGroupInfo(String name);
    public void addUserToGroup(String userMail, String groupName);
    public void removeUserFromGroup(String userMail, String groupName);

    void blockGroup (Long id);
    void unblockGroup (Long id);
}

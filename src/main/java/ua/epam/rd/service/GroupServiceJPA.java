package ua.epam.rd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.Group;
import ua.epam.rd.domain.User;
import ua.epam.rd.repository.GroupRepository;
import ua.epam.rd.repository.UserRepository;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 28.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */

@Service
public class GroupServiceJPA implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    public static int PAGE_SIZE = 20;

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public Long registerNew(Group newGroup) {
        if (newGroup == null) throw new IllegalArgumentException("Incorrect group object");
        if (!newGroup.verifyMe()) {
            throw new IllegalArgumentException("Incorrect group name");
        }

        //Duplicate protection - just return Id if already exist
        Group group = groupRepository.getByName(newGroup.getGroupName());
        if (group != null) throw new IllegalArgumentException("Group already exist");
        groupRepository.add(newGroup);
        return newGroup.getId();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Group> getAllNow() {
        return groupRepository.getAll();
    }

    @Override
    public int getAllTotalPagesWFiler(Boolean blocked, String regexp) {
        long totalEntry = groupRepository.getTotalEntryWithFilter(blocked,regexp);
        int pages = (int) (totalEntry / PAGE_SIZE + (totalEntry % PAGE_SIZE == 0 ? 0 : 1));
        if (pages == 0) pages = 1;
        return pages;
    }

    @Override
    public List<Group> getAllFromPageWFilter(int page, Boolean blocked, Boolean sort, String regexp) {
        return groupRepository.getEntryInRangeWithFilter((page - 1) * PAGE_SIZE, PAGE_SIZE, blocked, sort, regexp);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Group getGroupInfo(String name) {
        if (name == null)
            throw new IllegalArgumentException("Please enter valid group name");
        Group result = groupRepository.getByName(name);
        if (result == null) throw new IllegalArgumentException("Group not found");
        result.getMembers().size();
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUserToGroup(String userMail, String groupName) {
        User user = userRepository.getByMail(userMail);
        Group group = groupRepository.getByName(groupName);
        if (group==null) throw new IllegalArgumentException("Invalid group");
        if (user==null) throw new IllegalArgumentException("Invalid user");
        List<User> members = group.getMembers();
        if (members.contains(user)) throw new IllegalArgumentException ("Already group member");
        members.add(user);
        groupRepository.merge(group);
//        System.out.println("SIZE = " + group.getMembers().size());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeUserFromGroup(String userMail, String groupName) {
        User user = userRepository.getByMail(userMail);
        Group group = groupRepository.getByName(groupName);
        if (group==null) throw new IllegalArgumentException("Invalid group");
        if (user==null) throw new IllegalArgumentException("Invalid user");
        List<User> members = group.getMembers();
        if (!members.contains(user)) throw new IllegalArgumentException ("User id not a group member");
        members.remove(user);
        groupRepository.merge(group);
        System.out.println("SIZE = " + group.getMembers().size());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void blockGroup(Long id) {
        if (id == null) throw new IllegalArgumentException("Please enter valid group id");
        Group group = groupRepository.getById(id);
        if (group == null) throw new IllegalArgumentException("Group not found");
        group.setBlocked(Boolean.TRUE);
        groupRepository.merge(group);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void unblockGroup(Long id) {
        if (id == null) throw new IllegalArgumentException("Please enter valid group id");
        Group group = groupRepository.getById(id);
        if (group == null) throw new IllegalArgumentException("Group not found");
        group.setBlocked(Boolean.FALSE);
        groupRepository.merge(group);
    }
}

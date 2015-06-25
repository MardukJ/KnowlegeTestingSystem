package ua.epam.rd.repository;

import ua.epam.rd.domain.Group;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 28.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public interface GroupRepository {
    Long add(Group group);

    Group getById (Long id);

    Group getByName (String name);

    List <Group> getAll();

    long getTotalEntryWithFilter(Boolean blocked, String regexp);

    List<Group> getEntryInRangeWithFilter(int first, int size, Boolean blocked, Boolean sort, String regexp);

    void merge(Group group);

    void delete(Long id);

    long getTotalEntry();

    List<Group> getEntryInRange(int first, int size);
}

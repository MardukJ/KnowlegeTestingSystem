package ua.epam.rd.domain;

/**
 * Created by Mykhaylo Gnylorybov on 05.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
/*
* Пояснение к алгоритмам оценки:
* А: по полному совпадению
* если в вопросе все ответы правильные +1 за вопрос, иначе 0
* максимум балов = количеству вопросов
*
* B: по каждому варианту ответа
* выбран правильный вариант +1
* выбран неправильный вариант -1
* все невыбранные 0
* максимум балов = количеству правильных вариантов отвтетов в тесте (вопросы неравнозначны)
* за каждый вопрос оценка не менее 0
* */
public enum ScoringAlgorithm {
    A, B;
}

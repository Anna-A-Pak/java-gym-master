package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        TimeOfDay timeOfDay13 = new TimeOfDay(13, 0);
        TimeOfDay timeOfDay20 = new TimeOfDay(20, 0);

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertEquals(timeOfDay13, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).firstKey());
        Assertions.assertEquals(timeOfDay20, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).lastKey());
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        TimeOfDay timeOfDay13 = new TimeOfDay(13, 0);
        TimeOfDay timeOfDay14 = new TimeOfDay(14, 0);
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                timeOfDay13).size());
        Assertions.assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, timeOfDay14));
    }

    @Test
    void shouldBeNullTestGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();
        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeDoubleSession() {
        Timetable timetable = new Timetable();
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Смирнова", "Ирина", "Викторовна");

        TimeOfDay timeOfDay13 = new TimeOfDay(13, 0);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupAdult, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(mondayAdultTrainingSession);

        Assertions.assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                timeOfDay13).size());
    }

    @Test
    void testGetCountByCoachesTwoCoaches() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Смирнова", "Ирина", "Викторовна");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupAdult, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);

        Assertions.assertEquals(2, timetable.getCountByCoaches().getFirst().getCount());
        Assertions.assertEquals(coach2, timetable.getCountByCoaches().getFirst().getCoach());
        Assertions.assertEquals(1, timetable.getCountByCoaches().getLast().getCount());
        Assertions.assertEquals(coach1, timetable.getCountByCoaches().getLast().getCoach());
    }

    @Test
    void shouldBeNullTestGetCountByCoaches() {
        Timetable timetable = new Timetable();
        Assertions.assertEquals(0, timetable.getCountByCoaches().size());
    }

    @Test
    void testGetCountByCoachesOneCoach() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Assertions.assertEquals(1, timetable.getCountByCoaches().getFirst().getCount());
        Assertions.assertEquals(coach1, timetable.getCountByCoaches().getFirst().getCoach());
        Assertions.assertEquals(1, timetable.getCountByCoaches().getLast().getCount());
        Assertions.assertEquals(coach1, timetable.getCountByCoaches().getLast().getCoach());
    }

    @Test
    void testGetCountByCoachesTwoCoachesSingleSession() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Смирнова", "Ирина", "Викторовна");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupAdult, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayAdultTrainingSession);

        Assertions.assertEquals(1, timetable.getCountByCoaches().getFirst().getCount());
        Assertions.assertEquals(coach2, timetable.getCountByCoaches().getFirst().getCoach());
        Assertions.assertEquals(1, timetable.getCountByCoaches().getLast().getCount());
        Assertions.assertEquals(coach1, timetable.getCountByCoaches().getLast().getCoach());
    }
}

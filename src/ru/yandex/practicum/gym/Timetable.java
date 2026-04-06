package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        timetable
                .computeIfAbsent(dayOfWeek, d -> new TreeMap<>())
                .computeIfAbsent(timeOfDay, t -> new ArrayList<>())
                .add(trainingSession);
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (!timetable.isEmpty()) {
            return timetable.get(dayOfWeek).get(timeOfDay);
        } else {
            return null;
        }
    }

    public List<CounterForCoach> getCountByCoaches() {
        LinkedHashMap<Coach, Integer> unSortCoach = new LinkedHashMap<>();
        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> entry : timetable.entrySet()) {
            for (Map.Entry<TimeOfDay, ArrayList<TrainingSession>> trainings : entry.getValue().entrySet()) {
                for (TrainingSession training : trainings.getValue()) {
                    Coach coach = training.getCoach();
                    if (!unSortCoach.containsKey(coach)) {
                        unSortCoach.put(coach, 1);
                    } else {
                        int numTrainings = unSortCoach.get(coach) + 1;
                        unSortCoach.put(coach, numTrainings);
                    }
                }
            }
        }
        List<CounterForCoach> counterForCoaches = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : unSortCoach.entrySet()) {
            counterForCoaches.add(new CounterForCoach(entry.getKey(), entry.getValue()));
        }
        Collections.reverse(counterForCoaches);

        return counterForCoaches;
    }
}

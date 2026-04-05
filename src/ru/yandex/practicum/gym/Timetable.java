package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        if (!timetable.containsKey(dayOfWeek)) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainings = new TreeMap<>();
            ArrayList<TrainingSession> training = new ArrayList<>();
            training.add(trainingSession);
            trainings.put(timeOfDay, training);
            timetable.put(dayOfWeek, trainings);
        } else {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainings = timetable.get(dayOfWeek);
            if (trainings.containsKey(timeOfDay)) {
                trainings.get(timeOfDay).add(trainingSession);
            } else {
                ArrayList<TrainingSession> training = new ArrayList<>();
                training.add(trainingSession);
                trainings.put(timeOfDay, training);
            }
        }
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public List <Map.Entry<Coach, Integer>> getCountByCoaches() {
        Map<Coach, Integer> unSortCoach = new LinkedHashMap<>();
        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> entry : timetable.entrySet()) {
            for (Map.Entry<TimeOfDay, ArrayList<TrainingSession>> trainings : entry.getValue().entrySet()) {
                for (TrainingSession training : trainings.getValue()) {
                    Coach coach = training.getCoach();
                    if(!unSortCoach.containsKey(coach)) {
                        unSortCoach.put(coach, 1);
                    } else {
                        int numTrainings = unSortCoach.get(coach) + 1;
                        unSortCoach.put(coach, numTrainings);
                    }
                }
            }
        }
        List <Map.Entry<Coach, Integer>> sortCoach = new ArrayList<>(unSortCoach.entrySet());
        sortCoach.sort(new Comparator<Map.Entry<Coach, Integer>>() {
            @Override
            public int compare(Map.Entry<Coach, Integer> o1, Map.Entry<Coach, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        Collections.reverse(sortCoach);
        return sortCoach;
    }
}

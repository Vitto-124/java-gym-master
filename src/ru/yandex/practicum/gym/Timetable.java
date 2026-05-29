package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek trainingDay = trainingSession.getDayOfWeek();
        TimeOfDay timeOfTraining = trainingSession.getTimeOfDay();
        TreeMap<TimeOfDay, List<TrainingSession>> treeMapForDay;
        List<TrainingSession> sessionsAtTime;

        if (timetable.containsKey(trainingDay)) {
            treeMapForDay = timetable.get(trainingDay);
        } else {
            treeMapForDay = new TreeMap<>();
            timetable.put(trainingDay, treeMapForDay);
        }

        if (treeMapForDay.containsKey(timeOfTraining)) {
            sessionsAtTime = treeMapForDay.get(timeOfTraining);
        } else {
            sessionsAtTime = new ArrayList<>();
            treeMapForDay.put(timeOfTraining, sessionsAtTime);
        }
        sessionsAtTime.add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        List<TrainingSession> result;
        TreeMap<TimeOfDay, List<TrainingSession>> treeMapForDay = timetable.get(dayOfWeek);
        if (treeMapForDay == null) {
            return new ArrayList<>();
        } else {
            result = new ArrayList<>();
            for (TimeOfDay t : treeMapForDay.keySet()) {
                result.addAll(treeMapForDay.get(t));
            }
            return result;
        }
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> treeMapForDay = timetable.get(dayOfWeek);
        if (treeMapForDay == null) {
            return new ArrayList<>();
        }
        return treeMapForDay.getOrDefault(timeOfDay, new ArrayList<>());
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> countMap = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> treeMapForDay : timetable.values()) {
            for (List<TrainingSession> sessionAtTime : treeMapForDay.values()) {
                for (TrainingSession session : sessionAtTime) {
                    Coach coach = session.getCoach();
                    countMap.put(coach, countMap.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : countMap.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        result.sort((c1, c2) -> c2.getCount() - c1.getCount());

        return result;
    }
}

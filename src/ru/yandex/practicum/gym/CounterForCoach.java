package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterForCoach implements Comparable<CounterForCoach> {
    private final Coach coach;
    private final int count;

    public CounterForCoach(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(CounterForCoach o) {
        final int result = Integer.compare(this.count, o.count);
        if (result == 0) {
            return String.CASE_INSENSITIVE_ORDER.compare(this.coach.getSurname(), o.coach.getSurname());
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CounterForCoach that = (CounterForCoach) o;
        return count == that.count && Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(coach);
        result = 31 * result + count;
        return result;
    }

    @Override
    public String toString() {
        return "CounterForCoach{" +
                "coach=" + coach +
                ", count=" + count +
                '}';
    }
}

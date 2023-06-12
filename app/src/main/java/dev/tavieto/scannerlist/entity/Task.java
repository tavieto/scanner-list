package dev.tavieto.scannerlist.entity;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Task {
    private final int id;
    private String title;
    private boolean done;

    public Task(int id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", done=" + done +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return done == task.done && Objects.equals(id, task.id) && Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, done);
    }
}
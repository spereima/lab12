package ua.ucu.apps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Group<T> extends Task<T> {
    public String groupUuid;
    private List<Task<T>> tasks;

    public Group<T> addTask(Task<T> task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
        return this;
    }

    @Override
    public void freeze() {
        super.freeze();
        groupUuid = UUID.randomUUID().toString();
        for (Task<T> task: tasks) {
            task.freeze();
        }
    }

    @Override
    public void apply(T arg) {
        this.freeze();
        groupUuid = UUID.randomUUID().toString();
        tasks = Collections.unmodifiableList(tasks);

        for (Task<T> task : tasks) {
            if (task instanceof Signature) {
                ((Signature<T>) task).setHeader("groupId", this.groupUuid);
            } else if (task instanceof Group) {
                ((Group<T>) task).setGroupHeader(this.groupUuid);
            }
            task.apply(arg);
        }
    }

    public void setGroupHeader(String groupUuid) {
        this.groupUuid = groupUuid;
        for (Task<T> task : tasks) {
            if (task instanceof Signature) {
                ((Signature<T>) task).setHeader("groupId", groupUuid);
            } else if (task instanceof Group) {
                ((Group<T>) task).setGroupHeader(groupUuid);
            }
        }
    }

    public List<Task<T>> getTasks() {
        return tasks;
    }
}

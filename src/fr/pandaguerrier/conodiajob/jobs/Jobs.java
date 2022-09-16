package fr.pandaguerrier.conodiajob.jobs;

import fr.pandaguerrier.conodiajob.utils.JobsEnum;

public interface Jobs {
    void createPlayer(String player);

    void addXp(String player, JobsEnum jobsEnum, double xp);

    void addLevel(String player, JobsEnum job, int level);

    double getXp(String player, JobsEnum job);

    int getLevel(String player, JobsEnum job);

    boolean isExist(String player);
}

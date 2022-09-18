package fr.pandaguerrier.conodiainvest.utils;

import org.apache.commons.lang3.StringUtils;

public class Progress {
    double current;
    int max;

    private final String progressBar;
    private final double percentage;

    public Progress(double current, int max) {
        this.current = current;
        this.max = max;

        double percentage =  this.current / this.max;
        int progress = (int) Math.round((20 * percentage));
        int emptyProgress = 20 - progress;

        // Idée des signes a utiliser au cas où ça me plaît pas :D --> ┃┃┃┃┃┃┃┃▊▊ ▋ ▍▍ ▎▎ ▉▉ ▏▏ ●●

        String progressText = StringUtils.repeat("▍", progress);
        String emptyProgressText = StringUtils.repeat("▍", emptyProgress);

        this.progressBar = "§b" +  progressText + "§8" + emptyProgressText ;
        this.percentage = percentage;
    }

    public String getProgressBar() {
        return this.progressBar;
    }

    public double getPercentage() {
        return this.percentage;
    }
}

package dwz.framework.timer.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dwz.framework.timer.TaskPeriod;
import dwz.framework.timer.TaskPriority;
import dwz.framework.timer.TaskUnit;

public class TaskUnitImpl implements TaskUnit {

    private final String name;
    private final String priority;
    private final String period;
    private final String delay;
    private final Class<?> task;
    
    private Date startTime;
    private final Date currentTime;
    private final String runnable;

    public TaskUnitImpl(String name, String priority, String period,
            String delay, Class<?> task, String startTime, String runnable) {
        this.name = name;
        this.priority = priority;
        this.period = period;
        this.delay = delay;
        this.task = task;
        this.currentTime = new Date();
        initStartTime(startTime);
        this.runnable = runnable;
    }

    @Override
    public long getDelay() {
        if (this.delay == null || "".equals(this.delay))
            return 0;

        if ("0".equals(this.delay))
            return 0;

        if (!this.delay.contains(" ")) {
            return 0;
        }

        String[] time = this.delay.split(" ");

        return Long.parseLong(time[0])
                * TaskPeriod.valueOf(time[1].toUpperCase()).getValue();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getPeriod() {
        if (this.period == null || "".equals(this.period))
            return 0;

        if ("0".equals(this.period))
            return 0;

        if (!this.period.contains(" ")) {
            return 0;
        }

        String[] time = this.period.split(" ");

        return Long.parseLong(time[0]) * TaskPeriod.valueOf(time[1].toUpperCase()).getValue();
    }

    @Override
    public int getPriority() {
        return TaskPriority.valueOf(this.priority).getValue();
    }

    @Override
    public Date getStartTime() {
        return this.startTime;
    }

    private void initStartTime(String startTime) {
        if ("0".equals(startTime)) {
            this.startTime = new Date(currentTime.getTime() + this.getDelay());
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        if (startTime.indexOf("-") == 2) {
            String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
            String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            if (month.length() == 1) {
                month = "0" + month;
            }
            if (day.length() == 1) {
                day = "0" + day;
            }
            startTime = year + "-" + month + "-" + day + "-" + startTime;
        }

        try {
            this.startTime = format.parse(startTime);
            while (this.getPeriod() > 0
                    && this.startTime.getTime() < this.currentTime.getTime()) {
                if (this.startTime.getTime() < this.currentTime.getTime()) {
                    this.startTime = new Date(this.startTime.getTime()
                            + this.getPeriod());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Runnable getTask() {
        Class<?> clazz = this.getTaskClass();
        if (clazz == null)
            return null;

        Object obj = null;
        
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        /*
        try {
            //obj = clazz.newInstance();
            System.out.println(clazz.getName());
            obj = SpringContextHolder.getBean(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        return (Runnable) obj;
    }

    @Override
    public Class<?> getTaskClass() {
        return this.task;
    }

    @Override
    public boolean isRunnable() {
        if(!"TRUE".equals(this.runnable.toUpperCase())) {
            return false;
        }
        return !(this.getPeriod() == 0
                && this.startTime.getTime() < this.currentTime.getTime());
    }

}

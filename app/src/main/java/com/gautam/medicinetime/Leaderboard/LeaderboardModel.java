package com.gautam.medicinetime.Leaderboard;

import java.util.Comparator;

public class LeaderboardModel {
    String name,points;

    public LeaderboardModel(String name, String points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }



    public  static  final Comparator<LeaderboardModel> By_Point=new Comparator<LeaderboardModel>() {
        @Override
        public int compare(LeaderboardModel o1, LeaderboardModel o2) {
            return o1.getPoints().compareTo(o2.getPoints());
        }
    };

}

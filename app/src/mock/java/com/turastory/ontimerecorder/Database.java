package com.turastory.ontimerecorder;

import android.util.Log;

import java.util.ArrayList;

public class Database {
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    private static Database instance;
    private static String TAG = "Database";


    private ArrayList<TimePiece> timePieces;
    private ArrayList<Tag> tags;

    public Database() {
        timePieces = new ArrayList<>();
        tags = new ArrayList<>();

        tags.add(new Tag("프로그래밍"));
        tags.add(new Tag("피아노 연주"));
        tags.add(new Tag("걷기"));
        tags.add(new Tag("명상"));
        tags.add(new Tag("달리기 운동"));
        tags.add(new Tag("밥"));
    }

    public void addTag(Tag tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        } else {
            Log.e(TAG, "이미 존재하는 태그입니다.");
        }
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void addTimePiece(TimePiece timePiece) {
        timePieces.add(timePiece);

        Log.e(TAG, "TimePiece successfully added.");
        Log.e(TAG, String.format("Name: [%s] duration: [%d]s", timePiece.getTag().getName(), timePiece.getInterval()));
    }

    public ArrayList<TimePiece> getAllTimePieces() {
        return new ArrayList<>(timePieces);
    }

    public ArrayList<TimePiece> getTimePiecesWithTag(Tag tag) {
        ArrayList<TimePiece> ret = new ArrayList<>();

        for (TimePiece timePiece : timePieces) {
            if (timePiece.getTag().getName().equals( tag.getName() )) {
                ret.add(timePiece);
            }
        }

        return ret;
    }
}

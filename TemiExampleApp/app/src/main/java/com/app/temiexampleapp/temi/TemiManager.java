package com.app.temiexampleapp.temi;

import android.util.Log;

import com.robotemi.sdk.BatteryData;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

import org.jetbrains.annotations.NotNull;

public class TemiManager implements Robot.TtsListener,
        OnRobotReadyListener,
        OnGoToLocationStatusChangedListener {
    private Robot robot;

    public TemiManager() {
        robot = Robot.getInstance();
    }
    public Robot getRobot(){
        return robot;
    }

    public void init(){
        robot.addOnRobotReadyListener(this);
        robot.addTtsListener(this);
        robot.addOnGoToLocationStatusChangedListener(this);
    }

    public void speak(String str){
        TtsRequest ttsRequest = TtsRequest.create(str.trim(), true);
        robot.speak(ttsRequest);
    }


    @Override
    //TtsListener를 상속받았기 때문에 구현하는 메서드
    public void onTtsStatusChanged(@NotNull TtsRequest ttsRequest) {
        if (ttsRequest.getStatus() == TtsRequest.Status.COMPLETED ){
            //말하는 것이 끝났을 때에
        }
    }

    @Override
    //OnRobotReadyListener를 상속받았기 때문에 구현하는 메서드
    public void onRobotReady(boolean isReady) {
        if (isReady) {
            //
        }
    }

    public void saveLocation(String locationName){
        String location = locationName.toLowerCase().trim();
        boolean result = robot.saveLocation(location);
        if (result) {
            robot.speak(TtsRequest.create("I've successfully saved the " + location + " location.", true));
        } else {
            robot.speak(TtsRequest.create("Saved the " + location + " location failed.", true));
        }
    }
    public void deleteLocation(String locationName){
        String location = locationName.toLowerCase().trim();
        boolean result = robot.deleteLocation(location);
        if (result) {
            robot.speak(TtsRequest.create("I've successfully deleted the " + location + " location.", true));
        } else {
            robot.speak(TtsRequest.create("Deleting the " + location + " location failed.", true));
        }
    }

    public void stopMovement(){
        robot.stopMovement();
    }

    public void tiltAngle(int i, float vect) {
        robot.tiltAngle(i,vect);//23, 5.3F
    }

    public void tiltBy(int i, float vect) {
        robot.tiltBy(i,vect);//70, 1.2F
    }

    public void goTo(String place){
        for (String location : robot.getLocations()) {
            if (location.equals(place.toLowerCase().trim())) {
                robot.goTo(place.toLowerCase().trim());
            }
        }
    }

    public void getBatteryData() {
        BatteryData batteryData = robot.getBatteryData();
        if (batteryData.isCharging()) {
            TtsRequest ttsRequest = TtsRequest.create(batteryData.getBatteryPercentage() + " percent battery and charging.", true);
            robot.speak(ttsRequest);
        } else {
            TtsRequest ttsRequest = TtsRequest.create(batteryData.getBatteryPercentage() + " percent battery and not charging.", true);
            robot.speak(ttsRequest);
        }
    }

    public void followMe() {
        robot.beWithMe();
    }

    public void callOwner() {
        robot.startTelepresence(robot.getAdminInfo().getName(), robot.getAdminInfo().getUserId());
    }

    @Override
    public void onGoToLocationStatusChanged(String location, String status, int descriptionId, String description) {
        Log.d("GoToStatusChanged", "descriptionId=" + descriptionId + ", description=" + description);
        switch (status) {
            case "start":
                //robot.speak(TtsRequest.create("Starting", false));
                break;

            case "calculating":
                //robot.speak(TtsRequest.create("Calculating", false));
                break;

            case "going":
                robot.speak(TtsRequest.create("Going", false));
                break;

            case "complete":
                    robot.speak(TtsRequest.create("도착했습니다", false));
                break;

            case "abort":
                robot.speak(TtsRequest.create("Cancelled", false));
                break;
        }
    }
    public void skidJoy(float vel,float vel2 , int sec) {
        long t = System.currentTimeMillis();
        long end = t + 1000*sec;
        while (System.currentTimeMillis() < end) {
            robot.skidJoy(vel, vel2);
        }
    }
    public void turnBy(int angle, float vel) {
        robot.turnBy(angle, vel);
    }



}

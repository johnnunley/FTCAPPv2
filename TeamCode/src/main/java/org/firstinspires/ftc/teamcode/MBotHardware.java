package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.LinkedList;

/**
 * Created by jtnunley on 10/6/17.
 */

public class MBotHardware {
    private Drive drive;
    private Arm arm;
    private GyroView gyroSensor;
    private TeamView teamSensor;
    private BallArm ballArm;

    private HardwareMap hm;

    private String robotStatus;
    public void setStatus(String rs) { robotStatus = rs; }
    public String getStatus() { return robotStatus; }

    public MBotHardware(HardwareMap hmx) {
        hm = hmx;
    }

    public Arm RobotArm() { if (arm == null) arm = new Arm(hm); return arm; }
    public Drive DriveTrain() { if (drive == null) drive = new Drive(hm); return drive; }
    public GyroView GyroViewer() { if (gyroSensor == null) gyroSensor = new GyroView(hm); return gyroSensor; }
    public TeamView TeamViewer() { if (teamSensor == null) teamSensor = new TeamView(hm); return teamSensor; }
    public BallArm GemArm() { if (ballArm == null) ballArm = new BallArm(hm); return ballArm; }
    public HardwareMap Hardware() { return hm; }

    public LinkedList<SensorWrapper> SensorWrappers() {
        LinkedList<SensorWrapper> sw = new LinkedList<>();
        sw.add(TeamViewer());
        sw.add(DriveTrain());
        sw.add(RobotArm());
        sw.add(GemArm());
        sw.add(GyroViewer());
        return sw;
    }

    public void RunMotorsWithEncoders() {
        for (SensorWrapper wrapper : SensorWrappers()) {
            if (wrapper instanceof MotorSensorWrapper) {
                MotorSensorWrapper motorWrapper = (MotorSensorWrapper)wrapper;
                motorWrapper.setRunWithEncoders();
            }
        }
    }

    public void RunMotorsWithoutEncoders() {
        for (SensorWrapper wrapper : SensorWrappers()) {
            if (wrapper instanceof MotorSensorWrapper) {
                MotorSensorWrapper motorWrapper = (MotorSensorWrapper)wrapper;
                motorWrapper.setRunWithoutEncoders();
            }
        }
    }

    public void ResetMotorEncoders() {
        for (SensorWrapper wrapper : SensorWrappers()) {
            if (wrapper instanceof MotorSensorWrapper) {
                MotorSensorWrapper motorWrapper = (MotorSensorWrapper)wrapper;
                motorWrapper.resetEncoders();
            }
        }
    }
}

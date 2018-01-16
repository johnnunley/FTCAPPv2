package org.firstinspires.ftc.teamcode;

/**
 * Created by jtnunley on 12/5/17.
 */

public interface MotorSensorWrapper extends SensorWrapper {
    void resetEncoders();
    void setRunWithoutEncoders();
    void setRunWithEncoders();
}

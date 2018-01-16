package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by jtnunley on 10/6/17.
 */

public class GyroView implements SensorWrapper {
    private ModernRoboticsI2cGyro gs;

    public GyroView(HardwareMap hmx) {
        gs = (ModernRoboticsI2cGyro)hmx.gyroSensor.get(MBotConstants.gyroSensorName);
    }

    public void recalibrate() {
        gs.calibrate();
    }

    public boolean isCalibrating() {
        return gs.isCalibrating();
    }

    public void resetZAxis() {
        gs.resetZAxisIntegrator();
    }

    public double getZValue() {
        return gs.getIntegratedZValue();
    }

    public double getValue(int index) {
        if (index == 0)
            return getZValue();
        if (index == 1)
            return gs.rawX();
        if (index == 2)
            return gs.rawY();
        return Double.NaN;
    }

    public String name() { return "Gyro"; }
}

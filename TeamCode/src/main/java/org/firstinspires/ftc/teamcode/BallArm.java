package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by jtnunley on 12/5/17.
 */

public class BallArm implements SensorWrapper {

    private Servo arm;
    private ModernRoboticsI2cColorSensor colorSensor;
    private AnalogInput tSensor;

    public BallArm(HardwareMap hmx) {
        colorSensor = (ModernRoboticsI2cColorSensor)hmx.colorSensor.get(MBotConstants.ballSensorName);
        colorSensor.enableLed(true);
        arm = hmx.servo.get(MBotConstants.ballArmName);
        arm.setDirection(Servo.Direction.REVERSE);
        tSensor = hmx.analogInput.get(MBotConstants.tSensorName);
    }

    @Override
    public double getValue(int index) {
        if (index == 0)
            return arm.getPosition();
        if (index == 1)
            return getColorRed();
        if (index == 2)
            return getColorGreen();
        if (index == 3)
            return getColorBlue();
        if (index == 4)
            return (isTSensorActive() ? 1 : 0);
        return Double.NaN;
    }

    @Override
    public String name() {
        return "BallArm";
    }

    public double getPosition() {
      return arm.getPosition();
    }

    public void setPosition(double val) {
        arm.setPosition(val);
    }

    public int getColorRed() {
        return colorSensor.red();
    }

    public int getColorGreen() {
        return colorSensor.green();
    }

    public int getColorBlue() {
        return colorSensor.blue();
    }

    public boolean isRed() { return (getColorRed() >= 1); }

    public boolean isBlue() { return (getColorBlue() >= 1); }

    public void activateLED() { colorSensor.enableLed(true); }

    public void deactivateLED() { colorSensor.enableLed(false);}

    public boolean isTSensorActive() {
      return true;
    }
}

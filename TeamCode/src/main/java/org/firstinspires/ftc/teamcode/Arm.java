package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by jtnunley on 10/6/17.
 */

public class Arm implements MotorSensorWrapper {
    private DcMotor bMotor;
    private DcMotor mMotor;
    private TouchSensor aSensor;

    public Arm(HardwareMap hmx) {
        bMotor = hmx.dcMotor.get(MBotConstants.bMotorName);
        //mMotor = hmx.dcMotor.get(MBotConstants.mMotorName);
        mMotor = hmx.dcMotor.get(MBotConstants.aServoName);
        aSensor = hmx.touchSensor.get(MBotConstants.aSensorName);
    }

    public double getValue(int index) {
        if (index == 0)
            return bMotor.getPower();
        if (index == 1)
            return (aSensor.isPressed() ? 1 : 0);
        if (index == 2)
            mMotor.getPower();

        return Double.NaN;
    }

    // set motors to reverse
    public void reverseBaseMotor() { bMotor.setDirection(DcMotor.Direction.REVERSE);}
    public void reverseMidsectionMotor() { mMotor.setDirection(DcMotor.Direction.REVERSE);}

    // set power for one/both motors
    public void setBaseMotorPower(double power) { bMotor.setPower(power);}
    public void setMidsectionMotorPower(double power) { mMotor.setPower(power);}


    // set both motors to run without encoders
    public void runBaseMotorWithoutEncoders() { bMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);}
    public void runMidsectionMotorWithoutEncoders() { mMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);}
    public void runMotorsWithoutEncoders() { runBaseMotorWithoutEncoders();runMidsectionMotorWithoutEncoders();}

    // reset one/both of motor's encoders
    public void resetBaseMotorEncoders() {bMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);}
    public void resetMidsectionMotorEncoders() {mMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);}
    public void resetMotorEncoders() { resetBaseMotorEncoders();resetMidsectionMotorEncoders();}

    // run one/both motors with encoders
    public void runBaseMotorWithEncoders() { bMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);}
    public void runMidsectionMotorWithEncoders() { mMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);}
    public void runMotorsWithEncoders() { runBaseMotorWithEncoders();runMidsectionMotorWithEncoders();}

    public int getBasePosition() { return bMotor.getCurrentPosition();}
    public int getMidsectionPosition() { return mMotor.getCurrentPosition();}

    public void setBaseTarget(int target) { bMotor.setTargetPosition(target);}
    public void setMidsectionTarget(int target) { mMotor.setTargetPosition(target);}

    public void setBaseToRunToPosition() { bMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); }
    public void setMidsectionToRunToPosition() { mMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); }
    public void setRunToPosition() { setBaseToRunToPosition();setMidsectionToRunToPosition();}

    public boolean isBaseBusy() { return bMotor.isBusy(); }
    public boolean isMidsectionBusy() { return mMotor.isBusy(); }

    public String name() { return "Arm"; }

    public boolean getLimitSwitchState() {
        return !(aSensor.isPressed());
    }


    @Override
    public void resetEncoders() {
        resetMotorEncoders();
    }

    @Override
    public void setRunWithoutEncoders() {
        runMotorsWithoutEncoders();
    }

    @Override
    public void setRunWithEncoders() {
        runMotorsWithEncoders();
    }
}

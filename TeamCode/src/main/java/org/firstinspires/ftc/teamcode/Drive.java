package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by jtnunley on 10/6/17.
 */

public final class Drive implements MotorSensorWrapper {
    private DcMotor lMotor;
    private DcMotor rMotor;

    public Drive(HardwareMap hmx) {
        lMotor = hmx.dcMotor.get(MBotConstants.lMotorName);
        rMotor = hmx.dcMotor.get(MBotConstants.rMotorName);
    }

    public double getValue(int index) {
        if (index == 0)
            return (lMotor.getPower());
        if (index == 1)
            return (rMotor.getPower());
        return Double.NaN;
    }

    // set motors to reverse
    public void reverseLeftMotor() { lMotor.setDirection(DcMotor.Direction.REVERSE);}
    public void reverseRightMotor() { rMotor.setDirection(DcMotor.Direction.REVERSE);}

    // set power for one/both motors
    public void setLeftMotorPower(double power) { lMotor.setPower(power);}
    public void setRightMotorPower(double power) { rMotor.setPower(power);}
    public void setMotorPower(double left, double right) { setLeftMotorPower(left); setRightMotorPower(right);}
    public void setMotorPower(double power) { setMotorPower(power, power); }


    // set both motors to run without encoders
    public void runLeftMotorWithoutEncoders() { lMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);}
    public void runRightMotorWithoutEncoders() { rMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);}
    public void runMotorsWithoutEncoders() { runLeftMotorWithoutEncoders();runRightMotorWithoutEncoders();}

    // reset one/both of motor's encoders
    public void resetLeftMotorEncoders() {lMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);}
    public void resetRightMotorEncoders() {rMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);}
    public void resetMotorEncoders() { resetLeftMotorEncoders();resetRightMotorEncoders();}

    // run one/both motors with encoders
    public void runLeftMotorWithEncoders() { lMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);}
    public void runRightMotorWithEncoders() { rMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);}
    public void runMotorsWithEncoders() { runLeftMotorWithEncoders();runRightMotorWithEncoders();}

    public int getLeftPosition() { return lMotor.getCurrentPosition();}
    public int getRightPosition() { return rMotor.getCurrentPosition();}

    public void setLeftTarget(int target) { lMotor.setTargetPosition(target);}
    public void setRightTarget(int target) { rMotor.setTargetPosition(target);}

    public void setLeftToRunToPosition() { lMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); }
    public void setRightToRunToPosition() { rMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); }
    public void setRunToPosition() { setLeftToRunToPosition();setRightToRunToPosition();}

    public boolean isLeftBusy() { return lMotor.isBusy(); }
    public boolean isRightBusy() { return rMotor.isBusy(); }

    public String name() { return "Drive"; }

    @Override
    public void resetEncoders() {
        this.resetMotorEncoders();
    }

    @Override
    public void setRunWithoutEncoders() {
        this.runMotorsWithoutEncoders();
    }

    @Override
    public void setRunWithEncoders() {
        this.runMotorsWithEncoders();
    }
}
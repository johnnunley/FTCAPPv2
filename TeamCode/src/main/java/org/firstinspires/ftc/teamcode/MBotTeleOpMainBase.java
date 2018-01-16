package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by jtnunley on 10/12/17.
 */

public abstract class MBotTeleOpMainBase extends MBotTeleOpBase {

    static final double armSpeed = 0.5;
    double clawOffset = 0;                       // Servo mid position
    final double CLAW_SPEED = 0.02 ;

    protected class two_ints {
        public double i;
        public double j;

        public two_ints(double i, double j) {
            this.i = i;
            this.j = j;
        }
    }

    private int initialArmPosition;

    protected two_ints gamepadInput(double lsx, double lsy, double rsx, double rsy) {
        double drive = -lsx;
        double turn  = -lsy;
        double left  = drive + turn;
        double right = drive - turn;
        return new two_ints(left, right);
    }


    protected abstract boolean usingTwoControllers();

    private double multiplier = 0.1;

    @Override
    protected boolean run() throws InterruptedException {

        double left;
        double right;
        double max;
        double speed;

        int currPos = robot.RobotArm().getBasePosition();
        int difference = Math.abs(initialArmPosition - currPos);

        speed = 0.65;
        if (gamepad1.x)
            speed = 0.40 - (difference);

        two_ints ti = gamepadInput(gamepad1.left_stick_x, gamepad1.left_stick_y,
                gamepad1.right_stick_x, gamepad1.right_stick_y);
        left = ti.i;
        right = ti.j;

        left = -left;
        right = -right;

        // Normalize the values so neither exceed +/- 1.0
        left = Range.clip(left, -speed, speed);
        right = Range.clip(right, -speed,speed);

        max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0) {
            left /= max;
            right /= max;
        }

        if (left == 0 && right != 0)
            left = -right;
        else if (left != 0 && right == 0)
            right = -left;

        double bMSpeed = 0;
        Gamepad armGamepad = usingTwoControllers() ? gamepad2 : gamepad1;
        if (armGamepad.y)
            bMSpeed = armSpeed;
        else if (armGamepad.a)
            bMSpeed = -armSpeed;

        if (armGamepad.right_bumper)
            clawOffset = 0.3;
        else if (armGamepad.left_bumper)
            clawOffset = -0.3;
        else
            clawOffset = 0.0;
          
        clawOffset = Range.clip(clawOffset, -0.5, 0.5);
        robot.RobotArm().setMidsectionMotorPower(clawOffset);

        if (left != 0 && right != 0 && multiplier != 1) {
            multiplier += 0.1;
        }
        else multiplier = 0;
        speed *= multiplier;

        robot.DriveTrain().setLeftMotorPower(left * speed);
        robot.DriveTrain().setRightMotorPower(right * speed);
        robot.RobotArm().setBaseMotorPower(
                ((robot.RobotArm().getLimitSwitchState()) ? bMSpeed : (bMSpeed > 0 ? 0 : bMSpeed)));

        return true;
    }

    @Override
    protected void initialize() {
        robot.RunMotorsWithoutEncoders();
        robot.RobotArm().runBaseMotorWithEncoders();
        initialArmPosition = robot.RobotArm().getBasePosition();
        robot.GemArm().deactivateLED();
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by jtnunley on 10/12/17.
 */

public abstract class MBotAutonomousBase extends MBotOpBase {
    protected static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    protected static final double     DRIVE_GEAR_REDUCTION    = 0.6 ;     // This is < 1.0 if geared UP
    protected static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    protected static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    protected static final double     FORWARD_SPEED = 0.5;
    protected static final double TURN_SPEED = 0.2;

    protected static final double     HEADING_THRESHOLD       = 1 ;
    protected static final double     P_TURN_COEFF            = 0.1;
    protected static final double     P_DRIVE_COEFF           = 0.15;

    private double currentAngle = 0.0;

    @Override
    protected boolean isAutonomous() {
        return true;
    }

    @Override
    protected void initialize() {
        robot.ResetMotorEncoders();
        robot.DriveTrain().reverseLeftMotor();
        robot.GyroViewer().recalibrate();
        while (!isStopRequested() && robot.GyroViewer().isCalibrating())  {
            sleep(50);
            idle();
        }
        robot.RunMotorsWithEncoders();
        robot.GemArm().activateLED();
        robot.GyroViewer().resetZAxis();
    }

    // note: obsolete function
    public void encoderDrive(double speed,
                              double leftInches, double rightInches,
                              double timeoutS) throws InterruptedException {
        //telemetry.addData("STATUS","ENTERING ENCODERDRIVE");
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.DriveTrain().getLeftPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.DriveTrain().getRightPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.DriveTrain().setLeftTarget(newLeftTarget);
            robot.DriveTrain().setRightTarget(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.DriveTrain().setRunToPosition();

            // reset the timeout time and start motion.
            runtime.reset();
            robot.DriveTrain().setMotorPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.DriveTrain().isLeftBusy() && robot.DriveTrain().isRightBusy())) {
          //      telemetry.clear();
          //      telemetry.addData("STATUS","ENCODERDRIVE");
            }

            //telemetry.clear();
            //telemetry.addData("STATUS","EXITING ENCODERDRIVE");

            // Stop all motion;
            robot.DriveTrain().setMotorPower(0);
            robot.DriveTrain().runMotorsWithEncoders();

            // Turn off RUN_TO_POSITION
            robot.DriveTrain().resetMotorEncoders();

            //  sleep(250);   // optional pause after each move
        }
    }

    public void encoderDrive_gemArm(double pos,
                             double timeoutS) throws InterruptedException {
        //telemetry.addData("STATUS","ENTERING ENCODERDRIVE");
        int newArmTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            robot.GemArm().setPosition(pos);

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    robot.GemArm().getPosition() != pos) {
                //      telemetry.clear();
                //      telemetry.addData("STATUS","ENCODERDRIVE");
            }

            //telemetry.clear();
            //telemetry.addData("STATUS","EXITING ENCODERDRIVE");

            // Stop all motion;

            //  sleep(250);   // optional pause after each move
        }
    }

    public double getError(double targetAngle) {

        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle - robot.GyroViewer().getZValue();
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    public double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }

    boolean onHeading(double speed, double angle, double PCoeff) {
        double   error ;
        double   steer ;
        boolean  onTarget = false ;
        double leftSpeed;
        double rightSpeed;

        // determine turn power based on +/- error
        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        }
        else {
            steer = getSteer(error, PCoeff);
            rightSpeed  = speed * steer;
            leftSpeed   = -rightSpeed;
        }

        double placeholder = leftSpeed;
        leftSpeed = rightSpeed;
        rightSpeed = placeholder;

        // Send desired speeds to motors.
        robot.DriveTrain().setLeftMotorPower(leftSpeed);
        robot.DriveTrain().setRightMotorPower(rightSpeed);

        return onTarget;
    }

    public void gyroDrive_unenhanced ( double speed,
                                       double distance,
                                       double angle) {

        int     newLeftTarget;
        int     newRightTarget;
        int     moveCounts;
        double  max;
        double  error;
        double  steer;
        double  leftSpeed;
        double  rightSpeed;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            moveCounts = (int)(distance * COUNTS_PER_INCH);
            newLeftTarget = robot.DriveTrain().getLeftPosition() + moveCounts;
            newRightTarget = robot.DriveTrain().getRightPosition() + moveCounts;

            // Set Target and Turn On RUN_TO_POSITION
            robot.DriveTrain().setLeftTarget(newLeftTarget);
            robot.DriveTrain().setRightTarget(newRightTarget);

            robot.DriveTrain().setRunToPosition();

            // start motion.
            speed = Range.clip(Math.abs(speed), 0.0, 1.0);
            robot.DriveTrain().setMotorPower(speed);

            // keep looping while we are still active, and BOTH motors are running.
            while (opModeIsActive() &&
                    (robot.DriveTrain().isLeftBusy() && robot.DriveTrain().isRightBusy())) {

                // adjust relative speed based on heading error.
                error = getError(angle);
                steer = getSteer(error, P_DRIVE_COEFF);

                // if driving in reverse, the motor correction also needs to be reversed
                if (distance < 0)
                    steer *= -1.0;

                leftSpeed = speed - steer;
                rightSpeed = speed + steer;

                // Normalize speeds if either one exceeds +/- 1.0;
                max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
                if (max > 1.0)
                {
                    leftSpeed /= max;
                    rightSpeed /= max;
                }

                double placeholder = leftSpeed;
                leftSpeed = rightSpeed;
                rightSpeed = placeholder;

                robot.DriveTrain().setLeftMotorPower(leftSpeed);
                robot.DriveTrain().setRightMotorPower(rightSpeed);
            }

            // Stop all motion;
            robot.DriveTrain().setMotorPower(0);

            // Turn off RUN_TO_POSITION
            robot.DriveTrain().runMotorsWithEncoders();
        }
    }

    public void gyroTurn_unenhanced (  double speed, double angle) {
        // keep looping while we are still active, and not on heading.
        while (opModeIsActive() && !onHeading(speed, angle, P_TURN_COEFF)) {
            idle();
        }
    }

    public void gyroHold_unenhanced( double speed, double angle, double holdTime) {

        ElapsedTime holdTimer = new ElapsedTime();

        // keep looping while we have time remaining.
        holdTimer.reset();
        while (opModeIsActive() && (holdTimer.time() < holdTime)) {
            // Update telemetry & Allow time for other processes to run.
            onHeading(speed, angle, P_TURN_COEFF);
        }

        // Stop all motion;
        robot.DriveTrain().setMotorPower(0);
    }

    public void gyroDrive(double speed, double distance) {
        gyroDrive_unenhanced(speed,distance,currentAngle);
    }

    public void gyroTurn(double speed, double angle) {
        currentAngle += angle;
        gyroTurn_unenhanced(speed,currentAngle);
        gyroHold_unenhanced(speed,currentAngle,1.0);
    }
}

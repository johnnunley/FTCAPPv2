package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by jtnunley on 12/5/17.
 */

@Autonomous(name="Autonomous - Knock over Jewel",group="MBot")
public class MBotAutonomousArmDown extends MBotAutonomousBase {

    @Override
    protected boolean run() throws InterruptedException {
        //gyroDrive(FORWARD_SPEED / 3,-2);

        encoderDrive_gemArm(180,1.5);
        sleep(1000);

        boolean correctOrientation = (robot.TeamViewer().getValue(0) == 0.0);
        if (correctOrientation && robot.GemArm().isRed()) correctOrientation = false;
        else if (!correctOrientation && robot.GemArm().isRed()) correctOrientation = true;

        if (correctOrientation) gyroTurn(TURN_SPEED,-45);
        else gyroTurn(TURN_SPEED,45);

        encoderDrive_gemArm(0,1.5);

        if (correctOrientation) gyroTurn(TURN_SPEED,45);
        else gyroTurn(TURN_SPEED,-45);

        gyroDrive(FORWARD_SPEED,3*12);

        double teamFactor = (robot.TeamViewer().getValue(0) == 1.0 ? -1 : 1);
        gyroTurn(TURN_SPEED,-90 * teamFactor);
        gyroDrive(FORWARD_SPEED,3*12);

        return false;
    }
}

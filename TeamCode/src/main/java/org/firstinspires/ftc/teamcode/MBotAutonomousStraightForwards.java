package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by jtnunley on 10/12/17.
 */

@Autonomous(name="Autonomous - Go Straight",group="MBot")
public class MBotAutonomousStraightForwards extends MBotAutonomousBase {
    @Override
    protected boolean run() throws InterruptedException {
        encoderDrive(FORWARD_SPEED,3*12,3*12,30);
        return false;
    }


}

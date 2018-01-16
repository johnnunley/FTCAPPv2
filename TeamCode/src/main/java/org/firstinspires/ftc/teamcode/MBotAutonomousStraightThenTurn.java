package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by jtnunley on 11/7/17.
 */

@Autonomous(name="Autonomous - Placing Glyph",group="MBot")
public class MBotAutonomousStraightThenTurn extends MBotAutonomousBase {
    @Override
    protected boolean run() throws InterruptedException {
        gyroDrive(FORWARD_SPEED,3*12);
        gyroDrive(FORWARD_SPEED,-(6));
        return false;
    }


}

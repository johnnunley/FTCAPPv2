package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by jtnunley on 10/19/17.
 */

@TeleOp(name="TeleOp - Two Controllers",group="MBot Teleop")
public class MBotTeleOpTwoStick extends MBotTeleOpMainBase {
    @Override
    protected boolean usingTwoControllers() {
        return true;
    }
}
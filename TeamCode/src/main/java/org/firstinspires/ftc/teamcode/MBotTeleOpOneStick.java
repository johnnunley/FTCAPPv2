package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by jtnunley on 10/17/17.
 */

@TeleOp(name="TeleOp - One Controller",group="MBot Teleop")
public class MBotTeleOpOneStick extends MBotTeleOpMainBase {


    @Override
    protected boolean usingTwoControllers() {
        return false;
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by jtnunley on 10/6/17.
 */

@Autonomous(name="Sensor Test")
public class MBotSensorTest extends MBotAutonomousBase {

    @Override
    protected boolean run() throws InterruptedException {
        sleep(2*60*1000);
        return false;
    }
}

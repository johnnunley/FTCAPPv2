package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by jtnunley on 10/12/17.
 */

public class TeamView implements SensorWrapper {
    private DigitalChannel tv;

    public TeamView(HardwareMap hmx) {
        tv = hmx.digitalChannel.get(MBotConstants.teamSensorName);
    }

    // Note: Red = 1.0, Blue = 0.0
    @Override
    public double getValue(int index) {
        if (index == 0) {
            if (getColor() == TeamColor.Blue)
                return 0;
            else
                return 1;
        }
        return Double.NaN;
    }

    @Override
    public String name() {
        return "Team";
    }

    public TeamColor getColor() {
        if (!tv.getState())
            return TeamColor.Red;
        else
            return TeamColor.Blue;
    }
}

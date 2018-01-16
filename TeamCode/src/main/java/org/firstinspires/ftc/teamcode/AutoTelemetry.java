package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by jtnunley on 10/6/17.
 */

public class AutoTelemetry {
    private AutoTelemetry() { }
    public static void RecordData(Telemetry telemetry, MBotHardware hw) {
        telemetry.clear();
        telemetry.addData("Status",hw.getStatus());
        for (SensorWrapper sw : hw.SensorWrappers()) {
            double val;
            String name = sw.name();
            int i = 0;
            while (!(Double.isNaN((val = sw.getValue(i))))) {
                telemetry.addData(name + i, val);
                i++;
            }
        }
        telemetry.update();
    }
}

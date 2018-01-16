package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by jtnunley on 10/12/17.
 */

public abstract class MBotOpBase extends LinearOpMode {

    protected MBotHardware robot;
    protected ElapsedTime runtime;

    public MBotOpBase() {
    }

    private boolean running = false;
    private boolean isRunning() { return running; }

    private final class AutoTelemetryRunnable implements Runnable {
        private Telemetry tr;
        private MBotHardware robot;
        private MBotOpBase opBase;

        public AutoTelemetryRunnable(Telemetry t, MBotHardware r, MBotOpBase oBase) {
            tr = t;
            robot = r;
            opBase = oBase;
        }

        @Override
        public void run() {
            while (opBase.isRunning())
                AutoTelemetry.RecordData(tr,robot);
        }
    }

    protected abstract boolean isAutonomous();
    protected abstract boolean run() throws InterruptedException;
    protected abstract void initialize();

    @Override
    public void runOpMode() throws InterruptedException {
        running = true;

        telemetry.addData("Status","Initializing Robot");
        telemetry.update();

        robot = new MBotHardware(hardwareMap);
        runtime = new ElapsedTime();

        initialize();

        robot.setStatus("Ready to Run");

        Thread th = new Thread(new AutoTelemetryRunnable(telemetry,robot,this));
        th.start();
        waitForStart();

        if (isAutonomous()) {
            robot.setStatus("Running Auto Mode");
            run();
        }
        else {
            robot.setStatus("Running TeleOp Mode");
            while (opModeIsActive()) {
                if (!run()) break;
            }
        }

        running = false;
    }
}

package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {
    Servo clawL;
    Servo clawR;

    public Claw(HardwareMap hardwareMap) {
        clawL = hardwareMap.get(Servo.class, "clawL");
        clawR = hardwareMap.get(Servo.class, "clawR");
    }

    public void close() {
        clawL.setPosition(0.42); // i don't know if 1 is open or close (part of why it's helpful to encapsulate logic in classes using methods that are self describing)
        clawR.setPosition(0.605);
    }

    public void open() {
        clawL.setPosition(0.7);  // i don't know if 0 is open or close
        clawR.setPosition(0.3);
    }

    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addData("CLAW_L_Position", clawL.getPosition());
        telemetry.addData("CLAW_R_Position", clawR.getPosition());
    }
}

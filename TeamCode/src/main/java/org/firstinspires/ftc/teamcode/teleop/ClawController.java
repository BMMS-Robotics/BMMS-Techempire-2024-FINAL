package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawController {

    Servo claw ;

    public ClawController(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "claw");
    }

    public void open() {
        claw.setPosition(1);
    }

    public void close() {
        claw.setPosition(0);
    }
}

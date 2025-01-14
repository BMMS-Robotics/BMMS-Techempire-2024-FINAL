package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {

    // Class for arm logic

    // Variables

    // Floats
    double targetPosition = 0.85; // Should this equal the maxArmPosition instead of 1?
    double maxArmPosition = 0.85;
    double minArmPosition = 0.5;
    double armPositionIncrement = 0.02;

    // Hardware
    Servo arm;

    public Arm(HardwareMap hardwareMap) { // Arm init
        arm = hardwareMap.get(Servo.class, "arm");
    }

    public void moveUp() { // Move the arm up (towards linear slide)
        setTargetPosition(targetPosition + armPositionIncrement);
    }

    public void moveDown() { // Move the arm down (towards the ground)
        setTargetPosition(targetPosition - armPositionIncrement);
    }


    private void setTargetPosition(double target) {
        // Ensure the target position is within the allowed range

        // Clamp to the minimum. For example, if target 0 and minimum is .1, use .1
        target = Math.max(minArmPosition, target);

        // Clamp to the maximum. For example, if target 2 and max is 1, use 1
        this.targetPosition = Math.min(target, maxArmPosition);

        // Move the arm to the safe target position
        arm.setPosition(targetPosition);
    }

    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addData("Arm target position", targetPosition);
        telemetry.addData("Arm position", arm.getPosition());
        telemetry.addData("Arm direction", arm.getDirection());
    }
}

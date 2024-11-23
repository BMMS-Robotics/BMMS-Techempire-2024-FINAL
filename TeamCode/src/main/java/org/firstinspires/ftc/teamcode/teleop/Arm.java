package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The Arm class manages the movement of a servo-controlled robotic arm.
 * It provides methods to move the arm up and down within defined software limits, ensuring
 * the arm operates safely and smoothly.
 *
 * <p>Key Features:</p>
 * <ul>
 *   <li>Encapsulates arm movement logic to ensure position stays within allowable bounds.</li>
 *   <li>Adjustable software limits (`minArmPosition` and `maxArmPosition`) to prevent physical damage or overextension.</li>
 *   <li>Supports incremental movement for fine control using `moveUp()` and `moveDown()` methods.</li>
 *   <li>Provides telemetry feedback on the arm's target and actual position for debugging and monitoring.</li>
 * </ul>
 *
 * <p>Usage:</p>
 * <ol>
 *   <li>Instantiate the ArmController by passing the `HardwareMap` to its constructor.</li>
 *   <li>Call `moveUp()` or `moveDown()` to adjust the arm's position incrementally.</li>
 *   <li>Use `updateTelemetry()` to display the arm's state during operation.</li>
 * </ol>
 *
 * <p>Example:</p>
 * <pre>
 *   Arm arm = new Arm(hardwareMap);
 *   arm.moveUp(); // Moves the arm incrementally upward
 *   arm.moveDown(); // Moves the arm incrementally downward
 *   arm.updateTelemetry(telemetry); // Outputs arm data to telemetry
 * </pre>
 *
 * <p>Notes:</p>
 * <ul>
 *   <li>The initial value of `targetPosition` should ideally match the starting position of the servo.
 *       If the arm starts at `maxArmPosition`, initialize `targetPosition` to `maxArmPosition` instead of `1`.</li>
 *   <li>The software limits (`minArmPosition` and `maxArmPosition`) can be adjusted to suit the physical design of the robot.</li>
 * </ul>
 */
public class Arm {

    double targetPosition = 1; // Should this equal the maxArmPosition instead of 1?

    //Fun software limits with kaitlyn
    double maxArmPosition = 0.9;
    double minArmPosition = 0.5;
    double armPositionIncrement = 0.025;

    Servo arm;

    public Arm(HardwareMap hardwareMap) {
        arm = hardwareMap.get(Servo.class, "arm");
    }

    public void moveUp() {
        setTargetPosition(targetPosition + armPositionIncrement);
    }

    public void moveDown() {
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

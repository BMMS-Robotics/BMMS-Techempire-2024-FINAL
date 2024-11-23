package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The Claw class provides an abstraction for controlling the robot's claw mechanism.
 * It encapsulates the logic for opening and closing the claw, allowing for easy integration
 * and clear, self-describing method calls in other parts of the code.
 *
 * <p>Key Features:</p>
 * <ul>
 *   <li>Encapsulates the claw's servo control logic for opening and closing.</li>
 *   <li>Provides methods that are self-descriptive (e.g., `open()` and `close()`).</li>
 *   <li>Supports telemetry updates to monitor the claw's position during operation.</li>
 * </ul>
 *
 * <p>Benefits:</p>
 * <ul>
 *   <li>Hides raw servo position values (e.g., `0` and `1`) to improve code readability and maintainability.</li>
 *   <li>Allows for adjustments to the servo's open and close positions in one centralized place,
 *       without changing references throughout the codebase.</li>
 * </ul>
 *
 * <p>Usage:</p>
 * <ol>
 *   <li>Instantiate the ClawController by passing the `HardwareMap` to its constructor.</li>
 *   <li>Use the `open()` and `close()` methods to control the claw's state.</li>
 *   <li>Call `updateTelemetry()` to display the current claw position during operation.</li>
 * </ol>
 *
 * <p>Example:</p>
 * <pre>
 *   Claw claw = new Claw(hardwareMap);
 *   claw.open(); // Opens the claw
 *   claw.close(); // Closes the claw
 *   claw.updateTelemetry(telemetry); // Sends claw position to telemetry
 * </pre>
 */
public class Claw {
    Servo claw ;

    public Claw(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "claw");
    }

    public void open() {
        claw.setPosition(1); // i don't know if 1 is open or close (part of why it's helpful to encapsulate logic in classes using methods that are self describing)
    }

    public void close() {
        claw.setPosition(0);  // i don't know if 0 is open or close
    }

    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addData("CLAW_Position", claw.getPosition());
    }
}

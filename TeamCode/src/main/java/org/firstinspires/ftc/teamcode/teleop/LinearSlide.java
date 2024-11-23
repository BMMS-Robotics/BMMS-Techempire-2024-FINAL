package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The LinearSlide class manages the operation of a motorized linear slide,
 * enabling controlled upward and downward movement within defined bounds.
 * It provides methods for adjusting the slide's position incrementally and smoothly,
 * while ensuring safety and accuracy through software limits and power scaling.
 *
 * <p>Key Features:</p>
 * <ul>
 *   <li>Controls the slide's position using encoder feedback for precise movement.</li>
 *   <li>Supports adjustable power scaling for smoother movement as the slide nears the target.</li>
 *   <li>Implements software limits to prevent the slide from exceeding physical constraints.</li>
 *   <li>Provides telemetry feedback on slide position, target, power, and motor state.</li>
 * </ul>
 *
 * <p>Usage:</p>
 * <ol>
 *   <li>Instantiate the LinearSlide by passing the `HardwareMap` to its constructor.</li>
 *   <li>Use `slideUp()` or `slideDown()` to move the slide incrementally.</li>
 *   <li>Call `adjustSlide()` in a loop (e.g., during teleop) to manage real-time slide control.</li>
 *   <li>Use `updateTelemetry()` to monitor the slide's state during operation.</li>
 * </ol>
 *
 * <p>Example:</p>
 * <pre>
 *   LinearSlide linearSlide = new LinearSlide(hardwareMap);
 *   linearSlide.slideUp(); // Move the slide upward incrementally
 *   linearSlide.adjustSlide(); // Adjust the slide to move smoothly toward the target
 *   linearSlide.updateTelemetry(telemetry); // Output slide status to telemetry
 * </pre>
 *
 * <p>Notes:</p>
 * <ul>
 *   <li>The slide uses encoder feedback to maintain precise control of its position.</li>
 *   <li>The power scaling logic in `calculateSmoothPower()` ensures smooth motion, but if
 *       smoother behavior is unnecessary, you can use a constant power (e.g., `0.8`) instead.</li>
 *   <li>The target position is clamped within software-defined limits (`MIN_EXTENDED_HEIGHT`
 *       and `MAX_EXTENDED_HEIGHT`) to prevent damage or out-of-range motion.</li>
 * </ul>
 */
public class LinearSlide {
    public static final int TICK_INCREMENT = 100;
    private static final int POSITION_TOLERANCE = 15; // Adjusted for smoother stops
    private static final double MAX_POWER = 1.0;
    private static final double MIN_POWER = 0.3; // Adjusted to avoid stalling

    private static final int MAX_EXTENDED_HEIGHT = 3850;
    private static final int MIN_EXTENDED_HEIGHT = 0;

    private final DcMotor linearSlideMotor;
    private int targetPositionInTicks;

    public LinearSlide(HardwareMap hardwareMap) {
        linearSlideMotor = hardwareMap.dcMotor.get("linearSlide");

        // Reset encoder and set initial motor mode
        linearSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Initialize target position to the current position
        targetPositionInTicks = linearSlideMotor.getCurrentPosition();
        linearSlideMotor.setTargetPosition(targetPositionInTicks); // Align the motor's target position
    }

    public void adjustSlide() {
        // Get the current position of the slide
        int currentPositionInTicks = linearSlideMotor.getCurrentPosition();
        int distanceToTarget = targetPositionInTicks - currentPositionInTicks;

        // If we're within the tolerance, stop the motor
        if (Math.abs(distanceToTarget) <= POSITION_TOLERANCE) {
            stopMotor();
            return;
        }

        // Calculate power needed to move smoothly toward the target
        double calculatedPower = calculateSmoothPower(distanceToTarget);

        // Set the target position and ensure the motor is in position control mode
        linearSlideMotor.setTargetPosition(targetPositionInTicks);
        ensureRunToPositionMode();

        // Apply the calculated power to the motor
        linearSlideMotor.setPower(calculatedPower);
    }

    private double calculateSmoothPower(int distanceToTarget) {
        // Calculate the distance ratio
        double distanceRatio = (double) Math.abs(distanceToTarget) / MAX_EXTENDED_HEIGHT;

        // Scale the ratio to the power range
        double powerRange = MAX_POWER - MIN_POWER;
        double scaledPower = distanceRatio * powerRange + MIN_POWER;

        // Clamp the power to stay within allowed limits
        return Math.max(MIN_POWER, Math.min(scaledPower, MAX_POWER));
    }

    private void ensureRunToPositionMode() {
        if (linearSlideMotor.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            linearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    private void stopMotor() {
        if (linearSlideMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            linearSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        linearSlideMotor.setPower(0);
    }

    public void slideUp() {
        setTargetPosition(Math.min(targetPositionInTicks + TICK_INCREMENT, MAX_EXTENDED_HEIGHT));
    }

    public void slideDown() {
        setTargetPosition(Math.max(targetPositionInTicks - TICK_INCREMENT, MIN_EXTENDED_HEIGHT));
    }

    private void setTargetPosition(int target) {
        // Clamp target position to valid range
        targetPositionInTicks = Math.max(MIN_EXTENDED_HEIGHT, Math.min(target, MAX_EXTENDED_HEIGHT));
        linearSlideMotor.setTargetPosition(targetPositionInTicks);
    }

    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addData("Slide Target Position", targetPositionInTicks);
        telemetry.addData("Slide Current Position", linearSlideMotor.getCurrentPosition());
        telemetry.addData("Slide Motor Power", linearSlideMotor.getPower());
        telemetry.addData("Slide Busy", linearSlideMotor.isBusy());
        telemetry.addData("Current Mode", linearSlideMotor.getMode());
    }
}

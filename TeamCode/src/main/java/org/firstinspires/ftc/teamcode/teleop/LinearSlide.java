package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LinearSlide {
    public static final int TICK_INCREMENT = 100;
    private static final int POSITION_TOLERANCE = 15; // Adjusted for smoother stops

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
        double calculatedPower = 0.5; //Power somewhat slow to make slide smooth

        // Set the target position and ensure the motor is in position control mode
        linearSlideMotor.setTargetPosition(targetPositionInTicks);
        ensureRunToPositionMode();

        // Apply the calculated power to the motor
        linearSlideMotor.setPower(calculatedPower);
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

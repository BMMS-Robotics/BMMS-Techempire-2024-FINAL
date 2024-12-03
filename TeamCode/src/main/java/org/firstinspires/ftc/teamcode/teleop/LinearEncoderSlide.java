package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LinearEncoderSlide {
    public static final int MAX_POSITION = 3850; // Maximum encoder value
    public static final int MIN_POSITION = 0;    // Minimum encoder value
    public static final double SLIDE_POWER = 0.8; // Power for sliding up/down

    private final DcMotor linearSlideMotor;

    public LinearEncoderSlide(HardwareMap hardwareMap) {
        linearSlideMotor = hardwareMap.dcMotor.get("linearSlide");

        // Reset encoder and set initial motor mode
        linearSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearSlideMotor.setDirection(DcMotorSimple.Direction.FORWARD); // Might need to be BACKWARD

        initializePosition();
    }

    public void slideUp() {
        // Set power to move up, respecting the maximum position limit
        setPowerWithLimits(SLIDE_POWER);
    }

    public void slideDown() {
        // Set power to move down, respecting the minimum position limit
        setPowerWithLimits(-SLIDE_POWER);
    }

    private void setPowerWithLimits(double power) {
        int currentPosition = linearSlideMotor.getCurrentPosition();

        // Enforce limits on motion
        if (currentPosition >= MAX_POSITION && power > 0) {
            // Prevent upward motion beyond the maximum position
            linearSlideMotor.setPower(0);
        } else if (currentPosition <= MIN_POSITION && power < 0) {
            // Prevent downward motion beyond the minimum position
            linearSlideMotor.setPower(0);
        } else {
            // Allow motion within the limits
            linearSlideMotor.setPower(power);
        }
    }

    public void stopMotor() {
        // Immediately stop the motor
        linearSlideMotor.setPower(0);
    }

    // TODO - we can try this to see if we can get the linear slide to move to the 0 state when it starts (so that things work regardless of where the the linear slide starts)
    public void initializePosition() {
       /*
        // Ensure the motor is in manual control mode
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Start moving the slide downward at a low power
        linearSlideMotor.setPower(-0.3); // Use a low power to prevent strain

        long startTime = System.currentTimeMillis();
        int previousPosition = linearSlideMotor.getCurrentPosition();

        while (System.currentTimeMillis() - startTime < 2000) { // 2-second time limit
            int currentPosition = linearSlideMotor.getCurrentPosition();

            // Check if the encoder position has stopped changing
            if (Math.abs(currentPosition - previousPosition) < 5) { // Tolerance for small movements
                linearSlideMotor.setPower(0); // Stop the motor
                break;
            }

            // Update the previous position for the next check
            previousPosition = currentPosition;

            // Short delay to avoid busy-waiting
            try {
                Thread.sleep(10); // 10ms delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle interruptions properly
            }
        }

        // Ensure the motor stops in case the time limit is reached
        linearSlideMotor.setPower(0);

        // Reset encoder to mark the current position as 0
        linearSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/
    }

    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addData("Slide Current Position", linearSlideMotor.getCurrentPosition());
        telemetry.addData("Slide Motor Power", linearSlideMotor.getPower());
        telemetry.addData("At Max", linearSlideMotor.getCurrentPosition() >= MAX_POSITION);
        telemetry.addData("At Min", linearSlideMotor.getCurrentPosition() <= MIN_POSITION);
    }
}


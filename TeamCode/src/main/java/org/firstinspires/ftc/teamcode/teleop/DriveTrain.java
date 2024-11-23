package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The DriveTrain class manages the robot's motion, allowing for omnidirectional movement
 * (forward, backward, strafing, and rotation) with adjustable speed control.
 *
 * <p>Key Features:</p>
 * <ul>
 *   <li>Controls four motors (front-left, back-left, front-right, back-right) for a mecanum drivetrain.</li>
 *   <li>Implements adjustable "slow-motion" mode for precise movement control.</li>
 *   <li>Allows for fine-tuning of motor power scaling to ensure smooth and balanced movement.</li>
 *   <li>Supports telemetry feedback to monitor the current speed multiplier (slowMo).</li>
 * </ul>
 *
 * <p>Usage:</p>
 * <ol>
 *   <li>Instantiate the DriveController by passing the `HardwareMap` to its constructor.</li>
 *   <li>Call the `drive()` method, passing joystick values for x (strafe), y (forward/backward), and rx (rotation).</li>
 *   <li>Use `slowDown()` and `speedUp()` to adjust the slow-motion multiplier for more or less sensitive driving.</li>
 *   <li>Use `updateTelemetry()` to display the current `slowMo` value during operation.</li>
 * </ol>
 *
 * <p>Example:</p>
 * <pre>
 *   DriveTrain driveTrain = new DriveTrain(hardwareMap);
 *   driveTrain.drive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
 *   driveTrain.slowDown(); // Increase slow-motion effect for finer control
 *   driveTrain.updateTelemetry(telemetry); // Display slowMo multiplier on telemetry
 * </pre>
 *
 * <p>Notes:</p>
 * <ul>
 *   <li>The right-side motors are reversed by default. Adjust the directions in the constructor if needed.</li>
 *   <li>The `slowMo` value scales all joystick inputs, providing smoother, more controlled movement
 *       when set to higher values.</li>
 *   <li>The `speedUp()` and `slowDown()` methods adjust the `slowMo` value within defined limits
 *       (`slowMoMin` and `slowMoMax`).</li>
 * </ul>
 */
public class DriveTrain {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    double slowMo = 2.5;
    double slowMoMax = 7.5;
    double slowMoMin = 1.5;
    double slowMoChangeSpeed = 0.5;

    public DriveTrain(HardwareMap hardwareMap) {
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void drive(double x, double y, double rx) {
        //POTENTIAL add slow-mo toggle
        double y_adjusted = y / slowMo; // Remember, Y stick value is (no longer) reversed
        double x_adjusted = -x * 1.1 / slowMo; // Counteract imperfect strafing
        double rx_adjusted = -rx / slowMo;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y_adjusted) + Math.abs(x_adjusted) + Math.abs(rx_adjusted), 1);
        double frontLeftPower = (y_adjusted + x_adjusted + rx_adjusted) / denominator;
        double backLeftPower = (y_adjusted - x_adjusted + rx_adjusted) / denominator;
        double frontRightPower = (y_adjusted - x_adjusted - rx_adjusted) / denominator;
        double backRightPower = (y_adjusted + x_adjusted - rx_adjusted) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }

    public void slowDown() {
        slowMo += slowMoChangeSpeed;
        if (slowMo > slowMoMax) {
            slowMo = slowMoMax;
        }
    }

    public void speedUp() {
        slowMo -= slowMoChangeSpeed;
        if (slowMo < slowMoMin) {
            slowMo = slowMoMin;
        }
    }

    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addData("Drive SlowMo", slowMo);
    }
}

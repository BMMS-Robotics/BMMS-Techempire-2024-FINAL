package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

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

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TeleOp2P extends LinearOpMode {

    private LinearSlideController slideController;

    @Override
    public void runOpMode() throws InterruptedException {

        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        DcMotor extend = hardwareMap.dcMotor.get("linearSlide");
        slideController = new LinearSlideController(extend);
        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Servo arm = hardwareMap.get(Servo.class, "arm");
        Servo claw = hardwareMap.get(Servo.class, "claw");


        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        double desiredArmPos = 1;
        double slowMo = 2.5;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //left_trigger, right_

            //POTENTIAL add slow-mo toggle
            double y = gamepad1.right_stick_y / slowMo; // Remember, Y stick value is (no longer) reversed
            double x = -gamepad1.right_stick_x * 1.1 / slowMo; // Counteract imperfect strafing
            double rx = -gamepad1.left_stick_x / slowMo;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            //arm.setPower(0.5);

            //double clawPower = gamepad1.right_trigger - gamepad1.left_trigger; // Takes input values of triggers (assuming values of 0-1) and subtracts

            //double extendPower = 0;
            double armPower = 0;


            if (gamepad2.dpad_up) {
                //extendPower = -1;
                slideController.TARGET_POSITION_TICKS += 100;
                sleep(25);
                //slideController.extendArm();

            } else if (gamepad2.dpad_down) {
                //extendPower = 1;
                slideController.TARGET_POSITION_TICKS -= 100;
                sleep(25);
                // slideController.retractArm();

            }
            if (slideController.TARGET_POSITION_TICKS > slideController.MAX_EXTEND_HEIGHT) {
                slideController.TARGET_POSITION_TICKS = slideController.MAX_EXTEND_HEIGHT;
            }
            if (slideController.TARGET_POSITION_TICKS < 0) {
                slideController.TARGET_POSITION_TICKS = 0;
            }

            //Get the claw
            if (gamepad2.right_trigger == 1) {
                claw.setPosition(1);
            } else if (gamepad2.left_trigger == 1) {
                claw.setPosition(0);
            }

            //Slowmo mode, maybe switch to triggers later?
            if (gamepad1.x) {
                slowMo += 0.5;
                sleep(25);
            }
            if (gamepad1.b) {
                slowMo -= 0.5;
                sleep(25);
            }
            if (slowMo > 7.5) {
                slowMo = 7.5;
            }
            if (slowMo < 1.5) {
                slowMo = 1.5;
            }

            if (gamepad2.y) {
                desiredArmPos += 0.025;
                sleep(25);
            } else if (gamepad2.a) {
                desiredArmPos -= 0.025;
                sleep(25);
            }
            //Limits to prevent claw from slamming against floor or robot
            if (desiredArmPos > 0.9) {
                desiredArmPos = 0.9;
            }
            if (desiredArmPos < 0.5) {
                desiredArmPos = 0.5;
            }








            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            //claw.setPosition(clawPower);
            arm.setPosition(desiredArmPos);
            //extend.setPower(extendPower);
            slideController.stopIfReached();
            //telemetry.addData("A", gamepad1.a);
            telemetry.addData("Slide Target Position", LinearSlideController.TARGET_POSITION_TICKS);
            telemetry.addData("Slide Position", extend.getCurrentPosition());
            telemetry.addData("Slide Motor Power", extend.getPower());
            telemetry.addData("Slide Busy", extend.isBusy());
            slideController.extendSlide();
            telemetry.update();

        }


    }



}
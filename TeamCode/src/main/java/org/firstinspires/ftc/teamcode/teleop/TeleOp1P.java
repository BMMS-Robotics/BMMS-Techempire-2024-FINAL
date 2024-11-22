package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TeleOp1P extends LinearOpMode {

    private LinearSlideController slideController;

    @Override
    public void runOpMode() throws InterruptedException {

        //Git dummy comment
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

        //Fun software limits with kaitlyn
        double maxArmPosition = 0.9;
        double minArmPosition = 0.5;
        double armSpeed = 0.025;

        double slowMoMax = 7.5;
        double slowMoMin = 1.5;
        double slowMoChangeSpeed = 0.5;

        double linearSlideChangeSpeed = 100;

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

            if (gamepad1.dpad_up) {
                //extendPower = -1;
                slideController.TARGET_POSITION_TICKS += linearSlideChangeSpeed;
                sleep(25);
                //slideController.extendSlid();

            } else if (gamepad1.dpad_down) {
                //extendPower = 1;
                slideController.TARGET_POSITION_TICKS -= linearSlideChangeSpeed;
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
            if (gamepad1.right_trigger == 1) {
                claw.setPosition(0);
            } else if (gamepad1.left_trigger == 1) {
                claw.setPosition(1);
            }

            //Slowmo mode, maybe switch to triggers later?
            if (gamepad1.x) {
                slowMo += slowMoChangeSpeed;
                sleep(25);
            }
            if (gamepad1.b) {
                slowMo -= slowMoChangeSpeed;
                sleep(25);
            }
            if (slowMo > slowMoMax) {
                slowMo = slowMoMax;
            }
            if (slowMo < slowMoMin) {
                slowMo = slowMoMin;
            }

            if (gamepad1.y) {
                desiredArmPos += armSpeed;
                sleep(25);
            } else if (gamepad1.a) {
                desiredArmPos -= armSpeed;
                sleep(25);
            }
            //Limits to prevent claw from slamming against floor or robot
            if (desiredArmPos > maxArmPosition) {
                desiredArmPos = maxArmPosition;
            }
            if (desiredArmPos < minArmPosition) {
                desiredArmPos = minArmPosition;
            }







            //Various power things set to run every loop

            //4 motors for movement
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            //Arm
            arm.setPosition(desiredArmPos);
            //extend.setPower(extendPower);
            slideController.stopIfReached();
            //Telemetry
            telemetry.addData("Slide Target Position", LinearSlideController.TARGET_POSITION_TICKS);
            telemetry.addData("Slide Position", extend.getCurrentPosition());
            telemetry.addData("Slide Motor Power", extend.getPower());
            telemetry.addData("Slide Busy", extend.isBusy());
            //experimental telemetry - has not been compiled
            telemetry.addData("Forward Direction", y);
            telemetry.addData("Sideways Direction", x);
            telemetry.addData("Rotational Direction", rx);
            //telemetry.addData("Arm Position", arm.getPosition);
            telemetry.addData("Desired Arm Position", desiredArmPos);
            //telemetry.addData("Claw", claw.getPosition);

            slideController.extendSlide();
            telemetry.update();

            //sleep(25);

        }


    }



}
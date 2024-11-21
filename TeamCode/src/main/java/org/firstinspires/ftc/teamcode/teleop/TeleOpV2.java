/*package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TeleOpV2 extends LinearOpMode {

    boolean SINGLE_PLAYER_MODE = false;
    GamepadHub gamepadHub;
    private LinearSlideController slideController;
    private ClawController clawController;
    DriveController driveController;

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

        driveController = new DriveController(hardwareMap);
        clawController = new ClawController(hardwareMap);

        if (SINGLE_PLAYER_MODE) {
            gamepadHub = new GamepadHub(gamepad1);
        } else {
            gamepadHub = new GamepadHub(gamepad1, gamepad2);
        }




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
            // CONTROL DRIVING
            if (gamepadHub.slowDownButtonPressed()) {
                driveController.slowDown();
                sleep(25);
            } else if (gamepadHub.speedUpButtonPressed()) {
                driveController.speedUp();
                sleep(25);
            }
                driveController.drive(GamepadHub.getDriveX(slowMo), GamepadHub.getDriveY(), GamepadHub.getDriveRX());

            // CONTROL CLAW
            if (gamepadHub.isOpenClawButtonPressed()) {
                claw.open();
            } else if (gamepadHub.isCloseClawButtonPressed()) {
                claw.close();
            }



            if (gamepad2.y) {
                desiredArmPos += armSpeed;
                sleep(25);
            } else if (gamepad2.a) {
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



}*/
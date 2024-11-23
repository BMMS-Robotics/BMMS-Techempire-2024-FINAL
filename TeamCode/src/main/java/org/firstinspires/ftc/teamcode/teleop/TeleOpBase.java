package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TeleOpBase extends LinearOpMode {

    // Override if you want SINGLE player mode (or change default below)
    protected PlayerMode getPlayerMode() {
        return PlayerMode.DOUBLE_PLAYER;
    };

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize RevDriverHub to process input (joystick, buttons, etc) from gamepad1 and gamepad2 based on the selected player mode.
        // In SINGLE_PLAYER_MODE, gamepad2 actions (e.g., open claw) are mapped to gamepad1 for solo operation.
        RevDriverHub revDriverHub = new RevDriverHub(getPlayerMode(), gamepad1, gamepad2);

        // Create classes for each of the parts of the bot that move independently
        DriveTrain driveTrain = new DriveTrain(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        LinearSlide linearSlide = new LinearSlide(hardwareMap);
        Arm arm = new Arm(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (isStopRequested()) {
                break; // Exit the loop immediately
            }

            // ADJUST DriveTrain per revDriverHub input
            if (revDriverHub.slowDownButtonPressed()) {
                driveTrain.slowDown();
            } else if (revDriverHub.speedUpButtonPressed()) {
                driveTrain.speedUp();
            }
            driveTrain.drive(revDriverHub.getDriveX(), revDriverHub.getDriveY(), revDriverHub.getDriveRX());

            // ADJUST CLAW open or close per revDriverHub input
            if (revDriverHub.isOpenClawButtonPressed()) {
                claw.open();
            } else if (revDriverHub.isCloseClawButtonPressed()) {
                claw.close();
            }

            // ADJUST Linear Slide up or down per revDriverHub input
            if (revDriverHub.isSlideUpButtonPressed()) {
                linearSlide.slideUp();
            } else if (revDriverHub.isSlideDownButtonPressed()) {
                linearSlide.slideDown();
            }
            linearSlide.adjustSlide();

            // ADJUST Arm up or down per revDriverHub input
            if (revDriverHub.isArmUpButtonPressed()) {
                arm.moveUp();
            } else if (revDriverHub.isArmDownButtonPressed()) {
                arm.moveDown();
            }

            // collect telemetry from controllers to help with debugging
            driveTrain.updateTelemetry(telemetry);
            claw.updateTelemetry(telemetry);
            linearSlide.updateTelemetry(telemetry);
            arm.updateTelemetry(telemetry);
            telemetry.update();
        }
    }
}
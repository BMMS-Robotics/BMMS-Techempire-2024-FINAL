package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TeleOpBase extends LinearOpMode {
    private PlayerMode playerMode = PlayerMode.DOUBLE_PLAYER;

    public TeleOpBase(PlayerMode playerMode) {
        super();
        this.playerMode = playerMode;
    }

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize RevDriverHub to process input (joystick, buttons, etc) from gamepad1 and gamepad2 based on the selected player mode.
        // In SINGLE_PLAYER_MODE, gamepad2 actions (e.g., open claw) are mapped to gamepad1 for solo operation.
        RevDriverHub revDriverHub = new RevDriverHub(playerMode, gamepad1, gamepad2);
        Tiffany tiffany = new Tiffany(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (isStopRequested()) {
                break; // Exit the loop immediately
            }

            tiffany.processDriverCommands(revDriverHub, telemetry);
        }
    }
}
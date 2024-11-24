package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TeleOp1P extends TeleOpBase {
    // Change mode to Single Player
    @Override
    protected PlayerMode getPlayerMode() {
        return PlayerMode.SINGLE_PLAYER;
    }

    // everything else done by TeleOpBase
}
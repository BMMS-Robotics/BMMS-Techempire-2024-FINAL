package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.Gamepad;

public class RevDriverHub {
    private final Gamepad gamePad1;
    private final Gamepad gamePad2;

    private final PlayerMode playerMode;

    public RevDriverHub(PlayerMode playerMode, Gamepad gamepad1, Gamepad gamepad2) {
        this.playerMode = playerMode;
        this.gamePad1 = gamepad1;
        this.gamePad2 = gamepad2;
    }

    public double getDriveX() {
        return gamePad1.right_stick_x;
    }

    public double getDriveY() {
        return gamePad1.right_stick_y;
    }

    public double getDriveRX() {
        return gamePad1.left_stick_x;
    }

    public boolean slowDownButtonPressed() {
        boolean result = (gamePad1.x);

        // debounce for 25ms to prevent button smashing effects
        safeSleep(25);
        return result;
    }

    public boolean speedUpButtonPressed() {
        boolean result =  (gamePad1.b);

        // debounce for 25ms to prevent button smashing effects
        safeSleep(25);

        return result;
    }

    private void safeSleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime); // Use Thread.sleep explicitly for clarity
        } catch (InterruptedException ex) {
            // Log the interruption for debugging
            System.err.println("Sleep interrupted: " + ex.getMessage());
            // Restore the interrupted status to ensure proper thread behavior
            Thread.currentThread().interrupt();
        }
    }

    // claw can be operated on controller 1 or 2
    public boolean isOpenClawButtonPressed() {
        if (playerMode == PlayerMode.SINGLE_PLAYER) {
            return gamePad1.right_trigger == 1;
        } else {
            return gamePad2.right_trigger == 1;
        }
    }

    public boolean isCloseClawButtonPressed() {
        if (playerMode == PlayerMode.SINGLE_PLAYER) {
            return gamePad1.left_trigger == 1;
        } else {
            return gamePad2.left_trigger == 1;
        }
    }

    public boolean isSlideUpButtonPressed() {
        if (playerMode == PlayerMode.SINGLE_PLAYER) {
            return gamePad1.dpad_up;
        } else {
            return gamePad2.dpad_up;
        }
    }

    public boolean isSlideDownButtonPressed() {
        if (playerMode == PlayerMode.SINGLE_PLAYER) {
            return gamePad1.dpad_down;
        } else {
            return gamePad2.dpad_down;
        }
    }

    public boolean isArmUpButtonPressed() {
        if (playerMode == PlayerMode.SINGLE_PLAYER) {
            return gamePad1.y;
        } else {
            return gamePad2.y;
        }
    }

    public boolean isArmDownButtonPressed() {
        if (playerMode == PlayerMode.SINGLE_PLAYER) {
            return gamePad1.a;
        } else {
            return gamePad2.a;
        }
    }
}

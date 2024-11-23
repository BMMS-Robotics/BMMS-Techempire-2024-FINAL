package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * The DriverHub class abstracts the interaction with game controllers (Gamepad1 and Gamepad2)
 * and provides a centralized interface for retrieving driver inputs based on the current player mode.
 *
 * <p>Key Features:</p>
 * <ul>
 *   <li>Supports both SINGLE_PLAYER and DOUBLE_PLAYER modes via the PlayerMode enum.</li>
 *   <li>Encapsulates controller-specific inputs for driving, arm control, claw control, and slide control.</li>
 *   <li>Implements basic button debouncing to handle rapid presses and prevent unintended actions.</li>
 *   <li>Handles inputs for gamepad buttons, triggers, and joystick axes.</li>
 * </ul>
 *
 * <p>Usage:</p>
 * <ol>
 *   <li>Instantiate the DriverHub with the desired PlayerMode and references to Gamepad1 and Gamepad2.</li>
 *   <li>Use the provided methods to query inputs like joystick movement, trigger presses, and button states.</li>
 * </ol>
 *
 * <p>Example:</p>
 * <pre>
 *   DriverHub driverHub = new DriverHub(PlayerMode.SINGLE_PLAYER, gamepad1, gamepad2);
 *   double driveX = driverHub.getDriveX();
 *   boolean openClaw = driverHub.isOpenClawButtonPressed();
 * </pre>
 *
 * <p>Player Modes:</p>
 * <ul>
 *   <li><strong>SINGLE_PLAYER:</strong> All controls are managed using Gamepad1.</li>
 *   <li><strong>DOUBLE_PLAYER:</strong> Driving remains on Gamepad1, while secondary functions (e.g., arm and claw) are mapped to Gamepad2.</li>
 * </ul>
 */
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
        boolean result = (gamePad1.x == true);

        // debounce for 25ms to prevent button smashing effects
        safeSleep(25);
        return result;
    }

    public boolean speedUpButtonPressed() {
        boolean result =  (gamePad1.b == true);

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

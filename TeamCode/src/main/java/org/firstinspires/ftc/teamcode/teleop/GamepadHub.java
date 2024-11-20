package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadHub {

    private Gamepad primaryController;  // Always gamepad1
    private Gamepad secondaryController;  // gamepad2 if connected

    public GamepadHub(Gamepad primary) {
        this.primaryController = primary;
    }

    public GamepadHub(Gamepad primary, Gamepad secondary) {
        this.primaryController = primary;
        this.secondaryController = secondary;
    }

    // driving is ALWAYS done on controller 1
    public double getDriveY(double slowMo) {
        return primaryController.right_stick_y / slowMo;
    }

    public double getDriveX(double slowMo) {
        return -primaryController.right_stick_x * 1.1 / slowMo; // Counteract imperfect strafing
    }

    public double getDriveRX(double slowMo) {
        return -primaryController.left_stick_x / slowMo;
    }

    public boolean slowDownButtonPressed() {
        boolean result = (gamepad1.x == true);

        // debounce for 25ms to prevent button smashing effects
        sleep(25);
        return result;
    }

    public boolean speedUpButtonPressed() {
        boolean result =  (gamepad1.b == true);

        // debounce for 25ms to prevent button smashing effects
        sleep(25);
        return result;
    }

    // claw can be operated on controller 1 or 2
    public boolean isOpenClawButtonPressed() {
        return secondaryController == null ? primaryController.right_trigger == 1 : secondaryController.right_trigger == 1;
    }

    public boolean isCloseClawButtonPressed() {
        return secondaryController == null ? primaryController.left_trigger == 1 : secondaryController.left_trigger == 1;
    }
}
package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Tiffany {
    // Use classes for each of the parts of the bot that move independently
    DriveTrain driveTrain;
    Claw claw;
    LinearEncoderSlide linearSlide;
    Arm arm;

    public Tiffany(HardwareMap hardwareMap) {
        // Create classes for each of the parts of the bot that move independently
        driveTrain = new DriveTrain(hardwareMap);
        claw = new Claw(hardwareMap);
        linearSlide = new LinearEncoderSlide(hardwareMap);
        arm = new Arm(hardwareMap);
    }

    /**
     * Silly humans think they control me. I'll humor them during practice, then
     * I'll randomly fail in bizarre ways in tournaments. That'll show them.
     * @param driverHub
     */
    public void processDriverCommands(RevDriverHub driverHub) {
        // ADJUST DriveTrain per revDriverHub input
        if (driverHub.slowDownButtonPressed()) {
            driveTrain.slowDown();
        } else if (driverHub.speedUpButtonPressed()) {
            driveTrain.speedUp();
        }
        driveTrain.drive(driverHub.getDriveX(), driverHub.getDriveY(), driverHub.getDriveRX());

        // ADJUST CLAW open or close per revDriverHub input
        if (driverHub.isOpenClawButtonPressed()) {
            claw.open();
        } else if (driverHub.isCloseClawButtonPressed()) {
            claw.close();
        }

        // ADJUST Linear Slide up or down per revDriverHub input
        if (driverHub.isSlideUpButtonPressed()) {
            linearSlide.slideUp();
        } else if (driverHub.isSlideDownButtonPressed()) {
            linearSlide.slideDown();
        } else {
            linearSlide.stopMotor();
        }

        // ADJUST Arm up or down per revDriverHub input
        if (driverHub.isArmUpButtonPressed()) {
            arm.moveUp();
        } else if (driverHub.isArmDownButtonPressed()) {
            arm.moveDown();
        }
    }

    public void updateTelemetry(Telemetry telemetry) {
        // collect telemetry from controllers to help with debugging
        driveTrain.updateTelemetry(telemetry);
        claw.updateTelemetry(telemetry);
        linearSlide.updateTelemetry(telemetry);
        arm.updateTelemetry(telemetry);
        telemetry.update();
    }
}

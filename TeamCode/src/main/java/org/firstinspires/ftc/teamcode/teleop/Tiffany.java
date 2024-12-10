package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

/*
       ___________    _______     ________   ________   _____         ___    __    __    __
      /____  ____/   /__  __/    / ______/  / ______/  / __ |        /   |  / /    \ \  / /
          / /          / /      / /___     / /___     / /_| |       / /| | / /      \ \/ /
         / /          / /      / ____/    / ____/    / ___  |      / / | |/ /        \  /
        / /        __/ /__    / /        / /        / /   | |     / /  |   /         / /
       /_/        /______/   /_/        /_/        /_/    |_|    /_/   |__/         /_/
 Totally Intelligent, Fully Functioning, Awesome, Neat, Yawing Robot
 */
public class Tiffany {
    // Use classes for each of the parts of the bot that move independently
    DriveTrain driveTrain;
    Claw claw;
    LinearSlide linearSlide;
    Arm arm;
    SightNav sightNav;

    public Tiffany(HardwareMap hardwareMap) {
        // Create classes for each of the parts of the bot that move independently
        driveTrain = new DriveTrain(hardwareMap);
        claw = new Claw(hardwareMap);
        linearSlide = new LinearSlide(hardwareMap);
        arm = new Arm(hardwareMap);
        sightNav = new SightNav(hardwareMap);
    }

    /**
     * Silly humans think they control me. I'll humor them during practice, then
     * I'll randomly fail in bizarre ways in tournaments. That'll show them.
     * @param driverHub
     */
    public void processDriverCommands(RevDriverHub driverHub, Telemetry telemetry) {
        // ADJUST DriveTrain per revDriverHub input
        if (driverHub.slowDownButtonPressed()) {
            driveTrain.slowDown();
        } else if (driverHub.speedUpButtonPressed()) {
            driveTrain.speedUp();
        }

        if (driverHub.isNavigateToClosestAprilTagButtonPressed()) {
            sightNav.navigateToClosestTag(driveTrain, telemetry);
        } else {
            driveTrain.drive(driverHub.getDriveX(), driverHub.getDriveY(), driverHub.getDriveRX());
        }

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
        }
        linearSlide.adjustSlide();

        // ADJUST Arm up or down per revDriverHub input
        if (driverHub.isArmUpButtonPressed()) {
            arm.moveUp();
        } else if (driverHub.isArmDownButtonPressed()) {
            arm.moveDown();
        }

        updateTelemetry(telemetry);
    }

    public void updateTelemetry(Telemetry telemetry) {
        // collect telemetry from controllers to help with debugging
        //driveTrain.updateTelemetry(telemetry);
        //claw.updateTelemetry(telemetry);
        //linearSlide.updateTelemetry(telemetry);
        //arm.updateTelemetry(telemetry);
        telemetry.update();
    }
}

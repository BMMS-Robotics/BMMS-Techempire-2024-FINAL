package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.VisionPortal;

import java.util.List;

public class SightNav {
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTagProcessor;

    public SightNav(HardwareMap hardwareMap) {
        // Initialize AprilTag processing
        aprilTagProcessor = new AprilTagProcessor.Builder().build();
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"),
                aprilTagProcessor
        );
    }
    public void navigateToClosestTag(DriveTrain driveTrain, Telemetry telemetry) {
        List<AprilTagDetection> detections = aprilTagProcessor.getDetections();

        if (!detections.isEmpty()) {
            // Find the closest tag
            AprilTagDetection closestTag = detections.stream()
                    .min((a, b) -> Double.compare(a.ftcPose.z, b.ftcPose.z))
                    .get();

            telemetry.addData("Navigating to Tag ID", closestTag.id);
            telemetry.addData("X", closestTag.ftcPose.x);
            telemetry.addData("Y", closestTag.ftcPose.y);
            telemetry.addData("Yaw", closestTag.ftcPose.yaw);
            telemetry.update();

            // Calculate distance to the tag
            double distance = Math.hypot(closestTag.ftcPose.x, closestTag.ftcPose.y); // Pythagorean distance

            // Calculate adaptive scaling factor (cap it to prevent excessive speed)
            double scaleFactor = Math.min(0.5, distance * 0.1); // Closer = smaller scaling, max 0.5

            // Scale X and Y movement
            double driveX = closestTag.ftcPose.x * scaleFactor;
            double driveY = closestTag.ftcPose.y * scaleFactor;

            // Adjust rotation based on yaw
            double rotation = closestTag.ftcPose.yaw * 0.1;

            // Drive toward the tag
            driveTrain.drive(driveX, driveY, rotation);
        } else {
            telemetry.addData("Status", "No tags detected");
            telemetry.update();
        }
    }


}

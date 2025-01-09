package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.VisionPortal;

import java.util.List;


public class AutoClass {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    DcMotor slideMotor;
    Servo arm;
    Servo ClawL;
    Servo ClawR;
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTagProcessor;

    public static int TARGET_POSITION_TICKS = 0; // -UNUSED-
    int POSITION_TOLERANCE = 10;

    int MAX_EXTEND_HEIGHT = 3850;

    public void Init(HardwareMap hardwareMap) {

        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        slideMotor = hardwareMap.dcMotor.get("linearSlide");
        arm = hardwareMap.servo.get("arm");
        ClawL = hardwareMap.servo.get("clawL");
        ClawR = hardwareMap.servo.get("clawR");

        //Reverse motors for simplicity
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //Have motors stop and resist external movements when power is set to 0 to counteract drift.
        //Note: If a program is running and the power is set to 0, do not roll the bot. This puts stress on the brakes. They're already stressed about finals.
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Encoder init for linear slide
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //Encoder init for drive train
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        // Initialize AprilTag processing
        aprilTagProcessor = new AprilTagProcessor.Builder().build();
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"),
                aprilTagProcessor
        );


    }

    //1 ft per 360 ms

    public void EncoderTest(int ticks, float power) throws InterruptedException {
        frontLeftMotor.setTargetPosition(ticks);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setTargetPosition(ticks);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setTargetPosition(ticks);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setTargetPosition(ticks);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
        while (frontLeftMotor.getCurrentPosition() < ticks) {
            //Wait
        }

//
    }
    public void Forward(int ticks, float power) throws InterruptedException {
        //region MotorInit
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setTargetPosition(ticks);
        backLeftMotor.setTargetPosition(ticks);
        frontRightMotor.setTargetPosition(ticks);
        backRightMotor.setTargetPosition(ticks);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //endregion

        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
        while (frontLeftMotor.getCurrentPosition() < ticks) {
            //Wait
        }
    }

    public void Backward(int ticks, float power) throws InterruptedException {

        //region MotorInit
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setTargetPosition(-ticks);
        backLeftMotor.setTargetPosition(-ticks);
        frontRightMotor.setTargetPosition(-ticks);
        backRightMotor.setTargetPosition(-ticks);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //endregion
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(-power);
        while (frontLeftMotor.getCurrentPosition() > -ticks) {
            //Wait
        }
    }

    public void Left(int ticks, float power) throws InterruptedException {
        //region MotorInit
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setTargetPosition(-ticks);
        backLeftMotor.setTargetPosition(ticks);
        frontRightMotor.setTargetPosition(ticks);
        backRightMotor.setTargetPosition(-ticks);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //endregion
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(-power);
        while (frontLeftMotor.getCurrentPosition() > -ticks) {
            //Wait
        }
    }

    public void Right(int ticks, float power) throws InterruptedException {
        //region MotorInit
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setTargetPosition(ticks);
        backLeftMotor.setTargetPosition(-ticks);
        frontRightMotor.setTargetPosition(-ticks);
        backRightMotor.setTargetPosition(ticks);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //endregion
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(power);
        while (frontLeftMotor.getCurrentPosition() < ticks) {
            //Wait
        }
    }

    public void TurnLeft(int ticks, float power) throws InterruptedException {
        //region MotorInit
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setTargetPosition(-ticks);
        backLeftMotor.setTargetPosition(-ticks);
        frontRightMotor.setTargetPosition(ticks);
        backRightMotor.setTargetPosition(ticks);


        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //endregion
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
        while (frontLeftMotor.getCurrentPosition() > -ticks) {
            //Wait
        }
    }

    public void TurnRight(int ticks, float power) throws InterruptedException {
        //region MotorInit
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setTargetPosition(ticks);
        backLeftMotor.setTargetPosition(ticks);
        frontRightMotor.setTargetPosition(-ticks);
        backRightMotor.setTargetPosition(-ticks);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //endregion
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(-power);
        while (frontLeftMotor.getCurrentPosition() < ticks) {
            //Wait
        }
    }


    public void LinearSlideController(DcMotor motor) {
        //Magical initialization stuff
        this.slideMotor = motor;

        // Reset encoder to start at zero
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motor direction if needed
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set motor to RUN_TO_POSITION mode for position control
        slideMotor.setTargetPosition(TARGET_POSITION_TICKS);


        // Set power for the motor to start moving towards the target
        //slideMotor.setPower(0);
    }

    public void extendSlide(int targetPos) {
        //set target position and move to it i guess
        slideMotor.setTargetPosition(targetPos);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (slideMotor.getCurrentPosition() != MAX_EXTEND_HEIGHT && targetPos >= POSITION_TOLERANCE && slideMotor.getPower() == 0) {
            slideMotor.setPower(1);
        }
        if (targetPos <= POSITION_TOLERANCE && slideMotor.getPower() != 0) {
            slideMotor.setPower(0);
        }
    }


    public void stopIfReached() {
        // Check if the arm has reached its target position
        if (!slideMotor.isBusy()) {
            // If the motor is no longer busy, stop the motor
            slideMotor.setPower(0);
            //Can it automatically keep its pos at 0? Probably not, but if so maybe try resetting encoder
        }
        //Limits to prevent motor from going bonkers, just in case
        if (slideMotor.getCurrentPosition() > MAX_EXTEND_HEIGHT) {
            slideMotor.setPower(0);
        }
        if (slideMotor.getCurrentPosition() < 0) {
            slideMotor.setPower(0);
        }
    }

    public void CloseClaw() {
        ClawL.setPosition(0.425);
        ClawR.setPosition(0.6);
    }
    public void OpenClaw() {
        ClawL.setPosition(0.7);
        ClawR.setPosition(0.3);
    }

    public void Arm(double pos) {
        //Just a precaution...
        if (pos > 0.85) {
            pos = 0.85; //Oh no you don't
        }
        if (pos < 0.5) {
            pos = 0.5;
        }
        arm.setPosition(pos);
    }

    public void RightUntilAprilTag() throws InterruptedException {
//        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setTargetPosition(10000);
        backLeftMotor.setTargetPosition(-10000);
        frontRightMotor.setTargetPosition(-10000);
        backRightMotor.setTargetPosition(10000);
        List<AprilTagDetection> detections = aprilTagProcessor.getDetections();
        frontLeftMotor.setPower(0.25);
        backLeftMotor.setPower(-0.25);
        frontRightMotor.setPower(-0.25);
        backRightMotor.setPower(0.25);

        while (true) {
            detections = aprilTagProcessor.getDetections();
            if (!detections.isEmpty()) {
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
                Thread.sleep(1000);
                break;
            }
        }

    }
    public void LeftUntilAprilTag() throws InterruptedException {
//        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setTargetPosition(-10000);
        backLeftMotor.setTargetPosition(10000);
        frontRightMotor.setTargetPosition(10000);
        backRightMotor.setTargetPosition(-10000);
        List<AprilTagDetection> detections = aprilTagProcessor.getDetections();
        frontLeftMotor.setPower(-0.25);
        backLeftMotor.setPower(0.25);
        frontRightMotor.setPower(0.25);
        backRightMotor.setPower(-0.25);

        while (true) {
            detections = aprilTagProcessor.getDetections();
            if (!detections.isEmpty()) {
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
                Thread.sleep(1000);
                break;
            }
        }

    }
}

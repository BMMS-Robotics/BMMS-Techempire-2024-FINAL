package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
public class AutoClass {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    DcMotor slideMotor;
    Servo arm;
    Servo ClawL;
    Servo ClawR;

    public static int TARGET_POSITION_TICKS = 0; // -UNUSED-
    int POSITION_TOLERANCE = 10;

    int MAX_EXTEND_HEIGHT = 3850;

    public void MotorInit(HardwareMap hardwareMap) {
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        slideMotor = hardwareMap.dcMotor.get("linearSlide");
        arm = hardwareMap.servo.get("arm");
        ClawL = hardwareMap.servo.get("clawL");
        ClawR = hardwareMap.servo.get("clawR");


        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);


    }
    //1 ft per 360 ms
    //Math is ft * 360 / power (get the distance, times 360 for milliseconds, divide by power (which will be 0-1) to get the time
    public void Forward(int time, double power) throws InterruptedException {;
        //Set to power, move in direction
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
        //Fancy math
        //Converts feet to milliseconds, divides by power (so if power is 0.5 then it's x2), floors to remove decimals and converts to an int.
        //Thread.sleep((int) Math.floor(dist * 375 / power));
        Thread.sleep(time);
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
        Thread.sleep(1000); //Give time for the motor to brake.
    }
    public void ForwardDist(double dist, double power) throws InterruptedException {;
        //Set to power, move in direction
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
        //Fancy math
        //Converts feet to milliseconds, divides by power (so if power is 0.5 then it's x2), floors to remove decimals and converts to an int.
//        Thread.sleep((int) Math.floor(dist * 375 / power));
        Thread.sleep((int) Math.floor(dist * 187.5 / power));
        //Thread.sleep(time);
        //Sets power to opposite for 10ms to counteract drift
        //Stops motors.
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
        Thread.sleep(500); //Time for the motor to brake.
    }

    public void Backward(double dist, double power) throws InterruptedException {
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(-power);
        Thread.sleep((int) Math.floor(dist * 187.5 / power));
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
        Thread.sleep(500);
    }

    public void Left(int dist, double power) throws InterruptedException {
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(-power);
        Thread.sleep((int) Math.floor(dist * 187.5 / power));
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
        Thread.sleep(500);
    }

    public void Right(int dist, double power) throws InterruptedException {
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(power);
        Thread.sleep((int) Math.floor(dist * 187.5 / power));
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
        Thread.sleep(500);
    }

    public void TurnLeft(int dist, double power) throws InterruptedException {
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
        Thread.sleep((int) Math.floor(dist * 360 / power)); //Do math for radians later
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(-power);
        Thread.sleep(10);
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void TurnRight(int dist, double power) throws InterruptedException {
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(-power);
        Thread.sleep((int) Math.floor(dist * 360 / power));
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
        Thread.sleep(10);
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
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

    public void extendSlide(int targetPos) { //The actually useful linear slide part. Call this and passthrough the target position ticks.
        //set target position and move to it i guess
        slideMotor.setTargetPosition(targetPos);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        /*
        if (slideMotor.getCurrentPosition() != MAX_EXTEND_HEIGHT && slideMotor.getPower() == 0) { //only apply the power if it's actually needed to prevent jolting the motor
            slideMotor.setPower(1); // Full power to reach target
        }
        //Maybe use abs value for better tolerance both up and down
        if (slideMotor.getCurrentPosition() <= POSITION_TOLERANCE && TARGET_POSITION_TICKS <= POSITION_TOLERANCE && slideMotor.getPower() != 1) { //Check if we're at/less than 0 and we want to be at 0. If so, turn off motor to reduce strain.
            slideMotor.setPower(0);
        }
        */
        if (slideMotor.getCurrentPosition() != MAX_EXTEND_HEIGHT && targetPos >= POSITION_TOLERANCE && slideMotor.getPower() == 0) {
            //  Ensure the motor is not out of its limit             If the target is lower than the position,          If the power's already on,
            //  just in case                                         set the power to 0 to prevent unneeded strain      why turn it on again?
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
}

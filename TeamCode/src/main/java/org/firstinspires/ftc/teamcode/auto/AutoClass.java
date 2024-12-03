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

    public static int TARGET_POSITION_TICKS = 0; // -UNUSED-
    int POSITION_TOLERANCE = 10;

    int MAX_EXTEND_HEIGHT = 3850;

    public void MotorInit(HardwareMap hardwareMap) {
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        slideMotor = hardwareMap.dcMotor.get("linearSlide");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void Forward(int time, double power) throws InterruptedException {
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
        Thread.sleep(time);
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(-power);
        Thread.sleep(10);
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void Backward(int time, double power) throws InterruptedException {
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(-power);
        Thread.sleep(time);
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
        Thread.sleep(10);
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void Left(int time, double power) throws InterruptedException {
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(-power);
        Thread.sleep(time);
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(power);
        Thread.sleep(10);
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void Right(int time, double power) throws InterruptedException {
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(power);
        Thread.sleep(time);
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(-power);
        Thread.sleep(10);
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void TurnLeft(int time, double power) throws InterruptedException {
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
        Thread.sleep(time);
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

    public void TurnRight(int time, double power) throws InterruptedException {
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(-power);
        Thread.sleep(time);
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

    public void extendSlide(int targetPos) { //Keep in mind this is called every tick! (Or so)
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
}

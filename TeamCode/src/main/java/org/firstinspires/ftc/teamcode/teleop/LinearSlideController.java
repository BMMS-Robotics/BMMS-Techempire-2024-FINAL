package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class LinearSlideController {
    public static int TARGET_POSITION_TICKS = 0; // Set at 0, modified in the teleop script
    int POSITION_TOLERANCE = 10; //Highish tolerance because gravity

    private DcMotor slideMotor;

    int MAX_EXTEND_HEIGHT = 3850;

    public LinearSlideController(DcMotor motor) {
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

    public void extendSlide() { //Keep in mind this is called every tick! (Or so)
        //set target position and move to it i guess
        slideMotor.setTargetPosition(TARGET_POSITION_TICKS);
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
        if (slideMotor.getCurrentPosition() != MAX_EXTEND_HEIGHT && TARGET_POSITION_TICKS >= POSITION_TOLERANCE && slideMotor.getPower() == 0) {
            //  Ensure the motor is not out of its limit             If the target is lower than the position,          If the power's already on,
            //  just in case                                         set the power to 0 to prevent unneeded strain      why turn it on again?
            slideMotor.setPower(1);
        }
        if (TARGET_POSITION_TICKS <= POSITION_TOLERANCE && slideMotor.getPower() != 0) {
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



// main code
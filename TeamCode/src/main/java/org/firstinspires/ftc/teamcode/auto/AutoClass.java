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

    // The main class for autonomous code.

    // Variables

    //Values
    public static int TARGET_POSITION_TICKS = 0; // -UNUSED-

    int POSITION_TOLERANCE = 0;

    int MAX_EXTEND_HEIGHT = 3850;

    double ClawLClosed = 0.425;
    double ClawRClosed = 0.6;
    double ClawLOpen = 0.7;
    double ClawROpen = 0.3;

    //Hardware
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

    // region Math
    /*
    Wheel diameter is 2 inches, meaning radius is 1in
    Circumfrence is 3.14
    So 1 full rotation would theoretically bring the bot forward 3.14 inches
    GoBilda Yellow Jacket has 600 RPM (i think) so 1884 inches/min or 157 ft/min
    300 RPM at 0.5x speed, 942 in/min, 78.5 ft/min,


    15.7 inches/second at 0.5 speed

    Encoder resolution is 537.7 PPR, so maybe 538 ticks per rotation? Try at home

    (inches / 3.14) is the rotations needed to travel that distance
     * 538 = the ticks needed for that distance (i think)

     so apparently if you wanted to go forward 1 inch it would be 172 ticks


    1 foot:

    (12 / 3.14) * 538
    3.82 * 538 = 2055.16 ticks, probably apply a floor, celing or round operand on each step
    round to 2055 ticks
    And that should be one foot
    which doesn't sound right

    well


    Maybe using static final double COUNTS_PER_MOTOR_REV = 1440; will do something

    LOOK aT THE VARIABLES FOR THE MORE FINAL MATH STUFF IDK

     */
    // endregion

    

    public void Init(HardwareMap hardwareMap) { //Initialize motors

        //Define and map motors & servos
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        slideMotor = hardwareMap.dcMotor.get("linearSlide");
        arm = hardwareMap.servo.get("arm");
        ClawL = hardwareMap.servo.get("clawL");
        ClawR = hardwareMap.servo.get("clawR");
        //Modify motors & servo
        //Do math and research to figure out the counts per motor revolution on the GoBilda 5203
        final double     COUNTS_PER_MOTOR_REV    = 538;    // Figure out later
        final double     DRIVE_GEAR_REDUCTION    = 1.0;     // 1.0 is no external gearing, figure out later
        final double     WHEEL_DIAMETER_INCHES   = 4.0;     // For figuring circumference
        //final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
        final double COUNTS_PER_INCH = 1028;

        //say 538 per revolution, to go 1 foot you would need 6.28 revolutions
        // 6.28 * 538 = 1027.58 rount to 1028 (nice round number)

        //IMPORTANT
        //1 foot = 1028 ticks

        //Too much? Too little? who knows.
        //To find counts per motor revolution, maybe slowly turn motor by
        //increments of 100 ticks, mark part of the wheel, and count ticks
        //until a full revolution.

        //Reverse motors for simplicity
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //Have motors stop and resist external movements when power is set to 0 to counteract drift.
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
    /*
    public void Forward(int ticks, float power) throws InterruptedException { //Move forward using encoders.
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
    */


    public void Forward(int inches, float power) throws InterruptedException { //Move forward using encoders.
        //region MotorInit
        int ticks = (int)(538/(4*3.1415)) * inches;
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

        //mhm mhm yes very productive what are you tal;king about iu am being
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
        while (frontLeftMotor.getCurrentPosition() < ticks) {
            //Wait
        }
    }

    public void Backward(int inches, float power) throws InterruptedException { //Move backward using encoders.
        int ticks = (int)(538/(4*3.1415)) * inches;
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

    public void Left(int inches, float power) throws InterruptedException { //Move left using encoders.
        int ticks = (int)(538/(4*3.1415)) * inches;
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

    public void Right(int inches, float power) throws InterruptedException { //Move right using encoders.
        int ticks = (int)(538/(4*3.1415)) * inches;
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

    public void TurnLeft(double degrees, float power) throws InterruptedException { //Turn left using encoders
        int inches = (int)(23 * (degrees / 90));
        int ticks = (int)((538/(4*3.1415)) * inches);
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
        frontRightMotor.setTargetPosition(ticks);
        backRightMotor.setTargetPosition(ticks);


        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
         /*
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

          */

        //endregion
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
//        Thread.sleep(ticks);
//        frontLeftMotor.setPower(0);
//        backLeftMotor.setPower(0);
//        frontRightMotor.setPower(0);
//        backRightMotor.setPower(0);
        while (frontLeftMotor.getCurrentPosition() > -ticks) {
            //Wait
        }
    }

    public void TurnRight(double degrees, float power) throws InterruptedException { //Turn right using encoders.
        //region MotorInit
        /*
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



         */
        int inches = (int)(23 * (degrees / 90));
        int ticks = (int)((538/(4*3.1415)) * inches);
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

        while (frontLeftMotor.getCurrentPosition() > ticks) {
            //Wait
        }
    }


    public void LinearSlideController(DcMotor motor) { //Linear slide init, unused
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

    public void extendSlide(int targetPos) { //Move the linear slide to the target position.
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

    public void CloseClaw() { //Close claw... maybe?
        ClawL.setPosition(0.425);
        ClawR.setPosition(0.6);
    }
    public void OpenClaw() { //Open claw maybe?
        ClawL.setPosition(0.7);
        ClawR.setPosition(0.3);
    }
    public void MaintainClosed(double time) throws InterruptedException {
        //Close claw
        ClawL.setPosition(ClawLClosed);
        ClawR.setPosition(ClawRClosed);
        float x = 0;
        while (true) { //Loop until done to continually force claw closed
            if (ClawL.getPosition() != ClawLClosed || ClawR.getPosition() != ClawRClosed) { //If the claw is not in its closed position, close it.
                ClawL.setPosition(ClawLClosed);
                ClawR.setPosition(ClawRClosed);
            }
            Thread.sleep(5); //Sleep, change value
            x += 5;
            if (x >= time) { //If it's waited for long enough, break the loop and continue the auto program
                break;
            }
        
        }
    }
    

    public void Arm(double pos) {
        //Set limits to prevent arm slamming against the bot or the wall.
        if (pos > 0.85) {
            pos = 0.85; //Oh no you don't
        }
        if (pos < 0.5) {
            pos = 0.5;
        }
        arm.setPosition(pos);
    }

    public void RightUntilAprilTag() throws InterruptedException { //Move right until it sees an AprilTag.
        //Set motor targets to a distant value
        frontLeftMotor.setTargetPosition(10000);
        backLeftMotor.setTargetPosition(-10000);
        frontRightMotor.setTargetPosition(-10000);
        backRightMotor.setTargetPosition(10000);
    
        //Process apriltags
        List<AprilTagDetection> detections = aprilTagProcessor.getDetections(); 

        //Move motors
        frontLeftMotor.setPower(0.25);
        backLeftMotor.setPower(-0.25);
        frontRightMotor.setPower(-0.25);
        backRightMotor.setPower(0.25);

        while (true) { //Check if an AprilTag is in sight, if so, stop the motors.
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
    public void LeftUntilAprilTag() throws InterruptedException { //Move left until it sees an apriltag
        //Set motor targets to a distant value
        frontLeftMotor.setTargetPosition(-10000);
        backLeftMotor.setTargetPosition(10000);
        frontRightMotor.setTargetPosition(10000);
        backRightMotor.setTargetPosition(-10000);

        //Process apriltags
        List<AprilTagDetection> detections = aprilTagProcessor.getDetections();

        //Move motors
        frontLeftMotor.setPower(-0.25);
        backLeftMotor.setPower(0.25);
        frontRightMotor.setPower(0.25);
        backRightMotor.setPower(-0.25);

        while (true) { //Check if an apriltag is in sight, if so, stop the motors.
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

package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class Auto1LS extends LinearOpMode {
    //Auto code that starts anywhere TO THE RIGHT of the very middle of the field. Preload with 1 (one) specimen.


    BNO055IMU imu;
    @Override
    public void runOpMode() throws InterruptedException {

        AutoClass autoClass = new AutoClass();


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            autoClass.Init(hardwareMap);
            autoClass.CloseClaw();
            autoClass.Forward(850, 0.5f);
            autoClass.Arm(0.85);
            Thread.sleep(1500);

            autoClass.RightUntilAprilTag(); // If there's any doubt, use the plain Auto1 script.

            Thread.sleep(1000);
            autoClass.Forward(150, 0.5f);


            autoClass.Arm(0.85); //Arm goes up to combat the energy transfer from moving
            Thread.sleep(500); //Sleep to allow arm to move

            autoClass.extendSlide(2700); //Linear slide goes up high enough to place specimen

            Thread.sleep(2000); //Wait for linear slide

            autoClass.Forward(200, 0.1f); //Forward slightly to properly align specimen

            autoClass.CloseClaw(); // Adjust grip on the claw

            autoClass.extendSlide(1750); //Move slide back down

            Integer i = 0;
            while (i < 1000) { //Overcomplicated code to force claw closed
                autoClass.CloseClaw();
                telemetry.addData(i.toString(), null);
                telemetry.update();
                Thread.sleep(10);

                i += 10;
            }
            Thread.sleep(400); //More time for linear slide
            autoClass.OpenClaw(); //Release specimen
            Thread.sleep(100); //Time for claw to open

            autoClass.extendSlide(11); //Set the linear slide to 0
            Thread.sleep(750); //Time for slide to go down
            autoClass.extendSlide(0); //Still having it going down just ignore this part

            autoClass.Backward(1000, 0.5f); //Move backwards (obviously)

            autoClass.Right(2000, 0.5f); //Move right to park

            //Wow much productivity
            Thread.sleep(1000);




            //autoClass.ForwardDist(0.25, 0.25);







            break;


        }
    }

    




}
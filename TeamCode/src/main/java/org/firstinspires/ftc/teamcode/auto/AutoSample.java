package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class AutoSample extends LinearOpMode {
    BNO055IMU imu;
    @Override
    public void runOpMode() throws InterruptedException {

        AutoClass autoClass = new AutoClass();
        //775 is a 90 degree turn, i think


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            autoClass.Init(hardwareMap); //Do not touch (Pretty please)

            autoClass.Forward(500, 0.5f);
            autoClass.TurnLeft(775, 0.5f);
            autoClass.Forward(500, 0.5f);
            autoClass.TurnLeft(387, 0.5f);
            autoClass.Forward(250, 0.5f);
            autoClass.extendSlide(3400);


            Thread.sleep(1500); //Time for final commands to finish executing

            break;
        }
    }






}
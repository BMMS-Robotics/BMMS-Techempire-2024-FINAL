package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class AutoTesting extends LinearOpMode {
    BNO055IMU imu;
    @Override
    public void runOpMode() throws InterruptedException {

        AutoClass autoClass = new AutoClass();


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            autoClass.Init(hardwareMap); //Do not touch (Pretty please)

            //autoClass.Forward(12, 0.5f);
            autoClass.TurnLeft(90, 0.5f);
            autoClass.TurnRight(90, 0.5f);
            



            Thread.sleep(1500); //Time for final commands to finish executing

            break;
        }
    }






}
package com.example.laboratorio10danp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.laboratorio10danp.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var  binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
            binding.apply {
                button.setOnClickListener{
                    periodicRequest()
                }
                OneTimeWorkRequest.setOnClickListener{
                    OneTimeWorkRequest()
                }
                button3.setOnClickListener{
                    OneTimeWorkRequest()
                    Thread.sleep(6000)
                    OneTimeWorkRequest2()
                }

            }

        //OneTimeWorkRequest()


    }
    private fun setCons(): Constraints {
        return Constraints.Builder().build()
    }
    private fun OneTimeWorkRequest(){
        val task= OneTimeWorkRequest.Builder(Eventos::class.java)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(setCons())
            .addTag("New Tag1")
            .build()
        val workManager= WorkManager.getInstance()
        workManager.enqueue(task)
    }
    private fun periodicRequest() {
       val constraints=Constraints.Builder()
           .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
           .build()
        val myWorkRequest=PeriodicWorkRequest.Builder(
            Eventos::class.java,
            15,
            TimeUnit.MINUTES
        ).setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .addTag("New tag2")
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("Mi id",ExistingPeriodicWorkPolicy.KEEP, myWorkRequest)

    }
    private fun OneTimeWorkRequest2(){
        val task= OneTimeWorkRequest.Builder(Evento2::class.java)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(setCons())
            .addTag("New Tag1.1")
            .build()
        val workManager= WorkManager.getInstance()
        workManager.enqueue(task)
    }

}
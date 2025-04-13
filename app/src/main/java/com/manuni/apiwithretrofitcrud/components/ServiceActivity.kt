package com.manuni.apiwithretrofitcrud.components

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.components.bound_service.MyServiceBound
import com.manuni.apiwithretrofitcrud.components.foreground_service.MyServiceForeground
import com.manuni.apiwithretrofitcrud.components.foreground_service.NotificationService
import com.manuni.apiwithretrofitcrud.databinding.ActivitySecondBinding
import kotlin.random.Random

class ServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private val TAG = "ServiceActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            startService(intent)
        }

        binding.stopServiceBtn.setOnClickListener {
            stopService(Intent(this, MyService::class.java))
        }

        binding.startNotificationBtn.setOnClickListener {

            showNotification()

        }

        binding.foregroundBtn.setOnClickListener {
            //we can also data passing with the intent
            val intent = Intent(this,MyServiceForeground::class.java)
            intent.putExtra("MY_NAME","Mahamud Islam")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                startForegroundService(intent)
            }

        }

        binding.stopForegroundBtn.setOnClickListener {
            val i = Intent(this,MyServiceForeground::class.java)
            stopService(i)
        }


        //bound service
        binding.startBound.setOnClickListener {
            val i = Intent(this,MyServiceBound::class.java)
            startService(i)
            bindService(i,serviceConnection,Context.BIND_AUTO_CREATE)
        }

        binding.stopBound.setOnClickListener {
            stopService(Intent(this,MyServiceBound::class.java))
            unbindService(serviceConnection)
        }

        binding.switchBoundGenNumber.setOnCheckedChangeListener { compoundButton, isChecked ->
            myServiceBound?.printRandomNumber(isChecked)
        }

    }

    //these interface is used to connect the bound service
    var myBinder:MyServiceBound.MyBinder? = null
    var myServiceBound:MyServiceBound? = null

    //at first we need to connect with Service from this activity and then only we need to get the object of the Bound service class
    val serviceConnection: ServiceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: ")
            myBinder = service as MyServiceBound.MyBinder
            myServiceBound = myBinder?.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }



    private fun showNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 100)
                return // 🔙 Stop execution here until permission is granted
            }
        }

        val intent = Intent(this, NotificationService::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK//it used here because if the activity is already opened then it will open the activity as new task and clear the previous task

            /**java format
             * Intent intent = new Intent(this, NotificationService.class);
             * intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
             */

        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )//IMMUTABLE - no change object of pending intent
        val channel_id = "my_channel"
        //we can use these two lines when use: jpg,png, webp etc. but not for vector image
        /*val bigImage = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher) // replace with your image
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)*/

        //You're targeting API 28+ (Android 9+), where getDrawable() is not deprecated.
        val bitmap = (resources.getDrawable(R.drawable.ic_delete)as Drawable).toBitmap()

        val builder = NotificationCompat.Builder(this, channel_id).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setLargeIcon(vectorToBitmap(this@ServiceActivity,R.drawable.ic_delete))
            setContentTitle("My Notification")
            //setStyle(NotificationCompat.BigTextStyle().bigText("This is a big text this is a big text this is a big text this is a big text this is a big text").setSummaryText("#Breaking news"))
            setStyle(NotificationCompat.BigPictureStyle().bigPicture(vectorToBitmap(this@ServiceActivity,R.drawable.baseline_add_a_photo_24)).bigLargeIcon(null as Bitmap?))
            /**
             * This tells Kotlin:
             *.bigLargeIcon(null as Bitmap?)`
             * “Yes, I really mean this is a null of type Bitmap?.”
             */
            setContentText("This is the description of the notification")
            //setPriority(NotificationCompat.PRIORITY_HIGH)//if high then it will try to show the notification in the top of all the others notifications in the notification bar or panel
            setPriority(NotificationCompat.PRIORITY_DEFAULT)//the operating system will handle it when set to PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)//when click on the notification then the notification will be dismissed from the notification panel


        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(channel_id, "Tech News", NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Daily tech news update"
            }
            notificationManager.createNotificationChannel(mChannel)
        }

        val randomNumber = Random.nextInt(1000, 10000)
        notificationManager.notify(randomNumber, builder.build())
    }

    private fun vectorToBitmap(context:Context,drawableId:Int):Bitmap{
        val drawable = AppCompatResources.getDrawable(context,drawableId)?:return Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888)
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,drawable.intrinsicHeight,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0,0,canvas.width,canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}
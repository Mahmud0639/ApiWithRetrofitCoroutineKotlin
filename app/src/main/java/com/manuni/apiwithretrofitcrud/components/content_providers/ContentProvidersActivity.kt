package com.manuni.apiwithretrofitcrud.components.content_providers

import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.ActivityContentProvidersBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ContentProvidersActivity : AppCompatActivity() {
    private lateinit var binding:ActivityContentProvidersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentProvidersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //since we want to run coroutine with main thread
        runBlocking {
            val res = getContacts()
            println("$res")
            binding.contactsTxt.text = "$res"
        }

    }

    private suspend fun getContacts(): List<String>{
        val result:ArrayList<String> = arrayListOf()

        //we use Coroutine(also we can use Thread) to not to hang our main thread for the long running tasks
        withContext(Dispatchers.IO){
            contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)?.let { cursor ->
                while (cursor.moveToNext()){
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val phoneNo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    result.add("$name $phoneNo")
                }
                cursor.close()
            }

        }

        return result

    }
}

/**
 * 🔓 What Can You Access with Content Providers?
 *
 * 📱 1. Contacts
 * URI: ContactsContract.*
 *
 * Access to: names, phone numbers, emails, photos, birthdays, notes, groups, etc.
 *
 * 📷 2. Media (Gallery / Files)
 * URI: MediaStore.*
 *📸 2. Access Images from Gallery
 * ------------------------------------------------------------------------------
 * val cursor = contentResolver.query(
 *     MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
 *     arrayOf(MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA),
 *     null, null,
 *     "${MediaStore.Images.Media.DATE_ADDED} DESC"
 * )
 * ------------------------------------------------------------------------------
 * Access to:
 *
 * Images → MediaStore.Images.Media.EXTERNAL_CONTENT_URI
 *
 * Videos → MediaStore.Video.Media.EXTERNAL_CONTENT_URI
 * ----------------------------------------------------------------------------
 * val cursor = contentResolver.query(
 *     MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
 *     arrayOf(MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DURATION),
 *     null, null,
 *     null
 * )
 *
 * -----------------------------------------------------------------------------
 *
 * Audio → MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
 * --------------------------------------------------------------------------------
 * val cursor = contentResolver.query(
 *     MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
 *     arrayOf(MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST),
 *     null, null,
 *     null
 * )
 *--------------------------------------------------------------------------------
 * Downloads, Documents, Files → MediaStore.Files
 *
 * 🗂️ 3. Calendar Events
 * URI: CalendarContract.*
 *-----------------------------------------------------------------------------------
 *val cursor = contentResolver.query(
 *     CalendarContract.Events.CONTENT_URI,
 *     arrayOf(CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART),
 *     null, null,
 *     "${CalendarContract.Events.DTSTART} DESC"
 * )
 *
 * -----------------------------------------------------------------------------------
 * Access to:
 *
 * Events
 *
 * Reminders
 *
 * Attendees
 *
 * Calendars (Google, Exchange, local)
 *
 * You can see upcoming or past events a user has saved on their device.
 *
 * ✉️ 4. SMS and MMS
 * URI: content://sms/, content://mms/
 *------------------------------------------------------------------------------
 * val cursor = contentResolver.query(
 *     Uri.parse("content://sms/inbox"),
 *     arrayOf("address", "body", "date"),
 *     null, null,
 *     "date DESC"
 * )
 *--------------------------------------------------------------------------------
 * Access to:
 *
 * Sent, received, draft messages
 *
 * SMS thread info
 *
 * MMS attachments (images, etc.)
 *
 * You need special permission (READ_SMS) and Android might limit it to default SMS apps.
 *
 * 📥 5. Call Logs
 * URI: CallLog.Calls.CONTENT_URI
 *-----------------------------------------------------------------------------------------
 * val cursor = contentResolver.query(
 *     CallLog.Calls.CONTENT_URI,
 *     arrayOf(CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.DURATION),
 *     null, null,
 *     "${CallLog.Calls.DATE} DESC"
 * )
 *-----------------------------------------------------------------------------------------
 * Access to:
 *
 * Incoming/outgoing/missed calls
 *
 * Duration
 *
 * Timestamps
 *
 * Phone numbers
 *
 * 📂 6. Downloads
 *
 * URI: content://downloads/public_downloads
 * -------------------------------------------------------------------------
 *val uri = Uri.parse("content://downloads/public_downloads")
 * val cursor = contentResolver.query(uri, null, null, null, null)
 *----------------------------------------------------------------------------
 * Access to:
 *
 * Files downloaded via browser or apps
 *
 * Metadata (file size, name, path)
 *
 * 📄 7. Documents (via Storage Access Framework)
 * URI: DocumentsContract.*
 *
 * Useful for:
 *
 * Accessing files from Google Drive, internal storage, USB, etc.
 *
 * Works with ACTION_OPEN_DOCUMENT and similar Intents
 *
 * 📝 8. Notes (if using 3rd-party apps that expose them)
 * Some note-taking apps (like Evernote or Keep) may expose their data via custom content providers.
 *
 * ⚙️ 9. Settings (System/User)
 * URI: Settings.System, Settings.Secure, Settings.Global
 *
 * Access to:
 *
 * Ringtone
 *
 * Brightness
 *
 * Airplane mode, etc.
 *
 * Warning: Some of these are read-only or need system-level permissions.
 *
 * 🔐 Note on Permissions
 * Most of these require runtime permissions, like:
 *
 * READ_CONTACTS
 *
 * READ_EXTERNAL_STORAGE / READ_MEDIA_IMAGES
 *
 * READ_CALENDAR
 *
 * READ_CALL_LOG
 *
 * READ_SMS
 *
 * And from Android 11+ or 13+, the permission model gets even stricter — especially for media access, background location, etc.
 *
 *
 *
 *
 *
 *
 */
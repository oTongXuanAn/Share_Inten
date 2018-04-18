package com.example.xuanan.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.pm.ResolveInfo
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log


class MainActivity : AppCompatActivity() {
    var SHARE_TYPE_TEXT = "text/*"
    var SHARE_TYPE_IMG = "image/*"

    lateinit var button2: Button
    lateinit var button3: Button
    lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            ShareInApp(SHARE_TYPE_TEXT, null, null)
        }

        initRecycleView()

    }

    fun initRecycleView() {
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
    }


    private fun ShareInApp(type: String, text: String? = null, bitmap: Bitmap? = null) {
        var targetedShareIntents = ArrayList<Intent>();
        var targetedShareIntents2 = ArrayList<ResolveInfo>();
        var share = Intent(Intent.ACTION_SEND);
        share.setType(SHARE_TYPE_TEXT);
        var resInfo: List<ResolveInfo> = getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (info in resInfo) {
                if (!info.activityInfo.packageName.contains("line") && !info.activityInfo.packageName.contains("facebook")
                        && !info.activityInfo.packageName.contains("twice")
                        ) {
                    var targetedShare: Intent = Intent(Intent.ACTION_SEND);
                    targetedShare.putExtra(Intent.EXTRA_SUBJECT, "sub");
                    targetedShare.setType(type);
                    if (type == SHARE_TYPE_TEXT) {
                        targetedShare.putExtra(Intent.EXTRA_TEXT, text)
                    }
                    if (type == SHARE_TYPE_IMG && bitmap != null) {
                        var bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
                        var bitmapUri = Uri.parse(bitmapPath);
                        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                    }
                    targetedShare.setPackage(info.activityInfo.packageName);
                    targetedShareIntents.add(targetedShare)
                    targetedShareIntents2.add(info)
                    Log.e("log",""+info.activityInfo.packageName)
                }
            }
           // recyclerView.adapter = MyAdapter(this, targetedShareIntents2)
            if (targetedShareIntents.size > 0) {
                var chooserIntent = Intent.createChooser(targetedShareIntents.removeAt(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(arrayOf<Parcelable>()));
                startActivity(chooserIntent);
            }
        }
    }
}

package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_book.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.back
import kotlinx.android.synthetic.main.list_item.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        back.setOnClickListener {
            val k = Intent(this, Librarybook::class.java)
            startActivity(k)
        }
        edit.setOnClickListener {
            BookUpdate()
        }

        button11.setOnClickListener {
            deletBook()

            xbookName.text.clear()
            xbookAouthor.text.clear()
            xbookprice.text.clear()
            xbookluch.text.clear()

            Toast.makeText(applicationContext, "Book deleted Successfuly", Toast.LENGTH_LONG).show()
        }


        db = Firebase.firestore


        xbookName.setText(intent.getStringExtra("BookName").toString())
        xbookAouthor.setText(intent.getStringExtra("BookAuthor").toString())
        xbookluch.setText(intent.getStringExtra("BookLunch").toString())
        xbookprice.setText(intent.getStringExtra("Price").toString())


    }

    private fun deletBook() {
        db!!.collection("Library").get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                document.toObject<books>()
                if (document.get("id") ==
                    intent.getStringExtra("id")
                ) {
                    db!!.collection("Library").document(
                        document.id
                    ).delete()
                }
            }

        }

    }

    private fun BookUpdate() {
        db!!.collection("Library").get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                document.toObject<books>()
                if (document.get("id") == intent.getStringExtra("id")) {
                    db!!.collection("Library").document(document.id)
                        .update("BookName", xbookName.text.toString())
                    db!!.collection("Library").document(document.id)
                        .update("BookAuthor", xbookAouthor.text.toString())
                    db!!.collection("Library").document(document.id)
                        .update("Price", xbookprice.text.toString())
                    db!!.collection("Library").document(document.id)
                        .update("BookLunch", xbookluch.text.toString())



                }
            }
        }
    }


}

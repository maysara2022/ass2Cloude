package com.example.myapplication

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_add_book.*
import java.io.ByteArrayOutputStream

class AddBook : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    var storge: FirebaseStorage? = null
    var reference: StorageReference? = null

    lateinit var progressDialog: ProgressDialog

    var imageURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)
        db = Firebase.firestore

        val id = System.currentTimeMillis()

        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("maysara11")

        storge = Firebase.storage
        reference = storge!!.reference

        back.setOnClickListener {
            val i = Intent(this, Librarybook::class.java)
            startActivity(i)
        }



//        imageeeView2.setOnClickListener {
//            val intent = Intent()
//            intent.action = Intent.ACTION_PICK
//            intent.type = "image/*"
//            startActivityForResult(intent, 111)
//            imageeeView2.setBackgroundResource(0)
//        }


        button.setOnClickListener {
            showDialog()
//            val bitmap = (imageeeView2.drawable as BitmapDrawable).bitmap
//            val baos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//            val data = baos.toByteArray()
//            val childRef = imageRef.child(System.currentTimeMillis().toString() + ".png")
//            var uploadTask = childRef.putBytes(data)
//            uploadTask.addOnFailureListener { exception ->
//                hideDialog()
//                Toast.makeText(this, "exception", Toast.LENGTH_SHORT).show()
//
//            }.addOnSuccessListener {
//                childRef.downloadUrl.addOnSuccessListener { uri ->

                    addBook(
                        id.toString(),
                        bookName.text.toString(),
                        bookAouthor.text.toString(),
                        bookprice.text.toString(),
                        bookluch.text.toString()
                    )
                    hideDialog()
                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Librarybook::class.java))


//                }
//            }
        }
    }

    fun addBook(
        id: String,
        name: String,
        author: String,
        price: String,
        Lunch: String
    ) {
        val books = hashMapOf(
            "id" to id,
            "BookAuthor" to author,
            "BookName" to name,
            "BookLunch" to Lunch,
            "Price" to price
        )
        db!!.collection("MyLibrary").add(books)

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
//            imageURI = data!!.data
//            imageeeView2.setImageURI(imageURI)
//
//        }
//    }

    private fun showDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Uploading ...")
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    private fun hideDialog() {
        if (progressDialog!!.isShowing)
            progressDialog!!.dismiss()
    }

}

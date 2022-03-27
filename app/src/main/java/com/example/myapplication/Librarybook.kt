package com.example.myapplication

import android.app.PictureInPictureParams
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_librarybook.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*

class Librarybook : AppCompatActivity(){

    private var db: FirebaseFirestore? = null
    var adabter: FirestoreRecyclerAdapter<books, MyViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_librarybook)

        imageView3.setOnClickListener {
            val u = Intent(this, AddBook::class.java)
            startActivity(u)
        }
     getallbooks()


    }

    fun getallbooks() {

        val query = db!!.collection("MyLibrary")
        val option =
            FirestoreRecyclerOptions.Builder<books>().setQuery(query, books::class.java).build()
        adabter = object : FirestoreRecyclerAdapter<books, MyViewHolder>(option) {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val view =
                    LayoutInflater.from(this@Librarybook).inflate(R.layout.list_item, parent, false)
                return MyViewHolder(view)

            }


            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: books) {

                holder.xBookName.text = model.BookName
                holder.xBookAuthor.text = model.BookAuthor
                holder.xBookPrice.text = model.Price
                holder.xBookLunch.text = model.BookLunch
                holder.button3.setOnClickListener {
                    intent(
                        model.id,
                        model.BookName,
                        model.BookAuthor,
                        model.BookLunch,
                        model.Price


                    )

                }


            }


        }

        tvBooks.layoutManager = LinearLayoutManager(this)
        tvBooks.adapter = adabter
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var xBookName = view.textname
        var xBookAuthor = view.textAuthor
        var xBookPrice = view.textPrice
        var xBookLunch = view.lunchtext
        var button3 = view.button3


    }

    fun intent(
        id: String,
        Name_Book: String,
        Name_Author: String,
        Launch_Year: String,
        Price_Book: String
    ) {
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("id", id)
        i.putExtra("BookName", Name_Book)
        i.putExtra("BookAuthor", Name_Author)
        i.putExtra("BookLunch", Launch_Year)
        i.putExtra("Price", Price_Book)
        startActivity(i)


    }

    override fun onStart() {
        super.onStart()
        adabter!!.startListening()

    }

    override fun onStop() {
        super.onStop()
        adabter!!.stopListening()
    }

}
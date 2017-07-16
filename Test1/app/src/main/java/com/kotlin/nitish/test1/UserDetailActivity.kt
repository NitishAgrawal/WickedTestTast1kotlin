package com.kotlin.nitish.test1

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.kotlin.nitish.test1.models.DataModel
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {

    lateinit var dataModel : DataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        try {
            dataModel = getIntent().getParcelableExtra(UserFormActivity.PARSE_KEY)
            ivImage.setImageURI(dataModel.imagePath)
            tvUserName.setText(dataModel.userName)
            tvEmail.setText(dataModel.email)
            tvDOB.setText(dataModel.dob)
            tvGender.setText(dataModel.gender)
        } catch(e: Exception) {
        }
    }

}

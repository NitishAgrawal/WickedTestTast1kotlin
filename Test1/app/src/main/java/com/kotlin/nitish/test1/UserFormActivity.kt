package com.kotlin.nitish.test1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import com.kotlin.nitish.test1.models.DataModel
import kotlinx.android.synthetic.main.activity_user_form.*
import org.jetbrains.anko.toast
// import jdk.nashorn.internal.objects.NativeDate.getTime
import android.widget.DatePicker
import android.app.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.provider.MediaStore
import android.graphics.Bitmap
import android.R.attr.data
import android.app.Activity
import java.io.IOException
import android.util.Patterns
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern


class UserFormActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        dataModel.gender = if(p1 == R.id.female){
            FEMALE
        } else {
            MALE
        }
    }
    companion object {
       public val PARSE_KEY: String = "com.android.text.parse_key";
    }
    var dataModel : DataModel = DataModel()
    val MALE : String = "MALE"
    val FEMALE : String = "FEMALE"
    val PICK_IMAGE : Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_form)
        dataModel.gender = MALE;
        radio.setOnCheckedChangeListener(this)
        submit.setOnClickListener {
            onSubmitButtonClicked();
        }
        initDatePicker()
        etDOB.setOnClickListener {
            showDatePickerDialog();
        }
        ivImage.setOnClickListener {
            onUserImageOnClickListener();
        }
    }

    private fun showDatePickerDialog() {
        dobDatePickerDialog.show()
    }

    lateinit var  dobDatePickerDialog: DatePickerDialog

    private fun initDatePicker() {
        var dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val newCalendar = Calendar.getInstance()
        dobDatePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val newDate = Calendar.getInstance()
            newDate.set(year, monthOfYear, dayOfMonth)
            etDOB.setError(null)
            etDOB.setText(dateFormatter.format(newDate.getTime()))
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))
    }


    private fun onUserImageOnClickListener() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === PICK_IMAGE && resultCode === Activity.RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            try {
                dataModel.imagePath = uri
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                ivImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun onSubmitButtonClicked() {
        if (hasValue(etUserName) && isValidEmail(etEmail) && isValidPhoneNumber(etPhoneNumber) &&
                hasValue(etDOB) && hasImagePath()) {
            dataModel.userName = etUserName.text.toString()
            dataModel.email = etEmail.text.toString()
            dataModel.phoneNumber = etPhoneNumber.text.toString()
            dataModel.dob = etDOB.text.toString()
            goToFormDetailActivity()
        }
    }

    private fun goToFormDetailActivity() {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(PARSE_KEY, dataModel)
        startActivity(intent)
    }

    private fun hasValue(vararg editTexts: EditText): Boolean {
        try {
            var isHasValue = true
            for (editText in editTexts) {
                if ((editText == null || editText.length() < 1) && isHasValue) {
                    editText.error = "Please enter value"
                    isHasValue = false
                    return isHasValue
                }
            }
            return isHasValue
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    private fun hasImagePath(): Boolean {
        var isImagePath = false
        try {
            isImagePath = dataModel.imagePath.toString().length > 0
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (!isImagePath) {
            Toast.makeText(this, "Please select user image", Toast.LENGTH_LONG).show()
        }
        return isImagePath
    }

    fun isValidEmail(email: EditText?): Boolean {

        var isValid = false
        if (email != null && email.length() > 0) {
            val emailStr = email.text.toString()

            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val inputStr = emailStr

            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(inputStr)
            if (matcher.matches()) {
                isValid = true
            }
        }
        if (!isValid) {
            email!!.error = "Please enter valid email address"
        }
        return isValid
    }

    private fun isValidPhoneNumber(etPhoneNumber: EditText?): Boolean {
        var isValidNumber = false
        if (etPhoneNumber != null && etPhoneNumber.length() > 0) {
            val phoneNumberStr = etPhoneNumber.text
            if (!TextUtils.isEmpty(phoneNumberStr)) {
                isValidNumber = Patterns.PHONE.matcher(phoneNumberStr).matches()
            }
        }
        if (!isValidNumber) {
            etPhoneNumber!!.error = "Please enter valid phone number"
        }
        return isValidNumber
    }
}

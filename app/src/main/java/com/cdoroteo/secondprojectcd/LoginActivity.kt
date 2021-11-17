package com.cdoroteo.secondprojectcd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.util.PatternsCompat
import androidx.databinding.DataBindingUtil
import com.cdoroteo.secondprojectcd.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        auth = FirebaseAuth.getInstance()


        loginUser()
    }

    fun forgot(view: View) {
        startActivity(Intent(this, forgotActivity::class.java))
    }
    fun register(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun loginUser() {
        binding.btnLogin.setOnClickListener {
            validate()
            val user: String = binding.editTextEmail.text.toString()
            val password: String = binding.editTextPassword.text.toString()

            if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)) {

                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.editTextEmail.text.toString(),
                    binding.editTextPassword.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showProfile(
                                it.result?.user?.uid ?: "",
                                it.result?.user?.email ?: ""
                            )
                            finish()
                        } else {
                            errorMessage()
                        }
                    }
            }
        }

    }

    private fun validate(){
        val result = arrayOf(validateEmail(), validatePassword())

        if(false in result)
        {
            return
        }

    }

    private fun validateEmail() : Boolean{
        val email = binding.editTextEmail.editableText.toString()
        return if(email.isEmpty()){
            binding.editTextEmail.error = "Field can not be empty"
            false
        }else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
            binding.editTextEmail.error = "Please enter a valid email address"
            false
        }else{
            binding.editTextEmail.error = null
            true
        }

    }

    private fun validatePassword() : Boolean{
        val password = binding.editTextPassword.editableText.toString()
        /*val passwordRegex = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +  //at least 1 digit
                    "(?=.*[a-z])" +  //at least 1 lower case letter
                    "(?=.*[A-Z])" +  //at least 1 upper case letter
                    ".{6,}" +        //at least 6 characters
                    "$"
        )*/
        return if(password.isEmpty()){
            binding.editTextPassword.error = "Field can not be empty"
            false
        }else{
            binding.editTextPassword.error = null
            true
        }
    }


    private fun showProfile(userId:String, email:String)
    {
        val profileIntent = Intent(this, ProfileActivity::class.java).apply {
            putExtra("UserId", userId)
            putExtra("email", email)
        }
        startActivity(profileIntent)

    }

    private fun errorMessage(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage("The user could not be authenticated")
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}